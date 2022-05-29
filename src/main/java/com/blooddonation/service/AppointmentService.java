package com.blooddonation.service;

import com.blooddonation.model.Appointment;
import com.blooddonation.model.Enums;
import com.blooddonation.model.Questionnaire;
import com.blooddonation.model.User;
import com.blooddonation.repository.AppointmentRepository;
import com.blooddonation.repository.QuestionnaireRepository;
import com.blooddonation.repository.UserRepository;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    //TODO: implementarea functiei getAvailableAppointments din controller ->

    public ResponseEntity<List<String>> getAvailableAppointments() {
        try {
            List<Appointment> appointments = new ArrayList<>();
            List<String> busyAppointments = new ArrayList<>();
            List<String> allPossibilities = Arrays.asList(Enums.Hours.values().toString());


            appointmentRepository.findAll().forEach(appointments::add);
            if (appointments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            System.out.println("1:"+allPossibilities);

            appointments.stream()
                    .filter(appointment -> appointment.getDayOfAppointment().equals(formatter.format(date)) && appointment.getHourOfAppointment()!=null)
                    .forEach(appointment -> busyAppointments.add(appointment.getHourOfAppointment().toString()));

            System.out.println("2:"+busyAppointments);
            if(busyAppointments.isEmpty())
                return new ResponseEntity<>(allPossibilities, HttpStatus.OK);


            allPossibilities.removeAll(Collections.singletonList(null));
            allPossibilities.removeAll(Collections.singletonList(busyAppointments));
            //allPossibilities.removeAll(busyAppointments);

            System.out.println("final"+allPossibilities);

            return new ResponseEntity<>(allPossibilities, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error while getting available appointments:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Appointment>> getAllAppointments() {
        try {
            List<Appointment> appointments = new ArrayList<>();
            appointmentRepository.findAll().forEach(appointments::add);
            if (appointments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(appointments, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error while getting all appointments:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Appointment> getAppointmentById(UUID id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()) {
            return new ResponseEntity<>(appointment.get(), HttpStatus.OK);
        } else {
            System.out.println("No appointment found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addAppointment(Appointment appointment) {
        try {
            UUID id = UUID.randomUUID();
            System.out.println(appointment.getUserId());
            Appointment editedAppointment = this.getUserIdFromQuestionnaire(appointment);
            if (editedAppointment == null) {
                return new ResponseEntity<>("Could not add user ID", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Appointment savedAppointment = appointmentRepository.save(new Appointment(id, editedAppointment.getDayOfAppointment(), editedAppointment.getHourOfAppointment(), editedAppointment.getQuestionnaireId(), editedAppointment.getUserId()));
            System.out.println("1>>,");
            ResponseEntity response = this.addAppointmentToUser(savedAppointment.getUserId(), savedAppointment.getId());
            if (response.getStatusCode().isError()) {
                System.out.println("Err:" + response.getBody());
                return new ResponseEntity<>("Add appointment to user error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Appointment saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("The appointment could not be added. Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Appointment getUserIdFromQuestionnaire(Appointment appointment) {
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(appointment.getQuestionnaireId());
        if (questionnaire.isPresent()) {
            Questionnaire questionnaireData = questionnaire.get();
            UUID userId = questionnaireData.getUserId();
            if (userId == null) {
                return null;
            } else {
                appointment.setUserId(userId);
            }
        } else {
            return null;
        }
        return appointment;
    }

    private ResponseEntity<User> addAppointmentToUser(UUID userId, UUID id) {
        if (userId == null) {
            System.out.println("User ID is null");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Optional<User> oldData = userRepository.findById(userId);
        if (oldData.isPresent()) {
            User updatedUser = oldData.get();
            if (updatedUser.getAppointmentIds() == null) {
                List<UUID> list = new ArrayList<>();
                list.add(id);
                updatedUser.setAppointmentIds(list);
            } else {
                List<UUID> list = updatedUser.getAppointmentIds();
                list.add(id);
                updatedUser.setAppointmentIds(list);
            }
            return new ResponseEntity<>(userRepository.save(updatedUser), HttpStatus.OK);
        } else {
            System.out.println("No such user found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Appointment> updateAppointment(UUID id, Appointment appointment) {
        Optional<Appointment> oldData = appointmentRepository.findById(id);
        if (oldData.isPresent()) {
            Appointment updatedAppointment = oldData.get();
            updatedAppointment.setDayOfAppointment(appointment.getDayOfAppointment()); // we can only change hour and day of appointment
            updatedAppointment.setHourOfAppointment(appointment.getHourOfAppointment());
            updatedAppointment.setUserId(updatedAppointment.getUserId());
            updatedAppointment.setQuestionnaireId(updatedAppointment.getQuestionnaireId());
            return new ResponseEntity<>(appointmentRepository.save(updatedAppointment), HttpStatus.OK);
        } else {
            System.out.println("No such appointment found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAllAppointments() {
        //TODO: remove from every user every appointment
        try {
            appointmentRepository.deleteAll();

            List<User> users = new ArrayList<User>();
            userRepository.findAll().forEach(users::add); // remove appointments from every user
            for (User user : users) {
                user.setAppointmentIds(null);
            }

            return new ResponseEntity<>("Appointments successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Appointments could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Appointments could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteAppointment(UUID id) {
        try {
            //delete from users
            UUID userId = null;
            Optional<Appointment> appointment = appointmentRepository.findById(id);
            if (appointment.isPresent()) {
                Appointment appointmentData = appointment.get();
                userId = appointmentData.getUserId();
            } else {
                return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
            }

            appointmentRepository.deleteById(id);


            if (userId == null) {
                return new ResponseEntity<>("Error deleting appointment from user: user ID null", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                ResponseEntity response = this.removeAppointmentsFromUsers(userId);
                if (response.getStatusCode().isError()) {
                    System.out.println("error:" + response.getBody());
                    return new ResponseEntity<>("Error deleting user from user", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            this.removeAppointmentsFromUsers(userId);
            return new ResponseEntity<>("Appointment " + id + " successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Appointment " + id + " could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Appointment " + id + " could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> removeAppointmentsFromUsers(UUID userId) {
        try {
            List<Appointment> userAppointments = appointmentRepository.findAllByUserId(userId);
            List<UUID> appointmentsIds = userAppointments.stream().map(Appointment::getId).collect(Collectors.toList());

            Optional<User> oldData = userRepository.findById(userId);
            if (oldData.isPresent()) {
                User updatedUser = oldData.get();
                updatedUser.setAppointmentIds(appointmentsIds);
                userRepository.save(updatedUser);
            } else {
                System.out.println("No such user found - remove appointments from user");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Removed appointments from user", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Appointment could not be removed: " + e.getMessage());
            return new ResponseEntity<>("Appointment could not be removed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
