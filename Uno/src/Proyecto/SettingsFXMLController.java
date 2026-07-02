/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Proyecto;

import static Proyecto.HistorialFXMLController.DB_PASSWD;
import static Proyecto.HistorialFXMLController.DB_URL;
import static Proyecto.HistorialFXMLController.DB_USER;
import static Proyecto.HistorialFXMLController.connection;
import static Proyecto.HistorialFXMLController.statement;
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
public class SettingsFXMLController implements Initializable {
    
    @FXML
    Label currentEmail;
    
    @FXML
    TextField cambiarEmail;
    
    @FXML
    Label errorEmail;
    
    @FXML
    Label volver;
    
    @FXML
    Label enviar;
    
    static final String DB_URL = "jdbc:mysql://localhost:3306/unobd";
    static final String DB_DRV = "com.mysql.jdbc.Driver";
    static final String DB_USER = "Admin_UNO";
    static final String DB_PASSWD = "BEMEN3";
    static Connection connection = null;
    static Statement statement = null;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pdCrearConexion();
        currentEmail.setText(Usuario.getInstance().getUserEmail());
        
        enviar.setOnMouseClicked(event -> {Enviar();});
        
        volver.setOnMouseClicked(event -> {SwitchScenes("MainFXML.fxml",volver);});
    }

    public static void pdCrearConexion() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
        } catch (SQLException ex) {
            Logger.getLogger(HistorialFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void Enviar(){
        ResultSet resultSet = pdConsultaEmail();
        try {
            if (!resultSet.next()){
                //pdInsertarUsuario();
                pdModificarEmail();
                Usuario.getInstance().Update();
                currentEmail.setText(Usuario.getInstance().getUserEmail());
                //SwitchScenes("MainFXML.fxml", enviar);
            }else{
                errorEmail.setText("El Email introducido ya esta en uso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet pdConsultaEmail() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Email FROM Jugadores WHERE ID = '"+Usuario.getInstance().getUserId()+"' AND Email = '"+cambiarEmail.getText()+"'");
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void pdModificarEmail(){
        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE Jugadores SET Email = '"+cambiarEmail.getText()+"' WHERE ID = '"+Usuario.getInstance().getUserId()+"'");
        } catch (SQLException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
