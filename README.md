# Gestion_Options_JWT

Service web RESTful développé avec **JAX-RS (Jersey)** et sécurisé par des jetons **JWT (JSON Web Token)** et un mécanisme de **Refresh Token** (rotation de jetons).

## 🚀 Fonctionnalités

- **Gestion des Options & Étudiants (CRUD complet)** :
  - Consultation, ajout, mise à jour et suppression des options et étudiants.
  - Formats supportés : `JSON` et `XML`.
- **Authentification & Sécurité JWT** :
  - Endpoint d'authentification (`/rest/authentication`).
  - Génération d'un **Access Token** (courte durée, 15 min) signé avec **HMAC-SHA512 (HS512)** via `jjwt`.
  - Génération d'un **Refresh Token** (longue durée, 7 jours) à usage unique (rotation de tokens avec identifiant `jti`).
  - Protection des endpoints avec l'annotation personnalisée `@Secured` et un filtre `ContainerRequestFilter` (`AuthenticationFilter`).
  - Renouvellement de session sans ressaisie du mot de passe via `/rest/authentication/refresh`.
  - Déconnexion et révocation de jeton via `/rest/authentication/logout`.

## 🛠️ Technologies utilisées

- **Java 17**
- **JAX-RS / Jersey 2.27**
- **JJWT (io.jsonwebtoken) 0.6.0**
- **Apache Tomcat 8.5+ / 9.0+**
- **Maven**
- **Jackson & JAXB**

## 📖 Endpoints Principaux

| Méthode | Endpoint | Protection | Description |
|---|---|---|---|
| `POST` | `/rest/authentication` | Publique | Connexion : retourne `accessToken` et `refreshToken` |
| `POST` | `/rest/authentication/refresh` | Publique | Rafraîchit les tokens via `refreshToken` |
| `POST` | `/rest/authentication/logout` | Publique | Révocation du refresh token (déconnexion) |
| `GET` | `/rest/options` | `@Secured` | Liste des options (requiert `Authorization: Bearer <token>`) |
| `POST` | `/rest/options` | `@Secured` | Ajout d'une option |
| `GET` | `/rest/options/{code}` | `@Secured` | Détails d'une option |
| `PUT` | `/rest/options/{code}` | `@Secured` | Mise à jour d'une option |
| `DELETE` | `/rest/options/{code}` | `@Secured` | Suppression d'une option |
| `GET` | `/rest/etudiants` | `@Secured` | Liste des étudiants |
| `GET` | `/rest/etudiants/{id}` | `@Secured` | Détails d'un étudiant |
