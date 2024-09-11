INSERT INTO users
(created_at, email, first_name, last_name, updated_at, user_uuid, username)
VALUES
('2022-01-15 10:30:00', 'mario@mushroom.kingdom', 'Mario', 'Mario', '2022-03-20 14:45:00', '123e4567-e89b-12d3-a456-426614174000', 'superMario'),
('2022-02-28 09:15:00', 'link@hyrule.com', 'Link', 'Hero', '2022-04-10 11:30:00', '123e4567-e89b-12d3-a456-426614174001', 'heroOfTime'),
('2022-03-17 13:45:00', 'samus@galactic.fed', 'Samus', 'Aran', '2022-05-22 16:20:00', '123e4567-e89b-12d3-a456-426614174002', 'metroidHunter'),
('2022-04-05 11:00:00', 'sonic@greenhill.zone', 'Sonic', 'Hedgehog', '2022-06-18 09:10:00', '123e4567-e89b-12d3-a456-426614174003', 'blueBlazer'),
('2022-05-20 14:30:00', 'lara@croft-manor.uk', 'Lara', 'Croft', '2022-07-30 12:45:00', '123e4567-e89b-12d3-a456-426614174004', 'tombRaider'),
('2022-06-10 08:45:00', 'gordon@blackmesa.com', 'Gordon', 'Freeman', '2022-08-15 10:30:00', '123e4567-e89b-12d3-a456-426614174005', 'halfLife'),
('2022-07-22 16:00:00', 'masterchief@unsc.org', 'John', 'Chief', '2022-09-05 15:15:00', '123e4567-e89b-12d3-a456-426614174006', 'masterChief117'),
('2022-08-30 12:15:00', 'cloud@avalanche.com', 'Cloud', 'Strife', '2022-10-12 13:40:00', '123e4567-e89b-12d3-a456-426614174007', 'ex_SOLDIER'),
('2022-09-18 10:45:00', 'kratos@olympus.god', 'Kratos', 'Spartan', '2022-11-25 17:00:00', '123e4567-e89b-12d3-a456-426614174008', 'godOfWar'),
('2022-10-07 15:30:00', 'ellie@lastofus.com', 'Ellie', 'Williams', '2022-12-19 11:55:00', '123e4567-e89b-12d3-a456-426614174009', 'lastSurvivor');

INSERT INTO habits
(user_id, title, description, habit_frequency_type, frequency, start_date, updated_at, created_at)
VALUES
(1, 'Coin Collecting', 'Collect 100 coins daily for extra lives', 'daily', 1, '2023-01-01 08:00:00', '2023-01-01 08:00:00', '2023-01-01 08:00:00'),
(2, 'Master Sword Training', 'Practice sword skills to defeat Ganon', 'weekly', 3, '2023-01-02 09:00:00', '2023-01-02 09:00:00', '2023-01-02 09:00:00'),
(3, 'Suit Maintenance', 'Regular checks and upgrades on the Power Suit', 'monthly', 2, '2023-01-03 10:00:00', '2023-01-03 10:00:00', '2023-01-03 10:00:00'),
(4, 'Ring Collection', 'Gather rings for power-ups', 'daily', 2, '2023-01-04 11:00:00', '2023-01-04 11:00:00', '2023-01-04 11:00:00'),
(5, 'Artifact Research', 'Study ancient artifacts for upcoming expeditions', 'weekly', 2, '2023-01-05 12:00:00', '2023-01-05 12:00:00', '2023-01-05 12:00:00'),
(6, 'Crowbar Practice', 'Improve crowbar skills for unforeseen circumstances', 'daily', 1, '2023-01-06 13:00:00', '2023-01-06 13:00:00', '2023-01-06 13:00:00'),
(7, 'Shield Recharge', 'Ensure energy shields are at full capacity', 'daily', 3, '2023-01-07 14:00:00', '2023-01-07 14:00:00', '2023-01-07 14:00:00'),
(8, 'Materia Fusion', 'Experiment with new materia combinations', 'weekly', 1, '2023-01-08 15:00:00', '2023-01-08 15:00:00', '2023-01-08 15:00:00'),
(9, 'Rage Management', 'Meditation to control the Spartan Rage', 'daily', 2, '2023-01-09 16:00:00', '2023-01-09 16:00:00', '2023-01-09 16:00:00'),
(10, 'Stealth Training', 'Practice moving silently to avoid infected', 'weekly', 4, '2023-01-10 17:00:00', '2023-01-10 17:00:00', '2023-01-10 17:00:00');