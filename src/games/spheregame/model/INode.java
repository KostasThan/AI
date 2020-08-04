package games.spheregame.model;

public interface INode extends Comparable<INode> {

	public void add(SphereGame game, int value);

	public SphereGame getGame();

	public int getAlgorithmCost();

	public int getActualCost();

	@Override
	public int compareTo(INode o);

	@Override
	public int hashCode();

	@Override
	public boolean equals(Object obj);

	@Override
	public String toString();
	

}
