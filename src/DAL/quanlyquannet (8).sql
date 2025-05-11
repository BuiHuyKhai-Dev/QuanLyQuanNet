-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 11, 2025 lúc 10:01 AM
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

CREATE TABLE `khachhang` (
  `MaKH` int(11) NOT NULL,
  `TenKH` varchar(255) DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `SoDuTaiKhoan` decimal(12,2) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`MaKH`, `TenKH`, `SoDienThoai`, `Email`, `SoDuTaiKhoan`, `created_at`) VALUES
(1, 'Nguyen Thi Mai', '0987654321', 'nguyenmai@gmail.com', 10000.00, '2025-04-18 21:20:01'),
(3, 'Le Thi Lan', '0978567345', 'lelan@hotmail.com', 1000000.00, '2025-04-18 21:20:01'),
(4, 'Pham Quang Hieu', '0945671234', 'phamquanghieu@outlook.com', -1941501.90, '2025-04-18 21:20:01'),
(5, 'Nguyen Bao Hoa', '0906789123', 'nguyenhoa@gmail.com', 200000.00, '2025-04-18 21:20:01'),
(6, 'Bùi Huy Khải', '0796137759', 'ok@gmail.com', 100000.00, '2025-05-10 17:14:59'),
(7, 'test123', '0123456789', 'test@gmail.com', 0.00, '2025-05-10 19:34:32'),
(8, 'test234', '123', '123', 0.00, '2025-05-10 19:34:59'),
(9, 'testing', '123456', '123456', 123456.00, '2025-05-10 19:41:35');

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
(1, 'Máy tính A1', 2, 'Khu 1', 5000, '2025-04-18 21:20:01'),
(2, 'Máy tính B2', 1, 'Khu 2', 4000, '2025-04-18 21:20:01'),
(3, 'Máy tính C3', 3, 'Khu 3', 6000, '2025-04-18 21:20:01'),
(4, 'Máy tính D4', 1, 'Khu 4', 5500, '2025-04-18 21:20:01'),
(5, 'Máy tính E5', 1, 'Khu 5', 4500, '2025-04-18 21:20:01');

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

CREATE TABLE `nhanvien` (
  `MaNV` int(11) NOT NULL,
  `HoTen` varchar(255) DEFAULT NULL,
  `GioiTinh` int(11) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `SoDienThoai` varchar(20) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `DiaChi` text DEFAULT NULL,
  `LuongCoBan` decimal(12,2) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`MaNV`, `HoTen`, `GioiTinh`, `NgaySinh`, `SoDienThoai`, `Email`, `DiaChi`, `LuongCoBan`, `created_at`) VALUES
(1, 'Nguyen Van A', 1, '1985-05-15', '0912345678', 'nguyenvana@gmail.com', 'Hà Nội', 8000000.00, '2025-04-18 21:20:01'),
(2, 'Truong Thi B', 2, '1990-08-22', '0923456789', 'truongthib@yahoo.com', 'Hồ Chí Minh', 7500000.00, '2025-04-18 21:20:01'),
(3, 'Phan Minh C', 1, '1992-03-30', '0934567890', 'phanminhc@gmail.com', 'Đà Nẵng', 8500000.00, '2025-04-18 21:20:01'),
(4, 'Lê Hoàng D', 1, '1987-11-10', '0945678901', 'lehoangd@outlook.com', 'Cần Thơ', 9500000.00, '2025-04-18 21:20:01'),
(5, 'Đặng Quế E', 2, '1995-01-05', '0956789012', 'dangquee@hotmail.com', 'Hải Phòng', 7200000.00, '2025-04-18 21:20:01'),
(6, 'Nguyễn Văn A', 1, '1990-01-01', '0123456789', 'a@gmail.com', 'Hà Nội', 10000000.00, '2025-04-18 21:22:02'),
(7, 'Trần Thị B', 2, '1985-05-12', '0987654321', 'b@gmail.com', 'TP Hồ Chí Minh', 12000000.00, '2025-04-18 21:22:02'),
(8, 'Lê Văn C', 1, '1980-07-21', '0932233445', 'c@gmail.com', 'Đà Nẵng', 11000000.00, '2025-04-18 21:22:02'),
(9, 'Phạm Thị D', 2, '1992-11-30', '0911223344', 'd@gmail.com', 'Cần Thơ', 9500000.00, '2025-04-18 21:22:02'),
(10, 'Hoàng Văn E', 1, '1995-09-15', '0900112233', 'e@gmail.com', 'Bình Dương', 11500000.00, '2025-04-18 21:22:02');

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
(53, 1, 4, 2, '2025-04-19 21:53:26', '2025-04-19 22:05:44', 0.21, 8200.00, '2025-04-19 21:53:26', 1),
(54, 1, 1, 1, '2025-04-19 21:55:59', '2025-04-19 21:56:01', 0.00, 27.78, '2025-04-19 21:55:59', 0),
(55, 1, 1, 1, '2025-05-11 14:12:26', '2025-05-11 14:12:27', 0.00, 13.89, '2025-05-11 14:12:26', 0),
(56, 1, 1, 1, '2025-05-11 14:20:50', '2025-05-11 14:20:51', 0.00, 13.89, '2025-05-11 14:20:50', 0),
(57, 1, 1, 1, '2025-05-11 14:29:59', '2025-05-11 14:30:01', 0.00, 27.78, '2025-05-11 14:29:59', 0),
(58, 1, 1, 1, '2025-05-11 14:32:42', NULL, 0.00, 0.00, '2025-05-11 14:32:42', 1);

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
  MODIFY `MaKH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

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
  MODIFY `MaSuDung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

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
-- Các ràng buộc cho bảng `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD CONSTRAINT `chitietphieunhap_ibfk_1` FOREIGN KEY (`MaPN`) REFERENCES `phieunhap` (`MaPN`);

--
-- Các ràng buộc cho bảng `donhangthucan`
--
ALTER TABLE `donhangthucan`
  ADD CONSTRAINT `donhangthucan_ibfk_2` FOREIGN KEY (`MaMay`) REFERENCES `maytinh` (`MaMay`);

--
-- Các ràng buộc cho bảng `khomaytinh`
--
ALTER TABLE `khomaytinh`
  ADD CONSTRAINT `khomaytinh_ibfk_2` FOREIGN KEY (`MaMay`) REFERENCES `maytinh` (`MaMay`);

--
-- Các ràng buộc cho bảng `khothucan`
--
ALTER TABLE `khothucan`
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
  ADD CONSTRAINT `sudungmay_ibfk_2` FOREIGN KEY (`MaMay`) REFERENCES `maytinh` (`MaMay`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
