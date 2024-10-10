package Negocio;

import datos.Datos;

public class Negocio {

    public String agregarVideojuego(String nombre, String compania, int nota) {
        if (nombre == null || nombre.length() < 3) {
            return "El nombre debe tener al menos 3 caracteres.";
        }
        if (compania == null || compania.length() < 5) {
            return "La compañía debe tener al menos 5 caracteres.";
        }
        if (nota < 0 || nota > 100) {
            return "La nota debe estar entre 0 y 100.";
        }

        Datos datos = new Datos();
        return datos.guardarVideojuego(nombre, compania, nota);
    }

    public void listarVideojuegos() {
        Datos datos = new Datos();
        datos.mostrarVideojuegos();
    }

    public String borrarVideojuego(String nombre) {
        Datos datos = new Datos();
        return datos.eliminarVideojuego(nombre);
    }
}
