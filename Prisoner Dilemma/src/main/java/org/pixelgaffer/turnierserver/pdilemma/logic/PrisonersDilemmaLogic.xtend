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
		new PDRenderData => [
			var firstId = game.ais.get(0).id
			var secondId = game.ais.get(1).id
			var pdgs = gamestate as PDGameState
			output = newLinkedHashMap(firstId -> pdgs.lastResponse.get(0).output, secondId -> pdgs.lastResponse.get(1).output)
			action = newLinkedHashMap(firstId -> (pdgs.ownChanges.last ?: false), secondId -> (pdgs.enemyChanges.last ?: false))
			points = newLinkedHashMap(firstId -> (pdgs.ownPoints ?: 0), secondId -> (pdgs.enemyPoints ?: 0))
		]
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