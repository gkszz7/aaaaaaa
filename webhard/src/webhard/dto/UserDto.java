package webhard.dto;

public class UserDto {
	private String userId;
	private String passwd;
	private String userName;
	private String userPhone;
	private String userAddr;
	private int admin;
	private int access;
	private int companyNum;
	private String companyName;
	public UserDto(){
		
	}
	
	public UserDto(String userId, String passwd, String userName,
			String userPhone, String userAddr, int admin, int access,
			int companyNum) {
		super();
		this.userId = userId;
		this.passwd = passwd;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userAddr = userAddr;
		this.admin = admin;
		this.access = access;
		this.companyNum = companyNum;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserAddr() {
		return userAddr;
	}
	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	
	public int getAccess() {
		return access;
	}

	public void setAccess(int access) {
		this.access = access;
	}

	public int getCompanyNum() {
		return companyNum;
	}
	public void setCompanyNum(int companyNum) {
		this.companyNum = companyNum;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
