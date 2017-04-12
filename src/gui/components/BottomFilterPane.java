package gui.components;

import javafx.scene.control.DateCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.time.LocalDate;

/**
 * Created by dcmeade on 4/12/2017.
 */
public class BottomFilterPane extends GridPane
{
    public BottomFilterPane()
    {
        // Rows
        int totalRows = 2;
        for (int i = 0; i < totalRows; i++)
        {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100 / totalRows);

            getRowConstraints().add(row);
        }

        // Columns
        int totalCols = 10;
        for (int i = 0; i < totalCols; i++)
        {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100 / totalCols);

            getColumnConstraints().add(col);
        }

        LabeledDatePicker startLabeledDatePicker = new LabeledDatePicker(null, "Start Date");
        add(startLabeledDatePicker, 4, 0);

        LabeledDatePicker endLabeledDatePicker = new LabeledDatePicker(LocalDate.now(), "End Date");
        add(endLabeledDatePicker, 6, 0);


        // TODO restrict the end date to be after the start date

        startLabeledDatePicker.getDatePicker().setDayCellFactory((p) -> new DateCell()
        {
            //LocalDate minDate = LocalDate.of(2014, Month.JANUARY, 1);

            LocalDate maxDate;

            @Override
            public void updateItem(LocalDate ld, boolean bln)
            {
                super.updateItem(ld, bln);

                maxDate = endLabeledDatePicker.getDatePicker().getValue();

                setDisable(ld.isAfter(maxDate));
            }
        });

        endLabeledDatePicker.getDatePicker().setDayCellFactory((p) -> new DateCell()
        {
            LocalDate minDate;

            @Override
            public void updateItem(LocalDate ld, boolean bln)
            {
                super.updateItem(ld, bln);

                if (startLabeledDatePicker.getDatePicker().getValue() == null)
                {
                    minDate = LocalDate.now().minusYears(1000);
                }
                else
                {
                    minDate = startLabeledDatePicker.getDatePicker().getValue();
                }

                setDisable(ld.isAfter(LocalDate.now()) || ld.isBefore(minDate));
            }
        });




        // TODO restrict start date to be before the end date


    }
}
