package com.nirus.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nirus.containers.Player;
import com.nirus.containers.ResponseForLobby;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.parsers.JSONRequestParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * Created by ndiezel on 28.11.2015.
 */
@WebServlet(name = "JoinLobby")
@Singleton
public class JoinLobby extends HttpServlet {
    @Inject
    public JoinLobby(ILobbyManager lobby) {
        _lobby=lobby;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ResponseForLobby responseForLobby = _lobby.JoinLobby();
        out.append(responseForLobby.GetStatus());
        out.println();
        out.append(responseForLobby.GetToken().toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    ILobbyManager _lobby;
}
