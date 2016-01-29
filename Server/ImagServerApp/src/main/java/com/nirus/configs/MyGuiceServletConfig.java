package com.nirus.configs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.interfaces.IRoomHandler;
import com.nirus.services.LobbyManager;
import com.nirus.services.RoomHandler;
import com.nirus.servlets.*;

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
                serve("/GameStatus").with(RequestGameStatus.class);
                serve("/ChangeGameStatus").with(RequestChangeOfGameStatus.class);
                serve("/Test").with(TestServlet.class);
                bind(ILobbyManager.class).to(LobbyManager.class);
                bind(IRoomHandler.class).to(RoomHandler.class);
            }
        });
    }
}
