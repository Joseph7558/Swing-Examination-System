import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SwingExaminationSystem {

    // =================================================================
    // I. CORE OOP MODEL (The Model)
    // =================================================================

    // --- Core Model Classes ---
    static class User {
        protected int id;
        protected String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    // INHERITANCE: Student extends User
    static class Student extends User {
        private String regNumber;
        private String dob;
        private String password;

        public Student(int id, String name, String regNumber, String dob, String password) {
            super(id, name);
            this.regNumber = regNumber;
            this.dob = dob;
            this.password = password;
        }

        public String getRegNumber() {
            return regNumber;
        }

        public String getDob() {
            return dob;
        }

        public String getPassword() {
            return password;
        }
    }

    static class Question {
        private String questionText;
        private List<String> options;
        private char correctAnswer;

        public Question(String questionText, List<String> options, char correctAnswer) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswer = Character.toUpperCase(correctAnswer);
        }

        public String getQuestionText() {
            return questionText;
        }

        public List<String> getOptions() {
            return options;
        }

        public char getCorrectAnswer() {
            return correctAnswer;
        }

        public boolean isCorrect(char studentAnswer) {
            return Character.toUpperCase(studentAnswer) == correctAnswer;
        }
    }

    // AGGREGATION: Exam has a set of Questions
    static class Exam {
        private List<Question> questions;
        private final int marksPerQuestion = 1;

        public Exam(List<Question> questions) {
            this.questions = questions;
        }

        public List<Question> getQuestions() {
            return questions;
        }

        public int getTotalMarks() {
            return questions.size() * marksPerQuestion;
        }
    }

    // AGGREGATION: Result has a Student and an Exam
    static class Result {
        private String regNumber;
        private int scoreAchieved;

        public Result(String regNumber, int scoreAchieved) {
            this.regNumber = regNumber;
            this.scoreAchieved = scoreAchieved;
        }

        public String getRegNumber() {
            return regNumber;
        }

        public int getScoreAchieved() {
            return scoreAchieved;
        }
    }

    // --- DATABASE MANAGEMENT ---
    static class DatabaseManager {
        private static final String DB_URL = "jdbc:sqlite:examination.db";

        public static Connection connect() {
            try {
                return DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                System.err.println("Database connection error: " + e.getMessage());
                return null;
            }
        }

        public static void createTables() {
            String studentSql = "CREATE TABLE IF NOT EXISTS students ("
                    + " regNumber TEXT PRIMARY KEY,"
                    + " name TEXT NOT NULL,"
                    + " dob TEXT NOT NULL,"
                    + " password TEXT NOT NULL,"
                    + " hasTakenExam BOOLEAN DEFAULT FALSE"
                    + ");";

            String examSql = "CREATE TABLE IF NOT EXISTS exams ("
                    + " questionId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " questionText TEXT NOT NULL,"
                    + " options TEXT NOT NULL,"
                    + " correctAnswer CHAR(1) NOT NULL"
                    + ");";

            String resultSql = "CREATE TABLE IF NOT EXISTS results ("
                    + " resultId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " regNumber TEXT NOT NULL,"
                    + " score INTEGER NOT NULL,"
                    + " FOREIGN KEY(regNumber) REFERENCES students(regNumber)"
                    + ");";

            try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
                stmt.execute(studentSql);
                stmt.execute(examSql);
                stmt.execute(resultSql);
                System.out.println("Database tables created successfully.");
            } catch (SQLException e) {
                System.err.println("Error creating tables: " + e.getMessage());
            }
        }

        public static void insertInitialQuestions() {
            try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM exams");
                if (rs.getInt(1) > 0) return; // Questions already exist

                List<Question> questions = new ArrayList<>(Arrays.asList(
                        new Question("What is the main principle of encapsulation?", Arrays.asList("A. Hiding implementation details", "B. Creating multiple objects", "C. Allowing all data to be public"), 'A'),
                        new Question("In Java, what is a class a blueprint for?", Arrays.asList("A. An interface", "B. An object", "C. A package"), 'B'),
                        new Question("Which OOP principle allows an object to take on many forms?", Arrays.asList("A. Inheritance", "B. Abstraction", "C. Polymorphism"), 'C'),
                        new Question("What is a superclass?", Arrays.asList("A. A subclass", "B. A parent class", "C. A final class"), 'B'),
                        new Question("Which of these is a correct method declaration in Java?", Arrays.asList("A. method myMethod()", "B. public static void myMethod()", "C. myMethod void()"), 'B'),
                        new Question("Which Swing component displays a single line of text?", Arrays.asList("A. JTextArea", "B. JList", "C. JTextField"), 'C'),
                        new Question("What layout manager arranges components in a grid?", Arrays.asList("A. FlowLayout", "B. BorderLayout", "C. GridLayout"), 'C'),
                        new Question("What listener handles button clicks in Swing?", Arrays.asList("A. MouseListener", "B. ActionListener", "C. KeyListener"), 'B'),
                        new Question("What is the top-level container for a Swing application?", Arrays.asList("A. JPanel", "B. JFrame", "C. JComponent"), 'B'),
                        new Question("Which keyword is used for inheritance in Java?", Arrays.asList("A. implement", "B. extends", "C. inherits"), 'B'),
                        new Question("What is the default layout manager for a JFrame?", Arrays.asList("A. FlowLayout", "B. BorderLayout", "C. GridLayout"), 'B'),
                        new Question("What does 'GUI' stand for?", Arrays.asList("A. General User Interface", "B. Graphical User Interface", "C. Global User Interface"), 'B'),
                        new Question("Which of these is NOT a primitive data type in Java?", Arrays.asList("A. int", "B. String", "C. boolean"), 'B'),
                        new Question("What is the main advantage of using an interface?", Arrays.asList("A. It allows multiple inheritance", "B. It makes a class abstract", "C. It defines a contract for classes"), 'C'),
                        new Question("What does a constructor do?", Arrays.asList("A. Creates a class", "B. Initializes an object", "C. Deletes an object"), 'B'),
                        new Question("Which method is the entry point of a Java application?", Arrays.asList("A. start()", "B. main()", "C. run()"), 'B'),
                        new Question("What is the primary role of a 'get' method (getter)?", Arrays.asList("A. To set a variable's value", "B. To retrieve a variable's value", "C. To check a variable's type"), 'B'),
                        new Question("Which one is a checked exception in Java?", Arrays.asList("A. NullPointerException", "B. IOException", "C. IllegalArgumentException"), 'B'),
                        new Question("In Swing, which component can be added to a JScrollPane?", Arrays.asList("A. JTable", "B. JButton", "C. JComboBox"), 'A'),
                        new Question("What is a method that has the same name as its class?", Arrays.asList("A. A constructor", "B. An accessor", "C. A mutator"), 'A')
                ));

                String sql = "INSERT INTO exams(questionText, options, correctAnswer) VALUES(?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    for (Question q : questions) {
                        pstmt.setString(1, q.getQuestionText());
                        pstmt.setString(2, String.join(";", q.getOptions()));
                        pstmt.setString(3, String.valueOf(q.getCorrectAnswer()));
                        pstmt.executeUpdate();
                    }
                }
                System.out.println("Initial exam questions inserted.");
            } catch (SQLException e) {
                System.err.println("Error inserting initial questions: " + e.getMessage());
            }
        }

        public static void saveStudent(String regNumber, String name, String dob, String password) {
            String sql = "INSERT INTO students(regNumber, name, dob, password) VALUES(?, ?, ?, ?)";
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, regNumber);
                pstmt.setString(2, name);
                pstmt.setString(3, dob);
                pstmt.setString(4, password);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error saving student: " + e.getMessage());
            }
        }
        
        // This method checks both reg number and password for exam login
        public static boolean checkLoginCredentials(String regNumber, String password) {
            String sql = "SELECT * FROM students WHERE regNumber = ? AND password = ?";
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, regNumber);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                System.err.println("Error checking login credentials: " + e.getMessage());
            }
            return false;
        }

        public static Student getStudentByRegNumber(String regNumber) {
            String sql = "SELECT regNumber, name, dob, password FROM students WHERE regNumber = ?";
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, regNumber);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return new Student(0, rs.getString("name"), rs.getString("regNumber"), rs.getString("dob"), rs.getString("password"));
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving student: " + e.getMessage());
            }
            return null;
        }

        public static boolean hasTakenExam(String regNumber) {
            String sql = "SELECT hasTakenExam FROM students WHERE regNumber = ?";
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, regNumber);
                ResultSet rs = pstmt.executeQuery();
                return rs.next() && rs.getBoolean("hasTakenExam");
            } catch (SQLException e) {
                System.err.println("Error checking exam status: " + e.getMessage());
            }
            return false;
        }

        public static void setExamTaken(String regNumber) {
            String sql = "UPDATE students SET hasTakenExam = TRUE WHERE regNumber = ?";
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, regNumber);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error updating exam status: " + e.getMessage());
            }
        }

        public static List<Question> getAllQuestions() {
            List<Question> questions = new ArrayList<>();
            String sql = "SELECT questionText, options, correctAnswer FROM exams";
            try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    List<String> options = Arrays.asList(rs.getString("options").split(";"));
                    questions.add(new Question(rs.getString("questionText"), options, rs.getString("correctAnswer").charAt(0)));
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving questions: " + e.getMessage());
            }
            return questions;
        }

        public static void saveResult(String regNumber, int score) {
            String sql = "INSERT INTO results(regNumber, score) VALUES(?, ?)";
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, regNumber);
                pstmt.setInt(2, score);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error saving result: " + e.getMessage());
            }
        }

        public static Integer getResult(String regNumber) {
            String sql = "SELECT score FROM results WHERE regNumber = ?";
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, regNumber);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("score");
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving result: " + e.getMessage());
            }
            return null;
        }
    }


    // =================================================================
    // II. GUI IMPLEMENTATION (Java Swing)
    // =================================================================
    static class MainFrame extends JFrame implements ActionListener {
        public MainFrame() {
            setTitle("Online Examination System");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(3, 1, 10, 10));

            JButton registerButton = new JButton("Student Register");
            JButton examButton = new JButton("Exam");
            JButton resultButton = new JButton("Check Result");

            registerButton.addActionListener(this);
            examButton.addActionListener(this);
            resultButton.addActionListener(this);

            add(registerButton);
            add(examButton);
            add(resultButton);

            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Student Register")) {
                new RegisterFrame();
            } else if (e.getActionCommand().equals("Exam")) {
                new ExamLoginFrame();
            } else if (e.getActionCommand().equals("Check Result")) {
                new ResultCheckFrame();
            }
        }
    }

    static class RegisterFrame extends JFrame implements ActionListener {
        private JTextField nameField, dobField;
        private JPasswordField passwordField;

        public RegisterFrame() {
            setTitle("Student Registration");
            setSize(350, 200);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(4, 2, 10, 10));

            add(new JLabel("Name:"));
            nameField = new JTextField();
            add(nameField);

            add(new JLabel("Date of Birth (YYYY-MM-DD):"));
            dobField = new JTextField();
            add(dobField);

            add(new JLabel("Password:"));
            passwordField = new JPasswordField();
            add(passwordField);

            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(this);
            add(new JLabel(""));
            add(submitButton);

            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            String dob = dobField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || dob.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String regNumber = generateRegistrationNumber();
            DatabaseManager.saveStudent(regNumber, name, dob, password);
            JOptionPane.showMessageDialog(this, "Registration Successful!\nYour Registration Number is: " + regNumber, "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }

        private String generateRegistrationNumber() {
            Random random = new Random();
            int number = 100000 + random.nextInt(900000);
            return String.valueOf(number);
        }
    }

    static class ExamLoginFrame extends JFrame implements ActionListener {
        private JTextField regNumberField;
        private JPasswordField passwordField;

        public ExamLoginFrame() {
            setTitle("Exam Login");
            setSize(350, 150);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(3, 2, 10, 10));

            add(new JLabel("Reg. Number:"));
            regNumberField = new JTextField();
            add(regNumberField);

            add(new JLabel("Password:"));
            passwordField = new JPasswordField();
            add(passwordField);

            JButton loginButton = new JButton("Start Exam");
            loginButton.addActionListener(this);
            add(new JLabel(""));
            add(loginButton);

            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String regNumber = regNumberField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            
            if (!DatabaseManager.checkLoginCredentials(regNumber, password)) {
                JOptionPane.showMessageDialog(this, "Invalid Registration Number or Password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (DatabaseManager.hasTakenExam(regNumber)) {
                JOptionPane.showMessageDialog(this, "You have already completed the exam. Please check your result.", "Exam Completed", JOptionPane.WARNING_MESSAGE);
                this.dispose();
                return;
            }

            List<Question> questions = DatabaseManager.getAllQuestions();
            if (questions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No exam found. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Student student = DatabaseManager.getStudentByRegNumber(regNumber);

            Exam exam = new Exam(questions);
            new ExamFrame(regNumber, student.getName(), exam);
            this.dispose();
        }
    }


    static class ExamFrame extends JFrame implements ActionListener {
        private String studentRegNumber;
        private String studentName;
        private Exam exam;
        private int currentQuestionIndex = 0;
        private Map<Integer, Character> studentAnswers = new HashMap<>();

        private final javax.swing.Timer timer;
        private int timeRemainingSeconds = 600; // 10 minutes
        private final JLabel timerLabel;

        private JTextArea questionArea;
        private JRadioButton[] optionButtons = new JRadioButton[4];
        private ButtonGroup optionGroup = new ButtonGroup();
        private JButton nextButton;

        public ExamFrame(String regNumber, String name, Exam exam) {
            this.studentRegNumber = regNumber;
            this.studentName = name;
            this.exam = exam;

            setTitle("OOP Exam - " + studentName);
            setSize(750, 550);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout(10, 10));

            timerLabel = new JLabel("Time Left: 10:00", SwingConstants.CENTER);
            timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
            timerLabel.setForeground(Color.RED);

            timer = new javax.swing.Timer(1000, new TimerActionListener());
            timer.start();

            JPanel headerPanel = new JPanel(new BorderLayout());

            JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            timerPanel.add(timerLabel);
            headerPanel.add(timerPanel, BorderLayout.NORTH);

            questionArea = new JTextArea();
            questionArea.setFont(new Font("Arial", Font.BOLD, 14));
            questionArea.setEditable(false);
            questionArea.setLineWrap(true);
            questionArea.setWrapStyleWord(true);

            JPanel questionPanel = new JPanel(new BorderLayout());
            questionPanel.setBorder(BorderFactory.createTitledBorder("Question"));
            questionPanel.add(new JScrollPane(questionArea), BorderLayout.CENTER);

            headerPanel.add(questionPanel, BorderLayout.CENTER);
            add(headerPanel, BorderLayout.NORTH);

            JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
            optionsPanel.setBorder(BorderFactory.createTitledBorder("Select Answer"));
            for (int i = 0; i < 4; i++) {
                optionButtons[i] = new JRadioButton();
                optionGroup.add(optionButtons[i]);
                optionsPanel.add(optionButtons[i]);
            }
            add(optionsPanel, BorderLayout.CENTER);

            nextButton = new JButton("Next Question >>");
            nextButton.addActionListener(this);
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(nextButton);
            add(buttonPanel, BorderLayout.SOUTH);

            displayQuestion();
            setVisible(true);
        }

        private class TimerActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemainingSeconds--;

                int minutes = timeRemainingSeconds / 60;
                int seconds = timeRemainingSeconds % 60;

                timerLabel.setText(String.format("Time Left: %02d:%02d", minutes, seconds));

                if (timeRemainingSeconds <= 60) {
                    timerLabel.setForeground(Color.RED.darker());
                }

                if (timeRemainingSeconds <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(ExamFrame.this, "Time's up! The exam will now submit.", "Time Over", JOptionPane.WARNING_MESSAGE);
                    finishExam();
                }
            }
        }

        private void displayQuestion() {
            if (currentQuestionIndex >= exam.getQuestions().size()) {
                finishExam();
                return;
            }

            Question q = exam.getQuestions().get(currentQuestionIndex);

            questionArea.setText((currentQuestionIndex + 1) + "/" + exam.getQuestions().size() + ": " + q.getQuestionText());
            optionGroup.clearSelection();

            char label = 'A';
            for (int i = 0; i < 4; i++) {
                if (i < q.getOptions().size()) {
                    optionButtons[i].setText(q.getOptions().get(i));
                    optionButtons[i].setActionCommand(String.valueOf(label));
                    optionButtons[i].setVisible(true);
                    label++;
                } else {
                    optionButtons[i].setVisible(false);
                }
            }
            if (currentQuestionIndex == exam.getQuestions().size() - 1) {
                nextButton.setText("Submit Exam");
            } else {
                nextButton.setText("Next Question >>");
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ButtonModel selectedModel = optionGroup.getSelection();

            if (selectedModel != null) {
                char answer = selectedModel.getActionCommand().charAt(0);
                studentAnswers.put(currentQuestionIndex, answer);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an answer.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            currentQuestionIndex++;
            displayQuestion();
        }

        private void finishExam() {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }

            int correctCount = 0;
            for (int i = 0; i < exam.getQuestions().size(); i++) {
                Question q = exam.getQuestions().get(i);
                Character studentAns = studentAnswers.get(i);

                if (studentAns != null && q.isCorrect(studentAns)) {
                    correctCount++;
                }
            }

            DatabaseManager.setExamTaken(studentRegNumber);
            DatabaseManager.saveResult(studentRegNumber, correctCount);

            new ResultFrame(studentRegNumber, correctCount);
            this.dispose();
        }
    }

    static class ResultCheckFrame extends JFrame implements ActionListener {
        private JTextField regNumberField;
        private JPasswordField passwordField;

        public ResultCheckFrame() {
            setTitle("Check Result");
            setSize(350, 150);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(3, 2, 10, 10));

            add(new JLabel("Reg. Number:"));
            regNumberField = new JTextField();
            add(regNumberField);

            add(new JLabel("Password:"));
            passwordField = new JPasswordField();
            add(passwordField);

            JButton checkButton = new JButton("Check Result");
            checkButton.addActionListener(this);
            add(new JLabel(""));
            add(checkButton);

            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String regNumber = regNumberField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (!DatabaseManager.checkLoginCredentials(regNumber, password)) {
                JOptionPane.showMessageDialog(this, "Invalid Registration Number or password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Integer score = DatabaseManager.getResult(regNumber);

            if (score != null) {
                new ResultFrame(regNumber, score);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "You have not attended the exam yet.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    static class ResultFrame extends JFrame {
        public ResultFrame(String regNumber, int score) {
            setTitle("Exam Results");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            String status = (score >= 8) ? "PASS" : "FAIL";
            String grade, note;

            if (score >= 19) { grade = "A+"; note = "Excellent!"; }
            else if (score >= 17) { grade = "A"; note = "Very good!"; }
            else if (score >= 15) { grade = "B+"; note = "Good!"; }
            else if (score >= 13) { grade = "B"; note = "Satisfactory."; }
            else if (score >= 11) { grade = "C+"; note = "Needs improvement."; }
            else if (score >= 9) { grade = "C"; note = "Just passed."; }
            else if (score == 8) { grade = "D+"; note = "Marginal pass."; }
            else { grade = "D"; note = "Better luck next time."; }

            JTextArea resultArea = new JTextArea();
            resultArea.setEditable(false);
            resultArea.setFont(new Font("Arial", Font.PLAIN, 16));

            resultArea.append("\n\n--- Exam Results for Reg. No: " + regNumber + " ---\n");
            resultArea.append("Score Achieved: " + score + " out of 20\n");
            resultArea.append("Grade: " + grade + "\n");
            resultArea.append("Status: " + status + "\n");
            resultArea.append("Note: " + note + "\n");
            resultArea.append("---------------------------------\n");

            add(new JScrollPane(resultArea), BorderLayout.CENTER);

            setVisible(true);
        }
    }


    // =================================================================
    // III. MAIN EXECUTION
    // =================================================================
    public static void main(String[] args) {
        DatabaseManager.createTables();
        DatabaseManager.insertInitialQuestions();
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}