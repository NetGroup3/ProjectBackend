package com.example.NetProjectBackend.service.impl;

import com.example.NetProjectBackend.confuguration.LinkConfig;
import com.example.NetProjectBackend.dao.UserDao;
import com.example.NetProjectBackend.dao.VerifyDao;
import com.example.NetProjectBackend.models.enums.EStatus;
import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.Verify;
import com.example.NetProjectBackend.service.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Objects;

@Service
@Slf4j
public class MailImpl implements Mail {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;
    private final UserDao userDao;
    private final VerifyDao verifyDao;
    private final LinkConfig l;

    public MailImpl(JavaMailSender emailSender, SpringTemplateEngine thymeleafTemplateEngine, UserDao userDao, VerifyDao verifyDao, LinkConfig l) {
        this.emailSender = emailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.userDao = userDao;
        this.verifyDao = verifyDao;
        this.l = l;
    }

    protected void sendMail(String email, int type, String code, String password) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject(email);
            if (type == 1) {
                helper.setText(getBodyPassword(password), true);
            } else {
                helper.setText(getBody(code), true);
            }
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    protected String getBody(String code) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("link", l.getUrl() + code);
        return thymeleafTemplateEngine.process("mail-template.html", thymeleafContext);
    }

    protected String getBodyPassword(String password) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("link", l.getUrl());
        thymeleafContext.setVariable("password", password);
        return thymeleafTemplateEngine.process("mail-template-password.html", thymeleafContext);
    }

    protected String md5Code (String string) {
        return DigestUtils.md5DigestAsHex(string.getBytes(StandardCharsets.UTF_8));
    }

    protected String getCode(String email) {
        long calendar = Calendar.getInstance().getTimeInMillis();
        String part1 = md5Code(String.valueOf(calendar) + Math.random() * 1000);
        String part2 = md5Code(String.valueOf(email) + Math.random() * 1000);
        String part3 = md5Code(email + calendar + Math.random() * 1000);
        return part1 + part2 + part3;
    }

    @Override
    public void confirmationCode(String email) {
        String code = getCode(email);
        sendMail(email, 0, code, "");
        int user_id = userDao.readByEmail(email).getId();
        verifyDao.delete(user_id);
        verifyDao.create(new Verify(user_id, code, OffsetDateTime.now()));
    }

    @Override
    public boolean recoveryCode(String email) {
        User user = userDao.readByEmail(email);
        if (Objects.equals(user.getStatus(), EStatus.NOT_VERIFY.getAuthority())) {
            return false;
        }
        confirmationCode(email);
        return true;
    }

    @Override
    public boolean sendNewPassword(String password, User user, Verify verify) {
        if (!checkData(verify)) {
            confirmationCode(user.getEmail());
            return false;
        }
        sendMail(user.getEmail(), 1, "", password);
        return true;
    }

    @Override
    public boolean sendModeratorPassword(String password, String email) {
        sendMail(email, 1, "", password);
        return true;
    }

    @Override
    public Verify readByCode(String code) {
        return verifyDao.readByCode(code);
    }

    @Override
    public void deleteCode(int userId) {
        verifyDao.delete(userId);
    }

    @Override
    public boolean checkData(Verify verify) {
        OffsetDateTime time = OffsetDateTime.now().plusDays(1);
        return time.toInstant().isAfter(verify.getTimestamp().toInstant());
    }
}
