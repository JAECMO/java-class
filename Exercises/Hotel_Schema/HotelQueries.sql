/* Hotel Query Response Worksheet:
Please review the questions below and submit 
your Query Script for each.  Once completed please 
upload your work to engage. The expected results are
provided to assist you. */ 

/* #1 
Write a query that returns a list of reservations that end in July 2023, 
including the name of the guest, the room number(s), and the reservation dates.
​
Expected Results 4 rows */

SELECT
Reservations.ReservationsId,
Guests.Name AS 'Guest Name',
Roomreservations.RoomNumber AS 'Room Number',
Reservations.StartDate,
Reservations.EndDate
FROM Guests
JOIN Reservations ON Guests.GuestsId = Reservations.GuestsId
JOIN RoomReservations ON Reservations.ReservationsId = RoomReservations.ReservationsId
WHERE Reservations.EndDate LIKE '2023-07%';

-- ANSWER:	SELECT Reservations.ReservationsId, Guests.Name AS 'Guest Name', Roomreservations.RoomNumber AS 'Room Number', Reservations.StartDate, Reservations.EndDate FROM Guests JOIN Reservations ON Guests.GuestsId = Reservations.GuestsId JOIN RoomReservations ON Reservations.ReservationsId = RoomReservations.ReservationsId WHERE Reservations.EndDate LIKE '2023-07%'	
-- 			4 row(s) returned	0.000 sec / 0.000 sec

-- ReservationsId	Guest Name			Room Number	StartDate	EndDate
-- 2				Jalal Abdul-Hadi	205			2023-06-28	2023-07-02
-- 7				Bettyann Seery		303			2023-07-28	2023-07-29
-- 18				Walter Holaway		204			2023-07-13	2023-07-14
-- 20				Wilfred Vise		401			2023-07-18	2023-07-21

/* #2
Write a query that returns a list of all reservations for rooms with a jacuzzi, 
displaying the guest's name, the room number, and the dates of the reservation.
​
Expected Results 11 rows*/

SELECT
Reservations.ReservationsId,
Guests.Name AS 'Guest Name',
Room.RoomNumber AS 'Room Number',
Reservations.StartDate,
Reservations.EndDate
FROM Guests
JOIN Reservations ON Guests.GuestsId = Reservations.GuestsId
JOIN RoomReservations ON Reservations.ReservationsId = RoomReservations.ReservationsId
JOIN Room ON RoomReservations.RoomNumber = Room.RoomNumber
JOIN RoomAmenities ON Room.RoomNumber = RoomAmenities.RoomNumber
JOIN Amenities On RoomAmenities.AmenitiesId = Amenities.AmenitiesId
WHERE Amenities.AmenitiesName = 'Jacuzzi'
ORDER BY Reservations.ReservationsId;

-- ANSWER:	SELECT Reservations.ReservationsId, Guests.Name AS 'Guest Name', Room.RoomNumber AS 'Room Number', Reservations.StartDate, Reservations.EndDate FROM Guests JOIN Reservations ON Guests.GuestsId = Reservations.GuestsId JOIN RoomReservations ON Reservations.ReservationsId = RoomReservations.ReservationsId JOIN Room ON RoomReservations.RoomNumber = Room.RoomNumber JOIN RoomAmenities ON Room.RoomNumber = RoomAmenities.RoomNumber JOIN Amenities On RoomAmenities.AmenitiesId = Amenities.AmenitiesId WHERE Amenities.AmenitiesName = 'Jacuzzi' ORDER BY Reservations.ReservationsId	
-- 			11 row(s) returned	0.016 sec / 0.000 sec

-- ReservationsId	Guest Name			Room Number	StartDate	EndDate
-- 1	            Jalal Abdul-Hadi	307			2023-03-17	2023-03-20
-- 2	            Jalal Abdul-Hadi	205			2023-06-28	2023-07-02
-- 5	            Mack Simmer			301			2023-11-22	2023-11-25
-- 6				Bettyann Seery		203			2023-02-05	2023-02-10
-- 7				Bettyann Seery		303			2023-07-28	2023-07-29
-- 8				Bettyann Seery		305			2023-08-30	2023-09-01
-- 9				Duane Cullison		305			2023-02-22	2023-02-24
-- 11				Karie Yang			201			2023-03-06	2023-03-07
-- 12				Karie Yang			203			2023-09-13	2023-09-15
-- 17				Walter Holaway		301			2023-04-09	2023-04-13
-- 19				Wilfred Vise		207			2023-04-23	2023-04-24


/* # 3
Write a query that returns all the rooms reserved for a specific guest, 
including the guest's name, the room(s) reserved, the starting date of the reservation, 
and how many people were included in the reservation. 
(Choose a guest's name from the existing data.)
​
Expected Results 4 rows */

SELECT
Guests.Name AS 'Guest Name',
RoomReservations.RoomNumber AS 'Room Number',
Reservations.StartDate,
RoomReservations.Adults + RoomReservations.Children AS 'Number of people'
FROM Guests
JOIN Reservations ON Guests.GuestsId = Reservations.GuestsId
JOIN RoomReservations ON Reservations.ReservationsId = RoomReservations.ReservationsId
WHERE Guests.GuestsId = 2;

-- ANSWER:	SELECT Guests.Name AS 'Guest Name', RoomReservations.RoomNumber AS 'Room Number', Reservations.StartDate, RoomReservations.Adults + RoomReservations.Children AS 'Number of people' FROM Guests JOIN Reservations ON Guests.GuestsId = Reservations.GuestsId JOIN RoomReservations ON Reservations.ReservationsId = RoomReservations.ReservationsId WHERE Guests.GuestsId = 2	
-- 			4 row(s) returned	0.000 sec / 0.000 sec

-- Guest Name	Room Number	StartDate	Number of people
-- Mack Simmer	308			2023-02-02		1
-- Mack Simmer	208			2023-09-16		2
-- Mack Simmer	206			2023-11-22		2
-- Mack Simmer	301			2023-11-22		4

/* #4
Write a query that returns a list of rooms, reservation ID, and per-room 
cost for each reservation. The results should include all rooms, 
whether or not there is a reservation associated with the room.
​
Expected Results 26 Rows */

SELECT
Room.RoomNumber AS 'Room Number',
RoomReservations.ReservationsId,
RoomReservations.TotalCost AS 'Total Cost'
FROM Room
LEFT OUTER JOIN RoomReservations ON Room.RoomNumber = RoomReservations.RoomNumber;

-- ANSWER:     SELECT Room.RoomNumber AS 'Room Number', RoomReservations.ReservationsId, RoomReservations.TotalCost AS 'Total Cost' FROM Room LEFT OUTER JOIN RoomReservations ON Room.RoomNumber = RoomReservations.RoomNumber	
-- 			   26 row(s) returned	0.000 sec / 0.000 sec

-- Room Number	ReservationsId	Total Cost
-- 201			11				199.99
-- 202			15				349.98
-- 203			6				999.95
-- 203			12				399.98
-- 204			18				184.99
-- 205			2				699.96
-- 206			5				449.97
-- 206			23				599.96
-- 207			19				174.99
-- 208			4				149.99
-- 208			23				599.96
-- 301			5				599.97
-- 301			17				799.96
-- 302			13				924.95
-- 302			22				699.96
-- 303			7				199.99
-- 304			14				184.99
-- 305			8				349.98
-- 305			9				349.98
-- 306			NULL			NULL
-- 307			1				524.97
-- 308			3				299.98
-- 401			10				1199.97
-- 401			20				1259.97
-- 401			21				1199.97
-- 402			NULL			NULL

/* #5
Write a query that returns all the rooms accommodating at least 
three guests and that are reserved on any date in April 2023.
​
Expected Results: Write your response on why */

SELECT
RoomReservations.RoomNumber AS 'Room Number',
RoomReservations.Adults + RoomReservations.Children AS NumberofGuests,
Reservations.StartDate AS 'Start Date',
Reservations.EndDate AS 'End Date'
FROM RoomReservations
JOIN Reservations ON RoomReservations.ReservationsId = Reservations.ReservationsId
WHERE Reservations.StartDate <= '2023-04-30' AND  Reservations.EndDate >= '2023-04-01'
HAVING NumberofGuests > 2;

-- ANSWER : SELECT RoomReservations.RoomNumber AS 'Room Number', RoomReservations.Adults + RoomReservations.Children AS NumberofGuests, Reservations.StartDate AS 'Start Date', Reservations.EndDate AS 'End Date' FROM RoomReservations JOIN Reservations ON RoomReservations.ReservationsId = Reservations.ReservationsId WHERE Reservations.StartDate <= '2023-04-30' AND  Reservations.EndDate >= '2023-04-01' HAVING NumberofGuests > 2	
-- 		0 row(s) returned	0.000 sec / 0.000 sec

-- No room satisfy the conditions stipulated

-- I evaluated that a room is reserved at any time in the month of april by having the starting reservation date anytime in the year that is AT/OR BEFORE  the last day of the month
-- and the ending reservation date anytime in the year AT/OR AFTER the first day of the month.
-- To determine the number of guests I added the number of Adults and Children for the room that in reserved anytime in april.
-- To have a room accomodating at least 3 guests the total number of guests has to be higher than 2.



/* #6
Write a query that returns a list of all guest names and the 
number of reservations per guest, sorted starting with the guest 
with the most reservations and then by the guest's last name.
​
Expected Results 11 rows */


SELECT 
SUBSTRING_INDEX(Guests.Name, ' ', 1) AS FirstName,
SUBSTRING_INDEX(Guests.Name, ' ', -1) AS LastName,
COUNT(DISTINCT Reservations.ReservationsId) Number_of_Reservations /* Some reservations have more than one room reserved at the same date */
FROM Guests
JOIN Reservations ON Guests.GuestsId = Reservations.GuestsId
JOIN RoomReservations ON Reservations.ReservationsId = RoomReservations.ReservationsId 
GROUP BY  Reservations.GuestsId
ORDER BY Number_of_Reservations DESC, LastName  ;

-- ANSWER : SELECT  SUBSTRING_INDEX(Guests.Name, ' ', 1) AS FirstName, SUBSTRING_INDEX(Guests.Name, ' ', -1) AS LastName, COUNT(DISTINCT Reservations.ReservationsId) Number_of_Reservations FROM Guests JOIN Reservations ON Guests.GuestsId = Reservations.GuestsId JOIN RoomReservations ON Reservations.ReservationsId = RoomReservations.ReservationsId  GROUP BY  Reservations.GuestsId ORDER BY Number_of_Reservations DESC, LastName	
-- 		 11 row(s) returned	0.016 sec / 0.000 sec

-- FirstName	LastName	Number_of_Reservations
-- Bettyann		Seery		3
-- Mack			Simmer		3
-- Jalal		Abdul-Hadi	2
-- Duane		Cullison	2
-- Walter		Holaway		2
-- Aurore		Lipton		2
-- Maritza		Tilton		2
-- Wilfred		Vise		2
-- Karie		Yang		2
-- Zachery		Luechtefeld	1
-- Joleen		Tison		1

	

/* #7
Write a query that displays the name, address, and phone number of a 
guest based on their phone number. (Choose a phone number from the existing data.)
​
Expected Results 1 row */

SELECT
Guests.Name AS 'Guest Name',
Guests.Address,
CONCAT('(', SUBSTR(Guests.Phone, 1, 3),') ',SUBSTR(Guests.Phone, 4, 3),'-',SUBSTR(Guests.Phone, 7)) AS Phone
FROM Guests
WHERE Guests.Phone = '3775070974';

-- ANSWER :	SELECT Guests.Name AS 'Guest Name', Guests.Address, CONCAT('(', SUBSTR(Guests.Phone, 1, 3),') ',SUBSTR(Guests.Phone, 4, 3),'-',SUBSTR(Guests.Phone, 7)) AS Phone FROM Guests WHERE Guests.Phone = '3775070974'	
-- 			1 row(s) returned	0.000 sec / 0.000 sec

-- Guest Name	    Address	                Phone
-- Aurore Lipton	762 Wild Rose Street	(377) 507-0974

