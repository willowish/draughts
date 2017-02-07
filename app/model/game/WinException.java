package model.game;

public class WinException extends RuntimeException {
	private static final long serialVersionUID = -3668164963003770568L;
	private Player player;

	public WinException(Player player) {
		this.player = player;
	}

	public Player getWinningPlayer() {
		return player;
	}

}
