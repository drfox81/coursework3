package com.example.cours3dkokorin.services.impl;

import com.example.cours3dkokorin.model.ColorSock;
import com.example.cours3dkokorin.model.Socks;
import com.example.cours3dkokorin.model.TypeOperation;
import com.example.cours3dkokorin.services.FileServices;
import com.example.cours3dkokorin.services.ProductServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashMap;

@Service
public class SocksServicesImpl implements ProductServices {

    private final FileServices fileServices;
    private static int id;

    public SocksServicesImpl(FileServices fileServices) {
        this.fileServices = fileServices;
    }

    public static int getId() {
        return id;
    }

    private static HashSet<Socks> sockMap = new HashSet<>();

    public static HashSet<Socks> getSockMap() {
        return sockMap;
    }

    @Override
    public int addProduct(Socks socks) {
        for (Socks value : sockMap) {
            if (value.equals(socks)) {
                value.setQuantity(value.getQuantity() + socks.getQuantity());
                value.getTime().add(TypeOperation.ADD.name() + " || количество поступивших носков = "
                        + socks.getQuantity() + "|| " + LocalDateTime.now());
                saveToFile();
                return id;
            }
        }
        socks.getTime().set(0,TypeOperation.ADD.name() + " || количество поступивших носков = "
                + socks.getQuantity() + " || " + LocalDateTime.now());
        sockMap.add(socks);
        saveToFile();
        return id;
    }

    @Override
    public String putAndDeleteProduct(Socks socks, TypeOperation typeOperation) {
        for (Socks value : sockMap) {
            if (value.equals(socks)) {
                if (socks.getQuantity() <= value.getQuantity()) {
                    value.setQuantity(value.getQuantity() - socks.getQuantity());
                    if (typeOperation.equals(TypeOperation.PUT)) {
                        value.getTime().add(TypeOperation.PUT.name() + " || количество отпущенных носков = "
                                + socks.getQuantity() + " || " +
                                LocalDateTime.now());
                        saveToFile();
                        return "отпущено со склада " + socks.getQuantity() + ". Осталось на складе - " + value.getQuantity();
                    } else if (typeOperation.equals(TypeOperation.DELETE)) {
                        value.getTime().add(TypeOperation.DELETE.name() + " || количество списаных носков = "
                                + socks.getQuantity() + " || " +
                                LocalDateTime.now());
                        saveToFile();
                        return "списано со склада " + socks.getQuantity() + ". Осталось на складе - " + value.getQuantity();
                    } else return " проверь операцию ";
                } else return "нет такого количества на складе";
            } else
                return "таких носков нет";
        }

        return "таких носков нет, давай в другой раз";
    }

    @Override
    public String getSockColorMaxCotton(ColorSock colorSock, int cottonPart) {
        int number = 0;
        for (Socks socks : sockMap) {
            if (socks.getColor().equals(colorSock) & socks.getCottonPart() >= cottonPart) {
                number += socks.getQuantity();
            }
        }
        return colorSock.toString() + " хлопка больше "
                + cottonPart + " на складе " + number;
    }

    @Override
    public String getSockColorMinCotton(ColorSock colorSock, int cottonPart) {
        int numb = 0;
        for (Socks socks : sockMap) {
            if (socks.getColor().equals(colorSock) & socks.getCottonPart() < cottonPart) {
                numb += socks.getQuantity();
            }
        }
        return colorSock.toString() + " хлопка меньше "
                + cottonPart + " на складе " + numb;
    }

    @Override
    public int getProductAllOnWarehouse() {
        return 0;
    }

    @Override
    public void saveToFile() {
        try {
            SaveData saveData = new SaveData(sockMap);
            String json = new ObjectMapper().writeValueAsString(saveData);
            fileServices.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() throws JsonProcessingException {
        String json = fileServices.readFromFile();
        SaveData saveData = new ObjectMapper().readValue(json, new TypeReference<SaveData>() {
        });
        sockMap = saveData.getSockMap();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class SaveData {
        private HashSet<Socks> sockMap;

    }

    @PostConstruct
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Path getAllSocks() {
        Path path = fileServices.creatTempFile("tempSoack");
        for (Socks value : sockMap) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append("Цвет-" + value.getColor().name() + "\n\n");
                writer.append("Размер - " + value.getSize() + "\n\n");
                writer.append("Количество хлопка - " + value.getCottonPart() + "\n\n");
                writer.append("Количество носков на складе - " + value.getQuantity() + "\n\n");
                writer.append("Дата, время, тип операции:\n");
                for (int i = 0; i < value.getTime().size(); i++) {
                    //writer.append(value.getTime().get(i) + "\n");
                    writer.append(i + 1 + ".  " + value.getTime().get(i) + "\n");
                }
                writer.append("-------------------------------------------------------\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return path;
    }

}
