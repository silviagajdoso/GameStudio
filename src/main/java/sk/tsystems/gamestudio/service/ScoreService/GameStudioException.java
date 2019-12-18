package sk.tsystems.gamestudio.service.ScoreService;

public class GameStudioException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GameStudioException() {
		super();
	}

	public GameStudioException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public GameStudioException(String arg0) {
		super(arg0);
	}

	public GameStudioException(Throwable arg0) {
		super(arg0);
	}
}
