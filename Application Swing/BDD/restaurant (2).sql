-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 13 fév. 2023 à 11:29
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `restaurant`
--

-- --------------------------------------------------------

--
-- Structure de la table `adresse`
--

CREATE TABLE `adresse` (
  `id` int(11) NOT NULL,
  `id_client` int(11) DEFAULT NULL,
  `rue` varchar(100) DEFAULT NULL,
  `cod_postal` varchar(45) DEFAULT NULL,
  `ville` varchar(45) DEFAULT NULL,
  `complement` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `cat_produit`
--

CREATE TABLE `cat_produit` (
  `id` int(11) NOT NULL,
  `nom` varchar(60) DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `cat_produit`
--

INSERT INTO `cat_produit` (`id`, `nom`, `description`) VALUES
(1, 'Plat', 'les sushis, sashimi...'),
(2, 'Boisson', 'Coca, orangina, bieres, cocktails sans alcool'),
(3, 'Menu', 'composition de plats, desserts et boissons'),
(4, 'Dessert', 'lychee, glace...');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `numtel` varchar(10) DEFAULT NULL,
  `statut` varchar(45) DEFAULT 'Actif'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `nom`, `prenom`, `numtel`, `statut`) VALUES
(1, 'Moldoveanu', 'Laura', '0645781295', 'Actif');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `id` int(11) NOT NULL,
  `id_client` int(11) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `commentaire` varchar(150) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `date_comm` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `detaille_commande`
--

CREATE TABLE `detaille_commande` (
  `id` int(11) NOT NULL,
  `id_commande` int(11) DEFAULT NULL,
  `id_produit` int(11) DEFAULT NULL,
  `quantite` int(11) DEFAULT NULL,
  `prix_unitaire` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `etablissement`
--

CREATE TABLE `etablissement` (
  `id` int(11) NOT NULL,
  `nom` varchar(60) DEFAULT NULL,
  `siret` varchar(60) DEFAULT NULL,
  `rue` varchar(100) DEFAULT NULL,
  `cod_postal` varchar(45) DEFAULT NULL,
  `ville` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `etablissement`
--

INSERT INTO `etablissement` (`id`, `nom`, `siret`, `rue`, `cod_postal`, `ville`) VALUES
(1, 'ETE EDO - Paris 11E', '123456789087654', '14 RUE DE NICE', '75011', 'Paris');

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id` int(11) NOT NULL,
  `id_cat_produit` int(11) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `code` varchar(10) DEFAULT NULL,
  `nom` varchar(60) DEFAULT NULL,
  `type_statut` varchar(45) DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL,
  `prix` double NOT NULL,
  `date_mod` datetime DEFAULT NULL,
  `statut` varchar(45) DEFAULT 'Actif'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `id_cat_produit`, `id_user`, `code`, `nom`, `type_statut`, `description`, `prix`, `date_mod`, `statut`) VALUES
(2, 1, 1, 'P01', 'Poke1 Tempura', 'Chaud', 'PokeBowl crevette', 12, '2023-02-11 11:11:08', 'Actif'),
(3, 1, 1, 'P02', 'Poke1 Saumon', 'Chaud', 'Bla', 12, '2023-02-11 11:11:04', 'Actif'),
(4, 2, 1, 'P02', 'Coca', 'Froid', 'Boisson gazeuse', 3, '2023-02-11 11:11:19', 'Actif'),
(5, 4, 1, 'D01', 'coupe Lychee', 'Froid', 'Fruits en sirop', 4, '2023-02-11 11:11:23', 'Actif');

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `nom` varchar(60) DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL,
  `statut` varchar(45) DEFAULT 'Actif'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `role`
--

INSERT INTO `role` (`id`, `nom`, `description`, `statut`) VALUES
(1, 'Administrateur', 'description administration', 'Actif'),
(2, 'Vendeur', 'equipier polyvalent', 'Actif'),
(3, 'Cuisinier(e)', 'confection des plats chuds et froids', 'Actif');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `id_role` int(11) DEFAULT NULL,
  `nom` varchar(60) DEFAULT NULL,
  `prenom` varchar(60) DEFAULT NULL,
  `email` varchar(60) NOT NULL,
  `url` varchar(100) DEFAULT NULL,
  `password` varchar(150) NOT NULL,
  `statut` varchar(45) DEFAULT 'Active'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `id_role`, `nom`, `prenom`, `email`, `url`, `password`, `statut`) VALUES
(1, 1, 'Ambroise', 'Moris', 'test@test.com', 'null', '*23AE809DDACAF96AF0FD78ED04B6A265E05AA257', 'Actif'),
(2, 1, 'ADMIN', 'Manajer', 'email@email.com', '', '*23AE809DDACAF96AF0FD78ED04B6A265E05AA257', 'Actif'),
(3, 2, 'AQUINO M', 'José', 'mail@mail.com', '', '*23AE809DDACAF96AF0FD78ED04B6A265E05AA257', 'Actif');

-- --------------------------------------------------------

--
-- Structure de la table `user_etablis`
--

CREATE TABLE `user_etablis` (
  `id` int(11) NOT NULL,
  `id_etablissment` int(11) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `user_etablis`
--

INSERT INTO `user_etablis` (`id`, `id_etablissment`, `id_user`) VALUES
(3, 1, 1),
(4, 1, 2),
(5, 1, 3);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `adresse`
--
ALTER TABLE `adresse`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD KEY `fk_id_client_adress_idx` (`id_client`);

--
-- Index pour la table `cat_produit`
--
ALTER TABLE `cat_produit`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD KEY `fk_id_client_comm_idx` (`id_client`),
  ADD KEY `fk_id_user_comm_idx` (`id_user`);

--
-- Index pour la table `detaille_commande`
--
ALTER TABLE `detaille_commande`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD KEY `fk_id_commande_idx` (`id_commande`),
  ADD KEY `fk_id_produit_idx` (`id_produit`);

--
-- Index pour la table `etablissement`
--
ALTER TABLE `etablissement`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD KEY `fk_cat_produit_idx` (`id_cat_produit`),
  ADD KEY `fk_user_idx` (`id_user`);

--
-- Index pour la table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`),
  ADD KEY `fk_id_role_idx` (`id_role`);

--
-- Index pour la table `user_etablis`
--
ALTER TABLE `user_etablis`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD KEY `fk_id_user_idx` (`id_user`),
  ADD KEY `fk_id_etablissement_idx` (`id_etablissment`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `adresse`
--
ALTER TABLE `adresse`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `cat_produit`
--
ALTER TABLE `cat_produit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `detaille_commande`
--
ALTER TABLE `detaille_commande`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `etablissement`
--
ALTER TABLE `etablissement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `user_etablis`
--
ALTER TABLE `user_etablis`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `adresse`
--
ALTER TABLE `adresse`
  ADD CONSTRAINT `fk_id_client_adress` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `fk_id_client_comm` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_id_user_comm` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Contraintes pour la table `detaille_commande`
--
ALTER TABLE `detaille_commande`
  ADD CONSTRAINT `fk_id_commande` FOREIGN KEY (`id_commande`) REFERENCES `commande` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_id_produit` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `fk_cat_produit` FOREIGN KEY (`id_cat_produit`) REFERENCES `cat_produit` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Contraintes pour la table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_id_role` FOREIGN KEY (`id_role`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Contraintes pour la table `user_etablis`
--
ALTER TABLE `user_etablis`
  ADD CONSTRAINT `fk_id_etablissement` FOREIGN KEY (`id_etablissment`) REFERENCES `etablissement` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_id_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
