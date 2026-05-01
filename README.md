# Java-Lab

A comprehensive collection of Java programming assignments covering fundamental object-oriented programming concepts, data structures, exception handling, and database integration.

## Overview

This repository contains 10 assignments that progressively cover various Java concepts and programming techniques. Each assignment is organized in its own directory and focuses on specific learning objectives.

## Assignment Breakdown

### Assignment 1: Basic Programming
**Files:** `Assignment1.java`, `calculator.java`, `color.java`

Introduction to basic Java programming including:
- Simple arithmetic operations with the calculator
- Color manipulation and representation
- Basic class structure and methods

---

### Assignment 2: Object-Oriented Programming Basics
**Files:** `MainVehicle.java`, `Vehicle.java`

Covers fundamental OOP concepts:
- Class definition and properties
- Object instantiation
- Method implementation
- Vehicle properties and behaviors

---

### Assignment 3: Collections and Exception Handling
**Files:** `ArraylistEx.java`, `Book.java`, `MainBook.java`, `InvalidGenreException.java`, `InvalidPriceException.java`

Focuses on:
- ArrayList usage and manipulation
- Custom exception handling
- Creating domain-specific exceptions
- Book inventory management system

---

### Assignment 4: Generic Data Structures
**Files:** `Vector.java`, `VectorOperations.java`, `validateSameDimension.java`, `VectorException.java`

Implements:
- Custom vector class
- Vector mathematical operations
- Exception handling for invalid operations
- Dimension validation

---

### Assignment 5: Banking System with Inheritance
**Files:** `Account.java`, `SavingsAccount.java`, `CurrentAccount.java`, `Customer.java`, `Loan.java`, `Main.java`, `InsufficientBalanceException.java`, `InvalidAccountException.java`

Demonstrates:
- Inheritance hierarchies (Account types)
- Polymorphism with different account behaviors
- Exception handling for banking operations
- Customer and Loan management

---

### Assignment 6: Employee Management System
**Files:** `Employee.java`, `FullTimeEmployee.java`, `ContractEmployee.java`, `Manager.java`, `Main.java`, `InvalidEmployeeException.java`

Covers:
- Employee hierarchy with inheritance
- Different employee types with specific behaviors
- Salary calculations based on employment type
- Manager role implementation
- Custom exception handling

---

### Assignment 7: Academic Ledger
**Files:** `AcademicLedger.java`

Features:
- Academic record management
- Student performance tracking
- Ledger maintenance

---

### Assignment 9: Database Integration with JDBC
**Files:** `RestaurantJDBC.java`

Introduces:
- JDBC database connectivity
- SQL operations (CRUD)
- Restaurant data management

---

### Assignment 10: Restaurant Management System (GUI + Database)
**Files:** `MainApp.java`, `RestaurantUI.java`, `MenuItemUI.java`, `RestaurantJDBC.java`

Complete restaurant management system featuring:
- User interface with menu item management
- Database operations with JDBC
- GUI components for data interaction
- Restaurant and menu item management

---

## Prerequisites

- **Java Development Kit (JDK)** 8 or higher
- **IDE** (Eclipse, IntelliJ IDEA, or VS Code with Java extensions recommended)
- **Database** (for Assignment 9 and 10 - typically MySQL or another relational database)

## Compilation and Execution

### Basic Compilation
```bash
javac FileName.java
java ClassName
```

### Example - Assignment 1
```bash
cd "Assignment 1"
javac calculator.java
java calculator
```

### Database Setup (for Assignments 9 & 10)
1. Ensure you have MySQL or another relational database running
2. Create the necessary database and tables as defined in the assignment
3. Update database connection details in the Java files
4. Compile and run the program

## Key Concepts Covered

✓ Basic Java syntax and operations  
✓ Object-Oriented Programming (Classes, Inheritance, Polymorphism)  
✓ Exception Handling (try-catch, custom exceptions)  
✓ Collections (ArrayList, Vector)  
✓ JDBC Database Integration  
✓ GUI Programming (Swing)  
✓ Design Patterns and Best Practices  

## Project Structure

```
Java-Lab/
├── Assignment 1/
├── Assignment 2/
├── Assignment 3/
├── Assignment 4/
├── Assignment 5/
├── Assignment 6/
├── Assignment 7/
├── Assignment 9/
├── Assignment 10/
└── README.md
```

## Notes

- Each assignment builds upon previous concepts
- Some assignments may have dependencies on earlier work
- Database assignments (9 & 10) require proper database setup
- Refer to individual assignment files for detailed requirements

## Author

These assignments are part of a Java learning curriculum designed to progressively build programming skills.

---

**Last Updated:** May 2026
