package es.iesnervion.bluetoohcar;

/**
 * Created by Migue-w10 on 23/01/2017.
 */

public class Dispositivo {
    private String Nombre;
    private String Direccion;

    public Dispositivo(String nombre ,String direccion){
        this.Direccion = direccion;
        this.Nombre = nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String toString(){
        return (getNombre() + "\n" + getDireccion());
    }
}
