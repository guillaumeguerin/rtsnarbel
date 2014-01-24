package gameframework.base;

public class MoveStrategyDefaultImpl implements MoveStrategy {
	public TravelVector getTravelVector() {
		return TravelVectorDefaultImpl.createNullVector();
	}
}
