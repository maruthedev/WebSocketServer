package com.example.sockjsserver.model;

import org.springframework.web.socket.WebSocketSession;

public class Listener {
    private String alias;
    private WebSocketSession session;

    public Listener() {
    }

    public Listener(String alias, WebSocketSession session) {
        this.alias = alias;
        this.session = session;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }
}
