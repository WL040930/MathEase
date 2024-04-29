package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Model.User;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendVerificationEmail(User user) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(user.getEmail());
            helper.setSubject("Account Activation");

            Context context = new Context();
            context.setVariable("username", user.getUsername());
            context.setVariable("activationLink", "http://localhost:8080/activate?token=" + user.getActivationToken());

            String emailContent = templateEngine.process("verification-email", context);
            helper.setText(emailContent, true);

            mailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
