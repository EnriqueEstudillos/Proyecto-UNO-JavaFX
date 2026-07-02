CREATE DATABASE UnoBD;
USE UnoBD;

CREATE USER 'Admin_UNO'@'localhost' IDENTIFIED BY 'BEMEN3';
GRANT ALL ON UnoBD.* to 'Admin_UNO'@'localhost';

USE UnoBD;

CREATE TABLE Jugadores(
	ID int Unique Not Null auto_increment ,
    Nombre varchar(50) Unique Not Null,
    Email varchar(50) Null,
    Contraseña varchar(15) Not Null,
    Primary Key(ID)
);

INSERT INTO Jugadores
VALUES (1, "ZaK", "enesca23@bemen3.cat", "bemen3"),
		(2, "Pomni", "enesca23@bemen3.cat", "bemen3");
        
CREATE TABLE HISTORIAL(
	ID int AUTO_INCREMENT Not Null,
    Ganador varchar(50) Not Null,
    Modo varchar(3) Not Null,
    Duracion varchar(30),
    Puntuacion varchar(20),
    Primary Key(ID)
);

INSERT INTO HISTORIAL
VALUES (1,"ZaK","1V1","00:00:23","1000"),
	(2,"Pomni","1v1","00:02:40","37");

CREATE TABLE Jugadores_X_Partida(
	ID_Jugador int Not Null,
    ID_Partida int Not Null,
    Primary Key(ID_Jugador, ID_Partida),
    FOREIGN KEY (ID_Jugador) REFERENCES Jugadores(ID),
    FOREIGN KEY (ID_Partida) REFERENCES HISTORIAL(ID)
);

INSERT INTO Jugadores_X_Partida
VALUES (1,1), (2,2);