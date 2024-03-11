import ejb.StudentEntity;
import exceptions.FieldException;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateStudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        //preiau datele din cererea de tip GET
        int id = Integer.parseInt(request.getParameter("id"));
        String nume = request.getParameter("nume");
        String prenume = request.getParameter("prenume");
        int varsta = Integer.parseInt(request.getParameter("varsta"));

        //creare EntityManagerFactory si EntityManager
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em= factory.createEntityManager();

        //cautare student dupa id
        StudentEntity student = em.find(StudentEntity.class,id);

        // actualizare in baza de date
        // se face intr-o tranzactie


        if (student != null) {
                // Modifică câmpurile dorite
                student.setNume(nume);
                student.setPrenume(prenume);
                student.setVarsta(varsta);
            }
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.merge(student);
            transaction.commit();
        }
        catch (RollbackException s)
        {
            String eroare = s.getMessage();
            eroare = eroare.replace("exceptions.FieldException: ", "");
            response.setContentType("text/html");
            response.getWriter().println(eroare+
                    "<br /><br /><a href='./'>Inapoi la meniul principal</a>");
            return;
        }

        // inchidere EntityManager
        em.close();
        factory.close();

        // trimitere raspuns inapoi la client
        response.setContentType("text/html");
        response.getWriter().println("Datele au fost actualizate in baza de date." +
                "<br /><br /><a href='./fetch-student-list'>Inapoi la meniul principal</a>");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
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

        request.setAttribute("id",student.getId());
        request.setAttribute("nume",student.getNume() );
        request.setAttribute("prenume",student.getPrenume());
        request.setAttribute("varsta",student.getVarsta());
        // redirectionare date catre pagina de afisare a informatiilor studentului
        request.getRequestDispatcher("./formular_actualizare.jsp").forward(request, response);
    }
}
