package Proyecto;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bemen3
 */
public class PlayerGameFXMLController implements Initializable{

    //@FXML
    //TextField TextoTest;
    
    @FXML
    ListView<Carta> ListCartas;
    
    @FXML
    ImageView CartaSeleccionada;
    
    private Carta Selecion;
    
    @FXML
    ImageView CartaMonton;
    
    @FXML
    Text ContadorBaraja;
    
    @FXML
    Button JugarCarta;
    
    public Jugador jugador;
    
    private GameLoopFXMLController controller;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        //ListCartas.setSelectionModel(false);
        
        
        ListCartas.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);
        
        JugarCarta.setOnMousePressed(event -> {JugarCarta();});
    }
    
    private void JugarCarta(){
        /*
        Image img = CartaSeleccionada.getImage();
        String fileName = img.getUrl();
        String[] names = fileName.split("/");
        String imageName = names[names.length-1];
        String[] namesS = imageName.split("\\.");
        String FinalName = namesS[0];
        System.out.println("Boton: "+CartaSeleccionada.getId()+", Imagen: "+FinalName);*/
        
        controller.ComprobarCarta(this, getJugador().getMano(), Selecion);
    }
    
    public void ElegirColor(){
        OpenColorSelection();
    }
    
    private void OpenColorSelection(){ 
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ColorSelectionFXML.fxml"));  
            Scene scene2 = new Scene(fxmlLoader.load());
            Stage stage2 = new Stage();
            stage2.setTitle("Color Selection");
            stage2.setScene(scene2);
            
            ColorSelectionFXMLController controlador = (ColorSelectionFXMLController) fxmlLoader.getController();
            controlador.setParentController(this);
            
            stage2.show();
            
            
            
        }catch (Exception e){
            System.out.println("Error OpenPlayerGame(): "+e);
        }
    }
    
    public void EnviarColorMonton(String color){
        controller.setColorMonton(color);
    }
    
    private void selectionChanged(ObservableValue<? extends Carta> Observable, Carta oldVal, Carta newVal){
        if (ListCartas.getSelectionModel().getSelectedItem() == null){
            try{
                //System.out.println("/imagenes/Cartas/"+newVal.getNombre()+newVal.getColor()+".png");
                Image imagen = new Image("/imagenes/Cartas/"+newVal.getNombre()+newVal.getColor()+".png");
                CartaSeleccionada.setImage(imagen);
                Selecion = newVal;
            }catch (Exception e){
                System.out.println("Error ImagenCarta: "+e);
            }
        }
        else{
            try{
                //System.out.println("/imagenes/Cartas/"+newVal.getNombre()+newVal.getColor()+".png");
                Image imagen = new Image("/imagenes/Cartas/"+newVal.getNombre()+newVal.getColor()+".png");
                CartaSeleccionada.setImage(imagen);
                Selecion = newVal;
            }catch (Exception e){
                System.out.println("Error ImagenCarta: "+e);
            }
        }
    }
    
    public void UpdateListCartas(){
        ListCartas.getItems().clear();
        for (int i = 0;i < getJugador().ManoLength();i++){
            ListCartas.getItems().add(getJugador().getMano().get(i));
            System.out.println(getJugador().getMano().get(i));
        }
    }
    
    public void UpdateContadorBaraja(ArrayList<Carta> Baraja){
        ContadorBaraja.textProperty().setValue(""+Baraja.size());
    }
    
    public void UpdateCartaMonton(Carta Monton){
        CartaMonton.setImage(new Image("/imagenes/Cartas/"+Monton.getNombre()+Monton.getColor()+".png"));
    }
    
    /*public void RepartirCartas(ArrayList<Carta> Baraja){
        jugador.setMano(new ArrayList<>());
        jugador.RobarCarta(Baraja,7);
    }*/

    public void setParentController(GameLoopFXMLController controller){
        this.controller = controller;
    }
    
    public void setJugador(Jugador jugador){
        this.jugador = jugador;
    }
    
    public Jugador getJugador(){
        return this.jugador;
    }
    
}
