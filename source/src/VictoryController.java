import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class handles the victory screen which is shown when a user gets a top 3 time in a level.
 * Version History - version 1.0
 * Filename: VictoryController.java
 * @author Sean Coaker
 * @version 1.0
 * @since 25-04-2020
 * copyright: No Copyright Purpose
 */
public class VictoryController implements Initializable {
    @FXML
    private Button nextButton;

    @FXML
    private Button shareButton;
    //Shows if the user was top 3 or the fastest
    @FXML
    private Label rankLabel;

    @FXML
    private Label timeLabel;

    private static String username;

    private static int level;
    private static int time;
    //Boolean to tell if the user was fastest.
    private static boolean fastest;
    //Boolean to tell if the user was top 3 but not fastest.
    private static boolean top3;
    //Boolean to tell if the user has shared the time or not.
    private static boolean shared = false;

    /**
     * This method assigns the correct values from the completed level to the variables in this class.
     * @param user The username of the user that completed the level.
     * @param levelNo The level number of the level completed.
     * @param seconds The time taken to complete the level.
     * @param isFastest A boolean to tell if the user got the fastest time or not.
     * @param isTop3 A boolean to say if the user got top 3 but not the fastest time.
     */
    public static void setupWindow(String user, int levelNo, int seconds, boolean isFastest, boolean isTop3) {
        username = user;
        level = levelNo;
        time = seconds;
        fastest = isFastest;
        top3 = isTop3;
    }

    /**
     * Sets up the window with the correct info such as telling the user if they had the fastest time or a top 3 time
     * whilst also displaying their time.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        if (top3) {
            if (fastest) {
                rankLabel.setText("1st Place:");
            } else {
                rankLabel.setText("Top 3:");
            }
        } else {
            rankLabel.setText("");
            shareButton.setDisable(true);
        }

        timeLabel.setText(time + " seconds");
    }

    /**
     * A method for handling what happens when certain buttons in the scene are clicked.
     * @param e The button clicked.
     */
    public void onButtonClicked(ActionEvent e) {
        try {
            if (e.getSource().equals(nextButton)) {
                Node node = (Node) e.getSource();
                Stage stage = (Stage) (node.getScene().getWindow());
                stage.close();
            } else if (e.getSource().equals(shareButton)) {
                Node node = (Node) e.getSource();
                Stage thisStage = (Stage) (node.getScene().getWindow());
                //Shows different content depending on if the user set the fastest time or not
                if (fastest) {
                    SendTweetController.setupTweetPreview("You got the fastest time! Would you like to share it?",
                            username, level, true, time);
                    Parent root = FXMLLoader.load(getClass().getResource("SendTweet.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Send Tweet");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                } else {
                    SendTweetController.setupTweetPreview("You got a top 3 time! Would you like to share it?",
                            username, level, false, time);
                    Parent root = FXMLLoader.load(getClass().getResource("SendTweet.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Send Tweet");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                }

                if (shared) {
                    shareButton.setDisable(true);
                }
                thisStage.setIconified(false);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * A method to set the boolean shared.
     * @param isShared If the user has shared their time then true and false otherwise.
     */
    public static void shared(boolean isShared) {
        shared = isShared;
    }
}
