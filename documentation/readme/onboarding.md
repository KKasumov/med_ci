

**__посадка на проект__**

проект представляет из себя:

- в model находятся модели, а именно:
entity - сущности, разложенные по группам
enums - енамы
exception - исключения которые мы будем кидать, что бы потом ловить их в GlobalRestExceptionHandler
dto - дто, разложенные по группам и конверторы для каждой сущности которую конвертируем
response - мы будем возвращать свой кастомный респонс
- в repository находятся репозитории для всех сущностей разложенные по группам
- в service находятся сервисы сущностей и дто разложенные по группам.
смысл сервиса такой: каждый сервис работает с бизнес-логикой своей сущности,
смысл сервиса дто такой: каждый контроллер возвращает Response<Dto> то есть в контроллере будем инжектить сервис дто, 
а в нем будем инжектить нужные нам сервисы сущностей, конверторы и т.д.
- в controller находятся контроллеры разложенные по ролям. Суть контроллера - это точка входа для каждой роли и валидация входных параметров,
но что бы мы не запутались в методах контроллера (эндпойнтах) мы создадим отдельный контроллер для каждой роли, логический работающий с сущностью.
Так же тут находится GlobalRestExceptionHandler, в котором есть ExceptionHandler-ы которые отлавливают определенный тип эксепшенов
и заворачивают их в нужные бэд респонсы


**__Code Style__**
- Убрать импорт \*
- Отступы должны быть 4 пробела
- Помощь по настройкам находится в stack.md


**__ЧТО НАДО, ЧТО БЫ ВЗЛЕТЕЛО ?__**
- 
- склонировать проект
- установить докер на ПК
- из директории documentation/devops/local собрать контейнер 
- если есть технические ограничения или озу менее 8GB ставьте PostgreSQL физически на комп + PgAdmin и создайте там 2 базы с настройками из проперти файла
- запускаем приложение
- запускаем все тесты


**__И ЧТО С ЭТИМ ДЕЛАТЬ ДАЛЬШЕ ?__**
-
- принцип дальнейшей разработки следующий:
- 
- 
- выполняешь задачу:
-
- если ты хочешь править какой-либо метод, сначала убедись, что он нигде не используется, а если используется - ты не портишь его. 
- если с чем то не согласен в коде - вешай //todo с описанием проблемы. Мы потом починим. 
- не надо править код, который явно не относится к твоей задаче (его возможно кто-то уже делает).
- весь код должен быть покрыт тестами (если в мастере чужие тесты не падают, а у тебя упали, скорее всего ты их сломал(а)). 
- чужие тесты трогать можно ТОЛЬКО если ты явно понимаешь что ты меняешь функционал и, следовательно, тесты, а не пытаешься починить внезапно упавший тест
- 
- PS: чаще всего:  
- 
- например пациент должен получать талоны по указанному отделению, создадим PatientTalonRestController с маппингом "api/patient/talon",
- в нем создадим эндпоинт с GET маппингом "/get/all/by/department/{departmentId}"
- в нем используя нужные сервисы провалидируем входные параметры, например id отделения, вызвав у DepartmentService метод isExist(id);
- и если метод вернет false, кинем EntityNotFoundException с текстом "Переданное отделение не найдено"
- далее передадим процесс выполнения в TalonDtoService так как мы планируем возвращать дто талонов методу getTalonsByDepartment(id),
- в TalonDtoService инжектим нужные сервисы, используем нужные методы, которые в свою очередь используют нужные репозитории,
- далее инжектим TalonDtoConvertor и используем (или пишем метод) метод конвертации сущностей в дто, которые вернем в контроллер,
- в контроллере оборачиваем все в респонс
- *(TalonDtoService будет содержать в себе бизнес логику различных манипуляций с талонами в рамках приложения
- например получения отдельных талонов с областью видимости пациента связанных с конкретным отделением и конвертации этого всего добра в нужные дто.
- Обрати внимане TalonService содержит в себе логику работы с сущностью, репозиторий только выполняет манипуляции работы с базой.
- С репозторием работает только его сервис, а сервисДто связывается с нужными сервисами и компонентами. 
- Это выделение отдельного слоя спасет от циклических зависимостей)
- пишем тесты. 
- Наполняем базу тестовыми данными (не забываем про авторизуемого пользователя), в тесте получает токен авторизации 
- и подкладываем его в хедер в запросе и проверяем каждое поле, что оно действительно то которое мы ожидали. 
- Если наш метод может кидать какой-либо эксепшн, мы должны проверить эти кейсы
-
- не надо сразу пытаться сделать какой-то идеальный вариант решения
- первые задачи надо попытаться решить "в лоб" хотя бы как то
- когда ты решил(а) задачу хотя бы как то и понял(а) все нюансы получения тех или иных сущностей, можешь рефакторить код и наводить красоту
- 
- когда твоя задача (как тебе кажется) выполнена:
-  

-
**__ТЕСТЫ__**
- 
- все задачи должны быть покрыты тестами.
- задача теста - проверить, работает ли наш код так как мы задумали и защитить его от непреднамеренных изменений
- без тестов приходилось бы запускать приложение и проверять все кейсы, что просто нереально.
- каждый тест должен проверять отдельный кейс 
- тест содержит проверку выполнения нашего кода при заведомо известных вводых данных.
- для того что бы наполнить базу тестовыми данными - надо создать sql скрипт с именем эндпойнта который будем тестить и положить его в ресурсы тестов в директорию с именем контроллера
- !!ВАЖНО!! Помни про сиквенсы - они стартуют с 1, поэтому во избежание конфликтов делай id сущностей в подложенных данных больше 5000. Не надо начинать с 1
- так же там необходимо положить скрипт очистки всех таблиц в которых будут данные после выполнения работы эндпойнта
- !! ВНИМАНИЕ !! проверяем внимательно очистку таблиц - если что-то осталось, могут ломаться чужие тесты. Ошибку найти сложно.
- для наполнения данных необходимо вспомнить про констрейнты и то, как осуществляется связь сущностей в таблицах
- так же не забудь положить в таблицу пользователя и роль которые нужны для авторизации
- все тесты унаследованы от ContextIT - в котором уже есть основные связанные бины.
- получаем токен через утилиту tokenUtil и подкладываем его в хедер при тестовом запросе
- обрати внимание на твой эндпоинт - какой тип запроса и как передаются параметры. От этого зависит как ты будешь передавать их в тесте
- что бы посмотреть возвращаемый респонс - включи строку .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString())) но не забудь закомментирвать ее перед отправкой на проверку
- выполняешь тест и смотришь на json - если там все то что ты ожидаешь, надо описать ожидания.
- если возвращается коллекция, не забудь проверить количество объектов
- если все хорошо, надо проверить все сценарии, особенно которые кидают исключения
- в тесте необходимо проверять особенно приграничные значения, значения за диапазоном, отработку с/без параметров которые опциональны
- если тест модифицирует какие-то данные, но в респонсе этого не видно, необходимо используя методы Assertions и делать запрос к базе который позволит явно убедиться что данные изменены
- если метод возвращает коллекцию значений - то необходимо вернуть минимум 2 значения. Если может вернуть 0, то надо проверить и это
- в общем, чем точнее описан тест - тем меньше вероятность ошибки при рефакторинге.
- 
- мокито?
- есть треугольник тестирования, который гласит - делайте много модульных тестов, которые не обращаются к базе - они быстрые
- мы пишем тесты, которые обращаются к базе потому что они сложнее, а вам надо тренироваться
- но есть отдельный момент который связан со взаимодействием с внешними сервисами - в рамках нашего теста мы не можем быть уверены, что эти сервисы доступны и там есть нужные нам данные
- мы должны "мокать" обращение к таким сервиса и задавать поведение. Необходимо проверять все различные варианты ответа
- например, при сохранении регистратором нового пациента нам необходимо "замокать" сервис отправки письма и задать поведение - "ничего не делать",
- но при этом валидировать что сервис вызывался и пытался отправить приглашение на почту с интересующими нас параметрами
- 
- дебаг
- пользуйся брекпойнтами в тесте, не пиши sout!
- используя дебаг можно посмотреть в какой момент времени в какой переменной какие данные
- можно понять примерно место где вылетает эксепшн
- когда выполнение теста стоит на брекпойнте ты можешь заглянуть в тестовую базу и убедиться глазами какие данные там лежат
- понять: "а вообще заходит ли тест в тестируемый эндпоинт?" - например передача некорректных данных в параметры
- можно тестировать стримы
- при помощи evaluate можно тестировать запросы в текущий момент времени (правда придется временно в ContextIT предварительно сделать entityManager публичным)


