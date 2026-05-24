-- ============================================
-- TẠO DATABASE
-- ============================================
CREATE DATABASE QuanLyRapPhim;
GO

USE QuanLyRapPhim;
GO

-- ============================================
-- 1. BẢNG TÀI KHOẢN
-- ============================================
CREATE TABLE TaiKhoan (
    maTK        VARCHAR(10)  NOT NULL PRIMARY KEY,
    tenDangNhap VARCHAR(50)  NOT NULL,
    password    VARCHAR(50)  NOT NULL,
    vaiTro      NVARCHAR(50) NOT NULL
);
GO

INSERT INTO TaiKhoan (maTK, tenDangNhap, password, vaiTro)
VALUES
('ad01', 'admin',     '123', N'admin'),
('nv01', 'nhanvien',  '123', N'nhanVien');
GO

-- ============================================
-- 2. BẢNG NHÂN VIÊN
-- ============================================
CREATE TABLE NhanVien (
    maNhanVien  VARCHAR(10)   NOT NULL PRIMARY KEY,
    hoTen       NVARCHAR(100) NOT NULL,
    soDienThoai VARCHAR(10)   NOT NULL UNIQUE,
    gioiTinh    BIT           NOT NULL,  -- 1: Nam, 0: Nữ
    email       VARCHAR(100)  NOT NULL UNIQUE,
    chucVu      NVARCHAR(50)  NOT NULL,
    diaChi      NVARCHAR(255) NOT NULL,
    trangThai   NVARCHAR(30)  NOT NULL
);
GO

INSERT INTO NhanVien (maNhanVien, hoTen, soDienThoai, gioiTinh, email, chucVu, diaChi, trangThai)
VALUES
('TC0001', N'Nguyễn Văn An',  '0901234567', 1, 'an01@gmail.com',    N'Quản lý',   N'Cà Mau',         N'Đang làm'),
('TC0002', N'Trần Thị Bình',  '0912345678', 0, 'binh02@gmail.com',  N'Nhân viên', N'Bạc Liêu',       N'Đang làm'),
('TC0003', N'Lê Hoàng Nam',   '0987654321', 1, 'nam03@gmail.com',   N'Nhân viên', N'Cần Thơ',        N'Nghỉ'),
('TC0004', N'Phạm Ngọc Lan',  '0376543210', 0, 'lan04@gmail.com',   N'Nhân viên', N'Sóc Trăng',      N'Đang làm'),
('TC0005', N'Võ Minh Khang',  '0856789123', 1, 'khang05@gmail.com', N'Quản lý',   N'TP Hồ Chí Minh', N'Đang làm');
GO

-- ============================================
-- 3. BẢNG PHIM
-- ============================================
CREATE TABLE Phim (
    maPhim      VARCHAR(10)   NOT NULL PRIMARY KEY,
    tenPhim     NVARCHAR(100) NOT NULL,
    thoiLuong   INT           NOT NULL,
    theLoai     NVARCHAR(50)  NOT NULL,
    gioiHanTuoi NVARCHAR(10)  NOT NULL,
    ngonNgu     NVARCHAR(50)  NOT NULL
);
GO

INSERT INTO Phim (maPhim, tenPhim, thoiLuong, theLoai, gioiHanTuoi, ngonNgu)
VALUES
('P001', N'Avengers: Endgame', 181, N'Hành Động', N'T13', N'Phụ Đề'),
('P002', N'Frozen II',         103, N'Thiếu Nhi',  N'P',   N'Lồng Tiếng'),
('P003', N'Parasite',          132, N'Kinh Dị',    N'T18', N'Phụ Đề'),
('P004', N'Interstellar',      169, N'Khoa Học',   N'T13', N'Phụ Đề'),
('P005', N'Titanic',           194, N'Tình Cảm',   N'T13', N'Phụ Đề'),
('P006', N'John Wick 4',       169, N'Hành Động',  N'T18', N'Phụ Đề'),
('P007', N'Inside Out 2',       96, N'Thiếu Nhi',  N'P',   N'Lồng Tiếng'),
('P008', N'The Conjuring',     112, N'Kinh Dị',    N'T16', N'Phụ Đề'),
('P009', N'Your Name',         106, N'Tình Cảm',   N'K',   N'Phụ Đề'),
('P010', N'Dune: Part Two',    166, N'Khoa Học',   N'T13', N'Phụ Đề');
GO

-- ============================================
-- 4. BẢNG PHÒNG
-- ============================================
CREATE TABLE Phong (
    maPhong VARCHAR(10) NOT NULL PRIMARY KEY,
    sucChua INT         NOT NULL CHECK (sucChua > 0)
);
GO

INSERT INTO Phong (maPhong, sucChua)
VALUES
('P001', 80),
('P002', 100),
('P003', 120),
('P004', 90),
('P005', 150),
('P006', 110),
('P007', 95),
('P008', 130);
GO

-- ============================================
-- 5. BẢNG SUẤT CHIẾU
-- (phụ thuộc Phim + Phong)
-- ============================================
CREATE TABLE SuatChieu (
    maSuat    VARCHAR(10) NOT NULL PRIMARY KEY,
    maPhim    VARCHAR(10) NOT NULL,
    maPhong   VARCHAR(10) NOT NULL,
    ngayChieu DATE        NOT NULL,
    thoiGian  DATETIME2   NOT NULL,

    CONSTRAINT FK_SuatChieu_Phim  FOREIGN KEY (maPhim)  REFERENCES Phim(maPhim),
    CONSTRAINT FK_SuatChieu_Phong FOREIGN KEY (maPhong) REFERENCES Phong(maPhong)
);
GO

INSERT INTO SuatChieu (maSuat, maPhim, maPhong, ngayChieu, thoiGian)
VALUES
('SC001', 'P001', 'P001', '2026-04-29', '2026-04-29 09:00:00'),
('SC002', 'P001', 'P002', '2026-04-29', '2026-04-29 14:00:00'),
('SC003', 'P002', 'P003', '2026-04-29', '2026-04-29 19:30:00'),
('SC004', 'P003', 'P004', '2026-04-30', '2026-04-30 10:15:00'),
('SC005', 'P004', 'P005', '2026-04-30', '2026-04-30 16:45:00'),
('SC006', 'P005', 'P006', '2026-05-01', '2026-05-01 20:00:00');
GO

-- ============================================
-- 6. BẢNG GHẾ
-- trangThai: 0 = trống, 1 = đã đặt
-- loaiGhe:   0 = thường, 1 = VIP (D->H)
-- (phụ thuộc SuatChieu)
-- ============================================
CREATE TABLE Ghe (
    maGhe     VARCHAR(10) NOT NULL,
    loaiGhe   BIT         NOT NULL,
    trangThai BIT         NOT NULL DEFAULT 0,
    maSuat    VARCHAR(10) NOT NULL,

    CONSTRAINT PK_Ghe PRIMARY KEY (maGhe, maSuat),
    CONSTRAINT FK_Ghe_SuatChieu FOREIGN KEY (maSuat) REFERENCES SuatChieu(maSuat)
);
GO

-- Tạo 100 ghế cho SC001 -> SC006
DECLARE @i    INT = 1;
DECLARE @maSuat VARCHAR(10);

WHILE @i <= 6
BEGIN
    IF @i < 10
        SET @maSuat = 'SC00' + CAST(@i AS VARCHAR);
    ELSE
        SET @maSuat = 'SC0'  + CAST(@i AS VARCHAR);

    INSERT INTO Ghe (maGhe, loaiGhe, trangThai, maSuat) VALUES
    ('A1',0,0,@maSuat),('A2',0,0,@maSuat),('A3',0,0,@maSuat),('A4',0,0,@maSuat),('A5',0,0,@maSuat),
    ('A6',0,0,@maSuat),('A7',0,0,@maSuat),('A8',0,0,@maSuat),('A9',0,0,@maSuat),('A10',0,0,@maSuat),
    ('B1',0,0,@maSuat),('B2',0,0,@maSuat),('B3',0,0,@maSuat),('B4',0,0,@maSuat),('B5',0,0,@maSuat),
    ('B6',0,0,@maSuat),('B7',0,0,@maSuat),('B8',0,0,@maSuat),('B9',0,0,@maSuat),('B10',0,0,@maSuat),
    ('C1',0,0,@maSuat),('C2',0,0,@maSuat),('C3',0,0,@maSuat),('C4',0,0,@maSuat),('C5',0,0,@maSuat),
    ('C6',0,0,@maSuat),('C7',0,0,@maSuat),('C8',0,0,@maSuat),('C9',0,0,@maSuat),('C10',0,0,@maSuat),
    ('D1',1,0,@maSuat),('D2',1,0,@maSuat),('D3',1,0,@maSuat),('D4',1,0,@maSuat),('D5',1,0,@maSuat),
    ('D6',1,0,@maSuat),('D7',1,0,@maSuat),('D8',1,0,@maSuat),('D9',1,0,@maSuat),('D10',1,0,@maSuat),
    ('E1',1,0,@maSuat),('E2',1,0,@maSuat),('E3',1,0,@maSuat),('E4',1,0,@maSuat),('E5',1,0,@maSuat),
    ('E6',1,0,@maSuat),('E7',1,0,@maSuat),('E8',1,0,@maSuat),('E9',1,0,@maSuat),('E10',1,0,@maSuat),
    ('F1',1,0,@maSuat),('F2',1,0,@maSuat),('F3',1,0,@maSuat),('F4',1,0,@maSuat),('F5',1,0,@maSuat),
    ('F6',1,0,@maSuat),('F7',1,0,@maSuat),('F8',1,0,@maSuat),('F9',1,0,@maSuat),('F10',1,0,@maSuat),
    ('G1',1,0,@maSuat),('G2',1,0,@maSuat),('G3',1,0,@maSuat),('G4',1,0,@maSuat),('G5',1,0,@maSuat),
    ('G6',1,0,@maSuat),('G7',1,0,@maSuat),('G8',1,0,@maSuat),('G9',1,0,@maSuat),('G10',1,0,@maSuat),
    ('H1',1,0,@maSuat),('H2',1,0,@maSuat),('H3',1,0,@maSuat),('H4',1,0,@maSuat),('H5',1,0,@maSuat),
    ('H6',1,0,@maSuat),('H7',1,0,@maSuat),('H8',1,0,@maSuat),('H9',1,0,@maSuat),('H10',1,0,@maSuat),
    ('I1',0,0,@maSuat),('I2',0,0,@maSuat),('I3',0,0,@maSuat),('I4',0,0,@maSuat),('I5',0,0,@maSuat),
    ('I6',0,0,@maSuat),('I7',0,0,@maSuat),('I8',0,0,@maSuat),('I9',0,0,@maSuat),('I10',0,0,@maSuat),
    ('J1',0,0,@maSuat),('J2',0,0,@maSuat),('J3',0,0,@maSuat),('J4',0,0,@maSuat),('J5',0,0,@maSuat),
    ('J6',0,0,@maSuat),('J7',0,0,@maSuat),('J8',0,0,@maSuat),('J9',0,0,@maSuat),('J10',0,0,@maSuat);

    SET @i = @i + 1;
END
GO

-- ============================================
-- 7. BẢNG VÉ
-- (phụ thuộc NhanVien + Ghe)
-- ============================================
CREATE TABLE Ve (
    maVe       VARCHAR(20)    NOT NULL PRIMARY KEY,
    giaVe      DECIMAL(10,2)  NOT NULL,
    ngayDat    DATE           NOT NULL,
    trangThai  NVARCHAR(30)   NOT NULL,
    maNhanVien VARCHAR(10)    NOT NULL,
    maGhe      VARCHAR(10)    NOT NULL,
    maSuat     VARCHAR(10)    NOT NULL,

    CONSTRAINT FK_Ve_NhanVien FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien),
    CONSTRAINT FK_Ve_Ghe      FOREIGN KEY (maGhe, maSuat) REFERENCES Ghe(maGhe, maSuat)
);
GO

INSERT INTO Ve (maVe, giaVe, ngayDat, trangThai, maNhanVien, maGhe, maSuat)
VALUES
('VE001', 50000, '2026-04-28', N'DA_DAT', 'TC0001', 'A1', 'SC001'),
('VE002', 70000, '2026-04-28', N'DA_DAT', 'TC0002', 'D5', 'SC001'),
('VE003', 50000, '2026-04-28', N'DA_DAT', 'TC0003', 'B2', 'SC002'),
('VE004', 70000, '2026-04-27', N'DA_HUY', 'TC0004', 'F7', 'SC002'),
('VE005', 50000, '2026-04-27', N'DA_DAT', 'TC0005', 'C3', 'SC001'),
('VE006', 50000, '2026-04-29', N'DA_DAT', 'TC0001', 'A1', 'SC003'),
('VE007', 50000, '2026-04-29', N'DA_DAT', 'TC0001', 'A2', 'SC003');
GO

-- ============================================
-- 8. SYNC TRẠNG THÁI GHẾ THEO VÉ DA_DAT
-- Chạy sau khi insert Ve để đồng bộ trangThai
-- ============================================
UPDATE Ghe
SET trangThai = 1
WHERE EXISTS (
    SELECT 1 FROM Ve
    WHERE Ve.maGhe    = Ghe.maGhe
      AND Ve.maSuat   = Ghe.maSuat
      AND Ve.trangThai = N'DA_DAT'
);
GO