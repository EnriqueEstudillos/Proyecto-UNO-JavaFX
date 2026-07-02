package Proyecto;

/**
 *
 * @author bemen3
 */
public class Persona extends Jugador{
    int Edad;
    
    public Persona(String Nombre) {
        super(Nombre);
    }

    public Persona(int Edad, String Nombre) {
        super(Nombre);
        this.Edad = Edad;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int Edad) {
        this.Edad = Edad;
    }
    
    @Override
    public void TurnoJugador(Partida Partida){
        // Actualizar turno en lista jugadores
        // Actualizar cartas jugador
        MostrarMano();
        if (Partida.getMonton().color.equals("Negro")){
            Partida.getMonton().setColor(Partida.ObtenerColor());
        }
        System.out.println("\nLa carta del monton es: "+Partida.MostarCartaMonton());
        while (true){
            System.out.println("\nQue quieres hacer?");
            System.out.println("1.) Jugar Carta");
            System.out.println("2.) Robar Carta");
            switch (sc.nextInt()) {
                case 1:
                    while (true){
                        System.out.println("\nLa carta del monton es: "+Partida.MostarCartaMonton());
                        System.out.println("Que carta quieres jugar: ");
                        MostrarMano();
                        int opcion = sc.nextInt();
                        if (Mano.get(opcion).color.equals(Partida.getMonton().color) || Mano.get(opcion).nombre.equals(Partida.getMonton().nombre) || Mano.get(opcion).color.equals("Negro")){
                            System.out.println("\nPlayer: Ha jugado la carta "+Mano.get(opcion).nombre+" "+Mano.get(opcion).color);
                            switch (Mano.get(opcion).nombre) {
                                case "Wildcard":
                                    Partida.setMonton(Mano.remove(opcion));
                                    Partida.Monton.setColor(ElegirColor());
                                    NextTurno(Partida);
                                    break;
                                case "+4":
                                    Partida.setBlock(true);
                                    Partida.setMonton(Mano.remove(opcion));
                                    Partida.Monton.setColor(ElegirColor());
                                    Partida.getJugadores().get(NextPerson(Partida)).RobarCarta(Partida.getBaraja(), 4);
                                    System.out.println(Partida.getJugadores().get(NextPerson(Partida)).getNombre()+ " ha robado 4 cartas");
                                    NextTurno(Partida);
                                    break;
                                case "+2":
                                    Partida.setBlock(true);
                                    Partida.setMonton(Mano.remove(opcion));
                                    Partida.getJugadores().get(NextPerson(Partida)).RobarCarta(Partida.getBaraja(), 2);
                                    System.out.println(Partida.getJugadores().get(NextPerson(Partida)).getNombre()+ " ha robado 2 cartas");
                                    NextTurno(Partida);
                                    break;
                                case "block":
                                    Partida.setBlock(true);
                                    Partida.setMonton(Mano.remove(opcion));
                                    NextTurno(Partida);
                                    break;
                                case "reverse":
                                    Partida.setReverse(true);
                                    Partida.setMonton(Mano.remove(opcion));
                                    NextTurno(Partida);
                                    break;
                                default:
                                    Partida.setMonton(Mano.remove(opcion));
                                    NextTurno(Partida);
                                    break;
                            }
                            break;
                        }
                        else{
                            System.out.println("No cumple las condiciones");
                        }
                    }
                    break;
                case 2:
                    RobarCarta(Partida.getBaraja(), 1);
                    System.out.println("Has robado la carta '"+Mano.get(Mano.size()-1).getNombre() + " "+Mano.get(Mano.size()-1).getColor() + "' y has perdido tu turno.");
                    NextTurno(Partida);
                    break;
                default:
                    throw new AssertionError();
            }
            break;
        }
    }

    @Override
    public String toString() {
        return "Persona{" + "Edad=" + Edad + '}';
    }
}
