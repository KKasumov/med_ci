
1.PATIENT TASKS


*****************1.1 Главная страница пациента*****************


*****1.1.1 пациент получает информацию о себе*****
PatientRestController GET "api/patient/main"

- пациента получаем из авторизации

request: 
    -

response:
    CurrentPatientDto;

    CurrentPatientDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****1.1.2 пациент получает все свои документы*****
PatientRestController GET "api/patient/documents"

- пациента получаем из авторизации

request: 
    -

response:
    PatientDocsDto

    PatientDocsDto {
        String snils;       //все символы заменить на # кроме последних 4
        List<IdentityDocumentDto> identityDocuments;
        List<PolisDto> polises;
    }
    PolisDto {
        long id;
        InsuranceType insuranceType;
        @NUllable String serial;        //если есть, все символы заменить на # кроме последних 2
        String number;                  //все символы заменить на # кроме последних 4
        String dateStart;       //format dd.MM.yyyy
        String dateEnd;         //format dd.MM.yyyy
        boolean isDeprecated;
        SmoForPolisDto smo;
    }
    SmoForPolisDto {
        long id;
        String name;
        Region region;
        String code;
    }
    IdentityDocumentDto {
        long id;
        IdentityDocumentType documentType;
        String serial;          //все символы заменить на # кроме последних 2
        String number;          //все символы заменить на # кроме последних 4
        String dateStart;       //format dd.MM.yyyy
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;        //format dd.MM.yyyy
        Gender gender;
        boolean isDeprecated;
    }


*****1.1.3 пациент получает все СМО*****
PatientSmoRestController GET "api/patient/smo/get/all"

request: 
    -

response:
    List<SmoForPolisDto>;

    SmoForPolisDto {
        long id;
        String name;
        Region region;
        String code;
    }


*****1.1.4 пациент сохраняет себе новый полис*****
PatientPolisRestController POST "api/patient/polis/add/new"

- пациента получаем из авторизации
- если передается новый полис с типом ОМС, эндпоинт должен выставить всем старым полисам пациента с типом ОМС поле isDeprecated = true

request:
    NewPolisDto; 

    NewPolisDto {
        InsuranceType insuranceType;
        @Nullable String serial;
        String number;
        String dateStart;   //format dd.MM.yyyy
        String dateEnd;     //format dd.MM.yyyy
        long smoId;
    }

response:
    List<PolisDto>;

    PolisDto {
        long id;
        InsuranceType insuranceType;
        @NUllable String serial;        //если есть, все символы заменить на # кроме последних 2
        String number;                  //все символы заменить на # кроме последних 4
        String dateStart;       //format dd.MM.yyyy
        String dateEnd;         //format dd.MM.yyyy
        boolean isDeprecated;
        SmoForPolisDto smo;
    }
    SmoForPolisDto {
        long id;
        String name;
        Region region;
        String code;
    }


*****1.1.5 пациент сохраняет себе новый документ подтверждающий личность*****
PatientIdentityRestController POST "api/patient/identity/add/new"

- пациента получаем из авторизации
- при получении нового документа, старым выставить isDeprecated = true

request:
    NewIdentityDocumentDto;

    NewIdentityDocumentDto {
        IdentityDocumentType documentType;
        String serial;
        String number;
        String dateStart;       //format dd.MM.yyyy
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;        //format dd.MM.yyyy
        Gender gender;
    }

response:
    List<IdentityDocumentDto>;

    IdentityDocumentDto {
        long id;
        IdentityDocumentType documentType;
        String serial;          //все символы заменить на # кроме последних 2
        String number;          //все символы заменить на # кроме последних 4
        String dateStart;       //format dd.MM.yyyy
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;        //format dd.MM.yyyy
        Gender gender;
        boolean isDeprecated;
    }


*****1.1.6 пациент получает все свои контакты*****
PatientContactRestController GET "api/patient/contact/get/all"

- пациента получаем из авторизации

request:
    -

response:
    PatientContactDto

    PatientContactDto {
        String email;       //оставляем первые 3 символа и @домен.ru открытым, а остальные символы заменить на #
        List<ContactDto> contacts;
    }
    ContactDto {
        long id;
        ContactType contactType;
        String value;       // у номера скрыть половину символов посередине, у телеграм и адреса вторую половину символов
    }


*****1.1.7 пациент добавляет себе контакт*****
PatientContactRestController POST "api/patient/contact/add/new"

- пациента получаем из авторизации
- необходимо проверить что нет такого же контакта

request:
    NewContactDto

    NewContactDto {
        ContactType contactType;
        String value;
    }

response:
    ContactDto

    ContactDto {
        long id;
        ContactType contactType;
        String value;       // у номера скрыть половину символов посередине, у телеграм и адреса вторую половину символов
    }


*****1.1.8 пациент удаляет свой контакт*****
PatientContactRestController DELETE "api/patient/contact/{contactId}/delete"

- пациента получаем из авторизации
- необходимо проверить что контакт существует
- необходимо проверить что контакт принадлежит пациенту

request:
    long contactId;

response:
    -


//TODO метод ожидает когда будет прикручен сервис по отправке сообщений
*****1.1.9 пациент изменяет свой email*****
PatientRestController PATCH "api/patient/email/change"

- пациента получаем из авторизации
- необходимо проверить что переданный ящик не занят
- надо придумать логику с отправкой инвайта на существующий мейл для подтверждения смены мейла

request:
    String email; 

response:
    -


*****************1.2 Страница записи на прием*****************


*****1.2.1 пациент получает все отделения с врачами*****
PatientDepartmentRestController GET "api/patient/department/get/all/with/doctors"

- пациента получаем из авторизации
- необходимо вернуть только те отделения у которых AgeType соответсвует возрасту авторизованного пациента в данный момент
- необходимо вернуть только тех врачей у которых есть свободные талоны от сейчас до +patientScope дней из пропертей

request:
    - 

response:
    List<DepartmentWithDoctorsForPatientDto>;

    DepartmentWithDoctorsForPatientDto {
        long id;
        String name;
        AgeType ageType;
        List<DoctorForPatientDto>doctors;
    }
    DoctorForPatientDto {
        long id;
        String firstName;
        String lastName;
        String patronymic;
        Gender gender;
    }


*****1.2.2 пациент получает свободные талоны доктора*****
PatientTalonRestController GET "api/patient/talon/all/by/doctor/{doctorId}"

- пациента получаем из авторизации
- необходимо проверить что доктор существует
- необходимо проверить что доктор работает с возрастом пациента
- необходимо вернуть только свободные талоны от сейчас до +patientScope дней из пропертей в разрезе дней

request:
    long doctorId;

response:
    List<TalonsByDaysDto>;

    TalonsByDaysDto {
        String day; //format dd.MM.yyyy
        List<TalonDto> talons;
    }
    TalonDto {
        long id;
        String time; //dd.MM.yyyy HH:mm
        DoctorForTalonDto doctorDto
        @Nullable PatientForTalonDto patientDto; //в нашем случае всегда должно быть null
    }
    DoctorForTalonDto {
        long id;
        String firstName;
        String lastName;
        @Nullable String patronymic;
    }
    PatientForTalonDto {
        long id;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;    //format dd.MM.yyyy
    }


*****1.2.3 пациент записывается на свободный талон доктора*****
PatientTalonRestController PATCH "api/patient/talon/{talonId}/assign"

- пациента получаем из авторизации
- необходимо проверить что талон существует
- необходимо проверить что талон свободен
- необходимо проверить что дата талона действительно входит в диапазон от сейчас до +patientScope дней из пропертей
- необходимо проверить что доктор работает с возрастом пациента
- после успешной записи необходимо отправить сообщение пациенту на почту об успешной записи

request:
    long talonId;

response:
    TalonDto;

    TalonDto {
        long id;
        String time; //dd.MM.yyyy HH:mm
        DoctorForTalonDto doctorDto
        @Nullable PatientForTalonDto patientDto;
    }
    DoctorForTalonDto {
        long id;
        String firstName;
        String lastName;
        @Nullable String patronymic;
    }
    PatientForTalonDto {
        long id;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;    //format dd.MM.yyyy
    }


*****************1.3 Страница пациента и его талонов*****************


*****1.3.1 пациент получает все талоны на которые он записан*****
PatientTalonRestController GET "api/patient/talon/get/all/assigned"

- пациента получаем из авторизации
- необходимо вернуть все талоны на которые записан пациент

request:
    -

response:
    List<TalonDto>;

    TalonDto {
        long id;
        String time; //dd.MM.yyyy HH:mm
        DoctorForTalonDto doctorDto
        @Nullable PatientForTalonDto patientDto;
    }
    DoctorForTalonDto {
        long id;
        String firstName;
        String lastName;
        @Nullable String patronymic;
    }
    PatientForTalonDto {
        long id;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;    //format dd.MM.yyyy
    }


*****1.3.2 пациент снимается с записи на талон*****
PatientTalonRestController PATCH "api/patient/talon/{talonId}/unAssign"

- пациента получаем из авторизации
- необходимо проверить что талон существует
- необходимо проверить что именно этот пациент записан на него
- необходимо освободить талон от пациента не удаляя его
- после успешной операции необходимо отправить сообщение пациенту на почту

request:
    long talonId;

response:
    TalonDto;

    TalonDto {
        long id;
        String time; //dd.MM.yyyy HH:mm
        DoctorForTalonDto doctorDto
        @Nullable PatientForTalonDto patientDto;
    }
    DoctorForTalonDto {
        long id;
        String firstName;
        String lastName;
        @Nullable String patronymic;
    }
    PatientForTalonDto {
        long id;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;    //format dd.MM.yyyy
    }


*****************1.4 Страница работы с обращениями по заболеванию*****************


*****1.4.1 пациент получает все свои обращения по заболеваниям*****
PatientAppealRestController GET "api/patient/appeal/get/all"

- пациента получаем из авторизации

request:
    -

response:
    List<AppealForPatientDto>;

    AppealForPatientDto {
        long id;
        @Nullable String closedDate; //dd.MM.yyyy HH:mm
        InsuranceType insuranceType;
        String diseaseName;
    }


*****1.4.2 пациент получает полную информацию по своему обращению*****
PatientAppealRestController GET "api/patient/appeal/{appealId}/get"

- пациента получаем из авторизации
- проверяем существует ли такое обращение
- проверяем принадлежит ли обращение пациенту
- для каждого посещения необходмо посчитать стоимость (в зависимости от типа обращения: платно или по ОМС)

request:
    long appealId;

response:
    AppealForPatientFullDto;

    AppealForPatientFullDto {
        long id;
        @Nullable String closedDate; //dd.MM.yyyy HH:mm
        InsuranceType insuranceType;
        DiseaseDto disease;
        List<VisitForPatientFullDto> visits;
        BigDecimal money;
    }
    DiseaseDto {
        long id;
        String identifier;
        String name;
        boolean isDisabled;
        AgeType ageType;
        @Nullable DepartmentDto;
    }
    DepartmentDto {
        long id;
        String name;
        AgeType ageType;
    }
    VisitForPatientFullDto {
        long id;
        String dayOfVisit;      //dd.MM.yyyy HH:mm
        DoctorForTalonDto doctor;
        List<MedicalServiceDto> medicalServices;
        BigDecimal money;
    }
    DoctorForTalonDto {
        long id;
        String firstName;
        String lastName;
        @Nullable String patronymic;
    }
    MedicalServiceDto {
        long id;
        String identifier;
        String name;
    }