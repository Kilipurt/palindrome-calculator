package com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "NUMBERS")
@Getter
@Setter
public class Number {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    @Column(name = "NUMBER_VALUE")
    private String numberValue;

    @Column(name = "AMOUNT")
    private long amount;

    @ElementCollection
    @CollectionTable(name = "PALINDROMES", joinColumns = @JoinColumn(name = "NUMBER_ID"))
    @Column(name = "PALINDROME_VALUE")
    private List<String> palindromes;
}
