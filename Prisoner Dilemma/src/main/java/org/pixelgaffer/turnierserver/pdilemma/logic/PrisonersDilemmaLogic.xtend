package org.pixelgaffer.turnierserver.pdilemma.logic

import com.google.gson.reflect.TypeToken
import org.pixelgaffer.turnierserver.gamelogic.TurnBasedGameLogic
import org.pixelgaffer.turnierserver.gamelogic.interfaces.Ai
import org.pixelgaffer.turnierserver.pdilemma.PDGameState
import org.pixelgaffer.turnierserver.pdilemma.PDResponse

class PrisonersDilemmaLogic extends TurnBasedGameLogic<PDAiObject, PDResponse> {
	
	new() {
		super(new TypeToken<PDResponse>() {})
	}
	
	override protected update() {
		(gamestate as PDGameState).updatePoints
		progress = playedRounds * 1.0 / maxTurns
		display = '''«playedRounds» von «maxTurns» Runden gespielt'''
		newLinkedHashMap(game.ais.get(0).id -> (gamestate as PDGameState).ownPoints, game.ais.get(1).id -> (gamestate as PDGameState).enemyPoints)
	}
	
	override protected createGameState() {
		new PDGameState
	}
	
	override protected createUserObject(Ai ai) {
		new PDAiObject
	}
	
	override protected gameFinished() {
		for(ai : game.ais) {
			getUserObject(ai).score = (gamestate as PDGameState).getPoints(ai.index)
		}
	}
	
	override protected setup() {
		maxTurns = 200
		for(ai : game.ais) {
			getUserObject(ai).millisLeft = maxTurns * 10
		}
	}
	
}