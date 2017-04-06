package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.SystemInfo;

import java.io.File;

import static util.SystemInfo.*;


public class GUIMain extends Application
{
    // Fields //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final Scene scene = new Scene(new BorderPane(), STARTING_SCENE_WIDTH, STARTING_SCENE_HEIGHT);
    private final BorderPane sceneRoot = (BorderPane) scene.getRoot();

    // Methods /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void start(Stage stage)
    {
        System.out.println("Starting: " + PROGRAM_NAME);

        Label label1 = new Label("Top Label");
        Label label2 = new Label("Left Label");
        Label label3 = new Label("Right Label");
        Label label4 = new Label("Bottom Label");


        sceneRoot.setTop(label1);
        sceneRoot.setLeft(label2);
        sceneRoot.setRight(label3);
        sceneRoot.setBottom(label4);


        ////// Main scene //////
        showScene(stage);
        setUpSceneListeners();

    }

    private void showScene(Stage stage)
    {
        // Set up scene
        scene.getStylesheets().add(SystemInfo.MAIN_STYLE_SHEET_NAME);

        ///// Setup and Show Stage /////
        stage.setTitle(PROGRAM_NAME);
        stage.setScene(scene);
        stage.show();
    }

    private void setUpSceneListeners()
    {
        scene.getWindow().setOnCloseRequest(event ->
        {
            System.out.println("Closing");

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

    // Start the whole application
    public static void main(String[] args)
    {
        Application.launch(GUIMain.class, args);
    }
}
