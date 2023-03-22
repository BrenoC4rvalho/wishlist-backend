package com.breno.wishlist.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImgService {

    private static String basePath = "C:\\Users\\Samsung\\3D Objects\\wishlist-frontend\\public\\img\\";
    private String img;
    public void setImg(MultipartFile file, String wish) {

        try {
            if(!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(basePath+wish+".jpg");
                Files.write(path, bytes);
                this.img = wish+".jpg";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getImg() {
        return img;
    }

    public void delete(String img) throws IOException {
        Path path = Paths.get( basePath+img);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
