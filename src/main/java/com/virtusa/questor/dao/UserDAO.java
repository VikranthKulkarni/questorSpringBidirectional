package com.virtusa.questor.dao;

import com.virtusa.questor.dto.UserDTO;
import com.virtusa.questor.model.User;
import com.virtusa.questor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDAO {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private WishlistDAO wishlistDAO;

    public UserDTO save(UserDTO userDTO) {
        User userModel = toModel(userDTO);
        userModel = userRepo.save(userModel);
        return toDTO(userModel);
    }

    public UserDTO findById(Long id) {
        User userModel = userRepo.findById(id).orElse(null);
        return userModel != null ? toDTO(userModel) : null;
    }

    public UserDTO updateById(Long id,UserDTO userDTO){
        User existingUser = userRepo.findById(id).orElse(null);
        if (existingUser != null){
            User updatedUser = toModel(userDTO);
            updatedUser.setUserId(id);
            updatedUser = userRepo.save(updatedUser);
            return toDTO(updatedUser);
        } else {
            throw new IllegalArgumentException("User not found" + id);
        }
    }

    public UserDTO update(UserDTO userDTO){
        User existingUser = userRepo.findById(userDTO.getUserId()).orElse(null);
        if (existingUser != null){
            User updatedUser = toModel(userDTO);
            updatedUser = userRepo.save(updatedUser);
            return toDTO(updatedUser);
        } else {
            throw new IllegalArgumentException("User not found" + userDTO.getUserId());
        }
    }

    public List<UserDTO> findAll(){
        List<User> users = userRepo.findAll();
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void deleteByID(Long id){
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            userRepo.delete(user);
        } else {
            throw new IllegalArgumentException("User not found" + id);
        }
    }


    public UserDTO toDTO(User user){
        return UserDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .dob(user.getDob())
                .bio(user.getBio())
                .phoneNumber(user.getPhoneNumber())
                .imageUrl(user.getImageUrl())
                .projects(user.getProjects() != null ? user.getProjects().stream().map(projectDAO::toDTO).collect(Collectors.toList()) : null)
                .wishlist(user.getWishlist() != null ? wishlistDAO.toDTO(user.getWishlist()) : null)
                .build();
    }

    public User toModel(UserDTO userDTO){
        return User.builder()
                .userId(userDTO.getUserId())
                .userName(userDTO.getUserName())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .dob(userDTO.getDob())
                .bio(userDTO.getBio())
                .imageUrl(userDTO.getImageUrl())
                .phoneNumber(userDTO.getPhoneNumber())
                .build();
    }

}