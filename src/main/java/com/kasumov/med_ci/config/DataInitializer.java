package com.kasumov.med_ci.config;

import com.kasumov.med_ci.repository.economic.MunicipalOrderForSmoOnMonthRepository;
import com.kasumov.med_ci.repository.economic.MunicipalOrderForSmoRepository;
import com.kasumov.med_ci.repository.economic.OmsPriceOfMedicalServiceRepository;
import com.kasumov.med_ci.repository.economic.PayPriceOfMedicalServiceRepository;
import com.kasumov.med_ci.repository.structure.BuildingRepository;
import com.kasumov.med_ci.repository.structure.DepartmentRepository;
import com.kasumov.med_ci.model.entity.medical.Talon;
import com.kasumov.med_ci.model.entity.structure.Building;
import com.kasumov.med_ci.model.entity.structure.Cabinet;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.entity.structure.License;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.structure.Wage;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.entity.user.Role;
import com.kasumov.med_ci.model.entity.user.history.DoctorHistory;
import com.kasumov.med_ci.model.entity.user.history.UserHistory;
import com.kasumov.med_ci.model.entity.user.items.Attestation;
import com.kasumov.med_ci.model.entity.user.items.Contact;
import com.kasumov.med_ci.model.entity.user.items.Diploma;
import com.kasumov.med_ci.model.entity.user.items.Event;
import com.kasumov.med_ci.model.entity.user.items.IdentityDocument;
import com.kasumov.med_ci.model.entity.user.items.LaborContract;
import com.kasumov.med_ci.model.entity.user.items.University;
import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.model.enums.ContactType;
import com.kasumov.med_ci.model.enums.EventLevel;
import com.kasumov.med_ci.model.enums.EventType;
import com.kasumov.med_ci.model.enums.Gender;
import com.kasumov.med_ci.model.enums.IdentityDocumentType;
import com.kasumov.med_ci.model.enums.RolesEnum;
import com.kasumov.med_ci.repository.bookkeeping.BankAccountRepository;
import com.kasumov.med_ci.repository.bookkeeping.BankRepository;
import com.kasumov.med_ci.repository.bookkeeping.EmployeeBankAccountRepository;
import com.kasumov.med_ci.repository.bookkeeping.OrganizationBankAccountRepository;
import com.kasumov.med_ci.repository.bookkeeping.PaymentRepository;
import com.kasumov.med_ci.repository.bookkeeping.SmoBankAccountRepository;
import com.kasumov.med_ci.repository.economic.MunicipalOrderRepository;
import com.kasumov.med_ci.repository.economic.OrderRepository;
import com.kasumov.med_ci.repository.economic.SmoRepository;
import com.kasumov.med_ci.repository.economic.YetRepository;
import com.kasumov.med_ci.repository.medical.AppealRepository;
import com.kasumov.med_ci.repository.medical.DiseaseRepository;
import com.kasumov.med_ci.repository.medical.MedicalServiceRepository;
import com.kasumov.med_ci.repository.medical.TalonRepository;
import com.kasumov.med_ci.repository.medical.VisitRepository;
import com.kasumov.med_ci.repository.structure.CabinetRepository;
import com.kasumov.med_ci.repository.structure.LicenseRepository;
import com.kasumov.med_ci.repository.structure.MedicalOrganizationRepository;
import com.kasumov.med_ci.repository.structure.PositionRepository;
import com.kasumov.med_ci.repository.structure.WageRepository;
import com.kasumov.med_ci.repository.user.AttestationRepository;
import com.kasumov.med_ci.repository.user.ContactRepository;
import com.kasumov.med_ci.repository.user.DiplomaRepository;
import com.kasumov.med_ci.repository.user.DoctorHistoryRepository;
import com.kasumov.med_ci.repository.user.DoctorRepository;
import com.kasumov.med_ci.repository.user.EmployeeHistoryRepository;
import com.kasumov.med_ci.repository.user.EmployeeRepository;
import com.kasumov.med_ci.repository.user.EquipmentRepository;
import com.kasumov.med_ci.repository.user.EventRepository;
import com.kasumov.med_ci.repository.user.IdentityDocumentRepository;
import com.kasumov.med_ci.repository.user.LaborContractRepository;
import com.kasumov.med_ci.repository.user.PatientHistoryRepository;
import com.kasumov.med_ci.repository.user.PatientRepository;
import com.kasumov.med_ci.repository.user.PolisRepository;
import com.kasumov.med_ci.repository.user.RoleRepository;
import com.kasumov.med_ci.repository.user.SalaryRepository;
import com.kasumov.med_ci.repository.user.UniversityRepository;
import com.kasumov.med_ci.repository.user.UserHistoryRepository;
import com.kasumov.med_ci.repository.user.UserRepository;
import com.kasumov.med_ci.repository.user.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.kasumov.med_ci.model.enums.Gender.FEMALE;
import static com.kasumov.med_ci.model.enums.Gender.MALE;

@Component
@RequiredArgsConstructor
@ConditionalOnExpression("${mis.property.runInitialize:true}")
public class DataInitializer {
    private final Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final EmployeeHistoryRepository employeeHistoryRepository;
    private final DoctorHistoryRepository doctorHistoryRepository;
    private final PatientHistoryRepository patientHistoryRepository;
    private final AttestationRepository attestationRepository;
    private final ContactRepository contactRepository;
    private final DiplomaRepository diplomaRepository;
    private final EquipmentRepository equipmentRepository;
    private final EventRepository eventRepository;
    private final IdentityDocumentRepository identityDocumentRepository;
    private final LaborContractRepository laborContractRepository;
    private final PolisRepository polisRepository;
    private final SalaryRepository salaryRepository;
    private final UniversityRepository universityRepository;
    private final VacationRepository vacationRepository;
    private final WageRepository wageRepository;
    private final PositionRepository positionRepository;
    private final MedicalOrganizationRepository medicalOrganizationRepository;
    private final LicenseRepository licenseRepository;
    private final DepartmentRepository departmentRepository;
    private final CabinetRepository cabinetRepository;
    private final BuildingRepository buildingRepository;
    private final AppealRepository appealRepository;
    private final DiseaseRepository diseaseRepository;
    private final MedicalServiceRepository medicalServiceRepository;
    private final OmsPriceOfMedicalServiceRepository omsPriceOfMedicalServiceRepository;
    private final PayPriceOfMedicalServiceRepository payPriceOfMedicalServiceRepository;
    private final TalonRepository talonRepository;
    private final VisitRepository visitRepository;
    private final MunicipalOrderRepository municipalOrderRepository;
    private final MunicipalOrderForSmoRepository municipalOrderForSmoRepository;
    private final MunicipalOrderForSmoOnMonthRepository municipalOrderForSmoOnMonthRepository;
    private final OrderRepository orderRepository;
    private final SmoRepository smoRepository;
    private final YetRepository yetRepository;
    private final BankAccountRepository bankAccountRepository;
    private final EmployeeBankAccountRepository employeeBankAccountRepository;
    private final OrganizationBankAccountRepository organizationBankAccountRepository;
    private final SmoBankAccountRepository smoBankAccountRepository;
    private final BankRepository bankRepository;
    private final PaymentRepository paymentRepository;


    private static int intInRange(int startBorder, int endBorder) {
        return new Random().nextInt(++endBorder - startBorder) + startBorder;
    }

    private static boolean chancePercent(int percent) {
        return intInRange(0, 100) < percent;
    }

    private static Gender randomGender() {
        return intInRange(0, 100) < 50 ? MALE : FEMALE;
    }

    private static LocalDate dateInRange(LocalDate startBorder, LocalDate endBorder) {
        return LocalDate.ofEpochDay(
                new Random()
                        .nextLong(endBorder.toEpochDay() - startBorder.toEpochDay()) + startBorder.toEpochDay());
    }

    private static LocalDate getRandomBirthday(int from, int to) {
        return dateInRange(
                LocalDate.now().minusYears(from),
                LocalDate.now().minusYears(to));
    }

    private static LocalDateTime dateTimeInRange(LocalDateTime startBorder, LocalDateTime endBorder) {
        return LocalDateTime.ofEpochSecond(
                new Random()
                        .nextLong(endBorder.toEpochSecond(ZoneOffset.UTC) -
                                startBorder.toEpochSecond(ZoneOffset.UTC)) +
                        startBorder.toEpochSecond(ZoneOffset.UTC),
                0,
                ZoneOffset.UTC
        );
    }

    private static double doubleInRange(double startBorder, double endBorder) {
        return new Random().nextDouble(++endBorder - startBorder) + startBorder;
    }

    private static BigDecimal bigDecimalInRange(double startBorder, double endBorder) {
        return BigDecimal.valueOf(doubleInRange(startBorder, endBorder));
    }


    @Value("${mis.property.doctorScope}")
    private Integer doctorScope;


    @Value("${mis.property.talonsOnDay}")
    private Integer talonsOnDay;


    private static List<MedicalOrganization> ORGANIZATION_LIST = new ArrayList<>();
    private static List<Cabinet> CABINET_LIST = new ArrayList<>();
    private static List<Building> BUILDING_LIST = new ArrayList<>();
    private static List<License> LICENSE_LIST = new ArrayList<>();
    private static List<Department> DEPARTMENT_LIST = new ArrayList<>();
    private static List<Position> POSITION_ADULT_LIST = new ArrayList<>();
    private static List<Position> POSITION_CHILD_LIST = new ArrayList<>();
    private static List<Position> POSITION_OTHER_LIST = new ArrayList<>();
    private static List<Wage> WAGE_LIST = new ArrayList<>();
    private static List<Doctor> DOCTOR_LIST = new ArrayList<>();
    private static List<UserHistory> USER_HISTORY_LIST = new ArrayList<>();
    private static List<IdentityDocument> IDENTITY_DOCUMENT_LIST = new ArrayList<>();
    private static List<Contact> CONTACT_LIST = new ArrayList<>();
    private static List<DoctorHistory> DOCTOR_HISTORY_LIST = new ArrayList<>();
    private static List<University> UNIVERSITY_LIST = new ArrayList<>();
    private static List<Diploma> DIPLOMA_LIST = new ArrayList<>();
    private static List<LaborContract> LABOR_CONTRACT_LIST = new ArrayList<>();
    private static List<Attestation> ATTESTATION_LIST = new ArrayList<>();
    private static List<Event> EVENT_LIST = new ArrayList<>();
    private static List<Talon> TALON_LIST = new ArrayList<>();

    @PostConstruct
    public void init() {
        LOGGER.info("Data initialization started. Relax - it will take some time.");

        Role roleDoctor = roleRepository.save(new Role(RolesEnum.DOCTOR));
        Role rolePatient = roleRepository.save(new Role(RolesEnum.PATIENT));
        Role roleEconomist = roleRepository.save(new Role(RolesEnum.ECONOMIST));
        Role roleBooker = roleRepository.save(new Role(RolesEnum.BOOKER));
        Role roleRegistrar = roleRepository.save(new Role(RolesEnum.REGISTRAR));
        Role roleAdmin = roleRepository.save(new Role(RolesEnum.ADMIN));
        Role roleChief = roleRepository.save(new Role(RolesEnum.CHIEF_DOCTOR));
        Role roleDirector = roleRepository.save(new Role(RolesEnum.DIRECTOR));
        Role roleHrManager = roleRepository.save(new Role(RolesEnum.HR_MANAGER));



        //организация
        MedicalOrganization medicalOrganization = medicalOrganizationRepository.save(
                MedicalOrganization.builder()
                        .code("06500")
                        .name("medical_organization_name")
                        .legalAddress("legal_address_med_organization")
                        .ogrn("12345000")
                        .startDate(LocalDate.now().minusYears(10))
                        .fullEmploymentStatusRange(new BigDecimal(String.valueOf(200.25)))
                        .build());
        ORGANIZATION_LIST.add(medicalOrganization);

        //лицензии
        for (int count_license = 1; count_license <= 10; count_license++) {
            License license = licenseRepository.save(
                    License.builder()
                            .name("license_name_" + count_license)
                            .number("license_number_" + count_license)
                            .dateFrom(LocalDate.now().minusYears(7).plusYears(count_license))
                            .dateTo(LocalDate.now().minusYears(3).plusYears(count_license))
                            .medicalOrganization(medicalOrganization)
                            .build());
            LICENSE_LIST.add(license);
        }


        //здания
        for (int count_building = 1; count_building <= 5; count_building++) {
            Building building = buildingRepository.save(
                    Building.builder()
                            .physicalAddress("builder_address_" + count_building)
                            .medicalOrganization(medicalOrganization)
                            .build());
            BUILDING_LIST.add(building);

            //кабинеты в зданиях
            for (int count_cabinet = 1 ; count_cabinet <= 10 * count_building; count_cabinet++) {
                Cabinet cabinet = cabinetRepository.save(
                        Cabinet.builder()
                                .number(count_cabinet)
                                .name("cabinet_name_" + count_cabinet)
                                .building(building)
                                .build());
                CABINET_LIST.add(cabinet);
            }
        }




        //детские отделения
        for (int count_department_child = 1; count_department_child <= 2; count_department_child++) {
            Department departmentChild = departmentRepository.save(
                    Department.builder()
                            .name("department_child_" + count_department_child)
                            .ageType(AgeType.CHILD)
                            .medicalOrganization(medicalOrganization)
                            .build());
            DEPARTMENT_LIST.add(departmentChild);

            //ставка
            for (int count_position = 1; count_position <= intInRange(30, 50); count_position++) {
                Position position = positionRepository.save(
                        Position.builder()
                                .name("position_child_name_" + POSITION_CHILD_LIST.size() +1)
                                .daysForVocation(intInRange(28, 42))
                                .department(departmentChild)
                                .cabinet(CABINET_LIST.get(intInRange(0, CABINET_LIST.size() - 1)))
                                .build());
                POSITION_CHILD_LIST.add(position);

                //оклад
                Wage wage = wageRepository.save(
                        Wage.builder()
                                .dateStart(medicalOrganization.getStartDate())
                                .value(bigDecimalInRange(12000.00, 100000.00))
                                .position(position)
                                .build());
                WAGE_LIST.add(wage);
            }

        }

        //взрослые отделения
        for (int count_department_adult = 1; count_department_adult <= 4; count_department_adult++) {
            Department departmentAdult = departmentRepository.save(
                    Department.builder()
                            .name("department_adult_" + count_department_adult)
                            .ageType(AgeType.ADULT)
                            .medicalOrganization(medicalOrganization)
                            .build());
            DEPARTMENT_LIST.add(departmentAdult);

            //ставка
            for (int count_position = 1; count_position <= intInRange(30, 50); count_position++) {
                Position position = positionRepository.save(
                        Position.builder()
                                .name("position_adult_name_" + POSITION_ADULT_LIST.size() + 1)
                                .daysForVocation(intInRange(28, 42))
                                .department(departmentAdult)
                                .cabinet(CABINET_LIST.get(intInRange(0, CABINET_LIST.size() - 1)))
                                .build());
                POSITION_ADULT_LIST.add(position);

                //оклад
                Wage wage = wageRepository.save(
                        Wage.builder()
                                .dateStart(medicalOrganization.getStartDate())
                                .value(bigDecimalInRange(12000.00, 100000.00))
                                .position(position)
                                .build());
                WAGE_LIST.add(wage);
            }
        }

        //прочее отделение
        for (int count_department_other = 1; count_department_other <= 1; count_department_other++) {
            Department departmentOther = departmentRepository.save(
                    Department.builder()
                            .name("department_other_" + POSITION_OTHER_LIST.size() + 1)
                            .ageType(AgeType.NO)
                            .medicalOrganization(medicalOrganization)
                            .build());
            DEPARTMENT_LIST.add(departmentOther);

            //ставка
            for (int count_position = 1; count_position <= intInRange(30, 50); count_position++) {
                Position position = positionRepository.save(
                        Position.builder()
                                .name("position_other_name_" + count_position)
                                .daysForVocation(28)
                                .department(departmentOther)
                                .cabinet(CABINET_LIST.get(intInRange(0, CABINET_LIST.size() - 1)))
                                .build());
                POSITION_OTHER_LIST.add(position);

                //оклад
                Wage wage = wageRepository.save(
                        Wage.builder()
                                .dateStart(medicalOrganization.getStartDate())
                                .value(bigDecimalInRange(12000.00, 100000.00))
                                .position(position)
                                .build());
                WAGE_LIST.add(wage);
            }
        }

        //университеты
        for (int count_university = 1; count_university <= intInRange(20, 30); count_university++) {
            University university = universityRepository.save(
                    University.builder()
                            .name("university_name_" + count_university)
                            .build());
            UNIVERSITY_LIST.add(university);
        }
        //иду по должностям взрослого отделения и 70% должностей делаю врачами
        for (int count_doctors = 1; count_doctors <= POSITION_ADULT_LIST.size() * 0.7; count_doctors++) {
            Position position = POSITION_ADULT_LIST.get(count_doctors);
            DOCTOR_LIST.add(createDoctor(roleDoctor, DOCTOR_LIST.size() + 1, position.getDepartment(), position));
        }
        //иду по должностям детского отделения и 70% должностей делаю врачами
        for (int count_doctors = 1; count_doctors <= POSITION_CHILD_LIST.size() * 0.7; count_doctors++) {
            Position position = POSITION_CHILD_LIST.get(count_doctors);
            DOCTOR_LIST.add(createDoctor(roleDoctor, DOCTOR_LIST.size() + 1, position.getDepartment(), position));
        }


        //талоны докторов
        for (DoctorHistory doctorHistory : DOCTOR_HISTORY_LIST) {
            //на все дни видимости врача
            for (int count_days = 0; count_days <= doctorScope; count_days++) {
                //на каждый рабочий день
                LocalDate day = LocalDate.now().plusDays(count_days);
                if (day.getDayOfWeek().getValue() < 6) {

                    //на каждый час один талон
                    for (int count_hours = 7; count_hours <= 7 + talonsOnDay; count_hours++) {
                        Talon talon = talonRepository.save(
                                Talon.builder()
                                        .time(LocalDateTime.of(day, LocalTime.MIN).plusHours(count_hours))
                                        .doctorHistory(doctorHistory)
                                        .build());
                        TALON_LIST.add(talon);
                    }
                }
            }
        }
    }


    private Doctor createDoctor(Role roleDoctor, int count_doctor, Department department, Position position) {
        //доктор
        Doctor doctor = new Doctor();
        doctor.setEmail("doctor_" + count_doctor + "_@email.com");
        doctor.setPassword(passwordEncoder.encode(String.valueOf(count_doctor))); // count_doctor
        doctor.setSnils(String.valueOf(11111111 + count_doctor));
        doctor.setFirstName("f_name_" + count_doctor);
        doctor.setLastName("l_name_" + count_doctor);
        doctor.setPatronymic("patronymic_" + count_doctor);
        doctor.setBirthday(getRandomBirthday(60, 30));
        doctor.setGender(randomGender());
        doctor.setRole(roleDoctor);
        doctor.setEnabled(true);
        doctor = doctorRepository.save(doctor);
        DOCTOR_LIST.add(doctor);

        //юсерхистори
        UserHistory userHistory = userHistoryRepository.save(
                UserHistory.builder()
                        .user(doctor)
                        .build());
        USER_HISTORY_LIST.add(userHistory);

        //паспорт
        IdentityDocument identityDocument = identityDocumentRepository.save(
                IdentityDocument.builder()
                        .documentType(IdentityDocumentType.PASSPORT)
                        .serial(String.valueOf(intInRange(1000, 9999)))
                        .number(String.valueOf(intInRange(100000, 999999)))
                        .dateStart(doctor.getBirthday())
                        .firstName(doctor.getFirstName())
                        .lastName(doctor.getLastName())
                        .patronymic(doctor.getPatronymic())
                        .birthday(doctor.getBirthday())
                        .gender(doctor.getGender())
                        .isDeprecated(false)
                        .userHistory(userHistory)
                        .build());
        IDENTITY_DOCUMENT_LIST.add(identityDocument);

        //контакты
        Contact contact1 = contactRepository.save(
                Contact.builder()
                        .contactType(ContactType.PHONE)
                        .value("+7 (" + intInRange(100, 999) + ") " + intInRange(1000000, 9999999))
                        .userHistory(userHistory)
                        .build());
        CONTACT_LIST.add(contact1);
        Contact contact2 = contactRepository.save(
                Contact.builder()
                        .contactType(ContactType.TELEGRAM)
                        .value("@DocTelega_" + count_doctor)
                        .userHistory(userHistory)
                        .build());
        CONTACT_LIST.add(contact2);
        Contact contact3 = contactRepository.save(
                Contact.builder()
                        .contactType(ContactType.ADDRESS)
                        .value("TownName, StreetName, " + count_doctor)
                        .userHistory(userHistory)
                        .build());
        CONTACT_LIST.add(contact3);

        //история доктора
        DoctorHistory doctorHistory = new DoctorHistory();
        doctorHistory.setPublic(true);
        doctorHistory.setEmployee(doctor);
        doctorHistory.setDepartment(department);
        doctorHistory = doctorHistoryRepository.save(doctorHistory);
        DOCTOR_HISTORY_LIST.add(doctorHistory);

        //диплом доктора
        Diploma diploma = diplomaRepository.save(
                Diploma.builder()
                        .serialNumber(String.valueOf(intInRange(1000000000, Integer.MAX_VALUE)))
                        .endDate(dateInRange(doctor.getBirthday().plusYears(25), LocalDate.now()))
                        .university(UNIVERSITY_LIST.get(intInRange(0, UNIVERSITY_LIST.size() -1)))
                        .build());
        DIPLOMA_LIST.add(diploma);

        //трудовой договор с доктором
        LaborContract laborContract = laborContractRepository.save(
                LaborContract.builder()
                        .startDate(dateInRange(diploma.getEndDate(), LocalDate.now()))
                        .part(bigDecimalInRange(0.25, 1.75))
                        .employeeHistory(doctorHistory)
                        .position(position)
                        .diploma(diploma)
                        .build());
        LABOR_CONTRACT_LIST.add(laborContract);

        //аттестации
        LocalDate attestationDateStart = laborContract.getStartDate();
        while (attestationDateStart.isBefore(LocalDate.now())) {
            Attestation attestation = attestationRepository.save(
                    Attestation.builder()
                            .dateFrom(attestationDateStart)
                            .dateTo(attestationDateStart.plusYears(1))
                            .number(String.valueOf(intInRange(10000, Integer.MAX_VALUE)))
                            .university(UNIVERSITY_LIST.get(intInRange(0, UNIVERSITY_LIST.size() - 1)))
                            .laborContract(laborContract)
                            .build());
            ATTESTATION_LIST.add(attestation);
            attestationDateStart = attestation.getDateTo();
        }


        //события
        for (int count_event = 1; count_event <= intInRange(1, 3); count_event++) {
            Event event = eventRepository.save(
                    Event.builder()
                            .eventType(EventType.GRATITUDE)
                            .eventLevel(EventLevel.MEDIUM)
                            .serialNumber(String.valueOf(intInRange(10000, Integer.MAX_VALUE)))
                            .date(dateInRange(laborContract.getStartDate(), LocalDate.now()))
                            .value("приказ о поощрении")
                            .laborContract(laborContract)
                            .build());
            EVENT_LIST.add(event);
        }
        for (int count_event = 1; count_event <= intInRange(1, 3); count_event++) {
            Event event = eventRepository.save(
                    Event.builder()
                            .eventType(EventType.PUNISHMENT)
                            .eventLevel(EventLevel.MEDIUM)
                            .serialNumber(String.valueOf(intInRange(10000, Integer.MAX_VALUE)))
                            .date(dateInRange(laborContract.getStartDate(), LocalDate.now()))
                            .value("приказ о взыскании")
                            .laborContract(laborContract)
                            .build());
            EVENT_LIST.add(event);
        }

        return doctor;
    }
}
