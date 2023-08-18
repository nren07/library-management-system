package com.example.librarymanagementsystem.Models;

import com.example.librarymanagementsystem.Enum.Genre;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    @Column(unique = true, name="Book_Title")
    private String name;

    private boolean isAvailable;
    
    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    private Date publishDate;
    private Integer price;



    @ManyToOne  // many can be written by one author so many to one relation is there
    @JoinColumn   // join column is used in child class for mapping purpose
    @JsonIgnore  //igonore author at the time of making list of book by mapping fk in parent class
    private Author author;

    //mapping with  transaction
    @OneToMany(mappedBy = "book", cascade= CascadeType.ALL)
    List<Transaction> transactionList=new ArrayList<>();

    public Book(String name, boolean isAvailable, Genre genre, Date publishDate, Integer price) {
        this.name = name;
        this.isAvailable = isAvailable;
        this.genre = genre;
        this.publishDate = publishDate;
        this.price = price;
    }
}
