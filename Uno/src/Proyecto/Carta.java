package Proyecto;

/**
 *
 * @author bemen3
 */
public class Carta {
    String nombre;
    String color;

    public Carta(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Carta{" + "nombre=" + nombre + ", color=" + color + '}';
    }
}
