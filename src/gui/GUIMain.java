package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import static util.SystemInfo.*;


public class GUIMain extends Application
{
    private static final Logger logger = Logger.getLogger(GUIMain.class.getName());

    // Fields //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final Scene scene = new Scene(new BorderPane(), STARTING_SCENE_WIDTH, STARTING_SCENE_HEIGHT);
    private final BorderPane sceneRoot = (BorderPane) scene.getRoot();

    // Methods /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void start(Stage stage)
    {
        // Todo possibly move this code to MainPage

        logger.info("Starting: " + PROGRAM_NAME);

        ////// Main scene //////
        RootPage.showScene(stage, scene, sceneRoot);
    }

    // Start the whole application
    public static void main(String[] args)
    {
        BasicConfigurator.configure();

        Application.launch(GUIMain.class, args);
    }
}
