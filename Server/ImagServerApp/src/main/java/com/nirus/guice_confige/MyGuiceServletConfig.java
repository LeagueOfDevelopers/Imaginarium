package com.nirus.guice_confige;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.interfaces.IRoomHandler;
import com.nirus.services.LobbyManager;
import com.nirus.services.RoomHandler;
import com.nirus.servlets.JoinLobby;
import com.nirus.servlets.UpdateGameStatus;
import com.nirus.servlets.UpdateLobby;

/**
 * Created by ndiezel on 28.11.2015.
 */

public class MyGuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule(){
            @Override
            protected void configureServlets() {
                serve("/JoinLobby").with(JoinLobby.class);
                serve("/UpdateLobby").with(UpdateLobby.class);
                serve("/UpdateGameStatus").with(UpdateGameStatus.class);
                bind(ILobbyManager.class).to(LobbyManager.class);
                bind(IRoomHandler.class).to(RoomHandler.class);
            }
        });
    }
}
