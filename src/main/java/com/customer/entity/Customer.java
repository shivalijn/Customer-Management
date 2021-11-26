package com.customer.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String streetName;
    @NonNull
    private int houseNr;
    private String houseAddition;
    @NotBlank
    @Pattern(regexp = "\\d{4} ?[a-zA-Z]{2}")
    private String postalCode;
    @NotBlank
    private String city;
    private LocalDate dateOfBirth;
}
