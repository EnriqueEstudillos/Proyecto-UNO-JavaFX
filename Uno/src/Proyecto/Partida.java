package Proyecto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author bemen3
 */
public class Partida {
    private ArrayList<Jugador> Jugadores;
    private ArrayList<Carta> Baraja;
    public Carta Monton;
    boolean reverse;
    boolean block;
    int Turno;
    LocalTime comienzo;
    
    private final String[][] cartas = {
        {"Amarillo","0","1","1","2","2","3","3","4","4","5","5","6","6","7","7","8","8","9","9","block","block","+2","+2","reverse","reverse","Wildcard","+4"},
        {"Rojo","0","1","1","2","2","3","3","4","4","5","5","6","6","7","7","8","8","9","9","block","block","+2","+2","reverse","reverse","Wildcard","+4"},
        {"Azul","0","1","1","2","2","3","3","4","4","5","5","6","6","7","7","8","8","9","9","block","block","+2","+2","reverse","reverse","Wildcard","+4"},
        {"Verde","0","1","1","2","2","3","3","4","4","5","5","6","6","7","7","8","8","9","9","block","block","+2","+2","reverse","reverse","Wildcard","+4"}
    };
    
    private final String[] colores = {"Amarillo","Rojo","Azul","Verde"};
    
    public String ObtenerColor(){
        return colores[(int)(Math.random() * 4)];
    }

    public Partida(ArrayList<Jugador> Jugadores, ArrayList<Carta> Baraja) {
        this.Jugadores = Jugadores;
        this.Baraja = Baraja;
    }

    public ArrayList<Jugador> getJugadores() {
        return Jugadores;
    }

    public ArrayList<Carta> getBaraja() {
        return Baraja;
    }
    
    public void setBaraja(ArrayList<Carta> Baraja) {
        this.Baraja = Baraja;
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

    public int getTurno() {
        return Turno;
    }

    public void setTurno(int Turno) {
        this.Turno = Turno;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }
    
    public LocalTime getComienzo() {
        return comienzo;
    }

    private void setComienzo(LocalTime comienzo) {
        this.comienzo = comienzo;
    }
    
    public int BarajaLength(){
        return getBaraja().size();
    }
    
    public String MostarCartaMonton(){
        return getMonton().nombre+" "+getMonton().color;
    }
    
    public void Barajar(){
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
        setBaraja(Cartas);
    }
    
    public void RepartirCartas(){
        for (Jugador Jugador : Jugadores) {
            Jugador.setMano(new ArrayList<>());
            Jugador.RobarCarta(Baraja,7);
        }
    }
    
    public void PrimeraCartaMonton(){
        setMonton(Baraja.remove(0));
    }
    
    public void ComenzarPartida(){
        setComienzo(LocalTime.now());
        Barajar();
        RepartirCartas();
        PrimeraCartaMonton();
        
        /*for (int i = 0;i < Jugadores.size();i++){
            System.out.println("\nCartas del jugador: "+Jugadores.get(i).getNombre());
            System.out.println("Cantidad: "+Jugadores.get(i).ManoLength());
            Jugadores.get(i).MostrarMano();
        }*/
        setTurno(0);
        setBlock(false);
        setReverse(false);
    }
    
    public boolean ComprobarCondicionGanador(String modo){
        for (Jugador jugador : Jugadores) {
            if (jugador.Mano.isEmpty()){
                System.out.println("Jugador "+jugador.Nombre+" ha ganado");
                EscribirHistorial(modo, jugador.Nombre, CalcularPuntuacion());
                return true;
            }
        }
        return false;
    }
    
    public int CalcularPuntuacion(){
        int puntuacion = 0;
        for (Jugador Jugador : Jugadores) {
            for (Carta Carta : Jugador.Mano) {
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
    
    @Override
    public String toString() {
        return "Partida{" + "Jugadores=" + Jugadores + ", Baraja=" + Baraja + '}';
    }
}
