# BooksRestService
BooksRestService

### При наличии в path пути до java cтрока запуска проекта примет вид - 
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

