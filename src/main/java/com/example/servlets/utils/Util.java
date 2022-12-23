package com.example.servlets.utils;

import com.example.servlets.model.Library;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class Util {
    public static int FIRST_REQUEST = 1;
    public static int SECOND_REQUEST = 2;
    private static Statement statment = null;
    private static Connection connection = null;

    public static void initDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (
                ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/my_db",
                    "postgres",
                    "timur_2022"
            );
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return connection;
    }

    public static void printResponseData(HttpServletResponse response, ResultSet resultSet, int selectReqParam) {
        PrintWriter pw = null;
        Library library = null;
        int count = 0;
        response.setContentType("text/html");
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (resultSet != null) {
            try {
                pw.println("<html><body>");
                pw.println("<table>");
                while (resultSet.next()) {
                    library = new Library(
                            resultSet.getString("id"),
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getString("quantity"));
                    count++;
                    pw.println("<tbody>");
                    pw.println("<tr>");
                    switch (selectReqParam) {
                        case 1:
                            pw.println("<td>");
                            pw.print(count);
                            pw.print(". ID: ");
                            pw.print(library.getId());
                            pw.print(" QUANTITY: ");
                            pw.print(library.getQuantity());
                            pw.println("</td>");
                            break;
                        case 2:
                            pw.println("<td>");
                            pw.print(count);
                            pw.print(". TITLE: ");
                            pw.print(library.getTitle());
                            pw.print(" AUTHOR: ");
                            pw.print(library.getAuthor());
                            pw.println("</td>");
                            break;
                    }
                    pw.println("</tr>");
                    pw.println("</tbody>");
                }
                pw.println("</table>");
                pw.println("<p>To return, click BACK<p/>");
                pw.println("</body></html>");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            pw.println("<html><body>");
            pw.println("<h1>" + "ResultSet is null..." + "</h1>");
            pw.println("<p>To return, click BACK<p/>");
            pw.println("</body></html>");
        }

        try {
            if (statment != null) {
                statment.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getData(String executeString) {// "select * from books"
        ResultSet resultSet = null;

        connection = getConnection();
        try {
            statment = connection.createStatement();
            resultSet = statment.executeQuery(executeString);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return resultSet;
    }
}
