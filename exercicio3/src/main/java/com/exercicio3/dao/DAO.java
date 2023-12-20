package com.exercicio3.dao;

import java.sql.*;
import java.util.Objects;
import java.util.Properties;

abstract public class DAO {
    private Connection connection;
    private final Properties props = new Properties();

    DAO() {
        this.props.setProperty("user", "postgres");
        this.props.setProperty("password", "1234");
        this.props.setProperty("ssl", "false");
    }

    protected void makeConnection() throws SQLException {
        try {
            String url = "jdbc:postgresql://localhost/livros";
            connection = DriverManager.getConnection(url, this.props);
        }
        catch (Exception e) {
            System.err.println("makeConnection - Exception: " + e.getMessage());
            throw e;
        }
    }

    protected void closeConnection() throws SQLException {
        try {
            connection.close();
        }
        catch (Exception e){
            System.err.println("closeConnection - Exception: " + e.getMessage());
            throw e;
        }
    }

    protected Statement returnStatement() throws SQLException {
        return this.connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }

    protected PreparedStatement returnPreparedStatement(String query) throws SQLException {
        return this.connection.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }

    public abstract Object getAll() throws SQLException;
    public abstract Object getById(String id) throws SQLException;
    public abstract Object deleteById(String id) throws SQLException;
    public abstract Object updateById(String id, Object o) throws SQLException;
    public abstract Object insert(Object o) throws SQLException;
}
