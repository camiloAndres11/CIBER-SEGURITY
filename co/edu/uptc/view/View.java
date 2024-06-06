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
            System.out.println("BIENVENIDO AL INICIO DE SESIÓN DE LA CARRERA DE SISTEMAS");
            System.out.println("""
                    ╔════════════════════════════════════════╗
                    ║                                        ║
                    ║           1. INICIAR SESIÓN            ║
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
                scanner.nextLine(); // Limpiar el buffer

                switch (decision) {
                    case 1:
                        loguearseMenu();
                        break;
                    case 2:
                        registrarseMenu();
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
            String nombreUsuario = scanner.nextLine().trim();

            System.out.println("Ingrese su contraseña:");
            String contraseña = scanner.nextLine().trim();

            if (nombreUsuario.isEmpty() || contraseña.isEmpty()) {
                throw new IllegalArgumentException("El nombre de usuario y la contraseña no pueden estar vacíos.");
            }

            if (loginController.validarCredenciales(nombreUsuario, contraseña)) {
                System.out.println("Bienvenido, " + nombreUsuario + ".");
                while (!homeUptc()) {
                    // Aquí puedes agregar el código para el home
                }
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
                System.out.println("Ingrese el ID de usuario:");
                usuario = scanner.nextLine().trim();
                if (usuario.isEmpty()) {
                    System.out.println("El ID de usuario no puede estar vacío.");
                } else if (loginController.verificarUsuario(usuario.toLowerCase())) {
                    System.out.println("El nombre de usuario ya está en uso. Por favor, ingrese otro.");
                } else {
                    break;
                }
            }

            String contraseña = "";
            while (true) {
                System.out.println("Ingrese la contraseña (mínimo 8 caracteres, una mayúscula, una minúscula, al menos 2 números y un carácter especial (.,*)):");
                contraseña = scanner.nextLine().trim();
                if (!loginController.verificarContraseña(contraseña)) {
                    System.out.println("La contraseña no cumple con los requisitos mínimos.");
                } else {
                    break;
                }
            }

            String telefono = "";
            while (true) {
                System.out.println("Ingrese su número de teléfono:");
                telefono = scanner.nextLine().trim();
                if (telefono.isEmpty()) {
                    System.out.println("El número de teléfono no puede estar vacío.");
                } else {
                    break;
                }
            }

            String nombre = "";
            while (true) {
                System.out.println("Ingrese su nombre:");
                nombre = scanner.nextLine().trim();
                if (nombre.isEmpty()) {
                    System.out.println("El nombre no puede estar vacío.");
                } else {
                    break;
                }
            }

            String apellido = "";
            while (true) {
                System.out.println("Ingrese sus apellidos:");
                apellido = scanner.nextLine().trim();
                if (apellido.isEmpty()) {
                    System.out.println("Los apellidos no pueden estar vacíos.");
                } else {
                    break;
                }
            }

            int num = (int) (1000 + Math.random() * 9000);
            String idInterno = usuario + num;
            String email = nombre.substring(0, 4) + num + "@uptc.edu.co";
            System.out.println("Su correo electrónico es: " + email);

            loginController.registrarUsuario(email, usuario, idInterno, contraseña, telefono, nombre, apellido);
            System.out.println("¡CUENTA CREADA CON ÉXITO!");
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
        try {
            boolean correoValido = false;

            do {
                System.out.println("Ingrese su correo electrónico:");
                String email = scanner.nextLine().trim();

                if (loginController.verificarCorreoExistente(email)) {
                    boolean codigoCorrecto = false;

                    do {
                        System.out.println("Se ha enviado un código de verificación a su correo electrónico.");
                        int codigoVerificacion = generarCodigoVerificacion();
                        System.out.println(codigoVerificacion); // Eliminar esta línea en producción, se muestra para pruebas

                        boolean codigoIngresadoCorrecto = false;
                        while (!codigoIngresadoCorrecto) {
                            try {
                                System.out.println("Ingrese el código de verificación:");
                                int ingresoCodigo = scanner.nextInt();
                                scanner.nextLine();

                                if (codigoVerificacion == ingresoCodigo) {
                                    codigoCorrecto = true;
                                    codigoIngresadoCorrecto = true;
                                    System.out.println("Código de verificación correcto. Ingrese una nueva contraseña:");

                                    boolean contraseñaValida = false;
                                    do {
                                        String nuevaContraseña = scanner.nextLine();
                                        if (loginController.verificarContraseña(nuevaContraseña)) {
                                            System.out.println("Confirme la nueva contraseña:");
                                            String confirmacionContraseña = scanner.nextLine();

                                            if (nuevaContraseña.equals(confirmacionContraseña)) {
                                                loginController.actualizarContraseña(email, nuevaContraseña);
                                                System.out.println("Contraseña actualizada exitosamente.");
                                                contraseñaValida = true;
                                            } else {
                                                System.out.println("Las contraseñas no coinciden. Inténtelo de nuevo.");
                                            }
                                        } else {
                                            System.out.println("La contraseña no cumple con los requisitos mínimos. Inténtelo de nuevo:");
                                        }
                                    } while (!contraseñaValida);

                                } else {
                                    System.out.println("Código de verificación incorrecto. Por favor, inténtelo de nuevo.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Ingrese un número válido para el código de verificación.");
                                scanner.next(); // Limpia el buffer para evitar un bucle infinito
                            }
                        }
                    } while (!codigoCorrecto);

                    correoValido = true;

                } else {
                    System.out.println("El correo electrónico no está registrado. Por favor, inténtelo de nuevo.");
                }
            } while (!correoValido);

        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }







    private int generarCodigoVerificacion() {
        return (int) (100000 + Math.random() * 900000);
    }

    public boolean homeUptc() {
        try {
            int aux = 0;
            System.out.println("""
                    ╔════════════════════════════════════════╗
                    ║                                        ║
                    ║    BIENVENIDO A LA ESCUELA DE SISTEMAS ║
                    ║                 UPTC                   ║
                    ║                                        ║
                    ╚════════════════════════════════════════╝
                    """);
            System.out.println("Si quieres cerrar sesión, presiona 1");
            aux = scanner.nextInt();
            if (aux == 1) {
                return true;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.next();
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
        return false;
    }
}
