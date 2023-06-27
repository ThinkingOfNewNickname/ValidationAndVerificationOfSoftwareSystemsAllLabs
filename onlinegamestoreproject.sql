
INSERT INTO `genre` (`genre_id`, `name`) VALUES
(1, 'Action'),
(2, 'Adventure'),
(3, 'Role-Playing');


INSERT INTO `game` (`game_id`, `description`, `image`, `name`, `price`, `genre_id`) VALUES
(1, 'Six Days in Fallujah is a realistic first-person tactical shooter based on true stories of Marines, Soldiers, and Iraqi civilians during the toughest urban battle since 1968.', '', 'Six Days in Fallujah', 740, 1),
(2, 'The only aim in Rust is to survive. Everything wants you to die - the island’s wildlife and other inhabitants, the environment, other survivors. Do whatever it takes to last another night.', '', 'Rust', 632, 2),
(3, 'Bethesda Game Studios, the award-winning creators of Fallout 3 and The Elder Scrolls V: Skyrim, welcome you to the world of Fallout 4 – their most ambitious game ever, and the next generation of open-world gaming.', '', 'Fallout 4', 115, 3);


INSERT INTO `user` (`id`, `email`, `password`, `role`, `username`) VALUES
(1, 'test@gmail.com', '123', 'ROLE_USER', 'test'),
(2, 'admin@gmail.com', '123', 'ROLE_ADMIN', 'admin');
