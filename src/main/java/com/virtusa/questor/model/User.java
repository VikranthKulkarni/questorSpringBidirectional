package com.virtusa.questor.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "bio")
    private String bio;

    @Column(name = "image_Url")
    private String imageUrl;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<Project> projects;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
//    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;

}