
-- Bảng khách hàng
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

-- Bảng nhân viên

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
