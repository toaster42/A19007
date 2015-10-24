import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

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
public class A19007 extends JDialog {
    // Declare Variables
    private JPanel contentPane;
    private JLabel lblTitle;
    private JComboBox cmbStationID;
    private JComboBox cmbAltitude;
    private JLabel lblWindDir;
    private JLabel lblWindSpeed;
    private JLabel lblWindTemp;

    static String[] aryAltitudes  = {"03", "06", "09", "12", "18", "24", "30", "34", "39"};
    static String strOutputFileName = "doc/FBOUT.txt";
    static String strInputFileName = "data/FBIN.txt";
    static int intLoadState = -1;



    /**
     * The purpose of this constructor is to set up the dialog box which serves as the main interface of this program.
     */
    public A19007() {
        setContentPane(contentPane);
        setModal(true);


        cmbStationID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (intLoadState == 1) {
                    //Create Stations object from input file name, catch possible IOException
                    Stations db = null;
                    try {
                        db = new Stations(strInputFileName);
                    } catch (IOException err) {
                        err.printStackTrace();
                    }

                    updateLabels(cmbStationID.getSelectedItem().toString(), cmbAltitude.getSelectedItem().toString(), db);
                }
            }
        });

        cmbAltitude.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (intLoadState == 1) {
                    //Create Stations object from input file name, catch possible IOException
                    Stations db = null;
                    try {
                        db = new Stations(strInputFileName);
                    } catch (IOException err) {
                        err.printStackTrace();
                    }
                    updateLabels(cmbStationID.getSelectedItem().toString(), cmbAltitude.getSelectedItem().toString(), db);
                }
            }
        });
    }


    /**
     * The purpose of this method is to update the JLables in the program dialog with the appropriate wind speed, temp,
     * and direction.
     * @param strStationID The three letter station id
     * @param strAltitude the altitude in feet
     */
    private void updateLabels(String strStationID, String strAltitude, Stations db) {
        //System.out.println("StationID: " + strStationID); //used in testing
        //System.out.println("Altitude: " + strAltitude);   //used in testing

        String strStationWeather = db.getStaWea(strStationID);
        //System.out.println("Station Weather: " + strStationWeather); // used in testing

        //Create NWSFB05 object from Station Weather string
        NWSFB05 fb = new NWSFB05(strStationWeather);

        //Update the labels.
        lblWindDir.setText(fb.getWindDirection(strAltitude));
        lblWindSpeed.setText(fb.getWindSpeed(strAltitude));
        lblWindTemp.setText(fb.getWindTemp(strAltitude));


    }

    /**
     * The purpose of this method is to serve as an entry point into this program and to load the dialog and create the
     * Stations object.
     * @param args - handles any potential arguments passed to this program.  Does nothing.
     */
    public static void main(String[] args) {

        A19007 dialog = new A19007();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //create Stations object to pass to loadAirports()
        Stations db = null;
        try {
            db = new Stations(strInputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


        dialog.pack();
        dialog.loadAirports(db);
        dialog.loadAltitudes(db);
        dialog.setTitle("A19007 - Michael P. Troester");
        dialog.setVisible(true);
        System.exit(0);
    }

    /**
     * The purpose of this method is to load the station IDs from the Stations object and populate the corresponding
     * JComboBox.
     * @param db the Stations object.
     */
    private void loadAirports(Stations db) {
        for (int i = 0; i < db.length(); i++) {
            cmbStationID.addItem(db.getStationID(i));
            //System.out.println(db.getStationID(i)); //for debugging purposes
        }
        intLoadState++;
        if (intLoadState==1)
            updateLabels(cmbStationID.getSelectedItem().toString(), cmbAltitude.getSelectedItem().toString(), db);

    }

    /**
     * The purpose of this method is to load the altitudes into the corresponding JComboBox, then update the output
     * JLables if both the altitude and stations have been loaded.
     */
    public void loadAltitudes(Stations db) {

        for (int x=0; x<=8; x++) {
            cmbAltitude.addItem(aryAltitudes[x]+"000");
            //System.out.println(aryAltitudes[x]+"000"); //For debugging purposes
        }
        intLoadState++;
        if (intLoadState==1)
            updateLabels(cmbStationID.getSelectedItem().toString(), cmbAltitude.getSelectedItem().toString(), db);
    }
}
