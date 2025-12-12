#News Feed Aggregator (Core Java + MySQL)

A console-based News Feed Aggregator application built using Core Java and MySQL.
Users can add news articles and view them in descending date order.
This project demonstrates Java–MySQL integration using JDBC.


---

# Project Overview

The News Feed Aggregator allows users to:

Add news articles with title, category, date, and content

Store articles in a MySQL database

Prevent duplicate titles using INSERT IGNORE

Retrieve all articles using ORDER BY date DESC

Auto-generate article IDs with AUTO_INCREMENT



---

# Features

✔ Add News Articles

User enters:

Title

Category

Date (dd-MM-yyyy)

Content


✔ Store Articles in MySQL

Data is saved using:

INSERT IGNORE INTO articles (...);

✔ Display All Articles

Retrieves and displays news articles in reverse chronological order:

SELECT * FROM articles ORDER BY date DESC;

✔ Auto-generated Primary Key

MySQL automatically generates unique IDs for each article.


---

# Technologies Used

Core Java

JDBC

MySQL Workbench

SQL



---

# Database Setup

Run the following commands in MySQL Workbench:

CREATE DATABASE newsdb;

USE newsdb;

CREATE TABLE articles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) UNIQUE,
    category VARCHAR(100),
    content TEXT,
    date DATE
);


---

# Concepts Demonstrated

Concept	Purpose

JDBC	Database connectivity
PreparedStatement	Secure insertion & prevents SQL injection
ResultSet	Fetch and display data
SimpleDateFormat	Convert user date to SQL date
Auto Increment	Generate unique IDs automatically
SQL ORDER BY	Sort articles (latest first)



---

# How to Run

1. Install

Java JDK

MySQL Server & Workbench

MySQL Connector/J (JDBC driver)


2. Add JDBC Driver

Place mysql-connector-j-8.x.x.jar in your project classpath.

3. Update Credentials in Java Code

String url = "jdbc:mysql://localhost:3306/newsdb";
String user = "root";
String password = "your_password";

4. Compile & Run

javac NewsAggregator.java
java NewsAggregator


---

# Sample Output

Enter Title: Technology Update
Enter Category: Tech
Enter Date (dd-MM-yyyy): 10-12-2025
Enter Content: New AI model released.

Article added successfully!

----- All Articles -----
[1] Technology Update | Tech | 2025-12-10
New AI model released.
-------------------------


---

# Resume Description

Developed a News Feed Aggregator using Core Java and MySQL. Implemented user input handling, date formatting, and secure data insertion with JDBC PreparedStatement. Designed SQL tables in MySQL Workbench and applied sorting logic to display articles by date in descending order. Demonstrated integration of Java applications with relational databases.


---

# License

This project is for educational and learning purposes.


---
