package com.example.librarymanagementsystem.Models;

import com.example.librarymanagementsystem.Enum.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


@Entity
@Table(name="Book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @Column(name="Book_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="Book_Title")
    private String name;
    private boolean isAvailable;
    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    private Date publishDate;
    private Integer price;
    @ManyToOne  // many can be written by one author so many to one relation is there
    @JoinColumn   // join column is used in child class for mapping purpose
    private Author author;

}
