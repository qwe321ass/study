package com.example.study;

public class App {

    public String greet(String name) {
        return "Hello, " + name + "!";
    }

    public static void main(String[] args) {
        String name = args.length > 0 ? args[0] : "World";
        System.out.println(new App().greet(name));
    }
}
