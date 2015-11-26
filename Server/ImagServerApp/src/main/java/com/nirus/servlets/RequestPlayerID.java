package com.nirus.servlets;

import com.nirus.parsers.JSONRequestParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ndiezel on 26.11.2015.
 */
@WebServlet(name = "RequestPlayerID")
public class RequestPlayerID extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONRequestParser parser = new JSONRequestParser(request);
        PrintWriter out = response.getWriter();
        out.append(parser.GetStringByKey("login") + " hello");
        out.println();
        out.append(parser.GetStringByKey("password"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
