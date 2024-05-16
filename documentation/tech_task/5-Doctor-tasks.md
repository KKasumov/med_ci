
5.DOCTOR TASKS


*****************5.1 Главная страница доктора*****************


*****5.1.1 доктор получает информацию о себе*****
DoctorRestController GET "api/doctor/main"

- доктора получаем из авторизации

request:
    -

response:
    CurrentDoctorDto;

    CurrentDoctorDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****5.1.2 доктор получает свои талоны на сегодня*****
DoctorTalonRestController GET "api/doctor/talon/get/today"

- доктора получаем из авторизации

request:
    -

response:
    TalonsByDaysDto;

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


*****************5.2 страница работы с талонами*****************


*****5.2.1 доктор получает свой талон*****
DoctorTalonRestController GET "api/doctor/talon/{talonId}/get"

- доктора получаем из авторизации
- проверить что талон существует
- проверить что талон принадлежит доктору

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


*****5.2.2 доктор получает свои талоны в диапазоне дней*****
DoctorTalonRestController GET "api/doctor/talon/get/for/days"

- доктора получаем из авторизации
- если dateStart isNull то используем сегодня
- если dateEnd isNull то высчитываем день равный сегодня + doctorScope из пропертей

request:
    @Nullable String dateStart;   //dd.MM.yyyy
    @Nullable String dateEnd;     //dd.MM.yyyy

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


*****5.2.3 доктор удаляет свой талон*****
DoctorTalonRestController DELETE "api/doctor/talon/{talonId}/delete"

- доктора получаем из авторизации
- необходимо проверить наличие талона
- необходимо проверить что талон принадлежит авторизованному доктору
- необходмо проверить что на талон не записан пациент

request:
    long talonId;

response:
    -


*****5.2.4 доктор создает новый талон*****
DoctorTalonRestController POST "api/doctor/talon/add/new"

- доктора получаем из авторизации
- необходимо проверить наличие талона на это время

request:
    String time; //dd.MM.yyyy HH:mm

response:
    TalonDto;

    TalonDto {
        long id;
        String time; //dd.MM.yyyy HH:mm
        DoctorForTalonDto doctorDto
        @Nullable PatientForTalonDto patientDto;
    }


*****5.2.5 доктор закрепляет талон за пациентом*****
DoctorTalonRestController PATCH "api/doctor/talon/{talonId}/add/patient/{patientId}"

- доктора получаем из авторизации
- необходимо проверить наличие талона
- необходимо проверить что талон принадлежит авторизованному доктору
- необходимо проверить что талон свободен от пациента
- необходимо проверить наличие пациента

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


*****5.2.6 доктор освобождает талон от пациента*****
DoctorTalonRestController PATCH "api/doctor/talon/{talonId}/clear/patient"

- доктора получаем из авторизации
- необходимо проверить наличие талона
- необходимо проверить что талон принадлежит авторизованному доктору

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


*****5.2.7 доктор создает новые талоны на указанный диапазон дней*****
DoctorTalonRestController POST "api/doctor/talon/add/new/for/days"

- доктора получаем из авторизации
- если dateStart isNull то используем сегодня
- если dateEnd isNull то высчитываем день равный сегодня + doctorScope из пропертей
- субботу и воскресение не наполняем
- если в какой-либо день есть хотя бы один талон, игнорируем этот день
- каждый день наполняем количество равное talonsOnDay из пропертей каждый час, начинаем с 7 утра.

request:
    @Nullable String dateStart;   //dd.MM.yyyy
    @Nullable String dateEnd;     //dd.MM.yyyy

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


*****5.2.8 доктор удаляет свободные талоны в указанный диапазон дней*****
DoctorTalonRestController DELETE "api/doctor/talon/delete/for/days"

- доктора получаем из авторизации
- если dateStart isNull то используем сегодня
- если dateEnd isNull то высчитываем день равный сегодня + doctorScope из пропертей
- если у какого-либо талона есть записанный пациент, этот талон не удаляем
- возвращаем неудаленные талоны

request:
    @Nullable String dateStart;   //dd.MM.yyyy
    @Nullable String dateEnd;     //dd.MM.yyyy

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