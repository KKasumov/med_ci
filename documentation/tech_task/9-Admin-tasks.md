
9.ADMIN TASKS

*****************9.1 Главная страница администратора*****************


*****************9.2 Администратор работает со структурой учреждения*****************


*****9.2.1 администратор получает здания учреждения*****
AdministratorBuildingRestController GET "api/administrator/building/get/all"

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


*****9.2.2 администратор добавляет здание*****
AdministratorBuildingRestController POST "api/administrator/building/add"

request:
    NewBuildingDto

    NewBuildingDto {
        String physicalAddress;
        long medicalOrganizationId;
        @Nullable List<NewCabinetDto> cabinets;
    }
    NewCabinetDto {
        int number;
        String name;
    }

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


*****9.2.3 администратор модифицирует здание*****
AdministratorBuildingRestController PATCH "api/administrator/building/{buildingId}/modify"

- убедиться что здание существует
- метод умеет модифицировать уже созданное здание и его кабинеты

request:
    long buildingId;
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


*****9.2.4 администратор создает новые кабинеты*****
AdministratorBuildingRestController POST "api/administrator/building/{buildingId}/add/cabinets"

request:
    long buildingId;
    List<NewCabinetDto>;

    NewCabinetDto {
        int number;
        String name;
    }

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


*****9.2.5 администратор удаляет кабинеты*****
AdministratorBuildingRestController DELETE "api/administrator/building/{buildingId}/delete/cabinets"

- убедиться что здание существует
- проверить что кабинеты существуют
- проверить что нет должностей которые бы имели рабочие места в этих кабинетах (кабинеты без должностей удалить а с должностями нет)

request:
    long buildingId;
    List<Long> cabinetsIds;

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


*****9.2.6 администратор удаляет здание*****
AdministratorBuildingRestController DELETE "api/administrator/building/{buildingId}/delete"

- убедиться что здание существует
- каскадно удалить кабинеты
- проверить что нет должностей которые бы имели рабочие места в этих кабинетах (прервать удаление)

request:
    long buildingId;

response:
    -


****************9.3 Администратор работает с лицензиями учреждения*****************


*****9.3.1 администратор выполняет crud операции с лицензиями*****
AdministratorLicenseRestController 

- необходимо реализовать создание, чтение, обновление и удаление лицензий ЛПУ