DROP DATABASE IF EXISTS mycollege_enrollment;

CREATE DATABASE IF NOT EXISTS mycollege_enrollment;

/*
 * ^ Rebuild database
 */

USE mycollege_enrollment;       -- Use database to execute query to

CREATE TABLE courses (
  courseID INT UNSIGNED AUTO_INCREMENT,
  title VARCHAR(50) NOT NULL,   -- e.g. Bchelor of Cmputer Science
  code CHAR(4) NOT NULL,        -- e.g. BSCS

  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                              -- Recorded automatically
  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- Autmatically updates every time there 
                                                                            --    is change in the record

  PRIMARY KEY (courseID)
);

CREATE TABLE majors (                       -- majorID 1 is blank for 'no major'
  majorID INT UNSIGNED AUTO_INCREMENT,
  title VARCHAR(50),

  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (majorID)
);

CREATE TABLE courses_majors (               -- Course-major pairings
  courseMajID INT UNSIGNED AUTO_INCREMENT, 
  courseID INT UNSIGNED NOT NULL,           -- e.g. 3 - Bachelor of Science in Business Administration
  majorID INT UNSIGNED NOT NULL,            -- e.g. 3 - Major in Business Management

  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (courseMajID),
  FOREIGN KEY (courseID) REFERENCES courses (courseID)    -- FOREIGN KEY: Relation to another table
    ON UPDATE CASCADE,                                    -- If reference in parent table is updated, update here also.
  FOREIGN KEY (majorID) REFERENCES majors (majorID)
    ON UPDATE CASCADE
);

CREATE TABLE instructors ( 
  instructorID INT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  gender ENUM('MALE', 'FEMALE') NOT NULL,
  birthdate DATE NOT NULL,                   -- Format: YEAR-MONTH-DAY (YYYY-MM-DD)
  address VARCHAR(100) NOT NULL,
  status ENUM('AVAILABLE', 'UNAVAILABLE') NOT NULL DEFAULT 'AVAILABLE', -- ENUM: Choose only from available options

  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (instructorID)
);

CREATE TABLE subjects (
  subID INT UNSIGNED AUTO_INCREMENT,

  code VARCHAR(10) NOT NULL,    -- e.g. CS 108
  title VARCHAR(100) NOT NULL,  
  units INT(1) NOT NULL,        -- only one (1) digit

  semester ENUM('1ST', '2ND', 'SUMMER') NOT NULL,
  yearLevel ENUM('1', '2', '3', '4') NOT NULL,

  courseMajID INT UNSIGNED NOT NULL,

  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (subID),
  FOREIGN KEY (courseMajID) REFERENCES courses_majors (courseMajID)
    ON UPDATE CASCADE
);

CREATE TABLE users (
  uid INT UNSIGNED AUTO_INCREMENT,
  email VARCHAR(100) NOT NULL,
  code BINARY(16) NOT NULL,         -- Raw bytes, encrypted password
  salt BINARY(16) NOT NULL,         -- Random bytes used to encrypt password

  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (uid)
);

CREATE TABLE students (
  studID INT UNSIGNED AUTO_INCREMENT,
  uid INT UNSIGNED,

  -- Account details

  schoolYear CHAR(14) NOT NULL,
  semester ENUM('1ST', '2ND', 'SUMMER') NOT NULL,

  lastName VARCHAR(50) NOT NULL,
  firstName VARCHAR(50) NOT NULL,
  middleName VARCHAR(50),
  suffix VARCHAR(5),

  birthplace VARCHAR(100) NOT NULL,
  address VARCHAR(100) NOT NULL,
  gender ENUM('MALE', 'FEMALE') NOT NULL, -- FIXME
  birthdate DATE NOT NULL,
  civilStatus VARCHAR(20) NOT NULL,
  nationality VARCHAR(20) NOT NULL,
  religion VARCHAR(20) NOT NULL,

  -- Contact info

  contact VARCHAR(15) NOT NULL,
  email VARCHAR(50) NOT NULL, -- FIXME
  landline VARCHAR(10), -- FIXME
  others VARCHAR(20),

  -- Parent/guardian info

  fatherLastName VARCHAR(50),
  fatherFirstName VARCHAR(50),
  fatherMiddleName VARCHAR(50),
  fatherMobileNumber VARCHAR(15),
  fatherAddress VARCHAR(100),

  motherLastName VARCHAR(50),
  motherFirstName VARCHAR(50),
  motherMiddleName VARCHAR(50),
  motherMobileNumber VARCHAR(15),
  motherAddress VARCHAR(100),

  guardianName VARCHAR(50),
  guardianContactNumber VARCHAR(15),
  guardianAddress VARCHAR(100),
  guardianEmail VARCHAR(50),

  courseMajID INT UNSIGNED NOT NULL,
  yearLevel INT(1) NOT NULL,
  lastSchool VARCHAR(50),

  image MEDIUMBLOB,                   -- Stores files e.g. images

  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  FULLTEXT (lastName, firstName),     -- Add indeces for searching

  PRIMARY KEY (studID),
  FOREIGN KEY (uid) REFERENCES users (uid)
    ON UPDATE CASCADE
    ON DELETE SET NULL,               -- If account is deleted (not student record), set uid to NULL
  FOREIGN KEY (courseMajID) REFERENCES courses_majors (courseMajID)
);

CREATE TABLE students_subjects (
  studSubID INT UNSIGNED AUTO_INCREMENT,
  studID INT UNSIGNED NOT NULL,
  subID INT UNSIGNED NOT NULL,
  instructorID INT UNSIGNED,

  days ENUM('MWF', 'TTh', 'F', 'S') NOT NULL,
  timeFrom TIME,                              -- Format: HH:MM:SS e.g. 08:00:00
  timeTo TIME,

  PRIMARY KEY (studSubID),
  FOREIGN KEY (studID) REFERENCES students (studID)
    ON UPDATE CASCADE,
  FOREIGN KEY (subID) REFERENCES subjects (subID),
  FOREIGN KEY (instructorID) REFERENCES instructors (instructorID)
    ON UPDATE CASCADE
    ON DELETE SET NULL
);

CREATE TABLE balance ( -- FIXME
  balID INT UNSIGNED AUTO_INCREMENT,
  studID INT UNSIGNED NOT NULL,

  y1s1 DECIMAL(9, 2) UNSIGNED DEFAULT 0.00,   -- Use DECIMAL for accurate numbers
  y1s2 DECIMAL(9, 2) UNSIGNED DEFAULT 0.00,   -- (9 digits max, 2 decimal digits) 
  y2s1 DECIMAL(9, 2) UNSIGNED DEFAULT 0.00,   -- e.g. 10000.75
  y2s2 DECIMAL(9, 2) UNSIGNED DEFAULT 0.00,
  y3s1 DECIMAL(9, 2) UNSIGNED DEFAULT 0.00,
  y3s2 DECIMAL(9, 2) UNSIGNED DEFAULT 0.00,
  y4s1 DECIMAL(9, 2) UNSIGNED DEFAULT 0.00,
  y4s2 DECIMAL(9, 2) UNSIGNED DEFAULT 0.00,
  misc DECIMAL(9, 2) UNSIGNED DEFAULT 0.00,

  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (balID),
  FOREIGN KEY (studID) REFERENCES students (studID)
    ON UPDATE CASCADE
);

CREATE TABLE transactions (
  transactionID INT UNSIGNED AUTO_INCREMENT,
  studID INT UNSIGNED NOT NULL,
  balID INT UNSIGNED NOT NULL,

  amount DECIMAL(9, 2) UNSIGNED,
  oldBalance DECIMAL(9, 2) UNSIGNED,
  newBalance DECIMAL(9, 2) UNSIGNED,
  transactionTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (transactionID),
  FOREIGN KEY (studID) REFERENCES students (studID)
    ON UPDATE CASCADE,
  FOREIGN KEY (balID) REFERENCES balance (balID)
);

CREATE TABLE registrations (
  regID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  studID INT UNSIGNED NOT NULL,
  studSubID INT UNSIGNED NOT NULL,
  balID INT UNSIGNED NOT NULL,

  balanceStatus ENUM('UNPAID', 'PARTIALLY_PAID', 'FULLY_PAID') DEFAULT 'UNPAID',
  enrollmentStatus ENUM('PENDING', 'ENROLLED', 'DROPPED') DEFAULT 'PENDING',

  PRIMARY KEY (regID),
  FOREIGN KEY (studID) REFERENCES students (studID)
    ON UPDATE CASCADE,
  FOREIGN KEY (studSubID) REFERENCES students_subjects (studSubID),
  FOREIGN KEY (balID) REFERENCES balance (balID)
);

INSERT INTO courses (title, code)
VALUES                                                        -- courseID
  ('Bachelor of Arts in Political Science', 'BAPS'),          -- 1
  ('Bachelor of Elementary Education', 'BEED'),               -- 2
  ('Bachelor of Science in Business Administration', 'BSBA'), -- 3
  ('Bachelor of Science in Computer Science', 'BSCS'),        -- 4
  ('Bachelor of Secondary Education', 'BSED');                -- 5

INSERT INTO majors (title)
VALUES                                          -- majorID
  (''),                                         -- 1
  ('Specialized in Early Childhood Education'), -- 2
  ('Major in Business Management'),             -- 3
  ('Major in Financial Management'),            -- 4
  ('Major in Marketing Management'),            -- 5
  ('Major in Biological Science'),              -- 6
  ('Major in English'),                         -- 7
  ('Major in Mathematics');                     -- 8

INSERT INTO courses_majors (courseID, majorID)
VALUES    -- courseMajID
  (1, 1), -- 1
  (2, 2), -- 2
  (3, 3), -- 3
  (3, 4), -- 4
  (3, 5), -- 5
  (4, 1), -- 6
  (5, 6), -- 7
  (5, 7), -- 8
  (5, 8); -- 9

INSERT INTO students ( -- Student record test
  studID,         schoolYear,             semester, 
  lastName,       firstName,              middleName,       suffix, 
  birthplace,     address,                gender,           birthdate,          civilStatus,  nationality, religion, 
  contact,        email,                  landline,         others, 
  fatherLastName, fatherFirstName,        fatherMiddleName, fatherMobileNumber, fatherAddress, 
  motherLastName, motherFirstName,        motherMiddleName, motherMobileNumber, motherAddress, 
  guardianName,   guardianContactNumber,  guardianEmail,    guardianAddress, 
  courseMajID,    yearLevel,              lastSchool)
VALUES ( 
  0,             'S.Y. 2023-2024',       '1ST', 
 'lastName',     'firstName',            'middleName',     'suffix', 
 'birthplace',   'address',              'MALE',           '1970-01-01',       'civilStatus','nationality','religion', 
 'contact',      'email',                'landline',       'others', 
 'fatherLastName','fatherFirstName',     'fatherMiddleName','fatherMobileNumber','fatherAddress', 
 'motherLastName','motherFirstName',     'motherMiddleName','motherMobileNumber','motherAddress', 
 'guardianName', 'guardianContactNumber','guardianEmail',  'guardianAddress', 
  1,              1,                     'lastSchool');

INSERT INTO instructors (name, gender, birthdate, address, status)
VALUES
  ('Juan Dela Cruz', 'MALE', '1990-01-01', 'ZONE 1, BRGY. MAG-UMAGA, SAN JOSE CITY, NUEVA ECIJA', 'AVAILABLE');

INSERT INTO subjects (code, title, units, semester, yearLevel, courseMajID)
VALUES
  -- Bachelor of Arts in Political Science (1)

  -- First Year/First Semester
  ('APS 1101', 'Fundamentals of Political Science', 3, '1ST', 1, 1),
  ('MLC 1101', 'Literacy/Civic Welfare/Military Science 1', 3, '1ST', 1, 1),
  ('PPE 1101', 'Physical Education 1', 2, '1ST', 1, 1),
  ('ZGE 1103', 'Ethics', 3, '1ST', 1, 1),
  ('ZGE 1105', 'Purposive Communication', 3, '1ST', 1, 1),
  ('ZGE 1106', 'Readings in Philippine History', 3, '1ST', 1, 1),
  ('ZGE 1108', 'Understanding the Self', 3, '1ST', 1, 1),
  -- First Year/Second Semester
  ('APS 1201', 'Introduction to International Relations', 3, '2ND', 1, 1),
  ('APS 1202', 'Introduction to Political Analysis and Research', 3, '2ND', 1, 1),
  ('APS 1203', 'Introduction to Philippine Politics and Governance', 3, '2ND', 1, 1),
  ('MLC 1102', 'Literacy/Civic Welfare/Military Science 2', 3, '2ND', 1, 1),
  ('PPE 1102', 'Physical Education 2', 2, '2ND', 1, 1),
  ('ZGE 1101', 'Art Appreciation', 3, '2ND', 1, 1),
  ('ZGE 1107', 'Science, Technology, and Society', 3, '2ND', 1, 1),
  ('ZPD 1102', 'Effective Communication with Personality Development', 3, '2ND', 1, 1),

  -- Second Year/First Semester
  ('AEN 2101', 'Argumentation and Debate', 3, '1ST', 2, 1),
  ('APS 2101', 'Introduction to Public International Law', 3, '1ST', 2, 1),
  ('APS 2102', 'Politics and Governance in Southeast Asia', 3, '1ST', 2, 1),
  ('APS 2103', 'Introduction to Political Theory', 3, '1ST', 2, 1),
  ('APS 2104', 'Philippine Public Administration', 2, '1ST', 2, 1),
  ('PPE 1103', 'Physical Education 3', 2, '1ST', 2, 1),
  ('ZGE 1102', 'The Contemporary World', 3, '1ST', 2, 1),
  ('ZGE 1104', 'Mathematics in the Modern World', 3, '1ST', 2, 1),
  -- Second Year/Second Semester
  ('APS 2201', 'Politics and Governance of the Americas', 3, '2ND', 2, 1),
  ('APS 2202', 'Ancient and Medieval Political thought', 3, '2ND', 2, 1),
  ('APS 2203', 'Politics and Development in Developed Nations', 3, '2ND', 2, 1),
  ('APS 2204', 'Local Government Administration', 3, '2ND', 2, 1),
  ('APS 2205', 'Introduction to Public Policy', 3, '2ND', 2, 1),
  ('APS 2206', 'Global Environmental Politics', 3, '2ND', 2, 1),
  ('PPE 1104', 'Physical Education 4', 2, '2ND', 2, 1),

  -- Third Year/First Semester
  ('APS 3101', 'Politics and Governance in Europe', 3, '1ST', 3, 1),
  ('APS 3102', 'Modern and Post-Modern Political Thought', 3, '1ST', 3, 1),
  ('APS 3103', 'Politics and Development in Developing Nations', 3, '1ST', 3, 1),
  ('APS 3104', 'Introduction to Private International Law', 3, '1ST', 3, 1),
  ('APS 3105', 'Qualitative and Quantitative Analysis of Political Data', 3, '1ST', 3, 1),
  ('ZGE EL01', 'GE Elective 1', 3, '1ST', 3, 1),
  -- Third Year/Second Semester
  ('APS 3201', 'Democratic Theory', 3, '2ND', 3, 1),
  ('APS 3202', 'Introduction to Comparative Politics', 3, '2ND', 3, 1),
  ('APS 3203', 'International and Regional Organizations', 3, '2ND', 3, 1),
  ('ARC 4980', 'Research 1', 3, '2ND', 3, 1),
  ('ZGE 1109', 'Life and Works of Rizal', 3, '2ND', 3, 1),
  ('ZGE EL02', 'GE Elective 2', 3, '2ND', 3, 1),

  -- Fourth Year/First Semester
  ('APS 4101', 'Political Behavior and Phenomena', 3, '1ST', 4, 1),
  ('APS 4102', 'International Political Economy', 3, '1ST', 4, 1),
  ('ARC 4990', 'Research 2', 3, '1ST', 4, 1),
  ('ZGE EL03', 'GE Elective 3', 3, '1ST', 4, 1),
  ('ZLI 1101', 'Philippine Literature', 3, '1ST', 4, 1),
  -- Fourth Year/Second Semester
  ('AIC 4970', 'Internship', 3, '2ND', 4, 1),

  -- Bachelor of Elementary Education with specialization in Early Childhood Education (2)

  -- First Year/First Semester
  ('CCO 113', 'Introduction to Information Technology (with Computer Fundamentals)', 3, '1ST', 1, 2),
  ('PPE 111', 'Physical Education 1', 2, '1ST', 1, 2),
  ('ZEN 110', 'Integrated Study Skills for Freshmen English', 3, '1ST', 1, 2),
  ('ZFI 111', 'Sining ng Komunikasyon', 3, '1ST', 1, 2),
  ('ZHI 111', 'Philippine History and Public Service', 3, '1ST', 1, 2),
  ('ZHI 112', 'Life and Works of Rizal', 3, '1ST', 1, 2),
  ('ZHU 111', 'Art Appreciation', 3, '1ST', 1, 2),
  ('ZNS 111', 'The Physical Sciences', 3, '1ST', 1, 2),
  ('ZPY 111', 'General Psychology', 3, '1ST', 1, 2),
  -- First Year/Second Semester
  ('EPE 121', 'Developmental Reading 1', 3, '2ND', 1, 2),
  ('EPE 1106', 'Teacher and the School Curriculum', 3, '2ND', 1, 2),
  ('EPS 121', 'Introduction to Early Childhood Education', 3, '2ND', 1, 2),
  ('EPS 122', 'Characteristics of the Young Filipino', 3, '2ND', 1, 2),
  ('MLC 111', 'Literacy/Civic Welfare/Military Science 1', 3, '2ND', 1, 2),
  ('PPE 112', 'Physical Education 2', 2, '2ND', 1, 2),
  ('ZFI 112', 'Pagbasa at Pagsulat sa Iba`t-ibang Disiplina', 3, '2ND', 1, 2),
  ('ZMA 111', 'College Algebra', 3, '2ND', 1, 2),
  ('ZNS 112', 'The Biological Sciences', 3, '2ND', 1, 2),

  -- Second Year/First Semester
  ('EPE 211', 'Child and Adolescent Development', 3, '1ST', 2, 2),
  ('EPE 213', 'Field Study 1 - Learners Development', 1, '1ST', 2, 2),
  ('EPE 214', 'Principles of Teaching 1', 3, '1ST', 2, 2),
  ('EPE 215', 'Social Dimensions of Education', 3, '1ST', 2, 2),
  ('EPE 1104', 'Facilitating Learner-centered Teaching', 3, '1ST', 2, 2),
  ('EPS 211', 'Early Childhood Education Curriculum', 3, '1ST', 2, 2),
  ('EPS 212', 'Creative Arts, Music and Drama for Young Children', 3, '1ST', 2, 2),
  ('MLC 112', 'Literacy/Civic Welfare/Military Science 2', 3, '1ST', 2, 2),
  ('PPE 113', 'Physical Education 3', 2, '1ST', 2, 2),
  ('ZEN 112', 'College Reading and Writing', 3, '1ST', 2, 2),
  -- Second Year/Second Semester
  ('EPE 221', 'Developmental Reading 2', 3, '2ND', 2, 2),
  ('EPE 222', 'Educational Technology 1', 3, '2ND', 2, 2),
  ('EPE 223', 'Field Study 2 - Experiencing the Teaching-Learning Process', 1, '2ND', 2, 2),
  ('EPE 224', 'Principles of Teaching 2', 3, '2ND', 2, 2),
  ('EPS 221', 'Personal and Social Development (Play & Social Living Experiences)', 3, '2ND', 2, 2),
  ('EPS 222', 'Language and Numeracy for Young Children', 3, '2ND', 2, 2),
  ('EPS 223', 'Science, Health & Nature Study', 3, '2ND', 2, 2),
  ('PPE 114', 'Physical Education 4', 2, '2ND', 2, 2),
  ('ZLI 111', 'Literatures of the Philippines/Panitikan ng Pilipinas', 3, '2ND', 2, 2),
  ('ZST 111', 'Basic Statistics', 3, '2ND', 2, 2),

  -- Third Year/First Semester
  ('EPE 311', 'Assessment of Learning 1', 3, '1ST', 3, 2),
  ('EPE 312', 'Educational Technology 2', 3, '1ST', 3, 2),
  ('EPE 313', 'Field Study 3 - Technology in the Learning Environment', 1, '1ST', 3, 2),
  ('EPE 314', 'Special Topics 1', 1, '1ST', 3, 2),
  ('EPE 315', 'Special Topics 2', 1, '1ST', 3, 2),
  ('EPE 316', 'Special Topics 3', 1, '1ST', 3, 2),
  ('EPS 311', 'Preoperational of Instructional Materials for Early Childhood', 3, '1ST', 3, 2),
  ('EPS 312', 'Observational Study', 3, '1ST', 3, 2),
  ('ESE 121', 'Introduction to SPED 1', 3, '1ST', 3, 2),
  ('ZEN 114', 'Speech Communication', 3, '1ST', 3, 2),
  ('ZLI 112', 'Literatures of the World/Panitikan ng Mundo', 3, '1ST', 3, 2),
  ('ZPS 111', 'Politics and Governance (with Philippine Constitution)', 3, '1ST', 3, 2),
  -- Third Year/Second Semester
  ('BEC 111', 'Introduction to Economics with Land Reform and Taxation', 3, '2ND', 3, 2),
  ('EIC 111', 'Integrated Course 1', 1, '2ND', 3, 2),
  ('EPE 321', 'Field Study 4 - Understanding Curriculum Development', 1, '2ND', 3, 2),
  ('EPS 321', 'Assessing Behaviors of Young Children', 3, '2ND', 3, 2),
  ('EPS 322', 'Guidance and Counselling in Early Childhood Education', 3, '2ND', 3, 2),
  ('EPS 323', 'Organization & Management of Child Development Programs', 3, '2ND', 3, 2),
  ('EPS 498', 'Directed Study in Early Childhood Education 1', 3, '2ND', 3, 2),
  ('ZFI 113', 'Kasanayan sa Malikhaing Pagpapahayag (Retorika)', 3, '2ND', 3, 2),
  ('ZPL 112', 'Introduction to Philosophy w/ Logic and Critical Thinking', 3, '2ND', 3, 2),
  ('ZSC 113', 'Society and Culture with Family Planning', 3, '2ND', 3, 2),

  -- Fourth Year/First Semester
  ('EIC 112', 'Integrated Course 2', 2, '1ST', 4, 2),
  ('EPE 411', 'Assessment of Learning 2', 3, '1ST', 4, 2),
  ('EPE 412', 'Field Study 5 - Learning Assessment', 1, '1ST', 4, 2),
  ('EPE 413', 'Field Study 6 - On Becoming a Teacher', 1, '1ST', 4, 2),
  ('EPE 414', 'The Teaching Profession', 3, '1ST', 4, 2),
  ('EPS 411', 'Classroom Management', 3, '1ST', 4, 2),
  ('EPS 412', 'Technology in Early Childhood Education', 3, '1ST', 4, 2),
  ('EPS 413', 'Home-School Relationship', 3, '1ST', 4, 2),
  ('EPS 414', 'Trends and Issues in Early Childhood Education', 3, '1ST', 4, 2),
  ('EPS 499', 'Directed Study in Early Childhood Education 2', 3, '1ST', 4, 2),
  ('ZPD 111', 'Personality Development', 1, '1ST', 4, 2),
  -- Fourth Year/Second Semester
  ('EIC 113', 'Integrated Course 3', 2, '2ND', 4, 2),
  ('EPE 421', 'Practicum', 6, '2ND', 4, 2),

  -- Bachelor of Science in Business Administration Major in Business Management (3)

  -- First Year/First Semester
  ('BBC 1101', 'Basic Microeconomics', 3, '1ST', 1, 3),
  ('BBC 1109', 'Human Resources Management', 3, '1ST', 1, 3),
  ('BME 1101', 'Administrative Management and Automation', 3, '1ST', 1, 3),
  ('MLC 1101', 'Literacy/Civic Welfare/Military Science 1', 3, '1ST', 1, 3),
  ('PPE 1101', 'Physical Education 1', 2, '1ST', 1, 3),
  ('ZGE 1104', 'Mathematics in the Modern World', 3, '1ST', 1, 3),
  ('ZGE 1106', 'Readings in Philippine History', 3, '1ST', 1, 3),
  ('ZGE 1108', 'Understanding the Self', 3, '1ST', 1, 3),
  -- First Year/Second Semester
  ('BBC 1106', 'Essentials of Accounting', 3, '2ND', 1, 3),
  ('BBC 1201', 'Quantitative Techniques in Business', 3, '2ND', 1, 3),
  ('BME 1201', 'Public Governance (Local and National)', 3, '2ND', 1, 3),
  ('BME 1202', 'International Business Management', 3, '2ND', 1, 3),
  ('MLC 1102', 'Literacy/Civic Welfare/Military Science 2', 3, '2ND', 1, 3),
  ('PPE 1102', 'Physical Education 2', 2, '2ND', 1, 3),
  ('ZGE 1102', 'The Contemporary World', 3, '2ND', 1, 3),
  ('ZGE 1107', 'Science, Technology, and Society', 3, '2ND', 1, 3),

  -- Second Year/First Semester
  ('BME 2101', 'Organizational Development', 3, '1ST', 2, 3),
  ('BME 2102', 'Labor Law and Legislation', 3, '1ST', 2, 3),
  ('BME 2103', 'Logistics and Supply Chain Management', 3, '1ST', 2, 3),
  ('BME EL01', 'Professional Elective 1', 3, '1ST', 2, 3),
  ('PPE 1103', 'Physical Education 3', 2, '1ST', 2, 3),
  ('ZGE 1105', 'Purposive Communication', 3, '1ST', 2, 3),
  ('ZGE 1109', 'Life and Works of Rizal', 3, '1ST', 2, 3),
  -- Second Year/Second Semester
  ('BBC 1102', 'Fundamentals of Business Analytics', 3, '2ND', 2, 3),
  ('BBC 1202', 'Taxation (Income Taxation)', 3, '2ND', 2, 3),
  ('BME 2201', 'Marketing Management (with Case Analysis)', 3, '2ND', 2, 3),
  ('BME 2202', 'Human Behavior in Organizations', 3, '2ND', 2, 3),
  ('BME EL02', 'Professional Elective 2', 3, '2ND', 2, 3),
  ('PPE 1104', 'Physical Education 4', 2, '2ND', 2, 3),
  ('ZGE 1103', 'Ethics', 3, '2ND', 2, 3),
  ('ZPD 1101', 'Personality Development', 1, '2ND', 2, 3),

  -- Third Year/First Semester
  ('BBC 1103', 'Business Analytics (Data Warehousing)', 3, '1ST', 3, 3),
  ('BBC 1104', 'Busines Law (Obligations & Contracts)', 3, '1ST', 3, 3),
  ('BMC 1101', 'Operations Management (TQM)', 3, '1ST', 3, 3),
  ('ZGE 1101', 'Art Appreciation', 3, '1ST', 3, 3),
  ('ZGE EL01', 'GE Elective 1', 3, '1ST', 3, 3),
  ('ZGE EL02', 'GE Elective 2', 3, '1ST', 3, 3),
  -- Third Year/Second Semester
  ('BBC 1107', 'Feasibility Study', 3, '2ND', 3, 3),
  ('BBC 1108', 'Good Governance and Social Responsibility', 3, '2ND', 3, 3),
  ('BBC 1200', 'International Business and Trade', 3, '2ND', 3, 3),
  ('BBC 4980', 'Business Research', 3, '2ND', 3, 3),
  ('BME 3201', 'Events Management', 3, '2ND', 3, 3),
  ('BME EL03', 'Professional Elective 3', 3, '2ND', 3, 3),
  ('ZGE EL03', 'GE Elective 3', 3, '2ND', 3, 3),

  -- Fourth Year/First Semester
  ('BBC 1105', 'Business Venture', 3, '1ST', 4, 3),
  ('BBC 4990', 'Thesis Writing', 3, '1ST', 4, 3),
  ('BMC 1102', 'Strategic Management', 3, '1ST', 4, 3),
  ('BME 4101', 'Special Topics in Human Resource Management', 3, '1ST', 4, 3),
  ('BME EL04', 'Professional Elective 4', 3, '1ST', 4, 3),
  -- Fourth Year/Second Semester
  ('BME 4970', 'Practicum/Work Integrated Learning', 6, '2ND', 4, 3),

  -- Bachelor of Science in Business Administration Major in Financial Management (4)
    
  -- First Year/First Semester
  ('BBC 1101', 'Basic Microeconomics', 3, '1ST', 1, 4),
  ('BBC 1109', 'Human Resources Management', 3, '1ST', 1, 4),
  ('BFN 1101', 'Banking and Financial Institutions', 3, '1ST', 1, 4),
  ('MLC 1101', 'Literacy/Civic Welfare/Military Science 1', 3, '1ST', 1, 4),
  ('PPE 1101', 'Physical Education 1', 2, '1ST', 1, 4),
  ('ZGE 1104', 'Mathematics in the Modern World', 3, '1ST', 1, 4),
  ('ZGE 1106', 'Readings in Philippine History', 3, '1ST', 1, 4),
  ('ZGE 1108', 'Understanding the Self', 3, '1ST', 1, 4),
  -- First Year/Second Semester
  ('BBC 1106', 'Essentials of Accounting', 3, '2ND', 1, 4),
  ('BBC 1201', 'Quantitative Techniques in Business', 3, '2ND', 1, 4),
  ('BFN 1201', 'Credit and Collection', 3, '2ND', 1, 4),
  ('BFN EL01', 'Professional Elective 1', 3, '2ND', 1, 4),
  ('MLC 1102', 'Literacy/Civic Welfare/Military Science 2', 3, '2ND', 1, 4),
  ('PPE 1102', 'Physical Education 2', 2, '2ND', 1, 4),
  ('ZGE 1102', 'The Contemporary World', 3, '2ND', 1, 4),
  ('ZGE 1107', 'Science, Technology, and Society', 3, '2ND', 1, 4),

  -- Second Year/First Semester
  ('BBC 1108', 'Good Governance and Social Responsibility', 3, '1ST', 2, 4),
  ('BFN 2101', 'Financial Management', 3, '1ST', 2, 4),
  ('BFN 2102', 'Monetary Policy and Central Banking', 3, '1ST', 2, 4),
  ('BMC 1101', 'Operations Management (TQM)', 3, '1ST', 2, 4),
  ('PPE 1103', 'Physical Education 3', 2, '1ST', 2, 4),
  ('ZGE 1105', 'Purposive Communication', 3, '1ST', 2, 4),
  ('ZGE 1109', 'Life and Works of Rizal', 3, '1ST', 2, 4),
  -- Second Year/Second Semester
  ('BBC 1102', 'Fundamentals of Business Analytics', 3, '2ND', 2, 4),
  ('BBC 1200', 'International Business and Trade', 3, '2ND', 2, 4),
  ('BBC 1202', 'Taxation (Income Taxation)', 3, '2ND', 2, 4),
  ('BFN 2201', 'Financial Analysis and Reporting', 3, '2ND', 2, 4),
  ('BFN EL02', 'Professional Elective 2', 3, '2ND', 2, 4),
  ('PPE 1104', 'Physical Education 4', 2, '2ND', 2, 4),
  ('ZGE 1103', 'Ethics', 3, '2ND', 2, 4),
  ('ZPD 1101', 'Personality Development', 1, '2ND', 2, 4),

  -- Third Year/First Semester
  ('BBC 1103', 'Business Analytics (Data Warehousing)', 3, '1ST', 3, 4),
  ('BBC 1104', 'Busines Law (Obligations & Contracts)', 3, '1ST', 3, 4),
  ('BFN 3101', 'Investment and Portfolio Management', 3, '1ST', 3, 4),
  ('ZGE 1101', 'Art Appreciation', 3, '1ST', 3, 4),
  ('ZGE EL01', 'GE Elective 1', 3, '1ST', 3, 4),
  ('ZGE EL02', 'GE Elective 2', 3, '1ST', 3, 4),
  -- Third Year/Second Semester
  ('BBC 1107', 'Feasibility Study', 3, '2ND', 3, 4),
  ('BBC 4980', 'Business Research', 3, '2ND', 3, 4),
  ('BFN 3201', 'Strategic Financial Management', 3, '2ND', 3, 4),
  ('BFN 3202', 'Special Topics in Financial Management', 3, '2ND', 3, 4),
  ('BFN EL03', 'Professional Elective 3', 3, '2ND', 3, 4),
  ('ZGE EL03', 'GE Elective 3', 3, '2ND', 3, 4),

  -- Fourth Year/First Semester
  ('BBC 1105', 'Business Venture', 3, '1ST', 4, 4),
  ('BBC 4990', 'Thesis Writing', 3, '1ST', 4, 4),
  ('BBE 4102', 'Managerial Economics', 3, '1ST', 4, 4),
  ('BFN 4101', 'Capital Market', 3, '1ST', 4, 4),
  ('BFN EL04', 'Professional Elective 4', 3, '1ST', 4, 4),
  ('BMC 1102', 'Strategic Management', 3, '1ST', 4, 4),
  -- Fourth Year/Second Semester
  ('BFN 4970', 'Practicum/Work Integrated Learning', 6, '2ND', 4, 4),

  -- Bachelor of Science in Business Administration Major in Marketing Management (5)

  -- First Year/First Semester
  ('BBC 1101', 'Basic Microeconomics', 3, '1ST', 1, 5),
  ('BBC 1109', 'Human Resources Management', 3, '1ST', 1, 5),
  ('BMM 1101', 'Essentials of Marketing Management', 3, '1ST', 1, 5),
  ('MLC 1101', 'Literacy/Civic Welfare/Military Science 1', 3, '1ST', 1, 5),
  ('PPE 1101', 'Physical Education 1', 2, '1ST', 1, 5),
  ('ZGE 1104', 'Mathematics in the Modern World', 3, '1ST', 1, 5),
  ('ZGE 1106', 'Readings in Philippine History', 3, '1ST', 1, 5),
  ('ZGE 1108', 'Understanding the Self', 3, '1ST', 1, 5),
  -- First Year/Second Semester
  ('BBC 1106', 'Essentials of Accounting', 3, '2ND', 1, 5),
  ('BMM 1201', 'Consumer Behavior', 3, '2ND', 1, 5),
  ('BMM EL01', 'Professional Elective 1', 3, '2ND', 1, 5),
  ('MLC 1102', 'Literacy/Civic Welfare/Military Science 2', 3, '2ND', 1, 5),
  ('PPE 1102', 'Physical Education 2', 2, '2ND', 1, 5),
  ('ZGE 1102', 'The Contemporary World', 3, '2ND', 1, 5),
  ('ZGE 1107', 'Science, Technology, and Society', 3, '2ND', 1, 5),
  ('ZGE 1109', 'Life and Works of Rizal', 3, '2ND', 1, 5),

  -- Second Year/First Semester
  ('BBC 1108', 'Good Governance and Social Responsibility', 3, '1ST', 2, 5),
  ('BMM 2101', 'Professional Salesmanship', 3, '1ST', 2, 5),
  ('BMM EL02', 'Professional Elective 2', 3, '1ST', 2, 5),
  ('PPE 1103', 'Physical Education 3', 2, '1ST', 2, 5),
  ('ZGE 1101', 'Art Appreciation', 3, '1ST', 2, 5),
  ('ZGE 1103', 'Ethics', 3, '1ST', 2, 5),
  ('ZGE 1105', 'Purposive Communication', 3, '1ST', 2, 5),
  -- Second Year/Second Semester
  ('BBC 1201', 'Quantitative Techniques in Business', 3, '2ND', 2, 5),
  ('BBC 1202', 'Taxation (Income Taxation)', 3, '2ND', 2, 5),
  ('BBC 4980', 'Business Research', 3, '2ND', 2, 5),
  ('BMM 2201', 'Product Management', 3, '2ND', 2, 5),
  ('BMM 2202', 'Distribution Management', 3, '2ND', 2, 5),
  ('BMM EL03', 'Professional Elective 3', 3, '2ND', 2, 5),
  ('PPE 1104', 'Physical Education 4', 2, '2ND', 2, 5),
  ('ZPD 1101', 'Personality Development', 1, '2ND', 2, 5),

  -- Third Year/First Semester
  ('BBC 1102', 'Fundamentals of Business Analytics', 3, '1ST', 3, 5),
  ('BMC 1101', 'Operations Management (TQM)', 3, '1ST', 3, 5),
  ('BMM 3101', 'Marketing Communications (Advertising)', 3, '1ST', 3, 5),
  ('BMM 3102', 'Marketing Research (Thesis Writing)', 3, '1ST', 3, 5),
  ('BMM EL04', 'Professional Elective 4', 3, '1ST', 3, 5),
  ('ZGE EL01', 'GE Elective 1', 3, '1ST', 3, 5),
  -- Third Year/Second Semester
  ('BBC 1103', 'Business Analytics (Data Warehousing)', 3, '2ND', 3, 5),
  ('BBC 1107', 'Feasibility Study', 3, '2ND', 3, 5),
  ('BMC 1102', 'Strategic Management', 3, '2ND', 3, 5),
  ('BMM 3201', 'Digital Marketing', 3, '2ND', 3, 5),
  ('BMM 3202', 'Retail Management', 3, '2ND', 3, 5),
  ('ZGE EL02', 'GE Elective 2', 3, '2ND', 3, 5),

  -- Fourth Year/First Semester
  ('BBC 1104', 'Busines Law (Obligations & Contracts)', 3, '1ST', 4, 5),
  ('BBC 1105', 'Business Venture', 3, '1ST', 4, 5),
  ('BBC 1200', 'International Business and Trade', 3, '1ST', 4, 5),
  ('BMM 4101', 'Pricing Strategy', 3, '1ST', 4, 5),
  ('BMM 4102', 'Marketing Management', 3, '1ST', 4, 5),
  ('ZGE EL03', 'GE Elective 3', 3, '1ST', 4, 5),
  -- Fourth Year/Second Semester
  ('BMM 4970', 'Practicum/Work Integrated Learning', 6, '2ND', 4, 5),

  -- Bachelor of Science in Computer Science (6)

  -- First Year/First Semester
  ('CCS 1101', 'Fundamentals of Programming', 3, '1ST', 1, 6),
  ('CIC 1101', 'Introduction to Computing', 3, '1ST', 1, 6),
  ('MLC 1101', 'Literacy/Civic Welfare/Military Science 1', 3, '1ST', 1, 6),
  ('PPE 1101', 'Physical Education 1', 2, '1ST', 1, 6),
  ('ZGE 1101', 'Art Appreciation', 3, '1ST', 1, 6),
  ('ZGE 1103', 'Ethics', 3, '1ST', 1, 6),
  ('ZGE 1104', 'Mathematics in the Modern World', 3, '1ST', 1, 6),
  -- First Year/Second Semester
  ('CCS 1201', 'Intermediate Programming', 3, '2ND', 1, 6),
  ('CMA 1101', 'Analysis 1 for CS', 3, '2ND', 1, 6),
  ('CSP 1101', 'Social and Professional Issues in Computing', 3, '2ND', 1, 6),
  ('MLC 1102', 'Literacy/Civic Welfare/Military Science 2', 3, '2ND', 1, 6),
  ('PPE 1102', 'Physical Education 2', 2, '2ND', 1, 6),
  ('ZGE 1105', 'Purposive Communication', 3, '2ND', 1, 6),
  ('ZGE 1106', 'Readings in Philippine History', 3, '2ND', 1, 6),
  ('ZGE 1108', 'Understanding the Self', 3, '2ND', 1, 6),

  -- Second Year/First Semester
  ('CCS 2101', 'Discrete Structures 1', 3, '1ST', 2, 6),
  ('CCS 2102', 'Object-Oriented Programming', 3, '1ST', 2, 6),
  ('CDS 1101', 'Data Structures and Algorithms', 3, '1ST', 2, 6),
  ('CHC 1101', 'Human Computer Interaction', 3, '1ST', 2, 6),
  ('CIM 1101', 'Information Management', 3, '1ST', 2, 6),
  ('CMA 1102', 'Analysis 2 for CS', 3, '1ST', 2, 6),
  ('PPE 1103', 'Physical Education 3', 2, '1ST', 2, 6),
  ('ZGE 1102', 'The Contemporary World', 3, '1ST', 2, 6),
  -- Second Year/Second Semester
  ('CCS 2201', 'Architecture and Organization', 3, '2ND', 2, 6),
  ('CCS 2202', 'Discrete Structures 2', 3, '2ND', 2, 6),
  ('CCS 2203', 'Operating Systems', 3, '2ND', 2, 6),
  ('CCS 2204', 'Statistics for CS', 3, '2ND', 2, 6),
  ('CIA 1101', 'Information Assurance and Security 1', 3, '2ND', 2, 6),
  ('CIP 1101', 'Integrative Programming and Technologies 1', 3, '2ND', 2, 6),
  ('CSE 1101', 'Software Engineering 1', 3, '2ND', 2, 6),
  ('PPE 1104', 'Physical Education 4', 2, '2ND', 2, 6),

  -- Third Year/First Semester
  ('APC 3101', 'Physics for CS (with Electromagnetism)', 4, '1ST', 3, 6),
  ('CCS 3101', 'Algorithm and Complexity', 3, '1ST', 3, 6),
  ('CCS 3102', 'Methods of Research for CS', 3, '1ST', 3, 6),
  ('CCS 3103', 'Networks and Communications', 3, '1ST', 3, 6),
  ('CCS 3104', 'Software Engineering 2', 3, '1ST', 3, 6),
  ('CDT 1101', 'Data Analytics', 3, '1ST', 3, 6),
  ('CMS 1101', 'Multimedia Systems', 3, '1ST', 3, 6),
  -- Third Year/Second Semester
  ('CCS 3201', 'Artificial Intelligence', 3, '2ND', 3, 6),
  ('CCS 3202', 'Automata Theory and Formal Languages', 3, '2ND', 3, 6),
  ('CCS 4980', 'Thesis Writing 1 for CS', 3, '2ND', 3, 6),
  ('CCS EL01', 'Professional Elective 1', 3, '2ND', 3, 6),
  ('CDE 1101', 'Applications Development and Emerging Technologies', 3, '2ND', 3, 6),
  ('ZGE EL01', 'GE Elective 1', 3, '2ND', 3, 6),
  ('ZPD 1102', 'Effective Communication with Personality Development', 3, '2ND', 3, 6),

  -- Third Year/Summer
  ('CCS 4970', 'Practicum for CS', 3, 'SUMMER', 3, 6),

  -- Fourth Year/First Semester
  ('CCS 4101', 'Modeling and Simulation', 3, '1ST', 4, 6),
  ('CCS 4102', 'Programming Languages', 3, '1ST', 4, 6),
  ('CCS 4990', 'Thesis Writing 2 for CS', 3, '1ST', 4, 6),
  ('CCS EL02', 'Professional Elective 2', 3, '1ST', 4, 6),
  ('CNA 1101', 'Numerical Analysis for ITE', 3, '1ST', 4, 6),
  ('ZGE 1107', 'Science, Technology, and Society', 3, '1ST', 4, 6),
  ('ZGE 1109', 'Life and Works of Rizal', 3, '1ST', 4, 6),
  -- Fourth Year/Second Semester
  ('CCS 4201', 'Digital Electronics and Logic Design', 3, '2ND', 4, 6),
  ('CCS EL03', 'Professional Elective 3', 3, '2ND', 4, 6),
  ('CCS EL04', 'Professional Elective 4', 3, '2ND', 4, 6),
  ('CIS 3202', 'Technopreneurship', 3, '2ND', 4, 6),
  ('ZGE EL02', 'GE Elective 2', 3, '2ND', 4, 6),
  ('ZGE EL03', 'GE Elective 3', 3, '2ND', 4, 6),

  -- Bachelor of Secondary Education Major in Biological Sciences (7)

  -- First Year/First Semester
  ('CCO 113', 'Introduction to Information Technology (with Computer Fundamentals)', 3, '1ST', 1, 7),
  ('PPE 111', 'Physical Education 1', 2, '1ST', 1, 7),
  ('ZEN 110', 'Integrated Study Skills for Freshmen English', 3, '1ST', 1, 7),
  ('ZFI 111', 'Sining ng Komunikasyon', 3, '1ST', 1, 7),
  ('ZHI 111', 'Philippine History and Public Service', 3, '1ST', 1, 7),
  ('ZHI 112', 'Life and Works of Rizal', 3, '1ST', 1, 7),
  ('ZHU 111', 'Art Appreciation', 3, '1ST', 1, 7),
  ('ZNS 111', 'The Physical Sciences', 3, '1ST', 1, 7),
  ('ZPY 111', 'General Psychology', 3, '1ST', 1, 7),
  -- First Year/Second Semester
  ('AZO 111', 'General Zoology (Lecture)', 3, '2ND', 1, 7),
  ('AZO 112', 'General Zoology (Laboratory)', 2, '2ND', 1, 7),
  ('EHC 201', 'Science, Technology and Society', 3, '2ND', 1, 7),
  ('EPE 121', 'Developmental Reading 1', 3, '2ND', 1, 7),
  ('EPE 122', 'Intro to Curriculum Development', 3, '2ND', 1, 7),
  ('MLC 111', 'Literacy/Civic Welfare/Military Science 1', 3, '2ND', 1, 7),
  ('PPE 112', 'Physical Education 2', 2, '2ND', 1, 7),
  ('ZFI 112', 'Pagbasa at Pagsulat sa Iba`t-ibang Disiplina', 3, '2ND', 1, 7),
  ('ZMA 111', 'College Algebra', 3, '2ND', 1, 7),
  ('ZNS 112', 'The Biological Sciences', 3, '2ND', 1, 7),

  -- Second Year/First Semester
  ('ABO 111', 'General Botany (Lecture)', 3, '1ST', 2, 7),
  ('ABO 112', 'General Botany (Laboratory)', 2, '1ST', 2, 7),
  ('EPE 211', 'Child and Adolescent Development', 3, '1ST', 2, 7),
  ('EPE 212', 'Facilitating Learning', 3, '1ST', 2, 7),
  ('EPE 213', 'Field Study 1 - Learners Development', 1, '1ST', 2, 7),
  ('EPE 214', 'Principles of Teaching 1', 3, '1ST', 2, 7),
  ('EPE 215', 'Social Dimensions of Education', 3, '1ST', 2, 7),
  ('MLC 112', 'Literacy/Civic Welfare/Military Science 2', 3, '1ST', 2, 7),
  ('PPE 113', 'Physical Education 3', 2, '1ST', 2, 7),
  ('ZEN 112', 'College Reading and Writing', 3, '1ST', 2, 7),
  -- Second Year/Second Semester
  ('ABI 321', 'Ecology', 3, '2ND', 2, 7),
  ('ACH 111', 'General and Inorganic Chemistry (Lecture)', 3, '2ND', 2, 7),
  ('ACH 112', 'General and Inorganic Chemistry (Laboratory)', 2, '2ND', 2, 7),
  ('EPE 222', 'Educational Technology 1', 3, '2ND', 2, 7),
  ('EPE 223', 'Field Study 2 - Experiencing the Teaching-Learning Process', 1, '2ND', 2, 7),
  ('EPE 224', 'Principles of Teaching 2', 3, '2ND', 2, 7),
  ('PPE 114', 'Physical Education 4', 2, '2ND', 2, 7),
  ('ZLI 111', 'Literatures of the Philippines/Panitikan ng Pilipinas', 3, '2ND', 2, 7),
  ('ZNS 114', 'Environmental Science', 3, '2ND', 2, 7),
  ('ZST 111', 'Basic Statistics', 3, '2ND', 2, 7),

  -- Third Year/First Semester
  ('ABI 302', 'Cell Biology', 3, '1ST', 3, 7),
  ('ACH 221', 'Organic Chemistry (Lecture)', 3, '1ST', 3, 7),
  ('ACH 222', 'Organic Chemistry (Laboratory)', 2, '1ST', 3, 7),
  ('EPE 311', 'Assessment of Learning 1', 3, '1ST', 3, 7),
  ('EPE 312', 'Educational Technology 2', 3, '1ST', 3, 7),
  ('EPE 313', 'Field Study 3 - Technology in the Learning Environment', 1, '1ST', 3, 7),
  ('EPE 314', 'Special Topics 1', 1, '1ST', 3, 7),
  ('EPE 315', 'Special Topics 2', 1, '1ST', 3, 7),
  ('EPE 316', 'Special Topics 3', 1, '1ST', 3, 7),
  ('ZEN 114', 'Speech Communication', 3, '1ST', 3, 7),
  ('ZLI 112', 'Literatures of the World/Panitikan ng Mundo', 3, '1ST', 3, 7),
  ('ZPS 111', 'Politics and Governance (with Philippine Constitution)', 3, '1ST', 3, 7),
  -- Third Year/Second Semester
  ('ABI 322', 'General Microbiology (Lecture)', 3, '2ND', 3, 7),
  ('ABI 323', 'General Microbiology (Laboratory)', 2, '2ND', 3, 7),
  ('ACH 405', 'Chemistry of Biosystems', 5, '2ND', 3, 7),
  ('BEC 111', 'Introduction to Economics with Land Reform and Taxation', 3, '2ND', 3, 7),
  ('EBI 311', 'Genetics(Lecture)', 3, '2ND', 3, 7),
  ('EIC 111', 'Integrated Course 1', 1, '2ND', 3, 7),
  ('EPE 321', 'Field Study 4 - Understanding Curriculum Development', 1, '2ND', 3, 7),
  ('ZFI 113', 'Kasanayan sa Malikhaing Pagpapahayag (Retorika)', 3, '2ND', 3, 7),
  ('ZPL 112', 'Introduction to Philosophy w/ Logic and Critical Thinking', 3, '2ND', 3, 7),
  ('ZSC 113', 'Society and Culture with Family Planning', 3, '2ND', 3, 7),

  -- Fourth Year/First Semester
  ('ABI 412', 'Biological Techniques', 2, '1ST', 4, 7),
  ('ABI 413', 'Marine Biology (Lecture)', 3, '1ST', 4, 7),
  ('ABI 414', 'Marine Biology (Laboratory)', 2, '1ST', 4, 7),
  ('EBI 322', 'History and Philosophy of Science', 3, '1ST', 4, 7),
  ('EIC 112', 'Integrated Course 2', 2, '1ST', 4, 7),
  ('EPE 411', 'Assessment of Learning 2', 3, '1ST', 4, 7),
  ('EPE 412', 'Field Study 5 - Learning Assessment', 1, '1ST', 4, 7),
  ('EPE 413', 'Field Study 6 - On Becoming a Teacher', 1, '1ST', 4, 7),
  ('EPE 414', 'The Teaching Profession', 3, '1ST', 4, 7),
  ('EZN 211', 'Human Anatomy and Physiology (lec)', 3, '1ST', 4, 7),
  ('EZN 212', 'Human Anatomy and Physiology (lab)', 2, '1ST', 4, 7),
  ('ZPD 111', 'Personality Development', 1, '1ST', 4, 7),
  -- Fourth Year/Second Semester
  ('EIC 113', 'Integrated Course 3', 2, '2ND', 4, 7),
  ('EPE 421', 'Practicum', 6, '2ND', 4, 7),

  -- Bachelor of Secondary Education major in English (8)

  -- First Year/First Semester
  ('EEN 1101', 'Introduction to Linguistics', 3, '1ST', 1, 8),
  ('EEN 1102', 'Language, Culture, and Society', 3, '1ST', 1, 8),
  ('EEN 1103', 'Structures of English', 3, '1ST', 1, 8),
  ('EEN 1104', 'Children and Adolescent Literature', 3, '1ST', 1, 8),
  ('MLC 1101', 'Literacy/Civic Welfare/Military Science 1', 3, '1ST', 1, 8),
  ('PPE 1101', 'Physical Education 1', 2, '1ST', 1, 8),
  ('ZGE 1104', 'Mathematics in the Modern World', 3, '1ST', 1, 8),
  ('ZGE 1107', 'Science, Technology, and Society', 3, '1ST', 1, 8),
  ('ZGE 1108', 'Understanding the Self', 3, '1ST', 1, 8),
  -- First Year/Second Semester
  ('EEN 1201', 'Principles and Theories of Language Acquisition and Learning', 3, '2ND', 1, 8),
  ('EEN 1202', 'Contemporary, Popular, and Emergent Literature', 3, '2ND', 1, 8),
  ('EEN 1203', 'Mythology and Folklore', 3, '2ND', 1, 8),
  ('EPE 1101', 'The Child and Adolescent Learners and Learning Principles', 3, '2ND', 1, 8),
  ('EPE 1103', 'Technology for Teachingand Learning 1', 3, '2ND', 1, 8),
  ('MLC 1102', 'Literacy/Civic Welfare/Military Science 2', 3, '2ND', 1, 8),
  ('PPE 1102', 'Physical Education 2', 2, '2ND', 1, 8),
  ('ZGE 1103', 'Ethics', 3, '2ND', 1, 8),

  -- Second Year/First Semester
  ('EEN 2101', 'Teaching and Assessment of Macro Skills', 3, '1ST', 2, 8),
  ('EEN 2102', 'Teaching and Assessment of Grammar', 3, '1ST', 2, 8),
  ('EEN 2103', 'Language Programs and Policies in Multilingual Societies', 3, '1ST', 2, 8),
  ('EEN 4301', '(Cognate) Creative Writing', 3, '1ST', 2, 8),
  ('EPE 1110', 'The Teaching Profession', 3, '1ST', 2, 8),
  ('PPE 1103', 'Physical Education 3', 2, '1ST', 2, 8),
  ('ZGE 1101', 'Art Appreciation', 3, '1ST', 2, 8),
  ('ZGE 1106', 'Readings in Philippine History', 3, '1ST', 2, 8),
  -- Second Year/Second Semester
  ('EEN 2201', 'Language Research', 3, '2ND', 2, 8),
  ('EEN 2202', 'Survey of English and American Literature', 3, '2ND', 2, 8),
  ('EEN 2203', 'Survey of Afro-Asian Literature', 3, '2ND', 2, 8),
  ('EEN 2204', 'Survey of Philippine Literature in English', 3, '2ND', 2, 8),
  ('EPE 1108', 'The Teacher and the Community, School Culture and Organizational Leadership', 3, '2ND', 2, 8),
  ('PPE 1104', 'Physical Education 4', 2, '2ND', 2, 8),
  ('ZGE 1105', 'Purposive Communication', 3, '2ND', 2, 8),
  ('ZGE 1109', 'Life and Works of Rizal', 3, '2ND', 2, 8),

  -- Third Year/First Semester
  ('EEN 3101', 'Language Learning Materials Development', 3, '1ST', 3, 8),
  ('EEN 3102', 'Speech and Theater Arts', 3, '1ST', 3, 8),
  ('EEN 3103', 'Literary Criticism', 3, '1ST', 3, 8),
  ('EEN 3104', 'Technical Writing', 3, '1ST', 3, 8),
  ('EPE 1104', 'Facilitating Learner-centered Teaching', 3, '1ST', 3, 8),
  ('EPE 1105', 'Foundations of Special and Inclusive Education', 3, '1ST', 3, 8),
  ('EPE 1107', 'Assessment in Learning 1', 3, '1ST', 3, 8),
  ('ZFN 1101', 'Kontekstwalisadong Komunikasyon sa Filipino (KOMFIL)', 3, '1ST', 3, 8),
  -- Third Year/Second Semester
  ('EEN 3201', 'Campus Journalism', 3, '2ND', 3, 8),
  ('EEN 3202', 'Teaching and Assessment of Literature Studies', 3, '2ND', 3, 8),
  ('EEN 3203', 'Technology for Teaching and Learning 2 (Technology in Secondary Language Education', 3, '2ND', 3, 8),
  ('EPE 1102', 'Building and Enhancing New Literacies Across the Curriculum', 3, '2ND', 3, 8),
  ('EPE 1106', 'Teacher and the School Curriculum', 3, '2ND', 3, 8),
  ('EPE 1109', 'Assessment in Learning 2', 3, '2ND', 3, 8),
  ('EPE 1113', 'Special Topics', 3, '2ND', 3, 8),
  ('ZFN 1102', 'Filipino sa Iba`t ibang Disiplina (FILDIS)', 3, '2ND', 3, 8),

  -- Fourth Year/First Semester
  ('EEN 4302', '(Elective) English for Specific Purposes', 3, '1ST', 4, 8),
  ('EPE 1111', 'Field Study 1', 3, '1ST', 4, 8),
  ('EPE 1112', 'Field Study 2', 3, '1ST', 4, 8),
  ('EPE 1114', 'Critical and Creative Thinking', 3, '1ST', 4, 8),
  ('ZFN 1103', 'Daulumat ng/sa Filipino (DALUMAFIL)', 3, '1ST', 4, 8),
  ('ZGE 1102', 'The Contemporary World', 3, '1ST', 4, 8),
  ('ZPD 1102', 'Effective Communication with Personality Development', 3, '1ST', 4, 8),
  -- Fourth Year/Second Semester
  ('EIC 4201', '*Education Integration 1, 2, 3', 5, '2ND', 4, 8),

  -- Bachelor of Secondary Education major in Mathematics (9)

  -- First Year/First Semester
  ('CCO 113', 'Introduction to Information Technology (with Computer Fundamentals)', 3, '1ST', 1, 9),
  ('PPE 111', 'Physical Education 1', 2, '1ST', 1, 9),
  ('ZEN 110', 'Integrated Study Skills for Freshmen English', 3, '1ST', 1, 9),
  ('ZFI 111', 'Sining ng Komunikasyon', 3, '1ST', 1, 9),
  ('ZHI 111', 'Philippine History and Public Service', 3, '1ST', 1, 9),
  ('ZHI 112', 'Life and Works of Rizal', 3, '1ST', 1, 9),
  ('ZHU 111', 'Art Appreciation', 3, '1ST', 1, 9),
  ('ZNS 111', 'The Physical Sciences', 3, '1ST', 1, 9),
  ('ZPY 111', 'General Psychology', 3, '1ST', 1, 9),
  -- First Year/Second Semester
  ('AMA 114', 'Plane Geometry', 3, '2ND', 1, 9),
  ('EPE 121', 'Developmental Reading 1', 3, '2ND', 1, 9),
  ('EPE 122', 'Intro to Curriculum Development', 3, '2ND', 1, 9),
  ('MLC 111', 'Literacy/Civic Welfare/Military Science 1', 3, '2ND', 1, 9),
  ('PPE 112', 'Physical Education 2', 2, '2ND', 1, 9),
  ('ZFI 112', 'Pagbasa at Pagsulat sa Iba`t-ibang Disiplina', 3, '2ND', 1, 9),
  ('ZMA 111', 'College Algebra', 3, '2ND', 1, 9),
  ('ZMA 114', 'Plane Trigonometry', 3, '2ND', 1, 9),
  ('ZNS 112', 'The Biological Sciences', 3, '2ND', 1, 9),

  -- Second Year/First Semester
  ('AMA 211', 'College Algebra 2', 3, '1ST', 2, 9),
  ('EMA 221', 'Analytic Geometry', 3, '1ST', 2, 9),
  ('EPE 211', 'Child and Adolescent Development', 3, '1ST', 2, 9),
  ('EPE 212', 'Facilitating Learning', 3, '1ST', 2, 9),
  ('EPE 213', 'Field Study 1 - Learners Development', 1, '1ST', 2, 9),
  ('EPE 214', 'Principles of Teaching 1', 3, '1ST', 2, 9),
  ('EPE 215', 'Social Dimensions of Education', 3, '1ST', 2, 9),
  ('MLC 112', 'Literacy/Civic Welfare/Military Science 2', 3, '1ST', 2, 9),
  ('PPE 113', 'Physical Education 3', 2, '1ST', 2, 9),
  ('ZEN 112', 'College Reading and Writing', 3, '1ST', 2, 9),
  -- Second Year/Second Semester
  ('AMA 221', 'Solid Geometry', 3, '2ND', 2, 9),
  ('AMA 311', 'Abstract Algebra', 3, '2ND', 2, 9),
  ('EMA 222', 'Instrumentation in Mathematics', 3, '2ND', 2, 9),
  ('EMA 224', 'Calculus 1', 3, '2ND', 2, 9),
  ('EPE 222', 'Educational Technology 1', 3, '2ND', 2, 9),
  ('EPE 223', 'Field Study 2 - Experiencing the Teaching-Learning Process', 1, '2ND', 2, 9),
  ('EPE 224', 'Principles of Teaching 2', 3, '2ND', 2, 9),
  ('PPE 114', 'Physical Education 4', 2, '2ND', 2, 9),
  ('ZLI 111', 'Literatures of the Philippines/Panitikan ng Pilipinas', 3, '2ND', 2, 9),
  ('ZST 111', 'Basic Statistics', 3, '2ND', 2, 9),

  -- Third Year/First Semester
  ('AMA 315', 'Theory of Numbers', 3, '1ST', 3, 9),
  ('AST 112', 'Introduction to Probability Theory', 3, '1ST', 3, 9),
  ('EMA 311', 'Calculus II', 3, '1ST', 3, 9),
  ('EPE 311', 'Assessment of Learning 1', 3, '1ST', 3, 9),
  ('EPE 312', 'Educational Technology 2', 3, '1ST', 3, 9),
  ('EPE 313', 'Field Study 3 - Technology in the Learning Environment', 1, '1ST', 3, 9),
  ('EPE 314', 'Special Topics 1', 1, '1ST', 3, 9),
  ('EPE 315', 'Special Topics 2', 1, '1ST', 3, 9),
  ('EPE 316', 'Special Topics 3', 1, '1ST', 3, 9),
  ('ZEN 114', 'Speech Communication', 3, '1ST', 3, 9),
  ('ZLI 112', 'Literatures of the World/Panitikan ng Mundo', 3, '1ST', 3, 9),
  ('ZPS 111', 'Politics and Governance (with Philippine Constitution)', 3, '1ST', 3, 9),
  -- Third Year/Second Semester
  ('AMA 322', 'Linear Algebra', 3, '2ND', 3, 9),
  ('BEC 111', 'Introduction to Economics with Land Reform and Taxation', 3, '2ND', 3, 9),
  ('EIC 111', 'Integrated Course 1', 1, '2ND', 3, 9),
  ('EMA 321', 'Mathematics Curriculum in the Secondary School', 3, '2ND', 3, 9),
  ('EMA 322', 'Mathematical Investigation and Modelling', 3, '2ND', 3, 9),
  ('EPE 321', 'Field Study 4 - Understanding Curriculum Development', 1, '2ND', 3, 9),
  ('ZFI 113', 'Kasanayan sa Malikhaing Pagpapahayag (Retorika)', 3, '2ND', 3, 9),
  ('ZPL 112', 'Introduction to Philosophy w/ Logic and Critical Thinking', 3, '2ND', 3, 9),
  ('ZSC 111', 'Society and Culture', 3, '2ND', 3, 9),
  ('ZST 112', 'Basic Statistics II', 3, '2ND', 3, 9),

  -- Fourth Year/First Semester
  ('AMA 314', 'College Geometry', 3, '1ST', 4, 9),
  ('AMA 326', 'History of Mathematics', 3, '1ST', 4, 9),
  ('EIC 112', 'Integrated Course 2', 2, '1ST', 4, 9),
  ('EMA 413', 'Seminar in Problem Solving in Mathematics', 3, '1ST', 4, 9),
  ('EMA 411', 'Action Research in Math Education', 3, '1ST', 4, 9),
  ('EMA 412', 'Seminar on Technology in Math', 3, '1ST', 4, 9),
  ('EPE 411', 'Assessment of Learning 2', 3, '1ST', 4, 9),
  ('EPE 412', 'Field Study 5 - Learning Assessment', 1, '1ST', 4, 9),
  ('EPE 413', 'Field Study 6 - On Becoming a Teacher', 1, '1ST', 4, 9),
  ('EPE 414', 'The Teaching Profession', 3, '1ST', 4, 9),
  ('ZPD 111', 'Personality Development', 1, '1ST', 4, 9),
  -- Fourth Year/Second Semester
  ('EIC 113', 'Integrated Course 3', 2, '2ND', 4, 9),
  ('EPE 421', 'Practicum', 6, '2ND', 4, 9);
