DROP DATABASE IF EXISTS HotelDb;

CREATE DATABASE HotelDb;

USE HotelDb;

CREATE TABLE Room (
	RoomNumber INT PRIMARY KEY,
    RoomType VARCHAR(30) NOT NULL,
    BasePrice DECIMAL(10,2) NOT NULL,
    ExtraPersonPrice INT NULL,
    StandardOccupancy INT NOT NULL,
    MaximumOccupancy INT NOT NULL,
    ADA VARCHAR(3) NOT NULL
);

CREATE TABLE Guests (
	GuestsId INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL,
    Address VARCHAR(50) NOT NULL,
    City VARCHAR(50) NOT NULL,
    State CHAR(2) NOT NULL,
    Zip CHAR(5) NOT NULL,
    Phone CHAR(10) NOT NULL
);

CREATE TABLE Amenities (
	AmenitiesId INT PRIMARY KEY AUTO_INCREMENT ,
    AmenitiesName VARCHAR(30) NOT NULL
);

CREATE TABLE RoomAmenities (
    RoomNumber INT NOT NULL,
    AmenitiesId INT NOT NULL,
    PRIMARY KEY (RoomNumber, AmenitiesId),
    FOREIGN KEY fk_RoomAmenities_RoomNumber (RoomNumber)
		REFERENCES Room (RoomNumber),
    FOREIGN KEY fk_RoomAmenities_AmenitiesId (AmenitiesId)
		REFERENCES Amenities (AmenitiesId)
);


CREATE TABLE Reservations (
	ReservationsId INT PRIMARY KEY AUTO_INCREMENT,
    GuestsId INT NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    FOREIGN KEY fk_Reservations_Guests (GuestsId)
        REFERENCES Guests (GuestsId)

);

CREATE TABLE RoomReservations (
    ReservationsId INT NOT NULL,
    RoomNumber INT NOT NULL,
    Adults INT NOT NULL,
    Children INT NOT NULL,
    TotalCost DECIMAL (10,2) NOT NULL,
    PRIMARY KEY (RoomNumber, ReservationsId),
    FOREIGN KEY fk_RoomReservations_ReservationsId (ReservationsId)
		REFERENCES Reservations(ReservationsId),
    FOREIGN KEY fk_RoomReservations_RoomNumber (RoomNumber)
		REFERENCES Room (RoomNumber)
);

  


