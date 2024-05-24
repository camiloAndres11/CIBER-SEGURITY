package co.edu.uptc.view;

import co.edu.uptc.controller.Controller;

import java.util.InputMismatchException;
import java.util.Scanner;

public class View {
    Scanner scanner = new Scanner(System.in);
    Controller loginController = new Controller();
    private static final String[] dominiosCorreos = {"@uptc.edu.co", "@gmail.com", "@outlook.com"};

    public void MenuPrincipal() {
        int desicion = 0;
        while (desicion != 3) {
            System.out.println("BIENVENIDO AL INICIO DE SESION DE LA CARRERA DE SISTEMAS");
            System.out.println("""
                    ╔════════════════════════════════════════╗
                    ║                                        ║
                    ║           1. LOGUEARSE                 ║
                    ║                                        ║
                    ║           2. REGISTRARSE               ║
                    ║                                        ║
                    ║           3. SALIR                     ║
                    ║                                        ║
                    ╚════════════════════════════════════════╝
                                       """);
            desicion = scanner.nextInt();
            scanner.nextLine();
            switch (desicion) {
                case 1:
                    loguearseMenu();
                    break;
                case 2:
                    if (registrarseMenu()==true){
                        System.out.println("welcome to uptc");
                    }

                    break;
                case 3:
                    break;
                default:
                    System.out.println("digite una opcion valida");
                    break;
            }
        }
    }

    public void loguearseMenu() {
        try {

            System.out.println("Ingrese su nombre de usuario:");
            String nombreUsuario = scanner.nextLine();

            System.out.println("Ingrese su contraseña:");
            String contraseña = scanner.nextLine();

            // Validar si los campos están vacíos
            if (nombreUsuario.isEmpty() || contraseña.isEmpty()) {
                throw new IllegalArgumentException("El nombre de usuario y la contraseña no pueden estar vacíos.");
            }

            // Validar las credenciales
            if (loginController.validarCredenciales(nombreUsuario, contraseña)) {
                System.out.println("Bienvenido, " + nombreUsuario + ".");
                ////aca metemos el homeeeeeee
                //
                //   HOMEEEE
                ///
                //
            } else {
                System.out.println("Inicio de sesión fallido. Verifique sus credenciales.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese datos válidos para el nombre de usuario y la contraseña.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    public boolean registrarseMenu() {
        try {
            // Verificar si el nombre de usuario ya está en uso
            String usuario = "";
            while (true) {
                System.out.println("Ingrese el id de usuario:");
                usuario = scanner.nextLine().trim();
                if (usuario.isEmpty()) {
                    System.out.println("No puede estar vacío");
                } else if (loginController.verificarUsuario(usuario.toLowerCase())) {
                    System.out.println("El nombre de usuario ya está en uso. Por favor, ingrese otro.");
                } else {
                    break;
                }
            }

            // Solicitar y verificar la contraseña
            String contraseña = "";
            while (true) {
                System.out.println("Ingrese la contraseña (Mínimo 8 caracteres, una mayúscula, una minúscula,al menos 2 numeros y un caracter especial (.,*)):");
                contraseña = scanner.nextLine().trim();
                if (!loginController.verificarContraseña(contraseña)) {
                    System.out.println("La contraseña no cumple con los requisitos mínimos.");
                } else {
                    break;
                }
            }

            // Solicitar el email sin la extensión
            String email = "";
            while (true) {
                System.out.println("Ingrese el email (sin la extensión):");
                email = scanner.nextLine().trim();
                if (email.isEmpty()) {
                    System.out.println("No puede estar vacío");
                } else {
                    break;
                }
            }

            // Seleccionar la extensión de correo electrónico
            int opcionEmail = 0;
            boolean entradaValida = false;
            while (!entradaValida) {
                System.out.println("Seleccione la extensión de su email:");
                for (int i = 0; i < dominiosCorreos.length; i++) {
                    System.out.printf("%d. %s%n", i + 1, dominiosCorreos[i]);
                }
                if (scanner.hasNextInt()) {
                    opcionEmail = scanner.nextInt();
                    if (opcionEmail >= 1 && opcionEmail <= dominiosCorreos.length) {
                        entradaValida = true;
                    } else {
                        System.out.println("Ingresó un valor fuera de rango.");
                    }
                } else {
                    System.out.println("Ingresó un valor no válido. Por favor, ingrese un número.");
                    scanner.next(); // Limpiar el buffer de entrada
                }
            }

            scanner.nextLine(); // Limpiar el buffer de entrada

            email += dominiosCorreos[opcionEmail - 1];
            System.out.println("Su email completo es: " + email);

            // Llamar al método registrarUsuario con todos los datos ingresados
            int num = (int) (1000 + Math.random() * 9000);
            String idInterno = usuario + num;
            loginController.registrarUsuario(
                    email,
                    usuario,
                    idInterno,
                    contraseña);
            System.out.println("CUENTA CREADA CON EXITO!!!");
            return true;
        } catch (InputMismatchException e) {
            System.out.println("Ingresaste un valor erroneo");
            scanner.next(); // Limpiar el buffer de entrada
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
}

