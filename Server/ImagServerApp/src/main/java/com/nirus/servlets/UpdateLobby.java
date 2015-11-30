package com.nirus.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
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
@WebServlet(name = "UpdateLobby")
@Singleton
public class UpdateLobby extends HttpServlet {
    @Inject
    public UpdateLobby(ILobbyManager lobby) {
        _lobby = lobby;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        JSONRequestParser parser = new JSONRequestParser(request);
        UUID token = UUID.fromString(parser.GetStringByKey("token"));
        ResponseForLobby responseForLobby = _lobby.UpdateLobby(token);
        out.append(responseForLobby.GetStatus());
        out.println();
        out.append(token.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    ILobbyManager _lobby;
}
