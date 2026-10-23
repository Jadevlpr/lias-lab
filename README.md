# LIAS - Gestion de laboratoire

Application Jakarta EE (Servlets, JSP, JSTL, JDBC, MySQL) destinée à la gestion administrative et scientifique du laboratoire.

## Démarrage

1. Dans MySQL Workbench, exécuter `src/main/resources/database/schema.sql`.
2. Copier `src/main/resources/db.properties.example` vers `db.properties`, puis renseigner les paramètres MySQL locaux.
3. Exécuter `mvn clean package`.
4. Déployer le projet Maven `war` sur Tomcat 11.

## Modules couverts par le modèle de données

- Comptes, authentification, rôles et journal d'audit
- Membres, profils et historique d'affiliations
- Équipes, mandats et gouvernance
- Demandes d'adhésion
- Événements et organisateurs
- Publications et auteurs
- Documents et archivage
- Matériel, distributions et besoins
- Partenaires et conventions
- Réunions et procès-verbaux
- Discussions, notifications et données nécessaires au rapport annuel

## Sécurité

Les mots de passe sont hachés avec BCrypt. Ne jamais versionner `db.properties`.
