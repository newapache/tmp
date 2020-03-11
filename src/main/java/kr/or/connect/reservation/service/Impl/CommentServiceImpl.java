package kr.or.connect.reservation.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.CommentDao;
import kr.or.connect.reservation.dao.ImageFileDao;
import kr.or.connect.reservation.dto.Comment;
import kr.or.connect.reservation.dto.CommentImage;
import kr.or.connect.reservation.dto.ImageFile;
import kr.or.connect.reservation.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	CommentDao commentDao;
	
	@Autowired
	ImageFileDao imgfileDao;
	
	@Override
	public List<Comment> getComment(Integer productId){
		return commentDao.selectComment(productId);
	}
	
	@Override
	public List<CommentImage> getCommentImage(Integer commentId){
		return commentDao.selectCommentImage(commentId);
	}
	
	@Override	
	public int getCommentCount(Integer productId) {
		return commentDao.selectCommentCount(productId);
	}
	
	@Override
	public void addFileInfo(ImageFile imageFile) {
		Long fileInfoId = imgfileDao.insertFileInfo(imageFile);
		imageFile.setId(fileInfoId);
	}
	
	@Override
	public void addComment(Comment comment) {
		Long commentId = commentDao.insertComment(comment);
		comment.setCommentId(commentId);
	}
	
	@Override
	public void addCommentImage(Comment comment, ImageFile fileInfo) {
		CommentImage cimage = new CommentImage();
		cimage.setReservationInfoId(comment.getReservationInfoId());
		cimage.setReservationUserCommentId(comment.getCommentId());
		cimage.setFileId(fileInfo.getId());
		commentDao.insertCommentImage(cimage);
		cimage.setContentType(fileInfo.getContentType());
		cimage.setDeleteFlag(fileInfo.getDeleteFlag().toString());
		cimage.setFileName(fileInfo.getFileName());
		cimage.setSaveFileName(fileInfo.getSaveFileName());
		cimage.setCreateDate(fileInfo.getCreateDate());
		cimage.setModifyDate(fileInfo.getModifyDate());
		comment.setCommentImage(cimage);
	}
	
	@Override
	public List<ImageFile> getFile(Long imageId){
		return imgfileDao.selectFile(imageId);
	}
}
