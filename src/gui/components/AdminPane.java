package gui.components;

import data.MainProgramDatastore;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import util.SystemInfo;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class AdminPane extends TitledPane
{
    public AdminPane()
    {
        super();

        setText("Admin Section");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ComboBox<String> dropDownUser = new ComboBox<>();
        MainProgramDatastore.getInstance().readInUserNames();

        for (String name : MainProgramDatastore.getInstance().getUserNames())
        {
            dropDownUser.getItems().add(name);
        }

        dropDownUser.getSelectionModel().selectFirst();

        SystemInfo.CURRENT_USER = dropDownUser.getSelectionModel().getSelectedItem();

        gridPane.getChildren().addAll(dropDownUser);

        // TODO needs listener to load new content / save if not already saved

        setContent(gridPane);

        setCollapsible(false);
    }
}
