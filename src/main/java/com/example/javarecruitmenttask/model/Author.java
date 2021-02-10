package com.example.javarecruitmenttask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "bookSet")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    These annotations provide validation on both fields. Need to check whether only 1 from 2 fields matches pattern
//    @Pattern(regexp = "^A.*", message = "Author's forename must start with 'A'")
    @NotBlank(message = "Author's forename cannot be blank!")
    private String forename;

//    @Pattern(regexp = "^A.*", message = "Author's surname must start with 'A'")
    @NotBlank(message = "Author's surname cannot be blank!")
    private String surname;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private Set<Book> bookSet;

}

