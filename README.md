# Partie 2

La classe principale pour cette partie sere la classe Board.kt, la class Launch.kt vous sera fournie dès le début afin de tester les fonctionnalités que vous implémenterez. Cependant, vous ne constaterez de changement qu'une fois la première méthode codée et opérationnelle.

Dans cette partie, nous allons configurer notre démineur pour qu'il soit fonctionnel et prêt à l'utilisation, en mode texte. Pour ce faire, nous allons tout d'abord configurer le plateau de jeu pour qu'il corresponde bien à un plateau de démineur. Pour cela, il va désormais falloir calculer le nombre de Mines voisines à une cellule pour pouvoir changer son affichage (en effet, quand un Nombre est entouré de 8 mines, il faut que ce Nombre affiche 8 et non 0 !). 
Il faut donc, pour cela, observer les cases voisines et vérifier que ce sont des Mines ou non (par cases voisines nous entendons les cases qui entourent la cellule, donc celles qui sont en haut, à droite, en bas, à gauche mais aussi dans les diagonales de la Cellule).

Il faudra ensuite mettre en place l'action du clic sur une cellule. Elle se décompose en 3 parties, selon le type de la Cellule sur laquelle on effectue le clic :
- Si c'est une Mine, la partie est perdue ;
- Si c'est un Nombre, alors on révèle la case ;
- Si c'est un Vide (nouveau type de Cellule que l'on vous rajoute gracieusement), alors on ouvre toutes les cases voisines.

Enfin, dans une partie de démineur, il est également possible de mettre un "flag" sur une Cellule lorsque l'on pense que cette Cellule est une mine. L'avantage de cette fonctionnalité est tel que lorsque l'on appuie sur un Nombre et qu'il possède autant de flags voisins que de mines voisines, on ouvre toutes les cases voisines qui ne sont pas flag.
Cela permet, entre autre, de ne pas avoir à ouvrir toutes les Cellules voisines qui ne sont pas des flags !

Une fois toutes ces fonctionnalités codées, vous devriez obtenir un démineur fonctionnel en mode texte ! (bon, on avoue que c'est pas le démineur le plus agréable à jouer ...)
