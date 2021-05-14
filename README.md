# IAv2
IA Java V2

# Installation et utilisation
1. Cloner le dépôt
2. Lancer la tâche gradle shadowJar pour générer le jar
3. Modifier configCollection.json, config.json et table.tbl à côté du jar
4. Copier / coller le jar et les fichiers de config (configCollection.json, config.json, table0.tbl et table3000.tbl) ou lancer la tâche deployPrincess pour envoyer le jar sur la rasp
5. Enjoy

# Configuration
## table.json
1. Définir la taille de la table et les couleurs
2. Définir la marge à ajouter aux zones interdites (demi-diagonale du robot avec un poil de marge)
3. Définir les zones interdites statiques avec des id. Bien marqué les zones de départ en start0 et start3000
4. Exécuter le main de Table.java pour générer les 2 fichiers tbl

## config.json
Configurer le fichier en fonction du contenu du robot

## configCollection.json
Vous pouvez l'écrire à la main pour les courageux, ou utiliser le générateur de strat