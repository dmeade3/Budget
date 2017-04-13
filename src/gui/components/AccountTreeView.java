package gui.components;

import data.MainProgramDatastore;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import user.accounts.Account;
import user.accounts.AccountType;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class AccountTreeView extends StackPane
{
    public AccountTreeView()
    {
        super();

        TreeItem<String> rootTreeItem = new TreeItem<> ("All");
        rootTreeItem.setExpanded(true);

        for (AccountType accountType : AccountType.values())
        {
            TreeItem<String> item = new TreeItem<> (accountType.getName());

            //Read CSV line by line and use the string array as you want
            for(Account account : MainProgramDatastore.getInstance().getLoadedUser().getAccounts())
            {
                // make sure row exists and matches type for section
                if (account.getAccountType().getName().equals(accountType.getName()))
                {
                    TreeItem<String> item2 = new TreeItem<> (account.getName());
                    item.getChildren().add(item2);
                }
            }

            // Only add the account type to the tree if the account type has an account
            if (item.getChildren().size() > 0)
            {
                rootTreeItem.getChildren().add(item);
            }
        }

        TreeView<String> tree = new TreeView<> (rootTreeItem);

        // Hide the main root item
        //tree.setShowRoot(false);

        tree.getSelectionModel().select(rootTreeItem);

        getChildren().addAll(tree);
    }
}
