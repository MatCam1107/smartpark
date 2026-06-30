@echo off
start cmd /k "cd ms-ubicacion && mvnw.cmd spring-boot:run"
start cmd /k "cd ms-estacionamiento && mvnw.cmd spring-boot:run"
start cmd /k "cd ms-usuario && mvnw.cmd spring-boot:run"
start cmd /k "cd ms-pago && mvnw.cmd spring-boot:run"
start cmd /k "cd ms-reserva && mvnw.cmd spring-boot:run"
start cmd /k "cd ms-notificacion && mvnw.cmd spring-boot:run"