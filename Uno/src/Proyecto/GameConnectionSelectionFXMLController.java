/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Proyecto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bemen3
 */
public class GameConnectionSelectionFXMLController implements Initializable {
    
    @FXML 
    ImageView LocalButton;
    
    final String LocalImage = "/imagenes/Botones/Local.png";
    final String LocalHoverImage = "/imagenes/Botones/LocalHover.png";
    
    @FXML 
    ImageView OnlineButton;
    
    final String OnlineImage = "/imagenes/Botones/Online.png";
    final String OnlineHoverImage = "/imagenes/Botones/OnlineHover.png";
    
    @FXML 
    ImageView BackButton;
    
    final String BackImage = "/imagenes/Botones/Back.png";
    final String BackHoverImage = "/imagenes/Botones/BackHover.png";
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //LocalButton: 700X, 300Y
        InitializeButton(LocalButton, LocalImage, LocalHoverImage);
        //MultiplayerButton: 700X, 450Y
        InitializeButton(OnlineButton, OnlineImage, OnlineHoverImage);
        //BackButton: 700X, 750Y
        InitializeButton(BackButton, BackImage, BackHoverImage);

        LocalButton.setOnMousePressed(event -> {SwitchScenes("GameModeSelectionFXML.fxml", LocalButton);});
        
        BackButton.setOnMousePressed(event-> {SwitchScenes("MainFXML.fxml", BackButton);});

    }    
    
    private void SwitchImage(ImageView Button, String Image){
        try {
            Button.setImage(new Image(Image));
        }catch (Exception e) {
            System.out.println("Error: " + e);
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
