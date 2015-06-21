package org.pixelgaffer.turnierserver.pdilemma.logic;

import com.google.gson.reflect.TypeToken;
import java.util.LinkedHashMap;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pair;
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
    LinkedHashMap<String, Integer> _xblockexpression = null;
    {
      ((PDGameState) this.gamestate).updatePoints();
      this.progress = ((this.playedRounds * 1.0) / this.maxTurns);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(this.playedRounds, "");
      _builder.append(" von ");
      _builder.append(this.maxTurns, "");
      _builder.append(" Runden gespielt");
      this.display = _builder.toString();
      List<? extends Ai> _ais = this.game.getAis();
      Ai _get = _ais.get(0);
      String _id = _get.getId();
      Integer _ownPoints = ((PDGameState) this.gamestate).getOwnPoints();
      Pair<String, Integer> _mappedTo = Pair.<String, Integer>of(_id, _ownPoints);
      List<? extends Ai> _ais_1 = this.game.getAis();
      Ai _get_1 = _ais_1.get(1);
      String _id_1 = _get_1.getId();
      Integer _enemyPoints = ((PDGameState) this.gamestate).getEnemyPoints();
      Pair<String, Integer> _mappedTo_1 = Pair.<String, Integer>of(_id_1, _enemyPoints);
      _xblockexpression = CollectionLiterals.<String, Integer>newLinkedHashMap(_mappedTo, _mappedTo_1);
    }
    return _xblockexpression;
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
