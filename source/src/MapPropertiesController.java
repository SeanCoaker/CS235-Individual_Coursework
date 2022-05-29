/**
 * @author Sean Coaker,
 */

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

/**
 * This class handles the map properties window where the user can select the size and name for the map they want to create.
 * Version History - version 1.0, version 1.1
 * Filename: MapPropertiesController..java
 * @author Sean Coaker, Marcin Kapcia
 * @version 1.1
 * @since 25-04-2020
 * copyright: No Copyright Purpose
 */
public class MapPropertiesController {

    @FXML
    private TextField wSize;

    @FXML
    private TextField title;

    @FXML
    private Button back;


    /**
     * Handles the error checks for when the ok button is clicked. If another user has a map with the same name already
     * as the name just entered by the user, then the code adds a "_username" to the name of the new map. If the new name
     * for the map is already used by this user on another map then an error is flagged.
     * @param event The button pressed.
     */
    @FXML
    private void okButton(ActionEvent event) {
        try {
            File dir = new File("GlobalFiles/CustomFiles/");
            File[] folders = dir.listFiles();
            boolean duplicate = false;

            for (File folder : folders) {
                File[] files = folder.listFiles();
                for (File elem : files) {
                    if (elem.getName().equalsIgnoreCase(title.getText() + ".txt")) {
                        duplicate = true;
                    }
                }
            }

            boolean secondDuplicate = false;


            if (duplicate) {
                File userDir = new File("GlobalFiles/CustomFiles/" + LoginController.getUsername() + "/");
                File[] files = userDir.listFiles();
                for (File elem : files) {
                    if (elem.getName().equalsIgnoreCase(title.getText() + ".txt") ||
                            elem.getName().equalsIgnoreCase(title.getText() + "_"
                                    + LoginController.getUsername() + ".txt")) {
                        secondDuplicate = true;
                    }
                }

                if (secondDuplicate) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("A file already exists with this name.");
                    a.show();
                    return;
                } else {
                    title.setText(title.getText() + "_" + LoginController.getUsername());
                }
            }

            if (title.getText().equals("")) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Please enter a file name for your custom level.");
                a.show();
            } else {
                LevelEditorController.setTitle(title.getText());

                if (wSize.getText().isEmpty()) {
                    LevelEditorController.setWidth(10);
                    LevelEditorController.setHeight(10);
                } else {
                    LevelEditorController.setWidth(Integer.parseInt(wSize.getText()));
                    LevelEditorController.setHeight(Integer.parseInt(wSize.getText()));
                }

                Node node = (Node) event.getSource();
                Parent root = FXMLLoader.load(getClass().getResource("LevelEditor.fxml"));
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(new Scene(root, 1000, 700));
                stage.show();
            }
        } catch (IOException ex) {
            Logger.getLogger(LevelEditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles going back to the level editor menu when the back button is clicked.
     * @param actionEvent The button clicked.
     * @throws IOException
     */
    public void onButtonClicked(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource().equals(back)) {
            Node node = (Node) actionEvent.getSource();
            Parent root = FXMLLoader.load(getClass().getResource("LevelEditorMenu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
