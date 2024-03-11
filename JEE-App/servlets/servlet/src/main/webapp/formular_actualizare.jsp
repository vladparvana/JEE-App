<html xmlns:jsp="http://java.sun.com/JSP/Page">
    <head>
        <title>Formular student</title>
        <meta charset="UTF-8" />
    </head>
    <body>
        <h3>Formular student</h3>
        Introduceti datele despre student:
        <form action="./update-student?id=<%= request.getAttribute("id")%>" method="post">
            Nume: <input type="text" name="nume" value='<%=request.getAttribute("nume") %>'/>
            <br />
            Prenume: <input type="text" name="prenume" value='<%=request.getAttribute("prenume") %>'/>
            <br />
            Varsta: <input type="number" name="varsta" value='<%=request.getAttribute("varsta") %>'/>
            <br />
            <br />
            <button type="submit" name="submit">Trimite</button>
        </form>
    </body>
</html>