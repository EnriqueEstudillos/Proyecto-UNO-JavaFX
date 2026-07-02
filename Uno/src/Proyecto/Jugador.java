package Proyecto;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author bemen3
 */
public class Jugador {
    protected final Scanner sc = new Scanner(System.in);
    String Nombre;
    ArrayList<Carta> Mano;
    int Puntuacion;

    public Jugador(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public String getNombre() {
        return Nombre;
    }
    
    public ArrayList<Carta> getMano() {
        return Mano;
    }

    public void setMano(ArrayList<Carta> Mano) {
        this.Mano = Mano;
    }

    public int getPuntuacion() {
        return Puntuacion;
    }

    public void setPuntuacion(int Puntuacion) {
        this.Puntuacion = Puntuacion;
    }
    
    public void RobarCarta(ArrayList<Carta> Baraja, int Cantidad){
        for (int i = 0;i < Cantidad; i++){
            //ArrayList<Carta> Mano = getMano();
            Carta carta = Baraja.remove(0);
            if (carta != null){
                Mano.add(carta);
            }
            else{
                System.out.println("No hay cartas para robar");
            }
        }
    }
    
    public void MostrarMano(){
        for (int i = 0;i < getMano().size();i++){
            System.out.println("Carta "+i+": "+getMano().get(i).nombre+" "+getMano().get(i).color);
        }
    }
    
    public int ManoLength(){
        ArrayList<Carta> Mano = getMano();
        return Mano.size();
    }
    
    protected String ElegirColor(){
        System.out.println("\nElige el color del comodin");
        System.out.println("1.) Amarillo");
        System.out.println("2.) Rojo");
        System.out.println("3.) Azul");
        System.out.println("4.) Verde");
        switch (sc.nextInt()) {
            case 1:
                return "Amarillo";
            case 2:
                return "Rojo";
            case 3:
                return "Azul";
            case 4:
                return "Verde";
            default:
                return "NULL";
        }
    }
    
    protected String ElegirColorMaquina(){
        switch ((int) (Math.random()*4+1)) {
            case 1:
                return "Amarillo";
            case 2:
                return "Rojo";
            case 3:
                return "Azul";
            case 4:
                return "Verde";
            default:
                return "NULL";
        }
    }

    public void TurnoJugador(Partida Partida){
        System.out.println("Turno Jugador");
    }
    
    public int NextPerson(Partida Partida){
        if (!Partida.isReverse()){
            if (Partida.getTurno()+1 > Partida.getJugadores().size()-1){
                return 0;
            }
            else return Partida.getTurno()+1;
        }
        else{
            if (Partida.getTurno()-1 < 0){ 
                return Partida.getJugadores().size()-1;
            }
            else return Partida.getTurno()-1;
        }
    }
    
    public void NextTurno(Partida Partida){
        if (!Partida.isReverse()){
            if (Partida.getTurno()+1 > Partida.getJugadores().size()-1){
                Partida.setTurno(0);
                if (Partida.isBlock()){
                    System.out.println("El jugador "+Partida.getJugadores().get(Partida.getTurno()).Nombre+" ha perdido su turno");
                    Partida.setBlock(false);
                    NextTurno(Partida);
                }
            }
            else{
                Partida.setTurno(Partida.getTurno()+1);
                if (Partida.isBlock()){
                    System.out.println("El jugador "+Partida.getJugadores().get(Partida.getTurno()).Nombre+" ha perdido su turno");
                    Partida.setBlock(false);
                    NextTurno(Partida);
                }
            }
        }
        else{
            if (Partida.getTurno()-1 < 0){
                Partida.setTurno(Partida.getJugadores().size()-1);
                if (Partida.isBlock()){
                    System.out.println("El jugador "+Partida.getJugadores().get(Partida.getTurno()).Nombre+" ha perdido su turno");
                    Partida.setBlock(false);
                    NextTurno(Partida);
                }
            }
            else{
                Partida.setTurno(Partida.getTurno()-1);
                if (Partida.isBlock()){
                    System.out.println("El jugador "+Partida.getJugadores().get(Partida.getTurno()).Nombre+" ha perdido su turno");
                    Partida.setBlock(false);
                    NextTurno(Partida);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Jugador{" + "nombre=" + Nombre + ", mano=" + Mano + '}';
    }
}
