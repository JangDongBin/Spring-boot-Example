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
        System.out.println(simpleMessage);
        System.out.println("메일 전송 완료~(추상화)");
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
    }

    @Override
    public MimeMessage createMimeMessage() {
        return null;
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        return null;
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
    }

}
