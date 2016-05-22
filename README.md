# AStar
Projet d'Algorithmie de 2° année - ESIPe - IR2
Projet réalisé par Jérémie COLLOMB et Valentin GRISET

## Présentation
Le projet propose une implémentation de l'algorithme A* (A star).
Cet algorithme permet de calculer le chemin le plus court d'un point A vers un point B.

## Compilation
Pour pouvoir compiler le projet, il est nécessaire d'importer la librairie (d'annotation) présente dans le dossier lib.

## Utilisation
Pour lancer l'application, rien de plus simple : lancer la classe fr.upem.algo.astar.Main
L'application prend 1 , 3 ou 5 paramètres :
- le nom du fichier contenant le graphe (au format tableau -- voir exemple)
- le nom du fichier et les coordonnées (x y) du point de départ
- le nom du fichier, les coordonnées (x y) du point de départ et du point d'arrivée

exemple :
```bash
java fr.upem.algo.astar.Main data/carte.txt
java fr.upem.algo.astar.Main data/carte.txt 0 0
java fr.upem.algo.astar.Main data/carte.txt 0 0 3 5
```
    

## Affichage
Voici un exemple de résultat qu'affiche l'application pour `java fr.upem.algo.astar.Main data/petit_2.map`
**Attention** : Les x sont sur l'axe des ordonnées (0 en haut et 9 tout en bas) ; Les y sont sur l'axe des abscisses (0 à gauche et 19 à droite)

```
2 3 1 2 2 1 1 2 1 3 3 2 2 1 2 3 3 2 # 1 
2 3 2 2 2 2 2 1 1 1 1 # # # # 3 3 3 # 1 
1 1 2 2 1 1 2 3 2 2 3 2 2 3 3 1 2 2 # 1 
3 3 1 3 1 1 1 1 1 2 1 1 2 3 3 3 1 1 2 3 
2 2 1 2 # 1 3 3 3 2 1 3 2 2 1 # 2 3 2 2 
3 3 2 2 # 1 3 3 3 3 2 3 1 1 3 # 3 3 3 3 
1 1 1 1 3 3 3 3 1 3 2 1 2 3 3 2 2 2 2 2 
1 3 2 3 3 3 1 3 2 1 2 2 3 3 2 1 2 3 1 3 
2 # # # # # # # # # # # # # # 3 2 1 3 2 
1 1 3 1 2 1 3 1 3 2 2 3 1 1 2 3 2 1 2 2 
Astar: 73 étapes
Chemin de lg. 29 allant de (0, 0) à (9, 19)
(0, 0) -> (1, 0) -> (2, 1) -> (3, 2) -> (4, 3) -> (3, 4) -> (4, 5) -> (3, 6) -> (3, 7) -> (3, 8) -> (4, 9) -> (5, 10) -> (6, 11) -> (5, 12) -> (5, 13) -> (6, 14) -> (7, 15) -> (8, 16) -> (9, 17) -> (9, 18) -> (9, 19)
```


## Classes

| Classe | Description |
| ---: | :--- |
| AdjGraph | Représentation en Matrice d'adjacence de notre graphe |
| Astar | Algorithme de calcul du plus court chemin (A*) |
| Edge | Représentation d'une arrête entre deux sommets (Vertix) |
| Graph | Interface répresentant un graph (en représentation Matrice ou Adjacente) |
| Main | Lancement de l'application et transformation du tableau en graphe |
| MatGraph | Représentation en Matrice de notre graphe |
| Parse | Classe permettant de parser notre fichier source pour le transformer en tableau |
| Vertex | Représentation d'un sommet |


## Informations diverses
L'algorithme utilise une heuristique calculée sur le principe de Manhatan.

La transformation du couple (x, y) en position est réalisée grâce au calcul suivant :
```
x * maxY + y
```
*x & y* coordonnées du point
*maxY* le nombre maximale de position que y peut prendre (alias le nombre de colonne)
