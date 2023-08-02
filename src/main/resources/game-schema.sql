DROP table if exists `game` CASCADE;
create table `game` (
    `id` INTEGER AUTO_INCREMENT,
    `genre` VARCHAR(255),
    `name` VARCHAR(200) NOT NULL,
    `year_of_release` INTEGER NOT NULL CHECK (year_of_release>=1900 AND year_of_release<=2100),
    primary key (id)
);