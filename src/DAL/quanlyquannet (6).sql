-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 09, 2025 lúc 04:20 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlyquannet`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdonthucan`
--

CREATE TABLE `chitietdonthucan` (
  `MaDH` int(11) NOT NULL,
  `MaThucAn` int(11) NOT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `DonGia` decimal(12,2) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietdonthucan`
--

INSERT INTO `chitietdonthucan` (`MaDH`, `MaThucAn`, `SoLuong`, `DonGia`, `created_at`) VALUES
(1, 1, 5, 10000.00, '2025-04-18 21:30:42'),
(2, 2, 7, 15000.00, '2025-04-18 21:30:42'),
(3, 3, 10, 20000.00, '2025-04-18 21:30:42'),
(4, 4, 6, 12000.00, '2025-04-18 21:30:42'),
(5, 5, 8, 18000.00, '2025-04-18 21:30:42');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitiethoadon`
--

CREATE TABLE `chitiethoadon` (
  `MaHD` int(11) NOT NULL,
  `MaHang` int(11) NOT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `DonGia` decimal(12,2) DEFAULT NULL,
  `ThanhTien` decimal(12,2) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `chitiethoadon`
--

INSERT INTO `chitiethoadon` (`MaHD`, `MaHang`, `SoLuong`, `DonGia`, `ThanhTien`, `created_at`) VALUES
(1, 1, 1, 50000.00, 50000.00, '2025-04-18 21:20:01'),
(2, 2, 2, 40000.00, 80000.00, '2025-04-18 21:20:01'),
(3, 3, 3, 40000.00, 120000.00, '2025-04-18 21:20:01'),
(4, 4, 1, 65000.00, 65000.00, '2025-04-18 21:20:01'),
(5, 5, 2, 45000.00, 90000.00, '2025-04-18 21:20:01');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietphieunhap`
--

CREATE TABLE `chitietphieunhap` (
  `MaPN` int(11) NOT NULL,
  `MaHang` int(11) NOT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `DonGia` decimal(12,2) DEFAULT NULL,
  `ThanhTien` decimal(12,2) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietphieunhap`
--

INSERT INTO `chitietphieunhap` (`MaPN`, `MaHang`, `SoLuong`, `DonGia`, `ThanhTien`, `created_at`) VALUES
(1, 1, 10, 200000.00, 2000000.00, '2025-04-18 21:30:42'),
(2, 2, 15, 300000.00, 4500000.00, '2025-04-18 21:30:42'),
(3, 3, 20, 350000.00, 7000000.00, '2025-04-18 21:30:42'),
(4, 4, 12, 400000.00, 4800000.00, '2025-04-18 21:30:42'),
(5, 5, 18, 500000.00, 9000000.00, '2025-04-18 21:30:42');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhangthucan`
--

CREATE TABLE `donhangthucan` (
  `MaDH` int(11) NOT NULL,
  `MaKH` int(11) NOT NULL,
  `MaMay` int(11) NOT NULL,
  `NgayDat` datetime DEFAULT NULL,
  `TongTien` decimal(12,2) DEFAULT NULL,
  `TrangThai` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `MaNV` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `donhangthucan`
--

INSERT INTO `donhangthucan` (`MaDH`, `MaKH`, `MaMay`, `NgayDat`, `TongTien`, `TrangThai`, `created_at`, `MaNV`) VALUES
(1, 1, 1, '2025-04-10 00:00:00', 50000.00, 1, '2025-04-18 21:20:01', 1),
(2, 2, 2, '2025-04-11 00:00:00', 80000.00, 2, '2025-04-18 21:20:01', 4),
(3, 3, 3, '2025-04-12 00:00:00', 120000.00, 3, '2025-04-18 21:20:01', 5),
(4, 4, 4, '2025-04-13 00:00:00', 65000.00, 4, '2025-04-18 21:20:01', 3),
(5, 5, 5, '2025-04-14 00:00:00', 90000.00, 1, '2025-04-18 21:20:01', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
--

CREATE TABLE `hoadon` (
  `MaHD` int(11) NOT NULL,
  `MaKH` int(11) NOT NULL,
  `LoaiHD` int(11) DEFAULT NULL,
  `RefID` int(11) NOT NULL,
  `NgayLap` datetime DEFAULT NULL,
  `TongTien` decimal(12,2) DEFAULT NULL,
  `PhuongThucThanhToan` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `MaNV` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `hoadon`
--

INSERT INTO `hoadon` (`MaHD`, `MaKH`, `LoaiHD`, `RefID`, `NgayLap`, `TongTien`, `PhuongThucThanhToan`, `created_at`, `MaNV`) VALUES
(1, 1, 1, 1, '2025-04-10 00:00:00', 50000.00, 1, '2025-04-18 21:20:01', 3),
(2, 2, 2, 2, '2025-04-11 00:00:00', 80000.00, 2, '2025-04-18 21:20:01', 4),
(3, 3, 1, 3, '2025-04-12 00:00:00', 120000.00, 1, '2025-04-18 21:20:01', 3),
(4, 4, 2, 4, '2025-04-13 00:00:00', 65000.00, 2, '2025-04-18 21:20:01', 2),
(5, 5, 1, 5, '2025-04-14 00:00:00', 90000.00, 1, '2025-04-18 21:20:01', 5);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--
CREATE TABLE khachhang (
    makh int(255) AUTO_INCREMENT PRIMARY KEY,
    tenkh VARCHAR(255) NOT NULL,
    matkhau VARCHAR(255) NOT NULL,
    cccd VARCHAR(255) NOT NULL,
    sodienthoai INT (10) NOT NULL,
    ngaysinh DATE,
    ngaydangky DATE DEFAULT (CURRENT_DATE),
    sogio VARCHAR(20) NOT NULL,
    sotiennaptong DECIMAL(15,3),
    sodu DECIMAL(15,3),
    trangThai int(1)
);
INSERT INTO khachhang (tenkh, matkhau, cccd, sodienthoai, ngaysinh, ngaydangky, sogio, sotiennaptong, sodu, trangthai)
VALUES
('Nguyễn Hữu Cảnh', '12345678', '012345678901', '0912345678', '2000-05-10', CURDATE(), 5, 100000.000, 20000.000, 1),
('Bùi Thị Liên',  'abcdefgh', '011111111111', '0987654321', '1995-12-20', CURDATE(), 3, 50000.000, 10000.000, 1),
('Lê Văn Nhã',    'matkhau12', '022222222222', '0909090909', '1990-07-15', CURDATE(), 10, 150000.000, 75000.000, 1),
('Phạm Tiến Dũng', 'pass1234', '033333333333', '0911111111', '1999-01-01', CURDATE(), 0, 0.000, 0.000, 1),
('Lê Văn Lục',     'hoahoaa1', '044444444444', '0933333333', '1988-03-10', CURDATE(), 1, 20000.000, 10000.000, 1),
('Nguyễn Thị Loan',  'password9', '055555555555', '0901234567', '1992-08-25', CURDATE(), 7, 300000.000, 150000.000, 1),
('Mẫn Nhiên',  'abcd1234', '066666666666', '0976543210', '2001-04-14', CURDATE(), 2, 100000.000, 30000.000, 1),
('Phan Kim Tiền',     'linhpass8', '077777777777', '0981111222', '1997-07-07', CURDATE(), 0, 0.000, 0.000, 1),
('Nguyễn Thị Lý',    'ngopass99', '088888888888', '0922233445', '1985-12-12', CURDATE(), 12, 500000.000, 100000.000, 1),
('Bùi Quang MinhMinh', 'tran123456', '099999999999', '0912233445', '1994-09-09', CURDATE(), 4, 250000.000, 50000.000, 1);
-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `kho`
--

CREATE TABLE `kho` (
  `MaKho` int(11) NOT NULL,
  `TenKho` varchar(255) DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `SucChua` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `kho`
--

INSERT INTO `kho` (`MaKho`, `TenKho`, `DiaChi`, `SucChua`, `created_at`) VALUES
(1, 'Kho A', 'Số 10, Đường ABC', 50, '2025-04-18 21:20:01'),
(2, 'Kho B', 'Số 20, Đường XYZ', 30, '2025-04-18 21:20:01'),
(3, 'Kho C', 'Số 30, Đường 123', 70, '2025-04-18 21:20:01'),
(4, 'Kho D', 'Số 40, Đường DEF', 60, '2025-04-18 21:20:01'),
(5, 'Kho E', 'Số 50, Đường GHI', 80, '2025-04-18 21:20:01'),
(6, 'Kho A', 'Hà Nội', 100, '2025-04-18 21:22:02'),
(7, 'Kho B', 'TP Hồ Chí Minh', 150, '2025-04-18 21:22:02'),
(8, 'Kho C', 'Đà Nẵng', 200, '2025-04-18 21:22:02'),
(9, 'Kho D', 'Cần Thơ', 50, '2025-04-18 21:22:02'),
(10, 'Kho E', 'Bình Dương', 120, '2025-04-18 21:22:02');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khomaytinh`
--

CREATE TABLE `khomaytinh` (
  `MaKho` int(11) NOT NULL,
  `MaMay` int(11) NOT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khomaytinh`
--

INSERT INTO `khomaytinh` (`MaKho`, `MaMay`, `SoLuong`, `created_at`) VALUES
(1, 1, 10, '2025-04-18 21:20:01'),
(2, 2, 5, '2025-04-18 21:20:01'),
(3, 3, 8, '2025-04-18 21:20:01'),
(4, 4, 12, '2025-04-18 21:20:01'),
(5, 5, 15, '2025-04-18 21:20:01');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khothucan`
--

CREATE TABLE `khothucan` (
  `MaKho` int(11) NOT NULL,
  `MaThucAn` int(11) NOT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khothucan`
--

INSERT INTO `khothucan` (`MaKho`, `MaThucAn`, `SoLuong`, `created_at`) VALUES
(1, 1, 50, '2025-04-18 21:22:02'),
(1, 2, 100, '2025-04-18 21:22:02'),
(2, 3, 150, '2025-04-18 21:22:02'),
(3, 4, 80, '2025-04-18 21:22:02'),
(4, 5, 120, '2025-04-18 21:22:02');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `maytinh`
--

CREATE TABLE `maytinh` (
  `MaMay` int(11) NOT NULL,
  `TenMay` varchar(255) DEFAULT NULL,
  `TrangThai` int(11) DEFAULT NULL,
  `ViTri` varchar(255) DEFAULT NULL,
  `GiaThue` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `maytinh`
--

INSERT INTO `maytinh` (`MaMay`, `TenMay`, `TrangThai`, `ViTri`, `GiaThue`, `created_at`) VALUES
(1, 'Máy tính A1', 2, 'Khu 1', 50000, '2025-04-18 21:20:01'),
(2, 'Máy tính B2', 2, 'Khu 2', 40000, '2025-04-18 21:20:01'),
(3, 'Máy tính C3', 3, 'Khu 3', 60000, '2025-04-18 21:20:01'),
(4, 'Máy tính D4', 2, 'Khu 4', 55000, '2025-04-18 21:20:01'),
(5, 'Máy tính E5', 1, 'Khu 5', 45000, '2025-04-18 21:20:01');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `MaNCC` int(11) NOT NULL,
  `TenNCC` varchar(100) NOT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhacungcap`
--

INSERT INTO `nhacungcap` (`MaNCC`, `TenNCC`, `DiaChi`, `SoDienThoai`, `Email`, `created_at`) VALUES
(1, 'Công ty A', 'Hà Nội', '0101010101', 'ncca@gmail.com', '2025-04-18 21:22:02'),
(2, 'Công ty B', 'TP Hồ Chí Minh', '0202020202', 'nccb@gmail.com', '2025-04-18 21:22:02'),
(3, 'Công ty C', 'Đà Nẵng', '0303030303', 'nccc@gmail.com', '2025-04-18 21:22:02'),
(4, 'Công ty D', 'Cần Thơ', '0404040404', 'nccd@gmail.com', '2025-04-18 21:22:02'),
(5, 'Công ty E', 'Bình Dương', '0505050505', 'ncce@gmail.com', '2025-04-18 21:22:02');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE nhanvien (
    MaNV int(255) AUTO_INCREMENT PRIMARY KEY,
    TenNV VARCHAR(255) NOT NULL,
    MatKhau VARCHAR(255) NOT NULL,
    CCCD VARCHAR(50) NOT NULL,
    SoDT INT(10) NOT NULL,
    NgaySinh DATE,
    NgayDangKy DATE NOT NULL,
    Username VARCHAR(255) NOT NULL,
    Role VARCHAR(50) NOT NULL,
    ChucVu VARCHAR(50) NOT NULL,
    TrangThai int(1)
);
INSERT INTO nhanvien 
(TenNV, MatKhau, CCCD, SoDT, NgaySinh, NgayDangKy, Username, Role, ChucVu, TrangThai)
VALUES
('Nguyễn Văn An', '12345678', '012345678901', 912345678, '1990-05-10', CURDATE(), 'nguyenvana', 'Tiếp tân', 'Nhân viên', 1),
('Trần Thị Bích', 'abcxyz88', '011111111111', 987654321, '1995-12-20', CURDATE(), 'tranthib', 'Phục vụ', 'Nhân viên', 1),
('Lê Văn Cường', 'lepass12', '022222222222', 909090909, '1992-07-15', CURDATE(), 'levanc', 'Xuất nhập kho', 'Nhân viên', 1),
('Phạm Quốc Dũng', 'pqdpwd99', '033333333333', 911111111, '1988-01-01', CURDATE(), 'phamqd', 'Phục vụ', 'Quản lý', 1),
('Đỗ Thị Hạnh', 'hanhdo88', '044444444444', 922222222, '1998-03-10', CURDATE(), 'dothihanh', 'Tiếp tân', 'Nhân viên', 1),
('Trịnh Hữu Nam', 'nam123456', '055555555555', 971234567, '1985-08-25', CURDATE(), 'trinhhn', 'Xuất nhập kho', 'Nhân viên', 1),
('Nguyễn Thị Yến', 'yenpass88', '066666666666', 966666666, '1996-02-20', CURDATE(), 'nguyenty', 'Phục vụ', 'Admin', 1),
('Vũ Minh Tuấn', 'tuanvm123', '077777777777', 933333333, '1999-01-01', CURDATE(), 'vuminhtuan', 'Tiếp tân', 'Nhân viên', 1),
('Lê Thị Thảo', 'thaole98', '088888888888', 928888888, '1997-10-05', CURDATE(), 'lethithao', 'Xuất nhập kho', 'Quản lý', 1),
('Ngô Hữu Đức', 'ngohd321', '099999999999', 908888888, '1982-12-12', CURDATE(), 'ngohd', 'Phục vụ', 'Admin', 1);


-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieunhap`
--

CREATE TABLE `phieunhap` (
  `MaPN` int(11) NOT NULL,
  `MaNCC` int(11) NOT NULL,
  `NgayNhap` datetime DEFAULT NULL,
  `NhanVienNhap` varchar(100) DEFAULT NULL,
  `TongTien` decimal(12,2) DEFAULT NULL,
  `LoaiHang` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieunhap`
--

INSERT INTO `phieunhap` (`MaPN`, `MaNCC`, `NgayNhap`, `NhanVienNhap`, `TongTien`, `LoaiHang`, `created_at`) VALUES
(1, 1, '2025-04-01 00:00:00', '1', 5000000.00, 1, '2025-04-18 21:22:02'),
(2, 2, '2025-04-02 00:00:00', '2', 6000000.00, 1, '2025-04-18 21:22:02'),
(3, 3, '2025-04-03 00:00:00', '3', 7000000.00, 2, '2025-04-18 21:22:02'),
(4, 4, '2025-04-04 00:00:00', '4', 5500000.00, 2, '2025-04-18 21:22:02'),
(5, 5, '2025-04-05 00:00:00', '5', 8000000.00, 1, '2025-04-18 21:22:02');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sudungmay`
--

CREATE TABLE `sudungmay` (
  `MaSuDung` int(11) NOT NULL,
  `MaNV` int(11) NOT NULL,
  `MaKH` int(11) NOT NULL,
  `MaMay` int(11) NOT NULL,
  `ThoiGianBatDau` datetime DEFAULT NULL,
  `ThoiGianKetThuc` datetime DEFAULT NULL,
  `TongThoiGian` decimal(10,2) NOT NULL,
  `ChiPhi` decimal(12,2) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `trangthai` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sudungmay`
--

INSERT INTO `sudungmay` (`MaSuDung`, `MaNV`, `MaKH`, `MaMay`, `ThoiGianBatDau`, `ThoiGianKetThuc`, `TongThoiGian`, `ChiPhi`, `created_at`, `trangthai`) VALUES
(49, 1, 5, 5, '2025-04-19 21:45:48', '2025-04-19 22:05:48', 0.33, 15000.00, '2025-04-19 21:45:48', 1),
(50, 1, 2, 2, '2025-04-19 21:46:20', '2025-04-19 21:52:44', 0.11, 4266.67, '2025-04-19 21:46:20', 0),
(51, 1, 2, 2, '2025-04-19 21:52:50', '2025-04-19 21:53:17', 0.01, 300.00, '2025-04-19 21:52:50', 0),
(53, 1, 4, 2, '2025-04-19 21:53:26', '2025-04-19 22:05:44', 0.21, 8200.00, '2025-04-19 21:53:26', 0),
(54, 1, 1, 1, '2025-04-19 21:55:59', '2025-04-19 21:56:01', 0.00, 27.78, '2025-04-19 21:55:59', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `MaNV` int(11) NOT NULL,
  `TenDangNhap` varchar(100) DEFAULT NULL,
  `MatKhau` varchar(100) DEFAULT NULL,
  `NhomQuyen` varchar(50) DEFAULT NULL,
  `TrangThai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`MaNV`, `TenDangNhap`, `MatKhau`, `NhomQuyen`, `TrangThai`) VALUES
(1, 'A', '1', 'Quản lý thức ăn', 1),
(2, 'adminB', 'password234', 'Quản lý hệ thống', 1),
(3, 'adminC', 'password345', 'Quản lý phần mềm', 2),
(4, 'adminD', 'password456', 'Quản lý máy tính', 1),
(5, 'adminE', 'password567', 'Quản lý phần mềm', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thucan`
--

CREATE TABLE `thucan` (
  `MaThucAn` int(11) NOT NULL,
  `TenThucAn` varchar(255) DEFAULT NULL,
  `DonVi` varchar(50) DEFAULT NULL,
  `DonGia` decimal(12,2) DEFAULT NULL,
  `HanSuDung` date DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `thucan`
--

INSERT INTO `thucan` (`MaThucAn`, `TenThucAn`, `DonVi`, `DonGia`, `HanSuDung`, `created_at`) VALUES
(1, 'Cơm chiên', 'Phần', 25000.00, '2025-12-31', '2025-04-18 21:20:01'),
(2, 'Bánh mì', 'Cái', 15000.00, '2025-06-30', '2025-04-18 21:20:01'),
(3, 'Pizza', 'Cái', 70000.00, '2025-08-15', '2025-04-18 21:20:01'),
(4, 'Mì xào', 'Phần', 20000.00, '2025-11-20', '2025-04-18 21:20:01'),
(5, 'Gà rán', 'Cái', 35000.00, '2025-07-10', '2025-04-18 21:20:01');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitietdonthucan`
--
ALTER TABLE `chitietdonthucan`
  ADD PRIMARY KEY (`MaDH`,`MaThucAn`),
  ADD KEY `MaThucAn` (`MaThucAn`);

--
-- Chỉ mục cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD PRIMARY KEY (`MaHD`,`MaHang`);

--
-- Chỉ mục cho bảng `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD PRIMARY KEY (`MaPN`,`MaHang`);

--
-- Chỉ mục cho bảng `donhangthucan`
--
ALTER TABLE `donhangthucan`
  ADD PRIMARY KEY (`MaDH`),
  ADD KEY `MaKH` (`MaKH`),
  ADD KEY `MaMay` (`MaMay`);

--
-- Chỉ mục cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`MaHD`),
  ADD KEY `MaKH` (`MaKH`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`MaKH`);

--
-- Chỉ mục cho bảng `kho`
--
ALTER TABLE `kho`
  ADD PRIMARY KEY (`MaKho`);

--
-- Chỉ mục cho bảng `khomaytinh`
--
ALTER TABLE `khomaytinh`
  ADD PRIMARY KEY (`MaKho`,`MaMay`),
  ADD KEY `MaMay` (`MaMay`);

--
-- Chỉ mục cho bảng `khothucan`
--
ALTER TABLE `khothucan`
  ADD PRIMARY KEY (`MaKho`,`MaThucAn`),
  ADD KEY `MaThucAn` (`MaThucAn`);

--
-- Chỉ mục cho bảng `maytinh`
--
ALTER TABLE `maytinh`
  ADD PRIMARY KEY (`MaMay`);

--
-- Chỉ mục cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`MaNCC`);

--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`MaNV`);

--
-- Chỉ mục cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD PRIMARY KEY (`MaPN`),
  ADD KEY `MaNCC` (`MaNCC`);

--
-- Chỉ mục cho bảng `sudungmay`
--
ALTER TABLE `sudungmay`
  ADD PRIMARY KEY (`MaSuDung`),
  ADD KEY `MaKH` (`MaKH`),
  ADD KEY `MaMay` (`MaMay`),
  ADD KEY `fk_sudungmay_nhanvien` (`MaNV`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`MaNV`);

--
-- Chỉ mục cho bảng `thucan`
--
ALTER TABLE `thucan`
  ADD PRIMARY KEY (`MaThucAn`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `donhangthucan`
--
ALTER TABLE `donhangthucan`
  MODIFY `MaDH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `MaHD` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `MaKH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `kho`
--
ALTER TABLE `kho`
  MODIFY `MaKho` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `maytinh`
--
ALTER TABLE `maytinh`
  MODIFY `MaMay` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `MaNCC` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  MODIFY `MaNV` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  MODIFY `MaPN` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `sudungmay`
--
ALTER TABLE `sudungmay`
  MODIFY `MaSuDung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT cho bảng `thucan`
--
ALTER TABLE `thucan`
  MODIFY `MaThucAn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chitietdonthucan`
--
ALTER TABLE `chitietdonthucan`
  ADD CONSTRAINT `chitietdonthucan_ibfk_1` FOREIGN KEY (`MaDH`) REFERENCES `donhangthucan` (`MaDH`),
  ADD CONSTRAINT `chitietdonthucan_ibfk_2` FOREIGN KEY (`MaThucAn`) REFERENCES `thucan` (`MaThucAn`);

--
-- Các ràng buộc cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD CONSTRAINT `chitiethoadon_ibfk_1` FOREIGN KEY (`MaHD`) REFERENCES `hoadon` (`MaHD`);

--
-- Các ràng buộc cho bảng `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD CONSTRAINT `chitietphieunhap_ibfk_1` FOREIGN KEY (`MaPN`) REFERENCES `phieunhap` (`MaPN`);

--
-- Các ràng buộc cho bảng `donhangthucan`
--
ALTER TABLE `donhangthucan`
  ADD CONSTRAINT `donhangthucan_ibfk_1` FOREIGN KEY (`MaKH`) REFERENCES `khachhang` (`MaKH`),
  ADD CONSTRAINT `donhangthucan_ibfk_2` FOREIGN KEY (`MaMay`) REFERENCES `maytinh` (`MaMay`);

--
-- Các ràng buộc cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`MaKH`) REFERENCES `khachhang` (`MaKH`);

--
-- Các ràng buộc cho bảng `khomaytinh`
--
ALTER TABLE `khomaytinh`
  ADD CONSTRAINT `khomaytinh_ibfk_1` FOREIGN KEY (`MaKho`) REFERENCES `kho` (`MaKho`),
  ADD CONSTRAINT `khomaytinh_ibfk_2` FOREIGN KEY (`MaMay`) REFERENCES `maytinh` (`MaMay`);

--
-- Các ràng buộc cho bảng `khothucan`
--
ALTER TABLE `khothucan`
  ADD CONSTRAINT `khothucan_ibfk_1` FOREIGN KEY (`MaKho`) REFERENCES `kho` (`MaKho`),
  ADD CONSTRAINT `khothucan_ibfk_2` FOREIGN KEY (`MaThucAn`) REFERENCES `thucan` (`MaThucAn`);

--
-- Các ràng buộc cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD CONSTRAINT `phieunhap_ibfk_1` FOREIGN KEY (`MaNCC`) REFERENCES `nhacungcap` (`MaNCC`);

--
-- Các ràng buộc cho bảng `sudungmay`
--
ALTER TABLE `sudungmay`
  ADD CONSTRAINT `fk_sudungmay_nhanvien` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`),
  ADD CONSTRAINT `sudungmay_ibfk_1` FOREIGN KEY (`MaKH`) REFERENCES `khachhang` (`MaKH`),
  ADD CONSTRAINT `sudungmay_ibfk_2` FOREIGN KEY (`MaMay`) REFERENCES `maytinh` (`MaMay`);

--
-- Các ràng buộc cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD CONSTRAINT `taikhoan_ibfk_1` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


ALTER TABLE sudungmay 
MODIFY MaSuDung INT AUTO_INCREMENT PRIMARY KEY;