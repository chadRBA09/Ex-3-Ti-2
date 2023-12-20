package com.exercicio3.controller;

import static spark.Spark.*;
import com.exercicio3.service.LivrosService;
public class LivrosController {
    public static void setupRoutes() {
        get("/get", (request, response) -> LivrosService.get());

        get("/get/:id", (request, response) -> LivrosService.getById(request.params("id")));

        post("/create", (request, response) -> LivrosService.create(request));

        post("/update", (request, response) -> LivrosService.update(request));

        post("/delete", (request, response) -> LivrosService.delete(request));

        notFound((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Página não encontrada\"}";
        });
    }
}
