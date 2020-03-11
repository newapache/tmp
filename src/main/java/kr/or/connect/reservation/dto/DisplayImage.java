package kr.or.connect.reservation.dto;

public class DisplayImage {
	private String contentType;
	private String createDate;
	private Long deleteFlag;
	private Long displayInfoId;
	private Long displayInfoImageId;
	private Long fileId;
	private String fileName;
	private String modifyDate;
	private String saveFileName;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Long deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Long getDisplayInfoId() {
		return displayInfoId;
	}

	public void setDisplayInfoId(Long displayInfoId) {
		this.displayInfoId = displayInfoId;
	}

	public Long getDisplayInfoImageId() {
		return displayInfoImageId;
	}

	public void setDisplayInfoImageId(Long displayInfoImageId) {
		this.displayInfoImageId = displayInfoImageId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	@Override
	public String toString() {
		return "DisplayImage [contentType=" + contentType + ", createDate=" + createDate + ", deleteFlag=" + deleteFlag
				+ ", displayInfoId=" + displayInfoId + ", displayInfoImageId=" + displayInfoImageId + ", fileId="
				+ fileId + ", fileName=" + fileName + ", modifyDate=" + modifyDate + ", saveFileName=" + saveFileName
				+ "]";
	}

}
