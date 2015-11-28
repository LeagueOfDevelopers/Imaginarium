package com.nirus.guice_confige;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.nirus.interfaces.ILobby;
import com.nirus.services.Lobby;
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
                bind(ILobby.class).to(Lobby.class);
            }
        });
    }
}
