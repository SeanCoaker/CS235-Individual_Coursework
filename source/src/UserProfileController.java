
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 * Controller for selecting data from user save files.
 * Version History - version 1.0, 1.1, 1.2, 1.3
 * Filename: UserProfileController.java
 *
 * @author - Joseph Steven S, Marcin Kapcia, Chester D, Sean Coaker
 * @version - 1.3
 * @since 27-4-2020
 * copyright: No Copyright Purpose
 */
public class UserProfileController {

    //the home button
    @FXML
    private Button home;
    //shows the list of possible levels
    @FXML
    private ListView showListLevel;

    @FXML
    private ListView showCustomListLevel;

    private static String username;

    /**
     * Reads profiles into arrayList.
     */
    public void initialize() {
        showListLevel.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        showCustomListLevel.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        loadLists();
    }

    /**
     * Registers if the home, update or play button are pressed, and sets up the
     * appropriate scenes if so.
     *
     * @param e - the most recent action by the user
     * @throws IOException - if the Menu.fxml or LoadFile.fxml files are missing
     */
    public void onButtonClicked(ActionEvent e) throws IOException {
        if (e.getSource().equals(home)) {
            Node node = (Node) e.getSource();
            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }


    /**
     * Method that loads the lists with maps that the user is eligible to play
     */
    public void loadLists() {
        showCustomListLevel.getItems().clear();
        showListLevel.getItems().clear();

        File path = new File("GlobalFiles\\CustomFiles\\");
        File[] customLevelsFolders = path.listFiles();
        assert customLevelsFolders != null;

        for (File folder : customLevelsFolders) {
            File[] customLevels = folder.listFiles();
            for (File file : customLevels) {
                if (file.isFile()) {
                    showCustomListLevel.getItems().add(file.getName());
                }
            }
        }


        Profile profile = null;
        for (Profile p : FileReader.readProfiles()) {
            if (p.getUserName().equals(username)) {
                profile = p;
            }
        }
        // showListLevel.getItems().setAll(profile.getHighestLevel() + 1);
        for (int x = 1; x <= profile.getHighestLevel() + 1; x++) {

            showListLevel.getItems().add("level" + x);
        }


        File playerDir = new File("PlayerFolders/Player" + profile.getUserName());
        for (File save : playerDir.listFiles()) {
            showListLevel.getItems().add(save.getName());
        }


        GameController.setUserProfile(profile);


    }

    /**
     * Method that handles what happens when an item is selected from listView
     *
     * @param argo mouse event
     */
    public void mouseClickedLevel(MouseEvent argo) {
        String profileLevel = (String) (showListLevel.getSelectionModel().getSelectedItem());
        Node node = (Node) argo.getSource();
        MapController map = new MapController();
        Parent root = map.buildGUI();
        Scene scene = new Scene(root);
        if(profileLevel.contains("Save")) {
            GameController.loadFile("PlayerFolders/Player" + username + "/" + profileLevel);
            if (profileLevel.contains("Custom")) {
                GameController.setIsCustom(true);
            } else {
                GameController.setIsCustom(false);
            }
        } else {
            GameController.loadFile("GlobalFiles\\" + profileLevel + ".txt");

        }


        scene.addEventFilter(KeyEvent.KEY_PRESSED, map::processKeyEvent);
        map.drawGame();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that handles what happens when an item is selected from listView
     *
     * @param argo mouse event
     */
    public void mouseClickedCustomLevel(MouseEvent argo) {
        String profileLevel = (String) (showCustomListLevel.getSelectionModel().getSelectedItem());
        Node node = (Node) argo.getSource();
        MapController map = new MapController();
        Parent root = map.buildGUI();
        Scene scene = new Scene(root);
        GameController.loadCustomFile("GlobalFiles\\CustomFiles\\" + profileLevel);
        GameController.setIsCustom(true);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, map::processKeyEvent);
        map.drawGame();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the username variable to be the user parsed in. This is to keep to track of who is logged in.
     * @param user The username of the user logged in.
     */
    public static void setUsername(String user) {
        username = user;
    }


}
