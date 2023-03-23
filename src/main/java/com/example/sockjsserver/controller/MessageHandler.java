package com.example.sockjsserver.controller;

import com.example.sockjsserver.model.Listener;
import com.example.sockjsserver.model.Message;
import com.example.sockjsserver.utils.Participants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Controller
public class MessageHandler extends TextWebSocketHandler {
    @Autowired
    Participants participants;
    private static final ObjectMapper mapper = new ObjectMapper();

    public final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        Message messageObject = convertToObjectMessage(message.getPayload());
        System.out.println("-----------RECEIVED MESSAGE-----------\n"
                + messageObject.toString()
                + "\nfrom: " + session.getId()
                + "\n--------------------------------------");
        handleSendMessage(messageObject);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        Listener newMember = participants.addNewMember(new Listener("", session));
        logger.info("Established session: " + session.getId());
        logger.info("Total sessions: " + Participants.listeners.size());

        // send session id to client
        Message message = new Message("SERVER", session.getId(), Message.MESSAGE_TYPE_INITIAL, "");
        handleSendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        Listener leaveMember = participants.removeMember(new Listener("", session));
        logger.info("Closed session: " + session.getId());
        logger.info("Total sessions: " + Participants.listeners.size());
    }

    private Message convertToObjectMessage(String rawMessage) throws JsonProcessingException {
        return mapper.readValue(rawMessage, Message.class);
    }

    private void handleSendMessage(Message message) throws IOException {
        String sessionId = message.to;
        WebSocketSession session = participants.getListenerBySessionId(sessionId).getSession();
        session.sendMessage(new TextMessage(message.toJSON()));
    }
}
