package com.exercicio3.app;

import static spark.Spark.*;

import com.exercicio3.controller.LivrosController;

public class ExercicioApp {

    public static void main(String[] args) {
        port(8080);

        staticFiles.location("/public");

        LivrosController.setupRoutes();
    }
}
