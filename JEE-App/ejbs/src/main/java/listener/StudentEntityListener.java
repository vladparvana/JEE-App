package listener;

import ejb.StudentEntity;
import exceptions.FieldException;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class StudentEntityListener {

    @PrePersist
    @PreUpdate
    public void beforeAnyUpdate(StudentEntity student) {
        if (student.getVarsta()<18 || student.getVarsta()>35) {
            throw new FieldException("Campul varsta trebuie sa fie cuprins intre 18 si 35");
        }
    }
}
