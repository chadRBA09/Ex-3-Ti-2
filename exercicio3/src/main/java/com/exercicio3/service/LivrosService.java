package com.exercicio3.service;

import com.exercicio3.dao.LivroDAO;
import com.exercicio3.dto.LivroDTO;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class LivrosService {
    public static String get() throws SQLException {
        LivroDAO livroDAO = new LivroDAO();

        ArrayList<LivroDTO> books = livroDAO.getAll();

        return new VelocityTemplateEngine().render(
                new ModelAndView(returnBooks(books), "/public/views/item_view.vm")
        );
    }

    public static String getById(String id) throws SQLException {
        LivroDAO livroDAO = new LivroDAO();

        LivroDTO livro = livroDAO.getById(id);

        ArrayList<LivroDTO> books = new ArrayList<>();

        books.add(livro);

        return new VelocityTemplateEngine().render(
                new ModelAndView(returnBooks(books), "/public/views/item_view.vm")
        );
    }

    public static String create(Request request) throws SQLException {
        LivroDTO livro = parseRequestFormToBookDTO(request);

        LivroDAO livroDAO = new LivroDAO();

        livroDAO.insert(livro);

        ArrayList<LivroDTO> books = new ArrayList<>();

        books.add(livro);

        return new VelocityTemplateEngine().render(
                new ModelAndView(returnBooks(books), "/public/views/item_view.vm")
        );
    }

    public static String update(Request request) throws SQLException {
        LivroDTO livro = parseRequestFormToBookDTO(request);

        LivroDAO livroDAO = new LivroDAO();

        livroDAO.updateById(String.valueOf(livro.getId()), livro);

        ArrayList<LivroDTO> books = new ArrayList<>();

        books.add(livro);

        return new VelocityTemplateEngine().render(
                new ModelAndView(returnBooks(books), "/public/views/item_view.vm")
        );
    }

    public static String delete(Request request) throws SQLException {
        LivroDTO livro = parseRequestFormToBookDTO(request);

        LivroDAO livroDAO = new LivroDAO();

        livro = livroDAO.deleteById(String.valueOf(livro.getId()));

        ArrayList<LivroDTO> books = new ArrayList<>();

        books.add(livro);

        return new VelocityTemplateEngine().render(
                new ModelAndView(returnBooks(books), "/public/views/item_view.vm")
        );
    }

    private static LivroDTO parseRequestFormToBookDTO (Request request) {
        LivroDTO livro = new LivroDTO();

        livro.setAutor(request.queryParams("autor"));
        livro.setNome(request.queryParams("nome"));
        livro.setIsbn(request.queryParams("isbn"));

        if (Objects.nonNull(request.queryParams("dataPublicacao"))) {
            livro.setDataPublicacao(Date.valueOf(request.queryParams("dataPublicacao")));
        }

        if (Objects.nonNull(request.queryParams("id"))) {
            livro.setId(Integer.parseInt(request.queryParams("id")));
        }

        return livro;
    }

    private static HashMap<String, Object> returnBooks(ArrayList<LivroDTO> books) {
        HashMap<String, Object> h = new HashMap<>();

        h.put("books", books);

        return h;
    }
}
