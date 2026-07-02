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
public class GameModeSelectionFXMLController implements Initializable {
    
    @FXML 
    ImageView Button1v1;
    
    final String Image1v1 = "/imagenes/Botones/1v1.png";
    final String HoverImage1v1 = "/imagenes/Botones/1v1Hover.png";
    
    @FXML 
    ImageView Button1v3;
    
    final String Image1v3 = "/imagenes/Botones/1v3.png";
    final String HoverImage1v3 = "/imagenes/Botones/1v3Hover.png";
    
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
        InitializeButton(Button1v1, Image1v1, HoverImage1v1);
        //MultiplayerButton: 700X, 450Y
        InitializeButton(Button1v3, Image1v3, HoverImage1v3);
        //BackButton: 700X, 750Y
        InitializeButton(BackButton, BackImage, BackHoverImage);
        
        Button1v1.setOnMousePressed(event -> {SwitchScenes("GameLoopFXML.fxml", Button1v1);});
        
        
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
            
            root = loader.load();
            GameLoopFXMLController GameController = loader.getController();
            
            if (button.equals(Button1v1)){
                GameController.setGameMode("1v1");
            }
            else{
                GameController.setGameMode("1v3");
            }
            
            stage = (Stage) button.getScene().getWindow();
            //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.hide();
            
        }catch (IOException e){
            System.out.println("Error SwitchScenes(): "+e);
        }
    }
}
