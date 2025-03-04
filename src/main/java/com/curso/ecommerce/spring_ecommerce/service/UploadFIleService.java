package com.curso.ecommerce.spring_ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFIleService {

    private String folder = "images//";

 // Método para guardar una imagen en el sistema de archivos
public String saveImage(MultipartFile file) throws IOException {
    // Verifica si el archivo no está vacío
    if(!file.isEmpty()){
        // Obtiene los bytes del archivo recibido
        byte[] bytes = file.getBytes();
        // Define la ruta donde se guardará el archivo, concatenando la carpeta con el nombre original del archivo
        Path path = Paths.get(folder + file.getOriginalFilename());
        // Escribe el archivo en la ruta especificada
        Files.write(path, bytes);
        // Retorna el nombre original del archivo guardado
        return file.getOriginalFilename();
    }
    // Si el archivo está vacío, retorna un nombre de archivo por defecto
    return "default.jpg";
}

// Método para eliminar una imagen del sistema de archivos
public void delete(String nombre) {
    // Define la ruta de la carpeta donde se encuentran las imágenes
    String ruta = "images//";
    // Crea un objeto File con la ruta y el nombre del archivo a eliminar
    File file = new File(ruta + nombre);
    // Elimina el archivo de la ruta especificada
    file.delete();
}

}
