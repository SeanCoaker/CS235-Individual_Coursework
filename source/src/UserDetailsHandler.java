import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class handles the reading and writing of user details to the userdetails file.
 * Version History - version 1.0
 * Filename: UserDetailsHandler.java
 * @author Sean Coaker
 * @version 1.0
 * @since 25-04-2020
 * copyright: No Copyright Purpose
 */
public class UserDetailsHandler {
    private static String path = "GlobalFiles\\UserDetails.txt";

    private static File userDetailsFile = new File(path);

    private static Scanner in = null;

    /**
     * This method appends a new user (username and password) to the user details file.
     * @param username Username of the user to be added.
     * @param password Password of the user to be added.
     */
    public static void writeToFile(String username, String password) {
        PrintWriter out = null;

        try {
            out = new PrintWriter(new FileOutputStream(userDetailsFile, true));
        }
        catch (FileNotFoundException e) {
            System.out.println("User Details source file not found in game files: \n");
            System.out.println("Suggested fix - Re-download game files.");
            System.exit(0);
        }

        out.append(username + "," + password + "," + "\n");
        out.close();
    }

    /**
     * Checks to see if an account exists in the profile file
     * @param username The profile's username
     * @return Whether or not the account exists
     */
    public static boolean existAccount(String username) {
        setScanner();
        in.useDelimiter(",");

        while(in.hasNext()) {
            String fileUser = in.next();
            in.nextLine();
            if (username.equals(fileUser)) {
                return true;
            }
        }
        in.close();
        return false;
    }

    /**
     * Gets the password of a user
     * @param username Username bound to the profile
     * @return User's password
     */
    public static String getPassword(String username) {
        String password = null;
        setScanner();
        in.useDelimiter(",");

        while (in.hasNext()) {
            if (in.next().equals(username)) {
                password = in.next();
                return password;
            }
            in.nextLine();
        }
        in.close();
        return password;
    }

    /**
     * Sets up the scanner to be used throughout the class to read the user details file
     */
    private static void setScanner() {
        try {
            in = new Scanner(userDetailsFile);
        } catch (FileNotFoundException e) {
            System.out.println("User Details source file not found in game files: \n");
            System.out.println("Suggested fix - Re-download game files.");
        }
    }


}
