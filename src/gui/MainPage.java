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
import user.budget.Budget;
import util.SystemInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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


		ScrollPane middleScrollPane = new ScrollPane();
		middleScrollPane.setFitToHeight(true);
		middleScrollPane.setFitToWidth(true);

		ListView<BudgetWithProgressBar> mainbudgetMiddleList = new ListView<>();

		mainbudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget",  100,  1100)));
		mainbudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget2", 200,  1000)));
		mainbudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget3", 300,  900)));
		mainbudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget4", 400,  800)));
		mainbudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget5", 500,  700)));
		mainbudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget6", 600,  600)));
		mainbudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget2", 700,  500)));
		mainbudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget3", 800,  400)));
		mainbudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget4", 900,  300)));
		mainbudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget5", 1000, 200)));
		mainbudgetMiddleList.getItems().add(new BudgetWithProgressBar(new Budget("Muh budget6", 1100, 100)));

		middleScrollPane.setContent(mainbudgetMiddleList);


		Tab budgetTab = new Tab("Budget");
		budgetTab.setContent(middleScrollPane);
		budgetTab.setClosable(false);

		centerTabPane.getTabs().add(budgetTab);

		return centerTabPane;
	}

    public static StackPane createLeftSideTree()
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
