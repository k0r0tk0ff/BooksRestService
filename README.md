# BooksRestService
BooksRestService - REST сервис для работы с сущностями "book", "author", "wishlist", согласно задания.  
(задание - https://github.com/k0r0tk0ff/BooksRestService/blob/master/task.md)

#### Настройка окружения  
Необходимо скачать ПО "H2 Database Engine" для разворачивания полноценной базы данных (далее - БД)-  
http://www.h2database.com/h2-2018-03-18.zip и распаковать в произвольную папку.   

Для операционной системы Windows,   
к примеру, путь до распакованной папки пусть будет - "E:\tools\h2-2018-03-18".     
запустить базу данных можно скриптом запуска "E:\tools\h2-2018-03-18\h2\bin\h2w.bat".    
В трее появиться значек "H2 Database Engine". В браузере по умолчанию откроется web консоль,    
в которой можно выполнять sql запросы к данной БД. (Если по каким либо причинам не открылать,      
зайти можно по ссылке http://127.0.0.1:8082 логин - sa, пароль - не вводим.)    

#### Сборка приложения    
Перед запуском приложения его необходимо скачать по ссылке   
https://github.com/k0r0tk0ff/BooksRestService/archive/master.zip    
а затем собрать командой "mvn package" сборщиком Apache Maven    
(Необходима версия 3.3.2 или новее. Скачать можно отсюда - https://maven.apache.org/download.cgi).  

#### Работа приложения   


##### При наличии в path пути до java cтрока запуска проекта примет вид -      
java -Dfile.encoding=UTF-8 -jar BooksRestService-1.0-SNAPSHOT.jar  

##### Формат работы с сущностью BOOK (создание нового обьекта и модификация существующего обьекта)  
POST:  
{  
    "price": "1000",  
    "author_name": "Лермонтов",  
    "name": "2222"  
}  

PUT:  
{  
    "book_id": "1",  
    "author_name": "Лермонтов",  
    "price": "100",   
    "name": "asdf"    
}  

