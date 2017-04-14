package gui.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;

/**
 * Created by dcmeade on 4/14/2017.
 */
public class LoginDialog extends Dialog
{
    public LoginDialog()
    {
        super();

        // Create the custom dialog.
        setTitle("Login Dialog");
        setHeaderText("Look, a Custom Login Dialog");

        // Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));


        PasswordField password = new PasswordField();
        password.setPromptText("Password");


        grid.add(new Label("Password:"), 0, 0);
        grid.add(password, 1, 0);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = getDialogPane().lookupButton(loginButtonType);
        

        getDialogPane().setContent(grid);



        Optional<Pair<String, String>> result = showAndWait();
    }
}
