package webhard.dto;

public class FileDto extends ItemDto{
	
	private String fileURL;
	private String fileSize;
	private String fileType;
	
	public FileDto(){
		
	}

	public FileDto(int itemNum, String name, String date, int parentNum,
			String userId, int companyNum, String fileURL, String fileSize, String fileType) {
		super(itemNum,name,date,parentNum,userId,companyNum);
		this.fileURL = fileURL;
		this.fileSize = fileSize;
		this.fileType = fileType;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	@Override
	public int getItemNum() {
		// TODO Auto-generated method stub
		return super.getItemNum();
	}
	@Override
	public void setItemNum(int itemNum) {
		// TODO Auto-generated method stub
		super.setItemNum(itemNum);
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		super.setName(name);
	}
	@Override
	public String getDate() {
		// TODO Auto-generated method stub
		return super.getDate();
	}
	@Override
	public void setDate(String date) {
		// TODO Auto-generated method stub
		super.setDate(date);
	}
	@Override
	public int getParentNum() {
		// TODO Auto-generated method stub
		return super.getParentNum();
	}
	@Override
	public void setParentNum(int parentNum) {
		// TODO Auto-generated method stub
		super.setParentNum(parentNum);
	}
	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return super.getUserId();
	}
	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		super.setUserId(userId);
	}
	@Override
	public int getCompanyNum() {
		// TODO Auto-generated method stub
		return super.getCompanyNum();
	}
	@Override
	public void setCompanyNum(int companyNum) {
		// TODO Auto-generated method stub
		super.setCompanyNum(companyNum);
	}
}
