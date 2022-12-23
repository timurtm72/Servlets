package com.example.servlets;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class Util {

    public static PrintWriter initPrWriter(HttpServletResponse response){
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pw;
    }
    public static void initDriver(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (
                ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getData(String executeString) {// "select * from books"
        ResultSet resultSet;

        initDriver();
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/my_db",
                    "postgres",
                    "timur_2022"
            );
            Statement statment = connection.createStatement();
            resultSet = statment.executeQuery(executeString);
//            while (resultSet.next()) {
//                pw.println(resultSet.getString("title") + " " + resultSet.getString("author")
//                        + " " + resultSet.getString("quantity"));
//            }
            statment.close();
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
}
