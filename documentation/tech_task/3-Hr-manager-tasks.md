
3.HR MANAGER TASKS


*****************3.1 Главная страница кадровика*****************


*****3.1.1 кадровик получает информацию о себе*****
HrManagerRestController GET "api/manager/main"

- кадровика получаем из авторизации

request:
    -

response:
    UserDto;

    UserDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****************3.2 кадровик работает со штатным расписанием медучреждения*****************


*****3.2.1 кадровик получает медучреждение с отделениями*****
HrManagerOrganizationRestController GET "api/manager/organization/get/with/departments"

- ожидается что у нас только одна организация в базе

request:
    -

response:
    OrganizationWithDepartmentsDto;

    OrganizationWithDepartmentsDto {
        long id;
        String code;
        String name;
        String legalAddress;
        String ogrn;
        String startDate;           //format dd.MM.yyyy
        @Nullable String endDate;   //format dd.MM.yyyy
        BigDecimal fullEmploymentStatusRange;
        @Nullable UserDto director;
        @Nullable UserDto ioDirector;
        List<DepartmentWithChiefDto> departments;
    }
    DepartmentWithChiefDto {
        long id;
        String name;
        AgeType ageType;
        @Nullable UserDto chiefDoctor;
        @Nullable UserDto ioChiefDoctor;
    }
    UserDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****3.2.2 кадровик редактирует медучреждение*****
HrManagerOrganizationRestController PATCH "api/manager/organization/update"

- необходимо проверить что организация существует

request:
    MedicalOrganizationDto;

    MedicalOrganizationDto {
        long id;
        String code;
        String name;
        String legalAddress;
        String ogrn;
        String startDate;           //format dd.MM.yyyy
        @Nullable String endDate;   //format dd.MM.yyyy
        BigDecimal fullEmploymentStatusRange;
    }

response:
    OrganizationWithDirectorDto;

    OrganizationWithDirectorDto {
        long id;
        String code;
        String name;
        String legalAddress;
        String ogrn;
        String startDate;           //format dd.MM.yyyy
        @Nullable String endDate;   //format dd.MM.yyyy
        BigDecimal fullEmploymentStatusRange;
        @Nullable UserDto director;
        @Nullable UserDto ioDirector;
    }
    UserDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****3.2.3 кадровик назначает главного врача*****
HrManagerOrganizationRestController PATCH "api/manager/organization/{organizationId}/add/director/{doctorId}"

- необходимо проверить что организация существует
- необходимо проверить что доктор существует
- необходимо проверить что ид передаваемого доктора и того который занимает текущую должность разные
- необходимо проверить что у доктора роль не CHIEF_DOCTOR
- необходимо проверить что у доктора есть действующий трудовой договор
- если был старый главный врач, назначить ему роль DOCTOR
- новому главному врачу назначить роль DIRECTOR
- если главным врачом оказался ио главного врача, то сделать ио главного null

request:
    long organizationId;
    long doctorId;

response:
    OrganizationWithDirectorDto;

    OrganizationWithDirectorDto {
        long id;
        String code;
        String name;
        String legalAddress;
        String ogrn;
        String startDate;           //format dd.MM.yyyy
        @Nullable String endDate;   //format dd.MM.yyyy
        BigDecimal fullEmploymentStatusRange;
        @Nullable UserDto director;
        @Nullable UserDto ioDirector;
    }
    UserDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****3.2.4 кадровик убирает главного врача*****
HrManagerOrganizationRestController PATCH "api/manager/organization/{organizationId}/remove/director"

- необходимо проверить что организация существует
- проверить что у организации есть главный врач
- назначить главному врачу роль DOCTOR
- необходимо главному доктору выставить null

request:
    long organizationId;

response:
    OrganizationWithDirectorDto;

    OrganizationWithDirectorDto {
        long id;
        String code;
        String name;
        String legalAddress;
        String ogrn;
        String startDate;           //format dd.MM.yyyy
        @Nullable String endDate;   //format dd.MM.yyyy
        BigDecimal fullEmploymentStatusRange;
        @Nullable UserDto director;
        @Nullable UserDto ioDirector;
    }
    UserDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****3.2.5 кадровик назначает ио главного врача*****
HrManagerOrganizationRestController PATCH "api/manager/organization/{organizationId}/add/io/director/{doctorId}"

- необходимо проверить что организация существует
- необходимо проверить что доктор существует
- необходимо проверить что ид передаваемого доктора и того который занимает текущую должность разные
- необходимо проверить что у доктора роль не CHIEF_DOCTOR
- необходимо проверить что у доктора есть действующий трудовой договор
- если был старый ио главного врача, назначить ему роль DOCTOR
- новому ио главного врача назначить роль DIRECTOR
- если ио главного врача оказался главный врач, то сделать главного null

request:
    long organizationId;
    long doctorId;

response:
    OrganizationWithDirectorDto;

    OrganizationWithDirectorDto {
        long id;
        String code;
        String name;
        String legalAddress;
        String ogrn;
        String startDate;           //format dd.MM.yyyy
        @Nullable String endDate;   //format dd.MM.yyyy
        BigDecimal fullEmploymentStatusRange;
        @Nullable UserDto director;
        @Nullable UserDto ioDirector;
    }
    UserDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****3.2.6 кадровик убирает ио главного врача*****
HrManagerOrganizationRestController PATCH "api/manager/organization/{organizationId}/remove/io/director"

- необходимо проверить что организация существует
- проверить что у организации есть ио главного врача
- назначить ио главного врача роль DOCTOR
- необходимо ио главного врача выставить null

request:
    long organizationId;

response:
    OrganizationWithDirectorDto;

    OrganizationWithDirectorDto {
        long id;
        String code;
        String name;
        String legalAddress;
        String ogrn;
        String startDate;           //format dd.MM.yyyy
        @Nullable String endDate;   //format dd.MM.yyyy
        BigDecimal fullEmploymentStatusRange;
        @Nullable UserDto director;
        @Nullable UserDto ioDirector;
    }
    UserDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****3.2.7 кадровик добавляет новое отделение*****
HrManagerDepartmentRestController POST "api/manager/department/add/for/organization/{organizationId}"

- необходимо проверить что организация существует

request:
    long organizationId;
    NewDepartmentDto;

    NewDepartmentDto {
        String name;
        AgeType ageType;
    }

response:
    DepartmentWithChiefDto;

    DepartmentWithChiefDto {
        long id;
        String name;
        AgeType ageType;
        @Nullable UserDto chiefDoctor;
        @Nullable UserDto ioChiefDoctor;
    }


*****3.2.8 кадровик модифицирует отделение*****
HrManagerDepartmentRestController PATCH "api/manager/department/update"

- необходимо проверить что отделение существует
- изменение типа отделения запрещено если с ним связан хоть один доктор (не сотрудник)
- изменение типа отделения запрещено если с ним связано хоть одно заболевание
- изменение типа отделения запрещено если с ним связана хоть одна медицинская услуга

request:
    DepartmentDto;

    DepartmentDto {
        long id;
        String name;
        AgeType ageType;
    }

response:
    DepartmentWithChiefDto;

    DepartmentWithChiefDto {
        long id;
        String name;
        AgeType ageType;
        @Nullable UserDto chiefDoctor;
        @Nullable UserDto ioChiefDoctor;
    }


*****3.2.9 кадровик удаляет отделение*****
HrManagerDepartmentRestController DELETE "api/manager/department/{departmentId}/delete"

- необходимо проверить что отделение существует
- удаление запрещено если с ним связан хоть один сотрудник
- удаление запрещено если с ним связано хоть одно заболевание
- каскадно удалить связанные медицинские услуги (+каскадно удалить связанные цены ДМС и ОМС)
- каскадно удалить все связанные должности (разорвать связь с кабинетом)
PS: посещения лечатся в рамках заболевания, а если заболеваний нет, то и посещений не может быть

request:
    long departmentId;

response:
    -


*****3.2.10 кадровик назначает заведующего отделением*****
HrManagerDepartmentRestController PATCH "api/manager/department/{departmentId}/add/chief/{doctorId}"

- необходимо проверить что отделение существует
- необходимо проверить что доктор существует
- необходимо проверить что ид передаваемого доктора и того который занимает текущую должность разные
- необходимо проверить что у доктора роль не DIRECTOR
- необходимо проверить что доктор работает в этом же отделении
- необходимо проверить что у доктора есть действующий трудовой договор
- если был старый заведующий, назначить ему роль DOCTOR
- новому заведующему назначить роль CHIEF_DOCTOR
- если заведующим оказался ио заведующего, то сделать ио заведующего null

request:
    long departmentId;
    long doctorId;

response:
    DepartmentWithChiefDto;

    DepartmentWithChiefDto {
        long id;
        String name;
        AgeType ageType;
        @Nullable UserDto chiefDoctor;
        @Nullable UserDto ioChiefDoctor;
    }
    UserDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****3.2.11 кадровик назначает ио заведующего отделением*****
HrManagerDepartmentRestController PATCH "api/manager/department/{departmentId}/add/io/chief/{doctorId}"

- необходимо проверить что отделение существует
- необходимо проверить что доктор существует
- необходимо проверить что ид передаваемого доктора и того который занимает текущую должность разные
- необходимо проверить что у доктора роль не DIRECTOR
- необходимо проверить что доктор работает в этом же отделении
- необходимо проверить что у доктора есть действующий трудовой договор
- если был старый ио заведующего, назначить ему роль DOCTOR
- новому ио заведующего назначить роль CHIEF_DOCTOR
- если ио заведующего оказался заведующий, то сделать заведующего null

request:
    long departmentId;
    long doctorId;

response:
    DepartmentWithChiefDto;

    DepartmentWithChiefDto {
        long id;
        String name;
        AgeType ageType;
        @Nullable UserDto chiefDoctor;
        @Nullable UserDto ioChiefDoctor;
    }
    UserDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****3.2.12 кадровик снимает с должности заведующего отделением*****
HrManagerDepartmentRestController PATCH "api/manager/department/{departmentId}/delete/chief"

- необходимо проверить что отделение существует
- необходимо проверить что у отделения есть заведующий
- заведующему назначить роль DOCTOR
- сделать заведующего null

request:
    long departmentId;

response:
    DepartmentWithChiefDto;

    DepartmentWithChiefDto {
        long id;
        String name;
        AgeType ageType;
        @Nullable UserDto chiefDoctor;
        @Nullable UserDto ioChiefDoctor;
    }
    UserDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****3.2.13 кадровик снимает с должности ио заведующего отделением*****
HrManagerDepartmentRestController PATCH "api/manager/department/{departmentId}/delete/io/chief"

- необходимо проверить что отделение существует
- необходимо проверить что у отделения есть ио заведующего
- ио заведующего назначить роль DOCTOR
- сделать ио заведующего null

request:
    long departmentId;

response:
    DepartmentWithChiefDto;

    DepartmentWithChiefDto {
        long id;
        String name;
        AgeType ageType;
        @Nullable UserDto chiefDoctor;
        @Nullable UserDto ioChiefDoctor;
    }
    UserDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @Nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****************3.3 кадровик получает доп информацию по учреждению*****************


*****3.3.1 кадровик получает здания с кабинетами*****

HrManagerBuildingRestController GET "api/manager/building/get/all"

- предполагается что есть только одна организация в БД

request:
    -

response:
    BuildingDto;

    BuildingDto {
        long id;
        String physicalAddress;
        @Nullable List<CabinetDto> cabinets;
    }
    CabinetDto {
        long id;
        int number;
        String name;
    }


*****3.3.2 кадровик получает информацию по оборудованию*****

HrManagerEquipmentRestController GET "api/manager/equipment/get/by/parameters"

Задача такая: кадровику необходимо получать оборудование по параметрам:
- id отделения (опционально)
- id здания (опционально)
- id кабинета (опционально)
- id сотрудника (опционально)
- свободные или занятые или все (по умолчанию свободные)
- строковый паттерн входящий в имя оборудования или инвентарный номер (опционально)
оборудование должно иметь дату установки и не быть утилизированным на момент поиска



*****************3.4 кадровик работает со ставками учреждения*****************


*****3.4.1 кадровик получает информацию по ставкам в отделении*****
HrManagerPositionRestController GET "api/manager/position/get/information/by/department/{departmentId}"

- проверить наличие отделения

request:
    long departmentId

response:
    DepartmentWithPositionDto;

    DepartmentWithPositionDto {
        long id;
        String name;
        AgeType ageType;
        List<PositionDto> positions;
    }
    PositionDto {
        long id;
        String name;
        int daysForVocation;
        @Nullable CabinetWithBuildingDto cabinet;
        @Nullable EmployeeInformationDto employee;
        @Nullable WageDto wage;     //актуальная в данный момент времени ставка
    }
    CabinetWithBuildingDto {
        long cabinetId;
        int cabinetNumber;
        String cabinetName;
        long buildingId;
        String buildingPhysicalAddress;
    }
    EmployeeInformationDto {
        long employeeHistoryId;
        long laborContractId;
        List<UserDto> user;
    }
    UserDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }
    WageDto {
        long id;
        String dateStart;           //format dd.MM.yyyy
        @Nullable String dateEnd;   //format dd.MM.yyyy
        BigDecimal value;
    }


*****3.4.2 кадровик создает ставку для отделения*****
HrManagerPositionRestController POST "api/manager/position/add/for/department/{departmentId}"

- проверить наличие отделения
- проверить наличие кабинета (если передан)
- проверить наличие оборудования и что оно свободно (если передан)
- привязать новую должность к переданному отделении
- привязать переданный оклад к новой должности

request:
    long departmentId
    NewPositionDto newPosition;

    NewPositionDto {
        String name;
        int daysForVocation;
        @Nullable Long cabinetId;
        @Nullable List<Long> equipmentsId;
        NewWageDto wage;
    }
    NewWageDto {
        String dateStart;           //format dd.MM.yyyy
        @Nullable String dateEnd;   //format dd.MM.yyyy
        BigDecimal value;
    }

response:
    PositionDto;

    PositionDto {
        long id;
        String name;
        int daysForVocation;
        @Nullable CabinetWithBuildengDto cabinet;
        @Nullable EmployeeInformationDto employee;
        @Nullable WageDto wage;     //актуальная в данный момент времени ставка
    }
    CabinetWithBuildengDto {
        long cabinetId;
        int cabinetNumber;
        String cabinetName;
        long buildingId;
        String buildingPhysicalAddress;
    }
    EmployeeInformationDto {
        long employeeHistoryId;
        long laborContractId;
        UserDto user;
    }
    WageDto {
        long id;
        String dateStart;           //format dd.MM.yyyy
        @Nullable String dateEnd;   //format dd.MM.yyyy
        BigDecimal value;
    }
    UserDto {
        long id;
        String email;
        String firstName;
        String lastName;
        @nullable String patronymic;
        String birthday; //format dd.MM.yyyy
        Gender gender;
    }


*****3.4.3 кадровик редактирует оклад должности*****
HrManagerPositionRestController PATCH "api/manager/position/{positionId}"

Задача такая: кадровику необходимо уметь редактировать цену существующего оклада у должности, 
но дата окончания цены может быть null, что говорит о том, что цена действует в данный момент времени. 
Нельзя что бы в один момент времени действовало более одной цены оклада у должности.
Нельзя редактировать оклад занятой должности


*****3.4.4 кадровик добавляет цену оклада*****
HrManagerWageRestController POST "api/manager/position/{positionId}"

Задача такая: кадровику необходимо уметь редактировать цену оклад у должности, но дата окончания цены может быть null,
что говорит о том, что цена действует в данный момент времени.
Нельзя что бы в один момент времени действовало более одной цены оклада у должности.


*****3.4.5 кадровик удаляет ставку оклада*****
HrManagerWageRestController DELETE "api/manager/position/{positionId}/wage/{wageId}"

Задача такая: кадровику необходимо уметь удалять цену оклада у должности.
Нельзя удалять цену в тот период когда действовал трудовой договор по этой ставке



*****************3.5 кадровик работает с дипломами, аттестациями и университетами*****************


*****3.5.1 кадровик получает университеты*****
HrManagerUniversityRestController GET "api/manager/university/get/by/parameters"

Задача такая: кадровику необходимо уметь получать университеты по строковому паттерну входящему в имя университета


*****3.5.2 кадровик редактирует университет*****
HrManagerUniversityRestController PATCH "api/manager/university/{universityId}/update"

Задача такая: кадровику необходимо уметь редактировать университет


*****3.5.3 кадровик создает новый университет*****
HrManagerUniversityRestController POST "api/manager/university/crate"

Задача такая: кадровику необходимо уметь создавать новый университет


*****3.5.4 кадровик удаляет университет*****
HrManagerUniversityRestController DELETE "api/manager/university/{universityId}"

Задача такая: кадровику необходимо уметь удалять университет если с ним не связан ни один диплом или аттестация


*****3.5.5 кадровик получает всех врачей у которых истекает аттестация*****
HrManagerAttestationRestController GET "api/manager/attestation/attention/doctors/by/parameters"

Задача такая: кадровику необходимо иметь проверку которая принимает в параметры:
- id отделения (опционально)
- число дней (по умолчанию 30)
необходимо вернуть действующих врачей у которых на данный момент времени не истек трудовой договор 
и в период now() + переданное число дней истечет аттестация

  
*****3.5.6 кадровик создает аттестацию доктору*****
HrManagerAttestationRestController POST "api/manager/attestation/add/for/contract/{contractId}"

Задача такая: кадровику необходимо создавать доктору аттестации. 
Необходимо передать дто в котором будет информация по аттестация и id университета. Необходимо проверить что университет
существует, трудовой договор существует и его срок не истек и принадлежит доктору


*****3.5.7 кадровик удаляет аттестацию доктора*****
HrManagerAttestationRestController DELETE "api/manager/attestation/{attestationId}"

Задача такая: кадровику необходимо удалить аттестацию доктора


*****3.5.8 кадровик создает диплом сотруднику*****
HrManagerDiplomaRestController POST "api/manager/diploma/add/for/contract/{contractId}"

Задача такая: кадровику необходимо создавать сотрудникам дипломы.
Необходимо передать дто в котором будет информация по диплому и id университета. Необходимо проверить что университет
существует, трудовой договор существует, срок не истек


*****3.5.9 кадровик удаляет диплом сотрудника*****
HrManagerDiplomaRestController DELETE "api/manager/diploma/{diplomaId}"

Задача такая: кадровику необходимо удалить диплом сотрудника



*****************3.6 кадровик работает с трудовыми договорами сотрудников*****************


*****3.6.1 кадровик трудоустраивает человека*****
HrManagerContractRestController POST "api/manager/contract/create"

!!Это сложная задача!!

Задача такая: кадровику необходимо трудоустроить человека
необходимо принять дто в котором будет информация:
- email
- snils
- паспорт(IdentityDocument)
- трудовой договор (LaborContract)
- роль(Role) - опционально
- диплом(Diploma) - обязательно если передана роль
- контакты(Contact) - опционально
на основе этой информации создать юсера
обратить внимание!!!
- при создании пользователя необходимо сгенерировать случайный пароль при помощи class Generator с параметром 8 что бы никто не знал пароль
если пользователь имеет роль то
- создать Invite с class Generator с параметром 16
- выслать приглашение на эмейл пользователя

Так же необходимо убедиться что почта уникальна, все переданные параметры типа id универа или роль существуют в базе


*****3.6.2 кадровик разрывает трудовой договор*****
HrManagerContractRestController PATCH "api/manager/contract/{contractId}/close"

Задача такая: кадровику необходимо разрывать договор с сотрудником. 
- надо проверить что такое договор существует
- установить дату окончания работы(между сегодня и датой увольнения должно быть минимум 14 дней)