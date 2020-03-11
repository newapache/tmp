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

import kr.or.connect.reservation.dto.ImageFile;

@Repository
public class ImageFileDao {
	
	SimpleJdbcInsert fileInfoInsert;
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ImageFile> fileRowMapper = BeanPropertyRowMapper.newInstance(ImageFile.class);

    public ImageFileDao(DataSource dataSource) {
    	fileInfoInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("file_info")
                .usingGeneratedKeyColumns("id");
    	this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }
    
	public Long insertFileInfo(ImageFile imageFile) {
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("file_name", imageFile.getFileName());
	    params.put("save_file_name", imageFile.getSaveFileName());
	    params.put("content_type", imageFile.getContentType());
	    params.put("delete_flag", imageFile.getDeleteFlag());
	    params.put("create_date", imageFile.getCreateDate());
	    params.put("modify_date", imageFile.getModifyDate());
	    Number id = fileInfoInsert.executeAndReturnKey(params);
	    return id.longValue();
	}
	
	public List<ImageFile> selectFile(Long imageId) {
		Map<String, Long> params = new HashMap<>();
		params.put("imageId", imageId);
		return jdbc.query(SELECT_FILE, params, fileRowMapper);
	}

}
