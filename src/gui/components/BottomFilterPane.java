package gui.components;

import data.MainProgramDatastore;
import gui.RootPage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import util.TimeRange;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by dcmeade on 4/12/2017.
 */
public class BottomFilterPane extends GridPane
{
    private LabeledDatePicker startLabeledDatePicker;
    private LabeledDatePicker endLabeledDatePicker;
    private LabeledComboBox timeRangeComboBox;

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

        startLabeledDatePicker = new LabeledDatePicker(new Date(MainProgramDatastore.getInstance().getStartDate().getTime()).toLocalDate(), "Start Date");
        add(startLabeledDatePicker, 4, 0);

        endLabeledDatePicker = new LabeledDatePicker(new Date(MainProgramDatastore.getInstance().getEndDate().getTime()).toLocalDate(), "End Date");
        add(endLabeledDatePicker, 6, 0);

        // Make the combobox
        ComboBox<TimeRange> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(TimeRange.values());

        timeRangeComboBox = new LabeledComboBox(comboBox, "Preset Range");
        add(timeRangeComboBox, 5, 0);

        addListenersCellFactories();
    }

    private void addListenersCellFactories()
    {
        timeRangeComboBox.getComboBox().valueProperty().addListener(event ->
        {
            TimeRange timeRange = (TimeRange) timeRangeComboBox.getComboBox().getSelectionModel().getSelectedItem();

            endLabeledDatePicker.getDatePicker().setValue(LocalDate.now());
            startLabeledDatePicker.getDatePicker().setValue(LocalDate.now().minusDays(timeRange.getDayCount()));

            Instant startInstant = Instant.from(startLabeledDatePicker.getDatePicker().getValue().atStartOfDay(ZoneId.systemDefault()));
            Instant endInstant = Instant.from(endLabeledDatePicker.getDatePicker().getValue().atStartOfDay(ZoneId.systemDefault()));

            MainProgramDatastore.getInstance().setStartDate(Date.from(startInstant));
            MainProgramDatastore.getInstance().setEndDate(java.util.Date.from(endInstant));

            RootPage.reloadAllButAdminAndBottom(MainProgramDatastore.getInstance().getSelectedMainTabIndex());
        });

        // TODO restrict the end date to be after the start date
        startLabeledDatePicker.getDatePicker().setDayCellFactory((p) -> new DateCell()
        {
            LocalDate maxDate;

            @Override
            public void updateItem(LocalDate ld, boolean bln)
            {
                super.updateItem(ld, bln);

                maxDate = endLabeledDatePicker.getDatePicker().getValue();

                setDisable(ld.isAfter(maxDate));


                Instant instant = Instant.from(startLabeledDatePicker.getDatePicker().getValue().atStartOfDay(ZoneId.systemDefault()));

                MainProgramDatastore.getInstance().setStartDate(Date.from(instant));

                RootPage.reloadCenter(MainProgramDatastore.getInstance().getSelectedMainTabIndex());
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


                Instant instant = Instant.from(endLabeledDatePicker.getDatePicker().getValue().atStartOfDay(ZoneId.systemDefault()));

                MainProgramDatastore.getInstance().setEndDate(Date.from(instant));

                RootPage.reloadCenter(MainProgramDatastore.getInstance().getSelectedMainTabIndex());
            }
        });
    }
}
