package com.example.librarymanagementsystem.BookResponseDto;

import com.example.librarymanagementsystem.Enum.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {

    private String name;
    private boolean isAvailable;
    private Genre genre;
    private Date publishDate;
    private Integer price;
    private String authorName;
}
