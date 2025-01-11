package com.tcc.welfare.Welfare.repository;

public interface EmailSender {

    void send(String to, String email);

    void sendPassword(String to, String email);

}
