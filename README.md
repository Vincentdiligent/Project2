# Projet Android : Sûreté CHU Pellegrin Bordeaux
# Ce fichier README explique l’organisation du projet, la compilation et l’utilisation de l’application.

## Structure du projet
- `app/src/main/AndroidManifest.xml` : Déclaration des activités, permissions, services (FCM, Internet, etc.)
- `app/src/main/res/layout/` : Layouts XML pour chaque écran (connexion, carte, alerte, historique...)
- `app/src/main/res/values/strings.xml` : Toutes les chaînes en français
- `app/src/main/res/drawable/` : Illustrations, SVG, icônes
- `app/src/main/java/fr/chu_pellegrin/` : Code source de l’application (activités, fragments, ViewModels)
- `app/google-services.json` : Configuration Firebase (FCM)

## Compilation
- Ouvrir le dossier dans Android Studio
- Lancer la synchronisation Gradle
- Compiler et générer l’APK (aucune configuration supplémentaire requise)

## Fonctionnalités principales
- Connexion du personnel (immatriculation, mot de passe, gestion des rôles)
- Carte interactive de l’hôpital (OpenStreetMap ou SVG)
- Boutons d’alerte par bâtiment/étage (vol, agression, disparition, etc.)
- Notifications push (FCM)
- Historique, retours, notifications internes
- Interface moderne Material Design 3, thèmes dynamiques

## Librairies utilisées
- [osmdroid](https://github.com/osmdroid/osmdroid) : Carte OpenStreetMap
- [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging)
- [Material Components](https://m3.material.io/)

## Personnalisation graphique et thème
- Couleurs principales : bleu CHU (#0058A6), bleu foncé (#003366), vert CHU (#6DC067)
- Logo vectoriel personnalisé CHU Pellegrin (icône de l’application)
- Design ultra-moderne, Material Design 3, prise en charge automatique des thèmes clair/sombre

## Confidentialité
Aucune dépendance payante, aucun tracking, code open-source.

## Contact
Pour toute question, contactez la DSI du CHU Pellegrin Bordeaux.
