package com.example.sockjsserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    public final static String MESSAGE_TYPE_NORMAL = "0";
    public final static String MESSAGE_TYPE_SPECIAL = "1";
    public final static String MESSAGE_TYPE_INITIAL = "2";

    @JsonProperty("from")
    public String from;
    @JsonProperty("to")
    public String to;
    @JsonProperty("type")
    public String type;
    @JsonProperty("content")
    public String content;

    public Message() {
    }

    public Message(String from, String to, String type, String content) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.content = content;
    }

    @Override
    public String toString() {
        return "from: " + from + "\nto: " + to + "\ntype: " + type + "\ncontent: " + content;
    }

    public String toJSON() {
        return "{" +
                "\"from\":\"" + from + "\"," +
                "\"to\":\"" + to + "\"," +
                "\"type\":\"" + type + "\"," +
                "\"content\":\"" + content + "\"" +
                "}";
    }
}
