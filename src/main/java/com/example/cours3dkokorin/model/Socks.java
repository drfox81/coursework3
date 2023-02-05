package com.example.cours3dkokorin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socks {

    private ColorSock color;
    private Size size;

    private int structure;

    private int remains;

    public Socks(ColorSock color, Size size, int structure, int remains) {
        this.color = color;
        this.size = size;
        this.structure = structure;
        this.remains = remains;
    }

    public int getStructure() {
        if (structure<0 || structure>100){

        }
        return structure;
    }
}
