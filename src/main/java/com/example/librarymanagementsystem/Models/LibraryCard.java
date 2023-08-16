package com.example.librarymanagementsystem.Models;

import com.example.librarymanagementsystem.Enum.CardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibraryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CardNo")
    private Integer id;
    @Enumerated(value = EnumType.STRING)
    private CardStatus cardStatus;
    @Column(name="Issued Book QTY")
    private int noOfIssuedBooks;
    //librarycard is a child class in terms of Student
    @OneToOne  //one to one mappping
    @JoinColumn
    private Student student;    //foreign key coloumn of student class
    //child class have unidirectional mapping for sure


    
}
