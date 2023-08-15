package com.example.librarymanagementsystem.Models;

import com.example.librarymanagementsystem.Enum.Department;
import com.example.librarymanagementsystem.Enum.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Table(name="student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id //used for Primary key
    @Column(name="Roll_No") // this is used for defining coloumn name
    @GeneratedValue(strategy =GenerationType.IDENTITY) //
    private Integer id;
    @Column(name="Student_Name")
    private String name;
    private Integer age;
    @Enumerated(value = EnumType.STRING)
    private Gender gender; //this is enum class having MALE AND FEMALE as attributes;
    @Enumerated(value=EnumType.STRING)
    private Department department;
    @Column(name="email_id",unique = true)
    private String email;
    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL)
    private LibraryCard libraryCard; //this is a child class object
    //this is a bidirectional mapping


}
