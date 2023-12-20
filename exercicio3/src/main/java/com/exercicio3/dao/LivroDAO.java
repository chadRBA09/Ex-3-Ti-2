package com.exercicio3.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import com.exercicio3.dto.LivroDTO;

public class LivroDAO extends DAO{
    public LivroDAO() {
        super();
    }

    @Override
    public ArrayList<LivroDTO> getAll() throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM LIVROS");

        ArrayList<LivroDTO> books = new ArrayList<>();

       while (resultSet.next()) {
           books.add(parseRowToDTO(resultSet));
       }

        closeConnection();
        resultSet.close();

        return books;
    }

    @Override
    public LivroDTO getById(String id) throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM LIVROS WHERE id=" + id);

        LivroDTO book = null;

        if (resultSet.next()) {
            book = parseRowToDTO(resultSet);
        }

        closeConnection();
        resultSet.close();

        return book;
    }

    @Override
    public LivroDTO updateById(String id, Object o) throws SQLException {
        makeConnection();

        LivroDTO livro = (LivroDTO) o;

        PreparedStatement statement = this.returnPreparedStatement("UPDATE LIVROS SET autor=?, nome=?, dataPublicacao=?, isbn=? WHERE id=?");

        statement.setString(1, livro.getAutor());
        statement.setString(2, livro.getNome());
        statement.setDate(3, livro.getDataPublicacao());
        statement.setString(4, livro.getIsbn());
        statement.setInt(5, livro.getId());

        statement.executeUpdate();

        LivroDTO updated = getById(id);

        closeConnection();

        return updated;
    }
    
    @Override
    public LivroDTO deleteById(String id) throws SQLException {
        LivroDTO livro = getById(id);

        makeConnection();

        PreparedStatement statement = this.returnPreparedStatement("DELETE FROM LIVROS WHERE id=?");

        statement.setInt(1, Integer.parseInt(id));

        statement.executeUpdate();

        statement.close();
        closeConnection();

        return livro;
    }

    @Override
    public LivroDTO insert(Object o) throws SQLException {
        makeConnection();

        LivroDTO livro = (LivroDTO) o;

        PreparedStatement statement = this.returnPreparedStatement("INSERT INTO LIVROS(autor, nome, dataPublicacao, isbn) VALUES(?, ?, ?, ?)");

        statement.setString(1, livro.getAutor());
        statement.setString(2, livro.getNome());
        statement.setDate(3, livro.getDataPublicacao());
        statement.setString(4, livro.getIsbn());

        statement.executeUpdate();

        closeConnection();

        return livro;
    }

    private LivroDTO parseRowToDTO(ResultSet resultSet) throws SQLException {
        LivroDTO livroDTO = new LivroDTO();
        try {
            livroDTO.setId(resultSet.getInt("id"));
            livroDTO.setAutor(resultSet.getString("autor"));
            livroDTO.setIsbn(resultSet.getString("isbn"));
            livroDTO.setNome(resultSet.getString("nome"));
            livroDTO.setDataPublicacao(resultSet.getDate("dataPublicacao"));
        }

        catch (Exception e) {
            System.err.println("parseRowToDTO - Exception: " + e.getMessage());
            return null;
        }

        return livroDTO;
    }
}
