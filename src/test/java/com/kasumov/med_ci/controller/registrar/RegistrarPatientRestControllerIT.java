package com.kasumov.med_ci.controller.registrar;

import com.kasumov.med_ci.feign.TestSystemFeignClient;
import com.kasumov.med_ci.model.dto.testsystem.RequestPatientDtoTestSystem;
import com.kasumov.med_ci.model.dto.testsystem.ResponseJwtTokenTestSystem;
import com.kasumov.med_ci.model.dto.testsystem.ResponsePatientDtoTestSystem;
import com.kasumov.med_ci.model.dto.user.identityDocument.IdentityDocumentForTestSystemResponseDto;
import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.patient.NewPatientDto;
import com.kasumov.med_ci.model.dto.user.polis.NewPolisDto;
import com.kasumov.med_ci.model.dto.user.polis.PolisForTestSystemResponseDto;
import com.kasumov.med_ci.model.enums.IdentityDocumentType;
import com.kasumov.med_ci.model.enums.InsuranceType;
import com.kasumov.med_ci.model.enums.PatientStatus;
import com.kasumov.med_ci.service.dto.testsystem.impl.TestSystemIntegrationImpl;
import com.kasumov.med_ci.util.ContextIT;
import com.kasumov.med_ci.util.Generator;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;


import static com.kasumov.med_ci.model.enums.Gender.FEMALE;
import static com.kasumov.med_ci.model.enums.Gender.MALE;
import static com.kasumov.med_ci.model.enums.IdentityDocumentType.PASSPORT;
import static com.kasumov.med_ci.model.enums.InsuranceType.DMS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBeans({
        @MockBean(Generator.class),
        @MockBean(JavaMailSender.class),
        @MockBean(TestSystemIntegrationImpl.class),
        @MockBean(TestSystemFeignClient.class)
})
public class RegistrarPatientRestControllerIT extends ContextIT {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${testSystem.server.address}")
    private String serverAddress;

    @Autowired
    private Generator generator;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TestSystemIntegrationImpl testSystemIntegration;

    @Autowired
    private TestSystemFeignClient testSystemFeignClient;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registrar_patient_rest_controller/blockPatient.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registrar_patient_rest_controller/blockPatient_clear.sql")
    public void blockPatientTest() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(
                        patch("/api/registrar/patient/{patientId}/block", 20)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.id", Is.is(20)))
                .andExpect(jsonPath("$.data.email", Is.is("ivan@mail.com")))
                .andExpect(jsonPath("$.data.snils", Is.is("000-000-000 00")))
                .andExpect(jsonPath("$.data.lastName", Is.is("Ivanov")))
                .andExpect(jsonPath("$.data.firstName", Is.is("Ivan")))
                .andExpect(jsonPath("$.data.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.birthday", Is.is("25.06.1990")))
                .andExpect(jsonPath("$.data.gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.isEnabled", Is.is(false)));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registrar_patient_rest_controller/newPatient.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registrar_patient_rest_controller/newPatient_clear.sql")
    public void saveNewPatientTest() throws Exception {

        NewPatientDto dto = NewPatientDto.builder()
                .email("mis_mono_1@bk.ru")
                .snils("123456789")
                .polis(NewPolisDto.builder()
                        .insuranceType(DMS)
                        .serial("1111")
                        .number("111111")
                        .dateStart("01.01.2001")
                        .dateEnd("31.12.2030")
                        .smoId(1)
                        .build())
                .identityDocument(NewIdentityDocumentDto.builder()
                        .documentType(PASSPORT)
                        .serial("2222")
                        .number("222222")
                        .dateStart("01.01.2010")
                        .firstName("Peter")
                        .lastName("Jackson")
                        .patronymic("Petrovich")
                        .birthday("01.01.2001")
                        .gender(MALE)
                        .build())
                .build();
        NewPatientDto dtoSmoNotFound = NewPatientDto.builder()
                .email("ivanalekseev@mail.ru")
                .snils("987654321")
                .polis(NewPolisDto.builder()
                        .insuranceType(DMS)
                        .serial("0000")
                        .number("011111")
                        .dateStart("01.01.2001")
                        .dateEnd("31.12.2030")
                        .smoId(100500)
                        .build())
                .identityDocument(NewIdentityDocumentDto.builder()
                        .documentType(PASSPORT)
                        .serial("1111")
                        .number("122222")
                        .dateStart("01.01.2010")
                        .firstName("Ivan")
                        .lastName("Alekseev")
                        .patronymic("Aleksandrovich")
                        .birthday("01.01.2001")
                        .gender(MALE)
                        .build())
                .build();

        //дто которое будет отправляться почтой
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(username);
        smm.setTo(dto.email());
        smm.setSubject("Changing the password assigned during registration to a custom one");
        smm.setText(String.format("""
                Congratulations on registering with the mentally retarded hospital
                medical information system. Follow the link to set your own password
                in your personal account: \n
                %s/api/auth/confirm/emailpassword?token=%s&password=
                """, serverAddress, "Tooooooooooken16"));

        //задаю поведение нужных мне сервисов
        when(generator.getRandomString(8)).thenReturn("Toooken8");
        when(generator.getRandomString(16)).thenReturn("Tooooooooooken16");
        doNothing().when(mailSender).send(smm);

        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        //Проверка, что пациент создан
        mockMvc.perform(
                        post("/api/registrar/patient/new")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.email", Is.is("mis_mono_1@bk.ru")))
                .andExpect(jsonPath("$.data.snils", Is.is("123456789")))
                .andExpect(jsonPath("$.data.firstName", Is.is("Peter")))
                .andExpect(jsonPath("$.data.lastName", Is.is("Jackson")))
                .andExpect(jsonPath("$.data.patronymic", Is.is("Petrovich")))
                .andExpect(jsonPath("$.data.birthday", Is.is("01.01.2001")))
                .andExpect(jsonPath("$.data.gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.isEnabled", Is.is(true)));

        //Проверка, что email уже занят
        mockMvc.perform(post("/api/registrar/patient/new")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Пациент с данным email уже существует в базе")));

        // Проверка СМО
        mockMvc.perform(
                        post("/api/registrar/patient/new")
                                .header("Authorization", accessToken)
                                .content(objectMapper.writeValueAsString(dtoSmoNotFound))
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("СМО с таким id не существует")));

        //проверка действительно ли вызывался метод отправки письма с нужными параметрами 1 раз
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registrar_patient_rest_controller/unBlockPatient.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registrar_patient_rest_controller/unBlockPatient_clear.sql")
    public void unBlockPatientTest() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(
                        patch("/api/registrar/patient/{patientId}/unblock", 10)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.id", Is.is(10)))
                .andExpect(jsonPath("$.data.email", Is.is("ivan2023@mail.com")))
                .andExpect(jsonPath("$.data.snils", Is.is("000-000-000 00")))
                .andExpect(jsonPath("$.data.lastName", Is.is("Ivanov")))
                .andExpect(jsonPath("$.data.firstName", Is.is("Ivan")))
                .andExpect(jsonPath("$.data.patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.birthday", Is.is("25.06.1990")))
                .andExpect(jsonPath("$.data.gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.isEnabled", Is.is(true)));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registrar_patient_rest_controller/getPatientByParameters.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registrar_patient_rest_controller/getPatientByParameters_clear.sql")
    public void getPatientByParametersTest() throws Exception {
        //тест, находим пациента по номеру полиса
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(get("/api/registrar/patient/get/all")
                        .param("polisNumber", "887188")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                //               .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.[0].id", Is.is(10)))
                .andExpect(jsonPath("$.data.[0].email", Is.is("ivan2023@mail.com")))
                .andExpect(jsonPath("$.data.[0].snils", Is.is("000-000-000 00")))
                .andExpect(jsonPath("$.data.[0].lastName", Is.is("Ivanov")))
                .andExpect(jsonPath("$.data.[0].firstName", Is.is("Ivan")))
                .andExpect(jsonPath("$.data.[0].patronymic", Is.is("Ivanovich")))
                .andExpect(jsonPath("$.data.[0].birthday", Is.is("25.06.1990")))
                .andExpect(jsonPath("$.data.[0].gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.[0].isEnabled", Is.is(false)));
        //тест,находим пациентов по параметру firstName
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(get("/api/registrar/patient/get/all")
                        .param("firstName", "Elena")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.length()", Is.is(1)))
                .andExpect(jsonPath("$.data.[0].id", Is.is(30)))
                .andExpect(jsonPath("$.data.[0].email", Is.is("elena@mail.com")))
                .andExpect(jsonPath("$.data.[0].snils", Is.is("123-456-789 12")))
                .andExpect(jsonPath("$.data.[0].lastName", Is.is("Ivanova")))
                .andExpect(jsonPath("$.data.[0].firstName", Is.is("Elena")))
                .andExpect(jsonPath("$.data.[0].patronymic", Is.is("Ivanovna")))
                .andExpect(jsonPath("$.data.[0].birthday", Is.is("13.07.1995")))
                .andExpect(jsonPath("$.data.[0].gender", Is.is(FEMALE.name())))
                .andExpect(jsonPath("$.data.[0].isEnabled", Is.is(true)));

        //тест, находим пациентов по параметру lastName
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(get("/api/registrar/patient/get/all")
                        .param("lastName", "Ivanova")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.length()", Is.is(1)))
                .andExpect(jsonPath("$.data.[0].id", Is.is(30)))
                .andExpect(jsonPath("$.data.[0].email", Is.is("elena@mail.com")))
                .andExpect(jsonPath("$.data.[0].snils", Is.is("123-456-789 12")))
                .andExpect(jsonPath("$.data.[0].lastName", Is.is("Ivanova")))
                .andExpect(jsonPath("$.data.[0].firstName", Is.is("Elena")))
                .andExpect(jsonPath("$.data.[0].patronymic", Is.is("Ivanovna")))
                .andExpect(jsonPath("$.data.[0].birthday", Is.is("13.07.1995")))
                .andExpect(jsonPath("$.data.[0].gender", Is.is(FEMALE.name())))
                .andExpect(jsonPath("$.data.[0].isEnabled", Is.is(true)));

        //тест, находим пациентов по параметру gender
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(get("/api/registrar/patient/get/all")
                        .param("gender", "FEMALE")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.length()", Is.is(1)))
                .andExpect(jsonPath("$.data.[0].id", Is.is(30)))
                .andExpect(jsonPath("$.data.[0].email", Is.is("elena@mail.com")))
                .andExpect(jsonPath("$.data.[0].snils", Is.is("123-456-789 12")))
                .andExpect(jsonPath("$.data.[0].lastName", Is.is("Ivanova")))
                .andExpect(jsonPath("$.data.[0].firstName", Is.is("Elena")))
                .andExpect(jsonPath("$.data.[0].patronymic", Is.is("Ivanovna")))
                .andExpect(jsonPath("$.data.[0].birthday", Is.is("13.07.1995")))
                .andExpect(jsonPath("$.data.[0].gender", Is.is(FEMALE.name())))
                .andExpect(jsonPath("$.data.[0].isEnabled", Is.is(true)));

        //тест, находим пациентов по параметру snils
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(get("/api/registrar/patient/get/all")
                        .param("snils", "123-456-789 12")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", Is.is(true)))
                .andExpect(jsonPath("$.data.length()", Is.is(1)))
                .andExpect(jsonPath("$.data.[0].id", Is.is(30)))
                .andExpect(jsonPath("$.data.[0].email", Is.is("elena@mail.com")))
                .andExpect(jsonPath("$.data.[0].snils", Is.is("123-456-789 12")))
                .andExpect(jsonPath("$.data.[0].lastName", Is.is("Ivanova")))
                .andExpect(jsonPath("$.data.[0].firstName", Is.is("Elena")))
                .andExpect(jsonPath("$.data.[0].patronymic", Is.is("Ivanovna")))
                .andExpect(jsonPath("$.data.[0].birthday", Is.is("13.07.1995")))
                .andExpect(jsonPath("$.data.[0].gender", Is.is(FEMALE.name())))
                .andExpect(jsonPath("$.data.[0].isEnabled", Is.is(true)));

        //тест с пустыми параметрами

        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(get("/api/registrar/patient/get/all")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(20)));

        //тест с несколькими параметрами
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(get("/api/registrar/patient/get/all")
                        .param("firstName", "Ivan")
                        .param("lastName", "Ivanov")
                        .param("gender", MALE.name())
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(20)));

        //тест, что корректно отрабатывает, если пациента с таким номером полиса нет
        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);
        mockMvc.perform(get("/api/registrar/patient/get/all")
                        .param("polisNumber", "000000")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", Is.is(0)));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registrar_patient_rest_controller/getNearestTalon.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registrar_patient_rest_controller/getNearestTalon_clear.sql")
    public void getNearestTalon() throws Exception {
        //почтовое сообщение
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(username);
        smm.setTo("pat21@mail.com");
        smm.setSubject("Вы записаны на прием");
        smm.setText("Ваш талон на запись");

        doNothing().when(mailSender).send(smm);

        accessToken = tokenUtil.obtainNewAccessToken("rg@mail.ru", "100", mockMvc);

        mockMvc.perform(
                        patch("/api/registrar/patient/{patientId}/assigned/for/nearest/talon/department/{departmentId}", 21, 62)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                // .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Is.is(225)))
                .andExpect(jsonPath("$.data.time", Is.is(Matchers.notNullValue())))
                .andExpect(jsonPath("$.data.doctor.id", Is.is(17)))
                .andExpect(jsonPath("$.data.doctor.firstName", Is.is("doc17")))
                .andExpect(jsonPath("$.data.doctor.lastName", Is.is("docov17")))
                .andExpect(jsonPath("$.data.doctor.patronymic", Is.is("docovich17")))
                .andExpect(jsonPath("$.data.patient.id", Is.is(21)))
                .andExpect(jsonPath("$.data.patient.firstName", Is.is("pat21")))
                .andExpect(jsonPath("$.data.patient.lastName", Is.is("pat21")))
                .andExpect(jsonPath("$.data.patient.patronymic", Is.is("pat21")))
                .andExpect(jsonPath("$.data.patient.birthday", Is.is("02.02.1990")));

        mockMvc.perform(
                        patch("/api/registrar/patient/{patientId}/assigned/for/nearest/talon/department/{departmentId}", 221, 62)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Пациент с таким id не существует")));

        //проверка действительно ли вызывался метод отправки письма с нужными параметрами 1 раз
        Mockito.verify(mailSender, Mockito.times(1)).send(smm);

        mockMvc.perform(
                        patch("/api/registrar/patient/{patientId}/assigned/for/nearest/talon/department/{departmentId}", 21, 662)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Отделение с таким id не существует")));

        mockMvc.perform(
                        patch("/api/registrar/patient/{patientId}/assigned/for/nearest/talon/department/{departmentId}", 21, 66)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Возраст пациента не соответствует отделению")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/controller/registrar/registrar_patient_rest_controller/getNearestTalon_noTalons.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/controller/registrar/registrar_patient_rest_controller/getNearestTalon_clear.sql")
    public void getNearestTalon_noTalons() throws Exception {
        accessToken = tokenUtil.obtainNewAccessToken("rg@mail.ru", "100", mockMvc);

        mockMvc.perform(
                        patch("/api/registrar/patient/{patientId}/assigned/for/nearest/talon/department/{departmentId}", 21, 62)
                                .header("Authorization", accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Is.is(452)))
                .andExpect(jsonPath("$.text", Is.is("На текущую дату нет свободных талонов")));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/registrar/registrar_patient_rest_controller/checkPatientInTestSystem.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/registrar/registrar_patient_rest_controller/checkPatientInTestSystem_clear.sql")
    public void checkPatientInTestSystem() throws Exception {

        RequestPatientDtoTestSystem requestPatientDtoTestSystem = RequestPatientDtoTestSystem.builder()
                .snils("000-000-000 01")
                .serialAndNumberDocument("3000/3001")
                .serialAndNumberPolis("2000/2001")
                .build();

        ResponsePatientDtoTestSystem responsePatientDtoTestSystem = ResponsePatientDtoTestSystem.builder()
                .patientStatus(PatientStatus.FOUND)
                .snils("000-000-000 01")
                .document(IdentityDocumentForTestSystemResponseDto.builder()
                        .serial("3000")
                        .number("3001")
                        .lastName("Patient1")
                        .firstName("Patient1")
                        .patronymic("Patient1")
                        .address("address1")
                        .birthday("04.04.2007")
                        .startOfDocument("04.04.2012")
                        .endOfDocument("04.04.2024")
                        .documentType(IdentityDocumentType.PASSPORT)
                        .build())
                .polis(PolisForTestSystemResponseDto.builder()
                        .serialPolis("2000")
                        .numberPolis("2001")
                        .startOfPolis("04.04.2012")
                        .endOfPolis("04.04.2024")
                        .insuranceType(InsuranceType.OMS)
                        .build())
                .build();

        accessToken = tokenUtil.obtainNewAccessToken("registrar@mail.com", "1", mockMvc);

        // Проверка, что пациент найден
        when(testSystemIntegration
                .checkTokenAndPatientInTestSystem(requestPatientDtoTestSystem)).thenReturn(responsePatientDtoTestSystem);

        mockMvc.perform(post("/api/registrar/patient/check/patient")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(requestPatientDtoTestSystem))
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.patientStatus", Is.is("FOUND")))
                .andExpect(jsonPath("$.data.snils", Is.is("000-000-000 01")))
                .andExpect(jsonPath("$.data.document.serial", Is.is("3000")))
                .andExpect(jsonPath("$.data.document.number", Is.is("3001")))
                .andExpect(jsonPath("$.data.document.lastName", Is.is("Patient1")))
                .andExpect(jsonPath("$.data.document.firstName", Is.is("Patient1")))
                .andExpect(jsonPath("$.data.document.patronymic", Is.is("Patient1")))
                .andExpect(jsonPath("$.data.document.address", Is.is("address1")))
                .andExpect(jsonPath("$.data.document.birthday", Is.is("04.04.2007")))
                .andExpect(jsonPath("$.data.document.startOfDocument", Is.is("04.04.2012")))
                .andExpect(jsonPath("$.data.document.endOfDocument", Is.is("04.04.2024")))
                .andExpect(jsonPath("$.data.document.documentType", Is.is("PASSPORT")))
                .andExpect(jsonPath("$.data.polis.serialPolis", Is.is("2000")))
                .andExpect(jsonPath("$.data.polis.numberPolis", Is.is("2001")))
                .andExpect(jsonPath("$.data.polis.startOfPolis", Is.is("04.04.2012")))
                .andExpect(jsonPath("$.data.polis.endOfPolis", Is.is("04.04.2024")))
                .andExpect(jsonPath("$.data.polis.insuranceType", Is.is("OMS")));

        Mockito.verify(testSystemIntegration, Mockito.times(1))
                .checkTokenAndPatientInTestSystem(requestPatientDtoTestSystem);

        // проверка, что пациент не найден
        RequestPatientDtoTestSystem patientNotFound = RequestPatientDtoTestSystem.builder()
                .snils("000-000-000 20")
                .serialAndNumberDocument("3000/3001")
                .serialAndNumberPolis("2000/2010")
                .build();

        mockMvc.perform(post("/api/registrar/patient/check/patient")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(patientNotFound))
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.success", Is.is(false)))
                .andExpect(jsonPath("$.code", Is.is(457)))
                .andExpect(jsonPath("$.text", Is.is("Пациент не найден")));


        Mockito.verify(testSystemIntegration, Mockito.times(1))
                .checkTokenAndPatientInTestSystem(patientNotFound);

        // Проверка, что возвращается верный снилс
        RequestPatientDtoTestSystem patientWithWrongSnils = RequestPatientDtoTestSystem.builder()
                .snils("000-000-000 12")
                .serialAndNumberDocument("3000/3002")
                .serialAndNumberPolis("2000/2002")
                .build();

        ResponsePatientDtoTestSystem responseWithWrongSnils = ResponsePatientDtoTestSystem.builder()
                .patientStatus(PatientStatus.FOUND)
                .snils("000-000-000 02")
                .document(IdentityDocumentForTestSystemResponseDto.builder()
                        .serial("3000")
                        .number("3002")
                        .lastName("Patient2")
                        .firstName("Patient2")
                        .patronymic("Patient2")
                        .address("address2")
                        .birthday("04.04.2006")
                        .startOfDocument("04.04.2011")
                        .endOfDocument("04.04.2025")
                        .documentType(IdentityDocumentType.PASSPORT)
                        .build())
                .polis(PolisForTestSystemResponseDto.builder()
                        .serialPolis("2000")
                        .numberPolis("2002")
                        .startOfPolis("04.04.2011")
                        .endOfPolis("04.04.2025")
                        .insuranceType(InsuranceType.OMS)
                        .build())
                .build();

        doReturn(responseWithWrongSnils)
                .when(testSystemIntegration).checkTokenAndPatientInTestSystem(patientWithWrongSnils);

        mockMvc.perform(post("/api/registrar/patient/check/patient")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsString(patientWithWrongSnils))
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.patientStatus", Is.is("FOUND")))
                .andExpect(jsonPath("$.data.snils", Is.is("000-000-000 02")))
                .andExpect(jsonPath("$.data.document.serial", Is.is("3000")))
                .andExpect(jsonPath("$.data.document.number", Is.is("3002")))
                .andExpect(jsonPath("$.data.document.lastName", Is.is("Patient2")))
                .andExpect(jsonPath("$.data.document.firstName", Is.is("Patient2")))
                .andExpect(jsonPath("$.data.document.patronymic", Is.is("Patient2")))
                .andExpect(jsonPath("$.data.document.address", Is.is("address2")))
                .andExpect(jsonPath("$.data.document.birthday", Is.is("04.04.2006")))
                .andExpect(jsonPath("$.data.document.startOfDocument", Is.is("04.04.2011")))
                .andExpect(jsonPath("$.data.document.endOfDocument", Is.is("04.04.2025")))
                .andExpect(jsonPath("$.data.document.documentType", Is.is("PASSPORT")))
                .andExpect(jsonPath("$.data.polis.serialPolis", Is.is("2000")))
                .andExpect(jsonPath("$.data.polis.numberPolis", Is.is("2002")))
                .andExpect(jsonPath("$.data.polis.startOfPolis", Is.is("04.04.2011")))
                .andExpect(jsonPath("$.data.polis.endOfPolis", Is.is("04.04.2025")))
                .andExpect(jsonPath("$.data.polis.insuranceType", Is.is("OMS")));

        Mockito.verify(testSystemIntegration, Mockito.times(1))
                .checkTokenAndPatientInTestSystem(patientWithWrongSnils);

        // проверка, что токен свежий
        ResponseJwtTokenTestSystem tokenFresh = ResponseJwtTokenTestSystem.builder()
                .jwt("TokenFresh")
                .expirationDate(dateConvertor.localDateTimeToString(LocalDateTime.now().plusMinutes(5)))
                .build();

        ResponseJwtTokenTestSystem tokenNew = ResponseJwtTokenTestSystem.builder()
                .jwt("TokenNew")
                .expirationDate(dateConvertor.localDateTimeToString(LocalDateTime.now().plusHours(1)))
                .build();

        when(testSystemFeignClient.getJwtTokenByTestSystem(any())).thenReturn(tokenNew);

        TestSystemIntegrationImpl testSystemIntegration1 =
                new TestSystemIntegrationImpl(dateConvertor, testSystemFeignClient);
        testSystemIntegration1.setResponseJwtTokenTestSystem(tokenFresh);
        testSystemIntegration1.checkTokenAndPatientInTestSystem(requestPatientDtoTestSystem);
        Assertions.assertEquals(tokenFresh, testSystemIntegration1.getResponseJwtTokenTestSystem());

        // проверка, что токен протухший
        ResponseJwtTokenTestSystem tokenExpired = ResponseJwtTokenTestSystem.builder()
                .jwt("TokenExpired")
                .expirationDate(dateConvertor.localDateTimeToString(LocalDateTime.now().minusMinutes(5)))
                .build();

        testSystemIntegration1.setResponseJwtTokenTestSystem(tokenExpired);
        testSystemIntegration1.checkTokenAndPatientInTestSystem(requestPatientDtoTestSystem);
        Assertions.assertEquals(tokenNew, testSystemIntegration1.getResponseJwtTokenTestSystem());
    }
}
