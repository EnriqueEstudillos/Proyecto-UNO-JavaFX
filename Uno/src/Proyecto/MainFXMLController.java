package Proyecto;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bemen3
 */
public class MainFXMLController implements Initializable {
    
    @FXML 
    ImageView playButton;
    
    final String PlayImage = "/imagenes/Botones/Play.png";
    final String PlayHoverImage = "/imagenes/Botones/PlayHover.png";
    
    @FXML 
    ImageView settingsButton;
    
    final String SettingsImage = "/imagenes/Botones/Settings.png";
    final String SettingsHoverImage = "/imagenes/Botones/SettingsHover.png";
    
    @FXML 
    ImageView historyButton;
    
    final String HistoryImage = "/imagenes/Botones/History.png";
    final String HistoryHoverImage = "/imagenes/Botones/HistoryHover.png";
    
    @FXML 
    ImageView exitButton;
    
    final String ExitImage = "/imagenes/Botones/Exit.png";
    final String ExitHoverImage = "/imagenes/Botones/ExitHover.png";
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //PlayButton: 700X, 300Y
        InitializeButton(playButton, PlayImage, PlayHoverImage);
        //SettingsButton: 700X, 450Y
        InitializeButton(settingsButton, SettingsImage, SettingsHoverImage);
        //HistoryButton: 700X, 600Y
        InitializeButton(historyButton, HistoryImage, HistoryHoverImage);
        //ExitButton: 700X, 750Y
        InitializeButton(exitButton, ExitImage, ExitHoverImage);
        
        
        playButton.setOnMousePressed(event-> {SwitchScenes("GameConnectionSelectionFXML.fxml", playButton);});
        
        historyButton.setOnMousePressed(event-> {SwitchScenes("HistorialFXML.fxml", historyButton);});
        
        settingsButton.setOnMousePressed(event-> {SwitchScenes("SettingsFXML.fxml", settingsButton);});
        
        exitButton.setOnMousePressed(event -> {System.exit(0);});
    }
    
    private void SwitchImage(ImageView Button, String Image){
        try {
            Button.setImage(new Image(Image));
        }catch (Exception e) {
            System.out.println("Error SwitchImage(): " + e);
        }
    }
    
    private void InitializeButton(ImageView Button,String Image,String ImageHover){
        try{
            Button.setImage(new Image(Image));
            //Button.setLayoutX(X);
            //Button.setLayoutY(Y);
            
            Button.setOnMouseEntered(event -> {SwitchImage(Button, ImageHover);});

            Button.setOnMouseExited(event -> {SwitchImage(Button, Image);});
            
        }catch(Exception e){
            System.out.println("Error InitializeButton(): "+ e);
        }
    }
    
    private void SwitchScenes(String FXML, ImageView button){
        try{
            root = FXMLLoader.load(getClass().getResource(FXML));
            stage = (Stage) button.getScene().getWindow();
            //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            System.out.println("Error SwitchScenes(): "+e);
        }
    }
}
