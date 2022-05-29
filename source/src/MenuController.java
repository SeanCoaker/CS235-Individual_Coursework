import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * This class handles actions for the buttons in the main menu scene.
 * Version History - version 1.0, version 1.1
 * Filename: MenuController.java
 *
 * @author - Joseph Steven S, Marcin Kapcia, Sean Coaker
 * @version - 1.1
 * @since 25-4-2020
 * copyright: No Copyright Purpose
 */
public class MenuController {
    @FXML
    Button startButton;

    @FXML
    Button load;

    @FXML
    Button leaderBoardButton;

    @FXML
    Button helpButton;

    @FXML
    Button createLevelButton;

    @FXML
    Button audioButton;

    /**
     * reads data from profiles and leaderboards.
     */
    public void initialize() {
        GameController.setAllProfiles();
        FileReader.setupFileNames();
        FileReader.readLeaderboard();
    }

    /**
     * Method that handles button clicks to load different scenes.
     *
     * @param e the button that was clicked.
     * @throws IOException handles IO exceptions.
     */
    @FXML
    public void onButtonClicked(ActionEvent e) throws IOException {
        if (e.getSource().equals(startButton)) {
            Node node = (Node) e.getSource();
            Parent root = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setTitle("Start");
            stage.setScene(new Scene(root));
            stage.show();
        } else if (e.getSource().equals(load)) {
            LoginController.setIsCreate(false);
            Node node = (Node) e.getSource();
            Parent root = FXMLLoader.load(getClass().getResource("LoadLogin.fxml"));
            Stage stage = (Stage) (node.getScene().getWindow());
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        } else if (e.getSource().equals(leaderBoardButton)) {
            Node node = (Node) e.getSource();
            Parent root = FXMLLoader.load(getClass().getResource("leaderboard.fxml"));
            Stage stage = (Stage) (node.getScene().getWindow());
            stage.setTitle("LeaderBoard");
            stage.setScene(new Scene(root));
            stage.show();
        } else if (e.getSource().equals(helpButton)) {
            Node node = (Node) e.getSource();
            Parent root = FXMLLoader.load(getClass().getResource("Info.fxml"));
            Stage stage = (Stage) (node.getScene().getWindow());
            stage.setTitle("Info");
            stage.setScene(new Scene(root));
            stage.show();
        } else if (e.getSource().equals(createLevelButton)) {
            LoginController.setIsCreate(true);
            Node node = (Node) e.getSource();
            Parent root = FXMLLoader.load(getClass().getResource("LoadLogin.fxml"));
            Stage stage = (Stage) (node.getScene().getWindow());
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        } else if (e.getSource().equals(audioButton)) {
            AudioSettingsController.setInGame(false);
            Node node = (Node) e.getSource();
            Parent root = FXMLLoader.load(getClass().getResource("AudioSettings.fxml"));
            Stage stage = (Stage) (node.getScene().getWindow());
            stage.setTitle("Audio Settings");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

}
