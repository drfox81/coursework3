package com.example.cours3dkokorin.services;

import com.example.cours3dkokorin.model.ColorSock;
import com.example.cours3dkokorin.model.Socks;
import com.example.cours3dkokorin.model.TypeOperation;

import java.nio.file.Path;
import java.util.ArrayList;

public interface ProductServices {
   int addProduct(Socks socks);

   String putAndDeleteProduct(Socks socks, TypeOperation typeOperation);

   //int getProductFromWarehouse(int id, int quantity);

   String getSockColorMaxCotton(ColorSock colorSock, int cottonPart);

   String getSockColorMinCotton(ColorSock colorSock, int cottonPart);

   int getProductAllOnWarehouse();

   void saveToFile();

   Path getAllSocks();
}
