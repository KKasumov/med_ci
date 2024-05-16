package com.kasumov.med_ci.repository;

import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.entity.user.Patient;
import com.kasumov.med_ci.model.entity.user.Role;
import com.kasumov.med_ci.model.entity.user.history.UserHistory;
import com.kasumov.med_ci.model.entity.user.items.IdentityDocument;
import com.kasumov.med_ci.model.enums.Gender;
import com.kasumov.med_ci.model.enums.IdentityDocumentType;
import com.kasumov.med_ci.model.enums.RolesEnum;
import com.kasumov.med_ci.repository.user.DoctorRepository;
import com.kasumov.med_ci.repository.user.IdentityDocumentRepository;
import com.kasumov.med_ci.repository.user.PatientRepository;
import com.kasumov.med_ci.repository.user.RoleRepository;
import com.kasumov.med_ci.repository.user.UserHistoryRepository;
import com.kasumov.med_ci.repository.user.UserRepository;
import com.kasumov.med_ci.util.ContextIT;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class PersistTest extends ContextIT {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Autowired
    private IdentityDocumentRepository identityDocumentRepository;

    @AfterEach()
    void clear() {
        identityDocumentRepository.deleteAll();
        userHistoryRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void userPersistTest() {
        //сохранил роли
        Role rolePatient = new Role(RolesEnum.PATIENT);
        roleRepository.save(rolePatient);

        Role roleDoctor = new Role(RolesEnum.DOCTOR);
        roleRepository.save(roleDoctor);

        Assertions.assertEquals(2L, roleRepository.findAll().size());

        //сохранил пациента и доктора
        Patient patient = new Patient();
        patient.setEmail("patient@email.com");
        patient.setBirthday(LocalDate.now().minusYears(20));
        patient.setFirstName("firstPatient");
        patient.setLastName("lastPatient");
        patient.setGender(Gender.FEMALE);
        patient.setSnils("snilsPatient");
        patient.setPassword("blablabla");
        patient.setRole(rolePatient);
        patient = patientRepository.save(patient);

        Patient patientFromDb = patientRepository.findById(patient.getId()).get();
        Assertions.assertEquals(patient.getEmail(), patientFromDb.getEmail());

        Doctor doctor = new Doctor();
        doctor.setEmail("doctor@email.com");
        doctor.setBirthday(LocalDate.now().minusYears(30));
        doctor.setFirstName("firstDoctor");
        doctor.setLastName("lastDoctor");
        doctor.setGender(Gender.FEMALE);
        doctor.setSnils("snilsDoctor");
        doctor.setPassword("blablabla");
        doctor.setRole(roleDoctor);
        doctor = doctorRepository.save(doctor);

        Doctor doctorFromDb = doctorRepository.findById(doctor.getId()).get();
        Assertions.assertEquals(doctor.getEmail(), doctorFromDb.getEmail());

        //достал двух юсеров
        Assertions.assertEquals(2L, userRepository.findAll().size());
        Assertions.assertEquals(1L, doctorRepository.findAll().size());
        Assertions.assertEquals(1L, patientRepository.findAll().size());

        //создаю юсерхистори доктору
        UserHistory userHistoryDoctor = new UserHistory();
        userHistoryDoctor.setUser(doctor);
        userHistoryRepository.save(userHistoryDoctor);

        //создаю паспотр доктору
        IdentityDocument identityDocumentDoctor = new IdentityDocument();
        identityDocumentDoctor.setDocumentType(IdentityDocumentType.PASSPORT);
        identityDocumentDoctor.setSerial("1234");
        identityDocumentDoctor.setNumber("12345678");
        identityDocumentDoctor.setDateStart(LocalDate.now().minusYears(10));
        identityDocumentDoctor.setBirthday(LocalDate.now().minusYears(30));
        identityDocumentDoctor.setFirstName("firstDoctor");
        identityDocumentDoctor.setLastName("lastDoctor");
        identityDocumentDoctor.setGender(Gender.FEMALE);
        identityDocumentDoctor.setUserHistory(userHistoryDoctor);
        identityDocumentRepository.save(identityDocumentDoctor);



    }
}
