package com.ecommerce.springecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadFileService {
    //ruta donde se va guardar la imagen
    private String folder="images//";
    //objeto de tipo MultipartFile, que es la imagen para ser guardada
    public String saveImage(MultipartFile file) throws IOException {
        //condicion para saber si file trae algo y la pasamos bytes para que pueda enviarse del cliente al servidor
        if(!file.isEmpty()) {
            //transforma la imagen en bytes
            byte  [] bytes = file.getBytes();
            //la ubicacion donde se guarda la imagen en este caso pasamos la varibale folder que tiene la ubi
            Path path = Paths.get(folder+file.getOriginalFilename());
            Files.write(path, bytes);
            //retomamos el nombre de la imagen
            return file.getOriginalFilename();
        }
        //cunado no suve el usuario la imagen nosotros vamos agregar un por defecto
        return "default.jpg";
    }
    public void deleteImage(String nombre) {
        String ruta="images//";
        //crea una variable de tipo File, le pasamos donde se encuentra la imagen y su nombre
        File file = new File(ruta+nombre);
        file.delete();

    }
}
