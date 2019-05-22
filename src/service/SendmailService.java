package service;

import java.util.Date;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendmailService extends Authenticator{
	String userName = null;
	String password = null;
	public SendmailService(String userName, String passwrod) {
		this.userName = userName;
		this.password = passwrod;
	}
	public MimeMessage createMimeMessage(Session sendmailsession, String from, String to, String subject, String text) {
		MimeMessage newmessage = new MimeMessage(sendmailsession);
		try {
			newmessage.setFrom(new InternetAddress(from));
			newmessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			newmessage.setSubject(subject);
			newmessage.setText(text);
			newmessage.setSentDate(new Date());
			return newmessage;
		} catch (AddressException e) {
			e.printStackTrace();
			return null;
		} catch (MessagingException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		if(userName != null && userName.length()>0 && password != null && password.length() > 0) {
			return new PasswordAuthentication(userName, password);
		}
		return null;
	}
}
