package co.edu.uptc.controller;

import co.edu.uptc.model.Model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private ArrayList<Model> cuentasEstudiantes;
    public Controller(){
        cuentasEstudiantes=new ArrayList<>();

    }
    public boolean verificarContraseña(String contraseña) {
        // Longitud mínima de 8 caracteres
        if (contraseña.length() < 8) {
            return false;
        }

        // Al menos una minúscula
        if (!contraseña.matches(".*[a-z].*")) {
            return false;
        }

        // Al menos una mayúscula
        if (!contraseña.matches(".*[A-Z].*")) {
            return false;
        }

        // Al menos un carácter especial
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(contraseña);
        if (!matcher.find()) {
            return false;
        }

        // Al menos 5 números
        int countNumbers = 0;
        for (char c : contraseña.toCharArray()) {
            if (Character.isDigit(c)) {
                countNumbers++;
            }
        }
        if (countNumbers < 2) {
            return false;
        }

        // La contraseña cumple con todos los requisitos
        return true;
    }

    public void registrarUsuario(
            String email,
            String idInterno,
            String username,
            String contraseña

    ) throws Exception {
        // Verificar si la contraseña cumple con los requisitos mínimos
        if (!verificarContraseña(contraseña)) {
            throw new Exception("La contraseña no cumple con los requisitos mínimos.");
        }



        // Crear un nuevo usuario y agregarlo a la lista de usuarios
        Model model = new Model(
                email,
                idInterno,
                username,
                idInterno
        );
        cuentasEstudiantes.add(model); // Agregar el nuevo usuario a la lista de usuarios
    }

    public boolean verificarUsuario(String userName) {
        for (Model model : cuentasEstudiantes) {
            if (model.getUserName().equals(userName)) {
                return true; // El usuario existe
            }
        }
        return false; // El usuario no existe
    }
    public boolean validarCredenciales(String nombreUsuario, String contraseña) {
        // Buscar la persona por nombre
        for (Model persona : cuentasEstudiantes) {
            if (persona.getUserName().equals(nombreUsuario) && persona.getContraseña().equals(contraseña)) {
                return true;
            }
        }
        return false; // Si el nombre no existe, retorno falso
    }

}
