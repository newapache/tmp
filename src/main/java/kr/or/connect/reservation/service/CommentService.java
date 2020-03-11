package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.Comment;
import kr.or.connect.reservation.dto.CommentImage;
import kr.or.connect.reservation.dto.ImageFile;

public interface CommentService {
	public List<Comment> getComment(Integer productId);
	public List<CommentImage> getCommentImage(Integer commentId);
	public int getCommentCount(Integer productId);
	public void addFileInfo(ImageFile imageFile);
	public void addComment(Comment comment);
	public void addCommentImage(Comment comment, ImageFile fileInfo);
	public List<ImageFile> getFile(Long imageId);
}
