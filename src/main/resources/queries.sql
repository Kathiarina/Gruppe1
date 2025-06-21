DROP TABLE if exists Rennen_Fahrer;
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
	rennstrecken_id SERIAL,
	ort VARCHAR(30) not null,
	bundesland VARCHAR(30) not null,
	PRIMARY KEY (rennstrecken_id)
);

CREATE TABLE Rennen(
	rennen_id SERIAL,
	datum_uhrzeit TIMESTAMP not null,
	rennstrecken_id INT not null,
	PRIMARY KEY(rennen_id),
	FOREIGN KEY (rennstrecken_id) REFERENCES Rennstrecke (rennstrecken_id)
);

CREATE TABLE Status(
	status_id SERIAL,
	beschreibung VARCHAR(15) not null,
	PRIMARY KEY (status_id)
);

CREATE TABLE Nationalitaet(
	nationalitaets_id SERIAL,
	beschreibung VARCHAR(30) not null,
	PRIMARY KEY (nationalitaets_id)
);

CREATE TABLE Hauptsponsor(
	sponsor_id SERIAL,
	name VARCHAR(60) not null,
	jaehrliche_sponsorsumme int not null,
	PRIMARY KEY (sponsor_id)
);

CREATE TABLE Fahrzeugtyp(
	fahrzeugtyp_id SERIAL,
	modell VARCHAR(60) not null,
	motor VARCHAR(60) not null,
	gewicht_kg INT not null,
	PRIMARY KEY (fahrzeugtyp_id)
);

CREATE TABLE Team(
	team_id SERIAL,
	name VARCHAR(60) not null,
	gruendungsjahr INT not null,
	nationalitaets_id INT not null,
	sponsor_id INT not null,
	PRIMARY KEY (team_id),
	FOREIGN KEY (nationalitaets_id) REFERENCES Nationalitaet (nationalitaets_id),
	FOREIGN KEY (sponsor_id) REFERENCES Hauptsponsor (sponsor_id)
);

CREATE TABLE Fahrzeug(
	fahrzeug_id SERIAL,
	fahrzeugtyp_id INT not null,
	team_id INT not null,
	PRIMARY KEY (fahrzeug_id),
	FOREIGN KEY (fahrzeugtyp_id) REFERENCES Fahrzeugtyp (fahrzeugtyp_id),
	FOREIGN KEY (team_id) REFERENCES Team (team_id)
);

CREATE TABLE Fahrer(
	fahrer_id SERIAL,
	vorname VARCHAR(30) not null,
	nachname VARCHAR(30) not null,
	nationalitaets_id INT not null,
	fahrzeug_id INT not null,
	PRIMARY KEY(fahrer_id),
	FOREIGN KEY (nationalitaets_id) REFERENCES Nationalitaet (nationalitaets_id),
	FOREIGN KEY (fahrzeug_id) REFERENCES Fahrzeug (fahrzeug_id)
);

CREATE TABLE Rennen_Fahrer(
	fahrer_id INT not null,
	rennen_id INT not null,
	status_id INT not null,
	zeit INTERVAL,
	PRIMARY KEY (fahrer_id, rennen_id),
	FOREIGN KEY (rennen_id) REFERENCES Rennen (rennen_id),
	FOREIGN KEY (fahrer_id) REFERENCES Fahrer (fahrer_id),
	FOREIGN KEY (status_id) REFERENCES Status (status_id)
);


INSERT INTO Hauptsponsor (sponsor_id, name, jaehrliche_sponsorsumme) VALUES (default, 'Ineos', 23000000);
INSERT INTO Hauptsponsor (sponsor_id, name, jaehrliche_sponsorsumme) VALUES (default, 'OKX', 28000000);
INSERT INTO Hauptsponsor (sponsor_id, name, jaehrliche_sponsorsumme) VALUES (default, 'Hewlett-Packard', 80000000);
INSERT INTO Hauptsponsor (sponsor_id, name, jaehrliche_sponsorsumme) VALUES (default, 'Oracle', 89000000);
INSERT INTO Hauptsponsor (sponsor_id, name, jaehrliche_sponsorsumme) VALUES (default, 'Lenovo', 15000000);
INSERT INTO Hauptsponsor (sponsor_id, name, jaehrliche_sponsorsumme) VALUES (default, 'Prima Assicurazioni', 10000000);
INSERT INTO Hauptsponsor (sponsor_id, name, jaehrliche_sponsorsumme) VALUES (default, 'Red Bull', 20000000);
INSERT INTO Hauptsponsor (sponsor_id, name, jaehrliche_sponsorsumme) VALUES (default, 'Pertamina Lubricants', 20000000);

INSERT INTO Status (status_id, beschreibung) VALUES (default, 'Erster Platz');
INSERT INTO Status (status_id, beschreibung) VALUES (default, 'Zweiter Platz');
INSERT INTO Status (status_id, beschreibung) VALUES (default, 'Dritter Platz');
INSERT INTO Status (status_id, beschreibung) VALUES (default, 'Teilgenommen');
INSERT INTO Status (status_id, beschreibung) VALUES (default, 'Ausgeschieden');

INSERT INTO Nationalitaet (nationalitaets_id, beschreibung) VALUES (default, 'Australien');
INSERT INTO Nationalitaet (nationalitaets_id, beschreibung) VALUES (default, 'Deutschland');
INSERT INTO Nationalitaet (nationalitaets_id, beschreibung) VALUES (default, 'England');
INSERT INTO Nationalitaet (nationalitaets_id, beschreibung) VALUES (default, 'Italien');
INSERT INTO Nationalitaet (nationalitaets_id, beschreibung) VALUES (default, 'Mexiko');
INSERT INTO Nationalitaet (nationalitaets_id, beschreibung) VALUES (default, 'Monaco');
INSERT INTO Nationalitaet (nationalitaets_id, beschreibung) VALUES (default, 'Niederlande');
INSERT INTO Nationalitaet (nationalitaets_id, beschreibung) VALUES (default, 'Österreich');
INSERT INTO Nationalitaet (nationalitaets_id, beschreibung) VALUES (default, 'Spanien');
INSERT INTO Nationalitaet (nationalitaets_id, beschreibung) VALUES (default, 'Südafrika');

INSERT INTO Rennstrecke (rennstrecken_id, ort, bundesland) VALUES (default,'Spielberg', 'Steiermark');

INSERT INTO Rennen (rennen_id, datum_uhrzeit, rennstrecken_id) VALUES (default, '2024-06-20 15:00:00', 1);
INSERT INTO Rennen (rennen_id, datum_uhrzeit, rennstrecken_id) VALUES (default, '2023-07-02 15:00:00', 1);
INSERT INTO Rennen (rennen_id, datum_uhrzeit, rennstrecken_id) VALUES (default, '2024-08-18 14:00:00', 1);
INSERT INTO Rennen (rennen_id, datum_uhrzeit, rennstrecken_id) VALUES (default, '2023-08-20 14:00:00', 1);

INSERT INTO Team (team_id, name, gruendungsjahr, nationalitaets_id, sponsor_id) VALUES (default, 'Mercedes', 2009, 2, 1);
INSERT INTO Team (team_id, name, gruendungsjahr, nationalitaets_id, sponsor_id) VALUES (default, 'McLaron', 1966, 3, 2);
INSERT INTO Team (team_id, name, gruendungsjahr, nationalitaets_id, sponsor_id) VALUES (default, 'Ferrari', 1946, 4, 3);
INSERT INTO Team (team_id, name, gruendungsjahr, nationalitaets_id, sponsor_id) VALUES (default, 'Red Bull Racing', 2004, 8, 4);
INSERT INTO Team (team_id, name, gruendungsjahr, nationalitaets_id, sponsor_id) VALUES (default, 'Ducati Lenovo Team', 2003, 4, 5);
INSERT INTO Team (team_id, name, gruendungsjahr, nationalitaets_id, sponsor_id) VALUES (default, 'Prima Pramac Yamaha MotoGP', 2002, 4, 6);
INSERT INTO Team (team_id, name, gruendungsjahr, nationalitaets_id, sponsor_id) VALUES (default, 'Red Bull KTM Factory Racing', 2017, 8, 7);
INSERT INTO Team (team_id, name, gruendungsjahr, nationalitaets_id, sponsor_id) VALUES (default, 'Pertamina Enduro VR46 Racing Team', 2014, 4, 8);

INSERT INTO Fahrzeugtyp (fahrzeugtyp_id, modell, motor, gewicht_kg) VALUES (default, 'Mercedes-AMG F1 W15 E Performance', 'Mercedes 1.6 Liter V6-Turbomotor', 798);
INSERT INTO Fahrzeugtyp (fahrzeugtyp_id, modell, motor, gewicht_kg) VALUES (default, 'McLaren MCL38', 'Mercedes-AMG F1 M15 E Performance 1.6L V6-Turbomotor', 798);
INSERT INTO Fahrzeugtyp (fahrzeugtyp_id, modell, motor, gewicht_kg) VALUES (default, 'Ferrari SF-24', '1,6-Liter-V6-Turbomotor ', 798);
INSERT INTO Fahrzeugtyp (fahrzeugtyp_id, modell, motor, gewicht_kg) VALUES (default, 'Red Bull RB19', 'Honda RBPTH001 1,6-Liter V6-Turbomotor', 798);
INSERT INTO Fahrzeugtyp (fahrzeugtyp_id, modell, motor, gewicht_kg) VALUES (default, 'Ferrari SF-23', 'Ferrari 066/10 1,6-Liter V6-Turbomotor', 798);
INSERT INTO Fahrzeugtyp (fahrzeugtyp_id, modell, motor, gewicht_kg) VALUES (default, 'Ducati Desmosedici GP23', 'V4-Viertaktmotor', 157);
INSERT INTO Fahrzeugtyp (fahrzeugtyp_id, modell, motor, gewicht_kg) VALUES (default, 'Ducati Desmosedici GP24', 'V4-Viertaktmotor', 157);
INSERT INTO Fahrzeugtyp (fahrzeugtyp_id, modell, motor, gewicht_kg) VALUES (default, 'Ducati Desmosedici GP22', 'V4-Viertaktmotor', 157);
INSERT INTO Fahrzeugtyp (fahrzeugtyp_id, modell, motor, gewicht_kg) VALUES (default, 'KTM RC16', 'V4-Viertaktmotor', 157);

INSERT INTO Fahrzeug (fahrzeug_id, fahrzeugtyp_id, team_id) VALUES (default, 1, 1);
INSERT INTO Fahrzeug (fahrzeug_id, fahrzeugtyp_id, team_id) VALUES (default, 2, 2);
INSERT INTO Fahrzeug (fahrzeug_id, fahrzeugtyp_id, team_id) VALUES (default, 3, 3);
INSERT INTO Fahrzeug (fahrzeug_id, fahrzeugtyp_id, team_id) VALUES (default, 2, 2);
INSERT INTO Fahrzeug (fahrzeug_id, fahrzeugtyp_id, team_id) VALUES (default, 4, 4);
INSERT INTO Fahrzeug (fahrzeug_id, fahrzeugtyp_id, team_id) VALUES (default, 5, 3);
INSERT INTO Fahrzeug (fahrzeug_id, fahrzeugtyp_id, team_id) VALUES (default, 4, 4);
INSERT INTO Fahrzeug (fahrzeug_id, fahrzeugtyp_id, team_id) VALUES (default, 6, 5);
INSERT INTO Fahrzeug (fahrzeug_id, fahrzeugtyp_id, team_id) VALUES (default, 7, 6);
INSERT INTO Fahrzeug (fahrzeug_id, fahrzeugtyp_id, team_id) VALUES (default, 7, 5);
INSERT INTO Fahrzeug (fahrzeug_id, fahrzeugtyp_id, team_id) VALUES (default, 8, 8);
INSERT INTO Fahrzeug (fahrzeug_id, fahrzeugtyp_id, team_id) VALUES (default, 9, 7);

INSERT INTO Fahrer (fahrer_id, vorname, nachname, nationalitaets_id, fahrzeug_id) VALUES (default, 'Georg', 'Russel', 3, 1);
INSERT INTO Fahrer (fahrer_id, vorname, nachname, nationalitaets_id, fahrzeug_id) VALUES (default, 'Oscar', 'Piastri', 1, 2);
INSERT INTO Fahrer (fahrer_id, vorname, nachname, nationalitaets_id, fahrzeug_id) VALUES (default, 'Carlos', 'Sainz', 9, 3);
INSERT INTO Fahrer (fahrer_id, vorname, nachname, nationalitaets_id, fahrzeug_id) VALUES (default, 'Lando', 'Norris', 3, 4);
INSERT INTO Fahrer (fahrer_id, vorname, nachname, nationalitaets_id, fahrzeug_id) VALUES (default, 'Max', 'Verstappen', 7, 5);
INSERT INTO Fahrer (fahrer_id, vorname, nachname, nationalitaets_id, fahrzeug_id) VALUES (default, 'Charles', 'Leclerc', 6, 6);
INSERT INTO Fahrer (fahrer_id, vorname, nachname, nationalitaets_id, fahrzeug_id) VALUES (default, 'Sergio', 'Pèrez', 5, 7);
INSERT INTO Fahrer (fahrer_id, vorname, nachname, nationalitaets_id, fahrzeug_id) VALUES (default, 'Francesco', 'Bagnaia', 4, 8);
INSERT INTO Fahrer (fahrer_id, vorname, nachname, nationalitaets_id, fahrzeug_id) VALUES (default, 'Brad', 'Binder', 10, 12);
INSERT INTO Fahrer (fahrer_id, vorname, nachname, nationalitaets_id, fahrzeug_id) VALUES (default, 'Marco', 'Bezzecchi', 4, 11);
INSERT INTO Fahrer (fahrer_id, vorname, nachname, nationalitaets_id, fahrzeug_id) VALUES (default, 'Jorge', 'Martin', 9, 9);
INSERT INTO Fahrer (fahrer_id, vorname, nachname, nationalitaets_id, fahrzeug_id) VALUES (default, 'Enea', 'Bastianini', 4, 10);

INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (1, 1, 1, '1 hour 24 minutes 22.798 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (2, 1, 2, '1 hour 24 minutes 24.704 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (3, 1, 3, '1 hour 24 minutes 27.331 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (4, 1, 5, NULL);
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (5, 2, 1, '1 hour 25 minutes 33.607 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (6, 2, 2, '1 hour 25 minutes 38.762 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (7, 2, 3, '1 hour 25 minutes 50.795 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (3, 2, 4, '1 hour 26 minutes 04.984 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (1, 2, 4, '1 hour 26 minutes 22.010 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (2, 2, 4, '1 hour 26 minutes 20.176 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (5, 1, 4, '1 hour 25 minutes 00.051 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (6, 1, 4, '1 hour 25 minutes 29.854 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (7, 1, 4, '1 hour 25 minutes 17.470 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (4, 2, 4, '1 hour 25 minutes 59.934 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (8, 3, 1, '0 hour 42 minutes 11.173 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (11, 3, 2, '0 hour 42 minutes 14.405 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (12, 3, 3, '0 hour 42 minutes 18.530 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (8, 4, 1, '0 hour 42 minutes 23.315 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (9, 4, 2, '0 hour 42 minutes 28.506 seconds');
INSERT INTO Rennen_Fahrer (fahrer_id, rennen_id, status_id, zeit) VALUES (10, 4, 3, '0 hour 42 minutes 31.023 seconds');


