package com.example.cours3dkokorin.services;

public class SockErrorAddSock extends Exception{
    public SockErrorAddSock() {
        System.out.println("Че то пошло не так");
    }

    public String getMessage() {
        return "Что ж такое!";
    }
}
