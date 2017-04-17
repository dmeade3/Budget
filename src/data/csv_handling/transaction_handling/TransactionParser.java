package data.csv_handling.transaction_handling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by dcmeade on 4/17/2017.
 */
public class TransactionParser
{
    public static void parseTransactionFile(String filePath)
    {

        // TODO make sure fileending is on approved data list (csv for now)
        // TODO store the approved list somewhere
        // TODO if not on list popup error

        // TODO this code should be somewhere else, pass the filename to a transaction parser object
        // TODO call the transaction handler with the filepath
        // TODO Even if no error a popup should come up and confirm the transactions where successfully added
        //  -the handler based on user decisions should call a certain parser, ex: wells fargo: should be a gridpane with buttons


        if (filePath.endsWith(".csv"))
        {
            //read file into stream, try-with-resources
            try (Stream<String> stream = Files.lines(Paths.get(filePath)))
            {

                stream.forEach(System.out::println);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            // TODO popup error
        }
    }






}
