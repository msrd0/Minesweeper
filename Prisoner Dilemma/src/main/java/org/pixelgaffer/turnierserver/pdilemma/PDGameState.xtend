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
	
	new() {
		responses += new ArrayList<Boolean>
		responses += new ArrayList<Boolean>
		points += 0
		points += 0
	}
	
	override clearChanges(Ai ai) {}
	
	override getChanges(Ai ai) {
		new PDNextRound => [
			enemyResponse = responses.get(if(ai.index == 0) 1 else 0).last
			ownResponse = responses.get(if(ai.index == 0) 0 else 1).last
		]
	}
	
	override applyChanges(PDResponse response, Ai ai) {
		responses.get(ai.index) += response.response
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
		points.set(0, points.get(0) + getPoints(responses.get(0).last, responses.get(1).last))
		points.set(1, points.get(1) + getPoints(responses.get(1).last, responses.get(0).last))
	}
	
	def getPoints(Boolean ownReaction, Boolean enemyReaction) {
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