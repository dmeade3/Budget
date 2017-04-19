package gui.components;

import javafx.geometry.HPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Created by dcmeade on 4/19/2017.
 */
public class LabeledComboBox extends GridPane
{
    private ComboBox comboBox;

    public LabeledComboBox(ComboBox comboBox, String label)
    {
        this.comboBox = comboBox;

        setHgap(10);
        setVgap(10);

        Label checkInLabel = new Label(label);
        add(checkInLabel, 0, 0);

        GridPane.setHalignment(checkInLabel, HPos.LEFT);
        add(comboBox, 0, 1);
    }

    public ComboBox getComboBox()
    {
        return comboBox;
    }

    public void setComboBox(ComboBox comboBox)
    {
        this.comboBox = comboBox;
    }
}