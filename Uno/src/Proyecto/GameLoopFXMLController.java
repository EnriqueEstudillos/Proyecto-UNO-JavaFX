package Proyecto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bemen3
 */
public class GameLoopFXMLController implements Initializable {

    
    private GameModeSelectionFXMLController controller;
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private String mode;
    
    @FXML
    private ImageView popcorn;
    
    @FXML
    private ImageView rainbow;
    
    private Partida partida;
    private Maquina maquina = new Maquina("Pomni");
    
    private ArrayList<Jugador> Jugadores = new ArrayList<>();
    private ArrayList<PlayerGameFXMLController> PlayerControllers = new ArrayList<>();
    private ArrayList<Carta> Baraja;
    public Carta Monton;
    boolean reverse;
    boolean block;
    int Turno;
    LocalTime comienzo;
    
    private String colorMonton;
    
    private final String[][] cartas = {
        {"Amarillo","0","1","1","2","2","3","3","4","4","5","5","6","6","7","7","8","8","9","9","block","block","+2","+2","reverse","reverse","Wildcard","+4"},
        {"Rojo","0","1","1","2","2","3","3","4","4","5","5","6","6","7","7","8","8","9","9","block","block","+2","+2","reverse","reverse","Wildcard","+4"},
        {"Azul","0","1","1","2","2","3","3","4","4","5","5","6","6","7","7","8","8","9","9","block","block","+2","+2","reverse","reverse","Wildcard","+4"},
        {"Verde","0","1","1","2","2","3","3","4","4","5","5","6","6","7","7","8","8","9","9","block","block","+2","+2","reverse","reverse","Wildcard","+4"}
    };
    
    private final String[] colores = {"Amarillo","Rojo","Azul","Verde"};
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    private void setComienzo(LocalTime comienzo) {
        this.comienzo = comienzo;
    }
    
    public void setGameMode(String mode){
        switch (mode) {
            case "1v1":
                this.mode = mode;
                Game1v1();
                break;
            case "1v3":
                this.mode = mode;
                System.out.println("BBB");
                break;
            default:
                SwitchScenes("GameModeSelectionFXMLController", popcorn);
        }
    }

    public void setTurno(int Turno) {
        this.Turno = Turno;
    }
    
    public int getTurno() {
        return Turno;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public Carta getMonton() {
        return Monton;
    }

    public void setMonton(Carta Monton) {
        this.Monton = Monton;
    }
    
    public ArrayList<Carta> getBaraja(){
        return this.Baraja;
    }

    public String getColorMonton() {
        return colorMonton;
    }

    public void setColorMonton(String colorMonton) {
        this.colorMonton = colorMonton;
    }
    
    public String ObtenerColor(){
        return colores[(int)(Math.random() * 4)];
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
    
    private void Barajar(){
       ArrayList<Carta> Cartas = new ArrayList<>();
       for (int i = 0;i < 4;i++){
           for (int j = 1; j < 28; j++) {
               if (cartas[i][j].equals("+4") || cartas[i][j].equals("Wildcard")){
                   Cartas.add(new Carta(cartas[i][j], "Negro"));
               }
               else{
                   Cartas.add(new Carta(cartas[i][j], cartas[i][0]));                                    
               }
           }
       }
       Collections.shuffle(Cartas);
       Baraja = Cartas;
    }
    
    public void RepartirCartas(){
        for (PlayerGameFXMLController playerController : PlayerControllers){
            playerController.getJugador().setMano(new ArrayList<>());
            playerController.getJugador().RobarCarta(Baraja,7);
            playerController.UpdateListCartas();
        }
        for (Jugador jugador : Jugadores) {
            if (jugador instanceof Maquina){
                jugador.setMano(new ArrayList<>());
                jugador.RobarCarta(Baraja,7);
                System.out.println(maquina.getMano());
            }
            else{
                //jugador.setMano(PlayerControllers.get(0).getJugador().getMano());
            }
        }
    }
    
    private void Game1v1(){
        setComienzo(LocalTime.now());
        Barajar();
        PrimeraCartaMonton();
        
        OpenPlayerGame("ZaK");
        Jugadores.add(maquina);
        
        RepartirCartas();
        
        UpdatePlayerGame();
        
        setTurno(0);
        setBlock(false);
        setReverse(false);
        //OpenPlayerGame("Pomni", false);
        
        //Partida Partida = new Partida(Jugadores, new ArrayList<>());
        //Partida.ComenzarPartida();
        
        /*
        while (!ComprobarCondicionGanador("1v1") && !PlayerControllers.isEmpty()){
            //Jugadores.get(getTurno()).TurnoJugador(this);
        }*/
    }
    
    public void PrimeraCartaMonton(){
        setMonton(Baraja.remove(0));
    }
    
    private boolean ComprobarCondicionGanador(String modo){
        for (Jugador jugador : Jugadores) {
            if (jugador.getMano().isEmpty()){
                Alert alert = new Alert(AlertType.NONE);
                alert.setAlertType(AlertType.CONFIRMATION);
                alert.show();
                //System.out.println("Jugador "+jugador.Nombre+" ha ganado");
                EscribirHistorial(modo, jugador.Nombre, CalcularPuntuacion());
                return true;
            }
        }
        return false;
    }
    
    
    public boolean ComprobarCarta(PlayerGameFXMLController playerController, ArrayList<Carta> Mano, Carta Carta){
        if (getMonton().color.equals("Negro")){
            getMonton().setColor(ObtenerColor());
            UpdatePlayerGame();
        }
        
        if (Carta.getColor().equals(getMonton().getColor()) || Carta.getNombre().equals(getMonton().getNombre()) || Carta.getColor().equals("Negro")){
            //System.out.println("\nPlayer: Ha jugado la carta "+Carta.getNombre()+" "+Carta.getColor());
            switch (Carta.getNombre()) {
                case "Wildcard":
                    setMonton(Carta);
                    Mano.remove(Carta);
                    // IMPORTANTE:
                    playerController.ElegirColor();
                    getMonton().setColor(getColorMonton());
                    UpdatePlayerGame();
                    NextTurno();
                    return true;
                case "+4":
                    setBlock(true);
                    setMonton(Carta);
                    Mano.remove(Carta);
                    playerController.ElegirColor();
                    while (colorMonton != null){}
                    Monton.setColor(getColorMonton());
                    setColorMonton(null);
                    Jugadores.get(NextPerson()).RobarCarta(getBaraja(), 4);
                    //System.out.println(Partida.getJugadores().get(NextPerson(Partida)).getNombre()+ " ha robado 4 cartas");
                    UpdatePlayerGame();
                    NextTurno();
                    return true;
                case "+2":
                    setBlock(true);
                    setMonton(Carta);
                    Mano.remove(Carta);
                    Jugadores.get(NextPerson()).RobarCarta(getBaraja(), 2);
                    //System.out.println(Partida.getJugadores().get(NextPerson(Partida)).getNombre()+ " ha robado 2 cartas");
                    UpdatePlayerGame();
                    NextTurno();
                    return true;
                case "block":
                    setBlock(true);
                    setMonton(Carta);
                    Mano.remove(Carta);
                    NextTurno();
                    break;
                case "reverse":
                    setReverse(true);
                    setMonton(Carta);
                    Mano.remove(Carta);
                    UpdatePlayerGame();
                    NextTurno();
                    return true;
                default:
                    setMonton(Carta);
                    Mano.remove(Carta);
                    UpdatePlayerGame();
                    NextTurno();
                    return true;
            }
            return false;
        }
        return false;
    }
    
    public void UpdatePlayerGame(){
        for (PlayerGameFXMLController player : PlayerControllers) {
            player.UpdateCartaMonton(getMonton());
            player.UpdateListCartas();
        }
    }
    
    public void TurnoJugador(Partida Partida){
        // Actualizar turno en lista jugadores
        // Actualizar cartas jugador
        // Actualizar Carta Monton
        // Actualizar Turno Jugador
        
        /*
        case 2:
            RobarCarta(Partida.getBaraja(), 1);
            System.out.println("Has robado la carta '"+Mano.get(Mano.size()-1).getNombre() + " "+Mano.get(Mano.size()-1).getColor() + "' y has perdido tu turno.");
            NextTurno(Partida);*/

    }
    
    public int NextPerson(){
        if (!isReverse()){
            if (getTurno()+1 > Jugadores.size()-1){
                return 0;
            }
            else return getTurno()+1;
        }
        else{
            if (getTurno()-1 < 0){ 
                return Jugadores.size()-1;
            }
            else return getTurno()-1;
        }
    }
    
    private void OpenPlayerGame(String nombre){ 
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("PlayerGameFXML.fxml"));  
            Scene scene2 = new Scene(fxmlLoader.load());
            Stage stage2 = new Stage();
            stage2.setTitle("GamePlayer");
            stage2.setScene(scene2);
            
            PlayerGameFXMLController controlador = (PlayerGameFXMLController) fxmlLoader.getController();
            controlador.setParentController(this);
            
            controlador.setJugador(new Persona("ZaK"));
            Jugadores.add(controlador.getJugador());
            PlayerControllers.add(controlador);

            controlador.setJugador(new Persona(nombre));

            
            //controlador.RepartirCartas(Baraja);
            System.out.println(controlador.getJugador());
            

            stage2.show();
            
            
            
        }catch (Exception e){
            System.out.println("Error OpenPlayerGame(): "+e);
        }
    }
    
    public void NextTurno(){
        if (!isReverse()){
            if (getTurno()+1 > Jugadores.size()-1){
                setTurno(0);
                if (isBlock()){
                    //System.out.println("El jugador "+Partida.getJugadores().get(Partida.getTurno()).Nombre+" ha perdido su turno");
                    setBlock(false);
                    NextTurno();
                }
            }
            else{
                setTurno(getTurno()+1);
                if (isBlock()){
                    //System.out.println("El jugador "+Partida.getJugadores().get(Partida.getTurno()).Nombre+" ha perdido su turno");
                    setBlock(false);
                    NextTurno();
                }
            }
        }
        else{
            if (getTurno()-1 < 0){
                setTurno(Jugadores.size()-1);
                if (isBlock()){
                    //System.out.println("El jugador "+Partida.getJugadores().get(Partida.getTurno()).Nombre+" ha perdido su turno");
                    setBlock(false);
                    NextTurno();
                }
            }
            else{
                setTurno(getTurno()-1);
                if (isBlock()){
                    //System.out.println("El jugador "+Partida.getJugadores().get(Partida.getTurno()).Nombre+" ha perdido su turno");
                    setBlock(false);
                    NextTurno();
                }
            }
        }
    }
    
    public int CalcularPuntuacion(){
        int puntuacion = 0;
        for (Jugador Jugador : Jugadores) {
            for (Carta Carta : Jugador.getMano()) {
                switch (Carta.nombre) {
                    case "Wildcard":
                        puntuacion += 50;
                        break;
                    case "+4":
                        puntuacion += 50;
                        break;
                    case "+2":
                        puntuacion += 20;
                        break;
                    case "reverse":
                        puntuacion += 20;
                        break;
                    case "block":
                        puntuacion += 20;
                        break;
                    default:
                        int numero = Integer.parseInt(Carta.nombre);
                        puntuacion += numero;
                }
            }
        }
        return puntuacion;
    }
    
    
    private void EscribirHistorial(String modo, String ganador, int puntuacion){
        File historial = ComprobarExistenciaFicheroH();
        if (historial != null){
            try {
                FileOutputStream fos = new FileOutputStream(historial, true);
                PrintStream escriptor = new PrintStream(fos);
                Duration duracion = Duration.between(comienzo, LocalTime.now());
                long horas = duracion.toHours();
                long minutos = duracion.toMinutes() % 60;
                long segundos = duracion.getSeconds() % 60;

                //Formatter format = new Formatter("dd-MM-YYYY");

                String duration = String.format("%02d:%02d:%02d", horas, minutos, segundos);

                escriptor.print(ganador+","+modo+","+duration+","+puntuacion);
                escriptor.print("\n");
            } catch (FileNotFoundException ex) {
                System.out.println("Error: "+ex);
            }
        }
        else System.out.println("ERROR FICHERO HISTORIAL");
    }
    
    public File ComprobarExistenciaFicheroH(){
        String dia = Integer.toString(LocalDate.now().getDayOfMonth());
        String mes = Integer.toString(LocalDate.now().getMonthValue());
        if (LocalDate.now().getDayOfMonth() < 10){
            dia = "0"+LocalDate.now().getDayOfMonth();
        }
        if (LocalDate.now().getMonthValue() < 10){
            mes = "0"+LocalDate.now().getMonthValue();
        }
        String nombre = "Partidas_"+dia+"-"+mes+"-"+LocalDate.now().getYear()+".txt";
        File historial = new File("projecte_files//"+nombre);
        
        if (!historial.exists()) try {
            historial.createNewFile();
            //System.out.println("Nombre fichero historial: "+nombre);
            return historial;
        } catch (IOException ex) {
            System.out.println("Error: "+ex);
        }
        return historial;
    }
}
