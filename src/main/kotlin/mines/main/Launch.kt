package mines.main

import mines.objects.Board
import java.util.Scanner

fun main(args: Array<String>) {
	val b : Board = Board(8, 8)
	
	while(!b.isWin() || !b.isLost()) {
		b.toString()
		println()
		
		val sc = Scanner(System.`in`)
		
		println("Que voulez vous faire ?")
		println("1 - Cliquer sur une case")
		println("2 - Placer un drapeau")
		
		val inputChoix = sc.nextInt()
		
		if(inputChoix == 1) {
			println("Quelle case à ouvrir ?")
					
			print("X : ")
			val inputX : kotlin.Int = sc.nextInt()
			if(inputX < 0 || inputX > b.sizeX) {
				continue
			}
			
			print("Y : ")
			val inputY : kotlin.Int = sc.nextInt()
			if(inputY < 0 || inputY > b.sizeY) {
				continue
			}
			
			println()
			b.clickCellule(inputX, inputY)
			
			if(b.isWin()) {
				println("Bravo !")
				
				println()
				println()
				b.toString()
				return;
			} else if(b.isLost()) {
				println("Perdu !")
				
				println()
				println()
				b.toString()	
				return;
			}
		} else if(inputChoix == 2){
			println("Sur quelle case ?")
					
			print("X : ")
			val inputX : kotlin.Int = sc.nextInt()
			if(inputX < 0 || inputX > b.sizeX) {
				continue
			}
			
			print("Y : ")
			val inputY : kotlin.Int = sc.nextInt()
			if(inputY < 0 || inputY > b.sizeY) {
				continue
			}
			
			println()
			b.flagCellule(inputX, inputY)
		} else {
			println()
			println("Veuillez entrer un choix valide")
			println()
		}
	}
}