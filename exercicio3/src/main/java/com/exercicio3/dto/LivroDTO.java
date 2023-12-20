package com.exercicio3.dto;

import lombok.Data;
import java.sql.Date;

@Data
public class LivroDTO {
    private int id;
    private String autor;
    private String nome;
    private Date dataPublicacao;
    private String isbn;
}