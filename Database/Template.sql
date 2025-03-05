CREATE DATABASE product_db;

USE product_db;

CREATE TABLE products (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    price FLOAT,
    quantity INT,
    category VARCHAR(100)
);