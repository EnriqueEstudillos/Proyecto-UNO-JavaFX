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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bemen3
 */
public class HistorialFXMLController implements Initializable {

    static final String DB_URL = "jdbc:mysql://localhost:3306/unobd";
    static final String DB_DRV = "com.mysql.jdbc.Driver";
    static final String DB_USER = "Admin_UNO";
    static final String DB_PASSWD = "BEMEN3";
    static Connection connection = null;
    static Statement statement = null;
    
    @FXML
    ListView lista = new ListView<String>();
    
    @FXML
    Button volver;
    
    @FXML
    Button resetear;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private int user_id;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pdCrearConexion();
        user_id = Usuario.getInstance().getUserId();
        System.out.println(user_id);
        pdConsultaHistorial();
        
        volver.setOnAction(event -> {SwitchScenes("MainFXML.fxml",volver);});
        resetear.setOnAction(event -> {pdResetearHistorial();});
        
    }
    
    public void pdResetearHistorial(){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT H.ID FROM HISTORIAL H INNER JOIN Jugadores_x_Partida jp ON h.ID=jp.ID_Partida WHERE jp.ID_Jugador = '"+user_id+"'");
            
             while (resultSet.next()){
                String id = String.valueOf(resultSet.getInt("ID"));
                
                statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM Jugadores_X_Partida WHERE ID_Jugador = '"+user_id+"' AND ID_Partida = '"+id+"'");

                statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM HISTORIAL WHERE ID= '"+id+"'");
            }
            
            pdConsultaHistorial();
        } catch (SQLException ex) {
            Logger.getLogger(HistorialFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void pdCrearConexion() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
        } catch (SQLException ex) {
            Logger.getLogger(HistorialFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void pdConsultaHistorial(){
        boolean existencia = false;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT H.* FROM HISTORIAL H INNER JOIN Jugadores_x_Partida jp ON h.ID=jp.ID_Partida WHERE jp.ID_Jugador = '"+user_id+"'");

            ArrayList<String> arrayList = new ArrayList<>();

            //Actualizar ListView con historial
            while (resultSet.next()){
                existencia = true;
                String id = String.valueOf(resultSet.getInt("ID"));
                String ganador = resultSet.getString("Ganador");
                String modo = resultSet.getString("Modo");
                String duracion = resultSet.getString("Duracion");

                String resultRow = id+";"+ganador+";"+modo+";"+duracion;

                arrayList.add(resultRow);
                arrayList.toString();
            }
            
            if (!existencia) {
                System.out.println("No hay partidas en el historial");
            }

            ActualizarListView(arrayList);

        } catch (SQLException ex) {
            Logger.getLogger(HistorialFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void ActualizarListView(ArrayList<String> arrayList){
        ObservableList<String> observableList = FXCollections.observableArrayList(arrayList);
        
        lista.getItems().setAll(observableList);
    }

    private void SwitchScenes(String FXML, Button button){
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
