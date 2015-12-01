package com.nirus.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nirus.containers.ResponseForGameUpdate;
import com.nirus.interfaces.IRoomHandler;
import com.nirus.parsers.JSONRequestParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * Created by ndiezel on 01.12.2015.
 */
@WebServlet(name = "UpdateGameStatus")
@Singleton
public class UpdateGameStatus extends HttpServlet {
    @Inject
    public UpdateGameStatus(IRoomHandler roomHandler) {
        _roomHandler = roomHandler;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONRequestParser parser = new JSONRequestParser(request);
        logger.debug(request.getReader());
        PrintWriter out = response.getWriter();
        UUID token = UUID.fromString(parser.GetStringByKey("token"));
        ResponseForGameUpdate responseForGameUpdate = _roomHandler.RoomStatus(token);
        out.append(responseForGameUpdate.GetAsJSON().toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    IRoomHandler _roomHandler;
    private Logger logger = LogManager.getFormatterLogger();
}
