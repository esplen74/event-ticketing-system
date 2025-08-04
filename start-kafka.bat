@echo off
title Start Zookeeper and Kafka
echo Starting Zookeeper...
start "Zookeeper" cmd /k "D:\kafka_2.13-3.9.1\bin\windows\zookeeper-server-start.bat D:\kafka_2.13-3.9.1\config\zookeeper.properties"

:: Chờ 10 giây để Zookeeper khởi động ổn định
timeout /t 10 /nobreak >nul

echo Starting Kafka...
start "Kafka" cmd /k "D:\kafka_2.13-3.9.1\bin\windows\kafka-server-start.bat D:\kafka_2.13-3.9.1\config\server.properties"

echo Done! Zookeeper and Kafka are starting in separate windows.
pause
