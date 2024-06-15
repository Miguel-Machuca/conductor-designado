/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;
import Utils.Email;
import java.net.*;
import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SendEmailThread implements Runnable {

    /*String servidor = "mail.tecnoweb.org.bo";
    int puerto = 25;
    String user_emisor = "grupo13sa@tecnoweb.org.bo";
    String user_receptor;
    String comando = "";
    private Email email;*/
 /*String servidor = "smtp.googlemail.com";
    int puerto = 465;
    String user_emisor = "godofwarxv@gmail.com";
    String user_receptor;
    String comando = "";
    private Email email;
    private final static String USER = "godofwarx@gmail.com";
    private final static String MAIL = "godofwarxv@gmail.com";
    private final static String MAIL_PASSWORD = "gmquagkpveufscld";
    
    */
    /*
    private final static String PORT_SMTP = "25";
    private final static String PROTOCOL = "smtp";
    private final static String HOST = "mail.tecnoweb.org.bo";
    private final static String MAIL = "grupo04sc@tecnoweb.org.bo";
    private final static String MAIL_PASSWORD = "grup004grup004";
    
    */
     
    private final static String PORT_SMTP = "465";
    private final static String PROTOCOL = "smtp";
    private final static String HOST = "smtp.googlemail.com";
    private final static String USER = "";
    private final static String MAIL = "";
    private final static String MAIL_PASSWORD = "";
   /*
    */
    private Email email;

    public SendEmailThread(Email email) {
        this.email = email;
        //this.user_receptor = email.getTo();
    }

    @Override
    public void run() {

        /*try {
            Socket skCliente = new Socket(servidor, puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(skCliente.getInputStream()));
            DataOutputStream salida = new DataOutputStream(skCliente.getOutputStream());

            if ((skCliente != null) && (entrada != null) && (salida != null)) {
                System.out.println(" S: " + entrada.readLine());

                comando = "HELO " + servidor + "\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine());

                comando = "MAIL FROM : " + user_emisor + "\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine());

                comando = "RCPT TO : " + user_receptor + "\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine());

                comando = "DATA\n";
                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine());
                    
                comando = "SUBJECT: " + email.getSubject() + "\n";
                comando += "Content-Type: text/html; charset=UTF-8\n";
                comando += email.getMessage() + "\n";
                comando += ".\n"; // No mandar espacios, solo punto

                System.out.print("C : " + comando);
                salida.writeBytes(comando);
                System.out.println("S : " + entrada.readLine());

                //cerrar la conexion
                comando = "QUIT\n";
                System.out.print("C :" + comando);
                salida.writeBytes(comando);
                System.out.println("S :" + entrada.readLine());

                skCliente.close();
                entrada.close();
                salida.close();
            }else{
                System.out.println("OK");
            }

        } catch (Exception e) {
            System.out.println(" C : " + e.getMessage());
        }*/

        /*email.getTo();
         email.getSubject();
        email.getMessage();*/
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", PROTOCOL);
        properties.setProperty("mail.smtp.host", HOST);
        properties.setProperty("mail.smtp.port", PORT_SMTP);
        //properties.setProperty("mail.smtp.tls.enable", "true");//cuando user tecnoweb
        properties.setProperty("mail.smtp.ssl.enable", "true");//cuando usen Gmail
        properties.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL, MAIL_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MAIL));

            InternetAddress[] toAddresses = {new InternetAddress(email.getTo())};

            message.setRecipients(MimeMessage.RecipientType.TO, toAddresses);
            message.setSubject(email.getSubject());

            
            Multipart multipart = new MimeMultipart("alternative");
            MimeBodyPart htmlPart = new MimeBodyPart();

            htmlPart.setContent(email.getMessage(), "text/html; charset=utf-8");
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);
            message.saveChanges();

            Transport.send(message);
        } catch (NoSuchProviderException | AddressException ex) {
            Logger.getLogger(SendEmailThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(SendEmailThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
