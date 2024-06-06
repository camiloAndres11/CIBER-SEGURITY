package co.edu.uptc.view;

import co.edu.uptc.controller.Controller;

import java.util.InputMismatchException;
import java.util.Scanner;

public class View {
    Scanner scanner = new Scanner(System.in);
    Controller loginController = new Controller();



    public void MenuPrincipal() {
        int decision = 0;
        while (decision != 4) {
            System.out.println("BIENVENIDO AL INICIO DE SESION DE LA CARRERA DE SISTEMAS");
            System.out.println("""
                    ╔════════════════════════════════════════╗
                    ║                                        ║
                    ║           1. LOGUEARSE                 ║
                    ║                                        ║
                    ║           2. REGISTRARSE               ║
                    ║                                        ║
                    ║           3. RECUPERAR CONTRASEÑA      ║
                    ║                                        ║
                    ║           4. SALIR                     ║
                    ║                                        ║
                    ╚════════════════════════════════════════╝
                    """);

            try {
                decision = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer

                switch (decision) {
                    case 1:
                        loguearseMenu();
                        break;
                    case 2:
                        if (registrarseMenu()) {
                            System.out.println("Welcome to UPTC");
                        }
                        break;
                    case 3:
                        recuperarContraseña();
                        break;
                    case 4:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Digite una opción válida.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.next();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
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

            String telefono = "";
            while (true) {
                System.out.println("Ingrese su numero de telefono:");
                telefono = scanner.nextLine().trim();
                if (telefono.isEmpty()) {
                    System.out.println("No puede estar vacío");
                } else {
                    break;
                }
            }

            String nombre = "";
            while (true) {
                System.out.println("Ingrese su nombre:");
                nombre = scanner.nextLine().trim();
                if (nombre.isEmpty()) {
                    System.out.println("No puede estar vacío");
                } else {
                    break;
                }
            }

            String apellido = "";
            while (true) {
                System.out.println("Ingrese sus apellidos:");
                apellido = scanner.nextLine().trim();
                if (apellido.isEmpty()) {
                    System.out.println("No puede estar vacío");
                } else {
                    break;
                }
            }

            int num = (int) (1000 + Math.random() * 9000);
            String idInterno = usuario + num;
            String email = nombre + num + "@uptc.edu.co";
            System.out.println("Su email completo es: " + email);

            loginController.registrarUsuario(email, usuario, idInterno, contraseña, telefono, nombre, apellido);
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

    public void recuperarContraseña() {
        Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller();
    
        System.out.println("Ingrese su correo electrónico:");
        String email = scanner.nextLine().trim();
    
        if (controller.verificarCorreoExistente(email)) {
            System.out.println("Se ha enviado un código de verificación a su correo electrónico.");
            int codigoVerificacion = generarCodigoVerificacion();
            System.out.println(codigoVerificacion);
            System.out.println("Ingrese el código de verificación:");
            int ingresoCodigo = scanner.nextInt();
            scanner.nextLine(); 
    
            if (codigoVerificacion == ingresoCodigo) {
                System.out.println("Código de verificación correcto. Ingrese una nueva contraseña:");
                String nuevaContraseña = scanner.nextLine();
    
                if (controller.verificarContraseña(nuevaContraseña)) {
                    System.out.println("Confirme la nueva contraseña:");
                    String confirmacionContraseña = scanner.nextLine();
    
                    if (nuevaContraseña.equals(confirmacionContraseña)) {
                        controller.actualizarContraseña(email, nuevaContraseña);
                        System.out.println("Contraseña actualizada exitosamente.");
                    } else {
                        System.out.println("Las contraseñas no coinciden.");
                    }
                } else {
                    System.out.println("La contraseña no cumple con los requisitos mínimos.");
                }
            } else {
                System.out.println("Código de verificación incorrecto.");
            }
        } else {
            System.out.println("El correo electrónico no está registrado.");
        }
    }
    
    private int generarCodigoVerificacion() {
        return (int) (100000 + Math.random() * 900000);
    }
}

