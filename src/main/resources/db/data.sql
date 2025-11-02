-- 插入示例课程数据
INSERT INTO courses (course_code, title, description, capacity, instructor_id, instructor_name, instructor_email, instructor_department, schedule_day, start_time, end_time, classroom) VALUES
('CS101', 'Java程序设计', '面向对象编程基础', 50, 'T001', '张教授', 'zhang@zjgsu.edu.cn', '计算机科学', 'Monday', '08:00', '10:00', 'A101'),
('CS201', '数据结构与算法', '线性表、树、图等数据结构', 40, 'T002', '李教授', 'li@zjgsu.edu.cn', '计算机科学', 'Tuesday', '10:00', '12:00', 'A102');

-- 插入示例学生数据
INSERT INTO students (student_id, name, email, phone, major, grade) VALUES
('S2023001', '张三', 'zhangsan@student.zjgsu.edu.cn', '13800138001', '计算机科学', '2023'),
('S2023002', '李四', 'lisi@student.zjgsu.edu.cn', '13800138002', '计算机科学', '2023');

-- 插入示例选课数据
INSERT INTO enrollments (course_id, student_id, status) VALUES
(1, 1, 'ACTIVE'),
(1, 2, 'ACTIVE');