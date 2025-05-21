-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 21, 2025 at 07:46 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quanlyquannet`
--

-- --------------------------------------------------------

--
-- Table structure for table `chitietdonthucan`
--

CREATE TABLE `chitietdonthucan` (
  `MaDH` int(11) NOT NULL,
  `MaThucAn` int(11) NOT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `DonGia` decimal(12,2) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chitietdonthucan`
--

INSERT INTO `chitietdonthucan` (`MaDH`, `MaThucAn`, `SoLuong`, `DonGia`, `created_at`) VALUES
(1, 1, 5, 10000.00, '2025-04-18 21:30:42'),
(2, 2, 7, 15000.00, '2025-04-18 21:30:42'),
(3, 3, 10, 20000.00, '2025-04-18 21:30:42'),
(4, 4, 6, 12000.00, '2025-04-18 21:30:42'),
(5, 5, 8, 18000.00, '2025-04-18 21:30:42');

-- --------------------------------------------------------

--
-- Table structure for table `chitiethoadon`
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
-- Dumping data for table `chitiethoadon`
--

INSERT INTO `chitiethoadon` (`MaHD`, `MaHang`, `SoLuong`, `DonGia`, `ThanhTien`, `created_at`) VALUES
(1, 1, 1, 50000.00, 50000.00, '2025-04-18 21:20:01'),
(2, 2, 2, 40000.00, 80000.00, '2025-04-18 21:20:01'),
(3, 3, 3, 40000.00, 120000.00, '2025-04-18 21:20:01'),
(4, 4, 1, 65000.00, 65000.00, '2025-04-18 21:20:01'),
(5, 5, 2, 45000.00, 90000.00, '2025-04-18 21:20:01');

-- --------------------------------------------------------

--
-- Table structure for table `chitietphieunhap`
--

CREATE TABLE `chitietphieunhap` (
  `MaPN` int(11) NOT NULL,
  `MaThucAn` int(11) NOT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `DonGia` decimal(12,2) DEFAULT NULL,
  `ThanhTien` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chitietphieunhap`
--

INSERT INTO `chitietphieunhap` (`MaPN`, `MaThucAn`, `SoLuong`, `DonGia`, `ThanhTien`) VALUES
(1, 1, 10, 200000.00, 2000000.00),
(2, 2, 15, 300000.00, 4500000.00),
(3, 3, 20, 350000.00, 7000000.00),
(4, 4, 12, 400000.00, 4800000.00),
(5, 5, 18, 500000.00, 9000000.00),
(7, 4, 20, 20000.00, 400000.00),
(7, 5, 30, 35000.00, 1050000.00),
(8, 1, 50, 25000.00, 1250000.00),
(8, 4, 30, 20000.00, 600000.00),
(9, 1, 50, 25000.00, 1250000.00),
(9, 5, 70, 35000.00, 2450000.00),
(9, 6, 50, 35000.00, 1750000.00),
(10, 1, 50, 25000.00, 1250000.00),
(10, 5, 30, 35000.00, 1050000.00),
(11, 1, 50, 25000.00, 1250000.00),
(11, 4, 70, 20000.00, 1400000.00),
(11, 5, 70, 35000.00, 2450000.00),
(12, 3, 50, 70000.00, 3500000.00),
(12, 6, 50, 35000.00, 1750000.00),
(13, 1, 20, 25000.00, 500000.00),
(13, 2, 20, 15000.00, 300000.00),
(14, 1, 30, 25000.00, 750000.00),
(14, 2, 30, 15000.00, 450000.00),
(15, 1, 50, 25000.00, 1250000.00),
(15, 2, 50, 15000.00, 750000.00),
(16, 4, 100, 20000.00, 2000000.00),
(16, 5, 100, 35000.00, 3500000.00),
(16, 6, 100, 35000.00, 3500000.00),
(17, 7, 200, 45000.00, 9000000.00);

-- --------------------------------------------------------

--
-- Table structure for table `danhmucchucnang`
--

CREATE TABLE `danhmucchucnang` (
  `machucnang` int(11) NOT NULL,
  `tenchucnang` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `danhmucchucnang`
--

INSERT INTO `danhmucchucnang` (`machucnang`, `tenchucnang`) VALUES
(1, 'Quản lý máy'),
(2, 'Quản lý khách hàng'),
(3, 'Quản lý nhân viên'),
(4, 'Quản lý nhà cung cấp'),
(5, 'Quản lý nhập hàng'),
(6, 'Quản lý kho'),
(7, 'Thống kê'),
(8, 'Phân quyền');

-- --------------------------------------------------------

--
-- Table structure for table `donhangthucan`
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
-- Dumping data for table `donhangthucan`
--

INSERT INTO `donhangthucan` (`MaDH`, `MaKH`, `MaMay`, `NgayDat`, `TongTien`, `TrangThai`, `created_at`, `MaNV`) VALUES
(1, 1, 1, '2025-04-10 00:00:00', 50000.00, 1, '2025-04-18 21:20:01', 1),
(2, 2, 2, '2025-04-11 00:00:00', 80000.00, 2, '2025-04-18 21:20:01', 4),
(3, 3, 3, '2025-04-12 00:00:00', 120000.00, 3, '2025-04-18 21:20:01', 5),
(4, 4, 4, '2025-04-13 00:00:00', 65000.00, 4, '2025-04-18 21:20:01', 3),
(5, 5, 5, '2025-04-14 00:00:00', 90000.00, 1, '2025-04-18 21:20:01', 2),
(6, 3, 2, '2025-05-13 16:32:35', 60000.00, 1, '2025-05-13 16:32:35', 1),
(7, 1, 1, '2025-05-14 16:09:54', 85000.00, 1, '2025-05-14 16:09:54', 1),
(8, 1, 1, '2025-05-14 16:09:58', 0.00, 1, '2025-05-14 16:09:58', 1);

-- --------------------------------------------------------

--
-- Table structure for table `hoadon`
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
-- Dumping data for table `hoadon`
--

INSERT INTO `hoadon` (`MaHD`, `MaKH`, `LoaiHD`, `RefID`, `NgayLap`, `TongTien`, `PhuongThucThanhToan`, `created_at`, `MaNV`) VALUES
(1, 1, 1, 1, '2025-04-10 00:00:00', 50000.00, 1, '2025-04-18 21:20:01', 3),
(2, 2, 2, 2, '2025-04-11 00:00:00', 80000.00, 2, '2025-04-18 21:20:01', 4),
(3, 3, 1, 3, '2025-04-12 00:00:00', 120000.00, 1, '2025-04-18 21:20:01', 3),
(4, 4, 2, 4, '2025-04-13 00:00:00', 65000.00, 2, '2025-04-18 21:20:01', 2),
(5, 5, 1, 5, '2025-04-14 00:00:00', 90000.00, 1, '2025-04-18 21:20:01', 5);

-- --------------------------------------------------------

--
-- Table structure for table `khachhang`
--

CREATE TABLE `khachhang` (
  `MaKH` int(11) NOT NULL,
  `HoKH` varchar(100) NOT NULL,
  `TenKH` varchar(255) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `SoDuTaiKhoan` decimal(12,2) DEFAULT NULL,
  `MatKhau` varchar(255) NOT NULL,
  `TrangThai` int(11) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `khachhang`
--

INSERT INTO `khachhang` (`MaKH`, `HoKH`, `TenKH`, `SoDienThoai`, `Email`, `SoDuTaiKhoan`, `MatKhau`, `TrangThai`, `created_at`) VALUES
(1, 'Nguyễn Thành', 'An', '0901234567', 'an.nguyen@example.com', -1000.00, 'matkhau1', 1, '2024-05-01 08:30:00'),
(2, 'Trần Gia', 'Bình', '0912345678', 'binh.tran@example.com', 0.00, 'matkhau2', 1, '2024-05-02 09:00:00'),
(3, 'Lê Văn', 'Cường', '0923456789', 'cuong.le@example.com', 150000.00, 'matkhau3', 1, '2024-05-03 10:15:00'),
(4, 'Phạm Mỹ', 'Dung', '0934567890', 'dung.pham@example.com', 80000.00, 'matkhau4', 1, '2024-05-04 11:20:00'),
(5, 'Hoàng Hồng', 'Em', '0945678901', 'em.hoang@example.com', 250000.00, 'matkhau5', 1, '2024-05-05 12:45:00'),
(6, 'Vũ Ngọc', 'Giang', '0956789012', 'giang.vu@example.com', 120000.00, 'matkhau6', 1, '2024-05-06 13:10:00'),
(7, 'Đỗ', 'Hạnh', '0967890123', 'hanh.do@example.com', 60000.00, 'matkhau7', 1, '2024-05-07 14:25:00'),
(8, 'Bùi Huy', 'Khải', '0978901234', 'khai.bui@example.com', 90000.00, 'matkhau8', 1, '2024-05-08 15:40:00'),
(9, 'Đặng Tú', 'Linh', '0989012345', 'linh.dang@example.com', 200000.00, 'matkhau9', 1, '2024-05-09 16:55:00'),
(10, 'Ngô Cao', 'Minh', '0990123456', 'minh.ngo@example.com', 110000.00, 'matkhau10', 1, '2024-05-10 17:30:00');

-- --------------------------------------------------------

--
-- Table structure for table `kho`
--

CREATE TABLE `kho` (
  `MaKho` int(11) NOT NULL,
  `TenKho` varchar(255) DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `SucChua` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kho`
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
-- Table structure for table `khomaytinh`
--

CREATE TABLE `khomaytinh` (
  `MaKho` int(11) NOT NULL,
  `MaNCC` int(10) NOT NULL,
  `MaMay` int(11) NOT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `khomaytinh`
--

INSERT INTO `khomaytinh` (`MaKho`, `MaNCC`, `MaMay`, `SoLuong`, `created_at`) VALUES
(1, 1, 1, 1, '2025-04-18 21:20:01'),
(2, 1, 2, 1, '2025-04-18 21:20:01'),
(3, 1, 3, 1, '2025-04-18 21:20:01'),
(4, 2, 4, 1, '2025-04-18 21:20:01'),
(5, 2, 5, 1, '2025-04-18 21:20:01');

-- --------------------------------------------------------

--
-- Table structure for table `khothucan`
--

CREATE TABLE `khothucan` (
  `MaKho` int(11) NOT NULL,
  `MaNCC` int(11) NOT NULL,
  `MaThucAn` int(11) NOT NULL,
  `SoLuong` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `khothucan`
--

INSERT INTO `khothucan` (`MaKho`, `MaNCC`, `MaThucAn`, `SoLuong`, `created_at`) VALUES
(1, 3, 1, 50, '2025-04-18 21:22:02'),
(1, 3, 2, 100, '2025-04-18 21:22:02'),
(2, 3, 3, 150, '2025-04-18 21:22:02'),
(3, 3, 4, 80, '2025-04-18 21:22:02'),
(4, 3, 5, 120, '2025-04-18 21:22:02');

-- --------------------------------------------------------

--
-- Table structure for table `maytinh`
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
-- Dumping data for table `maytinh`
--

INSERT INTO `maytinh` (`MaMay`, `TenMay`, `TrangThai`, `ViTri`, `GiaThue`, `created_at`) VALUES
(1, 'Máy tính A1', 3, 'Khu 1', 5000, '2025-04-18 21:20:01'),
(2, 'Máy tính B2', 1, 'Khu 2', 4000, '2025-04-18 21:20:01'),
(3, 'Máy tính C3', 3, 'Khu 3', 6000, '2025-04-18 21:20:01'),
(4, 'Máy tính D4', 1, 'Khu 4', 5500, '2025-04-18 21:20:01'),
(5, 'Máy tính E5', 1, 'Khu 5', 4500, '2025-04-18 21:20:01');

-- --------------------------------------------------------

--
-- Table structure for table `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `MaNCC` int(11) NOT NULL,
  `TenNCC` varchar(100) NOT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `TrangThai` int(11) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nhacungcap`
--

INSERT INTO `nhacungcap` (`MaNCC`, `TenNCC`, `DiaChi`, `SoDienThoai`, `Email`, `TrangThai`, `created_at`) VALUES
(1, 'Công ty Phong Vũ', 'Hà Nội', '0101010101', 'ncca@gmail.com', 1, '2025-04-18 21:22:02'),
(2, 'Công ty GearVN', 'TP Hồ Chí Minh', '0202020202', 'nccb@gmail.com', 1, '2025-04-18 21:22:02'),
(3, 'Bách Hóa Xanh', 'Đà Nẵng', '0303030303', 'nccc@gmail.com', 1, '2025-04-18 21:22:02'),
(4, 'Công ty D', 'Cần Thơ', '0404040404', 'nccd@gmail.com', 1, '2025-04-18 21:22:02'),
(5, 'Công ty E', 'Bình Dương', '0505050505', 'ncce@gmail.com', 1, '2025-04-18 21:22:02'),
(6, 'Công ty F', 'Khánh Hòa', '0123456789', 'nccf@gmail.com', 1, '2025-05-14 00:20:07');

-- --------------------------------------------------------

--
-- Table structure for table `nhanvien`
--

CREATE TABLE `nhanvien` (
  `MaNV` int(11) NOT NULL,
  `HoNV` varchar(255) DEFAULT NULL,
  `TenNV` varchar(255) NOT NULL,
  `GioiTinh` int(11) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `DiaChi` text DEFAULT NULL,
  `LuongCoBan` decimal(12,2) DEFAULT NULL,
  `TrangThai` int(11) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nhanvien`
--

INSERT INTO `nhanvien` (`MaNV`, `HoNV`, `TenNV`, `GioiTinh`, `NgaySinh`, `SoDienThoai`, `Email`, `DiaChi`, `LuongCoBan`, `TrangThai`, `created_at`) VALUES
(1, 'Nguyễn Văn ', 'A', 1, '1985-05-15', '0912345678', 'nguyenvana@gmail.com', 'Hà Nội', 8000000.00, 1, '2025-04-18 21:20:01'),
(2, 'Trương Thị', 'B', 2, '1990-08-22', '0923456789', 'truongthib@yahoo.com', 'Hồ Chí Minh', 7500000.00, 1, '2025-04-18 21:20:01'),
(3, 'Phan Minh', 'C', 1, '1992-03-30', '0934567890', 'phanminhc@gmail.com', 'Đà Nẵng', 8500000.00, 1, '2025-04-18 21:20:01'),
(4, 'Lê Hoàng', 'D', 1, '1987-11-10', '0945678901', 'lehoangd@outlook.com', 'Cần Thơ', 9500000.00, 1, '2025-04-18 21:20:01'),
(5, 'Đặng Quế', 'E', 2, '1995-01-05', '0956789012', 'dangquee@hotmail.com', 'Hải Phòng', 7200000.00, 1, '2025-04-18 21:20:01'),
(6, 'Nguyễn Văn', 'A', 1, '1990-01-01', '0123456789', 'a@gmail.com', 'Hà Nội', 10000000.00, 1, '2025-04-18 21:22:02'),
(7, 'Trần Thị', 'B', 2, '1985-05-12', '0987654321', 'b@gmail.com', 'TP Hồ Chí Minh', 12000000.00, 1, '2025-04-18 21:22:02'),
(8, 'Lê Văn', 'C', 1, '1980-07-21', '0932233445', 'c@gmail.com', 'Đà Nẵng', 11000000.00, 1, '2025-04-18 21:22:02'),
(9, 'Phạm Thị', 'D', 2, '1992-11-30', '0911223344', 'd@gmail.com', 'Cần Thơ', 9500000.00, 1, '2025-04-18 21:22:02'),
(10, 'Hoàng Văn', 'E', 1, '1995-09-15', '0900112233', 'e@gmail.com', 'Bình Dương', 11500000.00, 1, '2025-04-18 21:22:02'),
(11, 'Nguyễn Văn', 'Z', 1, '2001-07-13', '0123456789', 'z@gmail.com', 'Thanh Hóa', 1200000.00, 1, '2025-05-14 13:19:33');

-- --------------------------------------------------------

--
-- Table structure for table `nhomquyen`
--

CREATE TABLE `nhomquyen` (
  `idnhomquyen` int(11) NOT NULL,
  `tennhomquyen` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nhomquyen`
--

INSERT INTO `nhomquyen` (`idnhomquyen`, `tennhomquyen`) VALUES
(1, 'Quản lý'),
(2, 'Nhân viên'),
(3, 'Kho'),
(4, 'Khách'),
(5, 'Máy');

-- --------------------------------------------------------

--
-- Table structure for table `phieunhap`
--

CREATE TABLE `phieunhap` (
  `MaPN` int(11) NOT NULL,
  `MaNCC` int(11) NOT NULL,
  `MaNV` int(100) DEFAULT NULL,
  `TongTien` decimal(12,2) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `phieunhap`
--

INSERT INTO `phieunhap` (`MaPN`, `MaNCC`, `MaNV`, `TongTien`, `created_at`) VALUES
(1, 1, 1, 2000000.00, '2025-04-18 21:22:02'),
(2, 2, 2, 4500000.00, '2025-04-18 21:22:02'),
(3, 3, 3, 7000000.00, '2025-04-18 21:22:02'),
(4, 4, 4, 4800000.00, '2025-04-18 21:22:02'),
(5, 5, 5, 9000000.00, '2025-04-18 21:22:02'),
(6, 1, 1, 3000000.00, '2025-05-16 23:34:12'),
(7, 3, 2, 1450000.00, '2025-05-16 23:37:40'),
(8, 3, 1, 1850000.00, '2025-05-16 23:42:08'),
(9, 3, 5, 5450000.00, '2025-05-16 23:47:04'),
(10, 1, 1, 2300000.00, '2025-05-16 23:55:10'),
(11, 1, 1, 5100000.00, '2025-05-16 23:58:26'),
(12, 3, 1, 5250000.00, '2025-05-17 00:01:37'),
(13, 3, 2, 800000.00, '2025-05-17 00:07:01'),
(15, 1, 1, 2000000.00, '2025-05-17 00:16:16'),
(16, 3, 1, 9000000.00, '2025-05-17 00:19:16');

-- --------------------------------------------------------

--
-- Table structure for table `sudungmay`
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
-- Dumping data for table `sudungmay`
--

INSERT INTO `sudungmay` (`MaSuDung`, `MaNV`, `MaKH`, `MaMay`, `ThoiGianBatDau`, `ThoiGianKetThuc`, `TongThoiGian`, `ChiPhi`, `created_at`, `trangthai`) VALUES
(49, 1, 5, 5, '2025-04-19 21:45:48', '2025-04-19 22:05:48', 0.33, 15000.00, '2025-04-19 21:45:48', 1),
(50, 1, 2, 2, '2025-04-19 21:46:20', '2025-04-19 21:52:44', 0.11, 4266.67, '2025-04-19 21:46:20', 0),
(51, 1, 2, 2, '2025-04-19 21:52:50', '2025-04-19 21:53:17', 0.01, 300.00, '2025-04-19 21:52:50', 0),
(53, 1, 4, 2, '2025-04-19 21:53:26', '2025-04-19 22:05:44', 0.21, 8200.00, '2025-04-19 21:53:26', 0),
(54, 1, 1, 1, '2025-04-19 21:55:59', '2025-04-19 21:56:01', 0.00, 27.78, '2025-04-19 21:55:59', 0),
(55, 1, 1, 1, '2025-05-11 14:12:26', '2025-05-11 14:12:27', 0.00, 13.89, '2025-05-11 14:12:26', 0),
(56, 1, 1, 1, '2025-05-11 14:20:50', '2025-05-11 14:20:51', 0.00, 13.89, '2025-05-11 14:20:50', 0),
(57, 1, 1, 1, '2025-05-11 14:29:59', '2025-05-11 14:30:01', 0.00, 27.78, '2025-05-11 14:29:59', 0),
(58, 1, 1, 1, '2025-05-11 14:32:42', '2025-05-13 16:18:11', 49.76, 248790.28, '2025-05-11 14:32:42', 0),
(59, 1, 1, 1, '2025-05-13 16:18:57', '2025-05-13 16:18:58', 0.00, 1.39, '2025-05-13 16:18:57', 0),
(60, 1, 1, 1, '2025-05-13 16:19:07', '2025-05-13 16:19:08', 0.00, 1.39, '2025-05-13 16:19:07', 0),
(61, 1, 4, 1, '2025-05-13 16:19:18', '2025-05-13 16:19:19', 0.00, 1.39, '2025-05-13 16:19:18', 0),
(62, 1, 3, 1, '2025-05-13 16:20:57', '2025-05-13 16:21:09', 0.00, 16.67, '2025-05-13 16:20:57', 1),
(63, 1, 3, 2, '2025-05-14 16:04:29', '2025-05-14 21:47:53', 5.72, 22893.33, '2025-05-14 16:04:29', 1);

-- --------------------------------------------------------

--
-- Table structure for table `taikhoan`
--

CREATE TABLE `taikhoan` (
  `tendangnhap` varchar(255) NOT NULL,
  `matkhau` varchar(10) NOT NULL,
  `manhomquyen` int(2) NOT NULL,
  `trangthai` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `taikhoan`
--

INSERT INTO `taikhoan` (`tendangnhap`, `matkhau`, `manhomquyen`, `trangthai`) VALUES
('Admin', '1', 1, 1),
('an.nguyen@example.com', 'matkhau1', 4, 1),
('binh.tran@example.com', 'matkhau2', 4, 1),
('cuong.le@example.com', 'matkhau3', 4, 1),
('dung.pham@example.com', 'matkhau4', 4, 1),
('em.hoang@example.com', 'matkhau5', 4, 1),
('giang.vu@example.com', 'matkhau6', 4, 1),
('hanh.do@example.com', 'matkhau7', 4, 1),
('Khach', '1', 4, 1),
('khai.bui@example.com', 'matkhau8', 4, 1),
('Kho', '1', 3, 1),
('linh.dang@example.com', 'matkhau9', 4, 1),
('May1', '1', 5, 1),
('May2', '1', 5, 1),
('May3', '1', 5, 1),
('May4', '1', 5, 1),
('May5', '1', 5, 1),
('minh.ngo@example.com', 'matkhau10', 4, 1),
('NhanVien', '1', 2, 1),
('Test', '1', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `thucan`
--

CREATE TABLE `thucan` (
  `MaThucAn` int(11) NOT NULL,
  `TenThucAn` varchar(255) DEFAULT NULL,
  `SoLuong` int(11) NOT NULL,
  `DonGia` decimal(12,2) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `thucan`
--

INSERT INTO `thucan` (`MaThucAn`, `TenThucAn`, `SoLuong`, `DonGia`, `created_at`) VALUES
(1, 'Cơm chiên', 200, 25000.00, '2025-04-18 21:20:01'),
(2, 'Bánh mì', 200, 15000.00, '2025-04-18 21:20:01'),
(3, 'Pizza', 200, 70000.00, '2025-04-18 21:20:01'),
(4, 'Mì xào', 200, 20000.00, '2025-04-18 21:20:01'),
(5, 'Gà rán', 200, 35000.00, '2025-04-18 21:20:01'),
(6, 'Lòng xào dưa', 200, 35000.00, '2025-05-16 18:57:13'),
(7, 'Gà xào xả ớt', 200, 45000.00, '2025-05-17 00:30:05'),
(8, 'Xúc xích phô mai', 0, 30000.00, '2025-05-19 00:10:47'),
(9, 'Bò né', 0, 50000.00, '2025-05-19 00:36:57');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chitietdonthucan`
--
ALTER TABLE `chitietdonthucan`
  ADD PRIMARY KEY (`MaDH`,`MaThucAn`),
  ADD KEY `MaThucAn` (`MaThucAn`);

--
-- Indexes for table `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD PRIMARY KEY (`MaHD`,`MaHang`);

--
-- Indexes for table `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD PRIMARY KEY (`MaPN`,`MaThucAn`);

--
-- Indexes for table `danhmucchucnang`
--
ALTER TABLE `danhmucchucnang`
  ADD PRIMARY KEY (`machucnang`);

--
-- Indexes for table `donhangthucan`
--
ALTER TABLE `donhangthucan`
  ADD PRIMARY KEY (`MaDH`),
  ADD KEY `MaKH` (`MaKH`),
  ADD KEY `MaMay` (`MaMay`);

--
-- Indexes for table `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`MaHD`),
  ADD KEY `MaKH` (`MaKH`);

--
-- Indexes for table `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`MaKH`);

--
-- Indexes for table `kho`
--
ALTER TABLE `kho`
  ADD PRIMARY KEY (`MaKho`);

--
-- Indexes for table `khomaytinh`
--
ALTER TABLE `khomaytinh`
  ADD PRIMARY KEY (`MaKho`,`MaMay`),
  ADD KEY `MaMay` (`MaMay`);

--
-- Indexes for table `khothucan`
--
ALTER TABLE `khothucan`
  ADD PRIMARY KEY (`MaKho`,`MaThucAn`),
  ADD KEY `MaThucAn` (`MaThucAn`);

--
-- Indexes for table `maytinh`
--
ALTER TABLE `maytinh`
  ADD PRIMARY KEY (`MaMay`);

--
-- Indexes for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`MaNCC`);

--
-- Indexes for table `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`MaNV`);

--
-- Indexes for table `nhomquyen`
--
ALTER TABLE `nhomquyen`
  ADD PRIMARY KEY (`idnhomquyen`);

--
-- Indexes for table `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD PRIMARY KEY (`MaPN`),
  ADD KEY `MaNCC` (`MaNCC`);

--
-- Indexes for table `sudungmay`
--
ALTER TABLE `sudungmay`
  ADD PRIMARY KEY (`MaSuDung`),
  ADD KEY `MaKH` (`MaKH`),
  ADD KEY `MaMay` (`MaMay`),
  ADD KEY `fk_sudungmay_nhanvien` (`MaNV`);

--
-- Indexes for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`tendangnhap`);

--
-- Indexes for table `thucan`
--
ALTER TABLE `thucan`
  ADD PRIMARY KEY (`MaThucAn`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `donhangthucan`
--
ALTER TABLE `donhangthucan`
  MODIFY `MaDH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `MaHD` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `MaKH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `kho`
--
ALTER TABLE `kho`
  MODIFY `MaKho` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `maytinh`
--
ALTER TABLE `maytinh`
  MODIFY `MaMay` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `MaNCC` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `nhanvien`
--
ALTER TABLE `nhanvien`
  MODIFY `MaNV` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `phieunhap`
--
ALTER TABLE `phieunhap`
  MODIFY `MaPN` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `sudungmay`
--
ALTER TABLE `sudungmay`
  MODIFY `MaSuDung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT for table `thucan`
--
ALTER TABLE `thucan`
  MODIFY `MaThucAn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `chitietdonthucan`
--
ALTER TABLE `chitietdonthucan`
  ADD CONSTRAINT `chitietdonthucan_ibfk_1` FOREIGN KEY (`MaDH`) REFERENCES `donhangthucan` (`MaDH`),
  ADD CONSTRAINT `chitietdonthucan_ibfk_2` FOREIGN KEY (`MaThucAn`) REFERENCES `thucan` (`MaThucAn`);

--
-- Constraints for table `donhangthucan`
--
ALTER TABLE `donhangthucan`
  ADD CONSTRAINT `donhangthucan_ibfk_2` FOREIGN KEY (`MaMay`) REFERENCES `maytinh` (`MaMay`);

--
-- Constraints for table `khomaytinh`
--
ALTER TABLE `khomaytinh`
  ADD CONSTRAINT `khomaytinh_ibfk_2` FOREIGN KEY (`MaMay`) REFERENCES `maytinh` (`MaMay`);

--
-- Constraints for table `sudungmay`
--
ALTER TABLE `sudungmay`
  ADD CONSTRAINT `sudungmay_ibfk_2` FOREIGN KEY (`MaMay`) REFERENCES `maytinh` (`MaMay`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
