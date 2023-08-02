INSERT INTO Room (RoomNumber, RoomType, BasePrice, ExtraPersonPrice, StandardOccupancy, MaximumOccupancy,ADA) VALUES
    (201, 'Double', 199.99, 10, 2, 4, 'No'),
    (202, 'Double', 174.99, 10, 2, 4, 'Yes'),
    (203, 'Double', 199.99, 10, 2, 4, 'No'),
    (204, 'Double', 174.99, 10, 2, 4, 'Yes'),
    (205, 'Single', 174.99, NULL, 2, 2, 'No'),
    (206, 'Single', 149.99, NULL, 2, 2, 'Yes'),
	(207, 'Single', 174.99, NULL, 2, 2, 'No'),
    (208, 'Single', 149.99, NULL, 2, 2, 'Yes'),
    (301, 'Double', 199.99, 10, 2, 4, 'No'),
    (302, 'Double', 174.99, 10, 2, 4, 'Yes'),
    (303, 'Double', 199.99, 10, 2, 4, 'No'),
    (304, 'Double', 174.99, 10, 2, 4, 'Yes'),
    (305, 'Single', 174.99, NULL, 2, 2, 'No'),
    (306, 'Single', 149.99, NULL, 2, 2, 'Yes'),
    (307, 'Single', 174.99, NULL, 2, 2, 'No'),
    (308, 'Single', 149.99, NULL, 2, 2, 'Yes'),
	(401, 'Suite', 399.99, 20, 3, 8, 'Yes'),
    (402, 'Suite', 399.99, 20, 3, 8, 'Yes');
    
    INSERT INTO Guests (Name, Address, City, State, Zip, Phone) VALUES
    ('Jalal Abdul-Hadi', '25 Success Ave', 'Gotham', 'AL', '12345', '9999999999'),
    ('Mack Simmer', '379 Old Shore Street', 'Council Bluffs', 'IA','51501','2915530508'),
    ('Bettyann Seery', '750 Wintergreen Dr.', 'Wasilla', 'AK', '99654', '4782779632'),
    ('Duane Cullison', '9662 Foxrun Lane.', 'Harlingen', 'TX', '78552', '3084940198'),
    ('Karie Yang', '9378 W. Augusta Ave.', 'West Deptford', 'NJ', '08096', '2147300298'),
    ('Aurore Lipton', '762 Wild Rose Street', 'Saginaw', 'MI', '48601', '3775070974'),
	('Zachery Luechtefeld', '7 Poplar Dr.', 'Arvada', 'CO', '80003', '8144852615'),
    ('Jeremiah Pendergrass', '70 Oakwood St.', 'Zion', 'IL', '60099', '2794910960'),
    ('Walter Holaway', '7556 Arrowhead St.', 'Cumberland', 'RI', '02864', '4463966785'),
    ('Wilfred Vise', '77 West Surrey Street', 'Oswego', 'NY	', '13126', '8347271001'),
    ('Maritza Tilton', '939 Linda Rd.', 'Burke', 'VA', '22015', '4463516860'),
    ('Joleen Tison', '87 Queen St.', 'Drexel Hill', 'PA', '19026', '2318932755');
    
    INSERT INTO Amenities (AmenitiesName) VALUES
    ('Microwave'),
    ('Jacuzzi'),
    ('Refrigerator'),
    ('Oven');
    
    INSERT INTO RoomAmenities (RoomNumber, AmenitiesId) VALUES
    (201, 1),
    (201, 2),
    (202, 3),
    (203, 1),
    (203, 2),
    (204, 3),
    (205, 1),
    (205, 3),
    (205, 2),
	(206, 1),
    (206, 3),
	(207, 1),
    (207, 3),
    (207, 2),
    (208, 1),
    (208, 3),
    (301, 1),
    (301, 2),
    (302, 3),
	(303, 1),
    (303, 2),
    (304, 3),
    (305, 1),
    (305, 3),
    (305, 2),
    (306, 1),
    (306, 3),
	(307, 1),
    (307, 3),
    (307, 2),
    (308, 1),
    (308, 3),
    (401, 1),
    (401, 3),
    (401, 4),
    (402, 1),
    (402, 3),
    (402, 4);
    
    INSERT INTO Reservations (GuestsId, StartDate, EndDate) VALUES
    (1, '2023-03-17', '2023-03-20'),
    (1, '2023-06-28', '2023-07-02'),
    (2, '2023-02-02', '2023-02-04'),
    (2, '2023-09-16', '2023-09-17'),
    (2, '2023-11-22', '2023-11-25'),
    (3, '2023-02-05', '2023-02-10'),
    (3, '2023-07-28', '2023-07-29'),
    (3, '2023-08-30', '2023-09-01'),
    (4, '2023-02-22', '2023-02-24'),
    (4, '2023-11-22', '2023-11-25'),
    (5, '2023-03-06', '2023-03-07'),
    (5, '2023-09-13', '2023-09-15'),
    (6, '2023-03-18', '2023-03-23'),
    (6, '2023-06-17', '2023-06-18'),
    (7, '2023-03-29', '2023-03-31'),
    (8, '2023-03-31', '2023-04-05'),
    (9, '2023-04-09', '2023-04-13'),
    (9, '2023-07-13', '2023-07-14'),
	(10, '2023-04-23', '2023-04-24'),	
    (10, '2023-07-18', '2023-07-21'),
	(11, '2023-05-30', '2023-06-02'),
    (11, '2023-12-24', '2023-12-28'),
    (12, '2023-06-10', '2023-06-14');
    
    INSERT INTO RoomReservations (ReservationsId, RoomNumber, Adults, Children, TotalCost) VALUES
    (1, 307, 1, 1, 524.97),
    (2, 205, 2, 0, 699.96),
    (3, 308, 1, 0, 299.98),
    (4, 208, 2, 0, 149.99),
    (5, 206, 2, 0, 449.97),
    (5, 301, 2, 2, 599.97),
	(6, 203, 2, 1, 999.95),
    (7, 303, 2, 1, 199.99),
    (8, 305, 1, 0, 349.98),
    (9, 305, 2, 0, 349.98),
    (10, 401, 2, 2, 1199.97),
    (11, 201, 2, 2, 199.99),
	(12, 203, 2, 2, 399.98),
    (13, 302, 3, 0, 924.95),
    (14, 304, 3, 0, 184.99),
    (15, 202, 2, 2, 349.98),
    (16, 304, 2, 0, 874.95),
    (17, 301, 1, 0, 799.96),
	(18, 204, 3, 1, 184.99),
    (19, 207, 1, 1, 174.99),
    (20, 401, 4, 2, 1259.97),
    (21, 401, 2, 4, 1199.97),
    (22, 302, 2, 0, 699.96),
    (23, 206, 2, 0, 599.96),
    (23, 208, 1, 0, 599.96);
    
    SET SQL_SAFE_UPDATES = 0;
    
    DELETE FROM RoomReservations
    WHERE ReservationsId = (
    SELECT DISTINCT
    ReservationsId
    FROM Reservations
    JOIN Guests ON Reservations.GuestsId = Guests.guestsId
    WHERE Guests.GuestsId = 8);
    
    DELETE FROM Reservations
    WHERE GuestsId = 8;
    
    DELETE FROM Guests
	WHERE GuestsId = 8;
    
    SET SQL_SAFE_UPDATES = 1;
    
    
    
    
    
    
    
    