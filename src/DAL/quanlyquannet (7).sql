-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 09, 2025 lúc 12:34 PM
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
  `makh` int(255) NOT NULL,
  `tenkh` varchar(255) NOT NULL,
  `matkhau` varchar(255) NOT NULL,
  `cccd` varchar(255) NOT NULL,
  `sodienthoai` int(10) NOT NULL,
  `ngaysinh` date DEFAULT NULL,
  `ngaydangky` date DEFAULT curdate(),
  `sogio` varchar(20) NOT NULL,
  `sotiennaptong` decimal(15,3) DEFAULT NULL,
  `sodu` decimal(15,3) DEFAULT NULL,
  `trangThai` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`makh`, `tenkh`, `matkhau`, `cccd`, `sodienthoai`, `ngaysinh`, `ngaydangky`, `sogio`, `sotiennaptong`, `sodu`, `trangThai`) VALUES
(1, 'Nguyễn Hữu Cảnh', '12345678', '012345678901', 912345678, '2000-05-10', '2025-05-09', '5', 100000.000, 23718737.540, 1),
(2, 'Bùi Thị Liên', 'abcdefgh', '011111111111', 987654321, '1995-12-20', '2025-05-09', '3', 50000.000, 17779.711, 1),
(3, 'Lê Văn Nhã', 'matkhau12', '022222222222', 909090909, '1990-07-15', '2025-05-09', '10', 150000.000, 75000.000, 1),
(4, 'Phạm Tiến Dũng', 'pass1234', '033333333333', 911111111, '1999-01-01', '2025-05-09', '0', 0.000, 18956261.489, 1),
(5, 'Lê Văn Lục', 'hoahoaa1', '044444444444', 933333333, '1988-03-10', '2025-05-09', '1', 20000.000, 10000.000, 1),
(6, 'Nguyễn Thị Loan', 'password9', '055555555555', 901234567, '1992-08-25', '2025-05-09', '7', 300000.000, 150000.000, 1),
(7, 'Mẫn Nhiên', 'abcd1234', '066666666666', 976543210, '2001-04-14', '2025-05-09', '2', 100000.000, 30000.000, 1),
(8, 'Phan Kim Tiền', 'linhpass8', '077777777777', 981111222, '1997-07-07', '2025-05-09', '0', 0.000, -1.921, 1),
(9, 'Nguyễn Thị Lý', 'ngopass99', '088888888888', 922233445, '1985-12-12', '2025-05-09', '12', 500000.000, 100000.000, 1),
(10, 'Bùi Quang MinhMinh', 'tran123456', '099999999999', 912233445, '1994-09-09', '2025-05-09', '4', 250000.000, 50000.000, 1);

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
(1, 'Máy tính A1', 1, 'Khu 1', 5000, '2025-04-18 21:20:01'),
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
  `MaNV` int(255) NOT NULL,
  `TenNV` varchar(255) NOT NULL,
  `MatKhau` varchar(255) NOT NULL,
  `CCCD` varchar(50) NOT NULL,
  `SoDT` int(10) NOT NULL,
  `NgaySinh` date DEFAULT NULL,
  `NgayDangKy` date NOT NULL,
  `Username` varchar(255) NOT NULL,
  `Role` varchar(50) NOT NULL,
  `ChucVu` varchar(50) NOT NULL,
  `TrangThai` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`MaNV`, `TenNV`, `MatKhau`, `CCCD`, `SoDT`, `NgaySinh`, `NgayDangKy`, `Username`, `Role`, `ChucVu`, `TrangThai`) VALUES
(1, 'Nguyễn Văn An', '12345678', '012345678901', 912345678, '1990-05-10', '2025-05-09', 'nguyenvana', 'Tiếp tân', 'Nhân viên', 1),
(2, 'Trần Thị Bích', 'abcxyz88', '011111111111', 987654321, '1995-12-20', '2025-05-09', 'tranthib', 'Phục vụ', 'Nhân viên', 1),
(3, 'Lê Văn Cường', 'lepass12', '022222222222', 909090909, '1992-07-15', '2025-05-09', 'levanc', 'Xuất nhập kho', 'Nhân viên', 1),
(4, 'Phạm Quốc Dũng', 'pqdpwd99', '033333333333', 911111111, '1988-01-01', '2025-05-09', 'phamqd', 'Phục vụ', 'Quản lý', 1),
(5, 'Đỗ Thị Hạnh', 'hanhdo88', '044444444444', 922222222, '1998-03-10', '2025-05-09', 'dothihanh', 'Tiếp tân', 'Nhân viên', 1),
(6, 'Trịnh Hữu Nam', 'nam123456', '055555555555', 971234567, '1985-08-25', '2025-05-09', 'trinhhn', 'Xuất nhập kho', 'Nhân viên', 1),
(7, 'Nguyễn Thị Yến', 'yenpass88', '066666666666', 966666666, '1996-02-20', '2025-05-09', 'nguyenty', 'Phục vụ', 'Admin', 1),
(8, 'Vũ Minh Tuấn', 'tuanvm123', '077777777777', 933333333, '1999-01-01', '2025-05-09', 'vuminhtuan', 'Tiếp tân', 'Nhân viên', 1),
(9, 'Lê Thị Thảo', 'thaole98', '088888888888', 928888888, '1997-10-05', '2025-05-09', 'lethithao', 'Xuất nhập kho', 'Quản lý', 1),
(10, 'Ngô Hữu Đức', 'ngohd321', '099999999999', 908888888, '1982-12-12', '2025-05-09', 'ngohd', 'Phục vụ', 'Admin', 1);

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
(57, 1, 1, 1, '2025-05-09 17:30:56', '2025-05-09 17:31:23', 0.01, 37.50, '2025-05-09 17:30:56', 1),
(58, 1, 8, 2, '2025-05-09 17:33:10', '2025-05-09 17:33:11', 0.00, 1.11, '2025-05-09 17:33:10', 1);

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
  ADD PRIMARY KEY (`makh`);

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
-- Chỉ mục cho bảng `sudungmay`
--
ALTER TABLE `sudungmay`
  ADD PRIMARY KEY (`MaSuDung`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `makh` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  MODIFY `MaNV` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `sudungmay`
--
ALTER TABLE `sudungmay`
  MODIFY `MaSuDung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
