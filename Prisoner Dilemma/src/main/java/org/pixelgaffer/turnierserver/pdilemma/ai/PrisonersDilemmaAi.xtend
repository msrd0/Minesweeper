package org.pixelgaffer.turnierserver.pdilemma.ai

import com.google.gson.reflect.TypeToken
import java.util.List
import org.pixelgaffer.turnierserver.ailibrary.Ai
import org.pixelgaffer.turnierserver.pdilemma.PDGameState
import org.pixelgaffer.turnierserver.pdilemma.PDNextRound
import org.pixelgaffer.turnierserver.pdilemma.PDResponse

abstract class PrisonersDilemmaAi extends Ai<PDGameState, PDNextRound> {
	
	
	private var gameState = new PDGameState
	
	new(String[] args) {
		super(new TypeToken<PDNextRound>() {}, args)
	}
	
	override protected getState(PDNextRound change) {
		gameState.applyChanges(change)
		gameState
	}
	
	override protected update(PDGameState state) {
		var response = new PDResponse
		response.response = respond()
		response.output = output.toString
		output = new StringBuilder
		response
	}
	
	/**
	 * Gibt die Antwort der AI auf das Prisoner Dilemma in dieser Runde zurück
	 */
	def boolean respond()
	
	/**
	 * Gibt die eigenen Punkte zurück
	 */
	def int ownPoints() {
		gameState.ownPoints
	}
	
	/**
	 * Gibt die gegnerischen Punkte zurück
	 */
	def int enemyPoints() {
		gameState.enemyPoints
	}
	
	/** 
	 * Gibt eine Liste mit allen eigenen Antworten zurück (chronologisch)
	 */
	def List<Boolean> getOwnResponses() {
		gameState.ownChanges
	}
	
	/**
	 * Gibt eine Liste mit allen Antworten des Gegners zurück (chronologisch)
	 */
	def List<Boolean> getEnemyResponses() {
		gameState.enemyChanges
	}
	
	/**
	 * Gibt die eigene Antwort in dieser Runde zurück
	 */
	def boolean getOwnResponse(int round) {
		ownResponses.get(round)
	}
	
	/**
	 * Gibt die gegnerische Antwort in dieser Runde zurück
	 */
	def boolean getEnemyResponse(int round) {
		enemyResponses.get(round)
	}
	
}