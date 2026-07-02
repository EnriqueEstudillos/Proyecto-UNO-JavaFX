/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Proyecto;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bemen3
 */
public class LoginFXMLController implements Initializable {

    @FXML
    TextField nombre;
    
    @FXML
    TextField contraseña;
    
    @FXML 
    Label enviarButton;
    
    @FXML 
    Label registrarButton;
    
    @FXML 
    Label errorUsuario;
    
    @FXML 
    Label errorContraseña;
    
    @FXML 
    Label correctoUsuario;
    
    private int user_id;
    
    final String DB_URL = "jdbc:mysql://localhost:3306/UnoBD";
    final String DB_DRV = "com.mysql.jdbc.Driver";
    final String DB_USER = "Admin_UNO";
    final String DB_PASSWD = "BEMEN3";
    Connection connection = null;
    Statement statement = null;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pdCrearConexion();
        enviarButton.setOnMouseClicked(event -> {Enviar();});
        registrarButton.setOnMouseClicked(event -> {SwitchScenes("RegistrarFXML.fxml", registrarButton);});
        pdConsultaJugadores();
    }
    
    public void pdCrearConexion() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            //statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void SwitchScenes(String FXML, Label button){
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
    
    public void Clear(){
        errorContraseña.setText("");
        errorUsuario.setText("");
        correctoUsuario.setText("");
    }
    
    public void Enviar(){
        Clear();
        ResultSet resultSet = pdConsultaJugadores();
        boolean UsuarioEncontrado = false;
        boolean ContraseñaCorrecta = false;
        
        if (nombre.getText() != null){
            if (contraseña.getText() != null){
                try {
                    while (resultSet.next()){
                        user_id = resultSet.getInt("ID");
                        String usuario = resultSet.getString("Nombre");
                        
                        if (usuario.equals(nombre.getText())){
                            UsuarioEncontrado = true;
                            String password = resultSet.getString("Contraseña");
                            if (password.equals(contraseña.getText())){
                                ContraseñaCorrecta = true;
                                break;
                            }else{ break;}
                        }
                    }
                    if (UsuarioEncontrado){
                        if (ContraseñaCorrecta){
                            System.out.println("Login Correcto");
                            //Login Correcto -- Funcion siguiente pagina con usuario
                            correctoUsuario.setText("Login Correcto");
                            Usuario.getInstance().Initialize(user_id);
                            SwitchScenes("MainFXML.fxml", enviarButton);
                        }else{
                            errorContraseña.setText("Contraseña Incorrecta");
                        }
                    }else{
                        errorUsuario.setText("No se ha encontrado el usuario con nombre: "+nombre.getText());
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                errorContraseña.setText("No se ha introducido la contraseña");
            }
        }else{
            errorUsuario.setText("No se ha introducido el usuario");
        }
    }
    
    public ResultSet pdConsultaJugadores() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Jugadores");
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void ComprobarNombre(ResultSet resultSet){
        
    }

    
}
