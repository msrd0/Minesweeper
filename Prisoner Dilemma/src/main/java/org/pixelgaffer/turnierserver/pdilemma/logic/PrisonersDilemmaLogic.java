package org.pixelgaffer.turnierserver.pdilemma.logic;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import org.pixelgaffer.turnierserver.gamelogic.TurnBasedGameLogic;
import org.pixelgaffer.turnierserver.gamelogic.interfaces.Ai;
import org.pixelgaffer.turnierserver.gamelogic.interfaces.GameState;
import org.pixelgaffer.turnierserver.pdilemma.PDGameState;
import org.pixelgaffer.turnierserver.pdilemma.PDResponse;
import org.pixelgaffer.turnierserver.pdilemma.logic.PDAiObject;

@SuppressWarnings("all")
public class PrisonersDilemmaLogic extends TurnBasedGameLogic<PDAiObject, PDResponse> {
  public PrisonersDilemmaLogic() {
    super(new TypeToken<PDResponse>() {
    });
  }
  
  @Override
  protected Object update() {
    return ((PDGameState) this.gamestate).updatePoints();
  }
  
  @Override
  protected GameState<?, PDResponse> createGameState() {
    return new PDGameState();
  }
  
  @Override
  protected PDAiObject createUserObject(final Ai ai) {
    return new PDAiObject();
  }
  
  @Override
  protected void gameFinished() {
    List<? extends Ai> _ais = this.game.getAis();
    for (final Ai ai : _ais) {
      PDAiObject _userObject = this.getUserObject(ai);
      int _index = ai.getIndex();
      Integer _points = ((PDGameState) this.gamestate).getPoints(_index);
      _userObject.score = (_points).intValue();
    }
  }
  
  @Override
  protected void setup() {
    this.maxTurns = 200;
    List<? extends Ai> _ais = this.game.getAis();
    for (final Ai ai : _ais) {
      PDAiObject _userObject = this.getUserObject(ai);
      _userObject.millisLeft = (this.maxTurns * 10);
    }
  }
}
