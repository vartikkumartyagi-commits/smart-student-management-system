FROM eclipse-temurin:26-jdk

WORKDIR /app

COPY . .

RUN javac -cp "lib/mysql-connector-j-26.7.0.jar" -d bin src/main/java/com/student/*.java

EXPOSE 8080

CMD ["java", "-cp", "bin:lib/mysql-connector-j-26.7.0.jar", "com.student.StudentServer"]