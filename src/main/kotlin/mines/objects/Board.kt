package mines.objects

import mines.objects.Cells.Cellule
import mines.objects.Cells.Mine
import mines.objects.Cells.Nombre
import mines.objects.Cells.Vide

data class Board(val sizeX: kotlin.Int, val sizeY: kotlin.Int) {			
	
	// Par dÃ©faut, le plateau est initialisÃ©
	var mines: Array<Array<Cellule?>?> = this.initBoard()
	
	/**
	 * initBoard() : initialise le plateau en crÃ©ant des sizeX * sizeY cellules.
	 * Celles-ci peuvent Ãªtre des mines, des cellules vides ou des cellules chiffres
	 */
	fun initBoard(): Array<Array<Cellule?>?> {
		var tableX : Array<Cellule?>
		var table : Array<Array<Cellule?>?>
				
		// Instantiation de la table globale
		table = arrayOfNulls<Array<Cellule?>>(this.sizeX)
		
		for(x in 0..this.sizeX-1) {
			
			// Instantiation de la ligne d'index x
			tableX = arrayOfNulls<Cellule>(this.sizeY)
			
			for (y in 0..this.sizeY-1) {
				
				// GÃ©nÃ©ration d'une valeur alÃ©atoire entre 1 et 4
				val random = (1..4).random()
				
				when {
					// Random entre 1 et 3 -> Nombre
					random in 1..3 -> tableX.set(y, Nombre(x, y, "0"))
					
					// Random = 2 -> Mine
					else -> tableX.set(y, Mine(x, y))
				}
			}
			// Ajout de la ligne Ã  la table globale
			table.set(x, tableX)
		}
		
		mines = table
		
		// Parcours de la table pour mise Ã  jour des voisins
		for(x in 0..this.sizeX - 1) {
			for(y in 0..this.sizeY - 1) {
				
				// Si la Cellule est de type Nombre
				if(table.get(x)?.get(y)?.toPrint == "0") {
					val nbVoisins : kotlin.Int = computeVoisins(x, y)
					
					// TODO : - si le nombre de voisins est supérieur à 0, alors on change l'affchage du Nombre pour afficher le nombre de mines voisines
					//		  - si le nombre de voisins est égal à 0, alors on dit que la cellule est en fait un Vide (voir class Cellule)
				}
			}
		}
		
		return table;
	}
	
	/**
	 * computeVoisins(posX : int, posY : int) : calcule le nombre de mines sur les cases
	 * voisines d'une Cellule de type Nombre
	 *
	 * Exemple (X = case Ã©tudiÃ©e, M = mine, C = cellule)
	 *
	 * M C M
	 * M X C
	 * C C M --> computeVoisins = 4
	 */
	fun computeVoisins(posX : kotlin.Int, posY : kotlin.Int) : kotlin.Int{
		var nbMines : kotlin.Int = 0;
		
		// TODO : - parcourir les cellules voisines de la cellule étudiée (posX, posY sur le plateau)
		//		  - vérifier si la cellule étudiée est une mine ou pas (son affichage pourrait être une bonne idée)
		//		  - retourner le nombre de mines voisines
		
		return nbMines;
	}
	
	/**
	 * computeFlags(posX : int, posY : int) : calcul le nombre de drapeaux sur les cases voisines
	 * On regarde sur les cases voisines si le Cellule.flag est ï¿½ true
	 *
	 * Exemple (X = case Ã©tudiÃ©e, ! = Flag)
	 *
	 * !   !
	 * ! 4  
	 *     ! --> computeFlags = 4
	 */
	fun computeFlags(posX : kotlin.Int, posY : kotlin.Int) : kotlin.Int{
		var nbFlags : kotlin.Int = 0;
		
		// TODO : De la même manière que pour compter le nombre de mines voisines, on va compter le nombre de flags voisins :
		//			- on regarde dans les cellules voisines si une cellule possède un flag
		//			- on retourne le nombre de flags rencontrés
		
		return nbFlags;
	}
	
	/**
	 * isWin() : vÃ©rifie si la partie est gagnÃ©e
	 * On vÃ©rifie pour chacune des Cellules prÃ©sentes (sauf les Mines) si elles sont cliquÃ©es ou non
	 * Si elles sont toutes cliquÃ©es -> partie gagnÃ©e
	 */
	fun isWin() : kotlin.Boolean {
		var ret : kotlin.Boolean = true
		
		for(posX in 0..this.sizeX - 1) {
			for(posY in 0..this.sizeY - 1) {
				var cell = mines.get(posX)?.get(posY)
				if(cell != null) {
					if(cell.toPrint != "*" && !cell.visible) {
						ret = false
						break
					}
				}
			}
		}
		
		return ret
	}
	
	/**
	 * isLost() : vÃ©rifie si la partie est perdue
	 * On vÃ©rifie pour chacune des mines prÃ©sentes si elles sont cliquÃ©es ou non
	 * Si une mine est cliquÃ©e -> partie perdue
	 */
	fun isLost() : kotlin.Boolean {
		var ret : kotlin.Boolean = false
		
		for(posX in 0..this.sizeX - 1) {
			for(posY in 0..this.sizeY - 1) {
				var cell = mines.get(posX)?.get(posY)
				if(cell != null) {
					if(cell.toPrint == "*" && cell.visible) {
						ret = true
					}
				}
			}
		}
		
		return ret
	}
	
	/**
	 * openVoisins(posX : int, posY : int) : Ouverture des cases voisines d'une Cellule
	 * On ouvre toutes les cases autour de la Cellule aux coordonï¿½es posX, posY
	 */
	fun openVoisins(posX : kotlin.Int, posY : kotlin.Int) {
		for(x in posX - 1..posX + 1) {
			for(y in posY - 1..posY + 1) {
				// On vï¿½rifie que la Cellule voisine est dans le tableau, diffï¿½rente de la Cellule de base et si elle n'est pas dï¿½jï¿½ ouverte
				if((x == posX && y == posY) || x < 0 || x >= this.sizeX || y < 0 || y >= this.sizeY) {
					// Si non, on continue
					continue
				} else {
					val cell = mines.get(x)?.get(y)
					
					if(cell != null) {
						// On regarde si la Cellule voisine est flag ou non
						if(cell.flag || cell.visible) {
							// Si elle est flag, on ne l'ouvre pas et on continue
							continue
						} else {
							// Si non, on l'ouvre
							clickCellule(x, y);
						}
					}
				}
				
			}
		}
	}
	
	/**
	 * clickCellule(posX : int, posY : int) : lancement d'un clic sur la cellule
	 * Clique sur la cellule de coordonï¿½es posX, posY et l'ouvre. Trois cas possibles :
	 * 	- Si c'est une Mine -> on passe Mine.visible ï¿½ true -> partie perdue
	 *	- Si c'est un Nombre -> on l'ouvre 
	 *	- Si c'est un Vide -> on ouvre toutes les Cellules voisines
	 *
	 * !! Une fois la fonction computeFlag() terminée !!
	 * De plus, si la Cellule est déjà visible, alors on ouvrira toutes les Cellules voisines si :
	 *	- La Cellule n'est ni une Mine, ni un Vide
	 *	- Le nombre de mines voisines = le nombre de Cellules avec un flag
	 */
	fun clickCellule(posX : kotlin.Int, posY : kotlin.Int) {
		
		// TODO : - on récupère la cellule aux coordonnées posX, posY
		//		  - on passe son état à visible
		//		  - si c'est un Vide, on ouvre ses voisins
		//
		// Attention : Kotlin n'autorise pas les actions sur un objet null, pensez donc à vérifier que ça ne l'est pas !
	}
	
	/**
	 * flagCellule(posX : int, posY : int) : Pose un flag sur une cellule
	 * On passe le Cellule.flag = true pour la Cellule aux coordonï¿½es posX, posY
	 */
	fun flagCellule(posX : kotlin.Int, posY : kotlin.Int) {
		
		// On rï¿½cupï¿½re la cellule aux coordonnï¿½es posX, posY
		val cell : Cellule? = mines.get(posX)?.get(posY)
		
		// On passe le flag de la Cellule ï¿½ true
		cell?.flag = true;
	}
	
	/**
	 * toString() : rÃ©Ã©criture de la fonction toString
	 * Affiche une copie du plateau
	 */
	override fun toString() : String {
		
		// Parcours du plateau
		for(posX in 0..this.sizeX-1) {
			
			// Retour charriot au début de la ligne
			print("".plus(posX).plus(" "));
			
			for(posY in 0..this.sizeY - 1) {
				print("|")
				
				var cell = mines.get(posX)?.get(posY)
				
				// Affichage de l a Cellule
				if(cell != null) {
					if(cell.visible) {
						print(cell.toPrint)
					} else if(cell.flag){
						print("!")
					} else {
						print(" ")
					}
				}
				print("|")
			}
			println()
		}
		println()
		println("   0  1  2  3  4  5  6  7 ")
		return "";
	}
}