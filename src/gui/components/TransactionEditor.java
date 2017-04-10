package gui.components;

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
    public TransactionEditor(String oldTransaction)
    {
        setPadding(new Insets(5));
        setHgap(5);
        setVgap(5);

        String oldTransactionArray[] = oldTransaction.split(",");

        Label dateLabel = new Label("Date");
        TextField dateField = new TextField(oldTransactionArray[0]);

        Label amountLabel = new Label("Amount");
        TextField amountField = new TextField(oldTransactionArray[1]);

        Label mysteryLabel = new Label("Mystery");
        TextField mysteryField = new TextField(oldTransactionArray[2]);

        Label checkNumberLabel = new Label("Check Number");
        TextField checkNumberField = new TextField(oldTransactionArray[3]);

        Label descriptionLabel = new Label("Description");
        TextField descriptionField = new TextField(oldTransactionArray[4]);

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

            String newTransaction = "\"" + dateField.getText() + "\",\"" + amountField.getText() + "\",\"" + mysteryField.getText()
                    + "\",\"" + checkNumber + "\",\"" + descriptionField.getText() + "\"";

            StringBuilder replace = new StringBuilder();

            // Modify the old transaction
            for (int i = 0; i < oldTransactionArray.length; i++)
            {
                if ((i == 3) && (oldTransactionArray[3].equals("0")))
                {
                    oldTransactionArray[3] = "";
                }

                replace.append("\"" + oldTransactionArray[i] + "\"");

                if (i != oldTransactionArray.length-1)
                {
                    replace.append(",");
                }
            }

            // Get all file names under dir
            File folder = new File(USERS_PATH + "\\" + CURRENT_USER + "\\transactions\\");
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++)
            {
                if (!listOfFiles[i].isDirectory())
                {
                    // TODO turn these prints into logging
                    try
                    {
                        Path path = Paths.get(String.valueOf(listOfFiles[i]));
                        Charset charset = StandardCharsets.UTF_8;

                        String content = new String(Files.readAllBytes(path), charset);

                        content = content.replace(replace.toString(), newTransaction);

                        Files.write(path, content.getBytes(charset));
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
