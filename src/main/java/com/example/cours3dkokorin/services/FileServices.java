package com.example.cours3dkokorin.services;

import java.io.File;
import java.nio.file.Path;

public interface FileServices {

        boolean saveToFile(String json);

        String readFromFile();

        boolean cleanDataFile();

        File getDataFile();

        Path creatTempFile(String suffix);
    }

