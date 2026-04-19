package isst.isst_group21.movemate_back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarBienvenida(String toEmail, String nombre) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("¡Bienvenido a Movemate!");
        message.setText("Hola " + nombre + ",\n\nGracias por unirte a Movemate. ¡Esperamos que disfrutes de la experiencia!\n\nEl equipo de Movemate");
        message.setFrom("movematemdasm@gmail.com");
        mailSender.send(message);
    }
}
