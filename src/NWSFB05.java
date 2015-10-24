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
public class NWSFB05 {
    private String strWeather;
    public NWSFB05(String strVar) {
        strWeather = strVar + " ";
    }


    /**
     * The purpose of this method is to return the
     * first three characters of the station weather.
     * @return strStatopmID - first three characters of station weather
     */
    public String getStationID() {
        String strStationID = strWeather.substring(0, 3);
        return strStationID;
    }

    /**The purpose of this method is to accept a string representing an altitude and return an integer representing
     * the starting character position of the altitude weather in the station weather string.
     * @param strAlt The specified altitude
     * @return intPos - the character position in the Station Data String
     */
    public static int getPos(String strAlt) {
        //utilize a switch structure to determine which altitude was sent. and where to set 'cursor' for string clipping
        int intPos = 0;

        switch (strAlt) {
            case "03000":   intPos = 4;
                break;
            case "06000":   intPos = 9;
                break;
            case "09000":   intPos = 17;
                break;
            case "12000":  intPos = 25;
                break;
            case "18000":  intPos = 33;
                break;
            case "24000":  intPos = 41;
                break;
            case "30000":  intPos = 49;
                break;
            case "34000":  intPos = 56;
                break;
            case "39000":  intPos = 63;
                break;
            default:    intPos = 0;
                break;
        }

        return intPos;
    }

    /**
     * The purpose of this method is to call getPos(strAlt) to determine where to begin extracting
     * weather data, then extracts the data from the string.
     * @param strAlt The specified altitude
     * @return strAltitudeWeather - The weather data for the requested altitude
     */
    public String getAltitudeWeather(String strAlt) {
        String strAltitudeWeather = "";
        int intPos=getPos(strAlt);

        if (intPos==4) {
            strAltitudeWeather = strWeather.substring(intPos, intPos+4) + "   ";
        }
        else if (intPos==0) {
            System.out.println("Invalid Altitude. Valid values:");
            System.out.println("3, 6, 9, 12, 18, 24, 30, 34, 39");
        }
        else {
            strAltitudeWeather = strWeather.substring(intPos, intPos + 7);
        }

        return strAltitudeWeather;
    }
    /**
     * The purpose of this method is to accept a two character string representing the altitude
     * and return the wind direction as a string.<br><br>
     * Exceptions:<br>
     *      1. If the first two characters are blanks, return "N/A"<br>
     *      2. If the first four characters of the altitude weather are "9900" then return "Calm"<br>
     *      3. If the first two characters of the Altitude Weather are greater than 36 then subtract 50.
     *          This happens when the wind is blowing over 100 knots and the NWS needed a condition to
     *          indicate that.  For example, 7715-12  would indicate that the wind is coming from 270
     *          degrees at 115 knots at -12C.  (77-50 = 27).  Because the two characters represent the
     *          wind direction, and something like 27 actually means 270 degrees, there is no such thing
     *          as 39 or 390 degrees.  Thus, the National Weather Service will use 01 through 36 and 51 through 86
     *          only, they do not code it with numbers that start with anything between 37 and 49 inclusive.<br><br>
     * Otherwise just return the first two characters with a "0" appended to it.
     * @param strAlt The two digit altitude (in thousands of feet)
     * @return strWindDirection - The formatted Wind Direction
     */
    public String getWindDirection(String strAlt) {
        String strAltitudeWeather = getAltitudeWeather(strAlt);
        //System.out.println("strAltitudeWeather = " + strAltitudeWeather); //Used in testing
        String strWindDirection;
        //System.out.println("strAltitudeWeather.substring(0,2) = " + strAltitudeWeather.substring(0,2)); //Used in testing
        if (strAltitudeWeather.substring(0,2).equals("  ")) {
            strWindDirection = "N/A        ";
        }
        else if (strAltitudeWeather.substring(0,4).equals("9900")) {
            strWindDirection = "Calm       ";
        }
        else if (Integer.parseInt(strAltitudeWeather.substring(0,2)) > 36) {
            int intWindDirection = Integer.parseInt(strAltitudeWeather.substring(0,2)) - 50;
            strWindDirection = String.valueOf(intWindDirection) + "0 degrees";
        }
        else {
            strWindDirection = strAltitudeWeather.substring(0,2) + "0 degrees";
        }
        return strWindDirection;
    }
    /**
     * The purpose of this method is to accept a two character string representing the
     * altitude in thousands and return the wind speed as a string.<br><br>
     * Exceptions:<br>
     *      If the third and fourth characters of the altitude weather are blanks, return "N/A"<br>
     *      If the first four characters of the altitude weather are "9900" then return "Calm"<br>
     *      If the first two characters of the Altitude Weather are greater then 36 then add 100 to returned speed.<br><br>
     *      Otherwise just return the second two characters of the altitude weather.<br>
     * @param strAlt The two digit altitude (in thousands of feet)
     * @return strWindSpeed - The formatted Wind Speed
     */
    public String getWindSpeed(String strAlt) {
        String strAltitudeWeather = getAltitudeWeather(strAlt);
        String strWindSpeed;

        if ((strAltitudeWeather.charAt(3) == ' ') || (strAltitudeWeather.charAt(4) == ' ')) {
            strWindSpeed = "N/A      ";
        }
        else if (strAltitudeWeather.substring(0,4).equals("9900")) {
            strWindSpeed = " Calm    ";
        }

        //Add 0 after speed
        else if (Integer.parseInt(strAltitudeWeather.substring(0,2)) > 36) {
            strWindSpeed = strAltitudeWeather.substring(2, 4) + "0 knots";
        }
        else {
            strWindSpeed = " " + strAltitudeWeather.substring(2, 4) + " knots";
        }
        return strWindSpeed;
    }

    /**
     * The purpose of this method is to accepts a two character string
     * representing the altitude and returns the wind temperature as a
     * string.<br><br>
     * If the fifth character is a blank, it returns "N/A."<br>
     * If the altitude is over 24000 feet, it adds a "-" as a prefix.<br><br>
     * Otherwise it just returns the last three characters.
     * @param strAlt The two digit altitude (in thousands of feet)
     * @return strWindTemp - The formatted Altitude Temperature
     */
    public String getWindTemp(String strAlt) {
        String strAltitudeWeather = getAltitudeWeather(strAlt);
        String strWindTemp;

        if (strAltitudeWeather.charAt(5) == ' ') {
            strWindTemp = " N/A";
        }
        else if (Integer.parseInt(strAlt) > 24000) {
            strWindTemp = "-" + strAltitudeWeather.substring(4, 6) + "C";
        }
        else {
            strWindTemp = strAltitudeWeather.substring(4, 7) + "C";
        }

        return strWindTemp;
    }

    /**
     * The purpose of this method is to return a formatted string of the Altitude Weather.<br><br>
     * Example output:<br>Station ID: SAN<br>
     The Altitude Weather for 3000 feet is: 3106<br>
     Wind Direction: 310 degrees<br>
     Wind Speed: 06 knots<br>
     Wind Temperature: n/a<br>
     * @param strAlt The two digit altitude (in thousands of feet)
     * @return strFmtWeatherReport - The formatted Altitude Weather
     */
    public String fmtWeatherReport(String strAlt) {
        return "Station ID: " + getStationID() + "\r\n" + "The Altitude Weather for " + strAlt +
                ",000 feet is: " + getAltitudeWeather(strAlt) + "\r\n" + "Wind Direction: " + getWindDirection(strAlt)
                + "\r\n" + "Wind Speed: " + getWindSpeed(strAlt) + "\r\n" + "Wind Temperature: " +
                getWindTemp(strAlt);
    }
}
