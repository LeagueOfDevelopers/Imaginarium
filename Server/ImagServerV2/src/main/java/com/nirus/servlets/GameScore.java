package com.nirus.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nirus.api_params.GameParams;
import com.nirus.basics.JSONRequestParser;
import com.nirus.basics.Player;
import com.nirus.interfaces.IRoomManager;
import com.nirus.responses.ResponseGame;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by ndiezel on 26.02.2016.
 */
@Singleton
public class GameScore extends HttpServlet {
    @Inject
    public GameScore(IRoomManager roomManager) {
        this.roomManager = roomManager;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONRequestParser parser = new JSONRequestParser(request);
        GameParams params = new GameParams(new Player(UUID.fromString(parser.GetStringByKey("token"))));
        ResponseGame responseGame = roomManager.getGameScore(params);
        response.getWriter().append(responseGame.getResponse());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    IRoomManager roomManager;
}
