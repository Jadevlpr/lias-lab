-- ====================================================================
-- LIAS - Script de mise à jour pour MySQL (Workbench)
-- ====================================================================

USE lias_db;

-- 1. Table equipes : ajout des colonnes acronyme et chef_id
ALTER TABLE equipes ADD COLUMN acronyme VARCHAR(50) AFTER nom;
ALTER TABLE equipes ADD COLUMN chef_id BIGINT AFTER description;

-- 2. Table evenements : ajout de lien_en_ligne
ALTER TABLE evenements ADD COLUMN lien_en_ligne VARCHAR(255) AFTER lieu;

-- 3. Table publications : ajout de journal_or_conf, lien_pdf et bibtex
ALTER TABLE publications ADD COLUMN journal_or_conf VARCHAR(255) AFTER annee;
ALTER TABLE publications ADD COLUMN lien_pdf VARCHAR(255) AFTER doi;
ALTER TABLE publications ADD COLUMN bibtex TEXT AFTER url;

-- 4. Table materiels : ajout des colonnes nom, numero_serie, categorie et localisation
ALTER TABLE materiels ADD COLUMN nom VARCHAR(200) AFTER id;
ALTER TABLE materiels ADD COLUMN numero_serie VARCHAR(120) AFTER nom;
ALTER TABLE materiels ADD COLUMN categorie VARCHAR(100) AFTER numero_serie;
ALTER TABLE materiels ADD COLUMN localisation VARCHAR(150) AFTER etat;

-- Synchronisation pour le mode 'safe update' de MySQL Workbench (avec id > 0)
UPDATE materiels SET nom = libelle WHERE id > 0 AND nom IS NULL AND libelle IS NOT NULL;
UPDATE materiels SET numero_serie = reference_materiel WHERE id > 0 AND numero_serie IS NULL AND reference_materiel IS NOT NULL;

-- 5. Vue de compatibilité 'documents'
CREATE OR REPLACE VIEW documents AS 
SELECT id, 
       nom_original AS titre, 
       chemin AS chemin_fichier, 
       categorie, 
       evenement_id, 
       ajoute_par, 
       version_document, 
       date_ajout 
FROM documents_laboratoire;
