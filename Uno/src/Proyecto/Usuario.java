package Proyecto;

import static Proyecto.SettingsFXMLController.DB_PASSWD;
import static Proyecto.SettingsFXMLController.DB_URL;
import static Proyecto.SettingsFXMLController.DB_USER;
import static Proyecto.SettingsFXMLController.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bemen3
 */
public class Usuario {
    private static Usuario instance;

    private int userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    
    static final String DB_URL = "jdbc:mysql://localhost:3306/unobd";
    static final String DB_DRV = "com.mysql.jdbc.Driver";
    static final String DB_USER = "Admin_UNO";
    static final String DB_PASSWD = "BEMEN3";
    static Connection connection = null;
    static Statement statement = null;
    

    private Usuario() {
        // Constructor privado para evitar instanciación
    }
    
    public static void pdCrearConexion() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
        } catch (SQLException ex) {
            Logger.getLogger(HistorialFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Usuario getInstance() {
        if (instance == null) {
            instance = new Usuario();
        }
        return instance;
    }

    public int getUserId() {
        return userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void Update(){
        ResultSet resultSet = pdSelectJugador();
        
        try {
            while (resultSet.next()){
                this.userName = resultSet.getString("Nombre");
                this.userEmail = resultSet.getString("Email");
                this.userPassword = resultSet.getString("Contraseña");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Initialize(int userId){
        this.userId = userId;
        pdCrearConexion();
        ResultSet resultSet = pdSelectJugador();
        
        try {
            while (resultSet.next()){
                this.userName = resultSet.getString("Nombre");
                this.userEmail = resultSet.getString("Email");
                this.userPassword = resultSet.getString("Contraseña");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private ResultSet pdSelectJugador(){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * FROM Jugadores WHERE ID = '"+this.userId+"'");
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
