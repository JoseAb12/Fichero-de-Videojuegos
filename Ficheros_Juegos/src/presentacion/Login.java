package presentacion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Login {
    private String nombre, password;
    private static final String NOMBRE_ARCHIVO = "usuarios.dat";
    private static List<Login> usuarios = new ArrayList<>();

    public Login(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }


    public String getNombre() {
        return nombre;
    }


    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return nombre + "/" + password;
    }
    public static void agregarUsuariosIniciales(Scanner sc, int cantidad) {
        for (int i = 0; i < cantidad; i++) agregarUsuario(sc);
    }
    public static boolean deseaAgregar(Scanner sc) {
        System.out.println("¿Deseas añadir 3 usuarios? (S/N)");
        return sc.nextLine().equalsIgnoreCase("S");
    }
    public static boolean iniciarSesion(Scanner sc) {
        for (int i = 0; i < 3; i++) {
            System.out.print("Usuario: ");
            String nombre = sc.nextLine();
            System.out.print("Contraseña: ");
            String pass = sc.nextLine();
            if (usuarios.stream().anyMatch(u -> u.validarCredenciales(nombre, pass))) {
                System.out.println("Bienvenido " + nombre + "!");
                return true;
            }
            System.out.println("Credenciales incorrectas. Intento " + (i + 1) + " de 3.");
        }
        return false;
    }
    public boolean validarCredenciales(String nombre, String password) {
        return this.nombre.equals(nombre) && this.password.equals(password);
    }
    public static void agregarUsuario(Scanner sc) {
        System.out.print("Nuevo usuario: ");
        String nombre = sc.nextLine();
        if (usuarios.stream().noneMatch(u -> u.getNombre().equals(nombre))) {
            System.out.print("Contraseña: ");
            Login nuevoUsuario = new Login(nombre, sc.nextLine());
            usuarios.add(nuevoUsuario);
            guardarUsuario(nuevoUsuario);
            System.out.println("Usuario añadido.");
        } else {
            System.out.println("El usuario ya existe.");
        }
    }
    public static Login fromLinea(String linea) {
        String[] datos = linea.split("/");
        return new Login(datos[0], datos[1]);
    }


    public static void guardarUsuario(Login usuario) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            bw.write(usuario + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
    public static void cargarUsuarios() throws IOException {
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) archivo.createNewFile();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.lines().map(Login::fromLinea).forEach(usuarios::add);
        }
    }


}