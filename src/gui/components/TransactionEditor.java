package gui.components;

import com.opencsv.CSVReader;
import data.MainProgramDatastore;
import data.csv_handling.transaction_handling.Transaction;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import user.accounts.Account;
import user.budget.BudgetCategory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static data.csv_handling.transaction_handling.TransactionSource.WELLSFARGOCHECKING;
import static util.SystemInfo.CURRENT_USER;
import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class TransactionEditor extends GridPane
{
    private Button saveButton;
    private Button deleteButton;
    private TextField dateField;
    private TextField amountField;
    private TextField checkNumberField;
    private TextField descriptionField;
    private ComboBox<String> accountNameComboBox;
    private ComboBox<String> categoryComboBox;
    private Transaction oldTransaction;
    private Label dateLabel;
    private Label amountLabel;
    private Label checkNumberLabel;
    private Label descriptionLabel;
    private Label accountNameLabel;
    private Label categoryLabel;


    // TODO refactor this
    public TransactionEditor(Transaction oldTransaction)
    {
        this.oldTransaction = oldTransaction;

        setPadding(new Insets(5));
        setHgap(5);
        setVgap(5);

        dateLabel = new Label("Date");
        dateField = new TextField(Transaction.dateFormat.format(oldTransaction.getDate()));

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
        // add user categories
        File file = new File(USERS_PATH + "\\" + CURRENT_USER + "\\userCategories.csv");
        List<String> userCategories = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(file), ',', '"', 1))
        {
            //Read all rows at once
            List<String[]> allRows = reader.readAll();

            //Read CSV line by line and use the string array as you want
            for (String[] row : allRows)
            {
                // make sure row exists and matches type for section
                if (row.length > 0)
                {
                    userCategories.add(row[0]);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Cant open userCategories.csv: " + e.getMessage());
        }

        // Add user categories
        categoryComboBox.getItems().addAll(userCategories);

        // Add the default
        for (BudgetCategory budgetCategory : BudgetCategory.values())
        {
            categoryComboBox.getItems().add(budgetCategory.name());
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
        final ObservableList<String> comboBoxItems = categoryComboBox.getItems();
        categoryComboBox.setItems(new SortedList<>(comboBoxItems, Collator.getInstance()));

        saveButton = new Button("Save");
        deleteButton = new Button("Delete");

        populateGridPane();

        addListeners();
    }

    private void populateGridPane()
    {
        GridPane.setHalignment(dateLabel, HPos.RIGHT);
        add(dateLabel, 0, 0);
        GridPane.setHalignment(dateField, HPos.LEFT);
        add(dateField, 1, 0);

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
        GridPane.setHalignment(saveButton, HPos.RIGHT);
        add(saveButton, 1, 6);

        // Delete Button
        GridPane.setHalignment(deleteButton, HPos.RIGHT);
        add(deleteButton, 0, 6);
    }

    private void addListeners()
    {
        deleteButton.setOnMouseClicked(event ->
        {
            StringBuilder replace = new StringBuilder();
            String oldTransactionArray[] = oldTransaction.transactionEditorString().split(",");

            // Format the date
            oldTransactionArray[WELLSFARGOCHECKING.getHeaderIndex("date")] = Transaction.dateFormat.format(new Date(oldTransactionArray[WELLSFARGOCHECKING.getHeaderIndex("date")]));

            // Modify the old transaction
            for (int i = 0; i < oldTransactionArray.length; i++)
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

            File file = new File(USERS_PATH + "\\" + CURRENT_USER + "\\transactions.csv");
            try
            {
                Path path = Paths.get(String.valueOf(file));
                Charset charset = StandardCharsets.UTF_8;

                String content = new String(Files.readAllBytes(path), charset);

                //System.out.println("replace");
                //System.out.println(replace);

                content = content.replace(replace.toString(), "");

                //System.out.println(content);

                Files.write(path, content.getBytes(charset));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            saveButton.setDisable(true);
            deleteButton.setDisable(true);
            dateField.setDisable(true);
            amountField.setDisable(true);
            checkNumberField.setDisable(true);
            descriptionField.setDisable(true);
            accountNameComboBox.setDisable(true);
            categoryComboBox.setDisable(true);
        });


        // On save verify right format and replace the transaction in history
        saveButton.setOnMouseClicked(event ->
        {
            String checkNumber = checkNumberField.getText();

            if (Integer.valueOf(checkNumberField.getText()).equals(0))
            {
                checkNumber = "";
            }

            String newTransaction = "\"" + Transaction.dateFormat.format(new Date(dateField.getText())) + "\",\"" +
                    Transaction.formatter.format(Double.valueOf(amountField.getText())) + "\",\"" +
                    checkNumber + "\",\"" +
                    descriptionField.getText() + "\",\"" +
                    accountNameComboBox.getSelectionModel().getSelectedItem() + "\",\"" +
                    categoryComboBox.getSelectionModel().getSelectedItem() + "\""
                    ;

            StringBuilder replace = new StringBuilder();
            String oldTransactionArray[] = oldTransaction.transactionEditorString().split(",");

            // Format the date
            oldTransactionArray[WELLSFARGOCHECKING.getHeaderIndex("date")] = Transaction.dateFormat.format(new Date(oldTransactionArray[WELLSFARGOCHECKING.getHeaderIndex("date")]));

            // Modify the old transaction
            for (int i = 0; i < oldTransactionArray.length; i++)
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


            File file = new File(USERS_PATH + "\\" + CURRENT_USER + "\\transactions.csv");
            try
            {
                Path path = Paths.get(String.valueOf(file));
                Charset charset = StandardCharsets.UTF_8;

                String content = new String(Files.readAllBytes(path), charset);

                //System.out.println("replace");
                //System.out.println(replace);

                content = content.replace(replace.toString(), newTransaction);

                //System.out.println(content);

                Files.write(path, content.getBytes(charset));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        });
    }
}
