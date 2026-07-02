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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bemen3
 */
public class RegistrarFXMLController implements Initializable {
    
    @FXML
    TextField nombre;
    
    @FXML
    TextField email;
    
    @FXML
    TextField contraseña;
    
    @FXML 
    Label enviarButton;
    
    @FXML 
    Label loginButton;
    
    @FXML 
    Label errorUsuario;
    
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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pdCrearConexion();
        
        loginButton.setOnMouseClicked(event -> {SwitchScenes("LoginFXML.fxml",loginButton);});
        enviarButton.setOnMouseClicked(event -> {Enviar();});
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
    
    public void Enviar(){
        ResultSet resultSet = pdConsultaNombre();
        try {
            if (!resultSet.next()){
                pdInsertarUsuario();
                
                ResultSet resultSet2 = pdConsultaNombre();
                resultSet2.next();
                Usuario.getInstance().Initialize(resultSet2.getInt("ID"));
                SwitchScenes("MainFXML.fxml", enviarButton);
            }else{
                errorUsuario.setText("El nombre introducido ya esta en uso por otro usuario");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pdCrearConexion() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            //statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet pdConsultaNombre() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Jugadores WHERE Nombre = '"+nombre.getText()+"'");
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void pdInsertarUsuario(){
        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Jugadores (Nombre, Email, Contraseña) VALUES ('"+nombre.getText()+"','"+email.getText()+"','"+contraseña.getText()+"')");
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
