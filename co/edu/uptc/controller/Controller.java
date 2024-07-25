package co.edu.uptc.controller;

import co.edu.uptc.model.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private ArrayList<Model> cuentasEstudiantes;
    private static final String FILE_PATH = "co\\edu\\uptc\\persistence\\Usuarios.json";


    public Controller() {
        try {
            cuentasEstudiantes = new ArrayList<>(JsonFile.readFromJson(FILE_PATH));
        } catch (IOException e) {
            cuentasEstudiantes = new ArrayList<>();
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
    }

    public boolean verificarContraseña(String contraseña) {
        if (contraseña.length() < 8) {
            return false;
        }
        if (!contraseña.matches(".*[a-z].*")) {
            return false;
        }
        if (!contraseña.matches(".*[A-Z].*")) {
            return false;
        }
        Pattern pattern = Pattern.compile("[^a-zA-Z0-10]");
        Matcher matcher = pattern.matcher(contraseña);
        if (!matcher.find()) {
            return false;
        }
        int countNumbers = 0;
        for (char c : contraseña.toCharArray()) {
            if (Character.isDigit(c)) {
                countNumbers++;
            }
        }
        return countNumbers >= 2;
    }

    public void registrarUsuario(String email, String idInterno, String username, String contraseña, String telefono, String nombre, String apellido) throws Exception {
        if (!verificarContraseña(contraseña)) {
            throw new Exception("La contraseña no cumple con los requisitos mínimos.");
        }
        Model model = new Model(email, contraseña, idInterno, username,telefono, nombre, apellido);
        cuentasEstudiantes.add(model);
        JsonFile.writeToJson(cuentasEstudiantes, FILE_PATH);
    }

    public boolean verificarUsuario(String userName) {
        for (Model model : cuentasEstudiantes) {
            if (model.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public boolean validarCredenciales(String nombreUsuario, String contraseña) {
        for (Model persona : cuentasEstudiantes) {
            if (persona.getUserName().equals(nombreUsuario) && persona.getContraseña().equals(contraseña)) {
                return true;
            }
        }
        return false;
    }

    public boolean validarNombres(String firstName, String lastName) {
        if(firstName.matches("[a-zA-Z]+") && lastName.matches("[a-zA-Z]+"))  {
               return true;
        }
        return false;
    }

    public String crearCorreo(String correoIngresado) throws IOException  {

        String[] correo=correoIngresado.split("@");
        String primeraParte=correo[0];
        int numero=0;
        String valorNuevoCorreo =correoIngresado;
        ArrayList<Model> cuentasEstudiantes = new ArrayList<>(JsonFile.readFromJson(FILE_PATH));
        for(Model model:cuentasEstudiantes) {
            String correoExistente=model.getCorreoElectronico();
            String [] valor=correoExistente.split("@");
            String correoExistenteString=valor[0];
            Pattern patternString=Pattern.compile("([a-zA-Z]+)\\d*");
            Matcher matcherString=patternString.matcher(correoExistenteString);
            String stringCorreo=null;
            if(matcherString.find())    {
                 stringCorreo=matcherString.group(1);
            }

            Pattern patternStringIngreso=Pattern.compile("([a-zA-Z]+)\\d*");
            Matcher matcherStringIngreso=patternStringIngreso.matcher(valorNuevoCorreo);
            String stringCorreoIngreso=null;
            if(matcherStringIngreso.find())    {
                 stringCorreoIngreso=matcherStringIngreso.group(1);
            }
           
            if (stringCorreoIngreso.equals(stringCorreo)) {
                Pattern pattern=Pattern.compile("\\d+");
                Matcher matcher=pattern.matcher(correoExistenteString);
                if(matcher.find())  {
                    String num=matcher.group();
                    numero=Integer.parseInt(num) + 1;

                   valorNuevoCorreo= primeraParte + numero + "@uptc.edu.co";
                }else   {
                    valorNuevoCorreo=primeraParte + "1" + "@uptc.edu.co";
                }
            }else   {
            valorNuevoCorreo=correoIngresado;
            }

        }


        return valorNuevoCorreo;
        
    }
    

    public void actualizarContraseña(String email, String nuevaContraseña) {
        for (Model model : cuentasEstudiantes) {
            if (model.getCorreoElectronico().equals(email)) {
                model.setContraseña(nuevaContraseña);
                try {
                    JsonFile.writeToJson(cuentasEstudiantes, FILE_PATH);
                } catch (IOException e) {
                    System.out.println("Error al actualizar la contraseña: " + e.getMessage());
                }
                break;
            }
        }
    }

    public boolean verificarCorreoExistente(String email) {
        for (Model model : cuentasEstudiantes) {
            if (model.getCorreoElectronico().equals(email)) {
                return true;
            }
        }
        return false;
    }
}

