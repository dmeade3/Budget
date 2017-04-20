package gui.components;

import data.MainProgramDatastore;
import data.csv_handling.transaction_handling.Transaction;
import gui.RootPage;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import user.accounts.Account;
import user.budget.BudgetCategory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import static data.csv_handling.transaction_handling.TransactionSource.WELLSFARGOCHECKING;
import static util.SystemInfo.*;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class TransactionEditor extends GridPane
{
    private ButtonType saveButton;
    private ButtonType deleteButton;
    private DatePicker datePicker;
    private TextField amountField;
    private TextField checkNumberField;
    private TextField descriptionField;
    private ComboBox<String> accountNameComboBox;
    private ComboBox<BudgetCategory> categoryComboBox;
    private Transaction oldTransaction;
    private Label dateLabel;
    private Label amountLabel;
    private Label checkNumberLabel;
    private Label descriptionLabel;
    private Label accountNameLabel;
    private Label categoryLabel;
    private Dialog dialog;


    // TODO refactor this
    public TransactionEditor(Transaction oldTransaction, Dialog dialog)
    {
        this.oldTransaction = oldTransaction;
        this.dialog = dialog;

        setPadding(new Insets(5));
        setHgap(5);
        setVgap(5);

        dateLabel = new Label("Date");

        // TODO make the string here the same as in transaction
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        datePicker = new DatePicker(LocalDate.parse(Transaction.dateFormat.format(oldTransaction.getDate()), formatter));

        amountLabel = new Label("Amount");
        amountField = new TextField(String.valueOf(oldTransaction.getAmount()));

        checkNumberLabel = new Label("Check Number");
        checkNumberField = new TextField(String.valueOf(oldTransaction.getCheckNumber()));

        descriptionLabel = new Label("Description");
        descriptionField = new TextField(oldTransaction.getDescription());

        accountNameLabel = new Label("Account Name");
        accountNameComboBox = new ComboBox();

        // init accountName combobox
        for (Account account : MainProgramDatastore.getInstance().getLoadedUser().getAccounts())
        {
            accountNameComboBox.getItems().add(account.getName());
        }

        if (accountNameComboBox.getItems().contains(oldTransaction.getAccountName()))
        {
            accountNameComboBox.getSelectionModel().select(oldTransaction.getAccountName());
        }
        else
        {
            accountNameComboBox.getSelectionModel().select(null);
        }

        categoryLabel = new Label("Category");
        categoryComboBox = new ComboBox(); // TODO the values for the should be read in by enum / user categories

        // init category combobox

        // Add the default
        for (BudgetCategory budgetCategory : BudgetCategory.values())
        {
            categoryComboBox.getItems().add(budgetCategory);
        }

        // Select default
        try
        {
            categoryComboBox.getSelectionModel().select(oldTransaction.getCategory());
        }
        catch (IllegalArgumentException e)
        {
            categoryComboBox.getSelectionModel().select(null);
        }

        // Sort combo Box
        final ObservableList<BudgetCategory> comboBoxItems = categoryComboBox.getItems();

        categoryComboBox.setItems(comboBoxItems.sorted());

        saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(saveButton);

        deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(deleteButton);

        populateGridPane();

        addListeners();
    }

    private void populateGridPane()
    {
        GridPane.setHalignment(dateLabel, HPos.RIGHT);
        add(dateLabel, 0, 0);
        GridPane.setHalignment(datePicker, HPos.LEFT);
        add(datePicker, 1, 0);

        GridPane.setHalignment(amountLabel, HPos.RIGHT);
        add(amountLabel, 0, 1);
        GridPane.setHalignment(amountField, HPos.LEFT);
        add(amountField, 1, 1);

        GridPane.setHalignment(checkNumberLabel, HPos.RIGHT);
        add(checkNumberLabel, 0, 2);
        GridPane.setHalignment(checkNumberField, HPos.LEFT);
        add(checkNumberField, 1, 2);

        GridPane.setHalignment(descriptionLabel, HPos.RIGHT);
        add(descriptionLabel, 0, 3);
        GridPane.setHalignment(descriptionField, HPos.LEFT);
        add(descriptionField, 1, 3);

        GridPane.setHalignment(accountNameLabel, HPos.RIGHT);
        add(accountNameLabel, 0, 4);
        GridPane.setHalignment(accountNameComboBox, HPos.LEFT);
        add(accountNameComboBox, 1, 4);

        GridPane.setHalignment(categoryLabel, HPos.RIGHT);
        add(categoryLabel, 0, 5);
        GridPane.setHalignment(categoryComboBox, HPos.LEFT);
        add(categoryComboBox, 1, 5);

        // Save button
        /*GridPane.setHalignment(saveButton, HPos.RIGHT);
        add(saveButton, 1, 6);

        // Delete Button
        GridPane.setHalignment(deleteButton, HPos.RIGHT);
        add(deleteButton, 0, 6);*/
    }

    private void addListeners()
    {
        // TODO refactor this to not have repeat code and to be very reliable
        dialog.setResultConverter(b ->
        {
            StringBuilder replace = new StringBuilder();
            String replaceWith = "";

            //System.out.println(oldTransaction.transactionEditorString());

            String oldTransactionArray[] = oldTransaction.transactionEditorString().split(SEPARATOR);

            //System.out.println(oldTransactionArray.length);

            // Format the date
            oldTransactionArray[WELLSFARGOCHECKING.getHeaderIndex("date")] = Transaction.dateFormat.format(new Date(oldTransactionArray[WELLSFARGOCHECKING.getHeaderIndex("date")]));

            // Modify the old transaction
            for (int i = 0; i < Arrays.asList(oldTransactionArray).size(); i++)
            {
                if (i == WELLSFARGOCHECKING.getHeaderIndex("unknown"))
                {
                    continue;
                }

                if ((i == WELLSFARGOCHECKING.getHeaderIndex("checkNumber")) && (oldTransactionArray[WELLSFARGOCHECKING.getHeaderIndex("checkNumber")].equals("0")))
                {
                    oldTransactionArray[WELLSFARGOCHECKING.getHeaderIndex("checkNumber")] = "";
                }

                replace.append("\"" + oldTransactionArray[i] + "\"");

                // Separate the items
                if (i != oldTransactionArray.length-1)
                {
                    replace.append(",");
                }
            }

            // On save verify right format and replace the transaction in history
            if (b == saveButton)
            {
                String checkNumber = checkNumberField.getText();

                if (Integer.valueOf(checkNumberField.getText()).equals(0))
                {
                    checkNumber = "";
                }

                // Format the date from the datepicker
                LocalDate localDate = datePicker.getValue();
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                Date date = Date.from(instant);
                String dateVal = Transaction.dateFormat.format(date);

                replaceWith = "\"" +
                        dateVal + "\",\"" +
                        Transaction.formatter.format(Double.valueOf(amountField.getText())) + "\",\"" +
                        checkNumber + "\",\"" +
                        descriptionField.getText() + "\",\"" +
                        accountNameComboBox.getSelectionModel().getSelectedItem() + "\",\"" +
                        categoryComboBox.getSelectionModel().getSelectedItem() + "\""
                        ;
            }
            else if (b == deleteButton)
            {
                // Nothing needs to be done
            }

            // Replace / Delete the transaction in the file
            File file = new File(USERS_PATH + "\\" + CURRENT_USER + "\\transactions.csv");
            try
            {
                Path path = Paths.get(String.valueOf(file));
                Charset charset = StandardCharsets.UTF_8;

                String content = new String(Files.readAllBytes(path), charset);

                //System.out.println("replace");
                //System.out.println(replace);

                content = content.replace(replace.toString(), replaceWith);

                //System.out.println(content);

                Files.write(path, content.getBytes(charset));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            // Reload the Center
            RootPage.reloadCenter(MainProgramDatastore.getInstance().getSelectedMainTabIndex());

            return "Results";
        });
    }
}
