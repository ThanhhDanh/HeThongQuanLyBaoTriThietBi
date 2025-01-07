# Sử dụng Maven image để build ứng dụng
FROM maven:3.8.6-openjdk-17 AS build

# Sao chép mã nguồn vào container
COPY . /app

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Chạy lệnh Maven để build dự án và tạo file WAR
RUN mvn clean install -DskipTests

# Sử dụng image của OpenJDK để chạy ứng dụng Java
FROM openjdk:17-jdk-slim

# Sao chép file WAR đã build từ bước trước vào container
COPY --from=build /app/target/your-app-name.war /your-app-name.war

# Cấu hình lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/your-app-name.war"]

# Mở port 8080 để ứng dụng có thể truy cập được
EXPOSE 8080
