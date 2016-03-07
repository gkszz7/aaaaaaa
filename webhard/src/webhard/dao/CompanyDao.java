package webhard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import webhard.dto.CompanyDto;

public class CompanyDao {
	
	
	/**
	 * 가입 된 회사들의 정보 모두 출력
	 * 
	 */
	public List<CompanyDto> selectCompany(){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<CompanyDto> companys = new ArrayList<CompanyDto>();
        
        try {
			con=connection.conn();
			String sql="select companyNum, companyName, companyAddr, companyPhone, company_Creation_Date"
					+ " from company";
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			
			while(rs.next()){
				CompanyDto company = new CompanyDto();
				company.setCompanyNum(rs.getInt(1));
				company.setCompanyName(rs.getString(2));
				company.setCompanyAddr(rs.getString(3));
				company.setCompanyPhone(rs.getString(4));
				company.setCompanyCreationDate(rs.getString(5));
				
				companys.add(company);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return companys;
	}
	//회사 이름으로 회사 정보 찾기
	public CompanyDto getData(String companyName){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CompanyDto dto = new CompanyDto();
        try {
			con=connection.conn();
			String sql="select companyNum,companyName,companyAddr,companyPhone from company where companyName = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, companyName);
			rs=ps.executeQuery();
			if(rs.next()){
				dto.setCompanyNum(rs.getInt(1));
				dto.setCompanyName(rs.getString(2));
				dto.setCompanyAddr(rs.getString(3));
				dto.setCompanyPhone(rs.getString(4));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        return dto;
	}
	
	/**
	 * 새로운 회사 가입
	 * 
	 */
	public void entryNewCompany(String companyName, String companyAddr, String companyPhone){
		Connection con = null;
        PreparedStatement ps = null;
        
        try {
			con=connection.conn();
			String sql="insert into company (companyNum,companyName,companyAddr,companyPhone,company_creation_date) "
					+ "values (next value for companyNum, ?, ?, ?, CURRENT_DATE)";
			ps = con.prepareStatement(sql);
			ps.setString(1, companyName);
			ps.setString(2, companyAddr);
			ps.setString(3, companyPhone);

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
	}
	/**
	 * 
	 * 선택한 회사 삭제
	 */
	public void deleteCompany(String companyName){
		Connection con = null;
        PreparedStatement ps = null;
        
        try {
			con=connection.conn();
			String sql="delete from company where companyName = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, companyName);
			ps.executeUpdate();

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
	}
	/**
	 * 회사의 정보 수정
	 */
	public void updateCompany(int companyNum, String companyName, String companyAddr, String companyPhone){
		Connection con = null;
        PreparedStatement ps = null;
        
        try {
			con=connection.conn();
			String sql="update company set companyName = ?, companyAddr = ?, companyPhone = ? where companyNum = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, companyName);
			ps.setString(2, companyAddr);
			ps.setString(3, companyPhone);
			ps.setInt(4, companyNum);
			
			ps.executeUpdate();

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
	}
	/**
	 * 회사 추가 및 수정 시 이름 변경 할 때 중복 확인
	 */
	public boolean checkCompanyName(String companyName){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean check = false;
        try {
			con=connection.conn();
			String sql="select companyName from company where companyName=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, companyName);
			rs = ps.executeQuery();
			if(rs.next()){
				check = true;
			}else{
				check = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
		return check;
	}
	/**
	 * 파일 및 폴더 생성 시 파일,폴더와 연관시킬 회사 번호 출력
	 */
	public int selectCompanyNum(String companyName){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int num=0;
        try {
			con=connection.conn();
			String sql="select companyNum from company where companyName=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, companyName);
			rs = ps.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
		return num;
	}
	
	/**
	 * 회사 이름으로 검색
	 * 
	 */
	public List<CompanyDto> searchCompanybyName(String companyName){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String search=null;
        ArrayList<CompanyDto> companys = new ArrayList<CompanyDto>();
        int index = companyName.indexOf("*");
        String name =null;
        if(companyName.endsWith("*")){
        	name = companyName.substring(0, index);
        	search = name+"%";
        }else if(companyName.startsWith("*")){
        	name = companyName.substring(index+1);
        	search = "%"+name;
        }else if(companyName.contains("*") == false){
        	search = "%"+companyName+"%";
        }
        try {
			con=connection.conn();
			String sql="select companyNum, companyName, companyAddr, companyPhone, company_Creation_Date"
					+ " from company where companyName LIKE ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, search);
			rs=ps.executeQuery();
			
			while(rs.next()){
				CompanyDto company = new CompanyDto();
				company.setCompanyNum(rs.getInt(1));
				company.setCompanyName(rs.getString(2));
				company.setCompanyAddr(rs.getString(3));
				company.setCompanyPhone(rs.getString(4));
				company.setCompanyCreationDate(rs.getString(5));
				 
				companys.add(company);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return companys;
	}
	/**
	 * 회사 주소로 검색
	 * 
	 */
	public List<CompanyDto> searchCompanybyAddr(String companyAddr){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<CompanyDto> companys = new ArrayList<CompanyDto>();
        int index = companyAddr.indexOf("*");
        String search=null;
        String name =null;
        if(companyAddr.endsWith("*")){
        	name = companyAddr.substring(0, index);
        	search = name+"%";
        }else if(companyAddr.startsWith("*")){
        	name = companyAddr.substring(index+1);
        	search = "%"+name;
        }else if(companyAddr.contains("*") == false){
        	search = "%"+companyAddr+"%";
        }
        try {
			con=connection.conn();
			String sql="select companyNum, companyName, companyAddr, companyPhone, company_Creation_Date"
					+ " from company where companyAddr LIKE ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, search);
			rs=ps.executeQuery();
			
			while(rs.next()){
				CompanyDto company = new CompanyDto();
				company.setCompanyNum(rs.getInt(1));
				company.setCompanyName(rs.getString(2));
				company.setCompanyAddr(rs.getString(3));
				company.setCompanyPhone(rs.getString(4));
				company.setCompanyCreationDate(rs.getString(5));
				
				companys.add(company);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return companys;
	}
	/**
	 * 회사 전화번호로 검색
	 * 
	 */
	public List<CompanyDto> searchCompanybyPhone(String companyPhone){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<CompanyDto> companys = new ArrayList<CompanyDto>();
        int index = companyPhone.indexOf("*");
        String search=null;
        String name =null;
        if(companyPhone.endsWith("*")){
        	name = companyPhone.substring(0, index);
        	search = name+"%";
        }else if(companyPhone.startsWith("*")){
        	name = companyPhone.substring(index+1);
        	search = "%"+name;
        }else if(companyPhone.contains("*") == false){
        	search = "%"+companyPhone+"%";
        }
        try {
			con=connection.conn();
			String sql="select companyNum, companyName, companyAddr, companyPhone, company_Creation_Date"
					+ " from company where companyPhone LIKE ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, search);
			rs=ps.executeQuery();
			
			while(rs.next()){
				CompanyDto company = new CompanyDto();
				company.setCompanyNum(rs.getInt(1));
				company.setCompanyName(rs.getString(2));
				company.setCompanyAddr(rs.getString(3));
				company.setCompanyPhone(rs.getString(4));
				company.setCompanyCreationDate(rs.getString(5));
				
				companys.add(company);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return companys;
	}
	
}
