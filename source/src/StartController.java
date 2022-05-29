import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This class provides the visuals and buttons of the main menu.
 * Filename: StartController.java
 * @author - Joseph Steven S, Marcin Kapcia
 * @version - 1.0
 * copyright: No Copyright Purpose
 */

public class StartController {
    //the leaderboard menu
    @FXML
    private MenuItem load;

    //the back button
    @FXML
    private Button backButton;

    //the create button for creating a profile
    @FXML
    private Button create;

    //the text field for the user to enter their name in
    @FXML
    private TextField user;

    //the username that the user enters
    private String username;

    private String passString;

    @FXML
    private MenuItem exit;
    //Text field to enter password
    @FXML
    private TextField password;
    //Text field to confirm password
    @FXML
    private TextField conPassword;

    /**
     * Initialises the start screen.
     */
    @FXML
    public void initialize() {
        create.setDisable(true);
    }

    /**
     * Registers and handles if the user presses the create or back button.
     * It also allows them to enter their name, and starts the game if its unique, or informs them otherwise. It then
     * checks the password and confirm password text fields and if they are the same then they can proceed. Otherwise
     * they are informed.
     * @param actionEvent - the most recent action done by the user
     * @throws IOException - if the file Menu.fxml is missing
     */
    @FXML
    private void onButtonClicked2(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource().equals(backButton)) {
            Node node = (Node) actionEvent.getSource();
            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (actionEvent.getSource().equals(create)) {
            Node node = (Node) actionEvent.getSource();
            MapController map = new MapController();
            Parent root = map.buildGUI();
            Scene scene = new Scene(root);

            this.username = user.getText();

            if (GameController.getUsername().contains(username)) {
                user.clear();
                user.setStyle("-fx-prompt-text-fill: RED");
                user.setPromptText("User already taken !!!");
            } else if (!conPassword.getText().equals(password.getText())) {
                conPassword.clear();
                conPassword.setStyle("-fx-prompt-text-fill: RED");
                conPassword.setPromptText("Password and Confirm Password Do Not Match !!!");
            } else {
                this.passString = password.getText();
                GameController.startGame(new Profile(username, 1));
                scene.addEventFilter(KeyEvent.KEY_PRESSED, map::processKeyEvent);
                UserDetailsHandler.writeToFile(username, passString);
                map.drawGame();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    /**
     * Disables the user text field if something has been entered.
     */
    @FXML
    private void handleKeyReleased() {
        String userText = user.getText();
        String passText = password.getText();
        String conPassText = conPassword.getText();
        boolean disabledButtons = userText.isEmpty() || userText.trim().isEmpty() || passText.isEmpty()
                || passText.trim().isEmpty() || conPassText.isEmpty() || conPassText.trim().isEmpty();
        create.setDisable(disabledButtons);
    }

    /**
     * If the user presses the leader_board button, it takes them to the leaderboard menu.
     * @param actionEvent - the most recent action done by the user
     * @throws IOException - if the file UserProfile.fxml can't be found
     */
    @FXML
    private void clickMenu(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource().equals(load)) {
            Parent root = FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
            Scene scene = new Scene(root);
            Stage stage = Main.getMainStage();
            stage.setScene(scene);
            stage.show();
        } else if(actionEvent.getSource().equals(exit)){
           Main.getMainStage().close();
        }
    }
}