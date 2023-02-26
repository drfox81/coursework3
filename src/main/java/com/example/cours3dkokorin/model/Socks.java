package com.example.cours3dkokorin.model;

import com.example.cours3dkokorin.services.SockError;
import com.example.cours3dkokorin.services.SockErrorAddSock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
public class Socks {

    private ColorSock color;
    private Size size;
    private int cottonPart;

    private int quantity;

    private ArrayList<String> time = new ArrayList<>();

    public Socks(ColorSock color, Size size, int cottonPart, int quantity) {
        this.color = color;
        this.size = size;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
        time.add(LocalDateTime.now().toString());
    }


    //    public int getCottonPart() {
//        try {
//            if (cottonPart < 0 || cottonPart > 100) {
//                throw new SockError();
//            } else {
//                return cottonPart;
//            }
//        } catch (SockError e) {
//             e.printStackTrace();
//        }
//        return 0;
//    }

    public int getQuantity() {
        try {
            if (quantity < 0) {
                throw new SockErrorAddSock();
            } else {
                return quantity;
            }
        } catch (SockErrorAddSock e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return quantity;
    }

    @Override
    public String toString() {
        return "Socks{" +
                "color=" + color +
                ", size=" + size +
                ", cottonPart=" + cottonPart +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return cottonPart == socks.cottonPart && color == socks.color && size == socks.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, size, cottonPart);
    }
}
