## Journey Java Application

***
Это приложение предствляет собой сайт с информацией о путешествиях, разработанный на Java с использованием Spring Boot
Framework, а так же с использованием PostgreSQL. Приложение предоставляет поиск по путешествиям, их удаление, изменение
и добавление.
***

## Содержание

- [Задание](#задание)
- [Реализация](#реализация)
- [Запуск](#запуск)
- [Результаты SonarCloud](#результаты-SonarCloud)

***

## Задание
1. Подключить в проект БД (PostgreSQL/MySQL/и т.д.).
   (0 - 7 баллов) - Реализация связи один ко многим @OneToMany
   (8 - 10 баллов) - Реализация связи многие ко многим @ManyToMany
2. Реализовать CRUD-операции со всеми сущностями.

***

## Реализация

Для выполнения задания необходимы классы:

- **Journey**: Этот класс представляет сущность путешествия. Он содержит поля, такие
  как `id`, `country`, `town`, `dateToCountry` и `dateFromCountry`, которые описывают книгу. Класс также содержит
  геттеры и сеттеры для доступа к этим полям.
- **TravelAgency**: Этот класс представляет сущность тур агентства. Он содержит поля, такие
  как `id`, `name`, которые описывают тур агентство. Класс также содержит геттеры и сеттеры для доступа к этим полям.
- **EntityNotFoundException**: Этот класс представляет исключение, которое бросается в случае, когда сущность не найдена
  в системе, обеспечивая обработку таких ситуаций в веб-приложении для сообщения об отсутствии запрошенных данных.
- **JourneyController**: Этот класс представляет собой контроллер Spring MVC, обрабатывающий HTTP-запросы и управляющий
  операциями создания, чтения, обновления и удаления путешествий (Journey) в веб-приложении. Он обеспечивает
  взаимодействие между пользовательским интерфейсом и сервисным слоем для эффективного управления данными о
  путешествиях.
- **TravelAgencyController**: Этот класс представляет собой контроллер Spring MVC, обрабатывающий HTTP-запросы и
  управляющий
  операциями создания, чтения, обновления и удаления тур агентств в веб-приложении. Он обеспечивает
  взаимодействие между пользовательским интерфейсом и сервисным слоем для эффективного управления данными о тур
  агентствах.
- **JourneyMapper**: Этот класс представляет собой маппер для преобразования объектов типа Journey и JourneyDto друг в
  друга, обеспечивая удобный способ конвертации данных между сущностями и их сокращенными представлениями.
- **JourneyDto**: Этот класс представляет собой объект передачи данных (DTO - Data Transfer Object) для сущности "
  Путешествие" (Journey), содержащий поля с информацией о стране, городе, датах выезда и возвращения, а также
  предоставляющий методы доступа и установки значений для этих полей.Он содержит поля, такие
  как `id`, `country`, `town`, `dateToCountry` и `dateFromCountry`, которые описывают книгу. Класс также содержит
  геттеры и сеттеры для доступа к этим полям.
- **TravelAgencyDto**: Этот класс представляет собой объект передачи данных (DTO - Data Transfer Object) для сущности "
  Тур Агентства" (TravelAgency), содержащий поле с информацией о названии, а также
  предоставляющий методы доступа и установки значений для этих полей.Он содержит поля, такие
  как `id`, `name` которые описывают тур агентство. Класс также содержит геттеры и сеттеры для доступа к этим полям.
- **JourneyService**: Этот класс представляет собой сервисный компонент, обеспечивающий бизнес-логику для управления
  данными о путешествиях (Journey), используя методы репозитория для выполнения операций CRUD (Create, Read, Update,
  Delete) в базе данных.
- **AgencyService**: Этот класс представляет собой сервисный компонент, обеспечивающий бизнес-логику для управления
    данными о тур агентствах (TravelAgency), используя методы репозитория для выполнения операций CRUD (Create, Read, Update,
    Delete) в базе данных.
- **JourneyRepository**: Этот класс представляет собой репозиторий для взаимодействия с базой данных, осуществляя
  операции CRUD (Create, Read, Update, Delete) для сущности "Путешествие" (Journey), используя Hibernate и предоставляя
  методы для сохранения, обновления, удаления и поиска данных путешествий.
- **HibernateConfig**: Этот класс предназначен для конфигурации Hibernate в приложении Spring, создавая бин
  SessionFactory, который обеспечивает взаимодействие с базой данных через Hibernate ORM.
- **JourneyJavaApplication**: Этот класс является точкой входа в ваше приложение Spring Boot. Он содержит метод `main`,
  который запускает приложение.

***

## Запуск

1. **Подключение к базе данных**: Для полной функциональности проекта необходимо подключиться к базе данных PostgreSQL.
   Для этого в IntelliJ idea создайте проект на Spring Boot, в pom.xml добавьте зависимость для PostgreSQL.

 ```   
<dependency>
        <groupId>org.postgresql</groupId>
           <artifactId>postgresql</artifactId>
           <scope>runtime</scope>
</dependency>  
 ```

2. **Запуск приложения**: После настройки подключения к базе данных, запустите приложение. Можно использовать средства
   сборки Maven или Gradle для сборки и запуска проекта.

3. **Использование веб-интерфейса**: После успешного запуска приложения откройте веб-браузер и перейдите по
   адресу `http://localhost:8080/journeys`, чтобы получить список всех путешествий.

***

## Результаты SonarCloud

По этой ссылке можно посмотреть результаты SonarCloud:
https://sonarcloud.io/project/overview?id=BaranovaKate_JavaJourney
