# Swing Examination System

A desktop-based examination management system developed using **Java Swing, JDBC, and SQLite**. The application provides a graphical interface for student registration, authentication, timed multiple-choice examinations, automatic evaluation, and result management.

## 📌 Project Overview

The **Swing Examination System** is a Java desktop application designed to simulate a computer-based examination environment.

Students can register using their personal and academic details, log in using their credentials, attend a timed multiple-choice examination, and view their results after submission.

The project demonstrates important **Object-Oriented Programming (OOP)** concepts including:

- Encapsulation
- Inheritance
- Abstraction
- Polymorphism
- Aggregation

The application uses **SQLite** for local data storage and **JDBC** for database connectivity.

## ✨ Features

- Student registration
- Student login and authentication
- Examination login
- SQLite database integration
- Multiple-choice examination
- Timed examination
- Automatic answer evaluation
- Automatic score calculation
- Grade calculation
- Pass/Fail status
- Result display
- Student result checking
- Question and answer management
- Java Swing graphical user interface
- JDBC-based database connectivity
- Prepared statements for database operations
- Local database storage
- Examination completion tracking

## 🛠️ Technologies Used

| Technology | Purpose |
|------------|---------|
| Java | Core application development |
| Java Swing | Graphical User Interface |
| SQLite | Local database management |
| JDBC | Java-database connectivity |
| SQLite JDBC Driver | SQLite integration |
| OOP | Application architecture |

## 🏗️ Application Architecture
```
The application follows a simple object-oriented desktop application architecture.


+--------------------------------------------------+
|              Java Swing GUI Layer                |
|                                                  |
| Main Frame                                       |
| Registration                                     |
| Login                                            |
| Exam Login                                       |
| Examination                                     |
| Result                                           |
| Result Checking                                  |
+-------------------------+------------------------+
                          |
                          | JDBC
                          ▼
+--------------------------------------------------+
|             Database Management Layer            |
|                                                  |
| DatabaseManager                                  |
|                                                  |
| - Database Connection                            |
| - Table Creation                                 |
| - Student Registration                           |
| - Student Authentication                         |
| - Question Management                            |
| - Exam Status Management                         |
| - Result Storage                                 |
| - Result Retrieval                               |
+-------------------------+------------------------+
                          |
                          ▼
+--------------------------------------------------+
|                SQLite Database                   |
|                                                  |
| Students                                         |
| Exams                                            |
| Results                                          |
+--------------------------------------------------+
```

# 🖥️ GUI Components
```text
The application provides different Java Swing interfaces for different operations.

Main Interface
Provides access to the main functions of the examination system.

Student Registration
Allows students to enter their personal and academic information and register for the examination.

Student Login
Authenticates registered students using their credentials.

Exam Login
Provides authentication before starting the examination.

Examination Interface
Provides the actual examination environment, including:

Question display
Multiple-choice options
Answer selection
Timer
Next question navigation
Examination submission
Result Interface
Displays the student's examination performance after evaluation.
Result Checking
Allows students to check their examination results.
```
# 🗄️ Database
```text
The application uses SQLite as a local relational database.
Database connectivity is implemented using Java Database Connectivity (JDBC).
The application uses:examination.db

The database stores application information such as:
Student details
Examination questions
Answer options
Correct answers
Examination status
Examination results
Students Table

Stores student information such as:
Registration number
Name
Date of birth
Password
Examination status
Exams Table

Stores examination information such as:
Question ID
Question text
Options
Correct answer
Results Table

Stores examination results such as:
Result ID
Student registration number
Score
```
## 📚 Examination Details
```text
The examination system currently contains:
20 multiple-choice questions
1 mark per question
Maximum score: 20
10-minute examination duration
Automatic evaluation
Automatic grade calculation
Pass/Fail status
The questions are based mainly on Java and Object-Oriented Programming concepts.
```
## 📊 Grading System
```text
| Score | Grade |
| ----: | :---: |
| 19–20 |   A+  |
| 17–18 |   A   |
| 15–16 |   B+  |
| 13–14 |   B   |
| 11–12 |   C+  |
|  9–10 |   C   |
|     8 |   D+  |
|   0–7 |   D   |

Passing Score
8 / 20
A score of 8 or above is considered a pass.
```
## 🔄 Application Workflow
```text
                  START
                   │
                   ▼
          Initialize Database
                   │
                   ▼
          Create Database Tables
                   │
                   ▼
       Initialize Examination Data
                   │
                   ▼
               Main Menu
              /         \
             /           \
            ▼             ▼
       Registration      Login
            │              │
            │              ▼
            │       Authenticate Student
            │              │
            └───────┬──────┘
                    │
                    ▼
               Exam Login
                    │
                    ▼
          Start Examination
                    │
                    ▼
            Load Questions
                    │
                    ▼
           Start 10-Min Timer
                    │
                    ▼
           Answer Questions
                    │
                    ▼
          Submit Examination
                    │
             ┌──────┴──────┐
             │             │
             ▼             ▼
       Manual Submit   Time Expires
             │             │
             └──────┬──────┘
                    │
                    ▼
           Evaluate Answers
                    │
                    ▼
             Calculate Score
                    │
                    ▼
             Calculate Grade
                    │
                    ▼
             Determine Result
                    │
                    ▼
            Save Examination
                 Result
                    │
                    ▼
             Display Result
                    │
                    ▼
                   END
```
## 💾 Data Flow
```text
Student
   │
   ▼
Registration
   │
   ▼
Student Information
   │
   ▼
SQLite Database
   │
   ▼
Login Authentication
   │
   ▼
Examination
   │
   ▼
Student Answers
   │
   ▼
Automatic Evaluation
   │
   ▼
Score & Grade
   │
   ▼
SQLite Database
   │
   ▼
Result Display
```
## ⏱️ Examination Timer
```text
The examination uses a Java Swing timer.
The examination duration is:
10 minutes
The timer counts down continuously during the examination
When the timer reaches zero, the examination is automatically submitted and evaluated.
```
## 📂 Project Structure
```text
Swing-Examination-System/
│
├── src/
│   └── SwingExaminationSystem.java
│
├── lib/
│   └── sqlite-jdbc-3.50.3.0.jar
│
├── .gitattributes
├── .gitignore
└── README.md
Local Database

The application uses:
examination.db
The database file is excluded from Git using .gitignore.
Compiled Files
Java compiled .class files are generated inside the bin directory when the project is compiled.
The bin directory is excluded from Git because compiled files do not need to be stored in the source repository.
```
## ⚙️ Requirements
```text
Before running the project, install:
Java JDK 8 or later
A Java IDE such as:
IntelliJ IDEA
Eclipse
NetBeans
Visual Studio Code
The project includes the SQLite JDBC driver:
lib/sqlite-jdbc-3.50.3.0.jar
```
## ▶️ How to Run

# 1. Clone the Repository
git clone https://github.com/YOUR-USERNAME/Swing-Examination-System.git
# 2. Navigate to the Project
cd Swing-Examination-System
# 3. Open the Project
Open the project folder in your preferred Java IDE.
# 4. Add SQLite JDBC Driver
Add the following JAR file to the project's classpath:
lib/sqlite-jdbc-3.50.3.0.jar
# 5. Compile
From the project root directory:
javac -cp "lib/sqlite-jdbc-3.50.3.0.jar" -d bin src/SwingExaminationSystem.java
# 6. Run on Windows
java -cp "bin;lib/sqlite-jdbc-3.50.3.0.jar" SwingExaminationSystem
# 7. Run on Linux/macOS
java -cp "bin:lib/sqlite-jdbc-3.50.3.0.jar" SwingExaminationSystem

## 🧠 Concepts Demonstrated
```text
Java Programming
Classes and objects
Constructors
Methods
Collections
Exception handling
Event-driven programming
GUI programming
Object-Oriented Programming
Encapsulation
Inheritance
Abstraction
Polymorphism
Aggregation
Java Swing
JFrame
JPanel
JButton
JLabel
JTextField
JPasswordField
JTextArea
JRadioButton
ButtonGroup
JOptionPane
JScrollPane
Layout managers
Event listeners
Swing Timer
Database
SQLite
SQL
JDBC
Prepared statements
CRUD operations
Database connectivity
Relational database concepts
```
## 🎓 Academic Purpose
```text
This project was developed as an academic project to demonstrate practical knowledge of:
Java Programming
Object-Oriented Programming
Java Swing GUI Development
Event-Driven Programming
JDBC
SQLite Database Management
SQL
Database Connectivity
Exception Handling
Desktop Application Development
```
## 📈 Learning Outcomes
```text
Through this project, the following practical skills are demonstrated:
Developing Java desktop applications
Designing graphical user interfaces
Applying Object-Oriented Programming principles
Implementing event-driven programming
Connecting Java applications with databases
Performing database operations using JDBC
Designing an examination workflow
Implementing automatic evaluation
Managing examination results
Using Git and GitHub for version control
```
## 👨‍💻 Author

**Joseph J.**  
🎓 BTech Computer Science and Engineering  
🏫 Vimal Jyothi Engineering College

