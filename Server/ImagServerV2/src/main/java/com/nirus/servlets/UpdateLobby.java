package com.nirus.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nirus.api_params.LobbyParams;
import com.nirus.basics.JSONRequestParser;
import com.nirus.basics.Player;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.responses.ResponseLobby;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by ndiezel on 09.02.2016.
 */
@Singleton
public class UpdateLobby extends HttpServlet {
    @Inject
    public UpdateLobby(ILobbyManager lobby){
        this.lobby  = lobby;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONRequestParser parser = new JSONRequestParser(request);
        LobbyParams params = new LobbyParams(new Player(UUID.fromString(parser.GetStringByKey("token"))));
        ResponseLobby responseLobby = lobby.UpdateLobby(params);
        response.getWriter().append(responseLobby.getResponse());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    private ILobbyManager lobby;
}
