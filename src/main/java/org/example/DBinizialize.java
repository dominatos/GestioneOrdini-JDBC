package org.example;

import java.sql.*;

public class DBinizialize {

    private String url = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String pass = "root1701";
    private String db_name = "gestione_ordini_jpa";
    private Statement st;
    private Connection conn;
    private boolean dropDb=false;
    public DBinizialize(boolean dropDb) throws SQLException {
        this.dropDb = dropDb;
        conn = DriverManager.getConnection(url, user, pass);
        st = conn.createStatement();
        System.out.println("DB Connesso!!");
        if ( this.dropDb) {
            dropDatabase();
        }

        createDatabase();

    }
    public DBinizialize() throws SQLException {
        conn = DriverManager.getConnection(url, user, pass);
        st = conn.createStatement();
        System.out.println("DB Connesso!!");
//        if (dropDb) {
//            dropDatabase();
//        }

        createDatabase();

    }

    public void createDatabase() throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS " + this.db_name;
        st.executeUpdate(sql);
        System.out.println("DB " + this.db_name + " e creato!!!");
        conn = DriverManager.getConnection(url+db_name, user, pass);
        st = conn.createStatement();
    }
    public void dropDatabase() throws SQLException {
        String sql = "DROP DATABASE IF EXISTS " + this.db_name;
        st.execute(sql);
        System.out.println("DB " + this.db_name + " e eliminato!!");

    }



























}
