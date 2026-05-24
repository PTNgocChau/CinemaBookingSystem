# Cinema Booking System | Hệ Thống Đặt Vé Xem Phim

A desktop-based movie ticket booking and management system developed using Java Swing (JFrame) and SQL Server.  

Hệ thống đặt vé xem phim dạng desktop được phát triển bằng Java Swing (JFrame) kết hợp với SQL Server.

---

## Overview | Tổng Quan

This project is an event-driven desktop application that allows users to manage movie ticket booking operations in a local environment. The system provides features for managing movies, showtimes, customers, ticket reservations, and payments through an interactive graphical user interface.

Đây là ứng dụng desktop hướng sự kiện cho phép quản lý các hoạt động đặt vé xem phim trong môi trường local. Hệ thống hỗ trợ quản lý phim, suất chiếu, khách hàng, đặt vé, thanh toán, và kết hợp thống kê thông qua giao diện đồ họa trực quan.

---

## Technologies Used | Công Nghệ Sử Dụng

- Java
- Java Swing (JFrame)
- JDBC
- SQL Server
- iText PDF

---

## Features | Chức Năng

- User authentication and role management  
  Đăng nhập và phân quyền người dùng

- Movie management  
  Quản lý phim

- Showtime scheduling  
  Quản lý suất chiếu

- Seat selection and ticket booking  
  Chọn ghế và đặt vé

- Invoice and ticket printing  
  In hóa đơn và vé xem phim

- Revenue statistics  
  Thống kê doanh thu

---

## System Architecture | Cấu Trúc Hệ Thống

```text
CinemaBookingSystem/
│
├── src/
│   ├── connectDB/
│   ├── dao/
│   ├── entity/
│   └── gui/
│
├── data/
│   └── ALL.sql
│
│
├── IN/
│   └── VeXemPhim.pdf
│
├── lib/
│
├── README.md
└── .gitignore
