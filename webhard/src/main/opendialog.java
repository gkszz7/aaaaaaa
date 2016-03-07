package main;

import user.AccessList;
import user.UserList;

import company.CompanyInsert;
import company.CompanyList;

import folder.FolderInsert;
import folder.FolderUpdate;

public class opendialog {

	private AccessList Al;
	private UserList ul;
	private CompanyList CompList;
	private CompanyInsert ci;
	private FolderInsert folderInsert;
	private FolderUpdate folderupdate;
	
	public opendialog(){
		
	}
	
	public opendialog(AccessList al, UserList ul, CompanyList compList,CompanyInsert ci, FolderInsert folderInsert,FolderUpdate folderupdate) {
		
		super();
		Al = al;
		this.ul = ul;
		CompList = compList;
		this.ci = ci;
		this.folderInsert = folderInsert;
		this.folderupdate = folderupdate;
	}
	public AccessList getAl() {
		return Al;
	}
	public void setAl(AccessList al) {
		Al = al;
	}
	public UserList getUl() {
		return ul;
	}
	public void setUl(UserList ul) {
		this.ul = ul;
	}
	public CompanyList getCompList() {
		return CompList;
	}
	public void setCompList(CompanyList compList) {
		CompList = compList;
	}
	public CompanyInsert getCi() {
		return ci;
	}
	public void setCi(CompanyInsert ci) {
		this.ci = ci;
	}
	public FolderInsert getFolderInsert() {
		return folderInsert;
	}
	public void setFolderInsert(FolderInsert folderInsert) {
		this.folderInsert = folderInsert;
	}
	public FolderUpdate getFolderupdate() {
		return folderupdate;
	}
	public void setFolderupdate(FolderUpdate folderupdate) {
		this.folderupdate = folderupdate;
	}
	
	

}
