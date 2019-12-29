package chezz.datastore;

import chezz.game.Game;
import java.util.*;

public interface GameStore {

  List<Game> getGamesByUserId(String userId);

  Optional<Game> getGameById(String gameId);

  void saveGame(Game game) throws Exception;

  List<String> getOpenGameIds();
}
