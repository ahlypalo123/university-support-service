# University Support Service

Приложение для обработки обращений студентов, реализованное с использованием **Hexagonal Architecture**, **Kotlin**, **Spring Boot** и **OpenAPI**.

## Структура проекта

- `domain`: Ядро системы. Содержит доменные модели и интерфейсы портов (Repository, NotificationService, Logger). Не зависит от фреймворков.
- `application`: Слой бизнес-логики. Содержит сервисы (Use Cases), реализующие логику обработки обращений.
- `infrastructure`: Слой инфраструктуры. Содержит:
    - Реализации портов (InMemoryRepository, ConsoleLogger и т.д.).
    - Конфигурацию Spring Boot.
    - OpenAPI спецификацию и сгенерированный на её основе код (API контроллеры и модели).

## Технологии

- **Kotlin** 1.9.22
- **Spring Boot** 3.2.2
- **Gradle** (Kotlin DSL)
- **OpenAPI Generator** (плагин для Gradle)
- **JUnit 5 & Mockito** для тестирования

## Как запустить

Для сборки проекта и генерации кода по OpenAPI выполните:

```bash
./gradlew build
```

Для запуска тестов:

```bash
./gradlew test
```

## Документация

В папке `docs` вы найдете:
- `DIAGNOSTICS.md`: Анализ проблем исходного "плохого" кода.
- `REFACTORING_PLAN.md`: План по улучшению дизайна.
- `diagrams/`: UML-диаграмма целевой архитектуры.
