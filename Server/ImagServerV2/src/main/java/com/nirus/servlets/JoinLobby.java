package com.nirus.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nirus.interfaces.ILobbyManager;

import java.io.IOException;

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

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
    private ILobbyManager lobby;
}
