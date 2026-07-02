package Proyecto;

/**
 *
 * @author bemen3
 */
public class Maquina extends Jugador{
    int Nivel;   
    
    public Maquina(String Nombre) {
        super(Nombre);
    }

    public Maquina(int Nivel, String Nombre) {
        super(Nombre);
        this.Nivel = Nivel;
    }

    public int getNivel() {
        return Nivel;
    }

    public void setNivel(int Nivel) {
        this.Nivel = Nivel;
    }
    
    public void Retar(){
        System.out.println("No podras conmigo");
    }
    
    @Override 
    public void TurnoJugador(Partida Partida){
        System.out.println("\nEs el turno del jugador: "+getNombre());
        System.out.println("Le quedan "+getMano().size()+" cartas.");
        System.out.println("\nLa carta del monton es: "+Partida.MostarCartaMonton());
        boolean Jugado = false;
        for (Carta Carta : Mano){
            if (!Jugado){
                if (Carta.color.equals(Partida.getMonton().color) || Carta.nombre.equals(Partida.getMonton().nombre) || Carta.color.equals("Negro")){
                    System.out.println("\n"+Partida.getJugadores().get(Partida.getTurno()).getNombre()+" ha jugado la carta "+Carta.nombre+" "+Carta.color);
                    switch (Carta.nombre) {
                        case "Wildcard":
                            Partida.setMonton(Carta);
                            Mano.remove(Carta);
                            Partida.Monton.setColor(ElegirColorMaquina());
                            NextTurno(Partida);
                            break;
                        case "+4":
                            Partida.setBlock(true);
                            Partida.setMonton(Carta);
                            Mano.remove(Carta);
                            Partida.getJugadores().get(NextPerson(Partida)).RobarCarta(Partida.getBaraja(), 4);
                            System.out.println(Partida.getJugadores().get(NextPerson(Partida)).getNombre()+ " ha robado 4 cartas");
                            Partida.Monton.setColor(ElegirColorMaquina());
                            NextTurno(Partida);
                            break;
                        case "+2":
                            Partida.setBlock(true);
                            Partida.setMonton(Carta);
                            Mano.remove(Carta);
                            Partida.getJugadores().get(NextPerson(Partida)).RobarCarta(Partida.getBaraja(), 2);
                            System.out.println(Partida.getJugadores().get(NextPerson(Partida)).getNombre()+ " ha robado 2 cartas");
                            NextTurno(Partida);
                            break;
                        case "block":
                            Partida.setBlock(true);
                            Partida.setMonton(Carta);
                            Mano.remove(Carta);
                            NextTurno(Partida);
                            break;
                        case "reverse":
                            Partida.setReverse(true);
                            Partida.setMonton(Carta);
                            Mano.remove(Carta);
                            NextTurno(Partida);
                            break;
                        default:
                            Partida.setMonton(Carta);
                            Mano.remove(Carta);
                            NextTurno(Partida);
                            break;
                    }
                    Jugado = true;
                    break;
                }
            }
            else{
                break;
            }
        }
        if (!Jugado){
            RobarCarta(Partida.getBaraja(), 1);
            System.out.println("\n"+Partida.getJugadores().get(Partida.getTurno()).getNombre()+" ha robado carta y ha perdido su turno");
            NextTurno(Partida);
        }
    }

    @Override
    public String toString() {
        return "Maquina{" + "Nivel=" + Nivel + '}';
    }
}
