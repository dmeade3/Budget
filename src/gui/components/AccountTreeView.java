package gui.components;

import com.opencsv.CSVReader;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import user.accounts.AccountType;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static util.SystemInfo.CURRENT_USER;
import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class AccountTreeView extends StackPane
{
    public AccountTreeView()
    {
        super();

        TreeItem<String> rootTreeItem = new TreeItem<> ("I'm the root and should be hidden");
        rootTreeItem.setExpanded(true);

        for (AccountType accountType : AccountType.values())
        {
            TreeItem<String> item = new TreeItem<> (accountType.getName());

            // TODO have better way to point to this file in the future
            try(CSVReader reader = new CSVReader(new FileReader(USERS_PATH + "\\" + CURRENT_USER + "\\accounts.csv"), ',' , '"' , 1))
            {
                //Read all rows at once
                List<String[]> allRows = reader.readAll();

                //Read CSV line by line and use the string array as you want
                for(String[] row : allRows)
                {
                    // make sure row exists and matches type for section
                    if ((row.length > 1) && (row[1]).equals(accountType.getName()))
                    {
                        TreeItem<String> item2 = new TreeItem<> (row[0]);
                        item.getChildren().add(item2);
                    }
                }

                // Only add the account type to the tree if the account type has an account
                if (item.getChildren().size() > 0)
                {
                    rootTreeItem.getChildren().add(item);
                }
            }
            catch (IOException e)
            {
                // TODO logger.warn("Could not open file: " + filename);
            }
        }

        TreeView<String> tree = new TreeView<> (rootTreeItem);

        // Hide the main root item
        tree.setShowRoot(false);

        getChildren().addAll(tree);
    }
}
