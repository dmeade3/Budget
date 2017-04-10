package gui.components;

import com.opencsv.CSVReader;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import user.budget.Budget;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static util.SystemInfo.CURRENT_USER;
import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class BudgetTab extends Tab
{
    // TODO Eventually make these objects
    public BudgetTab()
    {
        super("Budget");

        ScrollPane middleScrollPane = new ScrollPane();
        middleScrollPane.setFitToHeight(true);
        middleScrollPane.setFitToWidth(true);

        ListView<BudgetWithProgressBar> mainBudgetMiddleList = new ListView<>();

        // TODO have better way to point to this file in the future
        try(CSVReader reader = new CSVReader(new FileReader(USERS_PATH + "\\" + CURRENT_USER + "\\budget.csv"), ',' , '"' , 1))
        {
            //Read all rows at once
            List<String[]> allRows = reader.readAll();

            //Read CSV line by line and use the string array as you want
            for(String[] row : allRows)
            {
                // make sure row exists
                if (row.length > 1)
                {
                    mainBudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget(row[0], Integer.valueOf(row[1]), Double.valueOf(row[2]))));
                }
            }
        }
        catch (IOException e)
        {
            // TODO logger.warn("Could not open file: " + filename);
        }

        middleScrollPane.setContent(mainBudgetMiddleList);

        setContent(middleScrollPane);
        setClosable(false);
    }
}
