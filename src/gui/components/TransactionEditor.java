package gui.components;

import data.csv_handling.transaction_handling.Transaction;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static util.SystemInfo.CURRENT_USER;
import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class TransactionEditor extends GridPane
{
    public TransactionEditor(Transaction oldTransaction)
    {
        setPadding(new Insets(5));
        setHgap(5);
        setVgap(5);

        Label dateLabel = new Label("Date");
        TextField dateField = new TextField(oldTransaction.getDate());

        Label amountLabel = new Label("Amount");
        TextField amountField = new TextField(String.valueOf(oldTransaction.getAmount()));

        Label mysteryLabel = new Label("Mystery");
        TextField mysteryField = new TextField(String.valueOf(oldTransaction.getMystery()));

        Label checkNumberLabel = new Label("Check Number");
        TextField checkNumberField = new TextField(String.valueOf(oldTransaction.getCheckNumber()));

        Label descriptionLabel = new Label("Description");
        TextField descriptionField = new TextField(oldTransaction.getDescription());

        Button saveButton = new Button("Save");

        GridPane.setHalignment(dateLabel, HPos.RIGHT);
        add(dateLabel, 0, 0);
        GridPane.setHalignment(dateField, HPos.LEFT);
        add(dateField, 1, 0);

        GridPane.setHalignment(amountLabel, HPos.RIGHT);
        add(amountLabel, 0, 1);
        GridPane.setHalignment(amountField, HPos.LEFT);
        add(amountField, 1, 1);

        GridPane.setHalignment(mysteryLabel, HPos.RIGHT);
        add(mysteryLabel, 0, 2);
        GridPane.setHalignment(mysteryField, HPos.LEFT);
        add(mysteryField, 1, 2);

        GridPane.setHalignment(checkNumberLabel, HPos.RIGHT);
        add(checkNumberLabel, 0, 3);
        GridPane.setHalignment(checkNumberField, HPos.LEFT);
        add(checkNumberField, 1, 3);

        GridPane.setHalignment(descriptionLabel, HPos.RIGHT);
        add(descriptionLabel, 0, 4);
        GridPane.setHalignment(descriptionField, HPos.LEFT);
        add(descriptionField, 1, 4);

        // Save button
        GridPane.setHalignment(saveButton, HPos.RIGHT);
        add(saveButton, 1, 6);


        // TODO
        // On save verify right format and replace the transaction in history
        saveButton.setOnMouseClicked(event ->
        {
            String checkNumber = checkNumberField.getText();

            if (Integer.valueOf(checkNumberField.getText()).equals(0))
            {
                checkNumber = "";
            }

            String newTransaction = "\"" + dateField.getText() + "\",\"" +
                                    Transaction.formatter.format(Double.valueOf(amountField.getText())) + "\",\"" +
                                    mysteryField.getText() + "\",\"" +
                                    checkNumber + "\",\"" +
                                    descriptionField.getText() + "\"";

            StringBuilder replace = new StringBuilder();
            String oldTransactionArray[] = oldTransaction.guiViewString().split(",");

            // Modify the old transaction
            for (int i = 0; i < oldTransactionArray.length; i++)
            {
                if ((i == 3) && (oldTransactionArray[3].equals("0")))
                {
                    oldTransactionArray[3] = "";
                }

                replace.append("\"" + oldTransactionArray[i] + "\"");

                // Separate the items
                if (i != oldTransactionArray.length-1)
                {
                    replace.append(",");
                }
            }

            // Get all file names under dir
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
