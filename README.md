# BooksRestService
BooksRestService - REST сервис для работы с сущностями "book", "author", "wishlist", согласно задания.  
(задание - https://github.com/k0r0tk0ff/BooksRestService/blob/master/task.md)

##### Настройка окружения
Необходимо скачать ПО "" для разворачивания полноценной базы данных


##### Сборка приложения  
Перед запуском приложения его необходимо скачать по ссылке 
https://github.com/k0r0tk0ff/BooksRestService/archive/master.zip
а затем собрать командой "mvn package" сборщиком Apache Maven 
(Необходима версия 3.3.2 или новее. Скачать можно отсюда - https://maven.apache.org/download.cgi).

##### Работа приложения 


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

