package com.ecostream.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendDeliveryNotification(String recipient, String orderId){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject("Order Delivery Notification");
        message.setText("Your shipment for Order #" + orderId + " has arrived.");
        message.setFrom("noreply@ecostream.com");
        mailSender.send(message);
    }
}
