package data.csv_handling.transaction_handling;

import data.MainProgramDatastore;
import data.csv_handling.WellsFargoTransactionParser;
import gui.RootPage;
import javafx.scene.control.Alert;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcmeade on 4/17/2017.
 */
public class TransactionParser
{
    private static Logger logger = Logger.getLogger(TransactionParser.class);

    public static void parseTransactionFile(String filePath)
    {
        // TODO store the approved list of file endings somewhere
        // TODO if not on list popup error

        // TODO pass the filename to a transaction parser object
        // TODO call the transaction handler with the filepath
        // TODO Even if no error a popup should come up and confirm the transactions where successfully added
        //  -the handler based on user decisions should call a certain parser, ex: wells fargo: should be a gridpane with buttons

        List<String> fileEndings = new ArrayList<>();
        fileEndings.add(".csv");

        // Verifies file type
        boolean approved = false;
        for (String approvedEnding : fileEndings)
        {
            if (filePath.endsWith(approvedEnding))
            {
                approved = true;
            }
        }

        if (approved)
        {
            // TODO determine, will always return wells fargo until other banks programmed
            TransactionSource transactionSource = getTransactionSource();

            List<Transaction> inTransactions = new ArrayList<>();

            switch (transactionSource)
            {
                case WELLSFARGOCHECKING:
                    WellsFargoTransactionParser wellsFargoTransactionParser = new WellsFargoTransactionParser(filePath);

                    inTransactions = wellsFargoTransactionParser.parseTransactions();
                    break;

                default:
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Not an approved File Source");
                    alert.setHeaderText("");
                    alert.showAndWait();
            }

            List<Transaction> duplicateTransactions = new ArrayList<>();

            // Load the current user transactions
            MainProgramDatastore.getInstance().loadCurrentUser();

            // Transactions in the list under user are assumed to be right even if a duplicate
            for (Transaction inTransaction : inTransactions)
            {
                boolean found = false;

                for (Transaction confirmedTransaction : MainProgramDatastore.getInstance().getLoadedUser().getUnfilteredTransactions())
                {

                    // check if the new read in transaction is a duplicate of an existing transaction
                    if (confirmedTransaction.equals(inTransaction))
                    {
                        found = true;

                        duplicateTransactions.add(inTransaction);

                        break;
                    }
                }

                if (!found)
                {
                    inTransaction.writeOutTransaction();
                }
            }

            // TODO display duplicates and give the option to add them anyways
            for (Transaction transaction : duplicateTransactions)
            {
                logger.warn("Duplicate: " + transaction);
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not an approved File Type");
            alert.setHeaderText("");
            alert.showAndWait();
        }

        // Reload the center of the screen
        RootPage.reloadCenter(MainProgramDatastore.getInstance().getSelectedMainTabIndex());
    }

    private static TransactionSource getTransactionSource()
    {
        // TODO
        return TransactionSource.WELLSFARGOCHECKING;
    }
}
