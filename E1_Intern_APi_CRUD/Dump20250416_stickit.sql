-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: utf8stickit
-- ------------------------------------------------------
-- Server version	8.0.41-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--
-- use utf8stickit;
-- use utf8stickit;
DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `locale` varchar(10) DEFAULT NULL,
  `order` int DEFAULT '999',
  `isDisplayed` tinyint(1) DEFAULT '1',
  `packageCount` varchar(45) DEFAULT '0',
  `createdDate` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
  
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `categories` VALUES 
(1, 'Động vật', 'animals', 'vi-VN', 1, 1, '5', 1714042800000),
(2, 'Hoạt hình', 'cartoon', 'vi-VN', 2, 1, '7', 1714042800000),
(3, 'Vui nhộn', 'funny', 'vi-VN', 3, 1, '10', 1714042800000),
(4, 'Tình yêu', 'love', 'vi-VN', 4, 1, '8', 1714042800000),
(5, 'Meme', 'meme', 'vi-VN', 5, 1, '12', 1714042800000),
(6, 'Animals', 'animals', 'en-US', 1, 1, '5', 1714042800000),
(7, 'Cartoon', 'cartoon', 'en-US', 2, 1, '7', 1714042800000),
(8, 'Funny', 'funny', 'en-US', 3, 1, '10', 1714042800000),
(9, 'Love', 'love', 'en-US', 4, 1, '8', 1714042800000),
(10, 'Meme', 'meme', 'en-US', 5, 1, '12', 1714042800000),
(11, 'Ngày lễ', 'holidays', 'vi-VN', 6, 1, '6', 1714042800000),
(12, 'Holidays', 'holidays', 'en-US', 6, 1, '6', 1714042800000),
(13, 'Âm nhạc', 'music', 'vi-VN', 7, 1, '4', 1714042800000),
(14, 'Music', 'music', 'en-US', 7, 1, '4', 1714042800000),
(15, 'Thể thao', 'sports', 'vi-VN', 8, 1, '5', 1714042800000),
(16, 'Sports', 'sports', 'en-US', 8, 1, '5', 1714042800000),
(17, 'Trò chơi', 'games', 'vi-VN', 9, 1, '6', 1714042800000),
(18, 'Games', 'games', 'en-US', 9, 1, '6', 1714042800000),
(19, 'Thức ăn', 'food', 'vi-VN', 10, 1, '7', 1714042800000),
(20, 'Food', 'food', 'en-US', 10, 1, '7', 1714042800000),
(21, 'Công việc', 'work', 'vi-VN', 11, 1, '5', 1714042800000),
(22, 'Work', 'work', 'en-US', 11, 1, '5', 1714042800000),
(23, 'Nghệ thuật', 'art', 'vi-VN', 12, 1, '4', 1714042800000),
(24, 'Art', 'art', 'en-US', 12, 1, '4', 1714042800000),
(25, 'Thiên nhiên', 'nature', 'vi-VN', 13, 1, '6', 1714042800000),
(26, 'Nature', 'nature', 'en-US', 13, 1, '6', 1714042800000),
(27, 'Du lịch', 'travel', 'vi-VN', 14, 1, '5', 1714042800000),
(28, 'Travel', 'travel', 'en-US', 14, 1, '5', 1714042800000),
(29, 'Thời trang', 'fashion', 'vi-VN', 15, 1, '3', 1714042800000),
(30, 'Fashion', 'fashion', 'en-US', 15, 1, '3', 1714042800000),
(31, 'Công nghệ', 'tech', 'vi-VN', 16, 1, '4', 1714042800000),
(32, 'Tech', 'tech', 'en-US', 16, 1, '4', 1714042800000),
(33, 'Phim', 'movies', 'vi-VN', 17, 1, '8', 1714042800000),
(34, 'Movies', 'movies', 'en-US', 17, 1, '8', 1714042800000),
(35, 'Sách', 'books', 'vi-VN', 18, 1, '3', 1714042800000),
(36, 'Books', 'books', 'en-US', 18, 1, '3', 1714042800000),
(37, 'Xe cộ', 'vehicles', 'vi-VN', 19, 1, '4', 1714042800000),
(38, 'Vehicles', 'vehicles', 'en-US', 19, 1, '4', 1714042800000),
(39, 'Thể dục', 'fitness', 'vi-VN', 20, 1, '5', 1714042800000),
(40, 'Fitness', 'fitness', 'en-US', 20, 1, '5', 1714042800000),
(41, 'Cảm xúc', 'emotions', 'vi-VN', 21, 1, '10', 1714042800000),
(42, 'Emotions', 'emotions', 'en-US', 21, 1, '10', 1714042800000),
(43, 'Chúc mừng', 'congratulations', 'vi-VN', 22, 1, '6', 1714042800000),
(44, 'Congratulations', 'congratulations', 'en-US', 22, 1, '6', 1714042800000),
(45, 'Giáo dục', 'education', 'vi-VN', 23, 1, '4', 1714042800000),
(46, 'Education', 'education', 'en-US', 23, 1, '4', 1714042800000),
(47, 'Thiên văn học', 'astronomy', 'vi-VN', 24, 1, '3', 1714042800000),
(48, 'Astronomy', 'astronomy', 'en-US', 24, 1, '3', 1714042800000),
(49, 'Chính trị', 'politics', 'vi-VN', 25, 0, '2', 1714042800000),
(50, 'Politics', 'politics', 'en-US', 25, 0, '2', 1714042800000);

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `packages`
--

DROP TABLE IF EXISTS `packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `creatorName` varchar(45) DEFAULT NULL,
  `stickerCount` int DEFAULT '0',
  `addWhatsapp` int DEFAULT '0',
  `addTelegram` int DEFAULT '0',
  `viewCount` int DEFAULT '0',
  `categoryIds` text,
  `isDisplayed` int DEFAULT '0',
  `createdDate` bigint DEFAULT NULL,
  `locale` varchar(10) DEFAULT NULL,
  `order` int DEFAULT '999',
  `isPremium` tinyint(1) DEFAULT '0',
  `isAnimated` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `packages` VALUES 
(1, 'Thú cưng đáng yêu', 'StickerArtist', 12, 1050, 890, 5600, '1,6', 1, 1714042800000, 'vi-VN', 1, 0, 0),
(2, 'Cute Pets', 'StickerArtist', 12, 980, 850, 5200, '6,1', 1, 1714042800000, 'en-US', 1, 0, 0),
(3, 'Hoạt hình vui nhộn', 'FunnyCreator', 15, 850, 720, 4800, '2,3', 1, 1714042800000, 'vi-VN', 2, 0, 0),
(4, 'Funny Cartoons', 'FunnyCreator', 15, 820, 700, 4600, '7,8', 1, 1714042800000, 'en-US', 2, 0, 0),
(5, 'Tình yêu lãng mạn', 'LoveDesigner', 10, 1200, 980, 6200, '4', 1, 1714042800000, 'vi-VN', 3, 0, 0),
(6, 'Romantic Love', 'LoveDesigner', 10, 1150, 950, 6000, '9', 1, 1714042800000, 'en-US', 3, 0, 0),
(7, 'Meme hài hước', 'MemeCreator', 20, 1500, 1300, 7800, '5,3', 1, 1714042800000, 'vi-VN', 4, 0, 0),
(8, 'Funny Memes', 'MemeCreator', 20, 1450, 1250, 7500, '10,8', 1, 1714042800000, 'en-US', 4, 0, 0),
(9, 'Động vật hoang dã', 'WildlifeArtist', 15, 890, 720, 4300, '1', 1, 1714042800000, 'vi-VN', 5, 0, 0),
(10, 'Wild Animals', 'WildlifeArtist', 15, 850, 700, 4100, '6', 1, 1714042800000, 'en-US', 5, 0, 0),
(11, 'Ngày lễ vui vẻ', 'HolidayCreator', 18, 980, 850, 5100, '11', 1, 1714042800000, 'vi-VN', 6, 0, 0),
(12, 'Happy Holidays', 'HolidayCreator', 18, 950, 820, 4900, '12', 1, 1714042800000, 'en-US', 6, 0, 0),
(13, 'Âm nhạc pop', 'MusicLover', 14, 750, 620, 3800, '13', 1, 1714042800000, 'vi-VN', 7, 0, 0),
(14, 'Pop Music', 'MusicLover', 14, 720, 600, 3600, '14', 1, 1714042800000, 'en-US', 7, 0, 0),
(15, 'Thể thao đỉnh cao', 'SportsFan', 16, 880, 750, 4200, '15', 1, 1714042800000, 'vi-VN', 8, 0, 0),
(16, 'Sports Champions', 'SportsFan', 16, 850, 730, 4000, '16', 1, 1714042800000, 'en-US', 8, 0, 0),
(17, 'Game thủ', 'GameDesigner', 17, 920, 780, 4500, '17', 1, 1714042800000, 'vi-VN', 9, 0, 0),
(18, 'Gamers', 'GameDesigner', 17, 900, 760, 4300, '18', 1, 1714042800000, 'en-US', 9, 0, 0),
(19, 'Ẩm thực Việt Nam', 'FoodArtist', 12, 680, 550, 3400, '19', 1, 1714042800000, 'vi-VN', 10, 0, 0),
(20, 'Vietnamese Cuisine', 'FoodArtist', 12, 650, 530, 3200, '20', 1, 1714042800000, 'en-US', 10, 0, 0),
(21, 'Văn phòng vui vẻ', 'WorkLife', 10, 580, 450, 2900, '21', 1, 1714042800000, 'vi-VN', 11, 0, 0),
(22, 'Happy Office', 'WorkLife', 10, 560, 430, 2700, '22', 1, 1714042800000, 'en-US', 11, 0, 0),
(23, 'Nghệ thuật đương đại', 'ArtLover', 8, 420, 350, 2100, '23', 1, 1714042800000, 'vi-VN', 12, 0, 0),
(24, 'Contemporary Art', 'ArtLover', 8, 400, 330, 2000, '24', 1, 1714042800000, 'en-US', 12, 0, 0),
(25, 'Phong cảnh Việt Nam', 'NatureLover', 14, 780, 650, 3900, '25', 1, 1714042800000, 'vi-VN', 13, 0, 0),
(26, 'Vietnamese Landscapes', 'NatureLover', 14, 750, 620, 3700, '26', 1, 1714042800000, 'en-US', 13, 0, 0),
(27, 'Du lịch khắp thế giới', 'Traveler', 16, 820, 690, 4100, '27', 1, 1714042800000, 'vi-VN', 14, 0, 0),
(28, 'World Travel', 'Traveler', 16, 800, 670, 3900, '28', 1, 1714042800000, 'en-US', 14, 0, 0),
(29, 'Thời trang hiện đại', 'FashionDesigner', 12, 680, 550, 3400, '29', 1, 1714042800000, 'vi-VN', 15, 1, 0),
(30, 'Modern Fashion', 'FashionDesigner', 12, 650, 530, 3200, '30', 1, 1714042800000, 'en-US', 15, 1, 0),
(31, 'Công nghệ tương lai', 'TechGeek', 10, 560, 430, 2800, '31', 1, 1714042800000, 'vi-VN', 16, 1, 0),
(32, 'Future Tech', 'TechGeek', 10, 540, 410, 2700, '32', 1, 1714042800000, 'en-US', 16, 1, 0),
(33, 'Phim Việt', 'MovieBuff', 15, 720, 590, 3600, '33', 1, 1714042800000, 'vi-VN', 17, 1, 0),
(34, 'Vietnamese Movies', 'MovieBuff', 15, 700, 570, 3500, '34', 1, 1714042800000, 'en-US', 17, 1, 0),
(35, 'Sách hay nên đọc', 'BookWorm', 10, 480, 390, 2400, '35', 1, 1714042800000, 'vi-VN', 18, 1, 1),
(36, 'Must Read Books', 'BookWorm', 10, 460, 370, 2300, '36', 1, 1714042800000, 'en-US', 18, 1, 1),
(37, 'Xe cộ Việt Nam', 'CarEnthusiast', 12, 520, 410, 2600, '37', 1, 1714042800000, 'vi-VN', 19, 1, 1),
(38, 'Vietnamese Vehicles', 'CarEnthusiast', 12, 500, 390, 2500, '38', 1, 1714042800000, 'en-US', 19, 1, 1),
(39, 'Thể dục thể thao', 'FitnessFan', 14, 680, 550, 3400, '39', 1, 1714042800000, 'vi-VN', 20, 1, 1),
(40, 'Fitness and Sports', 'FitnessFan', 14, 650, 530, 3200, '40', 1, 1714042800000, 'en-US', 20, 1, 1),
(41, 'Cảm xúc hàng ngày', 'EmotionArtist', 20, 980, 850, 4900, '41', 1, 1714042800000, 'vi-VN', 21, 1, 1),
(42, 'Daily Emotions', 'EmotionArtist', 20, 950, 820, 4700, '42', 1, 1714042800000, 'en-US', 21, 1, 1),
(43, 'Chúc mừng năm mới', 'CelebrationArtist', 15, 780, 650, 3900, '43', 1, 1714042800000, 'vi-VN', 22, 1, 1),
(44, 'Happy New Year', 'CelebrationArtist', 15, 750, 620, 3700, '44', 1, 1714042800000, 'en-US', 22, 1, 1),
(45, 'Giáo dục mầm non', 'EducationArtist', 12, 520, 410, 2600, '45', 1, 1714042800000, 'vi-VN', 23, 1, 1),
(46, 'Preschool Education', 'EducationArtist', 12, 500, 390, 2500, '46', 1, 1714042800000, 'en-US', 23, 1, 1),
(47, 'Vũ trụ kỳ thú', 'SpaceExplorer', 10, 450, 360, 2250, '47', 1, 1714042800000, 'vi-VN', 24, 0, 1),
(48, 'Amazing Universe', 'SpaceExplorer', 10, 430, 340, 2150, '48', 1, 1714042800000, 'en-US', 24, 0, 1),
(49, 'Chính trị Việt Nam', 'PoliticalArtist', 8, 320, 250, 1600, '49', 0, 1714042800000, 'vi-VN', 25, 0, 0),
(50, 'Vietnamese Politics', 'PoliticalArtist', 8, 300, 230, 1500, '50', 0, 1714042800000, 'en-US', 25, 0, 0);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stickers`
--

DROP TABLE IF EXISTS `stickers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stickers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` text,
  `packageId` int DEFAULT NULL,
  `locale` varchar(10) DEFAULT NULL,
  `order` int DEFAULT '999',
  `viewCount` int DEFAULT '0',
  `createdDate` bigint DEFAULT NULL,
  `emojis` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `isPremium` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idxPackageId` (`packageId`)
) ENGINE=InnoDB AUTO_INCREMENT=1558 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- Insert data into stickers table
INSERT INTO `stickers` VALUES 
(1, 'https://example.com/stickers/dog_happy.png', 1, 'vi-VN', 1, 1200, 1714042800000, '😊🐶', 0),
(2, 'https://example.com/stickers/cat_sleeping.png', 1, 'vi-VN', 2, 980, 1714042800000, '😴🐱', 0),
(3, 'https://example.com/stickers/bunny_carrot.png', 1, 'vi-VN', 3, 850, 1714042800000, '🐰🥕', 0),
(4, 'https://example.com/stickers/happy_dog.png', 2, 'en-US', 1, 1150, 1714042800000, '😊🐶', 0),
(5, 'https://example.com/stickers/sleeping_cat.png', 2, 'en-US', 2, 950, 1714042800000, '😴🐱', 0),
(6, 'https://example.com/stickers/bunny_with_carrot.png', 2, 'en-US', 3, 820, 1714042800000, '🐰🥕', 0),
(7, 'https://example.com/stickers/cartoon_character1.png', 3, 'vi-VN', 1, 780, 1714042800000, '😂👾', 0),
(8, 'https://example.com/stickers/cartoon_character2.png', 3, 'vi-VN', 2, 650, 1714042800000, '🤣👽', 0),
(9, 'https://example.com/stickers/cartoon_character3.png', 3, 'vi-VN', 3, 520, 1714042800000, '😆👻', 0),
(10, 'https://example.com/stickers/funny_cartoon1.png', 4, 'en-US', 1, 750, 1714042800000, '😂👾', 0),
(11, 'https://example.com/stickers/funny_cartoon2.png', 4, 'en-US', 2, 620, 1714042800000, '🤣👽', 0),
(12, 'https://example.com/stickers/funny_cartoon3.png', 4, 'en-US', 3, 500, 1714042800000, '😆👻', 0),
(13, 'https://example.com/stickers/heart_red.png', 5, 'vi-VN', 1, 1100, 1714042800000, '❤️', 0),
(14, 'https://example.com/stickers/love_couple.png', 5, 'vi-VN', 2, 950, 1714042800000, '💑', 0),
(15, 'https://example.com/stickers/love_letter.png', 5, 'vi-VN', 3, 820, 1714042800000, '💌', 0),
(16, 'https://example.com/stickers/red_heart.png', 6, 'en-US', 1, 1050, 1714042800000, '❤️', 0),
(17, 'https://example.com/stickers/couple_in_love.png', 6, 'en-US', 2, 920, 1714042800000, '💑', 0),
(18, 'https://example.com/stickers/love_message.png', 6, 'en-US', 3, 800, 1714042800000, '💌', 0),
(19, 'https://example.com/stickers/meme_funny1.png', 7, 'vi-VN', 1, 1300, 1714042800000, '🤣', 0),
(20, 'https://example.com/stickers/meme_funny2.png', 7, 'vi-VN', 2, 1150, 1714042800000, '😂', 0),
(21, 'https://example.com/stickers/funny_meme1.png', 8, 'en-US', 1, 1250, 1714042800000, '🤣', 0),
(22, 'https://example.com/stickers/funny_meme2.png', 8, 'en-US', 2, 1100, 1714042800000, '😂', 0),
(23, 'https://example.com/stickers/lion_roar.png', 9, 'vi-VN', 1, 780, 1714042800000, '🦁', 0),
(24, 'https://example.com/stickers/elephant_walk.png', 9, 'vi-VN', 2, 650, 1714042800000, '🐘', 0),
(25, 'https://example.com/stickers/roaring_lion.png', 10, 'en-US', 1, 750, 1714042800000, '🦁', 0),
(26, 'https://example.com/stickers/walking_elephant.png', 10, 'en-US', 2, 620, 1714042800000, '🐘', 0),
(27, 'https://example.com/stickers/christmas_tree.png', 11, 'vi-VN', 1, 880, 1714042800000, '🎄', 0),
(28, 'https://example.com/stickers/new_year_fireworks.png', 11, 'vi-VN', 2, 750, 1714042800000, '🎆', 0),
(29, 'https://example.com/stickers/xmas_tree.png', 12, 'en-US', 1, 850, 1714042800000, '🎄', 0),
(30, 'https://example.com/stickers/fireworks_ny.png', 12, 'en-US', 2, 720, 1714042800000, '🎆', 0),
(31, 'https://example.com/stickers/music_notes.gif', 13, 'vi-VN', 1, 680, 1714042800000, '🎵', 0),
(32, 'https://example.com/stickers/dancing_notes.gif', 14, 'en-US', 1, 650, 1714042800000, '🎵', 0),
(33, 'https://example.com/stickers/football_goal.gif', 15, 'vi-VN', 1, 780, 1714042800000, '⚽', 0),
(34, 'https://example.com/stickers/soccer_goal.gif', 16, 'en-US', 1, 750, 1714042800000, '⚽', 0),
(35, 'https://example.com/stickers/gaming_controller.gif', 17, 'vi-VN', 1, 820, 1714042800000, '🎮', 0),
(36, 'https://example.com/stickers/game_controller.gif', 18, 'en-US', 1, 800, 1714042800000, '🎮', 0),
(37, 'https://example.com/stickers/vietnam_pho.png', 19, 'vi-VN', 1, 580, 1714042800000, '🍜', 0),
(38, 'https://example.com/stickers/pho_bowl.png', 20, 'en-US', 1, 550, 1714042800000, '🍜', 0),
(39, 'https://example.com/stickers/office_fun.png', 21, 'vi-VN', 1, 480, 1714042800000, '💼😊', 0),
(40, 'https://example.com/stickers/fun_office.png', 22, 'en-US', 1, 460, 1714042800000, '💼😊', 0),
(41, 'https://example.com/stickers/art_painting.png', 23, 'vi-VN', 1, 350, 1714042800000, '🎨', 0),
(42, 'https://example.com/stickers/painting_art.png', 24, 'en-US', 1, 330, 1714042800000, '🎨', 0),
(43, 'https://example.com/stickers/halong_bay.png', 25, 'vi-VN', 1, 680, 1714042800000, '🏞️', 0),
(44, 'https://example.com/stickers/ha_long_bay.png', 26, 'en-US', 1, 650, 1714042800000, '🏞️', 0),
(45, 'https://example.com/stickers/world_travel.png', 27, 'vi-VN', 1, 720, 1714042800000, '✈️🌍', 0),
(46, 'https://example.com/stickers/travel_world.png', 28, 'en-US', 1, 700, 1714042800000, '✈️🌍', 0),
(47, 'https://example.com/stickers/fashion_model.gif', 29, 'vi-VN', 1, 580, 1714042800000, '👗', 1),
(48, 'https://example.com/stickers/model_fashion.gif', 30, 'en-US', 1, 550, 1714042800000, '👗', 1),
(49, 'https://example.com/stickers/future_tech.gif', 31, 'vi-VN', 1, 480, 1714042800000, '🤖', 1),
(50, 'https://example.com/stickers/tech_future.gif', 32, 'en-US', 1, 460, 1714042800000, '🤖', 1);

/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
-- Dump completed on 2025-05-12 11:09:02
