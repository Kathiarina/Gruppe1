DROP TABLE if exists RennenFahrer;
DROP TABLE if exists Fahrer;
DROP TABLE if exists Fahrzeug;
DROP TABLE if exists Team;
DROP TABLE if exists Fahrzeugtyp;
DROP TABLE if exists Rennen;
DROP TABLE if exists Rennstrecke;
DROP TABLE if exists Status;
DROP TABLE if exists Nationalitaet;
DROP TABLE if exists Hauptsponsor;

CREATE TABLE Rennstrecke(
	rennstreckenId SERIAL,
	ort VARCHAR(30) not null,
	bundesland VARCHAR(30) not null,
	PRIMARY KEY (rennstreckenId)
);

CREATE TABLE Rennen(
	rennenId SERIAL,
	datumUhrzeit TIMESTAMP not null,
	rennstreckenId INT not null,
	PRIMARY KEY(rennenId),
	FOREIGN KEY (rennstreckenId) REFERENCES Rennstrecke (rennstreckenId)
);

CREATE TABLE Status(
	statusId SERIAL,
	statusBeschreibung VARCHAR(15) not null,
	PRIMARY KEY (statusId)
);

CREATE TABLE Nationalitaet(
	nationalitaetsId SERIAL,
	nationalitaetsBeschreibung VARCHAR(30) not null,
	PRIMARY KEY (nationalitaetsId)
);

CREATE TABLE Hauptsponsor(
	hauptsponsorId SERIAL,
	hauptsponsorName VARCHAR(60) not null,
	jaehrlicheSponsorsumme int not null,
	PRIMARY KEY (hauptsponsorId)
);

CREATE TABLE Fahrzeugtyp(
	fahrzeugtypId SERIAL,
	modell VARCHAR(60) not null,
	motor VARCHAR(60) not null,
	gewichtKg INT not null,
	PRIMARY KEY (fahrzeugtypId)
);

CREATE TABLE Team(
	teamId SERIAL,
	teamName VARCHAR(60) not null,
	gruendungsjahr INT not null,
	nationalitaetsId INT not null,
	hauptsponsorId INT not null,
	PRIMARY KEY (teamId),
	FOREIGN KEY (nationalitaetsId) REFERENCES Nationalitaet (nationalitaetsId),
	FOREIGN KEY (hauptsponsorId) REFERENCES Hauptsponsor (hauptsponsorId)
);

CREATE TABLE Fahrzeug(
	fahrzeugId SERIAL,
	fahrzeugtypId INT not null,
	teamId INT not null,
	PRIMARY KEY (fahrzeugId),
	FOREIGN KEY (fahrzeugtypId) REFERENCES Fahrzeugtyp (fahrzeugtypId),
	FOREIGN KEY (teamId) REFERENCES Team (teamId)
);

CREATE TABLE Fahrer(
	fahrerId SERIAL,
	vorname VARCHAR(30) not null,
	nachname VARCHAR(30) not null,
	nationalitaetsId INT not null,
	fahrzeugId INT not null,
	PRIMARY KEY(fahrerId),
	FOREIGN KEY (nationalitaetsId) REFERENCES Nationalitaet (nationalitaetsId),
	FOREIGN KEY (fahrzeugId) REFERENCES Fahrzeug (fahrzeugId)
);

CREATE TABLE RennenFahrer
(
	fahrerId INT not null,
	rennenId INT not null,
	statusId INT not null,
	zeit TEXT,
	PRIMARY KEY (fahrerId, rennenId),
	FOREIGN KEY (rennenId) REFERENCES Rennen (rennenId),
	FOREIGN KEY (fahrerId) REFERENCES Fahrer (fahrerId),
	FOREIGN KEY (statusId) REFERENCES Status (statusId)
);


INSERT INTO Hauptsponsor (hauptsponsorId, hauptsponsorName, jaehrlicheSponsorsumme)VALUES (default, 'Ineos', 23000000);
INSERT INTO Hauptsponsor (hauptsponsorId, hauptsponsorName, jaehrlicheSponsorsumme)VALUES (default, 'OKX', 28000000);
INSERT INTO Hauptsponsor (hauptsponsorId, hauptsponsorName, jaehrlicheSponsorsumme)VALUES (default, 'Hewlett-Packard', 80000000);
INSERT INTO Hauptsponsor (hauptsponsorId, hauptsponsorName, jaehrlicheSponsorsumme)VALUES (default, 'Oracle', 89000000);
INSERT INTO Hauptsponsor (hauptsponsorId, hauptsponsorName, jaehrlicheSponsorsumme)VALUES (default, 'Lenovo', 15000000);
INSERT INTO Hauptsponsor (hauptsponsorId, hauptsponsorName, jaehrlicheSponsorsumme)VALUES (default, 'Prima Assicurazioni', 10000000);
INSERT INTO Hauptsponsor (hauptsponsorId, hauptsponsorName, jaehrlicheSponsorsumme)VALUES (default, 'Red Bull', 20000000);
INSERT INTO Hauptsponsor (hauptsponsorId, hauptsponsorName, jaehrlicheSponsorsumme)VALUES (default, 'Pertamina Lubricants', 20000000);

INSERT INTO Status (statusId, statusBeschreibung)VALUES (default, 'Erster Platz');
INSERT INTO Status (statusId, statusBeschreibung)VALUES (default, 'Zweiter Platz');
INSERT INTO Status (statusId, statusBeschreibung)VALUES (default, 'Dritter Platz');
INSERT INTO Status (statusId, statusBeschreibung)VALUES (default, 'Teilgenommen');
INSERT INTO Status (statusId, statusBeschreibung)VALUES (default, 'Ausgeschieden');

INSERT INTO Nationalitaet (nationalitaetsId, nationalitaetsBeschreibung)
VALUES (default, 'Australien');
INSERT INTO Nationalitaet (nationalitaetsId, nationalitaetsBeschreibung)
VALUES (default, 'Deutschland');
INSERT INTO Nationalitaet (nationalitaetsId, nationalitaetsBeschreibung)
VALUES (default, 'England');
INSERT INTO Nationalitaet (nationalitaetsId, nationalitaetsBeschreibung)
VALUES (default, 'Italien');
INSERT INTO Nationalitaet (nationalitaetsId, nationalitaetsBeschreibung)
VALUES (default, 'Mexiko');
INSERT INTO Nationalitaet (nationalitaetsId, nationalitaetsBeschreibung)
VALUES (default, 'Monaco');
INSERT INTO Nationalitaet (nationalitaetsId, nationalitaetsBeschreibung)
VALUES (default, 'Niederlande');
INSERT INTO Nationalitaet (nationalitaetsId, nationalitaetsBeschreibung)
VALUES (default, 'Österreich');
INSERT INTO Nationalitaet (nationalitaetsId, nationalitaetsBeschreibung)
VALUES (default, 'Spanien');
INSERT INTO Nationalitaet (nationalitaetsId, nationalitaetsBeschreibung)
VALUES (default, 'Südafrika');

INSERT INTO Rennstrecke (rennstreckenId, ort, bundesland) VALUES (default,'Spielberg', 'Steiermark');
INSERT INTO Rennstrecke (rennstreckenId, ort, bundesland) VALUES (default,'Plainfeld', 'Salzburg');

INSERT INTO Rennen (rennenId, datumUhrzeit, rennstreckenId)VALUES (default, '2024-06-20 15:00:00', 1);
INSERT INTO Rennen (rennenId, datumUhrzeit, rennstreckenId)VALUES (default, '2023-07-02 15:00:00', 1);
INSERT INTO Rennen (rennenId, datumUhrzeit, rennstreckenId)VALUES (default, '2024-08-18 14:00:00', 2);
INSERT INTO Rennen (rennenId, datumUhrzeit, rennstreckenId)VALUES (default, '2023-08-20 14:00:00', 2);

INSERT INTO Team (teamId, teamName, gruendungsjahr, nationalitaetsId, hauptsponsorId)
VALUES (default, 'Mercedes', 2009, 2, 1);
INSERT INTO Team (teamId, teamName, gruendungsjahr, nationalitaetsId, hauptsponsorId)
VALUES (default, 'McLaron', 1966, 3, 2);
INSERT INTO Team (teamId, teamName, gruendungsjahr, nationalitaetsId, hauptsponsorId)
VALUES (default, 'Ferrari', 1946, 4, 3);
INSERT INTO Team (teamId, teamName, gruendungsjahr, nationalitaetsId, hauptsponsorId)
VALUES (default, 'Red Bull Racing', 2004, 8, 4);
INSERT INTO Team (teamId, teamName, gruendungsjahr, nationalitaetsId, hauptsponsorId)
VALUES (default, 'Ducati Lenovo Team', 2003, 4, 5);
INSERT INTO Team (teamId, teamName, gruendungsjahr, nationalitaetsId, hauptsponsorId)
VALUES (default, 'Prima Pramac Yamaha MotoGP', 2002, 4, 6);
INSERT INTO Team (teamId, teamName, gruendungsjahr, nationalitaetsId, hauptsponsorId)
VALUES (default, 'Red Bull KTM Factory Racing', 2017, 8, 7);
INSERT INTO Team (teamId, teamName, gruendungsjahr, nationalitaetsId, hauptsponsorId)
VALUES (default, 'Pertamina Enduro VR46 Racing Team', 2014, 4, 8);

INSERT INTO Fahrzeugtyp (fahrzeugtypId, modell, motor, gewichtKg)
VALUES (default, 'Mercedes-AMG F1 W15 E Performance', 'Mercedes 1.6 Liter V6-Turbomotor', 798);
INSERT INTO Fahrzeugtyp (fahrzeugtypId, modell, motor, gewichtKg)
VALUES (default, 'McLaren MCL38', 'Mercedes-AMG F1 M15 E Performance 1.6L V6-Turbomotor', 798);
INSERT INTO Fahrzeugtyp (fahrzeugtypId, modell, motor, gewichtKg)
VALUES (default, 'Ferrari SF-24', '1,6-Liter-V6-Turbomotor ', 798);
INSERT INTO Fahrzeugtyp (fahrzeugtypId, modell, motor, gewichtKg)
VALUES (default, 'Red Bull RB19', 'Honda RBPTH001 1,6-Liter V6-Turbomotor', 798);
INSERT INTO Fahrzeugtyp (fahrzeugtypId, modell, motor, gewichtKg)
VALUES (default, 'Ferrari SF-23', 'Ferrari 066/10 1,6-Liter V6-Turbomotor', 798);
INSERT INTO Fahrzeugtyp (fahrzeugtypId, modell, motor, gewichtKg)
VALUES (default, 'Ducati Desmosedici GP23', 'V4-Viertaktmotor', 157);
INSERT INTO Fahrzeugtyp (fahrzeugtypId, modell, motor, gewichtKg)
VALUES (default, 'Ducati Desmosedici GP24', 'V4-Viertaktmotor', 157);
INSERT INTO Fahrzeugtyp (fahrzeugtypId, modell, motor, gewichtKg)
VALUES (default, 'Ducati Desmosedici GP22', 'V4-Viertaktmotor', 157);
INSERT INTO Fahrzeugtyp (fahrzeugtypId, modell, motor, gewichtKg)
VALUES (default, 'KTM RC16', 'V4-Viertaktmotor', 157);

INSERT INTO Fahrzeug (fahrzeugId, fahrzeugtypId, teamId)
VALUES (default, 1, 1);
INSERT INTO Fahrzeug (fahrzeugId, fahrzeugtypId, teamId)
VALUES (default, 2, 2);
INSERT INTO Fahrzeug (fahrzeugId, fahrzeugtypId, teamId)
VALUES (default, 3, 3);
INSERT INTO Fahrzeug (fahrzeugId, fahrzeugtypId, teamId)
VALUES (default, 2, 2);
INSERT INTO Fahrzeug (fahrzeugId, fahrzeugtypId, teamId)
VALUES (default, 4, 4);
INSERT INTO Fahrzeug (fahrzeugId, fahrzeugtypId, teamId)
VALUES (default, 5, 3);
INSERT INTO Fahrzeug (fahrzeugId, fahrzeugtypId, teamId)
VALUES (default, 4, 4);
INSERT INTO Fahrzeug (fahrzeugId, fahrzeugtypId, teamId)
VALUES (default, 6, 5);
INSERT INTO Fahrzeug (fahrzeugId, fahrzeugtypId, teamId)
VALUES (default, 7, 6);
INSERT INTO Fahrzeug (fahrzeugId, fahrzeugtypId, teamId)
VALUES (default, 7, 5);
INSERT INTO Fahrzeug (fahrzeugId, fahrzeugtypId, teamId)
VALUES (default, 8, 8);
INSERT INTO Fahrzeug (fahrzeugId, fahrzeugtypId, teamId)
VALUES (default, 9, 7);

INSERT INTO Fahrer (fahrerId, vorname, nachname, nationalitaetsId, fahrzeugId)
VALUES (default, 'Georg', 'Russel', 3, 1);
INSERT INTO Fahrer (fahrerId, vorname, nachname, nationalitaetsId, fahrzeugId)
VALUES (default, 'Oscar', 'Piastri', 1, 2);
INSERT INTO Fahrer (fahrerId, vorname, nachname, nationalitaetsId, fahrzeugId)
VALUES (default, 'Carlos', 'Sainz', 9, 3);
INSERT INTO Fahrer (fahrerId, vorname, nachname, nationalitaetsId, fahrzeugId)
VALUES (default, 'Lando', 'Norris', 3, 4);
INSERT INTO Fahrer (fahrerId, vorname, nachname, nationalitaetsId, fahrzeugId)
VALUES (default, 'Max', 'Verstappen', 7, 5);
INSERT INTO Fahrer (fahrerId, vorname, nachname, nationalitaetsId, fahrzeugId)
VALUES (default, 'Charles', 'Leclerc', 6, 6);
INSERT INTO Fahrer (fahrerId, vorname, nachname, nationalitaetsId, fahrzeugId)
VALUES (default, 'Sergio', 'Pèrez', 5, 7);
INSERT INTO Fahrer (fahrerId, vorname, nachname, nationalitaetsId, fahrzeugId)
VALUES (default, 'Francesco', 'Bagnaia', 4, 8);
INSERT INTO Fahrer (fahrerId, vorname, nachname, nationalitaetsId, fahrzeugId)
VALUES (default, 'Brad', 'Binder', 10, 12);
INSERT INTO Fahrer (fahrerId, vorname, nachname, nationalitaetsId, fahrzeugId)
VALUES (default, 'Marco', 'Bezzecchi', 4, 11);
INSERT INTO Fahrer (fahrerId, vorname, nachname, nationalitaetsId, fahrzeugId)
VALUES (default, 'Jorge', 'Martin', 9, 9);
INSERT INTO Fahrer (fahrerId, vorname, nachname, nationalitaetsId, fahrzeugId)
VALUES (default, 'Enea', 'Bastianini', 4, 10);

INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (1, 1, 1, '1 hour 24 minutes 22.798 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (2, 1, 2, '1 hour 24 minutes 24.704 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (3, 1, 3, '1 hour 24 minutes 27.331 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (4, 1, 5, NULL);
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (5, 2, 1, '1 hour 25 minutes 33.607 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (6, 2, 2, '1 hour 25 minutes 38.762 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (7, 2, 3, '1 hour 25 minutes 50.795 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (3, 2, 4, '1 hour 26 minutes 04.984 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (1, 2, 4, '1 hour 26 minutes 22.010 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (2, 2, 4, '1 hour 26 minutes 20.176 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (5, 1, 4, '1 hour 25 minutes 00.051 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (6, 1, 4, '1 hour 25 minutes 29.854 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (7, 1, 4, '1 hour 25 minutes 17.470 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (4, 2, 4, '1 hour 25 minutes 59.934 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (8, 3, 1, '0 hour 42 minutes 11.173 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (11, 3, 2, '0 hour 42 minutes 14.405 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (12, 3, 3, '0 hour 42 minutes 18.530 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (8, 4, 1, '0 hour 42 minutes 23.315 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (9, 4, 2, '0 hour 42 minutes 28.506 seconds');
INSERT INTO RennenFahrer (fahrerId, rennenId, statusId, zeit)VALUES (10, 4, 3, '0 hour 42 minutes 31.023 seconds');


