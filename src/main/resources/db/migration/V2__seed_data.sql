-- Insert sample courses
INSERT INTO courses (title, description, duration, level) VALUES
('Introduction to Java Programming', 'Learn the fundamentals of Java programming language including variables, control structures, and object-oriented programming concepts.', 40, 'beginner'),
('Advanced Spring Framework', 'Deep dive into Spring Framework features including Spring Boot, Spring Security, and Spring Data JPA.', 60, 'advanced'),
('Database Design and SQL', 'Master database design principles and SQL query optimization techniques.', 30, 'intermediate'),
('Web Development with React', 'Build modern web applications using React.js, including hooks, state management, and component lifecycle.', 45, 'intermediate'),
('Machine Learning Fundamentals', 'Introduction to machine learning algorithms, data preprocessing, and model evaluation.', 50, 'beginner');

-- Insert sample lessons for Java Programming course (course_id = 1)
INSERT INTO lessons (course_id, title, content, order_index) VALUES
(1, 'Java Basics and Syntax', 'Introduction to Java syntax, variables, data types, and basic programming constructs.', 1),
(1, 'Object-Oriented Programming', 'Understanding classes, objects, inheritance, polymorphism, and encapsulation in Java.', 2),
(1, 'Exception Handling', 'Learning how to handle exceptions and errors in Java applications.', 3),
(1, 'Collections Framework', 'Working with Java collections including Lists, Sets, Maps, and their implementations.', 4);

-- Insert sample lessons for Spring Framework course (course_id = 2)
INSERT INTO lessons (course_id, title, content, order_index) VALUES
(2, 'Spring Boot Introduction', 'Getting started with Spring Boot and understanding auto-configuration.', 1),
(2, 'Spring Security', 'Implementing authentication and authorization in Spring applications.', 2),
(2, 'Spring Data JPA', 'Database operations using Spring Data JPA and repository pattern.', 3);

-- Insert sample lessons for Database Design course (course_id = 3)
INSERT INTO lessons (course_id, title, content, order_index) VALUES
(3, 'Database Fundamentals', 'Understanding relational databases, tables, and relationships.', 1),
(3, 'SQL Queries', 'Writing effective SQL queries for data retrieval and manipulation.', 2),
(3, 'Normalization', 'Database normalization techniques and best practices.', 3);

-- Insert sample quizzes
INSERT INTO quizzes (course_id, title, description) VALUES
(1, 'Java Basics Quiz', 'Test your understanding of Java fundamentals and syntax.'),
(1, 'OOP Concepts Quiz', 'Quiz on object-oriented programming concepts in Java.'),
(2, 'Spring Boot Quiz', 'Test your knowledge of Spring Boot features and configuration.'),
(3, 'SQL Fundamentals Quiz', 'Quiz on basic SQL operations and database concepts.');

-- Insert sample questions for Java Basics Quiz (quiz_id = 1)
INSERT INTO questions (quiz_id, question_text, options, correct_answer) VALUES
(1, 'Which of the following is the correct way to declare a variable in Java?', 
 ARRAY['int x = 5;', 'var x = 5;', 'x = 5;', 'integer x = 5;'], 'int x = 5;'),
(1, 'What is the main method signature in Java?', 
 ARRAY['public static void main(String[] args)', 'public void main(String[] args)', 'static void main(String[] args)', 'public main(String[] args)'], 'public static void main(String[] args)'),
(1, 'Which keyword is used to create a new object in Java?', 
 ARRAY['new', 'create', 'make', 'object'], 'new');

-- Insert sample questions for OOP Quiz (quiz_id = 2)
INSERT INTO questions (quiz_id, question_text, options, correct_answer) VALUES
(2, 'What is encapsulation in Java?', 
 ARRAY['Hiding implementation details', 'Creating multiple classes', 'Extending classes', 'Implementing interfaces'], 'Hiding implementation details'),
(2, 'Which keyword is used for inheritance in Java?', 
 ARRAY['extends', 'implements', 'inherits', 'super'], 'extends');

-- Insert sample questions for Spring Boot Quiz (quiz_id = 3)
INSERT INTO questions (quiz_id, question_text, options, correct_answer) VALUES
(3, 'What annotation is used to mark a Spring Boot main class?', 
 ARRAY['@SpringBootApplication', '@EnableAutoConfiguration', '@Configuration', '@Component'], '@SpringBootApplication'),
(3, 'Which file is used for Spring Boot configuration?', 
 ARRAY['application.properties', 'config.xml', 'spring.properties', 'boot.config'], 'application.properties');

-- Insert sample questions for SQL Quiz (quiz_id = 4)
INSERT INTO questions (quiz_id, question_text, options, correct_answer) VALUES
(4, 'Which SQL command is used to retrieve data from a database?', 
 ARRAY['SELECT', 'GET', 'RETRIEVE', 'FETCH'], 'SELECT'),
(4, 'What does the WHERE clause do in SQL?', 
 ARRAY['Filters rows based on conditions', 'Orders the result set', 'Groups rows', 'Joins tables'], 'Filters rows based on conditions');
