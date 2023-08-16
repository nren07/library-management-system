package com.example.librarymanagementsystem.RequestDto;

import com.example.librarymanagementsystem.Enum.Genre;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class AddBookDto {
    private String name;
    private boolean isAvailable;
    private Genre genre;
    private Date publishDate;
    private Integer price;
    private Integer authorId;
}
