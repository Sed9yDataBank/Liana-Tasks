package com.kanbanedchain.lianatasks.Services;

import com.kanbanedchain.lianatasks.Models.Board;

import javax.mail.MessagingException;
import java.util.UUID;

public interface InvitationService {

    String generatePassCodeForBoard(UUID boardId);

    void sendPassCode(String email, String passCode, Board board);

    void sendMail(String to, String text, String subject) throws MessagingException;
}
