package util.test_area;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;

public class Test extends Application
{
	private Stage stage;
	private DatePicker checkInDatePicker;

	private void initUI()
	{
		VBox vbox = new VBox(20);
		vbox.setStyle("-fx-padding: 10;");
		Scene scene = new Scene(vbox, 400, 400);
		stage.setScene(scene);

		checkInDatePicker = new DatePicker();

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		Label checkInLabel = new Label("Check-In Date:");
		gridPane.add(checkInLabel, 0, 0);

		GridPane.setHalignment(checkInLabel, HPos.LEFT);
		gridPane.add(checkInDatePicker, 0, 1);
		vbox.getChildren().add(gridPane);
	}

	@Override
	public void start(Stage stage)
	{
		this.stage = stage;
		stage.setTitle("DatePickerSample ");
		initUI();
		stage.show();
	}

	public static void main(String[] args)
	{
		Locale.setDefault(Locale.US);
		launch(args);
	}
}