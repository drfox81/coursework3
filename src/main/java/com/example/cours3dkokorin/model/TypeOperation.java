package com.example.cours3dkokorin.model;

public enum TypeOperation {
    ADD("Поступление на склад"),DELETE("Списание брака"),PUT("Отпуск со склада");
    private String name;

    TypeOperation(String name) {
        this.name = name;
    }
}
