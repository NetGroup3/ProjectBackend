package com.example.NetProjectBackend.service.mail;

import com.example.NetProjectBackend.dao.UserDao;
import com.example.NetProjectBackend.dao.VerifyDao;
import com.example.NetProjectBackend.models.EStatus;
import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.models.Verify;
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
public class MailImpl implements Mail{

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;
    private final UserDao userDao;
    private final VerifyDao verifyDao;

    private int type;
    private String password;
    private String email;
    private String link;
    private String code;

    public MailImpl(JavaMailSender emailSender, SpringTemplateEngine thymeleafTemplateEngine, UserDao userDao, VerifyDao verifyDao) {
        this.emailSender = emailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.userDao = userDao;
        this.verifyDao = verifyDao;
    }

    protected void sendMail() {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject(email);
            if (type == 1) {
                helper.setText(getBodyPassword(), true);
            } else {
                helper.setText(getBody(), true);
            }
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    protected String getBody() {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("link", link + code);
        return thymeleafTemplateEngine.process("mail-template.html", thymeleafContext);
    }

    protected String getBodyPassword() {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("link", link);
        thymeleafContext.setVariable("password", password);
        return thymeleafTemplateEngine.process("mail-template-password.html", thymeleafContext);
    }

    protected String md5Code (String string) {
        return DigestUtils.md5DigestAsHex(string.getBytes(StandardCharsets.UTF_8));
    }

    protected String getCode() {
        long calendar = Calendar.getInstance().getTimeInMillis();
        String part1 = md5Code(String.valueOf(calendar) + Math.random() * 1000);
        String part2 = md5Code(String.valueOf(email) + Math.random() * 1000);
        String part3 = md5Code(email + calendar + Math.random() * 1000);
        return part1 + part2 + part3;
    }

    @Override
    public void confirmationCode(String link, String email) {
        this.email = email;
        this.link = link;
        code = getCode();
        sendMail();
        codeDao();
    }


    private void codeDao () {
        int user_id = userDao.readByEmail(email).getId();
        verifyDao.delete(user_id);
        verifyDao.create(new Verify(user_id, code, OffsetDateTime.now()));

    }

    @Override
    public boolean recoveryCode(String link, String email) {
        User user = userDao.readByEmail(email);
        if (Objects.equals(user.getStatus(), EStatus.NOT_VERIFY.getAuthority())) {
            return false;
        }
        confirmationCode(link, email);
        return true;
    }

    @Override
    public boolean sendNewPassword(String link, String password, User user, Verify verify) {
        this.password = password;
        this.email = user.getEmail();
        this.link = link;
        if (!checkData(verify)) {
            confirmationCode(link, user.getEmail());
            return false;
        }
        type = 1;
        sendMail();
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
