import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class handles sending a tweet when the user decides to share their time.
 * Version History - version 1.0
 * Filename: SendTweetController.java
 * @author Sean Coaker
 * @version 1.0
 * @since 25-04-2020
 * copyright: No Copyright Purpose
 */
public class SendTweetController implements Initializable {
    private static String titleString;
    private static String username;

    private static int levelNo;
    private static int time;

    private static boolean fastest;

    private TwitterFunction t;

    @FXML
    private TextArea tweetPreviewText;

    @FXML
    private Label titleLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button tweetButton;

    /**
     * Sets up a preview of the tweet before the user can see the window.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        tweetButton.setStyle("-fx-background-color: #00ACEE; -fx-background-radius: 15px");
        titleLabel.setText(titleString);
        titleLabel.setAlignment(Pos.CENTER);
        t = new TwitterFunction();
        tweetPreviewText.setText(t.setupTweet(username, fastest, levelNo, time));
        tweetPreviewText.setFont(Font.font("Segoe UI", 22));
        tweetPreviewText.setEditable(false);
        tweetPreviewText.setWrapText(true);
    }

    /**
     * This method adds data to the class so that the correct data can be shown in the tweet preview.
     * @param content Generic content of the tweet, e.g if the user was fastest or top 3.
     * @param user Username of the user who completed the level.
     * @param level The level the user completed.
     * @param isFastest A boolean value stating if the user got the fastest time or not.
     * @param seconds The time it took the user to complete the level.
     */
    public static void setupTweetPreview(String content, String user, int level, boolean isFastest, int seconds) {
        titleString = content;
        username = user;
        levelNo = level;
        fastest = isFastest;
        time = seconds;
    }

    /**
     * Handles what happens when a button is clicked in this scene.
     * @param e The button clicked.
     */
    @FXML
    public void onButtonClicked(ActionEvent e) {
        if (e.getSource().equals(cancelButton)) {
            Node node = (Node) e.getSource();
            Stage stage = (Stage) (node.getScene().getWindow());
            stage.close();
        } else if (e.getSource().equals(tweetButton)) {
            t.sendTweet(tweetPreviewText.getText());
            VictoryController.shared(true);
            Node node = (Node) e.getSource();
            Stage stage = (Stage) (node.getScene().getWindow());
            stage.close();
        }
    }
}
