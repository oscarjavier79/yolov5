package com.pytorch.yolv5.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public enum Utils {

    INSTANCE;

    public Path getPath(String path) throws IOException {
        try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(path)){
            Path temp = Files.createTempFile("mask_yolov5", "pt");
            assert is != null;
            Files.copy(is, temp, StandardCopyOption.REPLACE_EXISTING);
            return temp;
        }
    }
}
