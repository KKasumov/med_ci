
4.ECONOMIST TASKS


*****************4.1 Главная страница экономиста*****************


*****4.1.1 экономист получает информацию о себе*****
EconomistRestController GET "api/economist/main"

- экономиста получаем из авторизации

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


*****************4.2 страница работы со СМО*****************


*****4.2.1 экономист получает все СМО*****
EconomistSmoRestController GET "api/economist/smo/get/by/parameters"

- найти все смо которые удовлетворяют параметрам
- pattern имеет дефолтное значение = ""
- если region isNull значит не учитывать его

request:
    @Nullable Region region;
    String pattern;

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


*****4.2.2 экономист сохраняет новую СМО*****
EconomistSmoRestController POST "api/economist/smo/add/new"

request:
    NewSmoDto;

    NewSmoDto {
        String name;
        Region region;
        String startDate;           //format dd.MM.yyyy
        @Nullable String endDate;   //format dd.MM.yyyy
        String code;
    }

response:
    SmoDto;

    SmoDto {
        long id;
        String name;
        Region region;
        String startDate;           //format dd.MM.yyyy
        @Nullable String endDate;   //format dd.MM.yyyy
        String code;
    }


*****4.2.3 экономист удаляет СМО*****
EconomistSmoRestController DELETE "api/economist/smo/{smoId}/delete"

- необходимо проверить что такая смо есть
- необходимо проверить что нет ни одного полиса с указанной смо
- необходимо проверить что нет счетов связанных с смо
- необходимо проверить что нет муниципальных заказов связанных с смо
- необходимо удалить счета в банках этой смо

request:
    long smoId;

response:
    -


*****************4.3 страница работы с УЕТ*****************


*****4.3.1 экономист получает все цены УЕТ*****
EconomistYetRestController GET "api/economist/yet/get/all"

request:
    -

response:
    List<YetDto>;

    YetDto {
        long id;
        BigDecimal price;
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
    }


*****4.3.2 экономист удаляет цену УЕТ*****
EconomistYetRestController DELETE "api/economist/yet/{yetId}/delete"

request:
    long yetId;

response:
    -


*****4.3.3 экономист сохраняет новую цену УЕТ*****
EconomistYetRestController POST "api/economist/yet/add/new"

request:
    NewYetDto

    NewYetDto {
        BigDecimal price;
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
    }

response:
    List<YetDto>;

    YetDto {
        long id;
        BigDecimal price;
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
    }


*****4.3.4 экономист модифицирует цену УЕТ*****
EconomistYetRestController PATCH "api/economist/yet/edit"

- необходимо убедиться что такая цена ует существует

request:
    YetDto;

    YetDto {
        long id;
        BigDecimal price;
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
    }

response:
    YetDto;

    YetDto {
        long id;
        BigDecimal price;
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
    }


*****4.3.5 экономист проверяет цены УЕТ*****
EconomistYetRestController GET "api/economist/yet/check"

- необходимо убедиться что цены на УЕТ не накладываются друг на друга и не имеют разрывов между собой
- необходимо убедиться что в даты когда есть медицинские услуги оплачиваемые по ОМС есть цена на УЕТ

request:
    -

response:
    List<String> messages; //вернуть текстовые описания ошибок которые помогут помочь пользователю понять где они


*****************4.4 страница работы с медицинскими услугами*****************


*****4.4.1 экономист получает все медицинские услуги*****
EconomistServicesRestController GET "api/economist/service/find/by/parameters"

- если передано отделение то необходимо проверить что оно существует и это медицинское отделение
- метод принимает строковый паттерн поиска медицинских услуг и id отдления
- pattern имеет дефолтное значание = ""
- должен вернуть медицинские услуги у которых pattern входит в идентификатор или в имя
- если паттерн isNull то необходимо вернуть все медицинские услуги
- если передан departmentId то услуги находятся только из переданного отделения


request:
    String pattern;
    @Nullable Long departmentId;

response:
    List<MedicalServiceDto>;

    MedicalServiceDto {
        long id;
        String identifier;
        String name;
        boolean isDisabled;
        @Nullable DepartmentDto;
    }
    DepartmentDto {
        long id;
        String name;
        AgeType ageType;
    }


*****4.4.2 экономист сохраняет новую медицинскую услугу*****
EconomistServicesRestController POST "api/economist/service/add/new"

- необходимо проверить что нет услуг с переданным идентификатором

request:
    NewMedicalServiceDto;

    NewMedicalServiceDto {
        String identifier;
        String name;
        boolean isDisabled;
    }

response:
    MedicalServiceDto;

    MedicalServiceDto {
        long id;
        String identifier;
        String name;
        boolean isDisabled;
        @Nullable DepartmentDto;
    }
    DepartmentDto {
        long id;
        String name;
        AgeType ageType;
    }


*****4.4.3 экономист блокирует медицинскую услугу*****
EconomistServicesRestController PATCH "api/economist/service/{medicalServiceId}/block"

- необходимо проверить что такая услуга существует

request:
    long medicalServiceId;

response:
    MedicalServiceDto;

    MedicalServiceDto {
        long id;
        String identifier;
        String name;
        boolean isDisabled;
        @Nullable DepartmentDto;
    }
    DepartmentDto {
        long id;
        String name;
        AgeType ageType;
    }


*****4.4.4 экономист разблокирует медицинскую услугу*****
EconomistServicesRestController PATCH "api/economist/service/{medicalServiceId}/unBlock"

- необходимо проверить что такая услуга существует

request:
    long medicalServiceId;

response:
    MedicalServiceDto;

    MedicalServiceDto {
        long id;
        String identifier;
        String name;
        boolean isDisabled;
        @Nullable DepartmentDto;
    }
    DepartmentDto {
        long id;
        String name;
        AgeType ageType;
    }


*****4.4.5 экономист удаляет медицинскую услугу*****
EconomistServicesRestController DELETE "api/economist/service/{medicalServiceId}/delete"

- необходимо проверить что такая услуга существует
- необходимо проверить что услуга не связана ни с одним отделением
- необходимо убедиться что нет посещений связанных с услугой
- необходимо удалить все цены медицинской услуги

request:
    long medicalServiceId;

response:
    -


*****4.4.6 экономист получает цены медицинской услуги*****
EconomistServicesPriceRestController GET "api/economist/service/{medicalServiceId}/price/get"

- необходимо проверить что такая услуга существует

request:
    long medicalServiceId;

response:
    MedicalServicePriceDto;

    MedicalServicePriceDto {
        long medicalServiceId;
        List<OmsPriceOfMedicalServiceDto>;
        List<PayPriceOfMedicalServiceDto>;
    }
    OmsPriceOfMedicalServiceDto {
        long id;
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
        BigDecimal yet;
    }
    PayPriceOfMedicalServiceDto {
        long id;
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
        BigDecimal money;
    }


*****4.4.7 экономист добавляет омс цену медицинской услуге*****
EconomistServicesPriceRestController POST "api/economist/service/{medicalServiceId}/price/oms/add"

- необходимо проверить что такая услуга существует

request:
    long medicalServiceId;
    NewOmsPriceOfMedicalServiceDto;

    NewOmsPriceOfMedicalServiceDto {
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
        BigDecimal yet;
    }

response:
    OmsPriceOfMedicalServiceDto;

    OmsPriceOfMedicalServiceDto {
        long id;
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
        BigDecimal yet;
    }


*****4.4.8 экономист добавляет цену платной медицинской услуге*****
EconomistServicesPriceRestController POST "api/economist/service/{medicalServiceId}/price/pay/add"

- необходимо проверить что такая услуга существует

request:
    long medicalServiceId;
    NewPayPriceOfMedicalServiceDto;

    NewPayPriceOfMedicalServiceDto {
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
        BigDecimal money;
    }

response:
    PayPriceOfMedicalServiceDto;

    PayPriceOfMedicalServiceDto {
        long id;
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
        BigDecimal money;
    }


*****4.4.9 экономист модифицирует омс цену медицинской услуги*****
EconomistServicesPriceRestController PATCH "api/economist/service/{medicalServiceId}/price/oms/edit"

- необходимо проверить что такая услуга существует
- необходимо проерить что такая цена существует

request:
    long medicalServiceId;
    OmsPriceOfMedicalServiceDto;

    OmsPriceOfMedicalServiceDto {
        long id;
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
        BigDecimal yet;
    }

response:
    OmsPriceOfMedicalServiceDto
    
    OmsPriceOfMedicalServiceDto {
        long id;
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
        BigDecimal yet;
    }


*****4.4.10 экономист модифицирует цену платной медицинской услуге*****
EconomistServicesPriceRestController PATCH "api/economist/service/{medicalServiceId}/price/pay/edit"

- необходимо проверить что такая услуга существует
- необходимо проерить что такая цена существует

request:
    long medicalServiceId;
    PayPriceOfMedicalServiceDto;

    PayPriceOfMedicalServiceDto {
        long id;
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
        BigDecimal money;
    }

response:
    PayPriceOfMedicalServiceDto

    PayPriceOfMedicalServiceDto {
        long id;
        String dayFrom;         //format dd.MM.yyyy
        @Nullable String dayTo; //format dd.MM.yyyy
        BigDecimal money;
    }


*****4.4.11 экономист удаляет омс цену медицинской услуги*****
EconomistServicesPriceRestController DELETE "api/economist/service/{medicalServiceId}/price/oms/{omsPriceId}/delete"

- необходимо проверить что такая услуга существует
- необходимо проерить что такая цена существует

request:
    long medicalServiceId;
    long omsPriceId;

response:
    -


*****4.4.12 экономист удаляет цену платной медицинской услуги*****
EconomistServicesPriceRestController DELETE "api/economist/service/{medicalServiceId}/price/pay/{payPriceId}/delete"

- необходимо проверить что такая услуга существует
- необходимо проерить что такая цена существует

request:
    long medicalServiceId;
    long payPriceId;

response:
    -


*****4.4.13 экономист делает проверку цен медицинской услуги*****
EconomistServicesPriceRestController GET "api/economist/service/{medicalServiceId}/price/check"

- необходимо проверить что такая услуга существует
- необходимо проверить что нет накладок цен за весь период существования организации. Разрывы могут быть.
- не забывай что есть цена окончания и она может быть null

- Если обе даты не переданы - проверку не запускать
- необходимо принять два парметра дней, интревал не должен быть более 30 дней. 
- если один параметр присутствует а второй отсутствует - сделай максимальное количество дней
- необходимо проверить что в эти дни когда используется цена услуги с оплатой по омс, есть цена омс
- необходимо проверить что в те дни когда оказывалась текущая услуга, существует цена с соответствующим типом оплаты 

request:
    long medicalServiceId;
    @Nullable String dayStart; //datePattern dd.mm.yyyy
    @Nullable String dayEnd; //datePattern dd.mm.yyyy

response:
    List<String> messages //уведомления с ошибками для пользователя, что бы он смог понять, где есть ошибка


*****************4.5 страница работы с заболеваниями*****************


*****4.5.1 экономист получает все заболевания*****
EconomistDiseaseRestController GET "api/economist/disease/find/by/parameters"

- если передано отделение то необходимо проверить что оно существует и это медицинское отделение
- метод принимает строковый паттерн поиска заболеваний и id отдления
- должен вернуть заболевания у которых pattern входит в идентификатор или в имя
- pattern иммеет дефолтное значение = ""
- если передан departmentId то заболевания находятся только из переданного отделения

request:
    String pattern;
    @Nullable Long departmentId;

response:
    List<DiseaseDto>;

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


*****4.5.2 экономист сохраняет новое заболевание*****
EconomistDiseaseRestController POST "api/economist/disease/add/new"

- необходимо проверить что идентификатор не занят

request:
    NewDiseaseDto;

    NewDiseaseDto {
        String identifier;
        String name;
        boolean isDisabled;
        AgeType ageType;
    }

response:
    DiseaseDto;

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


*****4.5.3 экономист удаляет заболевание*****
EconomistDiseaseRestController DELETE "api/economist/disease/{diseaseId}/delete"

- необходимо проверить что заболевание существует
- необходимо проверить что заболевание не используется каким-либо отделением
- необходимо проверить что заболеванием не используется ни в одном обращении

request:
    long diseaseId;

response:
    -


*****4.5.4 экономист блокирует заболевание*****
EconomistDiseaseRestController PATCH "api/economist/disease/{diseaseId}/block"

- необходимо проверить что заболевание существует
- установить флаг isDisabled = true

request:
    long diseaseId;

response:
    DiseaseDto;

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


*****4.5.5 экономист разблокирует заболевание*****
EconomistDiseaseRestController PATCH "api/economist/disease/{diseaseId}/unBlock"

- необходимо проверить что заболевание существует

request:
    long diseaseId;

response:
    DiseaseDto;

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


*****************4.6 страница работы с отделениями*****************


*****4.6.1 Экономист получает все медицинские отделения по параметрам*****
EconomistDepartmentRestController GET "api/economist/department/get/medical/by/parameters"

- если передан строковый паттерн надо найти отделения у которых паттерн входит в имя
- pattern имеет дефолтное значение = ""
- если передан AgeType то необходимо получить отделения соответсвующего типа

request:
    @Nullable AgeType ageType;
    String pattern;

response:
    List<DepartmentDto>;

    DepartmentDto {
        long id;
        String name;
        AgeType ageType;
    }


*****************4.7 страница работы со счетами*****************


*****4.7.1 экономист получает все счета*****
EconomistOrderRestController GET "api/economist/order/get/by/parameters"

- если параметр isNull не учитывать его
- если dateStart isNull необходимо использовать дату создания медицинской организации (она у нас одна)
- если dateEnd isNull необходимо использовать now().plusYear(1)

request:
    @Nullable InsuranceType insuranceType;
    @Nullable Boolean isFormed;
    @Nullable Boolean isAcceptedForPayment
    @Nullable Boolean isPayed
    @Nullable Long smoId;
    @Nullable String dateStart;     //format dd.MM.yyyy
    @Nullable String dateEnd;       //format dd.MM.yyyy

response:
    List<OrderDto>;

    OrderDto {
        long id;
        InsuranceType insuranceType;
        @Nullable String comment;
        String date;           //format dd.MM.yyyy
        @Nullable BigDecimal money;
        boolean isFormed;
        boolean isAcceptedForPayment;
        boolean isPayed;
        SmoDto smo;
    }
    SmoDto {
        long id;
        String name;
        Region region;
        String startDate;           //format dd.MM.yyyy
        @Nullable String endDate;   //format dd.MM.yyyy
        String code;
    }