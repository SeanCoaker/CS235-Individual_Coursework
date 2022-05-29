import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * This class handles the Audio Settings window where users can adjust volume levels of the game's audio.
 * Version History - version 1.0
 * Filename: AudioSettingsController.java
 * @author Sean Coaker
 * @version 1.0
 * @since 25-04-2020
 * copyright: No Copyright Purpose
 */
public class AudioSettingsController implements Initializable {
    @FXML
    private Slider masterSlider;

    @FXML
    private Slider musicSlider;

    @FXML
    private Slider fxSlider;

    @FXML
    private Button backButton;
    //Used to scale volume from 0.0 - 1.0 to 0 - 100 to set the sliders value.
    private static final int SCALAR = 100;

    private static double masterScale;
    private static double musicScale;
    private static double fxScale;
    //Boolean to say if user is changing audio settings in game or in menu
    private static boolean inGame = false;

    /**
     * Sets up the window with the correct details before the user can see the window.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        //Sets up the sliders by converting values from audio settings file from 0 to 1 into 0 to 100
        masterSlider.setValue(masterScale * SCALAR);
        musicSlider.setValue(musicScale * SCALAR);
        fxSlider.setValue(fxScale * SCALAR);

        //These blocks of code control what happen when a slider is changed
        masterSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            masterScale = newValue.doubleValue() / 100;
            Main.setMusicVolume(musicScale * masterScale);
            GameController.setVictoryMusicVolume(musicScale * masterScale);
        });

        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            musicScale = newValue.doubleValue() / 100;
            GameController.setVictoryMusicVolume(musicScale * masterScale);
            Main.setMusicVolume(musicScale * masterScale);
        });

        fxSlider.setOnMouseReleased(event -> {
            fxScale = fxSlider.getValue() / 100;
            GameController.setSoundVolume(fxScale * masterScale);
            GameController.playDoorSound();
        });
    }

    /**
     * Gets the value of the master volume.
     * @return value of master volume.
     */
    public static double getMasterScale() {
        return masterScale;
    }

    /**
     * Gets the value of the music volume.
     * @return value of music volume.
     */
    public static double getMusicScale() {
        return musicScale;
    }

    /**
     * Gets the value of the sound effects volume.
     * @return value of sound effects volume.
     */
    public static double getFxScale() {
        return fxScale;
    }

    /**
     * This class reads volume levels from a file so that the user can keep their audio settings even if they close the
     * game.
     */
    public static void readFile() {
        Scanner in = null;
        File file = new File("GlobalFiles\\Audio\\audioSettings.txt");

        try {
            in = new Scanner(file);
            in.useDelimiter(",");
            masterScale = in.nextDouble();
            musicScale = in.nextDouble();
            fxScale = in.nextDouble();
        } catch (FileNotFoundException e) {
            System.out.println("Audio settings source file not found in game files: \n");
            System.out.println("Suggested fix - Re-download game files.");
        }
        in.close();
    }

    /**
     * This class writes new audio settings to a file used to store these settings.
     */
    public void writeToFile() {
        PrintWriter out = null;
        File file = new File("GlobalFiles\\Audio\\audioSettings.txt");

        try {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Couldn't create new audio settings file");
            }
            out = new PrintWriter(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println("Audio settings source file not found in game files: \n");
            System.out.println("Suggested fix - Re-download game files.");
        }

        out.print(masterSlider.getValue()/100 + "," + musicSlider.getValue()/100 + "," + fxSlider.getValue()/100 + ",");
        out.close();
    }

    /**
     * This class takes the user back to their running game or to the menu depending on where they clicked the audio
     * settings button.
     * @param actionEvent The button clicked
     * @throws IOException
     */
    public void onButtonClicked(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource().equals(backButton)) {
            writeToFile();
            Node node = (Node) actionEvent.getSource();
            if (inGame) {
                Stage stage = (Stage) (node.getScene().getWindow());
                stage.close();
                MapController.Timer.resumeTime();
                GameController.setTime(MapController.Timer.time);
                GameController.startTimer();
            } else {
                Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    /**
     * This class is used to set if the user is in game or not.
     * @param inGame true if in game, false if in menu.
     */
    public static void setInGame(boolean inGame) {
        AudioSettingsController.inGame = inGame;
    }
}
