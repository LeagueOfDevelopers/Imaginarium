package com.nirus.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nirus.api_params.GameParams;
import com.nirus.api_params.LobbyParams;
import com.nirus.basics.JSONRequestParser;
import com.nirus.basics.Player;
import com.nirus.interfaces.IRoomManager;
import com.nirus.responses.ResponseGame;
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
public class GetStatus extends HttpServlet {
    @Inject
    public GetStatus(IRoomManager roomManager){
        this.roomManager = roomManager;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONRequestParser parser = new JSONRequestParser(request);
        GameParams params = new GameParams(new Player(UUID.fromString(parser.GetStringByKey("token"))));
        try {
            ResponseGame responseGame = roomManager.getGameStatus(params);
            response.getWriter().append(responseGame.getResponse());
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().append("{\"status\":\"ERROR\"");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    private IRoomManager roomManager;
}
