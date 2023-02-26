package com.example.cours3dkokorin.services.impl;

import com.example.cours3dkokorin.services.FileServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServicesImpl implements FileServices {

    @Value("${path.to.date.file}")
    private String dataFilePath;

    @Value("${name.of.data.file}")
    private String dataFileName;

    @Override
    public boolean saveToFile(String json) {
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath,dataFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean cleanDataFile() {
        Path path = Path.of(dataFilePath, dataFileName);
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public File getDataFile() {
        return  new File(dataFilePath +"/"+dataFileName);
    }

    @Override
    public Path creatTempFile(String suffix) {
        try {
            return Files.createTempFile(Path.of(dataFilePath),"tempFile",suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
