-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 01, 2024 lúc 04:15 PM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `db_cource_lts`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tb_accounts`
--

CREATE TABLE `tb_accounts` (
  `accountId` int(11) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `authorityId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tb_accounts`
--

INSERT INTO `tb_accounts` (`accountId`, `fullname`, `password`, `username`, `authorityId`) VALUES
(1, 'Admin', '123', 'LA', 1),
(2, 'CTV1', '123', 'LA_CTV1', 2),
(3, 'QuanLy1', '123', 'LA_QL1', 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tb_articles`
--

CREATE TABLE `tb_articles` (
  `articleId` int(11) NOT NULL,
  `articleName` varchar(50) NOT NULL,
  `authorName` varchar(50) NOT NULL,
  `content` varchar(255) NOT NULL,
  `creationTime` datetime(6) NOT NULL,
  `image` varchar(255) NOT NULL,
  `shortContent` varchar(1000) NOT NULL,
  `accountId` int(11) NOT NULL,
  `themeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tb_articles`
--

INSERT INTO `tb_articles` (`articleId`, `articleName`, `authorName`, `content`, `creationTime`, `image`, `shortContent`, `accountId`, `themeId`) VALUES
(1, 'Tên Bài Viết', 'Cộng Tác Viên', 'Nội Dung', '2018-01-01 00:00:00.000000', './image.baiviet1', 'Nội Dung Ngắn', 2, 1),
(2, 'Tên Bài Viết 1', 'Cộng Tác Viên', 'Nội Dung', '2018-01-01 00:00:00.000000', './image.baiviet2', 'Nội Dung Ngắn', 2, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tb_article_types`
--

CREATE TABLE `tb_article_types` (
  `articleTypeId` int(11) NOT NULL,
  `articleTypeName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tb_article_types`
--

INSERT INTO `tb_article_types` (`articleTypeId`, `articleTypeName`) VALUES
(1, 'Thông Báo'),
(2, 'Bài Giảng');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tb_authorities`
--

CREATE TABLE `tb_authorities` (
  `authorityId` int(11) NOT NULL,
  `authorityName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tb_authorities`
--

INSERT INTO `tb_authorities` (`authorityId`, `authorityName`) VALUES
(1, 'Admin'),
(2, 'Cộng Tác Viên'),
(3, 'Quản Lý Đào Tạo');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tb_cources`
--

CREATE TABLE `tb_cources` (
  `courceId` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `courceName` varchar(50) NOT NULL,
  `fees` float NOT NULL,
  `image` varchar(255) NOT NULL,
  `intro` varchar(255) NOT NULL,
  `timer` int(11) NOT NULL,
  `totalStudent` int(11) NOT NULL,
  `totalSubject` int(11) NOT NULL,
  `courceTypeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tb_cources`
--

INSERT INTO `tb_cources` (`courceId`, `content`, `courceName`, `fees`, `image`, `intro`, `timer`, `totalStudent`, `totalSubject`, `courceTypeId`) VALUES
(1, 'Nội Dung', '.NET', 2000000, './image/net.jpg', 'Giới Thiệu', 2, 20, 3, 1),
(2, 'Nội Dung', 'ReactJS', 2000000, './image/reactjs.jpg', 'Giới Thiệu', 2, 20, 4, 2),
(3, 'Nội Dung', 'JAVA', 2000000, './image/java.jpg', 'Giới Thiệu', 2, 20, 4, 1),
(4, 'Nội Dung', 'VueJs', 2000000, './image/vuejs.jpg', 'Giới Thiệu', 2, 20, 4, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tb_cource_types`
--

CREATE TABLE `tb_cource_types` (
  `courceTypeId` int(11) NOT NULL,
  `courceTypeName` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tb_cource_types`
--

INSERT INTO `tb_cource_types` (`courceTypeId`, `courceTypeName`) VALUES
(1, 'Back-End'),
(2, 'Front-End');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tb_registers`
--

CREATE TABLE `tb_registers` (
  `registerId` int(11) NOT NULL,
  `endDate` date NOT NULL,
  `regisDate` date NOT NULL,
  `startDate` date NOT NULL,
  `accountId` int(11) NOT NULL,
  `studentId` int(11) NOT NULL,
  `statusId` int(11) NOT NULL,
  `courceId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tb_registers`
--

INSERT INTO `tb_registers` (`registerId`, `endDate`, `regisDate`, `startDate`, `accountId`, `studentId`, `statusId`, `courceId`) VALUES
(1, '2018-03-02', '2018-01-01', '2018-01-02', 1, 1, 1, 1),
(2, '2018-03-02', '2018-01-01', '2018-01-02', 2, 2, 2, 2),
(3, '0000-00-00', '2018-01-01', '0000-00-00', 0, 1, 0, 1),
(4, '2018-03-02', '2018-01-01', '2018-01-02', 2, 2, 0, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tb_status`
--

CREATE TABLE `tb_status` (
  `statusId` int(11) NOT NULL,
  `statusName` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tb_status`
--

INSERT INTO `tb_status` (`statusId`, `statusName`) VALUES
(1, 'Status1'),
(2, 'Status2');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tb_students`
--

CREATE TABLE `tb_students` (
  `studentId` int(11) NOT NULL,
  `birthday` date NOT NULL,
  `district` varchar(50) NOT NULL,
  `email` varchar(40) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `houseNum` varchar(50) NOT NULL,
  `image` varchar(255) NOT NULL,
  `phoneNumber` varchar(11) NOT NULL,
  `province` varchar(50) NOT NULL,
  `wards` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tb_students`
--

INSERT INTO `tb_students` (`studentId`, `birthday`, `district`, `email`, `fullname`, `houseNum`, `image`, `phoneNumber`, `province`, `wards`) VALUES
(1, '2000-11-11', 'Từ Sơn', 'abc@gmail.com', 'Kim Soo Hook', '1', './image/anh1', '012345566', 'Bắc Ninh', 'Châu Khê'),
(2, '2000-11-11', 'Từ Sơn', 'abc2@gmail.com', 'Lee Min Hoo', '1', './image/anh2', '012345567', 'Bắc Ninh', 'Châu Khê'),
(3, '2000-11-11', 'Từ Sơn', 'abc3@gmail.com', 'Kim Tan', '1', './image/anh3', '012345568', 'Bắc Ninh', 'Châu Khê'),
(4, '2000-11-11', 'Từ Sơn', 'abc4@gmail.com', 'Uzumaki Naruto', '1', './image/anh4', '012345569', 'Bắc Ninh', 'Châu Khê');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tb_themes`
--

CREATE TABLE `tb_themes` (
  `themeId` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `themeName` varchar(50) NOT NULL,
  `articleTypeId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tb_themes`
--

INSERT INTO `tb_themes` (`themeId`, `content`, `themeName`, `articleTypeId`) VALUES
(1, 'Con Trỏ', 'Kiến Thức Về Con Trỏ', 2),
(2, 'Lịch Nghỉ Tết', 'Thông Báo Nghỉ Tết', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `tb_accounts`
--
ALTER TABLE `tb_accounts`
  ADD PRIMARY KEY (`accountId`),
  ADD KEY `FKncjsl62nk0jboitjyu07ktngg` (`authorityId`);

--
-- Chỉ mục cho bảng `tb_articles`
--
ALTER TABLE `tb_articles`
  ADD PRIMARY KEY (`articleId`),
  ADD KEY `FKcjphgksner8mv22aftsj1lkkc` (`accountId`),
  ADD KEY `FKqks8m91y6oapy0qqafy0m3kde` (`themeId`);

--
-- Chỉ mục cho bảng `tb_article_types`
--
ALTER TABLE `tb_article_types`
  ADD PRIMARY KEY (`articleTypeId`);

--
-- Chỉ mục cho bảng `tb_authorities`
--
ALTER TABLE `tb_authorities`
  ADD PRIMARY KEY (`authorityId`);

--
-- Chỉ mục cho bảng `tb_cources`
--
ALTER TABLE `tb_cources`
  ADD PRIMARY KEY (`courceId`),
  ADD KEY `FKq43ss3qjfyoha5k9ikgy6y4qu` (`courceTypeId`);

--
-- Chỉ mục cho bảng `tb_cource_types`
--
ALTER TABLE `tb_cource_types`
  ADD PRIMARY KEY (`courceTypeId`);

--
-- Chỉ mục cho bảng `tb_registers`
--
ALTER TABLE `tb_registers`
  ADD PRIMARY KEY (`registerId`),
  ADD KEY `FKb93xf2vwkmr78amxd8o6cy687` (`studentId`),
  ADD KEY `FKjvk54c52k2do6ieq24pcqefkj` (`courceId`);

--
-- Chỉ mục cho bảng `tb_status`
--
ALTER TABLE `tb_status`
  ADD PRIMARY KEY (`statusId`);

--
-- Chỉ mục cho bảng `tb_students`
--
ALTER TABLE `tb_students`
  ADD PRIMARY KEY (`studentId`),
  ADD UNIQUE KEY `UKoo5kao1reeda7lq99s9hvvonv` (`email`,`phoneNumber`);

--
-- Chỉ mục cho bảng `tb_themes`
--
ALTER TABLE `tb_themes`
  ADD PRIMARY KEY (`themeId`),
  ADD KEY `FKjw37ev125vfwg6iw399wej9vu` (`articleTypeId`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `tb_accounts`
--
ALTER TABLE `tb_accounts`
  MODIFY `accountId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `tb_articles`
--
ALTER TABLE `tb_articles`
  MODIFY `articleId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `tb_article_types`
--
ALTER TABLE `tb_article_types`
  MODIFY `articleTypeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `tb_authorities`
--
ALTER TABLE `tb_authorities`
  MODIFY `authorityId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `tb_cources`
--
ALTER TABLE `tb_cources`
  MODIFY `courceId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `tb_cource_types`
--
ALTER TABLE `tb_cource_types`
  MODIFY `courceTypeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `tb_registers`
--
ALTER TABLE `tb_registers`
  MODIFY `registerId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `tb_status`
--
ALTER TABLE `tb_status`
  MODIFY `statusId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `tb_students`
--
ALTER TABLE `tb_students`
  MODIFY `studentId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `tb_themes`
--
ALTER TABLE `tb_themes`
  MODIFY `themeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `tb_accounts`
--
ALTER TABLE `tb_accounts`
  ADD CONSTRAINT `FKncjsl62nk0jboitjyu07ktngg` FOREIGN KEY (`authorityId`) REFERENCES `tb_authorities` (`authorityId`);

--
-- Các ràng buộc cho bảng `tb_articles`
--
ALTER TABLE `tb_articles`
  ADD CONSTRAINT `FKcjphgksner8mv22aftsj1lkkc` FOREIGN KEY (`accountId`) REFERENCES `tb_accounts` (`accountId`),
  ADD CONSTRAINT `FKqks8m91y6oapy0qqafy0m3kde` FOREIGN KEY (`themeId`) REFERENCES `tb_themes` (`themeId`);

--
-- Các ràng buộc cho bảng `tb_cources`
--
ALTER TABLE `tb_cources`
  ADD CONSTRAINT `FKq43ss3qjfyoha5k9ikgy6y4qu` FOREIGN KEY (`courceTypeId`) REFERENCES `tb_cource_types` (`courceTypeId`);

--
-- Các ràng buộc cho bảng `tb_registers`
--
ALTER TABLE `tb_registers`
  ADD CONSTRAINT `FKb93xf2vwkmr78amxd8o6cy687` FOREIGN KEY (`studentId`) REFERENCES `tb_cources` (`courceId`),
  ADD CONSTRAINT `FKeoe2ugj1tc1qb7spahudxvj9` FOREIGN KEY (`statusId`) REFERENCES `tb_status` (`statusId`),
  ADD CONSTRAINT `FKj988atr22ilex7qnyrdlje45p` FOREIGN KEY (`accountId`) REFERENCES `tb_accounts` (`accountId`),
  ADD CONSTRAINT `FKjvk54c52k2do6ieq24pcqefkj` FOREIGN KEY (`courceId`) REFERENCES `tb_students` (`studentId`);

--
-- Các ràng buộc cho bảng `tb_themes`
--
ALTER TABLE `tb_themes`
  ADD CONSTRAINT `FKjw37ev125vfwg6iw399wej9vu` FOREIGN KEY (`articleTypeId`) REFERENCES `tb_article_types` (`articleTypeId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
