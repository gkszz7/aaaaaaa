package webhard.dto;

public class ItemDto {
	private int itemNum;
	private String name;
	private String date;
	private int parentNum;
	private String userId;
	private int companyNum;
	
	public ItemDto(){
		
	}

	public ItemDto(int itemNum, String name, String date, int parentNum,
			String userId, int companyNum) {
		super();
		this.itemNum = itemNum;
		this.name = name;
		this.date = date;
		this.parentNum = parentNum;
		this.userId = userId;
		this.companyNum = companyNum;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getParentNum() {
		return parentNum;
	}

	public void setParentNum(int parentNum) {
		this.parentNum = parentNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(int companyNum) {
		this.companyNum = companyNum;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
}
