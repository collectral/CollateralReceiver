-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 10, 2018 at 07:57 PM
-- Server version: 10.1.28-MariaDB
-- PHP Version: 7.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `collectral_data`
--

-- --------------------------------------------------------

--
-- Table structure for table `conf`
--

CREATE TABLE `conf` (
  `ID` int(11) NOT NULL,
  `TYPE` varchar(250) NOT NULL,
  `VALUE` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `data_get`
--

CREATE TABLE `data_get` (
  `ID` int(11) NOT NULL,
  `DEVICEID` int(11) NOT NULL,
  `FORMID` int(11) NOT NULL,
  `JSONTEXT` text NOT NULL,
  `CDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `devices`
--

CREATE TABLE `devices` (
  `ID` int(11) NOT NULL,
  `DKEY` varchar(200) NOT NULL COMMENT 'Device Unique ID of device registration key in both key\\ses comes from device side ',
  `DESCRIPTION` varchar(250) DEFAULT NULL COMMENT 'Owner ID',
  `ADMINID` int(11) NOT NULL,
  `DID` varchar(200) DEFAULT NULL COMMENT 'Unique device ID ',
  `UDT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Update timestamp ',
  `COMPANY` varchar(250) NOT NULL COMMENT 'Company Name',
  `DT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Createion timestamp '
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `devices_group`
--

CREATE TABLE `devices_group` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(250) COLLATE utf8mb4_bin NOT NULL,
  `ADMINID` int(11) NOT NULL,
  `CDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Table structure for table `devices_group_connectaion`
--

CREATE TABLE `devices_group_connectaion` (
  `ID` int(11) NOT NULL,
  `DEVICEID` int(11) NOT NULL,
  `GROUPID` int(11) NOT NULL,
  `ADMINID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Table structure for table `forms`
--

CREATE TABLE `forms` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(200) NOT NULL COMMENT 'Form Display Name',
  `URL` text NOT NULL,
  `ADMINID` int(11) NOT NULL,
  `GROUPID` int(11) NOT NULL DEFAULT '0',
  `UDATE` int(11) NOT NULL DEFAULT '0' COMMENT 'Update Timestamp should be provided latest changes to device device provides timestamp of his latest update ',
  `ENABLED` int(11) NOT NULL DEFAULT '1' COMMENT 'If  0 then changing or creating ; Enabled Status 1  ;  disable dis 2',
  `CDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `forms_fields`
--

CREATE TABLE `forms_fields` (
  `ID` int(11) NOT NULL,
  `SID` int(11) NOT NULL COMMENT 'Section ID',
  `FID` int(11) NOT NULL COMMENT 'Form ID',
  `NAME` varchar(250) NOT NULL COMMENT 'Name Of the Field',
  `DVAL` varchar(250) NOT NULL COMMENT 'Default Start Value',
  `TYPE` int(11) NOT NULL COMMENT 'Filed Type',
  `MAND` int(11) NOT NULL COMMENT 'Mandatory Field',
  `DISPL` int(11) NOT NULL COMMENT 'Displayable ',
  `ADMINID` int(11) NOT NULL,
  `CDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `forms_sections`
--

CREATE TABLE `forms_sections` (
  `ID` int(11) NOT NULL,
  `FID` int(11) NOT NULL COMMENT 'Form ID to which belongs this section',
  `FP` int(11) NOT NULL DEFAULT '0' COMMENT 'First Page Section  ID if = 1',
  `NAME` varchar(250) NOT NULL,
  `ADMINID` int(11) NOT NULL,
  `CDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `UN` varchar(100) NOT NULL COMMENT 'login',
  `CM` varchar(254) DEFAULT NULL COMMENT 'Company Name',
  `PS` varchar(200) NOT NULL COMMENT 'Password',
  `CK` varchar(200) DEFAULT NULL COMMENT 'Cookie Key ',
  `EM` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT 'Email ',
  `ADMINID` int(11) NOT NULL DEFAULT '0',
  `DT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp of registration'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `conf`
--
ALTER TABLE `conf`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `data_get`
--
ALTER TABLE `data_get`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `devices`
--
ALTER TABLE `devices`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `devices_group`
--
ALTER TABLE `devices_group`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `devices_group_connectaion`
--
ALTER TABLE `devices_group_connectaion`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `forms`
--
ALTER TABLE `forms`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `forms_fields`
--
ALTER TABLE `forms_fields`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `SID` (`SID`),
  ADD KEY `FID` (`FID`);

--
-- Indexes for table `forms_sections`
--
ALTER TABLE `forms_sections`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FID` (`FID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `conf`
--
ALTER TABLE `conf`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `data_get`
--
ALTER TABLE `data_get`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `devices`
--
ALTER TABLE `devices`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `devices_group`
--
ALTER TABLE `devices_group`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `devices_group_connectaion`
--
ALTER TABLE `devices_group_connectaion`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `forms`
--
ALTER TABLE `forms`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `forms_fields`
--
ALTER TABLE `forms_fields`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `forms_sections`
--
ALTER TABLE `forms_sections`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
