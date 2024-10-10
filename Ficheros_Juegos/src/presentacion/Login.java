package presentacion;
public class Login {
    private String nombre, password;
    private static final String NOMBRE_ARCHIVO = "usuarios.dat";
    private static List<Persona> usuarios = new ArrayList<>();

    public Persona(String nombre, String password) {
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
            Persona nuevoUsuario = new Persona(nombre, sc.nextLine());
            usuarios.add(nuevoUsuario);
            guardarUsuario(nuevoUsuario);
            System.out.println("Usuario añadido.");
        } else {
            System.out.println("El usuario ya existe.");
        }
    }
    public static Persona fromLinea(String linea) {
        String[] datos = linea.split("/");
        return new Persona(datos[0], datos[1]);
    }


    public static void guardarUsuario(Persona usuario) {
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
            br.lines().map(Persona::fromLinea).forEach(usuarios::add);
        }
    }


}