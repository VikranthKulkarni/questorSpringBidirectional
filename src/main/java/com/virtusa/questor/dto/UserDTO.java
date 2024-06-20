package com.virtusa.questor.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dob;
    private String bio;
    private String imageUrl;
    private String phoneNumber;
    private List<ProjectDTO> projects;
    private WishlistDTO wishlist;

}