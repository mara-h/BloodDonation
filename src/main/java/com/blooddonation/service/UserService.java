package com.blooddonation.service;

import com.blooddonation.model.Appointment;
import com.blooddonation.model.Questionnaire;
import com.blooddonation.model.User;
import com.blooddonation.repository.AppointmentRepository;
import com.blooddonation.repository.QuestionnaireRepository;
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

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = new ArrayList<User>();
            userRepository.findAll().forEach(users::add);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error while getting all users:" + e.getMessage());
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

    public ResponseEntity<String> addMedic(User user) {
        try {
            User medic = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getSex(), user.getBloodGroup(), user.getAge(), user.getCnp());
            medic.setMedic(true);
            User savedUser = userRepository.save(medic);
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
            questionnaireRepository.deleteAll(); // cascade delete Questionnaires;
            return new ResponseEntity<>("Users successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Users could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Users could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteUser(UUID id) {
        try {
            userRepository.deleteById(id);
            ResponseEntity response = this.deleteAllUserQuestionnaires(id); // cascade delete questionnaires
            if (response.getStatusCode().isError()) {
                return new ResponseEntity<>("User " + id + " questionnaires could not be deleted.", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            ResponseEntity responseAppointments = this.deleteAllUserAppointments(id);
            if (responseAppointments.getStatusCode().isError()) {
                return new ResponseEntity<>("User " + id + " appointments could not be deleted.", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>("User " + id + " successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("User " + id + " could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("User " + id + " could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> deleteAllUserQuestionnaires(UUID userId) {
        try {
            List<Questionnaire> userQuestionnaires = questionnaireRepository.findAllByUserId(userId);
            for (Questionnaire userQuestionnaire : userQuestionnaires) {
                questionnaireRepository.deleteById(userQuestionnaire.getId());
            }
            return new ResponseEntity<>("User " + userId + " questionnaires successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("User questionnaires could not be deleted: " + e.getMessage());
            return new ResponseEntity<>("User " + userId + "questionnaires could not be deleted: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> deleteAllUserAppointments(UUID userId) {
        try {
            List<Appointment> userAppointments = appointmentRepository.findAllByUserId(userId);
            for (Appointment userAppointment : userAppointments) {
                appointmentRepository.deleteById(userAppointment.getId());
            }
            return new ResponseEntity<>("User " + userId + " appointments successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("User appointments could not be deleted: " + e.getMessage());
            return new ResponseEntity<>("User " + userId + "appointments could not be deleted: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<User> verifyUserLogin(User givenUser) {
        try {
            Optional<User> user;
            String password = givenUser.getPassword();
            String email = givenUser.getEmail();

            if (password == null)
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            if (email == null)
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            user = Optional.ofNullable(userRepository.findByEmail(email));
            System.out.println("CEVA!@# "+ email + password);
            if (user.isPresent()) {
                User foundUser = user.get();
                System.out.println("167: found user " + foundUser);
                String savedPassword = foundUser.getPassword();
                System.out.println("167: found user " + savedPassword);
                if (savedPassword.equals(givenUser.getPassword())) {
                    System.out.println("CEVA" + foundUser.isMedic());
                    return new ResponseEntity<>(foundUser, HttpStatus.OK);
                }
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                System.out.println("No user found!!!");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.ALREADY_REPORTED);
        }

    }


}

