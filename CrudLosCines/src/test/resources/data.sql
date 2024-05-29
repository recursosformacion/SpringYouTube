
Insert into CINE (ID_CINE,CI_BARRIO,CI_CALLE,CI_CAPACIDAD,CI_NOMBRE) values (10,'Barrio 1','Calle 1',300,'Cine1');
Insert into CINE (ID_CINE,CI_BARRIO,CI_CALLE,CI_CAPACIDAD,CI_NOMBRE) values (11,'Barrio 2','Ronda San Pedro 101',500,'Capitol Segundo');
Insert into CINE (ID_CINE,CI_BARRIO,CI_CALLE,CI_CAPACIDAD,CI_NOMBRE) values (12,'Barrio 1','Calle 1',300,'Goya');
Insert into CINE (ID_CINE,CI_BARRIO,CI_CALLE,CI_CAPACIDAD,CI_NOMBRE) values (13,'Barrio 2','Calle 1',300,'Rex');
Insert into CINE (ID_CINE,CI_BARRIO,CI_CALLE,CI_CAPACIDAD,CI_NOMBRE) values (14,'Barrio 1','Calle 1',300,'Coliseum');
Insert into CINE (ID_CINE,CI_BARRIO,CI_CALLE,CI_CAPACIDAD,CI_NOMBRE) values (15,'Barrio 2','Calle 1',300,'Verdi');
Insert into CINE (ID_CINE,CI_NOMBRE,CI_CALLE,CI_BARRIO,CI_CAPACIDAD) values (16,'Poliorama','Avda Los cines 123','Peliculero',450);

INSERT INTO Entrada (id_entrada, ent_fecha, ent_fila, ent_numero, id_cliente, ent_cine) VALUES (11, '2024-01-01' , 5, 10, '12345678Z', 10);
INSERT INTO Entrada (id_entrada, ent_fecha, ent_fila, ent_numero, id_cliente, ent_cine) VALUES (12, '2024-02-01', 6, 11, '23456789E', 10);
INSERT INTO Entrada (id_entrada, ent_fecha, ent_fila, ent_numero, id_cliente, ent_cine) VALUES (13, '2024-02-01', 6, 11, '45678901T', 10);
INSERT INTO Entrada (id_entrada, ent_fecha, ent_fila, ent_numero, id_cliente, ent_cine) VALUES (14, '2024-03-01', 7, 12, '34567890R', 12);
INSERT INTO Entrada (id_entrada, ent_fecha, ent_fila, ent_numero, id_cliente, ent_cine) VALUES (15, '2024-04-01', 8, 13, '45678901T', 12);
INSERT INTO Entrada (id_entrada, ent_fecha, ent_fila, ent_numero, id_cliente, ent_cine) VALUES (16, '2024-05-01', 9, 14, '56789012A', 13);

INSERT INTO Pelicula (id_pelicula,pe_titulo, pe_identificador) VALUES (20,'La gran pelicula',1);
INSERT INTO Pelicula (id_pelicula,pe_titulo, pe_identificador) VALUES (21,'La pequeña pelicula',1);
INSERT INTO Pelicula (id_pelicula,pe_titulo, pe_identificador) VALUES (22,'La mediana pelicula',2);