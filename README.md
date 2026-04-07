# 🚉 Railway Test Automation Framework

![Automation Status](https://img.shields.io/badge/Status-Active-success?style=for-the-badge)
![Tech Stack](https://img.shields.io/badge/Stack-Java_25_|_Selenium_4_|_TestNG-blue?style=for-the-badge)
![Design](https://img.shields.io/badge/Design-Neobrutalism-yellow?style=for-the-badge)

Dự án kiểm thử tự động chuyên nghiệp dành cho hệ thống **Railway**, được tối ưu hóa với công nghệ mới nhất và hệ thống
báo cáo.

---

## 🛠 Hướng dẫn vận hành

Dự án sử dụng Maven là công cụ quản lý build chính. Dưới đây là các lệnh bạn sẽ sử dụng thường xuyên:

### 1. Chạy toàn bộ Test Suite

Lệnh này sẽ dọn dẹp dự án, biên dịch lại code và chạy tất cả các testcase đã được cấu hình:

```bash
mvn clean test
```

### 2. Chạy lẻ theo nhu cầu (Selective Execution)

Tận dụng sức mạnh của Maven Surefire để chạy nhanh các mục tiêu cụ thể:

* **Chạy toàn bộ một Class Test:**
  ```bash
  mvn test -Dtest=LoginTests
  ```
* **Chạy duy nhất một Test Case (Method):**
  ```bash
  mvn test -Dtest=LoginTests#TC01
  ```
* **Chạy nhiều Class cùng lúc:**
  ```bash
  mvn test -Dtest=LoginTests,PasswordTests
  ```

---

## 📂 Cấu trúc dự án (Project Structure)

```text
├── src/test/java/
│   ├── common/         # Các hằng số và cấu hình dùng chung
│   ├── pageobjects/    # Chứa các lớp Page Objects (POM)
│   └── testcases/      # Chứa các file Test Scripts
│       ├── BaseTest.java      # Khởi tạo Driver và Setup/Teardown
│       └── TestListener.java  # Điều khiển Báo cáo
├── test-output/        # Nơi lưu trữ file báo cáo HTML và Screenshot
└── pom.xml             # Quản lý thư viện và cấu hình Maven
```

---

## 📊 Báo cáo kiểm thử (Reports)

Báo cáo sẽ được sinh ra tự động trong thư mục `test-output/` sau mỗi lần chạy.

* **Tên file**: `ExtentReport_yyyy.MM.dd.HH.mm.ss.html`
* **Ảnh lỗi**: Nhấp chuột vào ảnh lỗi để phóng to quan sát chi tiết.

---
© 2026 **NHOM02**