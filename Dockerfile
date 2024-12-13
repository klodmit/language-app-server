# 1 STAGE: Сборка проекта
FROM openjdk:20-slim AS builder
LABEL author="Dmitriy Klochikhin"

# Устанавливаем рабочую директорию
WORKDIR /app

# Устанавливаем Gradle
RUN apt-get update && apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-8.1.1-bin.zip && \
    unzip gradle-8.1.1-bin.zip && \
    rm gradle-8.1.1-bin.zip && \
    mv gradle-8.1.1 /opt/gradle

# Устанавливаем переменные окружения для Gradle
ENV GRADLE_HOME=/opt/gradle
ENV PATH=$GRADLE_HOME/bin:$PATH

# Копируем файлы для сборки
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY src ./src

# Скачиваем зависимости и собираем проект
RUN gradle build -x test

# 2 STAGE: Создание контейнера для выполнения
FROM eclipse-temurin:20-jre-jammy
LABEL author="Dmitriy Klochikhin"

# Устанавливаем рабочую директорию
WORKDIR /app

# Создаем пользователя для запуска приложения
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser

# Копируем jar-файл из стадии сборки
COPY --from=builder /app/build/libs/*.jar /app/language-server.jar
COPY .env /app/.env

# Открываем порт приложения
EXPOSE 5000

# Аргументы для базы данных
ARG DB_NAME
ARG DB_USER
ARG DB_PASSWORD

# Переменные окружения для базы данных и приложения
ENV DB_HOST=localhost
ENV DB_PORT=5432
ENV SALT=${SALT}
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Запуск приложения
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dktor.storage.url=jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME -Dktor.storage.user=$DB_USER -Dktor.storage.password=$DB_PASSWORD -Dsalt=$SALT -jar /app/language-server.jar"]