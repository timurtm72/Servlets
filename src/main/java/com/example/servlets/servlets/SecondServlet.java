package com.example.servlets.servlets;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;

import com.example.servlets.utils.Util;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "secondServlet", value = "/second")
public class SecondServlet extends HttpServlet {
    public void init() {Util.initDriver();}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultSet resultSet = Util.getData("select * from books");
        Util.printResponseData(response, resultSet, Util.SECOND_REQUEST);
    }

    public void destroy() {
    }
}