package com.example.cours3dkokorin.services;

import org.springframework.http.HttpStatus;

public class SockErrorAddSock extends Exception{
    public SockErrorAddSock() {
        System.out.println(HttpStatus.BAD_REQUEST);
    }

    public String getMessage() {
        return "Что ж такое!";
    }
}
