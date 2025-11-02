-- 插入示例课程数据
INSERT INTO courses (course_code, title, description, capacity, instructor_id, instructor_name, instructor_email, instructor_department, schedule_day, start_time, end_time, classroom) VALUES
('CS101', 'Java程序设计', '面向对象编程基础，Java语言特性，集合框架等', 50, 'T001', '张教授', 'zhang@zjgsu.edu.cn', '计算机科学', 'Monday', '08:00', '10:00', 'A101'),
('CS201', '数据结构与算法', '线性表、树、图等数据结构，排序和查找算法', 40, 'T002', '李教授', 'li@zjgsu.edu.cn', '计算机科学', 'Tuesday', '10:00', '12:00', 'A102'),
('MA101', '高等数学', '微积分、极限、导数、积分等数学基础', 60, 'T003', '王教授', 'wang@zjgsu.edu.cn', '数学系', 'Wednesday', '14:00', '16:00', 'B201'),
('EN201', '大学英语', '英语听说读写综合训练', 45, 'T004', '陈教授', 'chen@zjgsu.edu.cn', '外语系', 'Thursday', '16:00', '18:00', 'C301'),
('CS301', '数据库系统', '关系数据库、SQL、事务处理、数据库设计', 35, 'T005', '赵教授', 'zhao@zjgsu.edu.cn', '计算机科学', 'Friday', '08:00', '10:00', 'A103');

-- 插入示例学生数据
INSERT INTO students (student_id, name, email, phone, major, grade) VALUES
('S2023001', '张三', 'zhangsan@student.zjgsu.edu.cn', '13800138001', '计算机科学', '2023'),
('S2023002', '李四', 'lisi@student.zjgsu.edu.cn', '13800138002', '计算机科学', '2023'),
('S2023003', '王五', 'wangwu@student.zjgsu.edu.cn', '13800138003', '软件工程', '2023'),
('S2022001', '赵六', 'zhaoliu@student.zjgsu.edu.cn', '13800138004', '计算机科学', '2022'),
('S2022002', '钱七', 'qianqi@student.zjgsu.edu.cn', '13800138005', '数学', '2022'),
('S2024001', '孙八', 'sunba@student.zjgsu.edu.cn', '13800138006', '软件工程', '2024');

-- 插入示例选课数据
INSERT INTO enrollments (course_id, student_id, status) VALUES
(1, 1, 'ACTIVE'),
(1, 2, 'ACTIVE'),
(2, 1, 'ACTIVE'),
(2, 3, 'ACTIVE'),
(3, 4, 'ACTIVE'),
(3, 5, 'ACTIVE'),
(4, 6, 'ACTIVE'),
(5, 2, 'ACTIVE'),
(5, 4, 'ACTIVE');