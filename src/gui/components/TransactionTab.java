package gui.components;

import data.csv_handling.transaction_handling.Transaction;
import data.MainProgramDatastore;
import gui.RootPage;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import static util.SystemInfo.CURRENT_USER;
import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class TransactionTab extends Tab
{
    private ScrollPane scrollPane = new ScrollPane();
    private ListView<String> mainTransactionMiddleList;

    public TransactionTab()
    {
        super("Transactions");

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        // Read in transactions
        MainProgramDatastore.getInstance().readInTransactions(USERS_PATH + "\\" + CURRENT_USER + "\\transactions");

        mainTransactionMiddleList = new ListView<>();

        // Add the header
        mainTransactionMiddleList.getItems().add(Transaction.header);

        for (Transaction transaction : MainProgramDatastore.getInstance().getTransactionsList())
        {
            mainTransactionMiddleList.getItems().add(transaction.guiViewString());
        }

        scrollPane.setContent(mainTransactionMiddleList);
        scrollPane.setStyle("-fx-font-size: 10pt;");

        setClosable(false);
        setContent(scrollPane);

        addListeners();
    }

    private void addListeners()
    {
        mainTransactionMiddleList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            // TODO
            // dont activate if its the header
            if (newValue.equals(Transaction.header))
            {
                return;
            }

            // Launch edit pane
            Stage stage = new Stage();
            Scene scene = new Scene(new TransactionEditor(newValue), 400, 300);

            stage.setScene(scene);
            stage.setTitle("Transaction Editor");
            stage.show();

            stage.setOnCloseRequest(event ->
            {
                RootPage.reloadCenter();
            });
        });
    }
}
