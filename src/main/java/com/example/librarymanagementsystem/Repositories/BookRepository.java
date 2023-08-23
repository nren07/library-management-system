package com.example.librarymanagementsystem.Repositories;

import com.example.librarymanagementsystem.Enum.Genre;
import com.example.librarymanagementsystem.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findBooksByGenre(Genre genre);


}
