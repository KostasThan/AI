package games.spheregame.searchalgorithms;

import games.spheregame.model.INode;
import games.spheregame.model.SphereGame;

public class UCSAlgorithm implements SearchMethod{

	
	
	@Override
	public Integer apply(INode state,SphereGame game) {
		return state.getActualCost();
		
	}
	
}
