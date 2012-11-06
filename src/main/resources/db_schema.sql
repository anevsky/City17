CREATE TABLE IF NOT EXISTS `image` (
  `id` BIGINT NOT NULL auto_increment,
  `width` decimal(10,0) default NULL,
  `height` decimal(10,0) default NULL,
  `upload_date` datetime default NULL,
  `name` varchar(256) default NULL,
  `extension` varchar(5) default NULL,
  `alt` varchar(300) default NULL,
  `category` varchar(100) default NULL,
  `path` varchar(256) default NULL,
  `thumb_path` varchar(256) default NULL,
  `size` int(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT NOT NULL auto_increment,
  `first_name` varchar(100) default NULL,
  `password` varchar(100) default NULL,
  `email` varchar(100) default NULL,
  `display_name` varchar(100) default NULL,
  `age` int(3) default NULL,
  `registered_date` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ;

CREATE TABLE IF NOT EXISTS `gallery` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX(`name`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 ;

CREATE TABLE IF NOT EXISTS `image_gallery` (
  `image_id` BIGINT NOT NULL,
  `gallery_id` BIGINT NOT NULL,
  PRIMARY KEY (`image_id`,`gallery_id`),
  CONSTRAINT `fk_image_id` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`),
  CONSTRAINT `fk_gallery_id` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ;

CREATE TABLE IF NOT EXISTS `story` (
  `id` BIGINT NOT NULL auto_increment,
  `title` varchar(200) default NULL,
  `source` varchar(200) default NULL,
  `published_date` datetime default NULL,
  `content` MEDIUMTEXT,
  PRIMARY KEY  (`id`),
  INDEX(`title`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ;

CREATE TABLE IF NOT EXISTS `story_gallery` (
  `story_id` BIGINT NOT NULL,
  `gallery_id` BIGINT NOT NULL,
  PRIMARY KEY (`story_id`,`gallery_id`),
  CONSTRAINT `fk_story_id` FOREIGN KEY (`story_id`) REFERENCES `story` (`id`),
  CONSTRAINT `fk_gallery_id` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ;

CREATE TABLE IF NOT EXISTS `story_term` (
  `id` BIGINT NOT NULL auto_increment,
  `name` varchar(200) default NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ;

CREATE TABLE IF NOT EXISTS `story_term_relationship` (
  `story_id` BIGINT NOT NULL,
  `term_id` BIGINT NOT NULL,
  PRIMARY KEY (`story_id`,`term_id`),
  CONSTRAINT `fk_story_id` FOREIGN KEY (`story_id`) REFERENCES `story` (`id`),
  CONSTRAINT `fk_term_id` FOREIGN KEY (`term_id`) REFERENCES `story_term` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ;