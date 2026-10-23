-- LIAS - Schéma complet de la plateforme
-- À exécuter une seule fois dans MySQL Workbench.

CREATE DATABASE IF NOT EXISTS lias_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lias_db;

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS utilisateurs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    actif BOOLEAN NOT NULL DEFAULT TRUE,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS utilisateur_roles (
    utilisateur_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (utilisateur_id, role_id),
    CONSTRAINT fk_utilisateur_role_utilisateur FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id),
    CONSTRAINT fk_utilisateur_role_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT IGNORE INTO roles (nom) VALUES
('ADMINISTRATEUR'), ('DIRECTEUR'), ('VICE_DIRECTEUR'), ('CHEF_EQUIPE'), ('MEMBRE'), ('DOCTORANT');

CREATE TABLE IF NOT EXISTS equipes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    axe_recherche VARCHAR(255),
    date_creation DATE,
    actif BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS membres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    utilisateur_id BIGINT NOT NULL UNIQUE,
    photo VARCHAR(255),
    date_naissance DATE,
    etablissement_origine VARCHAR(200),
    laboratoire_origine VARCHAR(200),
    biographie TEXT,
    centres_interet TEXT,
    date_embauche DATE,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_membre_utilisateur FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id)
);

CREATE TABLE IF NOT EXISTS affiliations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    membre_id BIGINT NOT NULL,
    statut ENUM('PERMANENT','ASSOCIE','DOCTORANT','RETRAITE','ANCIEN') NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE,
    CONSTRAINT fk_affiliation_membre FOREIGN KEY (membre_id) REFERENCES membres(id)
);

CREATE TABLE IF NOT EXISTS membre_equipe (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    membre_id BIGINT NOT NULL,
    equipe_id BIGINT NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE,
    CONSTRAINT fk_membre_equipe_membre FOREIGN KEY (membre_id) REFERENCES membres(id),
    CONSTRAINT fk_membre_equipe_equipe FOREIGN KEY (equipe_id) REFERENCES equipes(id)
);

CREATE TABLE IF NOT EXISTS mandats (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    membre_id BIGINT NOT NULL,
    equipe_id BIGINT,
    fonction ENUM('DIRECTEUR','VICE_DIRECTEUR','CHEF_EQUIPE') NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE,
    CONSTRAINT fk_mandat_membre FOREIGN KEY (membre_id) REFERENCES membres(id),
    CONSTRAINT fk_mandat_equipe FOREIGN KEY (equipe_id) REFERENCES equipes(id)
);

CREATE TABLE IF NOT EXISTS demandes_adhesion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    etablissement VARCHAR(200),
    motivation TEXT NOT NULL,
    cv_path VARCHAR(255),
    statut ENUM('EN_ATTENTE','ACCEPTEE','REFUSEE') NOT NULL DEFAULT 'EN_ATTENTE',
    date_demande TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    decision_par BIGINT,
    date_decision TIMESTAMP NULL,
    CONSTRAINT fk_demande_decideur FOREIGN KEY (decision_par) REFERENCES utilisateurs(id)
);

CREATE TABLE IF NOT EXISTS evenements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(200) NOT NULL,
    type ENUM('CONFERENCE','SEMINAIRE','WORKSHOP','REUNION','AUTRE') NOT NULL,
    description TEXT,
    lieu VARCHAR(200),
    date_debut DATETIME NOT NULL,
    date_fin DATETIME,
    statut ENUM('BROUILLON','PUBLIE','ARCHIVE') NOT NULL DEFAULT 'BROUILLON',
    createur_id BIGINT NOT NULL,
    CONSTRAINT fk_evenement_createur FOREIGN KEY (createur_id) REFERENCES utilisateurs(id)
);

CREATE TABLE IF NOT EXISTS evenement_organisateurs (
    evenement_id BIGINT NOT NULL,
    membre_id BIGINT NOT NULL,
    PRIMARY KEY (evenement_id, membre_id),
    CONSTRAINT fk_org_evenement FOREIGN KEY (evenement_id) REFERENCES evenements(id),
    CONSTRAINT fk_org_membre FOREIGN KEY (membre_id) REFERENCES membres(id)
);

CREATE TABLE IF NOT EXISTS publications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(300) NOT NULL,
    resume TEXT,
    type VARCHAR(80),
    annee SMALLINT NOT NULL,
    doi VARCHAR(150),
    url VARCHAR(255),
    equipe_id BIGINT,
    ajoute_par BIGINT NOT NULL,
    date_ajout TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_publication_equipe FOREIGN KEY (equipe_id) REFERENCES equipes(id),
    CONSTRAINT fk_publication_createur FOREIGN KEY (ajoute_par) REFERENCES utilisateurs(id)
);

CREATE TABLE IF NOT EXISTS publication_auteurs (
    publication_id BIGINT NOT NULL,
    membre_id BIGINT NOT NULL,
    ordre_auteur INT NOT NULL DEFAULT 1,
    PRIMARY KEY (publication_id, membre_id),
    CONSTRAINT fk_pub_auteur_publication FOREIGN KEY (publication_id) REFERENCES publications(id),
    CONSTRAINT fk_pub_auteur_membre FOREIGN KEY (membre_id) REFERENCES membres(id)
);

CREATE TABLE IF NOT EXISTS documents_laboratoire (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom_original VARCHAR(255) NOT NULL,
    chemin VARCHAR(255) NOT NULL,
    categorie ENUM('FINANCEMENT','PROGRAMME','ATTESTATION','RAPPORT','ADMINISTRATIF','PV','CONVENTION','AUTRE') NOT NULL,
    evenement_id BIGINT,
    ajoute_par BIGINT NOT NULL,
    version_document INT NOT NULL DEFAULT 1,
    date_ajout TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_document_evenement FOREIGN KEY (evenement_id) REFERENCES evenements(id),
    CONSTRAINT fk_document_auteur FOREIGN KEY (ajoute_par) REFERENCES utilisateurs(id)
);

CREATE TABLE IF NOT EXISTS materiels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(200) NOT NULL,
    reference_materiel VARCHAR(120),
    quantite_totale INT NOT NULL DEFAULT 0,
    quantite_disponible INT NOT NULL DEFAULT 0,
    date_arrivage DATE,
    etat VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS distributions_materiel (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    materiel_id BIGINT NOT NULL,
    membre_id BIGINT NOT NULL,
    quantite INT NOT NULL,
    date_distribution DATE NOT NULL,
    remarque VARCHAR(255),
    CONSTRAINT fk_distribution_materiel FOREIGN KEY (materiel_id) REFERENCES materiels(id),
    CONSTRAINT fk_distribution_membre FOREIGN KEY (membre_id) REFERENCES membres(id)
);

CREATE TABLE IF NOT EXISTS demandes_materiel (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    demandeur_id BIGINT NOT NULL,
    libelle VARCHAR(200) NOT NULL,
    justification TEXT,
    statut ENUM('EN_ATTENTE','ACCEPTEE','REFUSEE') NOT NULL DEFAULT 'EN_ATTENTE',
    date_demande TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    valide_par BIGINT,
    CONSTRAINT fk_besoin_demandeur FOREIGN KEY (demandeur_id) REFERENCES utilisateurs(id),
    CONSTRAINT fk_besoin_valideur FOREIGN KEY (valide_par) REFERENCES utilisateurs(id)
);

CREATE TABLE IF NOT EXISTS partenaires (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(200) NOT NULL,
    type VARCHAR(100),
    email VARCHAR(150),
    telephone VARCHAR(50),
    adresse VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS conventions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    partenaire_id BIGINT NOT NULL,
    titre VARCHAR(250) NOT NULL,
    objet TEXT,
    date_debut DATE,
    date_fin DATE,
    statut ENUM('BROUILLON','ACTIVE','EXPIREE','ARCHIVEE') NOT NULL DEFAULT 'BROUILLON',
    document_id BIGINT,
    CONSTRAINT fk_convention_partenaire FOREIGN KEY (partenaire_id) REFERENCES partenaires(id),
    CONSTRAINT fk_convention_document FOREIGN KEY (document_id) REFERENCES documents_laboratoire(id)
);

CREATE TABLE IF NOT EXISTS reunions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(200) NOT NULL,
    date_reunion DATETIME NOT NULL,
    lieu VARCHAR(150),
    ordre_du_jour TEXT,
    cree_par BIGINT NOT NULL,
    CONSTRAINT fk_reunion_createur FOREIGN KEY (cree_par) REFERENCES utilisateurs(id)
);

CREATE TABLE IF NOT EXISTS proces_verbaux (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reunion_id BIGINT NOT NULL UNIQUE,
    contenu TEXT,
    document_id BIGINT,
    date_publication TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pv_reunion FOREIGN KEY (reunion_id) REFERENCES reunions(id),
    CONSTRAINT fk_pv_document FOREIGN KEY (document_id) REFERENCES documents_laboratoire(id)
);

CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    utilisateur_id BIGINT NOT NULL,
    titre VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    lu BOOLEAN NOT NULL DEFAULT FALSE,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_notification_utilisateur FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id)
);

CREATE TABLE IF NOT EXISTS discussions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sujet VARCHAR(250) NOT NULL,
    equipe_id BIGINT,
    evenement_id BIGINT,
    cree_par BIGINT NOT NULL,
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_discussion_equipe FOREIGN KEY (equipe_id) REFERENCES equipes(id),
    CONSTRAINT fk_discussion_evenement FOREIGN KEY (evenement_id) REFERENCES evenements(id),
    CONSTRAINT fk_discussion_createur FOREIGN KEY (cree_par) REFERENCES utilisateurs(id)
);

CREATE TABLE IF NOT EXISTS messages_discussion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    discussion_id BIGINT NOT NULL,
    auteur_id BIGINT NOT NULL,
    contenu TEXT NOT NULL,
    date_envoi TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_message_discussion FOREIGN KEY (discussion_id) REFERENCES discussions(id),
    CONSTRAINT fk_message_auteur FOREIGN KEY (auteur_id) REFERENCES utilisateurs(id)
);

CREATE TABLE IF NOT EXISTS rapports_annuels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    annee SMALLINT NOT NULL UNIQUE,
    statut ENUM('BROUILLON','GENERE','VALIDE','ARCHIVE') NOT NULL DEFAULT 'BROUILLON',
    chemin_fichier VARCHAR(255),
    genere_par BIGINT,
    date_generation TIMESTAMP NULL,
    CONSTRAINT fk_rapport_generateurr FOREIGN KEY (genere_par) REFERENCES utilisateurs(id)
);

CREATE TABLE IF NOT EXISTS journal_audit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    utilisateur_id BIGINT,
    action VARCHAR(150) NOT NULL,
    entite VARCHAR(100) NOT NULL,
    entite_id BIGINT,
    details TEXT,
    date_action TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_utilisateur FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id)
);
