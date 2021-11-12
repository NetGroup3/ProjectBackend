package com.example.NetProjectBackend.services.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailImpl implements Mail{

    private final JavaMailSender emailSender;
    final SpringTemplateEngine thymeleafTemplateEngine;

    public MailImpl(JavaMailSender emailSender, SpringTemplateEngine thymeleafTemplateEngine) {
        this.emailSender = emailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }

    @Override
    public void sendCode(String link, String code, String email) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject(email);
            helper.setText(getBody(link + code), true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    protected String getBody(String link) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("link", link);
        return thymeleafTemplateEngine.process("mail-template.html", thymeleafContext);
    }
}
