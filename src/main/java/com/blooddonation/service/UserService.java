package com.blooddonation.service;

import com.blooddonation.model.User;
import com.blooddonation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = new ArrayList<User>();
            userRepository.findAll().forEach(users::add);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error while getting all products:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<User> getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            System.out.println("No user found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addUser(User user) {
        try {
            User savedUser = userRepository.save(new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getSex(), user.getBloodGroup(), user.getAge(), user.getCnp()));
            return new ResponseEntity<>("User saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("The user could not be added. Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<User> updateUser(UUID id, User userData) {
        Optional<User> oldUserData = userRepository.findById(id);
        if (oldUserData.isPresent()) {
            User updatedUser = oldUserData.get();
            updatedUser.setFirstName(userData.getFirstName());
            updatedUser.setLastName(userData.getLastName());
            updatedUser.setEmail(userData.getEmail());
            updatedUser.setPassword(userData.getPassword());
            updatedUser.setSex(userData.getSex());
            updatedUser.setBloodGroup(userData.getBloodGroup());
            updatedUser.setAge(userData.getAge());
            updatedUser.setCnp(userData.getCnp());
            return new ResponseEntity<>(userRepository.save(updatedUser), HttpStatus.OK);
        } else {
            System.out.println("No such user found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>("Products successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Users could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Users could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteUser(UUID id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>("User " + id + " successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("User " + id + " could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("User " + id + " could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
