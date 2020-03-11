package kr.or.connect.reservation.controller;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.connect.reservation.dto.ImageFile;
import kr.or.connect.reservation.service.CommentService;

@Controller
public class FileController {
	
	@Autowired
	CommentService commentService;
	
	public static final String PATH = "/tmp/";
	public static final int FILE_LENGTH = 1116303;
	
	@RequestMapping(value = "/displayById", method = RequestMethod.GET)
	public @ResponseBody byte[] viewFileById(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
		
		ImageFile imgFile = commentService.getFile(Long.parseLong(id)).get(0);
		String saveFileName = imgFile.getSaveFileName();
		String fileName = saveFileName.substring(saveFileName.lastIndexOf('/') + 1);
		String contentType = imgFile.getContentType();
		
		if(saveFileName.charAt(0) != '/') { // 기존 데이터 저장경로 수정 
			saveFileName = PATH +  saveFileName;
		}
		
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + FILE_LENGTH);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
        InputStream fis = new FileInputStream(saveFileName);
        return IOUtils.toByteArray(fis);
	}
	
	
	@RequestMapping(value = "/displayByName", method = RequestMethod.GET)
	public @ResponseBody byte[] viewFileByName(@RequestParam("name") String imgUrl, HttpServletResponse response) throws Exception {
		
		String saveFileName = PATH +  imgUrl;
		String fileName = saveFileName.substring(saveFileName.lastIndexOf('/') + 1);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", "image/png");
        response.setHeader("Content-Length", "" + FILE_LENGTH);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
        InputStream fis = new FileInputStream(saveFileName);
        return IOUtils.toByteArray(fis);
	}
	
}
