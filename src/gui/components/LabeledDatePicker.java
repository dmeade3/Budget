package gui.components;

import javafx.geometry.HPos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

/**
 * Created by dcmeade on 4/12/2017.
 */
public class LabeledDatePicker extends GridPane
{
    private DatePicker datePicker;

    public LabeledDatePicker(LocalDate pickerStart, String label)
    {
        datePicker = new DatePicker(pickerStart);

        setHgap(10);
        setVgap(10);

        Label checkInLabel = new Label(label);
        add(checkInLabel, 0, 0);

        GridPane.setHalignment(checkInLabel, HPos.LEFT);
        add(datePicker, 0, 1);
    }

    public DatePicker getDatePicker()
    {
        return datePicker;
    }

    public void setDateForPicker(LocalDate localDate)
    {
        this.datePicker = new DatePicker(localDate);
    }
}
