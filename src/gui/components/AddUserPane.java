package gui.components;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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

import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class AddUserPane extends GridPane
{
    private Button saveButton;
    private TextField nameField;
    private TextField passwordField;
    private Label nameLabel;
    private Label passwordLabel;


    // TODO refactor this
    public AddUserPane()
    {
        setPadding(new Insets(5));
        setHgap(5);
        setVgap(5);

        nameLabel = new Label("Name");
        nameField = new TextField();

        passwordLabel = new Label("Password");
        passwordField = new TextField();

        saveButton = new Button("Save");

        populateGridPane();

        addListeners();
    }

    private void populateGridPane()
    {
        GridPane.setHalignment(nameLabel, HPos.RIGHT);
        add(nameLabel, 0, 0);
        GridPane.setHalignment(nameField, HPos.LEFT);
        add(nameField, 1, 0);

        GridPane.setHalignment(passwordLabel, HPos.RIGHT);
        add(passwordLabel, 0, 1);
        GridPane.setHalignment(passwordField, HPos.LEFT);
        add(passwordField, 1, 1);

        // Save button
        GridPane.setHalignment(saveButton, HPos.RIGHT);
        add(saveButton, 1, 6);
    }

    private void addListeners()
    {
        saveButton.setOnMouseClicked(event ->
        {
            // Try and make the new dir for the user
            boolean success = (new File(USERS_PATH + "\\" + nameField.getText())).mkdirs();

            if (success)
            {
                String newUser = "\"" + nameField.getText() + "\",\"" + passwordField.getText() + "\"";

                File file = new File(USERS_PATH + "\\users.csv");
                try
                {
                    Path path = Paths.get(String.valueOf(file));
                    Charset charset = StandardCharsets.UTF_8;

                    String content = new String(Files.readAllBytes(path), charset);

                    content = content + "\n" + newUser;

                    Files.write(path, content.getBytes(charset));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Could not create new directory for user");
                alert.setHeaderText("Sorry bro");
                alert.showAndWait();
            }
        });
    }
}