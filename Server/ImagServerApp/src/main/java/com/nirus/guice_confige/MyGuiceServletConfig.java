package com.nirus.guice_confige;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.services.LobbyManager;
import com.nirus.servlets.JoinLobby;
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
                bind(ILobbyManager.class).to(LobbyManager.class);
            }
        });
    }
}
