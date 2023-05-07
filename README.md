# IAv2
IA Java V2

# Installation et utilisation
1. Cloner le dépôt
2. Copier le fichier gradle.properties.dist en gradle.properties et configurer les variables
3. Lancer la tâche gradle shadowJar pour générer le jar
4. Modifier configCollection.json, config.json et table.tbl dans config/annee
5. Copier / coller le jar et les fichiers de config (configCollection.json, config.json, table0.tbl et table3000.tbl) ou lancer la tâche deployPrincess pour envoyer le jar sur la rasp
6. Enjoy

PS : Pour utiliser les tache gradle de deploy, il faut modifier build.gradle

# Configuration dans config/annee
## table.json
1. Définir la taille de la table et les couleurs
2. Définir la marge à ajouter aux zones interdites (demi-diagonale du robot avec un poil de marge)
3. Définir les zones interdites statiques avec des id. Bien marqué les zones de départ en start0 et start3000
4. Exécuter le main de Table.java pour générer les 2 fichiers tbl

## config.json / config_PMI.json
Configurer le fichier en fonction du contenu du robot

## configCollection.json
Vous pouvez l'écrire à la main pour les courageux, ou utiliser le générateur de strat

# Simulateur
1. Créer une nouvelle version du simulateur en copiant le précédent
2. Mettre à jour le SVG
3. Mettre à jour les png des robots
4. Placer les robots en position de départ dans la fonction init de index.html (attention, les axes X/Y sont inversés)
5. Compléter le fichier variables.js avec le retour du simulateur de stratégie (ou à la main pour les premiers tests)