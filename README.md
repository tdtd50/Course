项目说明
本项目是一个基于Spring Boot开发的校园选课与教学资源管理平台，采用单体架构设计。系统实现了完整的课程管理、学生管理和选课管理功能，包含课程容量限制、重复选课检查、学生验证等核心业务逻辑。

主要功能：
学生信息管理（增删改查）
课程信息管理（增删改查）
选课管理（选课、退课、查询）
业务规则验证（容量限制、重复检查等）

如何运行项目
环境要求
JDK 17+
Maven 3.6+
Spring Boot 3.3.5

运行步骤
克隆项目

bash
git clone <项目地址>
cd course-system
编译项目

bash
mvn clean compile
启动应用

bash
mvn spring-boot:run
验证启动

text
控制台显示 "Tomcat started on port(s): 8080" 表示启动成功
应用访问地址: http://localhost:8080
IDE运行（IntelliJ IDEA）
导入Maven项目

打开 CourseApplication.java

点击运行main方法

API接口列表
学生管理接口
方法	路径	描述	状态码
POST	/api/students	创建学生	201
GET	/api/students	查询所有学生	200
GET	/api/students/{id}	根据ID查询学生	200/404
PUT	/api/students/{id}	更新学生信息	200/404
DELETE	/api/students/{id}	删除学生	204/404
课程管理接口
方法	路径	描述	状态码
POST	/api/courses	创建课程	201
GET	/api/courses	查询所有课程	200
GET	/api/courses/{id}	根据ID查询课程	200/404
PUT	/api/courses/{id}	更新课程信息	200/404
DELETE	/api/courses/{id}	删除课程	204/404
选课管理接口
方法	路径	描述	状态码
POST	/api/enrollments	学生选课	201
DELETE	/api/enrollments/{id}	学生退课	204
GET	/api/enrollments	查询所有选课记录	200
GET	/api/enrollments/course/{courseId}	按课程查询选课	200
GET	/api/enrollments/student/{studentId}	按学生查询选课	200
测试说明
测试文件
项目包含完整的HTTP测试文件 test-api.http，涵盖所有功能测试场景。

测试场景
场景1：课程管理流程
创建3门不同课程
查询验证课程数量
更新课程信息
删除课程

场景2：选课业务流程
创建容量为2的课程
测试选课成功和失败情况
验证容量限制和重复选课检查
检查enrolled字段自动更新

场景3：学生管理流程
创建3个不同学号的学生
测试学生CRUD操作
验证删除保护机制（有选课记录无法删除）

场景4：错误处理
查询不存在的资源
创建时缺少必填字段
使用重复学号/课程代码
无效邮箱格式验证

测试方法
使用Apifox测试
导入提供的HTTP请求
按顺序执行测试场景
记录响应结果
使用IntelliJ IDEA HTTP Client
打开 test-api.http 文件
点击每个请求旁的运行按钮
观察响应状态和数据

预期测试结果
所有核心功能正常运行
业务规则正确执行
错误处理恰当返回
HTTP状态码使用正确

