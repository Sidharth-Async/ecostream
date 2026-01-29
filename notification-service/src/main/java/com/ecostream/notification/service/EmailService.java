package com.ecostream.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendDeliveryNotification(String to, String orderId) {
     try {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sidharthyadav134134@gmail.com");
        message.setTo(to);
        message.setSubject("📦 EcoStream Update: Order " + orderId);
        message.setText("Great news!\n\nYour order " + orderId + " has been successfully delivered.\n\nThank you for choosing EcoStream.");

        mailSender.send(message);

     } catch (Exception e) {
        System.err.println("❌ Failed to send email: " + e.getMessage());
        e.printStackTrace();
      }
  }

    public void sendInTransitNotification(String to, String orderId, String location) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("sidharthyadav134134@gmail.com");
            message.setTo(to);
            message.setSubject("🚚 Update: Order " + orderId + " is on the move");

            String locText = (location != null) ? location : "a distribution center";
            message.setText("Your package is currently in transit.\n\nLatest Location: " + locText + "\n\nWe will notify you when it arrives!");

            mailSender.send(message);

        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
        }
    }
}
