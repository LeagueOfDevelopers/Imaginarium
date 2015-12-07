package com.nirus.servlets;

import com.google.inject.Singleton;
import com.nirus.parsers.JSONRequestParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ndiezel on 07.12.2015.
 */
@WebServlet(name = "TestServlet")
@Singleton
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String line;
        StringBuffer _bufferOfJson = new StringBuffer();
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            _bufferOfJson.append(line);
        }
        PrintWriter out = response.getWriter();
        out.append(_bufferOfJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
