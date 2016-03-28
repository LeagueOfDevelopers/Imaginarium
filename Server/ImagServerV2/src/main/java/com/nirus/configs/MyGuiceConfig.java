package com.nirus.configs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.nirus.interfaces.ILobbyManager;
import com.nirus.interfaces.IRoomManager;
import com.nirus.services.LobbyManager;
import com.nirus.services.RoomManager;
import com.nirus.servlets.*;

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
                serve("/UpdateLobby").with(UpdateLobby.class);
                serve("/LeaveLobby").with(LeaveLobby.class);
                serve("/GetGameStatus").with(GetStatus.class);
                serve("/SetGameStatus").with(UpdateStatus.class);
                serve("/Test").with(Test.class);
                serve("/GetGameScore").with(GameScore.class);
                serve("/LeaveGame").with(LeaveGame.class);
                bind(ILobbyManager.class).to(LobbyManager.class);
                bind(IRoomManager.class).to(RoomManager.class);
            }
        });
    }
}
