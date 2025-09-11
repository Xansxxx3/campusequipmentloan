package edu.cit.agramon.vicci.campusequimentloan.student.Service;

import edu.cit.agramon.vicci.campusequimentloan.student.Entity.studentEntity;
import edu.cit.agramon.vicci.campusequimentloan.student.Repo.studentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class studentService {

    @Autowired studentRepo studentrepo;

    public studentEntity createStudent(String studentNo, String name, String email){
        studentEntity student = new studentEntity();
        student.setStudentNo(studentNo);
        student.setName(name);
        student.setEmail(email);
        return studentrepo.save(student);
    }
}
