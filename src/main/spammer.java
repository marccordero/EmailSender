package main;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Scanner;

public class spammer {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Solicitar al usuario que ingrese los detalles del correo
        System.out.println("Ingrese la dirección de correo electrónico desde la cual enviar:");
        String from = scanner.nextLine();
        System.out.println("Ingrese la contraseña de su cuenta de correo electrónico:");
        String password = scanner.nextLine();
        System.out.println("Ingrese la dirección de correo electrónico destino:");
        String to = scanner.nextLine();
        System.out.println("Ingrese el asunto del correo:");
        String subject = scanner.nextLine();
        System.out.println("Ingrese el contenido del mensaje:");
        String body = scanner.nextLine();
        System.out.println("Ingrese el número de correos a enviar:");
        int numEmails = scanner.nextInt();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, null);

        Transport transport = session.getTransport("smtp");

        try {
            transport.connect("smtp.gmail.com", from, password);
            for (int i = 1; i <= numEmails; i++) {
                MimeMessage msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(from));
                msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                msg.setSubject(subject + " " + i);
                msg.setContent(body + "<p>Correo número: " + i + "</p>", "text/html");

                System.out.println("Enviando correo número " + i + "...");
                transport.sendMessage(msg, msg.getAllRecipients());
                System.out.println("Correo número " + i + " enviado correctamente.");
                Thread.sleep(1000); // Esperar un segundo entre cada envío (opcional)
            }
        } catch (Exception ex) {
            System.out.println("Ocurrió un error al enviar los correos electrónicos: " + ex.getMessage());
        } finally {
            transport.close(); // Asegurarse de cerrar la conexión al finalizar
            scanner.close(); // Cerrar el scanner
        }
    }
}
