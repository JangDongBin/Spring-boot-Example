package com.sample.sample.account;

import java.io.InputStream;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;


@Component
public class TempMailSender implements JavaMailSender {

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        // TODO Auto-generated method stub
        System.out.println(simpleMessage);
        System.out.println("메일 전송 완료~(추상화)");
       
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public MimeMessage createMimeMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
        // TODO Auto-generated method stub
        
    }

}
