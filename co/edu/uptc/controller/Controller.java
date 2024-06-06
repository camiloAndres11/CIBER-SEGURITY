package co.edu.uptc.controller;

import co.edu.uptc.model.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private ArrayList<Model> cuentasEstudiantes;
    private static final String FILE_PATH = "Usuarios.json";


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
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
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
        Model model = new Model(email, contraseña, username, idInterno, telefono, nombre, apellido);
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
}
