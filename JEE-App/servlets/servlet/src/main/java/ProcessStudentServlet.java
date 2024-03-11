import ejb.StudentEntity;
import exceptions.FieldException;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProcessStudentServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        // se citesc parametrii din cererea de tip POST
        String nume = request.getParameter("nume");
        String prenume = request.getParameter("prenume");
        int varsta = Integer.parseInt(request.getParameter("varsta"));

        // pregatire EntityManager
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();

        // creare entitate JPA si populare cu datele primite din
        // formular
        StudentEntity student = new StudentEntity();
        student.setNume(nume);
        student.setPrenume(prenume);
        student.setVarsta(varsta);

        // adaugare entitate in baza de date (operatiune de
        // persistenta)
        // se face intr-o tranzactie

        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(student);
            transaction.commit();
        }
        catch (FieldException s)
        {
            response.setContentType("text/html");
            response.getWriter().println(s.getMessage()+
                    "<br /><br /><a href='./'>Inapoi la meniul principal</a>");
            return;
        }


        // inchidere EntityManager
        em.close();
        factory.close();


        // trimitere raspuns inapoi la client
        response.setContentType("text/html");
        response.getWriter().println("Datele au fost adaugate in baza de date." +
                "<br /><br /><a href='./'>Inapoi la meniul principal</a>");
    }
}