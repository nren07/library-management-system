package com.example.librarymanagementsystem.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @Column(name="AuthorId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name="EmailId",unique = true)
    private String email;
    private Integer age;
    private String penName;
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)  // by directional mapping between book and author
    List<Book> bookList=new ArrayList<>(); // therefore one author can write many book so we use list here

    
}
