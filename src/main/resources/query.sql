use db_movie;

select movie.id from movie 
inner join schedule on movie.id = schedule.movieId 
inner join ticket on ticket.scheduleId = schedule.id 
inner join billTicket on billTicket.ticketId = ticket.id 
group by movie.id 
order by sum(billTicket.quantity) desc;

select movie.id, count(ticket.id) from movie 
inner join schedule on movie.id = schedule.movieId 
inner join ticket on ticket.scheduleId = schedule.id 
inner join billTicket on billTicket.ticketId = ticket.id 
group by movie.id 
order by count(ticket.id) desc;

select movie.id,  from movie 
inner join schedule on movie.id = schedule.movieId 
inner join ticket on ticket.scheduleId = schedule.id 
inner join billTicket on billTicket.ticketId = ticket.id 
group by movie.id 
order by count(billTicket.ticketId) desc;


-- display movie by cinema, room, seatStatus
select distinct movie.id from movie 
inner join schedule on movie.id = schedule.movieId 
inner join room on schedule.roomId = room.id 
inner join cinema on room.cinemaId = cinema.id 
inner join seat on room.id = seat.roomId 
inner join seatStatus on seat.seatStatusId = seatStatus.id 
where nameOfCinema like '%c%' 
and room.name like '%r%' 
and seatStatus.nameStatus like '%NOT_EMPTY%' 
;

-- display schedule in :date
select schedule.* from schedule 
where year(startAt) = 2024 
and month(startAt) = 12 
and day(startAt) = 12;

-- display schedule in :date and room
select schedule.* from schedule 
where year(startAt) = 2024 
and month(startAt) = 3 
and day(startAt) = 25 
and roomId = 1;

-- display roomId from seatId
select seat.roomId from seat 
where seat.id = 100;

-- display list schedule from roomId
select distinct schedule.id from schedule 
where roomId = 10;

-- display cinema have schedule movie(id)
select distinct cinema.* from cinema 
inner join room on cinema.id = room.cinemaId 
inner join schedule on room.id = schedule.roomId 
where movieId = 4;

-- display room of cinema have screening movie
select distinct room.* from room 
inner join cinema on room.cinemaId = cinema.id 
inner join schedule on schedule.roomId = room.id 
inner join movie on movie.id = schedule.movieId 
where movie.id = 4 
and cinema.id = 2;

-- display schedule screening movie at room
select distinct schedule.* from schedule 
inner join movie on movie.id = schedule.movieId 
inner join room on room.id = schedule.roomId 
where movie.id = 2 
and room.id = 1;

-- display user
select user.* from user 
limit 1
;

-- display bill from userId
select bill.* from bill 
inner join billStatus on billStatus.id = bill.id 
where bill.customerId = 1 
and billStatus.name like "%UNPAID%" 
limit 1;

select count(DISTINCT bill.id) from bill 
inner join billStatus on billStatus.id = bill.id 
where bill.customerId = 2 
and billStatus.name like "%UNPAID%";

-- count promotion from rankCustomerId
select count(promotion.id) from promotion 
where rankCustomerId = 1;

-- count user by rankCustomerId
select count(user.id) from user 
where rankCustomerId = 1;

-- display promotion list from username
select distinct promotion.* from promotion 
inner join rankCustomer on rankCustomer.id = promotion.rankCustomerId 
inner join user on user.rankCustomerId = rankCustomer.id 
where username like "sasuke";

-- display promotion.id list from username
select distinct promotion.id from promotion 
inner join rankCustomer on rankCustomer.id = promotion.rankCustomerId 
inner join user on user.rankCustomerId = rankCustomer.id 
where username like "naruto";

-- display bill from userId
select distinct bill.* from bill 
inner join user on user.id = bill.customerId 
where user.username like :username;

-- display ticket from scheduleId and isActive = true
select ticket.* from ticket 
where scheduleId = :scheduleId 
and isActive = true;

-- display total revenue from cinemaId
select cinemaId, sum(food.price * billFood.quantity) + sum(ticket.priceTicket) as 'Total' from food 
inner join billFood on food.id = billFood.foodId 
inner join bill on bill.id = billFood.billId 
inner join billTicket on billTicket.billId = bill.id 
inner join ticket on ticket.id = billTicket.ticketId 
inner join schedule on schedule.id = ticket.scheduleId 
inner join room on room.id = schedule.roomId 
where createTime > "2024-2-2" 
and createTime < "2024-4-4" 
group by cinemaId
;


-- display total revenue from cinemaId [success - completed]
select room.cinemaId, sum(billFood.quantity * food.price) + sum(ticket.priceTicket) from food 
inner join billFood on food.id = billFood.foodId 
inner join bill on bill.id = billFood.billId 
inner join billTicket on billTicket.billId = bill.id 
inner join ticket on ticket.id = billTicket.ticketId 
inner join schedule on schedule.id = ticket.scheduleId 
inner join room on room.id = schedule.roomId 
where bill.createTime < Date("2024-4-4") 
and bill.createTime > Date("2024-2-2") 
group by room.cinemaId 
; -- doanh so tinh ca chua thanh toan **

-- display total revenue from cinemaId [success - completed]
select concat(room.cinemaId , '---', 
cinema.nameOfCinema, '---', 
sum(billFood.quantity * food.price), '---', 
sum(ticket.priceTicket), '---', 
sum(billFood.quantity * food.price) + sum(ticket.priceTicket)) 
from food 
inner join billFood on food.id = billFood.foodId 
inner join bill on bill.id = billFood.billId 
inner join billTicket on billTicket.billId = bill.id 
inner join ticket on ticket.id = billTicket.ticketId 
inner join schedule on schedule.id = ticket.scheduleId 
inner join room on room.id = schedule.roomId 
inner join cinema on cinema.id = room.cinemaId 
where bill.createTime between "2024-2-2" and "2024-4-4" 
group by room.cinemaId 
; -- doanh so tinh ca chua thanh toan **

select concat(12, "HAAA");

select 2,3;
-- display total revenue from cinemaId
select distinct cinemaId, food.price, billFood.quantity, ticket.priceTicket from food 
inner join billFood on food.id = billFood.foodId 
inner join bill on bill.id = billFood.billId 
inner join billTicket on billTicket.billId = bill.id 
inner join ticket on ticket.id = billTicket.ticketId 
inner join schedule on schedule.id = ticket.scheduleId 
inner join room on room.id = schedule.roomId 
where createTime > "2024-2-2" 
and createTime < "2024-4-4" 
-- group by cinemaId
;


select 157+60;

-- display billId create form 2024-2-2
select billFood.* from billFood 
inner join bill on bill.id = billFood.billId  
inner join billTicket on billTicket.billId = bill.id 
inner join ticket on ticket.id = billTicket.ticketId 
where createTime > "2024-2-2" 
and createTime < "2024-4-4";


-- REVENUE BY CINEMA ID
    -- Display total revenue food from cinema 2
    -- select distinct sum(food.price * billFood.quantity) from billFood 
    -- inner join food on food.id = billFood.foodId 
    -- inner join bill on bill.id = billFood.billId 
    -- inner join billTicket on billTicket.billId = bill.id 
    -- inner join ticket on ticket.id = billTicket.ticketId 
    -- inner join schedule on schedule.id = ticket.scheduleId 
    -- inner join room on room.id = schedule.roomId 
    -- inner join cinema on cinema.id = room.cinemaId 
    -- where cinema.id = 2;

    -- DISPLAY BILL AT CINEMA ID = 2
    select bill.* from bill 
    inner join billTicket on billTicket.billId = bill.id 
    inner join ticket on ticket.id = billTicket.ticketId 
    inner join schedule on schedule.id = ticket.scheduleId 
    inner join room on room.id = schedule.roomId 
    inner join cinema on cinema.id = room.cinemaId 
    group by bill.id; -- => [1,2]
    
    -- DISPLAY BILL TICKET AT BILL ID IN [1,2]
    select room.cinemaId, sum(ticket.priceTicket) from billTicket 
    inner join ticket on ticket.id = billTicket.ticketId 
    inner join schedule on schedule.id = ticket.scheduleId 
    inner join room on room.id = schedule.roomId 
    where billTicket.billId in 
    (select bill.id from bill 
    inner join billTicket on billTicket.billId = bill.id 
    inner join ticket on ticket.id = billTicket.ticketId 
    inner join schedule on schedule.id = ticket.scheduleId 
    inner join room on room.id = schedule.roomId 
    inner join cinema on cinema.id = room.cinemaId 
    group by bill.id) 
    group by room.cinemaId;

    select 181 + 39;
    select 36 + 55 + 90 + 39;

    -- DISPLAY BILL FOOD AT BILL ID IN [1,2]
    select room.cinemaId, sum(food.price * billFood.quantity) from billFood 
    inner join food on food.id = billFood.foodId 
    inner join bill on bill.id = billFood.id 
    inner join billTicket on billTicket.billId = bill.id 
    inner join ticket on ticket.id = billTicket.ticketId 
    inner join schedule on schedule.id = ticket.scheduleId 
    inner join room on room.id = schedule.roomId 
    where billFood.billId in 
    (select bill.id from bill 
    inner join billTicket on billTicket.billId = bill.id 
    inner join ticket on ticket.id = billTicket.ticketId 
    inner join schedule on schedule.id = ticket.scheduleId 
    inner join room on room.id = schedule.roomId 
    inner join cinema on cinema.id = room.cinemaId 
    group by bill.id)
    group by room.cinemaId;


-- DISPLAY FEATURED FOOD IN 1 WEEK
select food.nameOfFood, sum(billFood.quantity) from food 
inner join billFood on billFood.foodId = food.id 
inner join bill on bill.id = billFood.billId 
where bill.createTime >= date_sub(now(), interval 7 year) 
group by food.id 
order by sum(billFood.quantity) desc
;

    select concat(food.id, "---", 
    food.nameOfFood, "---", 
    sum(billFood.quantity)) from food 
    inner join billFood on billFood.foodId = food.id 
    inner join bill on bill.id = billFood.billId 
    where bill.createTime >= date_sub(now(), interval 7 day) 
    group by food.id 
    order by sum(billFood.quantity) desc
    ;

select date_sub(now(), interval 7 day);


--- COUNT BILL BY USER ID
select count(bill.id) from bill 
where bill.customerId = :userId;
--- COUNT REFRESH TOKEN BY USER ID
select count(refreshToken.id) from refreshToken 
where refreshToken.userId = :userId;
--- COUNT CONFIRM EMAIL BY USER ID
select count(confirmEmail.id) from confirmEmail 
where confirmEmail.userId = :userId;