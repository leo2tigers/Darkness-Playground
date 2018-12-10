package logic;

public enum TileType {
	FLOOR ("FLOOR"), PLATFORM ("PLATFORM");
	
	private final String string;
	TileType(String string) {
		this.string = string;
	}
	
	@Override
	public String toString() {
		return this.string;
	}
}
