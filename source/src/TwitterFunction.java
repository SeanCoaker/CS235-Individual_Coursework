import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class handles sending a tweet to the game's twitter account.
 * Version History - version 1.0
 * Filename: TwitterFunction.java
 * @author Sean Coaker
 * @version 1.0
 * @since 25-04-2020
 * copyright: No Copyright Purpose
 */
public class TwitterFunction {
    private Twitter twitter;
    //An array string of possible endings to a tweet.
    private ArrayList<String> possibleTweets;

    /**
     * This adds possible endings to a tweet to the array list.
     */
    public TwitterFunction() {
        this.twitter = TwitterFactory.getSingleton();
        possibleTweets = new ArrayList<>();
        possibleTweets.add("You da man!... or Woman!");
        possibleTweets.add("What a performance!");
        possibleTweets.add("Unreal scenes!");
        possibleTweets.add("You love to see it!");
        possibleTweets.add("Not all heroes wear capes!");
    }

    /**
     * This method builds the content of tweet depending on if the user was fastest or top 3. It also picks a random
     * tweet ending string from the array list and appends it to the tweet.
     * @param username The username of the user that completed the level.
     * @param fastest Boolean stating if the user got the fastest time or not.
     * @param levelNo The level number of the level the user completed.
     * @param time The time the user took to complete the level.
     * @return The tweet to send.
     */
    public String setupTweet(String username, boolean fastest, int levelNo, int time) {
        int randomIndex = new Random().nextInt(possibleTweets.size() - 1);
        String content = "";
        if (fastest) {
            content = username + " just got a new best time on Level " + levelNo + " with a time of " + time +
                    " seconds! "  + possibleTweets.get(randomIndex);
        } else {
            content = username + " just got top 3 on Level " + levelNo + " with a time of " + time + " seconds! "
                    + possibleTweets.get(randomIndex);
        }
        return content;
    }

    /**
     * This methods sends the tweet from the game's twitter account.
     * @param content The content of the tweet.
     */
    public void sendTweet(String content) {
        try {
            Status status = twitter.updateStatus(content);
        } catch (TwitterException e) {
            System.out.println("Error! Sending tweet failed!");
        }
    }
}
