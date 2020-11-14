create database shop;
use shop;

create table Clients (
	id int AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    address varchar(60) NOT NULL,
    
    PRIMARY KEY(ID)
) ENGINE = INNODB;

create table Products (
	id int AUTO_INCREMENT,
    name varchar(35) NOT NULL,
    amount int NOT NULL,
    price double NOT NULL,
    
    PRIMARY KEY(id)
) ENGINE = INNODB;

create table OrderItems (
	orderId int,
    productId int NOT NULL,
    amount int NOT NULL,
    cost double NOT NULL,
    
    PRIMARY KEY(orderId, productId)
) ENGINE = INNODB;

create table Orders (
	id int AUTO_INCREMENT,
    clientId int NOT NULL,
    total double NOT NULL,
	
    PRIMARY KEY(id)
) ENGINE = INNODB;

ALTER TABLE Orders
ADD FOREIGN KEY (clientId)
REFERENCES Clients (id);

ALTER TABLE OrderItems
ADD FOREIGN KEY (orderId)
REFERENCES Orders (id);

ALTER TABLE OrderItems
ADD FOREIGN KEY (productId)
REFERENCES Products (id);