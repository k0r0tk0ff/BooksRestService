INSERT INTO AUTHOR (name) VALUES ('Пушкин');
INSERT INTO AUTHOR (name) VALUES ('Толстой');
INSERT INTO AUTHOR (name) VALUES ('Шолохов');
CREATE CONSTANT pushkin_id VALUE (SELECT author_id FROM author WHERE NAME = 'Пушкин');
CREATE CONSTANT sholohov_id VALUE (SELECT author_id FROM author WHERE NAME = 'Шолохов');
CREATE CONSTANT tolstoy_id VALUE (SELECT author_id FROM author WHERE NAME = 'Толстой');
INSERT INTO BOOK (author_id, name, price) VALUES (pushkin_id, 'Пиковая дама', '400.50');
INSERT INTO BOOK (author_id, name, price) VALUES (pushkin_id, 'Капитанская дочка', '420.90');
INSERT INTO BOOK (author_id, name, price) VALUES (pushkin_id, 'Медный всадник', '1410.10');
INSERT INTO BOOK (author_id, name, price) VALUES (sholohov_id, 'Тихий Дон', '800.00');
INSERT INTO BOOK (author_id, name, price) VALUES (tolstoy_id, 'Война и мир', '1500.00');
INSERT INTO WISHLIST (wishlist_id, book_id, count) VALUES ('1', '1', '5');
INSERT INTO WISHLIST (wishlist_id, book_id, count) VALUES ('2', '2', '4');
DROP constant pushkin_id;
DROP constant sholohov_id;
DROP constant tolstoy_id;

