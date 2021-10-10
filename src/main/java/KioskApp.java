import edu.mccneb.CLI;
import edu.mccneb.Tracks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KioskApp {

    public static String welcome = "\nWelcome to the Kiosk!";
    public static String lineSeparator = "-----------------------";
    public static CLI cli = new CLI();
    public static List<Tracks> trackList = new ArrayList<>();

    public static void main(String[] args) {

        try {
            KioskApp run = new KioskApp();
            run.app();
            System.out.println("\nGoodbye!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void app() {
        System.out.println(welcome);
        System.out.println(lineSeparator);

        try {
            String url = "jdbc:sqlite:C:\\Users\\admin\\Desktop\\mcc-code-school-repos\\chinook\\chinook.db";
            Connection conn = null;
            conn = DriverManager.getConnection(url);
            ResultSet rs = getAllTracksFromDB(conn);
            displayAllTracks(rs);

            boolean addAnotherTrack = true;
            while(addAnotherTrack) {
                ResultSet selectedTrack = selectTracks(conn);
                addTrackToPlaylist(selectedTrack);
                addAnotherTrack = cli.continueAddingTracks();
            }
            viewPlaylist();
            getInvoice();
        } catch (Exception e) {
            System.out.println("\nError");
            e.printStackTrace();
        }

    }

    public ResultSet getAllTracksFromDB(Connection conn) {
        try {
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM tracks");
            ResultSet rs = sql.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println("\nError selecting all tracks");
        }
        return null;
    }

    public void displayAllTracks(ResultSet rs) {
        try {
            while (rs.next()) {
                int trackid = rs.getInt("trackId");
                String name = rs.getString("name");
                String albumId = rs.getString("AlbumId");
                String mediaType = rs.getString("mediatypeid");
                String genreId = rs.getString("genreid");
                String composer = rs.getString("composer");
                String milliseconds = rs.getString("milliseconds");
                String bytes = rs.getString("bytes");
                double unitprice = rs.getDouble("unitprice");
                System.out.println(trackid + " " + name + " " + albumId + "" + mediaType + " " + genreId +
                        "\t" + composer + " " + milliseconds + " " + bytes + " " + unitprice);
            }
        } catch (SQLException e) {
            System.out.println("Error looping through results");
            e.printStackTrace();
        }
    }

    public void addTrackToPlaylist(ResultSet rs) {
        try{
            while(rs.next()) {
                int trackid = rs.getInt("trackId");
                String name = rs.getString("name");
                double unitPrice = rs.getDouble("unitprice");
                Tracks newTrack = new Tracks(trackid, name, unitPrice);
                trackList.add(newTrack);
            }
        }catch(SQLException e) {
            System.out.println("Error");
        }
    }


    public ResultSet selectTracks(Connection conn) {
        try {
            int trackIdFromUser = cli.getTrackFromUser();
            PreparedStatement selectTrack = conn.prepareStatement("SELECT * FROM tracks WHERE trackId = ?");
            selectTrack.setInt(1, trackIdFromUser);
            return selectTrack.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error getting track");
            return null;
        }
    }

    public void getInvoice() {
        double totalPrice = 0;
        for (Tracks tracks : trackList) {
            totalPrice += tracks.getUnitPrice();
        }
        totalPrice = Math.round(totalPrice * 100) / 100.0 ;
        System.out.println("\nThe total cost for your playlist is $" + totalPrice);
    }

    public void viewPlaylist() {
        System.out.println("\nYour Playlist");
        for(Tracks track : trackList) {
            System.out.println(track.getTrackId() + " " + track.getName() + " " + track.getUnitPrice());
        }
    }
}




