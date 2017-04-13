package data;

import user.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static util.SystemInfo.CURRENT_USER;
import static util.SystemInfo.USERS_PATH;

/**
 * Created by dcmeade on 4/10/2017.
 */
public class MainProgramDatastore
{
    // Create the instance of the object
    private static MainProgramDatastore ourInstance = new MainProgramDatastore();

    ///////////////////// PROGRAM DATA ////////////////////////

    private User loadedUser;

    ///////////////////////////////////////////////////////////

    private MainProgramDatastore()
    {
    }

    public static MainProgramDatastore getInstance()
    {
        return ourInstance;
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
}
