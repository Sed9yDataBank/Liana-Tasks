package com.kanbanedchain.lianatasks.Services.Implementations;

import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Models.PassCode;
import com.kanbanedchain.lianatasks.Repositories.PassCodeRepository;
import com.kanbanedchain.lianatasks.Services.InvitationService;
import com.kanbanedchain.lianatasks.Utils.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class InvitationImplementation implements InvitationService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    PassCodeRepository passCodeRepository;

    @Override
    @Transactional
    public String generatePassCodeForBoard(UUID boardId) {
        String code = AuthenticationUtil.OTP(8);
        PassCode passCode = new PassCode();
        passCode.setCode(code);
        passCode.setPassId(boardId);
        passCodeRepository.save(passCode);
        return code;
    }

    @Override
    @Transactional
    public void sendPassCode(String email, String passCode, Board board) {
        String text = "You Have Been Invited To Join Board: " +
                board.getTitle()+ " Use PassCode Given Below: " + passCode;
        String subject = "Invitation to Join Board";
        try {
            sendMail(email, text, subject);
        } catch (MessagingException e) {
        }
    }

    @Override
    @Transactional
    public void sendMail(String to, String text, String subject) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
        } catch (MessagingException e) {
            e.printStackTrace();
            return;
        }
        mailSender.send(message);
    }
}

