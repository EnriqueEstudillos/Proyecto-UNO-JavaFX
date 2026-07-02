package Proyecto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author bemen3
 */
public class ColorSelectionFXMLController implements Initializable {
    
    @FXML
    private ComboBox<String> colores;
    
    @FXML
    Button elegir;
    
    private String color;
    
    private PlayerGameFXMLController jugador;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Agregamos las opciones al ComboBox de género
        colores.getItems().addAll("Color", "Rojo", "Azul", "Verde", "Amarillo");
        // Establecemos la opción predeterminada
        colores.setValue("Color");
        
        elegir.setOnAction(event -> {EnviarColor();});
    }    
    
    private void EnviarColor(){
        
        color = colores.getSelectionModel().getSelectedItem();
        if (!color.equals("Color")) {
            jugador.EnviarColorMonton(color);
        }
    }
    
    public void setParentController(PlayerGameFXMLController Jugador){
        this.jugador = Jugador;
    }
}
