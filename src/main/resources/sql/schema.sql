create database artWood;

use artWood;

create table employee(
    emp_id varchar(50) primary key,
	name varchar(100) not null,
	address varchar(100) not null,
    tel int(10) not null,
    status varchar(20),
    job_role varchar(100) not null
);

insert into employee values("E1","Kamal","Galle",0719568456,"no","stock_manager");
insert into employee values("E0","Nimal","Galle",0775538451,"no","owner");
insert into employee values("E2","Tharindu","Mathara",0772139752,"no","cashier");


create table login(
	user_name varchar(100) primary key,
	pw varchar(10) not null,
    emp_id varchar(50) not null,
    foreign key (emp_id) references employee (emp_id) on update cascade on delete cascade
);

insert into login values("cash","c_123","E2");
insert into login values("admin","o_123","E0");


select e.job_role, l.user_name, l.pw from employee e join login l on e.emp_id = l.emp_id where user_name = "cash";



create table finance(
    pay_method varchar(20) primary key,
	amount double(10,2) not null
);


create table salary(
    salary_id varchar(20) primary key,
	amount double(7,2) not null,
    emp_id varchar(50) not null,
	foreign key (emp_id) references employee(emp_id) on update cascade on delete cascade
);


create table customer(
    cus_id varchar(20),
	name varchar(50) not null,
	address varchar (50) not null,
	tel int(10) primary key
);


create table orders(
    order_id varchar(20) primary key,
	date date not null,
    tel int(10),
    foreign key (tel) references customer (tel) on update cascade on delete cascade
);


create table supplier(
    sup_id varchar(20) primary key,
	sup_name varchar(50) not null,
	address varchar (50) not null,
    email varchar(50) not null
);


create table sup_orders(
    sup_order_id varchar(20) primary key,
	price double(10,2) not null,
    wood_type varchar(50) not null,
    sup_id varchar(20) not null,
    foreign key (sup_id) references supplier (sup_id) on update cascade on delete cascade
);


create table product_type(
    product_id varchar(20) primary key,
	product_name varchar(50) not null,
	quality varchar (50) not null,
	wood_type varchar(50) not null,
    price double(7,2) not null
);


create table log_stock(
    logs_id varchar(20) primary key,
	wood_type varchar(50) not null,
    logs_amount int(5) not null
);


create table sup_order_details(
    date date,
    logs_id varchar(20),
    foreign key (logs_id) references log_stock (logs_id) on update cascade on delete cascade,
	sup_order_id varchar(20),
    foreign key (sup_order_id) references sup_orders (sup_order_id) on update cascade on delete cascade
);


create table wood_pieces_stock(
    wood_piece_id varchar(20) primary key,
	quality varchar(50) not null,
    wood_amount int(5) not null,
    wood_type varchar(50) not null,
    logs_id varchar(20) not null,
    foreign key (logs_id) references log_stock (logs_id) on update cascade on delete cascade
);


create table finished_stock(
    finished_stock_id varchar(20) primary key,
    finished_amount int(5) not null,
    product_id varchar(20) not null,
    foreign key (product_id) references product_type (product_id) on update cascade on delete cascade
);


create table pending_stock(
    pending_stock_id varchar(20) primary key,
    pending_amount int(5) not null,
    emp_id varchar(20) not null,
    foreign key (emp_id) references employee (emp_id) on update cascade on delete cascade,
    wood_piece_id varchar(20) not null,
    foreign key (wood_piece_id) references wood_pieces_stock (wood_piece_id) on update cascade on delete cascade,
    finished_stock_id varchar(20) not null,
    foreign key (finished_stock_id) references finished_stock (finished_stock_id) on update cascade on delete cascade
);


create table order_details(
    order_id varchar(20) not null,
    finished_stock_id varchar(20),
    qty int not null,
    unit_price double not null,
    foreign key (finished_stock_id) references finished_stock (finished_stock_id) on update cascade on delete cascade,
    foreign key (order_id) references orders (order_id) on update cascade on delete cascade
);
