package Proyecto;

import java.io.FileInputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Enrique Estudillos Casas
 */
public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
            primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/imagenes/RainbowIcon.gif")));
            
            //FileInputStream inputstream = new FileInputStream("/Imagenes/Popcorn.gif"); 
            //primaryStage.getIcons().add(new Image(inputstream));       
            
            primaryStage.setTitle("UNO");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Error Start(): "+e);
        }
    }
}
