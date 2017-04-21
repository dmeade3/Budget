package gui.components;

import data.MainProgramDatastore;
import gui.RootPage;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import util.SystemInfo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/14/2017.
 */
public class LoginDialog extends Dialog
{
    public LoginDialog(ComboBox<String> dropDownUser, String newUser)
    {
        super();

        // Create the custom dialog.
        setTitle("Login Dialog");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create password label and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Password:"), 0, 0);
        grid.add(password, 1, 0);

        getDialogPane().setContent(grid);

        showAndWait();

        // Evaluate is password is correct
        if (passwordCheck(password.getText(), newUser))
        {
            SystemInfo.CURRENT_USER = newUser;

            MainProgramDatastore.getInstance().loadCurrentUser();

            RootPage.reloadAll(MainProgramDatastore.getInstance().getSelectedMainTabIndex());
        }
        else
        {
            RootPage.reloadAll(MainProgramDatastore.getInstance().getSelectedMainTabIndex());
        }
    }

    public static boolean passwordCheck(String typedPassword, String newUser)
    {

        System.out.println("new user: " + newUser);

        File file = new File(USERS_PATH + "\\" + "users.csv");
        try
        {
            Path path = Paths.get(String.valueOf(file));
            Charset charset = StandardCharsets.UTF_8;

            String content = new String(Files.readAllBytes(path), charset);

            String[] contentLines = content.split("\n");

            int ctr = 0;
            for (String line : contentLines)
            {
                // Skip header
                if ((ctr == 0) || (!line.contains(newUser)))
                {
                    ctr++;
                    continue;
                }

                System.out.println(line);

                String[] splitLine = line.split(",");

                System.out.println(splitLine[1].replaceAll("\"", "").equals(typedPassword));

                return splitLine[1].replaceAll("\"", "").equals(typedPassword);
            }


            Files.write(path, content.getBytes(charset));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
