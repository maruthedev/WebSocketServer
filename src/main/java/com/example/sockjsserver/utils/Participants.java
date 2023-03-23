package com.example.sockjsserver.utils;

import com.example.sockjsserver.model.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Participants {
    private final Logger logger = LoggerFactory.getLogger(Participants.class);
    public static List<Listener> listeners;

    public Participants() {
        listeners = new ArrayList<Listener>();
    }

    public Listener addNewMember(Listener listener) {
        try {
            if (!listeners.contains(listener)) {
                listeners.add(listener);
                return listener;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("A member with this websocket session already exists");
        }
        return null;
    }

    public Listener removeMember(Listener listener) {
        try {
            listeners.removeIf(l -> l.getSession().getId().equals(listener.getSession().getId()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Participants has no members like this");
        }
        return null;
    }

    public Listener getListenerBySessionId(String webSocketSessionId) {
        try {
            for (Listener listener : listeners) {
                if (listener.getSession().getId().equals(webSocketSessionId)) return listener;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Participants has no members like this");
        }
        return null;
    }
}
