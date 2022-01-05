package com.blooddonation.repository;

import com.blooddonation.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, UUID> {
    List<Appointment> findAllByUserId(UUID userId);
}
