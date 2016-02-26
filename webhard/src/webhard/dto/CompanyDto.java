package webhard.dto;

public class CompanyDto {
	private int companyNum;
	private String companyName;
	private String companyAddr;
	private String companyPhone;
	private String companyCreationDate;
	public CompanyDto(){
		
	}
	public CompanyDto(int companyNum, String companyName, String companyAddr,
			String companyPhone, String companyCreationDate) {
		super();
		this.companyNum = companyNum;
		this.companyName = companyName;
		this.companyAddr = companyAddr;
		this.companyPhone = companyPhone;
		this.companyCreationDate = companyCreationDate;
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
	public String getCompanyAddr() {
		return companyAddr;
	}
	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public String getCompanyCreationDate() {
		return companyCreationDate;
	}
	public void setCompanyCreationDate(String companyCreationDate) {
		this.companyCreationDate = companyCreationDate;
	}
	
	
}
