create table testhiber.CUSTOMER (ID number(19,0) not null, CUSTOMER_NAME varchar2(255 char) not null, primary key (ID));

create table testhiber.ORDER_TABLES (ID number(19,0) not null, BILLING_NUMBER number(19,0) not null, PLACING_DATE date not null, CUSTOMER_ID number(19,0) not null, primary key (ID));

create table testhiber.PRODUCT (ID number(19,0) not null, PRODUCT_NAME varchar2(255 char) not null, PRODUCT_UNITS varchar2(255 char) not null, primary key (ID));

create table testhiber.ORDER_ITEMS (ID number(19,0) not null, QUANTITY number(19,2) not null, PRODUCT_ID number(19,0) not null, LIST_INDEX number(10,0) not null, primary key (ID, LIST_INDEX));

create table testhiber.PRODUCT_PRICES (ID number(19,0) not null, PRODUCT_PRICE number(19,2) not null, PRODUCT_PRICE_END_EFFECT_DAY date not null, LIST_INDEX number(10,0) not null, primary key (ID, LIST_INDEX));

alter table testhiber.CUSTOMER add constraint UNIQUE_CUSTOMER_NAME unique (CUSTOMER_NAME);

alter table testhiber.ORDER_TABLES add constraint UNIQUE_BILLING_NUMBER unique (BILLING_NUMBER);

alter table testhiber.PRODUCT add constraint UNIQUE_PRODUCT_NAME unique (PRODUCT_NAME);

alter table testhiber.ORDER_ITEMS add constraint UNIQUE_ORDERS_ITEMS unique (ID, PRODUCT_ID);

alter table testhiber.ORDER_ITEMS add constraint ORDER_ITEMS_FOREIGN_KEY foreign key (ID) references testhiber.ORDER_TABLES (ID);

alter table testhiber.PRODUCT_PRICES add constraint PRODUCT_PRICES_FOREIGN_KEY foreign key (ID) references testhiber.PRODUCT (ID);

CREATE SEQUENCE customer_id_sequence
  MINVALUE 1
  START WITH 4
  INCREMENT BY 1
  CACHE 20;
  
CREATE SEQUENCE order_id_sequence
  MINVALUE 1
  START WITH 8
  INCREMENT BY 1
  CACHE 20;
  
CREATE SEQUENCE product_id_sequence
  MINVALUE 1
  START WITH 4
  INCREMENT BY 1
  CACHE 20;

CREATE SEQUENCE billing_number_generator
  MINVALUE 1
  START WITH 108
  INCREMENT BY 1
  CACHE 20;
  
insert into testhiber.CUSTOMER (ID, CUSTOMER_NAME) values (1, 'Vasya');

insert into testhiber.CUSTOMER (ID, CUSTOMER_NAME) values (2, 'Petya');

insert into testhiber.CUSTOMER (ID, CUSTOMER_NAME) values (3, 'Vanya');

insert into testhiber.PRODUCT (ID, PRODUCT_NAME, PRODUCT_UNITS) values (1, 'Potato', 'kg');

insert into testhiber.PRODUCT (ID, PRODUCT_NAME, PRODUCT_UNITS) values (2, 'Milk', 'L');

insert into testhiber.PRODUCT (ID, PRODUCT_NAME, PRODUCT_UNITS) values (3, 'Apples', 'kg');

insert into testhiber.PRODUCT (ID, PRODUCT_NAME, PRODUCT_UNITS) values (4, 'Cake', 'kg');

insert into testhiber.PRODUCT (ID, PRODUCT_NAME, PRODUCT_UNITS) values (5, 'Orange', 'kg');

insert into testhiber.PRODUCT (ID, PRODUCT_NAME, PRODUCT_UNITS) values (6, 'Meat', 'kg');

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (1, 10, TO_DATE('2013-12-31', 'yyyy/mm/dd'), 0);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (1, 15, TO_DATE('2014-12-31', 'yyyy/mm/dd'), 1);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (1, 20, TO_DATE('2015-12-31', 'yyyy/mm/dd'), 2);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (1, 25, TO_DATE('2016-12-31', 'yyyy/mm/dd'), 3);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (2, 30, TO_DATE('2015-12-31', 'yyyy/mm/dd'), 0);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (2, 25, TO_DATE('2016-12-31', 'yyyy/mm/dd'), 1);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (3, 7, TO_DATE('2016-12-31', 'yyyy/mm/dd'), 0);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (4, 2, TO_DATE('2014-12-31', 'yyyy/mm/dd'), 0);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (4, 3, TO_DATE('2015-12-31', 'yyyy/mm/dd'), 1);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (4, 4, TO_DATE('2016-12-31', 'yyyy/mm/dd'), 2);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (5, 2, TO_DATE('2014-12-31', 'yyyy/mm/dd'), 0);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (5, 3, TO_DATE('2015-12-31', 'yyyy/mm/dd'), 1);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (5, 5, TO_DATE('2016-12-31', 'yyyy/mm/dd'), 2);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (5, 4, TO_DATE('2017-12-31', 'yyyy/mm/dd'), 3);

insert into testhiber.PRODUCT_PRICES (ID, PRODUCT_PRICE, PRODUCT_PRICE_END_EFFECT_DAY, LIST_INDEX) values (6, 25, TO_DATE('2014-12-31', 'yyyy/mm/dd'), 0);

insert into testhiber.ORDER_TABLES (ID, BILLING_NUMBER, PLACING_DATE, CUSTOMER_ID) values (1, 101, TO_DATE('2016-12-12', 'yyyy/mm/dd'), 1);

insert into testhiber.ORDER_TABLES (ID, BILLING_NUMBER, PLACING_DATE, CUSTOMER_ID) values (2, 102, TO_DATE('2016-12-12', 'yyyy/mm/dd'), 1);

insert into testhiber.ORDER_TABLES (ID, BILLING_NUMBER, PLACING_DATE, CUSTOMER_ID) values (3, 103, TO_DATE('2016-12-13', 'yyyy/mm/dd'), 2);

insert into testhiber.ORDER_TABLES (ID, BILLING_NUMBER, PLACING_DATE, CUSTOMER_ID) values (4, 104, TO_DATE('2016-12-13', 'yyyy/mm/dd'), 2);

insert into testhiber.ORDER_TABLES (ID, BILLING_NUMBER, PLACING_DATE, CUSTOMER_ID) values (5, 105, TO_DATE('2016-12-14', 'yyyy/mm/dd'), 3);

insert into testhiber.ORDER_TABLES (ID, BILLING_NUMBER, PLACING_DATE, CUSTOMER_ID) values (6, 106, TO_DATE('2016-12-14', 'yyyy/mm/dd'), 3);

insert into testhiber.ORDER_TABLES (ID, BILLING_NUMBER, PLACING_DATE, CUSTOMER_ID) values (7, 107, TO_DATE('2016-12-14', 'yyyy/mm/dd'), 3);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (1, 2, 1, 0);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (1, 5, 2, 1);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (1, 1, 3, 2);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (2, 2, 2, 0);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (2, 5, 1, 1);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (3, 1, 3, 0);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (3, 12, 2, 1);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (3, 5, 1, 2);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (4, 1, 3, 0);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (5, 7, 2, 0);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (5, 7, 1, 1);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (6, 7, 3, 0);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (7, 1, 3, 0);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (7, 2, 1, 1);

insert into testhiber.ORDER_ITEMS (ID, QUANTITY, PRODUCT_ID, LIST_INDEX) values (7, 3, 2, 2);