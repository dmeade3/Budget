package gui.components;

import javafx.scene.control.Tab;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class TransactionTab extends Tab
{
    public TransactionTab()
    {
        super("Transactions");

        setContent(new TransactionTable());

        setClosable(false);
    }
}
