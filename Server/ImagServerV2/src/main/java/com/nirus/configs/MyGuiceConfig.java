package com.nirus.configs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.services.LobbyManager;
import com.nirus.servlets.JoinLobby;

/**
 * Created by ndiezel on 25.01.2016.
 */
public class MyGuiceConfig extends GuiceServletContextListener{
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule(){
            @Override
            protected void configureServlets(){
                serve("/JoinLobby").with(JoinLobby.class);
                bind(ILobbyManager.class).to(LobbyManager.class);
            }
        });
    }
}
