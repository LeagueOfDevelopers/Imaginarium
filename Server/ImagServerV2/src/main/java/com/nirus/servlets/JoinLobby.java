package com.nirus.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nirus.api_params.LobbyParams;
import com.nirus.basics.JSONRequestParser;
import com.nirus.basics.Player;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.responses.ResponseLobby;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by ndiezel on 25.01.2016.
 */
@Singleton
public class JoinLobby extends javax.servlet.http.HttpServlet {
    @Inject
    public JoinLobby(ILobbyManager lobby){
        this.lobby  = lobby;
    }
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        JSONRequestParser parser = new JSONRequestParser(request);
        LobbyParams params = new LobbyParams(new Player(UUID.randomUUID()));
        try {
            params.setLobbyMaxSize(Integer.parseInt(parser.GetStringByKey("size")));
        }
        catch (Exception e){
            params.setLobbyMaxSize(6);
        }
        ResponseLobby responseLobby = lobby.JoinLobby(params);
        logger.debug(responseLobby);
        response.getWriter().append(responseLobby.getResponse());
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
    private ILobbyManager lobby;
    private org.apache.logging.log4j.Logger logger = LogManager.getLogger();
}
