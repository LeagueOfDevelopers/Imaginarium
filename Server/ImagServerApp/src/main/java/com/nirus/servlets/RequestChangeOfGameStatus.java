package com.nirus.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nirus.containers.RequestForAChange;
import com.nirus.containers.ResponseForAChange;
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
 * Created by ndiezel on 05.12.2015.
 */
@WebServlet(name = "RequestChangeOfGameStatus")
@Singleton
public class RequestChangeOfGameStatus extends HttpServlet {
    @Inject
    public RequestChangeOfGameStatus(IRoomHandler roomHandler) {
        _roomHandler = roomHandler;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONRequestParser parser = new JSONRequestParser(request);

        logger.debug(parser.GetStringBuffer() + "\nsession id: " + request.getSession().getId());
        RequestForAChange requestForAChange = new RequestForAChange(parser.GetJSONObject());
        UUID token = UUID.fromString(parser.GetStringByKey("token"));
        ResponseForAChange responseForAChange = _roomHandler.ChangeRoomStatus(token, requestForAChange);
        PrintWriter out = response.getWriter();
        out.append(responseForAChange.GetAsJson().toString());
        logger.debug(responseForAChange.GetAsJson().toString() + "\nsession id: " + request.getSession().getId());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    private IRoomHandler _roomHandler;
    private Logger logger = LogManager.getLogger(RequestChangeOfGameStatus.class);
}
