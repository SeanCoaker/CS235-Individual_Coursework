import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * This class handles the login window. Where a user can login to play their saves or to create, edit and delete maps.
 * Version History - version 1.0
 * Filename: LoginController.java
 * @author Sean Coaker
 * @version 1.0
 * @since 25-04-2020
 * copyright: No Copyright Purpose
 */
public class LoginController {
    //the back button
    @FXML
    private Button backButton;

    //the create button for creating a profile
    @FXML
    private Button login;

    //the text field for the user to enter their name in
    @FXML
    private TextField userText;

    @FXML
    private TextField password;

    private static String username;
    private String user;
    private String passString;
    //Boolean to tell if user is trying to login from the create map function.
    private static boolean isCreate = false;

    /**
     * Initialises the login screen.
     */
    @FXML
    public void initialize() {
        login.setDisable(true);
    }

    /**
     * Registers and handles if the user presses the login or back button.
     * It also allows them to enter their name and password and if correct they can login.
     * @param actionEvent - the most recent action done by the user
     * @throws IOException
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
        } else if (actionEvent.getSource().equals(login)) {
            this.user = userText.getText();

            if (!UserDetailsHandler.existAccount(user)) {
                userText.clear();
                userText.setStyle("-fx-prompt-text-fill: RED");
                userText.setPromptText("This username does not exist !!!");
            } else {
                this.passString = password.getText();
                if (UserDetailsHandler.getPassword(user).equals(passString)) {
                    if (isCreate) {
                        username = user;
                        //Makes a custom map directory for the specified user.
                        File file = new File("GlobalFiles/CustomFiles/" + user);
                        if (!file.exists() || !file.isDirectory()) {
                            file.mkdir();
                        }
                        Node node = (Node) actionEvent.getSource();
                        Parent root = FXMLLoader.load(getClass().getResource("LevelEditorMenu.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        UserProfileController.setUsername(user);
                        Node node = (Node) actionEvent.getSource();
                        Parent root = FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    }
                } else {
                    password.clear();
                    password.setStyle("-fx-prompt-text-fill: RED");
                    password.setPromptText("The login credentials are incorrect !!!");
                }
            }
        }
    }

    /**
     * Disables the login button if a field is empty.
     */
    @FXML
    private void handleKeyReleased() {
        String userString = userText.getText();
        String passText = password.getText();
        boolean disabledButtons = userString.isEmpty() || userString.trim().isEmpty() || passText.isEmpty()
                || passText.trim().isEmpty();
        login.setDisable(disabledButtons);
    }

    /**
     * Returns the username of the user logged in.
     * @return Username of the user logged in.
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Sets if the user is trying to login from the create map screen.
     * @param value true if the user is trying to login from the create map screen and false otherwise.
     */
    public static void setIsCreate(boolean value) {
        isCreate = value;
    }

}
