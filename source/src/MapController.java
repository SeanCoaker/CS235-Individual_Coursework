import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * This draws out the game's map and surrounding GUI
 * Version History - version 1.0, version 1.1
 * Filename: MapController.java
 * @author Chester Descallar, Petko Kuzmanov, Joseph Sembagalawe, Sean Coaker
 * @version 1.1
 * @since 25-4-2020
 * copyright: No Copyright Purpose
 */
public class MapController {
    // The size of the canvas
    private static final int CANVAS_WIDTH = 700;
    private static final int CANVAS_HEIGHT = 700;

    //The distances to the centre of the screen
    private static int distanceToCenterX;
    private static int distanceToCenterY;

    //the distance the furthest visible cells should be from the player
    private static final int PLAYER_DISTANCE = 5;

    // The canvas to display all the visuals through
    private Canvas canvas;

    //The root pane for the visuals
    @FXML
    Pane root;

    /**
     * Process a key event for movements or the escape button. It then draws out the map and the inventory contents.
     * It also calls the method so that all enemies move.
     * @param event The key that was pressed.
     */
    public void processKeyEvent(KeyEvent event) {

        try{
            switch (event.getCode()){
                case RIGHT:
                    GameController.nextMove(GameController.getPlayer().checkNextObject(event));
                    distanceToCenterX++;
                    break;
                case LEFT:
                    GameController.nextMove(GameController.getPlayer().checkNextObject(event));
                    distanceToCenterX--;
                    break;
                case UP:
                    GameController.nextMove(GameController.getPlayer().checkNextObject(event));
                    distanceToCenterY++;
                    break;
                case DOWN:
                    GameController.nextMove(GameController.getPlayer().checkNextObject(event));
                    distanceToCenterY--;
                    break;
                case ESCAPE:
                    //save and exit the game
                    GameController.saveGame();
                    Main.getMainStage().close();
                    break;
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        HBox hbox = (HBox) (root.getChildren().get(1));

        //Shows the amount of tokens the player has
        Label tokenLabel = (Label) (hbox.getChildren().get(1));
        tokenLabel.setText("Tokens: " + (Integer.toString(GameController.getPlayer().countTokens())));

        //Shows the contents of the inventory in a text box
        Label inventoryLabel = (Label) (hbox.getChildren().get(2));
        inventoryLabel.setText("Inventory: " + GameController.getPlayer().getPlayerInventoryWithoutTokens());

        //move all the enemies
        GameController.moveEnemies();
        // Redraw game as the player may have moved, and consume the event.
        drawGame();
        event.consume();
    }

    /**
     * Draw the game on the canvas.
     */
    public void drawGame() {
        // Get the Graphic Context of the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Sets the size of each cell
        int GRID_CELL_WIDTH = 64;
        distanceToCenterX = ((CANVAS_WIDTH / GRID_CELL_WIDTH) / 2 - GameController.getPlayer().getXPos());
        int GRID_CELL_HEIGHT = 64;
        distanceToCenterY = ((CANVAS_HEIGHT / GRID_CELL_HEIGHT) / 2 - GameController.getPlayer().getYPos());

        // Clears the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //for every cell within PLAYER_DISTANCE squares of the player
        for (int y = GameController.getPlayer().yPos - PLAYER_DISTANCE; y <= (GameController.getPlayer().yPos + PLAYER_DISTANCE); y++) {
            for (int x = GameController.getPlayer().xPos - PLAYER_DISTANCE; x <= (GameController.getPlayer().xPos + PLAYER_DISTANCE); x++) {
                try {
                    //if its a valid object inside the map, draw it
                    int[] location = {x, y};
                    Object newObject = GameController.getObject(location);
                    //System.out.println(((GameObject) newObject).getImage());
                    gc.drawImage(new Image(((GameObject) newObject).getImage()), (x + distanceToCenterX) * GRID_CELL_WIDTH,
                            (y + distanceToCenterY) * GRID_CELL_HEIGHT);
                } catch (ArrayIndexOutOfBoundsException E) {
                    //otherwise its outside the map, so draw a wall there
                    gc.drawImage(new Image("Images/environment/wall.png"), (x + distanceToCenterX) * GRID_CELL_WIDTH,
                            (y + distanceToCenterY) * GRID_CELL_HEIGHT);
                }
            }
        }
    }


    /**
     * Create the GUI.
     * @return The panel holding the GUI
     */
    public Pane buildGUI() {
        // Create top-level panel that will hold all GUI
        BorderPane root = new BorderPane();

        // Create canvas
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);

        // Create a top 'toolbar' space for the inventory and tokens
        HBox toolbar = new HBox();
        toolbar.setSpacing(10);
        toolbar.setPadding(new Insets(10, 10, 10, 10));
        root.setTop(toolbar);

        //show a save button
        Button save = new Button("Save");
        Button audio = new Button("Audio");
        Button back = new Button("Back");
        toolbar.getChildren().add(save);

        //if they press the save button, try and save the game. if it fails, restart the level
        save.setOnAction(e -> {
            try {
                GameController.saveGame();

            } catch (IOException ex) {
                ex.printStackTrace();
                GameController.initiateLevel(GameController.getLevel());
            }
        });

        audio.setOnAction(e -> {
            try {
                GameController.pauseTimer();
                Timer.pauseTime();
                AudioSettingsController.setInGame(true);
                Parent newRoot = FXMLLoader.load(getClass().getResource("AudioSettings.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Audio Settings");
                stage.setScene(new Scene(newRoot));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        back.setOnAction(e -> {
            try {
                Timer.resetTime();
                GameController.setTime(0);
                GameController.pauseTimer();
                Node node = (Node) e.getSource();
                Parent parent = FXMLLoader.load(getClass().getResource("Menu.fxml"));
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setTitle("Menu");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        //Puts a label of how many tokens they have, their inventory contents and their time onto the toolbar
        Label tokens = new Label("Tokens: " + Integer.toString(0));
        toolbar.getChildren().add(tokens);
        Label inventory = new Label("Inventory: ");
        toolbar.getChildren().add(inventory);
        Label time = new Label("Time Spent: ");
        toolbar.getChildren().add(time);
        Timer timer = new Timer();
        toolbar.getChildren().add(timer);
        toolbar.getChildren().add(audio);
        toolbar.getChildren().add(back);

        this.root = root;
        return root;
    }

    /**
     * This inner class acts as a timer to keep track of how long they've been playing for at once.
     */
    public static class Timer extends Pane {
        //the animation to show the time changing
        private static Timeline animation;
        //the time in seconds
        public static int time = 0;
        //the label showing the time, which is initially 0
        public static Label result = new Label("0");

        /**
         * This creates a smooth way to play the animation of time changing indefinitely.
         */
        private Timer() {
            getChildren().add(result);
            animation = new Timeline(new KeyFrame(Duration.seconds(1), e -> time()));
            ((Animation) animation).setCycleCount(Timeline.INDEFINITE);
        }

        /**
         * Increment the time by one, and put this change on the text box.
         */
        private void time() {
            if (time >= -1) {
                time++;
            }
            String s = time + "";
            result.setText(s);
        }

        /**
         * Reset the time to 0.
         */
        public static void resetTime() {
            time = 0;
            animation.stop();
        }

        /**
         * Pauses the timer animation.
         */
        public static void pauseTime() {
            animation.pause();
        }

        /**
         * Resumes the timer animation.
         */
        public static void resumeTime() {
            animation.play();
        }
    }

}