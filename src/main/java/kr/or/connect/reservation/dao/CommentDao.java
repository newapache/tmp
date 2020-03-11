package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationSqls.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Comment;
import kr.or.connect.reservation.dto.CommentImage;

@Repository
public class CommentDao {

	private NamedParameterJdbcTemplate jdbc;
    private RowMapper<Comment> commentRowMapper = BeanPropertyRowMapper.newInstance(Comment.class);
    private RowMapper<CommentImage> commentImageRowMapper = BeanPropertyRowMapper.newInstance(CommentImage.class);
    SimpleJdbcInsert commentInsert;
    SimpleJdbcInsert commentImageInsert;
    

    public CommentDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        commentInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_user_comment")
                .usingGeneratedKeyColumns("id");
        
        commentImageInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_user_comment_image")
                .usingGeneratedKeyColumns("id");
    }
    
    public List<Comment> selectComment(Integer productId) {	 
		Map<String, Integer> params = new HashMap<>();
		params.put("productId", productId);
    return jdbc.query(SELECT_COMMENT, params, commentRowMapper);
    }
    
    public List<CommentImage> selectCommentImage(Integer commentId) {	 
		Map<String, Integer> params = new HashMap<>();
		params.put("commentId", commentId);
    return jdbc.query(SELECT_COMMENT_IMAGE, params, commentImageRowMapper);
    }
    
	public int selectCommentCount(Integer productId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("productId", productId);
		return jdbc.queryForObject(SELECT_COMMENT_COUNT, params, Integer.class);
	}
	
	
	public Long insertComment(Comment comment) {
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("product_id", comment.getProductId());
	    params.put("reservation_info_id", comment.getReservationInfoId());
	    params.put("score",comment.getScore());
	    params.put("comment", comment.getComment());
	    params.put("create_date", comment.getCreateDate());
	    params.put("modify_date", comment.getModifyDate());
	    Number id = commentInsert.executeAndReturnKey(params);
	    return id.longValue();
	}
	
	public Long insertCommentImage(CommentImage commentIamge) {
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("reservation_info_id", commentIamge.getReservationInfoId());
	    params.put("reservation_user_comment_id", commentIamge.getReservationUserCommentId());
	    params.put("file_id", commentIamge.getFileId());
	    Number id = commentImageInsert.executeAndReturnKey(params);
	    return id.longValue();
	}
	

	
    
}
