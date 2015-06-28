package org.pixelgaffer.turnierserver.pdilemma

import java.util.ArrayList
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import org.pixelgaffer.turnierserver.gamelogic.interfaces.Ai
import org.pixelgaffer.turnierserver.gamelogic.interfaces.GameState

class PDGameState implements GameState<PDNextRound, PDResponse> {
	
	@Accessors(PUBLIC_GETTER, PRIVATE_SETTER)
	private List<List<Boolean>> responses = new ArrayList<List<Boolean>>
	private List<Integer> points = new ArrayList<Integer>
	
	@Accessors(PUBLIC_GETTER, PRIVATE_SETTER)
	private ArrayList<PDResponse> lastResponse = new ArrayList<PDResponse>
	
	new() {
		responses += new ArrayList<Boolean>
		responses += new ArrayList<Boolean>
		points += 0
		points += 0
		var noResponse = new PDResponse => [
			output = "Diese KI hat in der ersten Runde schon verloren"
			response = false
		]
		lastResponse += noResponse
		lastResponse += noResponse
	}
	
	override clearChanges(Ai ai) {}
	
	override getChanges(Ai ai) {
		new PDNextRound => [
			enemyResponse = responses.get(if(ai.index == 0) 1 else 0).last ?: false
			ownResponse = responses.get(if(ai.index == 0) 0 else 1).last ?: false
		]
	}
	
	override applyChanges(PDResponse response, Ai ai) {
		responses.get(ai.index) += response.response
		lastResponse.set(ai.index, response)
	}
	
	override applyChanges(PDNextRound changes) {
		responses.get(0) += changes.ownResponse
		responses.get(1) += changes.enemyResponse
		updatePoints
	}
	
	def getOwnChanges() {
		responses.get(0)
	}
	
	def getEnemyChanges() {
		responses.get(1)
	}
	
	def getOwnPoints() {
		points.get(0)
	}
	
	def getEnemyPoints() {
		points.get(1)
	}
	
	def getPoints(int player) {
		points.get(player)
	}
	
	def void updatePoints() {
		points.set(0, points.get(0) + getPoints(responses.get(0).last ?: false, responses.get(1).last ?: false))
		points.set(1, points.get(1) + getPoints(responses.get(1).last ?: false, responses.get(0).last ?: false))
	}
	
	def getPoints(boolean ownReaction, boolean enemyReaction) {
		if(enemyReaction && ownReaction) {
			return 2
		}
		if(!ownReaction && !enemyReaction) {
			return 4
		}
		if(ownReaction && !enemyReaction) {
			return 5
		}
		return 0
	}
}