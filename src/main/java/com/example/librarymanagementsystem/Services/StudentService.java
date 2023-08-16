package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.Enum.Department;
import com.example.librarymanagementsystem.Models.Student;
import com.example.librarymanagementsystem.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public String addStudent(Student student) throws Exception{
        if(student.getId()!=null){
            throw new Exception("Id not to be sent as parameter");
        }
        studentRepository.save(student);
        return "Student added successfully";
    }

    public Department getDeptById(Integer id) throws Exception{
        Optional<Student> optionalStudent=studentRepository.findById(id);
        if(!optionalStudent.isPresent()) throw new Exception("Invalid RollNo");
        return optionalStudent.get().getDepartment();
    }
}
