package org.pixelgaffer.turnierserver.pdilemma.ai;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import org.pixelgaffer.turnierserver.ailibrary.Ai;
import org.pixelgaffer.turnierserver.pdilemma.PDGameState;
import org.pixelgaffer.turnierserver.pdilemma.PDNextRound;
import org.pixelgaffer.turnierserver.pdilemma.PDResponse;

@SuppressWarnings("all")
public abstract class PrisonersDilemmaAi extends Ai<PDGameState, PDNextRound> {
  private PDGameState gameState = new PDGameState();
  
  public PrisonersDilemmaAi(final String[] args) {
    super(new TypeToken<PDNextRound>() {
    }, args);
  }
  
  @Override
  protected PDGameState getState(final PDNextRound change) {
    PDGameState _xblockexpression = null;
    {
      this.gameState.applyChanges(change);
      _xblockexpression = this.gameState;
    }
    return _xblockexpression;
  }
  
  @Override
  protected Object update(final PDGameState state) {
    PDResponse _xblockexpression = null;
    {
      PDResponse response = new PDResponse();
      boolean _respond = this.respond();
      response.response = _respond;
      String _string = this.output.toString();
      response.output = _string;
      this.clear(this.output);
      _xblockexpression = response;
    }
    return _xblockexpression;
  }
  
  /**
   * Gibt die Antwort der AI auf das Prisoner Dilemma in dieser Runde zurück
   */
  public abstract boolean respond();
  
  /**
   * Gibt die eigenen Punkte zurück
   */
  public int ownPoints() {
    return (this.gameState.getOwnPoints()).intValue();
  }
  
  /**
   * Gibt die gegnerischen Punkte zurück
   */
  public int enemyPoints() {
    return (this.gameState.getEnemyPoints()).intValue();
  }
  
  /**
   * Gibt eine Liste mit allen eigenen Antworten zurück (chronologisch)
   */
  public List<Boolean> getOwnResponses() {
    return this.gameState.getOwnChanges();
  }
  
  /**
   * Gibt eine Liste mit allen Antworten des Gegners zurück (chronologisch)
   */
  public List<Boolean> getEnemyResponses() {
    return this.gameState.getEnemyChanges();
  }
  
  /**
   * Gibt die eigene Antwort in dieser Runde zurück
   */
  public boolean getOwnResponse(final int round) {
    List<Boolean> _ownResponses = this.getOwnResponses();
    return (_ownResponses.get(round)).booleanValue();
  }
  
  /**
   * Gibt die gegnerische Antwort in dieser Runde zurück
   */
  public boolean getEnemyResponse(final int round) {
    List<Boolean> _enemyResponses = this.getEnemyResponses();
    return (_enemyResponses.get(round)).booleanValue();
  }
}
