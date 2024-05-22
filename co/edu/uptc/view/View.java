package co.edu.uptc.view;

import java.util.Scanner;

public class View {
    Scanner sc = new Scanner(System.in);
    public void MenuPrincipal(){
        int desicion=0;
        while (desicion!=3){
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
            desicion=sc.nextInt();
            sc.nextLine();
            switch (desicion){
                case 1:
                    loguearseMenu();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("digite una opcion valida");
                    break;
            }
        }
    }
    public void loguearseMenu(){
        int desicion=0;
        String usuario;
        String contraseña;
        System.out.println("Escriba su usuario");
        usuario=sc.nextLine();
        System.out.println("");
        System.out.println("Escriba su contraseña");
        contraseña=sc.nextLine();

    }
    public void registrarseMenu(){

    }
}
