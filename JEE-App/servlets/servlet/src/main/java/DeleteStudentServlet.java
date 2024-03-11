import ejb.StudentEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteStudentServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //preiau datele din cererea de tip GET
        int id = Integer.parseInt(request.getParameter("id"));

        //creare EntityManagerFactory si EntityManager
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em= factory.createEntityManager();

        //cautare student dupa id
        StudentEntity student = em.find(StudentEntity.class,id);

        //verificare daca studentul exista
        if (student == null)
        {
            response.setContentType("text/html");
            response.getWriter().println("Studentul nu exista." +
                    "<br /><br /><a href='./'>Inapoi la meniul principal</a>");
            return;
        }

        //stergere student din baza de date intr-o transactie
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(student);
        transaction.commit();
        em.close();
        factory.close();

        // trimitere raspuns inapoi la client
        response.setContentType("text/html");
        response.getWriter().println("Studentul a fost sters din baza de date." +
                "<br /><br /><a href='./'>Inapoi la meniul principal</a>");
    }

}
