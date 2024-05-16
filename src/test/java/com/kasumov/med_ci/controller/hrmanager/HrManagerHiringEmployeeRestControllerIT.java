package com.kasumov.med_ci.controller.hrmanager;

import com.kasumov.med_ci.model.dto.user.contact.ContactEmployeeDto;
import com.kasumov.med_ci.model.dto.user.diploma.DiplomaForHiringDto;
import com.kasumov.med_ci.model.dto.user.employee.EmployeeForHiringDTO;
import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.laborContract.LaborContractForHiringDto;
import com.kasumov.med_ci.model.enums.ContactType;
import com.kasumov.med_ci.model.enums.Gender;
import com.kasumov.med_ci.util.ContextIT;
import com.kasumov.med_ci.util.Generator;
import org.hamcrest.core.Is;
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
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.kasumov.med_ci.model.enums.IdentityDocumentType.PASSPORT;

@MockBeans({
        @MockBean(Generator.class),
        @MockBean(JavaMailSender.class)
})
public class HrManagerHiringEmployeeRestControllerIT extends ContextIT {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${testSystem.server.address}")
    private String serverAddress;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Generator generator;

    @Autowired
    private JavaMailSender mailSender;


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_hiring_employee_rest_controller/hrmanager_hiring_employee.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "/scripts/controller/hrmanager/hr_manager_hiring_employee_rest_controller/hrmanager_hiring_employee_clear.sql")
    public void hiringEmployee() throws Exception {

        accessToken = tokenUtil.obtainNewAccessToken("hr@mail.ru","100", mockMvc);

        List<ContactEmployeeDto> contactDtos = new ArrayList<>();
        contactDtos.add(
                ContactEmployeeDto.builder()
                        .contactType(ContactType.TELEGRAM)
                        .value("chachacha23")
                        .build()
        );
        contactDtos.add(
                ContactEmployeeDto.builder()
                        .contactType(ContactType.PHONE)
                        .value("+79845072194")
                        .build()
        );
        contactDtos.add(
                ContactEmployeeDto.builder()
                        .contactType(ContactType.ADDRESS)
                        .value("Пензенская область. г. Пенза, ул. Рубеж Салата д.9 кв 35")
                        .build()
        );
        EmployeeForHiringDTO employeeForHiringDTO =
                EmployeeForHiringDTO
                        .builder()
                        .email("unique@mail.ru")
                        .snils("3213131")
                        .identityDocumentDto(
                                NewIdentityDocumentDto
                                        .builder()
                                        .documentType(PASSPORT)
                                        .serial("33131")
                                        .number("2343")
                                        .dateStart("01.05.2004")
                                        .firstName("Фома")
                                        .lastName("Фомин")
                                        .patronymic("Фоминович")
                                        .birthday("15.04.1999")
                                        .gender(Gender.MALE)
                                        .build())
                        .laborContractForHiringDto(
                                LaborContractForHiringDto
                                        .builder()
                                        .startDate("01.06.2023")
                                        .part(BigDecimal.valueOf(1))
                                        .build())
                        .roleId(300L)
                        .positionId(102L)
                        .diplomaForHiringDto(
                                DiplomaForHiringDto
                                        .builder()
                                        .serialNumber("231314151")
                                        .endDate("15.04.2005")
                                        .universityId(1L)
                                        .build())
                        .contactDtos(contactDtos)
                        .build();

        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(username);
        smm.setTo("unique@mail.ru");
        smm.setSubject("Changing the password assigned during registration to a custom one");
        smm.setText(
                String.format("""
                Congratulations on registering with the mentally retarded hospital
                medical information system. Follow the link to set your own password
                in your personal account: \n
                %s/api/auth/confirm/emailpassword?token=%s&password=
                """, serverAddress, "Tooooooooooken16")
        );

        when(generator.getRandomString(8)).thenReturn("Toooken8");
        when(generator.getRandomString(16)).thenReturn("Tooooooooooken16");
        doNothing().when(mailSender).send(smm);

        smm.setTo("unique@mail.ru");

        mockMvc.perform(post("/api/manager/employee/hiring/create")
                        .header("Authorization", accessToken)
                .content(objectMapper.writeValueAsBytes(employeeForHiringDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.identityDocumentDto.documentType", Is.is("PASSPORT")))
                .andExpect(jsonPath("$.data.identityDocumentDto.serial", Is.is("###31")))
                .andExpect(jsonPath("$.data.identityDocumentDto.number", Is.is("##43")))
                .andExpect(jsonPath("$.data.identityDocumentDto.dateStart", Is.is("01.05.2004")))
                .andExpect(jsonPath("$.data.identityDocumentDto.firstName", Is.is("Фома")))
                .andExpect(jsonPath("$.data.identityDocumentDto.lastName", Is.is("Фомин")))
                .andExpect(jsonPath("$.data.identityDocumentDto.patronymic", Is.is("Фоминович")))
                .andExpect(jsonPath("$.data.identityDocumentDto.birthday", Is.is("15.04.1999")))
                .andExpect(jsonPath("$.data.identityDocumentDto.gender", Is.is("MALE")))
                .andExpect(jsonPath("$.data.identityDocumentDto.isDeprecated", Is.is(false)))
                .andExpect(jsonPath("$.data.laborContractDto.startDate", Is.is("01.06.2023")))
                .andExpect(jsonPath("$.data.laborContractDto.part", Is.is(1)))
                .andExpect(jsonPath("$.data.laborContractDto.employeeHistoryDto.isPublic", Is.is(true)))
                .andExpect(jsonPath("$.data.laborContractDto.employeeHistoryDto.employeeDto.email", Is.is("unique@mail.ru")))
                .andExpect(jsonPath("$.data.laborContractDto.employeeHistoryDto.employeeDto.snils", Is.is("3213131")))
                .andExpect(jsonPath("$.data.laborContractDto.employeeHistoryDto.employeeDto.enabled", Is.is(true)))
                .andExpect(jsonPath("$.data.laborContractDto.employeeHistoryDto.departmentDto.id", Is.is(61)))
                .andExpect(jsonPath("$.data.laborContractDto.employeeHistoryDto.departmentDto.name", Is.is("Хирургическое отделение")))
                .andExpect(jsonPath("$.data.laborContractDto.employeeHistoryDto.departmentDto.ageType", Is.is("ADULT")))
                .andExpect(jsonPath("$.data.laborContractDto.positionDto.id", Is.is(102)))
                .andExpect(jsonPath("$.data.laborContractDto.positionDto.name", Is.is("Офтальмолог")))
                .andExpect(jsonPath("$.data.laborContractDto.positionDto.daysForVocation", Is.is(42)))
                .andExpect(jsonPath("$.data.laborContractDto.diplomaDto.serial", Is.is("231314151")))
                .andExpect(jsonPath("$.data.laborContractDto.diplomaDto.endDate", Is.is("15.04.2005")))
                .andExpect(jsonPath("$.data.laborContractDto.diplomaDto.universityDto.id", Is.is(1)))
                .andExpect(jsonPath("$.data.laborContractDto.diplomaDto.universityDto.name", Is.is("Университет имени очень важного человека")))
                .andExpect(jsonPath("$.data.contactDto.[0].value", Is.is("chachacha23")))
                .andExpect(jsonPath("$.data.contactDto.[1].value", Is.is("+79845072194")))
                .andExpect(jsonPath("$.data.contactDto.[2].value", Is.is("Пензенская область. г. Пенза, ул. Рубеж Салата д.9 кв 35")))
                .andExpect(jsonPath("$.data.contactDto.[0].contactType", Is.is(ContactType.TELEGRAM.name())))
                .andExpect(jsonPath("$.data.contactDto.[1].contactType", Is.is(ContactType.PHONE.name())))
                .andExpect(jsonPath("$.data.contactDto.[2].contactType", Is.is(ContactType.ADDRESS.name())));

        employeeForHiringDTO =
                EmployeeForHiringDTO
                        .builder()
                        .email("doc14@mail.com")
                        .snils("")
                        .build();

        mockMvc.perform(post("/api/manager/employee/hiring/create")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsBytes(employeeForHiringDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Is.is(451)))
                .andExpect(jsonPath("$.text", Is.is("Данный адрес эл. почты уже зарегистрирован. Введите другой")));

        employeeForHiringDTO =
                EmployeeForHiringDTO
                        .builder()
                        .email("unique1@mail.ru")
                        .snils("")
                        .build();

        mockMvc.perform(post("/api/manager/employee/hiring/create")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsBytes(employeeForHiringDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Is.is(453)))
                .andExpect(jsonPath("$.text", Is.is("Snils не был передан")));

        employeeForHiringDTO =
                EmployeeForHiringDTO
                        .builder()
                        .email("unique1@mail.ru")
                        .snils("313131")
                        .roleId(646L)
                        .build();

        mockMvc.perform(post("/api/manager/employee/hiring/create")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsBytes(employeeForHiringDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Role с данным id не существует")));

        EmployeeForHiringDTO employeeForHiringDTO_2 =
                EmployeeForHiringDTO
                        .builder()
                        .email("udani@mail.ru")
                        .snils("1ada2313")
                        .positionId(120L)
                        .roleId(300L)
                        .laborContractForHiringDto(
                                LaborContractForHiringDto
                                        .builder()
                                        .startDate("01.06.2023")
                                        .part(BigDecimal.valueOf(1))
                                        .build())
                        .build();

        mockMvc.perform(post("/api/manager/employee/hiring/create")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsBytes(employeeForHiringDTO_2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Позиция по id не существует")));


        employeeForHiringDTO_2 =
                EmployeeForHiringDTO
                        .builder()
                        .email("udani@mail.ru")
                        .snils("1ada2313")
                        .diplomaForHiringDto(
                                DiplomaForHiringDto
                                        .builder()
                                        .universityId(
                                                100L)
                                        .build())
                        .positionId(102L)
                        .roleId(
                               300L)
                        .laborContractForHiringDto(
                                LaborContractForHiringDto
                                        .builder()
                                        .startDate("01.06.2023")
                                        .part(BigDecimal.valueOf(1))
                                        .build())
                        .build();

        mockMvc.perform(post("/api/manager/employee/hiring/create")
                        .header("Authorization", accessToken)
                        .content(objectMapper.writeValueAsBytes(employeeForHiringDTO_2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", Is.is(450)))
                .andExpect(jsonPath("$.text", Is.is("Университета по id не существует")));

        Mockito.verify(mailSender, Mockito.times(1)).send(smm);
    }
}
