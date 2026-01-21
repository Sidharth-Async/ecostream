package com.ecostream.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendDeliveryNotification(String to, String orderId) {
        // SIMULATION MODE: Don't actually try to connect to a mail server
        System.out.println("==================================================");
        System.out.println("📧 MOCK EMAIL SENT");
        System.out.println("To: " + to);
        System.out.println("Subject: Your Order " + orderId + " is Delivered!");
        System.out.println("Body: Your package has arrived safely.");
        System.out.println("==================================================");

        /* // OLD CODE (Commented out until you have a real mail server)
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Shipment Delivered: " + orderId);
        message.setText("Good news! Your order " + orderId + " has been delivered.");
        mailSender.send(message);
        */
    }
}
