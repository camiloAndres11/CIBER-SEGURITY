package co.edu.uptc.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import co.edu.uptc.model.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonFile {
    
    public static void initializeJsonFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(path)) {
                // Escribimos un array vacío para inicializar el archivo JSON
                writer.write("[]");
                System.out.println("Archivo Usuarios.json creado correctamente.");
            }
        }
    }

    public static List<Model> readFromJson(String path) throws IOException {
        initializeJsonFile(path); // Asegurarse de que el archivo exista
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            Gson gson = new Gson();
            Model[] modelArray = gson.fromJson(br, Model[].class);
            return Arrays.asList(modelArray);
        }
    }

    public static void writeToJson(List<Model> models, String path) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(models, writer);
        }
    }
}
