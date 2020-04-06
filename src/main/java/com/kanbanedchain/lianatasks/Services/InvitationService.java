package com.kanbanedchain.lianatasks.Services;

import com.kanbanedchain.lianatasks.Models.Board;

import javax.mail.MessagingException;
import java.util.UUID;

public interface InvitationService {

    public String generatePassCodeForBoard(Long id);

    public String sendPassCode(String email, String passCode, Board board);

    public boolean sendMail(String to, String text, String subject) throws MessagingException;
}
