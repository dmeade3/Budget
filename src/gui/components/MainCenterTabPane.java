package gui.components;

import javafx.scene.control.TabPane;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class MainCenterTabPane extends TabPane
{
    public MainCenterTabPane()
    {
        getTabs().add(new BudgetTab());
        getTabs().add(new TransactionTab());



        //addListeners();
    }

    /*private void addListeners()
    {
        // Make tabpane look like its selected
        SingleSelectionModel<Tab> selectionModel = getSelectionModel();

        add
    }*/
}
