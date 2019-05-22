package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.mail.util.MailSSLSocketFactory;

import service.SendmailService;
import sun.awt.RepaintArea;

@WebServlet("/SendmailSetvlet")
public class SendmailSetvlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SendmailSetvlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String to = request.getParameter("to");
		String from = request.getParameter("from");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		String password = request.getParameter("password");
		String isKeepCookie = request.getParameter("question");
		System.out.println(isKeepCookie);
		String fromCookie = URLEncoder.encode(from, "utf-8");
		String passCookie = URLEncoder.encode(password,"utf-8");
		if(isKeepCookie.equals("true")) {
			Cookie userFromCookie = new Cookie("userFromCookie", fromCookie);
			Cookie passwordCookie = new Cookie("passwordCookie", passCookie);
			userFromCookie.setPath("/");
			passwordCookie.setPath("/");
			userFromCookie.setMaxAge(864000);
			passwordCookie.setMaxAge(864000);
			response.addCookie(userFromCookie);
			response.addCookie(passwordCookie);
			System.out.println("-----------====");
		}else {
			Cookie[] cookies = null;
			cookies = request.getCookies();
			if(cookies.length > 0 && cookies != null) {
				for(Cookie c : cookies) {
					if("fromCookie".equals(c.getName()) || "passCookie".equals(c.getName())) {
						c.setMaxAge(0);
						c.setPath("/");
						response.addCookie(c);
					}
				}
			}
		}
		String eMailType = "smtp.qq.com";
		try {
			Properties props = new Properties();
			props.put("mail.host", eMailType);
			props.put("mail.password", "cexqydqtzkyndced");
			props.put("mail.transport.protocol", "smtp");
			props.setProperty("mail.debug", "true");
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.socketFactory",sf);
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.socketFactory.port", "465");
			//为了规范密码，一律用邮箱stmp 验证码登陆
			Session sendMailSession = Session.getInstance(props, new SendmailService(from, password));
			sendMailSession.setDebug(true);
			SendmailService ss = new SendmailService(from,password);
			Message newMessage = ss.createMimeMessage(sendMailSession, from, to, subject, message);
			Transport transport = sendMailSession.getTransport("smtp");
			transport.connect(eMailType,from, password);
			transport.send(newMessage,newMessage.getAllRecipients());
			transport.close();
			response.sendRedirect("send_success.jsp");
		} catch (AddressException e) {
			e.printStackTrace();
			response.sendRedirect("send_error.jsp");
		} catch (MessagingException e) {
			e.printStackTrace();
			response.sendRedirect("send_error.jsp");
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			response.sendRedirect("send_error.jsp");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
