import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Stores the top three user profiles for the time in each level
 * Version History - version 1.0, version 2.0
 * Filename: Leaderboard.java
 * @author Ben Parker, Sean Coaker
 * @version 2.0
 * @since 25-4-2020
 * copyright: No Copyright Purpose
 */

public class Leaderboard {

    //an array list holding the three(or less) profiles for a level
    private static ArrayList<Profile[]> levels = new ArrayList<>();
    //an array list holding the three(or less) times for a level
    private static ArrayList<int[]> levelTimes = new ArrayList<>();
    //an array of the names and times for a level
    private static Object [][] print;

    private static Object[][] correctOrder = new Object[3][2];

    /**
     * Writes the leaderboard data into the leaderboard file, in the appropriate format.
     */
    public static void writeUp() {
        try {
            BufferedWriter file = new BufferedWriter(new FileWriter("GlobalFiles/leaderboard.txt"));
            Profile[] level;
            for (int x = 0; x < levels.size(); x++) {
                file.write("\nLevel " + (x+1));
                level = levels.get(x);
                for (int y = 0; y < level.length; y++) {
                    if (level[y] != null) {
                        int time = levelTimes.get(x)[y];
                        file.write("\n" + time / 60 + ":" + Math.round(time % 60) + ":" + level[y].getUserName());
                    }
                }
            }
            file.close();
        } catch (IOException e) {
            System.out.println("The file 'leaderboard.txt' is missing");
        }
    }



    /**
     * Creates a new level in the array lists, adding the first entry and two empty values to each.
     * null is used for the levels, and -1 for the level times.
     * @param firstProfile - the profile of the first person to complete the level
     * @param time - the time the first person completed the level in
     */
    public static void addLevel(Profile firstProfile, int time) {
        levels.add(new Profile[]{firstProfile, null, null});
        levelTimes.add(new int[]{time, -1, -1});
    }


    /**
     * Adds a profile to the levels arrays in the appropriate position, under the assumption it should be added.
     * @param level - the level they completed
     * @param newProfile - the user profile to be added
     * @param time - the time it was completed in, in seconds
     */
    public static void addProfile(int level, Profile newProfile, int time) {
        try {
            Profile[] levelsUsers = levels.get(level - 1);
            int[] levelsTimes = levelTimes.get(level - 1);
            if (levelsUsers[2] == null) {
                addProfileToLevel(level, newProfile, time);
            } else {
                //swaps the appropriate profiles and times
                if (time < (int) correctOrder[0][1]) {
                    for (int y = 0; y < 3; y++) {
                        Profile temp = (Profile) correctOrder[0][0];
                        if (levelsUsers[y].getUserName().equals(temp.getUserName())) {
                            levelsUsers[y] = newProfile;
                            levelsTimes[y] = time;
                        }
                    }
                }
                //adds the adjusted arrays to their previous positions
                levels.set(level - 1, levelsUsers);
                levelTimes.set(level - 1, levelsTimes);
            }
        } catch (IndexOutOfBoundsException e) {
            addLevel(newProfile, time);
        }
    }

    /**
     * Sets a level up with the contents of the profiles and times.
     * If the level doesn't exist, it creates it.
     * @param level - the number of the level
     * @param users - an array of the users to add to the level
     * @param times - an array of times to add to the level
     */
    public static void setLevel(int level, Profile[] users, int[] times) {
        if (isLevel(level)) {
            levels.set(level-1, users);
            levelTimes.set(level-1, times);
        } else {
            addLevel(users[0], times[0]);
            if (users[1] != null) {
                addProfile(level, users[1], times[1]);
                if (users[2] != null) {
                    addProfile(level, users[2], times[2]);
                }
            }
        }
    }

    /**
     * Gets the top three user profiles and their times for a level.
     * @param level - the number of the level to get data from
     * @return - a 2d object array, where each array inside it holds a user's profile and their time
     */
    public static Object[][] getLevelDetails(int level) {
        Object[][] details = new Object[3][2];
        try{
            Profile[] profiles = levels.get(level-1);
            int[] times = levelTimes.get(level-1);
            for (int x = 0; x < 3; x++) {
                details[x][0] = profiles[x];
                details[x][1] = times[x];
            }
            setPrint(details);
            return details;

        }catch (IndexOutOfBoundsException ignored){

        }
        return null;
    }

    /**
     * Converts the contents of a level into a string.
     * @param levelNo - the level to make into a string
     * @return the top three times and the players usernames for the level
     */
    public static String toString(int levelNo) {
        Object[][] print = getLevelDetails(levelNo);
        Object[][] correctOrder = convertToCorrectOrder(print);
        String output = "";

        for(int y = 2; y >= 0; y--){
            if (correctOrder != null && correctOrder

                    [y][0] != null) {
                output += ((int) correctOrder[y][1]) / 60 + ":" + Math.round(((int) correctOrder[y][1]) % 60) + "     "
                        + ((Profile) correctOrder[y][0]).getUserName() +"\n";
            }
        }
        return output;
    }

    /**
     * Checks if a level is currently a level in the leaderboard
     * @param level - the level number to check
     * @return - a boolean value stating true if the level exists, and false otherwise
     */
    private static boolean isLevel(int level) {
        try {
            levels.get(level-1);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Sets the print data.
     * @param print - the new print 2D array
     */
    private static void setPrint(Object[][] print) {
        Leaderboard.print = print;
    }

    /**
     * Puts a profile into a levels save files for a level that doesn't have three completion times yet.
     * @param level - the level number to be added to
     * @param newProfile - the user profile that completed it
     * @param time - the time the user compeleted it in
     */
    private static void addProfileToLevel(int level, Profile newProfile, int time) {
        try {
            Profile[] users = levels.get(level - 1);
            int[] times = levelTimes.get(level - 1);
            //gets the position to edit by testing if there is a profile in the index 1
            int posToEdit = 1;
            if (users[1] != null) {
                posToEdit++;
            }

            users[posToEdit] = newProfile;
            times[posToEdit] = time;
            levels.set(level-1, users);
            levelTimes.set(level-1, times);
        } catch(IndexOutOfBoundsException e) {
            addLevel(newProfile, time);
        }
    }

    /**
     * A method which converts the list of users and their times into the correct order.
     * @param print The 2D array of users and their times.
     * @return A 2D array of users and their times but in the correct order.
     */
    public static Object[][] convertToCorrectOrder(Object[][] print) {
        for (int y = 0; y < 3; y++) {
            correctOrder[y][1] = -1;
        }
        try {
            int counter = 0;
            while(counter < 3) {
                int maximum = -1;
                Profile bestProfile = null;
                int index = 0;
                for (int y = 0; y < 3; y++) {
                    if (print[y] != null && (int) print[y][1] > maximum) {
                        maximum = (int) print[y][1];
                        bestProfile = (Profile) print[y][0];
                        index = y;
                    }
                }
                correctOrder[counter][0] = bestProfile;
                correctOrder[counter][1] = maximum;
                print[index] = null;
                counter++;
            }
            return correctOrder;
        } catch (NullPointerException e) {
            return correctOrder;
        }
    }

}