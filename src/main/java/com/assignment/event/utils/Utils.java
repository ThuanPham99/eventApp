package com.assignment.event.utils;

import com.assignment.event.entity.Event;
import com.assignment.event.entity.Guest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Properties;

public class Utils {
    private static Utils utils = null;
    public static final String HOST_NAME = "smtp.gmail.com";  // mac dinh
    public static final String SSL_PORT  = "465";  // mac dinh
    public static final String USER_NAME = "callmynamept@gmail.com";  // ten dang nhap m su dung de gui mail
    public static final String USER_PASSWORD = "123edsaqw";  // password

    public static Utils getInstance() {
        if(utils == null){
            utils = new Utils();
        }
        return utils;
    }

    public void sendMail (Guest guest, Event event) throws WriterException, IOException {
        String data = "" + guest.getID();  // Chuoi String tao QR code
        QRCodeWriter qrCode = new QRCodeWriter();
        BitMatrix matrix = qrCode.encode(data, BarcodeFormat.QR_CODE,200,200);
        String qrnamefile = data.trim();

        String outputFile = "" + qrnamefile + ".png"; // duong dan luu file anh QR code
//        String outputFile = "C:\\Users\\Dell\\Desktop\\qrimage\\link" ;\
        Path path = FileSystems.getDefault().getPath(outputFile);
        MatrixToImageWriter.writeToPath(matrix, "PNG", path);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);
        byte[] pngByteArray = outputStream.toByteArray();

        //----------------------------gui malil

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", HOST_NAME);
        properties.put("mail.smtp.socketFactory.port", SSL_PORT);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.port", SSL_PORT);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, USER_PASSWORD);
            }
        });

        try {

            //gui noi dung mail
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USER_NAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(guest.getMail()));
            message.setSubject("Your QR Code");  //Tieu de trong mail


            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("Hello "+guest.getName()+" welcome to my Event : " + event.getName()+ ", des: " + event.getDescription()+", this event will start at " + event.getTimeStart()
            +" and location in "+ event.getLocation());   // tin nhan trong phan body cua mail


            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            //gui mot file anh vo mail
            String filename = outputFile;  // lay duong dan cua file anh
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);


            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);


            message.setContent(multipart);


            Transport.send(message);

            System.out.println("Message sent successfully");
        } catch (AddressException ex) {
            ex.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }
}
