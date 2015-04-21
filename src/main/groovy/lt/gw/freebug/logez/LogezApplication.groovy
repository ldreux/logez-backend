package lt.gw.freebug.logez

import static org.springframework.http.HttpStatus.ACCEPTED

import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus

@SpringBootApplication
@Controller
class LogezApplication {
	static final Logger logger = LoggerFactory.getLogger(LogezApplication.class)

	@Autowired
	Smtp smtp

	static void main(String[] args)  {
		SpringApplication.run(LogezApplication.class, args)
	}

	@ResponseStatus(ACCEPTED)
	@RequestMapping(value = "/message", method = RequestMethod.POST)
	void sendMessage(@RequestBody Message message) {
		Properties properties = System.getProperties()
		properties.setProperty("mail.smtp.host", smtp.host)
		Session session = Session.getDefaultInstance(properties)
		try {
			MimeMessage mimeMessage = new MimeMessage(session)
			mimeMessage.setSubject(message.subject)
			mimeMessage.setText(message.text)
			mimeMessage.setFrom(new InternetAddress(message.from))
			mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(message.to))
			Transport.send(mimeMessage);
			logger.info("Sent message successfully....");
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e)
		}
	}
}
