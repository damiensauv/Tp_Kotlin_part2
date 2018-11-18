package mines.objects

import mines.objects.Cells.Cellule
import mines.objects.Cells.Mine
import mines.objects.Cells.Nombre
import mines.objects.Cells.Vide

data class Board(val sizeX: kotlin.Int, val sizeY: kotlin.Int) {			
	
	// Par défaut, le plateau est initialisé
	var mines: Array<Array<Cellule?>?> = this.initBoard()
	
	/**
	 * initBoard() : initialise le plateau en créant des sizeX * sizeY cellules.
	 * Celles-ci peuvent être des mines, des cellules vides ou des cellules chiffres
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
				
				// Génération d'une valeur aléatoire entre 1 et 4
				val random = (1..4).random()
				
				when {
					// Random entre 1 et 3 -> Nombre
					random in 1..3 -> tableX.set(y, Nombre(x, y, "0"))
					
					// Random = 2 -> Mine
					else -> tableX.set(y, Mine(x, y))
				}
			}
			// Ajout de la ligne à la table globale
			table.set(x, tableX)
		}
		
		mines = table
		
		// Parcours de la table pour mise à jour des voisins
		for(x in 0..this.sizeX - 1) {
			for(y in 0..this.sizeY - 1) {
				
				// Si la Cellule est de type Nombre
				if(table.get(x)?.get(y)?.toPrint == "0") {
					val nbVoisins : kotlin.Int = computeVoisins(x, y)
					
					// TODO : - si le nombre de voisins est sup�rieur � 0, alors on change l'affchage du Nombre pour afficher le nombre de mines voisines
					//		  - si le nombre de voisins est �gal � 0, alors on dit que la cellule est en fait un Vide (voir class Cellule)
				}
			}
		}
		
		return table;
	}
	
	/**
	 * computeVoisins(posX : int, posY : int) : calcule le nombre de mines sur les cases
	 * voisines d'une Cellule de type Nombre
	 *
	 * Exemple (X = case étudiée, M = mine, C = cellule)
	 *
	 * M C M
	 * M X C
	 * C C M --> computeVoisins = 4
	 */
	fun computeVoisins(posX : kotlin.Int, posY : kotlin.Int) : kotlin.Int{
		var nbMines : kotlin.Int = 0;
		
		// TODO : - parcourir les cellules voisines de la cellule �tudi�e (posX, posY sur le plateau)
		//		  - v�rifier si la cellule �tudi�e est une mine ou pas (son affichage pourrait �tre une bonne id�e)
		//		  - retourner le nombre de mines voisines
		
		return nbMines;
	}
	
	/**
	 * computeFlags(posX : int, posY : int) : calcul le nombre de drapeaux sur les cases voisines
	 * On regarde sur les cases voisines si le Cellule.flag est � true
	 *
	 * Exemple (X = case étudiée, ! = Flag)
	 *
	 * !   !
	 * ! 4  
	 *     ! --> computeFlags = 4
	 */
	fun computeFlags(posX : kotlin.Int, posY : kotlin.Int) : kotlin.Int{
		var nbFlags : kotlin.Int = 0;
		
		// TODO : De la m�me mani�re que pour compter le nombre de mines voisines, on va compter le nombre de flags voisins :
		//			- on regarde dans les cellules voisines si une cellule poss�de un flag
		//			- on retourne le nombre de flags rencontr�s
		
		return nbFlags;
	}
	
	/**
	 * isWin() : vérifie si la partie est gagnée
	 * On vérifie pour chacune des Cellules présentes (sauf les Mines) si elles sont cliquées ou non
	 * Si elles sont toutes cliquées -> partie gagnée
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
	 * isLost() : vérifie si la partie est perdue
	 * On vérifie pour chacune des mines présentes si elles sont cliquées ou non
	 * Si une mine est cliquée -> partie perdue
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
	 * On ouvre toutes les cases autour de la Cellule aux coordon�es posX, posY
	 */
	fun openVoisins(posX : kotlin.Int, posY : kotlin.Int) {
		for(x in posX - 1..posX + 1) {
			for(y in posY - 1..posY + 1) {
				// On v�rifie que la Cellule voisine est dans le tableau, diff�rente de la Cellule de base et si elle n'est pas d�j� ouverte
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
	 * Clique sur la cellule de coordon�es posX, posY et l'ouvre. Trois cas possibles :
	 * 	- Si c'est une Mine -> on passe Mine.visible � true -> partie perdue
	 *	- Si c'est un Nombre -> on l'ouvre 
	 *	- Si c'est un Vide -> on ouvre toutes les Cellules voisines
	 *
	 * !! Une fois la fonction computeFlag() termin�e !!
	 * De plus, si la Cellule est d�j� visible, alors on ouvrira toutes les Cellules voisines si :
	 *	- La Cellule n'est ni une Mine, ni un Vide
	 *	- Le nombre de mines voisines = le nombre de Cellules avec un flag
	 */
	fun clickCellule(posX : kotlin.Int, posY : kotlin.Int) {
		
		// TODO : - on r�cup�re la cellule aux coordonn�es posX, posY
		//		  - on passe son �tat � visible
		//		  - si c'est un Vide, on ouvre ses voisins
		//
		// Attention : Kotlin n'autorise pas les actions sur un objet null, pensez donc � v�rifier que �a ne l'est pas !
	}
	
	/**
	 * flagCellule(posX : int, posY : int) : Pose un flag sur une cellule
	 * On passe le Cellule.flag = true pour la Cellule aux coordon�es posX, posY
	 */
	fun flagCellule(posX : kotlin.Int, posY : kotlin.Int) {
		
		// On r�cup�re la cellule aux coordonn�es posX, posY
		val cell : Cellule? = mines.get(posX)?.get(posY)
		
		// On passe le flag de la Cellule � true
		cell?.flag = true;
	}
	
	/**
	 * toString() : réécriture de la fonction toString
	 * Affiche une copie du plateau
	 */
	override fun toString() : String {
		
		// Parcours du plateau
		for(posX in 0..this.sizeX-1) {
			
			// Retour charriot au d�but de la ligne
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