package edu.cit.agramon.vicci.campusequimentloan.student.Service;

import edu.cit.agramon.vicci.campusequimentloan.student.Entity.studentEntity;
import edu.cit.agramon.vicci.campusequimentloan.student.Repo.studentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class studentService {

    @Autowired studentRepo studentrepo;

    public String createStudent(String studentNo, String name, String email) {
        // Check if a student with the same studentNo already exists
        Optional<studentEntity> existingStudent = studentrepo.findByStudentNo(studentNo);

        if (existingStudent.isPresent()) {
            return "Student with studentNo " + studentNo + " already exists.";
        }

        // Check if a student with the same email already exists
        Optional<studentEntity> existingEmail = studentrepo.findByEmail(email);

        if (existingEmail.isPresent()) {
            return "A student with email " + email + " already exists.";
        }

        // Create and populate a new student entity
        studentEntity student = new studentEntity();
        student.setStudentNo(studentNo);
        student.setName(name);
        student.setEmail(email);

        // Save the new student entity to the database
        studentrepo.save(student);

        // Return success message
        return "Student successfully created. Student No: " + studentNo + ", Name: " + name;
    }

    public List<studentEntity> getAllStudent(){
        return studentrepo.findAll();
    }
}
