package webhard.dto;

public class FolderDto extends ItemDto{
	private int step;
	private int folderType;
	
	public FolderDto(int itemNum, String name, String date, int parentNum,
			String userId, int companyNum, int step, int folderType) {
		super(itemNum,name,date,parentNum,userId,companyNum);
		this.step = step;
		this.folderType = folderType;
	}
	
	public FolderDto(){
		
	}

	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getFolderType() {
		return folderType;
	}
	public void setFolderType(int folderType) {
		this.folderType = folderType;
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
