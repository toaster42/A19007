import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Michael P. Troester
 * @version 1.01 - 10/07/2015
 * @studentid 5061001
 * @email michaelp.troester@gmail.com
 * @assignment.number A19007
 * @screenprint <a href='A19007.gif'>ScreenPrint</a>
 * @prgm.usage Called directly from OS
 * @see <a href='http://jcouture.net/cisc190/A19007.php' target='_blank'>Program Specification</a>
 * @see <br><a href='http://docs.oracle.com/javase/8/docs/technotes/guides/Javadoc/index.html'>Javadoc
 * Documentation</a><br>
 */
public class Stations {
    // Declare ArrayList Objects
    public ArrayList<String> staList = new ArrayList<>();
    public ArrayList <String> staIndex = new ArrayList<>();
    String strRecord;
    String strFilePath;
    int intCount;

    /**
     * The purpose of this constructor is to accept the winds aloft filename as an argument
     * and set it as a class variable before opening the file.  Then, if the file exists it reads the data into
     * two ArrayLists, staList for the data and staIndex for the Station IDs. Finally, it closes the input file.
     *
     * @param strInputFileName The Winds Aloft data file
     * @throws IOException Does nothing
     */
    public Stations(String strInputFileName) throws IOException{
        strFilePath=strInputFileName;

        // Open file for input (pg 248)
        File myFile = new File(strFilePath);
        if (myFile.exists())
        {
            // Yea! File Exists (pg 249)
            Scanner inputFile = new Scanner(myFile);
            // Initialize a Counter
            intCount = 0;
            while (inputFile.hasNext())
            {
                strRecord = inputFile.nextLine();

                if (intCount > 7) {
                    //write station weather to ArrayList
                    staList.add(strRecord);

                    //write StationID to Index Arraylist
                    staIndex.add(strRecord.substring(0, 3));

                }
                intCount++;
            }
            inputFile.close();
        }
    }

    /**The purpose of this method is to return the StationID at ArrayList staIndex's given ListPosition.
     *
     * @param intPosition the ordinal within ArrayList staIndex
     * @return strStationID - the 3 letter code for the airstation
     */
    public String getStationID(int intPosition) {
        String strStationID = staIndex.get(intPosition);
        return strStationID;
    }
    /**The purpose of this method is to return number of stations in ArrayList staIndex.
     *
     * @return staIndex.size()
     */
    public int length() {return staIndex.size();}

    /**The purpose of this method is to return true if the station exists in ArrayList staIndex.
     *
     * @param strStationID The 3 character Station ID
     * @return staIndex.contains(strStationID) True or False
     */
    public Boolean exists(String strStationID) { return staIndex.contains(strStationID); }

    /**The purpose of this method is to return the full station weather string from ArrayList staList. If it
     * does not exist it returns "Station Not Found."
     *
     * @param strStationID The 3 character Station ID
     * @return strStationData The Station Weather data
     */
    public String getStaWea(String strStationID) {
        String strStationData = null;
        int intPos;
        if (exists(strStationID)) {
            intPos = staIndex.indexOf(strStationID);
            strStationData = staList.get(intPos);
        }
        else {
            strStationData = "Station Not Found";
        }
        return strStationData;

    }
}
