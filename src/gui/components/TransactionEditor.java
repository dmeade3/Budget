package gui.components;

import data.MainProgramDatastore;
import data.csv_handling.transaction_handling.Transaction;
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
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static data.MainProgramDatastore.getTransactionColumnIndex;
import static util.SystemInfo.CURRENT_USER;
import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class TransactionEditor extends GridPane
{
    private Button saveButton;
    private TextField dateField;
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
        categoryComboBox.getItems().addAll(BudgetCategory.values());
        try
        {
            categoryComboBox.getSelectionModel().select(BudgetCategory.valueOf(oldTransaction.getCategory()));
        }
        catch (IllegalArgumentException e)
        {
            categoryComboBox.getSelectionModel().select(null);
        }

        saveButton = new Button("Save");

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
    }

    private void addListeners()
    {
        // TODO
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
            String oldTransactionArray[] = oldTransaction.guiViewString().split(",");

            // Format the date
            oldTransactionArray[getTransactionColumnIndex("name")] = Transaction.dateFormat.format(new Date(oldTransactionArray[getTransactionColumnIndex("name")]));

            // Modify the old transaction
            for (int i = 0; i < oldTransactionArray.length; i++)
            {
                if ((i == getTransactionColumnIndex("checkNumber")) && (oldTransactionArray[getTransactionColumnIndex("checkNumber")].equals("0")))
                {
                    oldTransactionArray[getTransactionColumnIndex("checkNumber")] = "";
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

                System.out.println("replace");
                System.out.println(replace);

                content = content.replace(replace.toString(), newTransaction);

                System.out.println(content);

                Files.write(path, content.getBytes(charset));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }
}
