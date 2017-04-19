package data;

import com.opencsv.CSVReader;
import org.apache.log4j.Logger;
import user.User;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static util.SystemInfo.CURRENT_USER;
import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class MainProgramDatastore
{
    private static Logger logger = Logger.getLogger(MainProgramDatastore.class);

    // Create the instance of the object
    private static MainProgramDatastore ourInstance = new MainProgramDatastore();

    ///////////////////// PROGRAM DATA ////////////////////////

    private User loadedUser;

    private Date startDate = getInitialStartDate();
    private Date endDate = new Date();

    private int selectedMainTabIndex = 0;

    ///////////////////////////////////////////////////////////

    private MainProgramDatastore()
    {
    }

    public static MainProgramDatastore getInstance()
    {
        return ourInstance;
    }

    //////////////////////////////////////////////////////////


    public int getSelectedMainTabIndex()
    {
        return selectedMainTabIndex;
    }

    public void setSelectedMainTabIndex(int selectedMainTabIndex)
    {
        this.selectedMainTabIndex = selectedMainTabIndex;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    private static String[] getHeader(String filename)
    {
        File file = new File(USERS_PATH + "\\" + CURRENT_USER + "\\" + filename);

        try (CSVReader reader = new CSVReader(new FileReader(file), ',', '"', 0))
        {
            //Read all rows at once
            List<String[]> allRows = reader.readAll();

            return allRows.get(0);
        }
        catch (IOException e)
        {
            logger.warn("Could not open file: " + file.getAbsolutePath());
        }

        return null;
    }

    // TODO find better way to pass file
    // TODO make the first arg a HEADER field
    public static int getColumnIndex(String columnName, String filename)
    {
        String[] header = getHeader(filename);

        if (header != null)
        {
            for (int i = 0; i < header.length; i++)
            {
                if (header[i].equals(columnName))
                {
                    return i;
                }
            }
        }

        return -1;
    }

    public void loadCurrentUser()
    {
        loadedUser = new User(CURRENT_USER);
    }

    public User getLoadedUser()
    {
        return loadedUser;
    }

    // Takes Dir
    public List<String> getAllUserNames()
    {
        List<String> userNames = new ArrayList<>();

        // Get all file names under dir
        File folder = new File(USERS_PATH);
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;

        for (File listOfFile : listOfFiles)
        {
            if (listOfFile.isDirectory())
            {
                userNames.add(listOfFile.getName());
            }
        }

        return userNames;
    }

    private Date getInitialStartDate()
    {
        Calendar cal = Calendar.getInstance();

        // todo Eventually make this a config option
        cal.add(Calendar.MONTH, -1);

        return cal.getTime();
    }
}
