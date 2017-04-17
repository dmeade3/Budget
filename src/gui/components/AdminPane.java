package gui.components;

import data.MainProgramDatastore;
import gui.RootPage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static util.SystemInfo.CURRENT_USER;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class AdminPane extends TitledPane
{
    private ComboBox<String> dropDownUser;
    private Button addUser;
    private Button addAccount;
    private Button addBudgetSection;
    private Button addTransaction;

    public AdminPane()
    {
        super();

        setText("Admin Section");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        dropDownUser = new ComboBox<>();

        for (String userName : MainProgramDatastore.getInstance().getAllUserNames())
        {
            dropDownUser.getItems().add(userName);
        }

        // Set the default user as the first in the list for now
        // TODO eventually save off the most recently used user and load here instead
        dropDownUser.getSelectionModel().selectFirst();

        CURRENT_USER = dropDownUser.getSelectionModel().getSelectedItem();

	    // TODO
	    // Buttons that add users, accounts, budget sections, transactions
	    addUser = new Button("Add User");
	    addAccount = new Button("Add Account");
	    addBudgetSection = new Button("Add Budget Section");
	    addTransaction = new Button("Add Transaction");

        gridPane.add(dropDownUser, 0, 0);
	    gridPane.add(addUser, 1, 0);
	    gridPane.add(addAccount, 2, 0);
	    gridPane.add(addBudgetSection, 3, 0);
	    gridPane.add(addTransaction, 4, 0);

        setAlignment(Pos.CENTER);
        setContent(gridPane);
        setCollapsible(false);

        addListeners();
    }

    private void addListeners()
    {
        addUser.setOnMouseClicked(event ->
        {
            // Launch edit pane
            Stage stage = new Stage();
            Scene scene = new Scene(new AddUserPane(), 400, 300);

            stage.setScene(scene);
            stage.setTitle("Add User");
            stage.show();

            stage.setOnCloseRequest(event2 ->
            {
                MainProgramDatastore.getInstance().loadCurrentUser();

                RootPage.reloadAll(); // TODO figure out a better way to do this, maybe map the tabs to their index
            });
        });

        dropDownUser.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) ->
        {
            if ((t1 != null) && (!t.equals(t1)))
            {
                new LoginDialog();

                CURRENT_USER = dropDownUser.getSelectionModel().getSelectedItem();

                MainProgramDatastore.getInstance().loadCurrentUser();

                RootPage.reloadAllButAdmin();
            }
        });
    }
}
