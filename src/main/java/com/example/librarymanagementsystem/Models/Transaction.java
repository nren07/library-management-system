package com.example.librarymanagementsystem.Models;

import com.example.librarymanagementsystem.Enum.TransactionStatus;
import com.example.librarymanagementsystem.Enum.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transactionId")
    private Integer id;

    @CreationTimestamp
    private Date creationDate;

    @UpdateTimestamp
    private Date updateDate;

    private Integer fine;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    //mapping between book and transaction
    //Many to one mapping where book is parent and transaction is child

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Book book;

    //mapping with library card
    //Many to one mapping where library card is parent and transaction is child

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private LibraryCard libraryCard;

    public Transaction(Integer fine, TransactionStatus transactionStatus, TransactionType transactionType) {
        this.fine = fine;
        this.transactionStatus = transactionStatus;
        this.transactionType = transactionType;
    }
}
