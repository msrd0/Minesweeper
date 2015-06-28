package org.pixelgaffer.turnierserver.pdilemma.logic;

import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.pixelgaffer.turnierserver.gamelogic.TurnBasedGameLogic;
import org.pixelgaffer.turnierserver.gamelogic.interfaces.Ai;
import org.pixelgaffer.turnierserver.gamelogic.interfaces.GameState;
import org.pixelgaffer.turnierserver.pdilemma.PDGameState;
import org.pixelgaffer.turnierserver.pdilemma.PDResponse;
import org.pixelgaffer.turnierserver.pdilemma.logic.PDAiObject;
import org.pixelgaffer.turnierserver.pdilemma.logic.PDRenderData;

@SuppressWarnings("all")
public class PrisonersDilemmaLogic extends TurnBasedGameLogic<PDAiObject, PDResponse> {
  public PrisonersDilemmaLogic() {
    super(new TypeToken<PDResponse>() {
    });
  }
  
  @Override
  protected Object update() {
    PDRenderData _xblockexpression = null;
    {
      ((PDGameState) this.gamestate).updatePoints();
      this.progress = ((this.playedRounds * 1.0) / this.maxTurns);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(this.playedRounds, "");
      _builder.append(" von ");
      _builder.append(this.maxTurns, "");
      _builder.append(" Runden gespielt");
      this.display = _builder.toString();
      PDRenderData _pDRenderData = new PDRenderData();
      final Procedure1<PDRenderData> _function = (PDRenderData it) -> {
        List<? extends Ai> _ais = this.game.getAis();
        Ai _get = _ais.get(0);
        String firstId = _get.getId();
        List<? extends Ai> _ais_1 = this.game.getAis();
        Ai _get_1 = _ais_1.get(1);
        String secondId = _get_1.getId();
        PDGameState pdgs = ((PDGameState) this.gamestate);
        ArrayList<PDResponse> _lastResponse = pdgs.getLastResponse();
        PDResponse _get_2 = _lastResponse.get(0);
        Pair<String, String> _mappedTo = Pair.<String, String>of(firstId, _get_2.output);
        ArrayList<PDResponse> _lastResponse_1 = pdgs.getLastResponse();
        PDResponse _get_3 = _lastResponse_1.get(1);
        Pair<String, String> _mappedTo_1 = Pair.<String, String>of(secondId, _get_3.output);
        LinkedHashMap<String, String> _newLinkedHashMap = CollectionLiterals.<String, String>newLinkedHashMap(_mappedTo, _mappedTo_1);
        it.output = _newLinkedHashMap;
        Boolean _elvis = null;
        List<Boolean> _ownChanges = pdgs.getOwnChanges();
        Boolean _last = IterableExtensions.<Boolean>last(_ownChanges);
        if (_last != null) {
          _elvis = _last;
        } else {
          _elvis = Boolean.valueOf(false);
        }
        Pair<String, Boolean> _mappedTo_2 = Pair.<String, Boolean>of(firstId, _elvis);
        Boolean _elvis_1 = null;
        List<Boolean> _enemyChanges = pdgs.getEnemyChanges();
        Boolean _last_1 = IterableExtensions.<Boolean>last(_enemyChanges);
        if (_last_1 != null) {
          _elvis_1 = _last_1;
        } else {
          _elvis_1 = Boolean.valueOf(false);
        }
        Pair<String, Boolean> _mappedTo_3 = Pair.<String, Boolean>of(firstId, _elvis_1);
        LinkedHashMap<String, Boolean> _newLinkedHashMap_1 = CollectionLiterals.<String, Boolean>newLinkedHashMap(_mappedTo_2, _mappedTo_3);
        it.action = _newLinkedHashMap_1;
        Integer _elvis_2 = null;
        Integer _ownPoints = pdgs.getOwnPoints();
        if (_ownPoints != null) {
          _elvis_2 = _ownPoints;
        } else {
          _elvis_2 = Integer.valueOf(0);
        }
        Pair<String, Integer> _mappedTo_4 = Pair.<String, Integer>of(firstId, _elvis_2);
        Integer _elvis_3 = null;
        Integer _enemyPoints = pdgs.getEnemyPoints();
        if (_enemyPoints != null) {
          _elvis_3 = _enemyPoints;
        } else {
          _elvis_3 = Integer.valueOf(0);
        }
        Pair<String, Integer> _mappedTo_5 = Pair.<String, Integer>of(secondId, _elvis_3);
        LinkedHashMap<String, Integer> _newLinkedHashMap_2 = CollectionLiterals.<String, Integer>newLinkedHashMap(_mappedTo_4, _mappedTo_5);
        it.points = _newLinkedHashMap_2;
      };
      _xblockexpression = ObjectExtensions.<PDRenderData>operator_doubleArrow(_pDRenderData, _function);
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
