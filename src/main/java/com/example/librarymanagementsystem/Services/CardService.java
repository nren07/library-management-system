package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.Enum.CardStatus;
import com.example.librarymanagementsystem.Models.LibraryCard;
import com.example.librarymanagementsystem.Models.Student;
import com.example.librarymanagementsystem.Repositories.CardRepository;
import com.example.librarymanagementsystem.Repositories.StudentRepository;
import jakarta.persistence.Enumerated;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private StudentRepository studentRepository;

    public String addCard(LibraryCard libraryCard) throws Exception{
        if(libraryCard.getId()!=null) throw new Exception("card id is not to be as a parameter");
        cardRepository.save(libraryCard);
        return "Card has  been added successfully";
    }

    public String associate(Integer rollNo,Integer cardNo) throws Exception{
        //student should be existed
        if(!studentRepository.existsById(rollNo)) throw new Exception("Roll no is not valid ");
        if(!cardRepository.existsById(cardNo)) throw new Exception("card no is not valid ");

        Optional<Student>optionalStudent=studentRepository.findById(rollNo);
        Student student=optionalStudent.get();

        Optional<LibraryCard>optionalLibraryCard=cardRepository.findById(cardNo);
        LibraryCard libraryCard=optionalLibraryCard.get();


        if(libraryCard.getCardStatus()!=CardStatus.NEW) throw new Exception("Card is not valid for association");

        libraryCard.setCardStatus(CardStatus.ISSUED); //update card status
        libraryCard.setStudent(student); //set object of student in library card
        student.setLibraryCard(libraryCard); //set object of library card in student card

        //therefore it is bidirectional mapping so we need to store only parent class object
        //child class object automatically saved
        studentRepository.save(student);

//        cardRepository.save(libraryCard); -- > no need to save it is automatically save due to casecade effect
        return "Student and Card is successfully associated";
    }
}
