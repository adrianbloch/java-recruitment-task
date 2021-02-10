package com.example.javarecruitmenttask.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Book's title cannot be blank!")
    private String title;

/* Digits annotation didn't validate properly less than 13-digits values and caused NumberFormatException for too big values.
 Change for String value with @Pattern solved problems. Force number value by input type="number" in HTML form */
    @Pattern(regexp = "[0-9]{13}", message = "ISBN must be 13-digits value!")
    @Column(unique= true)
    private String isbn;

    @Valid
    @ManyToOne
    private Author author;

}
