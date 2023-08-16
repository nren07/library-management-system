package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.Enum.Department;
import com.example.librarymanagementsystem.Models.Student;
import com.example.librarymanagementsystem.Services.StudentService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity addStudent(@RequestBody Student student){
        try{
            String result=studentService.addStudent(student);
            return new ResponseEntity(result, HttpStatus.CREATED);
        }catch(Exception e){
            log.error("student could not be added in db {}",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/deptById")
    public ResponseEntity getDeptById(@RequestParam("id") Integer id){
        try{
            Department department=studentService.getDeptById(id);
            return new ResponseEntity(department,HttpStatus.OK);

        }catch(Exception e){
            log.error("Could not find department {} ",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


}
