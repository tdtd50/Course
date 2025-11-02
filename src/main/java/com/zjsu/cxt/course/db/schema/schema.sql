-- 创建课程表
CREATE TABLE IF NOT EXISTS courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    capacity INT NOT NULL,
    enrolled_count INT DEFAULT 0,

    -- 讲师信息 (嵌入式对象)
    instructor_id VARCHAR(20),
    instructor_name VARCHAR(50),
    instructor_email VARCHAR(100),
    instructor_department VARCHAR(50),

    -- 排课信息 (嵌入式对象)
    schedule_day VARCHAR(10),
    start_time VARCHAR(10),
    end_time VARCHAR(10),
    classroom VARCHAR(50),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_course_code (course_code),
    INDEX idx_instructor (instructor_id),
    INDEX idx_title (title)
);

-- 创建学生表
CREATE TABLE IF NOT EXISTS students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    major VARCHAR(50),
    grade VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_student_id (student_id),
    INDEX idx_email (email),
    INDEX idx_major (major),
    INDEX idx_grade (grade)
);

-- 创建选课记录表
CREATE TABLE IF NOT EXISTS enrollments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,

    UNIQUE KEY unique_course_student (course_id, student_id),
    INDEX idx_course_status (course_id, status),
    INDEX idx_student_status (student_id, status),
    INDEX idx_status (status)
);