# BooksRestService
BooksRestService - REST сервис для работы с сущностями "book", "author", "wishlist", согласно задания.  
(задание - https://github.com/k0r0tk0ff/BooksRestService/blob/master/task.md)
Для работы приложения используется in-memory database H2.


#### Сборка приложения      
Перед запуском приложения его необходимо скачать по ссылке     
https://github.com/k0r0tk0ff/BooksRestService/archive/master.zip.  

#### Варианты запуска приложения
Переходим в папку, куда распаковали master.zip.

Вариант 1 - Собираем проект командой "mvn package" 
сборщиком Apache Maven (Необходима версия 3.6.2 или новее. 
Скачать можно отсюда - https://maven.apache.org/download.cgi).  
Должна создасться папка "target", в которой должен находиться артефакт "BooksRestService-1.0-SNAPSHOT.jar".
Запустить приложение можно из командной строки (при наличии в системной переменной path пути до java)    
#####"java -Dfile.encoding=UTF-8 -jar BooksRestService-1.0-SNAPSHOT.jar"    
либо создав bat файл запуска с этим содержимым.
  
Вариант 2 - запустив команду "mvn spring-boot:run"
 
При необходимости, перед сборкой (запуском), в проекте можно отредактировав файл "data-h2.sql"  
куда добавить/изменить тестовые данные. Также можно собирать проект вообще без этого файла,    
и добавить впоследствии данные посредством rest api   
с соблюдением очередности - сначала book, затем wishlist.    

### Работа приложения   
Можно использовать web-интерфейс swagger, доступный по URL
http://127.0.0.1:8080/book-rest-service/swagger-ui.html

или REST запросы -
### Формат работы с сущностями   
#### BOOK 
Добавить книгу -
POST: http://127.0.0.1:8080/book-rest-service/api/book    
{  
    "price": "1000",  
    "authorName": "Лермонтов",  
    "name": "Бородино"  
}  

Изменить книгу -  
PUT: http://127.0.0.1:8080/book-rest-service/api/book  
{  
	"bookId": "27",  
    "price": "1500",  
    "authorName": "Лермонтов",  
    "name": "Бородино"  
}  

Получить информацию о книге с id = 27  
GET: http://127.0.0.1:8080/book-rest-service/api/book/27  

Удалить информацию о книге с id = 29  
DELETE: http://127.0.0.1:8080/book-rest-service/api/book/29     

Получить информацию о всех книгах -  
GET: http://127.0.0.1:8080/book-rest-service/api/books    

#### AUTHOR  
Добавить автора -  
POST:  
{  
    "name": "Фет"  
}  

Изменить автора -  
PUT:  
{  
    "authorId": 28,  
    "name": "Фет Афанасий"  
}  

Получить информацию об авторе с id = 28  
GET: http://127.0.0.1:8080/book-rest-service/api/author/28  

Удалить информацию об авторе с id = 28  
DELETE: http://127.0.0.1:8080/book-rest-service/api/author/28  

Получить информацию обо всех авторах - 
GET: http://127.0.0.1:8080/book-rest-service/api/authors  

#### WISHLIST  
Добавить лист пожелания  
(При повторном выполнении количество книг с указанным bookId возрастет на единицу)
POST:  
{  
    "bookId": "1"  
}  

Изменить лист пожелания -    
PUT:    
{    
    "bookId": 1,    
    "count": 2     
}    

Получить информацию о листе пожелания с id = 1   
GET: http://127.0.0.1:8080/book-rest-service/api/wishlist/1  

Удалить информацию о листе пожелания с id = 1   
DELETE: http://127.0.0.1:8080/book-rest-service/api/wishlist/1  

Получить информацию о всех листах пожеланий  
GET: http://127.0.0.1:8080/book-rest-service/api/wishlists  

