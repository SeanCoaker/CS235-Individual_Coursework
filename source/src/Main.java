import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

/**
 * This class creates the main window and runs the game.
 * Version History - version 1.0, version 1.1
 * Filename: Main.java
 * @author Joseph Semgalawe, Sean Coaker
 * @version 1.1
 * @since 25-4-2020
 * copyright: No Copyright Purpose
 */
public class Main extends Application {

    //Variable used to close stage at the end of the game i.e when ESC is pressed
    private static Stage mainStage;

    // Track - Dreams and Fantasies
    // Artist - Jay Man (Royalty Free Zone)
    // Music Link - https://www.youtube.com/watch?v=IrgXAFt1yJA
    // Music Provided By Royalty Free Zone - No Copyright Music
    private static Media backgroundMusic = new Media(new File("GlobalFiles\\Audio\\music.mp3").toURI().toString());
    private static MediaPlayer mediaPlayer = new MediaPlayer(backgroundMusic);

    /**
     * Starts up the main menu.
     * @param primaryStage - the primary stage to put the main menu up to
     * @throws Exception - an exception if Menu.fxml isn't found
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        AudioSettingsController.readFile();
        playMusic();

        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        primaryStage.setTitle("Window");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        setMainStage(primaryStage);
    }


    /**
     * Gets the main stage.
     * @return the main stage object
     */
    public static Stage getMainStage() {
        return mainStage;
    }


    /**
     * Sets the main stage to a new stage.
     * @param mainStage - the new mainStage object
     */
    public static void setMainStage(Stage mainStage) {
        Main.mainStage = mainStage;
    }

    /**
     * The main method that calls args.
     * @param args - the arguments passed through by the user
     */
    public static void main(String [] args){
        launch(args);
    }

    /**
     * This method sets the musics volume, its start and end times and makes sure that the music loops.
     */
    public void playMusic() {
        mediaPlayer.setVolume(0.1 * AudioSettingsController.getMusicScale() * AudioSettingsController.getMasterScale());
        mediaPlayer.setStartTime(Duration.seconds(15));
        mediaPlayer.setStopTime(Duration.seconds(160));
        mediaPlayer.play();

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
    }

    /**
     * Sets the music volume.
     * @param scaler The value the volume should be scaled by.
     */
    public static void setMusicVolume(double scaler) {
        mediaPlayer.setVolume(0.1 * scaler);
    }

    /**
     * Pauses the music.
     */
    public static void pauseMusic() {
        mediaPlayer.pause();
    }

    /**
     * Resumes the music.
     */
    public static void resumeMusic() {
        mediaPlayer.play();
    }
}
