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


/**
 * Created by dcmeade on 4/12/2017.
 */
public class TransactionTable extends TableView
{
    private static ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private TableColumn<Transaction, String> dateColumn = new TableColumn<>("Date");
    private TableColumn<Transaction, Double> amountColumn = new TableColumn<>("Amount");
    private TableColumn<Transaction, String> accountColumn = new TableColumn<>("Account");
    private TableColumn<Transaction, Integer> checkNumberColumn = new TableColumn<>("Check #");
    private TableColumn<Transaction, String> categoryColumn = new TableColumn<>("Category");
    private TableColumn<Transaction, String> descriptionColumn = new TableColumn<>("Description");

    public TransactionTable()
    {
        super(loadTransactions());

        initializeColumns();
        addListeners();
    }

    private static ObservableList<Transaction> loadTransactions()
    {
        transactions = FXCollections.observableArrayList(MainProgramDatastore.getInstance().getLoadedUser().getTransactionsFiltered());

        return transactions;
    }

    private void initializeColumns()
    {
        // Initialize the person table with the two columns.
        dateColumn.setCellValueFactory(       cellData -> new SimpleStringProperty(Transaction.dateFormat.format(cellData.getValue().getDate())));
        amountColumn.setCellValueFactory(     cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()).asObject());
        accountColumn.setCellValueFactory(    cellData -> new SimpleStringProperty(cellData.getValue().getAccountName()));
        checkNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCheckNumber()).asObject());
        categoryColumn.setCellValueFactory(   cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        dateColumn.setMinWidth(125);
        dateColumn.setMaxWidth(125);
        dateColumn.setPrefWidth(125);

        amountColumn.setMinWidth(100);
        amountColumn.setMaxWidth(150);
        amountColumn.setPrefWidth(100);

        accountColumn.setMinWidth(100);
        accountColumn.setMaxWidth(150);
        accountColumn.setPrefWidth(100);

        categoryColumn.setMinWidth(200);
        categoryColumn.setMaxWidth(200);
        categoryColumn.setPrefWidth(200);

        checkNumberColumn.setMinWidth(100);
        checkNumberColumn.setMaxWidth(100);
        checkNumberColumn.setPrefWidth(100);

        descriptionColumn.setMinWidth(100);
        descriptionColumn.setMaxWidth(2000);
        descriptionColumn.setPrefWidth(1000);


        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        getColumns().setAll(dateColumn, amountColumn, accountColumn, categoryColumn, checkNumberColumn, descriptionColumn);
    }

    private void addListeners()
    {
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            // Launch edit pane
            Stage stage = new Stage();
            Scene scene = new Scene(new TransactionEditor((Transaction) newValue, stage), 400, 300);

            stage.setScene(scene);
            stage.setTitle("Transaction Editor");
            stage.show();

            stage.setOnCloseRequest(event ->
            {
                MainProgramDatastore.getInstance().loadCurrentUser();

                RootPage.reloadCenter(MainProgramDatastore.getInstance().getSelectedMainTabIndex());
            });
        });
    }
}
