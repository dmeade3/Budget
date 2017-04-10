package gui;

import dev_test_area.BudgetWithProgressBar;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import csv_handling.transaction_handling.Transaction;
import user.budget.Budget;
import util.SystemInfo;

import java.io.File;

import static util.SystemInfo.PROGRAM_NAME;

/**
 * Created by dcmeade on 4/7/2017.
 */
public class MainPage
{
    private static final Logger logger = Logger.getLogger(MainPage.class.getName());

    public static void showScene(Stage stage, Scene scene, BorderPane sceneRoot)
    {
        Label label1 = new Label("Top Label");
        //Label label2 = new Label("Left Label");
        Label label3 = new Label("Right Label");
        Label label4 = new Label("Bottom Label");


        sceneRoot.setTop(label1);

        sceneRoot.setLeft(MainPage.createLeftSideTree());

        sceneRoot.setRight(label3);
        sceneRoot.setBottom(label4);


	    sceneRoot.setCenter(getCenterTabPane());


        // Set up scene
        scene.getStylesheets().add(SystemInfo.MAIN_STYLE_SHEET_NAME);


        ///// Setup and Show Stage /////
        stage.setTitle(PROGRAM_NAME);
        stage.setScene(scene);
        stage.show();

        // Needs to be after set scene to compile correctly
        setUpSceneListeners(scene);
    }

	private static TabPane getCenterTabPane()
	{
		// make the middle stack pane // todo make a method / class to make this simpler
		// TODO this scroll pane should be in a tabpane, the tab pane should be its own object
		TabPane centerTabPane = new TabPane();

		centerTabPane.getTabs().add(createBugetTab());


		centerTabPane.getTabs().add(createTransactionTabTab());

		return centerTabPane;
	}

	// TODO Eventually make these objects
	private static Tab createTransactionTabTab()
	{
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);

		ListView<String> mainTransactionMiddleList = new ListView<>();

		mainTransactionMiddleList.getItems().add(new Transaction("4/29/1994", 999, "?", -1, "This is a description").toString());
		mainTransactionMiddleList.getItems().add(new Transaction("4/29/1994", 9999, "?", -1, "This is a description").toString());
		mainTransactionMiddleList.getItems().add(new Transaction("4/29/1994", 99, "?", -1, "This is a description").toString());
		mainTransactionMiddleList.getItems().add(new Transaction("4/29/1994", 999, "?", -1, "This is a description").toString());
		mainTransactionMiddleList.getItems().add(new Transaction("4/29/1994", 99999, "?", -1, "This is a description").toString());
		mainTransactionMiddleList.getItems().add(new Transaction("4/29/1994", 999, "?", -1, "This is a description").toString());
		mainTransactionMiddleList.getItems().add(new Transaction("4/29/1994", 99999, "?", -1, "This is a description").toString());
		mainTransactionMiddleList.getItems().add(new Transaction("4/29/1994", 9999, "?", -1, "This is a description").toString());
		mainTransactionMiddleList.getItems().add(new Transaction("4/29/1994", 997, "?", -1, "This is a description").toString());
		mainTransactionMiddleList.getItems().add(new Transaction("4/29/1994", 996, "?", -1, "This is a description").toString());
		mainTransactionMiddleList.getItems().add(new Transaction("4/29/1994", 995, "?", -1, "This is a description").toString());
		mainTransactionMiddleList.getItems().add(new Transaction("4/29/1994", 994, "?", -1, "This is a description").toString());



		scrollPane.setContent(mainTransactionMiddleList);


		Tab transactionTab = new Tab("Transactions");
		transactionTab.setClosable(false);
		transactionTab.setContent(scrollPane);

		return transactionTab;
	}

	// TODO Eventually make these objects
	private static Tab createBugetTab()
	{
		ScrollPane middleScrollPane = new ScrollPane();
		middleScrollPane.setFitToHeight(true);
		middleScrollPane.setFitToWidth(true);

		ListView<BudgetWithProgressBar> mainBudgetMiddleList = new ListView<>();

		mainBudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget 1",  100,  1100)));
		mainBudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget 2",  200,  1000)));
		mainBudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget 3",  300,  900)));
		mainBudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget 4",  400,  800)));
		mainBudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget 5",  500,  700)));
		mainBudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget 6",  600,  600)));
		mainBudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget 12", 700,  500)));
		mainBudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget 13", 800,  400)));
		mainBudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget 14", 900,  300)));
		mainBudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget 15", 1000, 200)));
		mainBudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget 16", 1100, 100)));

		middleScrollPane.setContent(mainBudgetMiddleList);


		Tab budgetTab = new Tab("Budget");
		budgetTab.setContent(middleScrollPane);
		budgetTab.setClosable(false);

		return budgetTab;
	}

    private static StackPane createLeftSideTree()
    {

        // TODO needs renaming of variables
        // TODO read in account from account file
        // TODO decide the format of stored info in program
        TreeItem<String> rootTreeItem = new TreeItem<> ("Accounts:");
        rootTreeItem.setExpanded(true);

        for (int i = 1; i < 6; i++)
        {
            TreeItem<String> item = new TreeItem<> ("Company: " + i);
            rootTreeItem.getChildren().add(item);

            for (int r = 1; r < 6; r++)
            {
                TreeItem<String> item2 = new TreeItem<> ("Account: #" + r);
                item.getChildren().add(item2);
            }
        }
        TreeView<String> tree = new TreeView<> (rootTreeItem);


        StackPane root = new StackPane();
        root.getChildren().addAll(tree);

        return root;
    }

    private static void setUpSceneListeners(Scene scene)
    {
        scene.getWindow().setOnCloseRequest(event ->
        {
            logger.info("Closing");
            Platform.exit();
        });

        scene.setOnDragOver(event ->
        {
            Dragboard db = event.getDragboard();

            if (db.hasFiles())
            {
                event.acceptTransferModes(TransferMode.COPY);
            } else
            {
                event.consume();
            }
        });

        // Dropping over surface
        scene.setOnDragDropped(event ->
        {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasFiles())
            {
                success = true;
                String filePath;

                for (File file:db.getFiles())
                {
                    filePath = file.getAbsolutePath();
                    System.out.println("Received: " +filePath);
                }
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }
}
