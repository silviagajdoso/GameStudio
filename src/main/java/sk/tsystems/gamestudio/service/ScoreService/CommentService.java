package sk.tsystems.gamestudio.service.ScoreService;

import java.util.List;

import sk.tsystems.gamestudio.entity.Comment;

public interface CommentService {

	void addComment(Comment comment);

	

	List<Comment> getComment(String game);

}
