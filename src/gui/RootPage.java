package gui;

import data.MainProgramDatastore;
import data.csv_handling.transaction_handling.TransactionParser;
import gui.components.AccountTreeView;
import gui.components.AdminPane;
import gui.components.BottomFilterPane;
import gui.components.MainCenterTabPane;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import util.SystemInfo;

import java.io.File;

import static util.SystemInfo.PROGRAM_NAME;

/**
 * Created by dcmeade on 4/7/2017.
 */
public class RootPage
{
    private static final Logger logger = Logger.getLogger(RootPage.class.getName());

    private static BorderPane root;

    public static void showScene(Stage stage, Scene scene, BorderPane sceneRoot)
    {
        root = sceneRoot;

        loadSceneRoot();

        // Set up scene
        scene.getStylesheets().add(SystemInfo.MAIN_STYLE_SHEET_NAME);

        ///// Setup and Show Stage /////
        stage.setTitle(PROGRAM_NAME);
        stage.setScene(scene);
        stage.show();

        // Needs to be after set scene to compile correctly
        setUpSceneListeners(scene);
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
                    System.out.println("Received: " +filePath); // make logging

                    TransactionParser.parseTransactionFile(filePath);
                }
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }

    private static void loadSceneRoot()
    {
        root.setTop(new AdminPane());

        // Load the user after admin panel because loading the admin panel will pick the default/last user
        MainProgramDatastore.getInstance().loadCurrentUser();

        root.setLeft(new AccountTreeView());

        root.setCenter(new MainCenterTabPane());

        //root.setRight(label3);

        root.setBottom(new BottomFilterPane());
    }

    public static void reloadCenter(int tabIndexToSelect)
    {
        MainCenterTabPane mainCenterTabPane = new MainCenterTabPane();

        mainCenterTabPane.getSelectionModel().select(tabIndexToSelect);

        root.setCenter(mainCenterTabPane);
    }

    public static void reloadAll()
    {
        loadSceneRoot();
    }

    public static void reloadAllButAdmin()
    {
        root.setLeft(new AccountTreeView());
        root.setCenter(new MainCenterTabPane());
    }
}
