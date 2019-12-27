package chezz.datastore;

import chezz.game.Game;
import java.util.*;

public interface GameStore {

  List<Game> getGames(String userId);

  Optional<Game> getGame(String gameId);

  void saveGame(Game game) throws Exception;

  List<String> getOpenGameIds();
}
