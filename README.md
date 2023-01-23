# Random Number Client-Server Application
Клиент-серверное приложение.

Сервер генерирует случайные числа. 
На стороне клиента отображаются новые значения.

Доступ к данным предоставляется после авторизации с помощью OAuth GitHub.
Трансляция данных производится неограниченному количеству клиентов.
Данные (случайно генерируемые номера) одинаковы для всех клиентов.

Стэк:
Java, Servlets, WebSockets, JUnit, slf4j, html, JS, OAuth, Docker.

Деплой:
Docker-контейнер развернут на облачном сервере.
URL: http://31.31.202.202:8080/RandomNumberClientServerApp-1.0-SNAPSHOT/index.html
