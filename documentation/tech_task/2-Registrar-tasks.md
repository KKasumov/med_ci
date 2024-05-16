
2.REGISTRAR TASKS


*****************2.1 Главная страница регистратора*****************


*****2.1.1 регистратор получает информацию о себе*****
RegistrarRestController GET "api/registrar/main"

- регистратора получаем из авторизации

request:
    -

response:
    CurrentRegistrarDto;

    CurrentRegistrarDto {
        long registrarId;
        String email;
        String firstName;
        String lastName;
        @nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****2.1.2 регистратор получает информацию о расписании на сегодня*****
RegistrarTalonRestController GET "api/registrar/talon/get/all/today"

- эндпоинт должен вернуть все талоны на сегодня в разрезе отделений

request:
    -

response:
    List<DepartmentWithTalonsDto>;

    DepartmentWithTalonsByDaysDto {
        Long id;
        String name;
        AgeType ageType;
        @Nullable String chiefDoctorFirstName;      //если есть исполняющий обязанности, передать сюда его данные
        @Nullable String chiefDoctorLastName;       //если есть исполняющий обязанности, передать сюда его данные
        List<TalonsByDaysDto> days
    }
    TalonsByDaysDto {
        String day; //format dd.MM.yyyy
        List<TalonDto> talons;
    }
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


*****************2.2 Страница работы с пациентами*****************


*****2.2.1 регистратор получает пациентов по параметрам*****
RegistrarPatientRestController GET "api/registrar/patient/get/all"

- эндпоинт должен вернуть пациентов по параметрам - если параметр isNull то его значение игнорируется
- вернуть максимум 20 значений

request:
    @Nullable String firstName;
    @Nullable String lastName;
    @Nullable Gender gender;
    @Nullable String snils;
    @Nullable String polisNumber;

response:
    List<PatientDto>;

    PatientDto {
        Long id;
        String email;
        String snils;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;    //format dd.MM.yyyy
        Gender gender;
        boolean isEnabled;
    }


*****2.2.2 регистратор блокирует пациента*****
RegistrarPatientRestController PATCH "api/registrar/patient/{patientId}/block/"

- необходимо проверить что такой пациент есть
- эндпоинт должен выставить поле isEnabled = false

request:
    long patientId;

response:
    PatientDto;

    PatientDto {
        Long id;
        String email;
        String snils;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;    //format dd.MM.yyyy
        Gender gender;
        boolean isEnabled;
    }


*****2.2.3 регистратор разблокирует пациента*****
RegistrarPatientRestController PATCH "api/registrar/patient/{patientId}/unblock/"

- необходимо проверить что такой пациент есть
- эндпоинт должен выставить поле isEnabled = true

request:
    long patientId;

response:
    PatientDto;

    PatientDto {
        Long id;
        String email;
        String snils;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;    //format dd.MM.yyyy
        Gender gender;
        boolean isEnabled;
    }


*****2.2.4 регистратор сохраняет нового пациента*****
RegistrarPatientRestController POST "api/registrar/patient/new/"

- необходимо проверить что email не занят
- эндпоинт должен наполнить данные юсера из документа идентификации

request:
    NewPatientDto

    NewPatientDto {
        String email;
        String snils;
        NewPolisDto polis;
        NewIdentityDocument identityDocument;
    }
    NewPolisDto {
        InsuranceType insuranceType;
        @Nullable String serial;
        String number;
        String dateStart;       //format dd.MM.yyyy
        String dateEnd;         //format dd.MM.yyyy
        long smoId;
    }
    NewIdentityDocument {
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
    PatientDto;

    PatientDto {
        Long id;
        String email;
        String snils;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;    //format dd.MM.yyyy
        Gender gender;
        boolean isEnabled;
    }


*****2.2.5 регистратор получает все талоны на которые записан пациент*****
RegistrarTalonRestController GET "api/registrar/talon/get/all/by/patient/{patientId}"

- необходимо проверить что такой пациент есть
- получить все талоны на которые записан пациент

request:
    long patientId;

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


*****2.2.6 регистратор снимает пациента с талона*****
RegistrarTalonRestController PATCH "api/registrar/talon/{talonId}/clear"

- необходимо проверить что такой талон есть
- талон не должен быть удален
- если пациент был то ему необходимо отправить письмо на мейл о событии

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


*****************2.3 Страница работы со СМО*****************


*****2.3.1 регистратор получает все СМО*****
RegistrarSmoRestController GET "api/registrar/smo/get/all"

request:
    -

response:
    List<SmoDto>;

    SmoDto {
        long id;
        String name;
        Region region;
        String startDate;           //format dd.MM.yyyy
        @Nullable String endDate;   //format dd.MM.yyyy
        String code;
    }


*****************2.4 Страница работы с документами пациентами*****************


*****2.4.1 регистратор получает все полисы пациента*****
RegistrarPolisRestController GET "api/registrar/polis/for/patient/{patientId}/get/all"

- необходимо проверить что такой пациент есть

request:
    long patientId;

response:
    List<PolisDto>;

    PolisDto {
        long id;
        InsuranceType insuranceType;
        @NUllable String serial;
        String number;
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


*****2.4.2 регистратор сохраняет существующему пациенту новый полис*****
RegistrarPolisRestController POST "api/registrar/polis/add/new/for/patient/{patientId}"

- необходимо проверить что такой пациент есть
- необходимо проверить что смо по переданному id существует
- если передается новый полис с типом ОМС, эндпоинт должен выставить всем старым полисам пациента с типом ОМС поле isDeprecated = true

request:
    long patientId;
    NewPolisDto;

    NewPolisDto {
        InsuranceType insuranceType;
        @Nullable String serial;
        String number;
        String dateStart;       //format dd.MM.yyyy
        String dateEnd;         //format dd.MM.yyyy
        long smoId;
    }

response:
    PolisDto;

    PolisDto {
        long id;
        InsuranceType insuranceType;
        @NUllable String serial;
        String number;
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


*****2.4.3 регистратор получает все документы идентификации личности*****
RegistrarIdentityRestController GET "api/registrar/identity/for/patient/{patientId}/get/all"

- необходимо проверить что такой пациент есть

request:
    long patientId;

response:
    List<IdentityDocumentDto>;

    IdentityDocumentDto {
        long id;
        IdentityDocumentType documentType;
        String serial;
        String number;
        String dateStart;       //format dd.MM.yyyy
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;        //format dd.MM.yyyy
        Gender gender;
        boolean isDeprecated;
    }


*****2.4.4 регистратор сохраняет существующему пациенту новый документ идентификации личности*****
RegistrarIdentityRestController POST "api/registrar/identity/add/new/for/patient/{patientId}"

- необходимо проверить что такой пациент есть
- при получении нового документа, старым выставить isDeprecated = true

request:
    long patientId;
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
        String serial;
        String number;
        String dateStart;       //format dd.MM.yyyy
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;        //format dd.MM.yyyy
        Gender gender;
        boolean isDeprecated;
    }


*****2.4.5 регистратор заменяет существующему пациенту снилс*****
RegistrarIdentityRestController POST "api/registrar/snils/change/for/patient/{patientId}"

- необходимо проверить что такой пациент есть

request:
    long patientId;
    String snils;

response:
    PatientDto;

    PatientDto {
        Long id;
        String email;
        String snils;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday;    //format dd.MM.yyyy
        Gender gender;
        boolean isEnabled;
    }


*****************2.5 Страница работы с талонами врачей*****************


*****2.5.1 регистратор получает отделения с врачами*****
RegistrarDepartmentRestController GET "api/registrar/department/get/all/with/doctors"

- необходимо вернуть отделения у которых AgeType детский и взрослый
- необходимо получить информацию по заведующему отделением, если есть исполняющий обязанности, то передать его информацию

request:
    -

response:
    List<DepartmentWithDoctorsForRegistrarDto>;

    DepartmentWithDoctorsForRegistrarDto {
        long id;
        String name;
        AgeType ageType;
        @Nullable String chiefDoctorFirstName;      //если есть исполняющий обязанности, передать сюда его данные
        @Nullable String chiefDoctorLastName;       //если есть исполняющий обязанности, передать сюда его данные
        @Nullable List<ContactDto> chiefContacts;   //получить только контакты телефона и телеграмма
        List<DoctorForRegistrarDto>doctors;
    }
    DoctorForRegistrarDto {
        long id;
        String firstName;
        String lastName;
        String patronymic;
        Gender gender;
        @Nullable List<ContactDto> contacts;   //получить только контакты телефона и телеграмма
    }
    ContactDto {
        long id;
        ContactType contactType;
        String value;
    }


*****2.5.2 регистратор получает талоны по параметрам*****
RegistrarTalonRestController GET "api/registrar/talon/get/by/parameters"

- если dateStart is null то используем LocalDate.now() с минимальным временем
- если dateEnd is null то используем LocalDate.now() + количество дней из сценариев
- если ageType is null то используем детские и взрослые отделения

- для уменьшения выборки есть сценарии:
- если явно не указано одно отделение, и не передан AgeType, то между датами должно быть максимум 3 дня иначе throw
- если явно не указано одно отделение, и передан ageType, то между датами должно быть максимум 7 дней иначе throw
- если явно указано отделение, то между датами должно быть максимум {patientScope} дней из параметров иначе throw
- если явно указан врач, то между датами должно быть максимум {doctorScope} дней из параметров иначе throw

- необходимо проверить что переданное отделение существует иначе throw, если параметр is null то он не влияет на выборку
- необходимо проверить что переданный врач существует иначе throw, если параметр is null то он не влияет на выборку
- параметр isFree имеет дефолтное значение true - что означает вернуть свободные от записи талоны, если вернется false значит вернуть все
- если переданы взаимоисключающие параметры, например id взрослого отделения и детский ageType - ничего делать не надо - выборка должна отработать как положено и вернуть пустую коллекцию

request:
    @Nullable Long departmentId;
    @Nullable Long doctorId;
    @Nullable String dateStart;
    @Nullable String dateEnd;
    @Nullable AgeType ageType;
    @Nullable boolean isFree; 

response:
    List<DepartmentWithTalonsByDaysDto>;

    DepartmentWithTalonsByDaysDto {
        long id;
        String name;
        AgeType ageType;
        List<TalonsByDaysDto> days;
    }
    TalonsByDaysDto {
        String day; //format dd.MM.yyyy
        List<TalonDto> talons;
    }
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


*****2.5.3 регистратор записывает пациента на свободный талон*****
RegistrarTalonRestController PATCH "api/registrar/talon/{talonId}/assigned/by/patient/{patientId}"

- необходимо проверить что талон существует
- необходимо проветить что талон свободный
- необходимо проверить что пациент существует
- после успешной записи отправить пациенту письмо на мейл с сообщением от успешной записи

request:
    long talonId;
    long patientId;

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


*****2.5.4 регистратор удаляет талон*****
RegistrarTalonRestController DELETE "api/registrar/talon/{talonId}/delete"

- необходимо проверить что талон существует
- необходимо проветить что талон свободный

request:
    long talonId;

response:
    -


*****2.5.5 регистратор создает талоны доктору на указанный диапазон дней*****
RegistrarTalonRestController POST "api/registrar/talon/add/for/doctor/{doctorId}/by/days"

- необходимо проверить что доктор существует
- если dayStart isNull то взять сегодня за основу
- если dayEnd isNull то использовать сегодня + {doctorScope} дней из пропертей включительно
- на все будние дни в диапазоне необходимо создать талоны в количестве {talons} из пропертей на каждый час начиная с 7:00
- если в какой-либо день есть хоть один талон - в этот день талоны не создавать

request:
    long doctorId;
    @Nullable String dayStart;
    @Nullable String dayEnd;

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


*****2.5.6 регистратор удаляет свободные талоны доктора в указанный диапазон дней*****
RegistrarTalonRestController DELETE "api/registrar/talon/delete/free/for/doctor/{doctorId}/by/days"

- необходимо проверить что доктор существует
- если dayStart isNull то взять сегодня за основу
- если dayEnd isNull то использовать сегодня + {doctorScope} дней из пропертей включительно
- необходимо удалить все талоны на которые не записаны пациенты
- необходимо вернуть талоны которые не были удалены из задействованного диапазона дней

request:
    long doctorId;
    @Nullable String dayStart;
    @Nullable String dayEnd;

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


*****2.5.7 регистратор перезаписывает пациента на другой талон*****
RegistrarTalonRestController PUT "api/registrar/talon/{talonId}/assigned/patient/from/old/talon/{oldTalonId}"

- необходимо проверить что принимающий талон существует
- необходимо проверить что принимающий талон свободный
- необходимо проверить что старый талон существует
- необходимо проверить что на старый талон записан пациент
- необходимо проверить что талоны из одного отделения
- если isDeleteOldTalon == true то старый талон, после того как пациент будет перезаписан, должен быть удален
- пвциенту необходимо отправить информационное письмо о событии


request:
    long talonId;
    long oldTalonId;
    @Nullable boolean isDeleteOldTalon;

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


*****2.5.8 регистратор записывает пациента в отделение на ближайший прием*****
RegistrarPatientRestController PATCH "api/registrar/patient/{patientId}/assigned/for/nearest/talon/department/{departmentId}"

- необходимо проверить что пациент существует
- необходимо проверить что отделение существует
- необходимо проверить что возраст пациента соответствует отделению
- необходимо записать пациента на ближайший прием сегодня в указанном отделении любому доктору иначе throw
- отправить пациенту письмо на мейл с информацией

request:
    long patientId;
    long departmentId;

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


*****2.5.9 регистратор автоматически распределяет пациентов доктора в диапазоне дней и удаляет талоны*****
RegistrarDoctorRestController DELETE "api/registrar/doctor/{doctorId}/assigned/patients/in/department/by/days"

- необходимо проверить что доктор существует
- если dayStart isNull то использовать сегодня с утра
- если dayEnd isNull то использовать сегодня + {doctorScope} из пропертей с максимальным временем
- необходимо удалить все свободные талоны в получившемся диапазоне дней
- необходимо перенести пациентов в том же отделении на то же время другим докторам, а освободившиеся старые талоны удалить
- все пациенты должны получить письма с оповещением об изменениях в записи
- вернуть талоны которые не получилость автоматически перенести


request:
    long doctorId;
    @Nullable String dayStart;
    @Nullable String dayEnd;

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