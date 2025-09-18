package edu.cit.agramon.vicci.campusequimentloan.student.Controller;
import edu.cit.agramon.vicci.campusequimentloan.student.Entity.studentEntity;
import edu.cit.agramon.vicci.campusequimentloan.student.Service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class studentController {

    @Autowired studentService studentservice;

    @PutMapping("/add")
            public String createStudent(@RequestBody studentEntity student){
        return studentservice.createStudent(student.getStudentNo(),student.getName(),student.getEmail());
    }
    @GetMapping("/getAll")
    public List<studentEntity> getAllStudent(){
        return studentservice.getAllStudent();
    }
}
