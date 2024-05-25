package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Model.User;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    // Send verification email to user
    public void sendVerificationEmail(User user) {
        // Create a new MimeMessage
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            // Set the email address of the recipient
            helper.setTo(user.getEmail());
            helper.setSubject("Account Activation");

            // Create a new context and set the variables
            Context context = new Context();
            context.setVariable("username", user.getUsername());
            context.setVariable("activationLink", "http://localhost:8080/activate?token=" + user.getActivationToken());

            // Process the email template and set the email content
            String emailContent = templateEngine.process("email/verification-email", context);
            helper.setText(emailContent, true);

            // Send the email
            mailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // Send forget password email to user
    public void sendForgetPassword(User user) {
        // Create a new MimeMessage
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            // Set the email address of the recipient
            helper.setTo(user.getEmail());
            helper.setSubject("Reset Password");

            // Create a new context and set the variables
            Context context = new Context();
            context.setVariable("username", user.getUsername());
            context.setVariable("resetLink", "http://localhost:8080/reset-password?token=" + user.getResetToken());

            // Process the email template and set the email content
            String emailContent = templateEngine.process("email/forget-password-email", context);
            helper.setText(emailContent, true);

            // Send the email
            mailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
