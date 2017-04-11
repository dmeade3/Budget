package gui.components;

import data.MainProgramDatastore;
import gui.RootPage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import util.SystemInfo;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class AdminPane extends TitledPane
{
    ComboBox<String> dropDownUser;

    public AdminPane()
    {
        super();

        setText("Admin Section");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        dropDownUser = new ComboBox<>();
        MainProgramDatastore.getInstance().readInUserNames();

        for (String name : MainProgramDatastore.getInstance().getUserNames())
        {
            dropDownUser.getItems().add(name);
        }

        dropDownUser.getSelectionModel().selectFirst();

        SystemInfo.CURRENT_USER = dropDownUser.getSelectionModel().getSelectedItem();

	    // TODO
	    // Buttons that add users, accounts, budget sections, transactions
	    Button addUser = new Button("Add User");
	    Button addAccount = new Button("Add Account");
	    Button addBudgetSection = new Button("Add Budget Section");
	    Button addTransaction = new Button("Add Transaction");

        gridPane.add(dropDownUser, 0, 0);
	    gridPane.add(addUser, 1, 0);
	    gridPane.add(addAccount, 2, 0);
	    gridPane.add(addBudgetSection, 3, 0);
	    gridPane.add(addTransaction, 4, 0);

        // TODO needs listener to load new content / save if not already saved

        setAlignment(Pos.CENTER);
        setContent(gridPane);
        setCollapsible(false);

        addListeners();
    }

    private void addListeners()
    {
        dropDownUser.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) ->
        {
            if ((t1 != null) && (!t.equals(t1)))
            {
                SystemInfo.CURRENT_USER = dropDownUser.getSelectionModel().getSelectedItem();

                RootPage.reloadAllButAdmin();
            }
        });
    }
}
