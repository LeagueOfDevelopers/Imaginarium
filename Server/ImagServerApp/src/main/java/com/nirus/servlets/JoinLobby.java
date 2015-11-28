package com.nirus.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nirus.containers.Player;
import com.nirus.interfaces.ILobby;
import com.nirus.parsers.JSONRequestParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ndiezel on 28.11.2015.
 */
@WebServlet(name = "JoinLobby")
@Singleton
public class JoinLobby extends HttpServlet {
    @Inject
    public JoinLobby(ILobby lobby) {
        _lobby=lobby;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONRequestParser parser = new JSONRequestParser(request);
        PrintWriter out = response.getWriter();
        out.append(_lobby.JoinLobby(new Player(parser.GetStringByKey("name"))).GetStatus());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    ILobby _lobby;
}
