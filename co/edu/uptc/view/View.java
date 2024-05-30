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
                    if (registrarseMenu()) {
                        System.out.println("welcome to uptc");
                    }
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Digite una opción válida.");
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

            if (nombreUsuario.isEmpty() || contraseña.isEmpty()) {
                throw new IllegalArgumentException("El nombre de usuario y la contraseña no pueden estar vacíos.");
            }

            if (loginController.validarCredenciales(nombreUsuario, contraseña)) {
                System.out.println("Bienvenido, " + nombreUsuario + ".");
                // Aquí puedes agregar el código para el home
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

            String contraseña = "";
            while (true) {
                System.out.println("Ingrese la contraseña (Mínimo 8 caracteres, una mayúscula, una minúscula, al menos 2 números y un caracter especial (.,*)):");
                contraseña = scanner.nextLine().trim();
                if (!loginController.verificarContraseña(contraseña)) {
                    System.out.println("La contraseña no cumple con los requisitos mínimos.");
                } else {
                    break;
                }
            }

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
                    scanner.next();
                }
            }

            scanner.nextLine(); // Limpiar el buffer de entrada

            email += dominiosCorreos[opcionEmail - 1];
            System.out.println("Su email completo es: " + email);

            int num = (int) (1000 + Math.random() * 9000);
            String idInterno = usuario + num;
            loginController.registrarUsuario(email, usuario, idInterno, contraseña);
            System.out.println("CUENTA CREADA CON ÉXITO!!!");
            return true;
        } catch (InputMismatchException e) {
            System.out.println("Ingresaste un valor erróneo.");
            scanner.next();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
}
