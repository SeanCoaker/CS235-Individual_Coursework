import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * This class is used to handle the window containing information and instructions of how to play the game.
 * Version History - version 1.0
 * Filename: InfoController.java
 * @author Sean Coaker
 * @version 1.0
 * @since 25-04-2020
 * copyright: No Copyright Purpose
 */
public class InfoController implements Initializable {
    @FXML
    private HBox collectablesBox;

    @FXML
    private HBox doorsBox;

    @FXML
    private HBox environmentBox;

    @FXML
    private HBox moveableEntitiesBox;

    @FXML
    private Button btnBack;

    @FXML
    private TextArea infoText;

    private HashMap<String, String> descMap = new HashMap<>();

    private ArrayList<ArrayList<SavedImage>> categories = new ArrayList<>(); // array list of saved image files.
    private ArrayList<SavedImage> collectibles; // array list of collectibles.
    private ArrayList<SavedImage> doors; // array list of doors.
    private ArrayList<SavedImage> environment; // array list of environments.
    private ArrayList<SavedImage> movableEntities; // array list of movable entities.
    private ArrayList<SavedImage> images = new ArrayList<>(); // array list of images.

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        readDescriptions();
        infoText.setEditable(false);
        infoText.setWrapText(true);
        infoText.setFont(Font.font("Consolas", 20));
        infoText.setStyle("-fx-text-fill:  Black");
        infoText.setText(descMap.get("Reset"));
        readFolders();
        resizeImage();
    }

    /**
     * Reads all the images in subfolders within the Images folder and adds these images to their relevant categories.
     */
    private void readFolders() {
        try {
            File path = new File("src\\Images");
            File[] files = path.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isDirectory()) { //this line weeds out other directories/folders
                    switch (file.getName()) {
                        case "collectables":
                            collectibles = readFile(file.getName());
                            categories.add(collectibles);
                            break;
                        case "doors":
                            doors = readFile(file.getName());
                            categories.add(doors);
                            break;
                        case "environment":
                            environment = readFile(file.getName());
                            categories.add(environment);
                            break;
                        case "moveableEntities":
                            movableEntities = readFile(file.getName());
                            categories.add(movableEntities);
                            break;
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads all files within a folder, and adds them to an ArrayList.
     * @param folder - the folder to search within
     * @return an ArrayList holding all the SavedImage objects of the folder's contents
     */
    private ArrayList<SavedImage> readFile(String folder) {
        ArrayList<SavedImage> imageCollection;
        try {
            File path = new File("src\\Images\\" + folder);
            imageCollection = new ArrayList();
            File[] files = path.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isFile()) { //this line weeds out other directories/folders
                    imageCollection.add(readImageFile(file));
                    //allCells.add(readImageFile(file));
                }
            }
            return imageCollection;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a SavedImage object from an image's file
     * @param file - the image file
     * @return a SavedImage object containing the image and it's filename
     */
    private SavedImage readImageFile(File file) {
        SavedImage image = null;
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(file.getPath()));
            image = new SavedImage(is, file);
            images.add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * For every category, resizes each objects image and places it along the left hand side of the screen. It also adds
     * the string change action in the infoText area when a mouse is hovered over the images.
     */
    private void resizeImage() {
        for (SavedImage img : collectibles) {
            ImageView imageView = new ImageView();
            imageView.setImage(img);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
            setMouseHoverEvent(imageView);
            collectablesBox.getChildren().add(imageView);
            collectablesBox.setSpacing(10);
        }

        for (SavedImage img : doors) {
            ImageView imageView = new ImageView();
            imageView.setImage(img);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
            setMouseHoverEvent(imageView);
            doorsBox.getChildren().add(imageView);
            doorsBox.setSpacing(10);
        }

        for (SavedImage img : environment) {
            ImageView imageView = new ImageView();
            imageView.setImage(img);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
            setMouseHoverEvent(imageView);
            environmentBox.getChildren().add(imageView);
            environmentBox.setSpacing(10);
        }

        for (SavedImage img : movableEntities) {
            ImageView imageView = new ImageView();
            imageView.setImage(img);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
            setMouseHoverEvent(imageView);
            moveableEntitiesBox.getChildren().add(imageView);
            moveableEntitiesBox.setSpacing(10);
        }
    }

    /**
     * Method that handles button clicks to load different scenes.
     *
     * @param e the button that was clicked.
     * @throws IOException handles IO exceptions.
     */
    @FXML
    public void btnBackClicked(ActionEvent e) throws IOException {
        if (e.getSource().equals(btnBack)) {
            Node node = (Node) e.getSource();
            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setTitle("Menu");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * This method handles the event of a mouse being hovered over an image of a game object/tile. When this happens
     * the code searches for which image was hovered over, gets it's name and then shows the description for that game
     * object in the text area.
     * @param imageView The image that's been hovered over.
     */
    private void setMouseHoverEvent(ImageView imageView) {
        SavedImage savedImage = (SavedImage) imageView.getImage();

        String output = "";

        switch (savedImage.getName()) {
            case "BlueKey.png":
                output = "Blue Key - " + descMap.get("Blue Key");
                break;

            case "GreenKey.png":
                output = "Green Key - " + descMap.get("Green Key");
                break;

            case "RedKey.png":
                output = "Red Key - " + descMap.get("Red Key");
                break;

            case "FireBoots.png":
                output = "Fire Boots - " + descMap.get("Fire Boots");
                break;

            case "Flippers.png":
                output = "Flippers - " + descMap.get("Flippers");
                break;

            case "Token.png":
                output = "Token - " + descMap.get("Token");
                break;

            case "BlueDoor.png":
                output = "Blue Door - " + descMap.get("Blue Door");
                break;

            case "GreenDoor.png":
                output = "Green Door - " + descMap.get("Green Door");
                break;

            case "RedDoor.png":
                output = "Red Door - " + descMap.get("Red Door");
                break;

            case "TokenDoor.png":
                output = "Token Door - " + descMap.get("Token Door");
                break;

            case "Fire.png":
                output = "Lava - " + descMap.get("Lava");
                break;

            case "Goal.png":
                output = "Goal - " + descMap.get("Goal");
                break;

            case "Ground.png":
                output = "Ground - " + descMap.get("Ground");
                break;

            case "Teleporter.png":
                output = "Teleporter - " + descMap.get("Teleporter");
                break;

            case "Wall.png":
                output = "Wall - " + descMap.get("Wall");
                break;

            case "Water.png":
                output = "Water - " + descMap.get("Water");
                break;

            case "BlindEnemy.png":
                output = "Blind Enemy - " + descMap.get("Blind Enemy");
                break;

            case "DumbEnemy.png":
                output = "Dumb Enemy - " + descMap.get("Dumb Enemy");
                break;

            case "LineEnemy.png":
                output = "Line Enemy - " + descMap.get("Line Enemy");
                break;

            case "Player.png":
                output = "Player - " + descMap.get("Player");
                break;

            case "SmartEnemy.png":
                output = "Smart Enemy - " + descMap.get("Smart Enemy");
                break;

            case "WallEnemy.png":
                output = "Wall Following Enemy - " + descMap.get("Wall Following Enemy");
                break;
        }

        String finalOutput = output;
        imageView.setOnMouseEntered(e -> infoText.setText(finalOutput));

        imageView.setOnMouseExited(e -> infoText.setText(descMap.get("Reset")));
    }

    /**
     * This method reads descriptions for all game objects from the descriptions.txt file. The code then assigns a
     * description to the game object in a hash map.
     */
    public void readDescriptions() {
        File file = new File("GlobalFiles\\descriptions.txt");
        Scanner in = null;

        try {
            in = new Scanner(file);
            in.useDelimiter(":");
        } catch (FileNotFoundException e) {
            System.out.println("Object description file could not be found. Recommended Fix: Please re-install game files");
            System.exit(0);
        }

        while (in.hasNext()) {
            descMap.put(in.next(), in.next());
            in.nextLine();
        }

        in.close();
    }
}
