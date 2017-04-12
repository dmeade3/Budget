package gui.components;

import data.MainProgramDatastore;
import data.csv_handling.transaction_handling.Transaction;
import gui.RootPage;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import static util.SystemInfo.CURRENT_USER;
import static util.SystemInfo.USERS_PATH;


/**
 * Created by dcmeade on 4/12/2017.
 */
public class TransactionTable extends TableView
{
    private static ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private TableColumn<Transaction, String> dateColumn = new TableColumn<>("Date");
    private TableColumn<Transaction, Double> amountColumn = new TableColumn<>("Amount");
    private TableColumn<Transaction, String> mysteryColumn = new TableColumn<>("Mystery");
    private TableColumn<Transaction, Integer> checkNumberColumn = new TableColumn<>("Check #");
    private TableColumn<Transaction, String> descriptionColumn = new TableColumn<>("Description");

    public TransactionTable()
    {
        super(loadTransactions());

        initializeColumns();
        addListeners();
    }

    private static ObservableList<Transaction> loadTransactions()
    {
        MainProgramDatastore.getInstance().readInTransactions(USERS_PATH + "\\" + CURRENT_USER + "\\transactions.csv");

        for (Transaction transaction: MainProgramDatastore.getInstance().getTransactionsList())
        {
            transactions.add(transaction);
        }

        return transactions;
    }

    private void initializeColumns()
    {
        // Initialize the person table with the two columns.
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        amountColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()).asObject());
        mysteryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMystery()));
        checkNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCheckNumber()).asObject());
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        dateColumn.setMinWidth(125);
        dateColumn.setMaxWidth(125);

        amountColumn.setMinWidth(100);
        amountColumn.setMaxWidth(100);

        mysteryColumn.setMinWidth(100);
        mysteryColumn.setMaxWidth(100);

        checkNumberColumn.setMinWidth(100);
        checkNumberColumn.setMaxWidth(100);

        descriptionColumn.setMinWidth(100);
        descriptionColumn.setMaxWidth(2000);
        descriptionColumn.setPrefWidth(1000);

        getColumns().setAll(dateColumn, amountColumn, mysteryColumn, checkNumberColumn, descriptionColumn);
    }


    private void addListeners()
    {
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            // Launch edit pane
            Stage stage = new Stage();
            Scene scene = new Scene(new TransactionEditor((Transaction) newValue), 400, 300);

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
