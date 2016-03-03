package webhard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import webhard.dto.CompanyDto;
import webhard.dto.UserDto;

public class UserDao {
	
    /*
     * �α���
     */
	public int loginUser(String userId, String passwd){
//      Statement stmt=null; 												//create table � ����
		Connection con = null;
        PreparedStatement ps = null;//�Ϲ� insert update ��
        ResultSet rs = null;
        int check = -1;			//1=�α��� ���� 0=��й�ȣ Ʋ�� -1= ���̵� Ʋ��
        String checkPass=null;
        
        try {
			con=connection.conn();
			String sql="select passwd from users where userId=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			if(rs.next()){
				checkPass=rs.getString("passwd");
				if(checkPass.equals(passwd)){
					check=1;
				}else{
					check=0;
				}
			}else{
				check=-1;
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
	
	/*
	 * ȸ������
	 */
	public void entryNewUser(String userId, String passwd, String userName, String userPhone,
								String userAddr, int companyNum){
		Connection con = null;
        PreparedStatement ps = null;
        
        try {
			con=connection.conn();
			String sql="insert into users (userId,passwd,userName,userPhone,userAddr,companyNum) "
					+ "values (?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, passwd);
			ps.setString(3, userName);
			ps.setString(4, userPhone);
			ps.setString(5, userAddr);
			ps.setInt(6, companyNum);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
	}
	/*
	 * ȸ������ �� ���̵� �ߺ� Ȯ��
	 */
	public boolean checkUserId(String userId){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean check = false;
        try {
			con=connection.conn();
			String sql="select * from users where userId=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			if(rs.next()){
				check = true;
			} else {
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
	/*
	 * ����ȭ�鿡�� �α��� �� ������ �̸� ��� ��
	 */
	public String getUserName(String userId){
		
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String userName=null;
        try {
			con=connection.conn();
			String sql="select userName from users where userId=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			rs=ps.executeQuery();
			if(rs.next()){
				userName=rs.getString(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
		
		return userName;
	}
	/*
	 * ���� ��� ���� ����� ��� ���
	 */
	public List<UserDto> selectAccessUser(){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserDto> users = new ArrayList<UserDto>();
        
        try {
			con=connection.conn();
			String sql="select u.userid, u.username, u.userphone, u.useraddr,c.companyName "
					+ "from users u,(select companyNum, companyName from company) c "
					+ "where c.companyNum=u.companyNum and u.access=0";
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			
			while(rs.next()){
				UserDto user = new UserDto();
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setUserPhone(rs.getString(3));
				user.setUserAddr(rs.getString(4));
				user.setCompanyName(rs.getString(5));
				
				users.add(user);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return users;
	}
	/*
	 * ���� ����� ���� �� ��
	 */
	public void permitAccessUser(String userId){
		Connection con = null;
        PreparedStatement ps = null;
        
        try {
			con=connection.conn();
			String sql="update users set access=1 where userid = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.executeUpdate();

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
	}
	/*
	 * �����ڸ� ������ ��� ����� ���
	 */
	public List<UserDto> selectAllUser(){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserDto> users = new ArrayList<UserDto>();
        
        try {
			con=connection.conn();
			String sql="select u.userid, u.username, u.userphone, u.useraddr,c.companyName "
					+ "from users u,(select companyNum, companyName from company) c "
					+ "where c.companyNum=u.companyNum and u.admin=0";
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			
			while(rs.next()){
				UserDto user = new UserDto();
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setUserPhone(rs.getString(3));
				user.setUserAddr(rs.getString(4));
				user.setCompanyName(rs.getString(5));
				
				users.add(user);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return users;
	}
	/**
	 * ȸ����Ͽ��� ���� �̸����� �˻�
	 */
	public List<UserDto> searchUserByUserName(String searchname){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserDto> users = new ArrayList<UserDto>();
        int index = searchname.indexOf("*");
        String search=null;
        String name =null;
        if(searchname.endsWith("*")){
        	name = searchname.substring(0, index);
        	search = name+"%";
        }else if(searchname.startsWith("*")){
        	name = searchname.substring(index+1);
        	search = "%"+name;
        }else if(searchname.contains("*") == false){
        	search = "%"+searchname+"%";
        }
        try {
			con=connection.conn();
			String sql="select u.userid, u.username, u.userphone, u.useraddr,c.companyName "
					+ "from users u,(select companyNum, companyName from company) c "
					+ "where c.companyNum=u.companyNum and u.admin=0 and u.username LIKE ? ";
			ps = con.prepareStatement(sql);
			ps.setString(1, search);
			rs=ps.executeQuery();
			
			while(rs.next()){
				UserDto user = new UserDto();
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setUserPhone(rs.getString(3));
				user.setUserAddr(rs.getString(4));
				user.setCompanyName(rs.getString(5));
				
				users.add(user);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return users;
	}
	/**
	 * ȸ����Ͽ��� ���� ���̵�� �˻�
	 */
	public List<UserDto> searchUserByUserId(String searchId){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserDto> users = new ArrayList<UserDto>();
        int index = searchId.indexOf("*");
        String search=null;
        String name =null;
        if(searchId.endsWith("*")){
        	name = searchId.substring(0, index);
        	search = name+"%";
        }else if(searchId.startsWith("*")){
        	name = searchId.substring(index+1);
        	search = "%"+name;
        }else if(searchId.contains("*") == false){
        	search = "%"+searchId+"%";
        }
        try {
			con=connection.conn();
			String sql="select u.userid, u.username, u.userphone, u.useraddr,c.companyName "
					+ "from users u,(select companyNum, companyName from company) c "
					+ "where c.companyNum=u.companyNum and u.admin=0 and u.userid LIKE ? ";
			ps = con.prepareStatement(sql);
			ps.setString(1, search);
			rs=ps.executeQuery();
			
			while(rs.next()){
				UserDto user = new UserDto();
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setUserPhone(rs.getString(3));
				user.setUserAddr(rs.getString(4));
				user.setCompanyName(rs.getString(5));
				
				users.add(user);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return users;
	}
	
	/**
	 * ȸ����Ͽ��� ���� ��ȭ��ȣ�� �˻�
	 */
	public List<UserDto> searchUserByUserPhone(String searchphone){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserDto> users = new ArrayList<UserDto>();
        int index = searchphone.indexOf("*");
        String search=null;
        String name =null;
        if(searchphone.endsWith("*")){
        	name = searchphone.substring(0, index);
        	search = name+"%";
        }else if(searchphone.startsWith("*")){
        	name = searchphone.substring(index+1);
        	search = "%"+name;
        }else if(searchphone.contains("*") == false){
        	search = "%"+searchphone+"%";
        }
        try {
			con=connection.conn();
			String sql="select u.userid, u.username, u.userphone, u.useraddr,c.companyName "
					+ "from users u,(select companyNum, companyName from company) c "
					+ "where c.companyNum=u.companyNum and u.admin=0 and u.userphone LIKE ? ";
			ps = con.prepareStatement(sql);
			ps.setString(1, search);
			rs=ps.executeQuery();
			
			while(rs.next()){
				UserDto user = new UserDto();
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setUserPhone(rs.getString(3));
				user.setUserAddr(rs.getString(4));
				user.setCompanyName(rs.getString(5));
				
				users.add(user);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return users;
	}
	/**
	 * ȸ����Ͽ��� ���� ȸ�� �̸����� �˻�
	 */
	public List<UserDto> searchUserByCompany(String searchcom){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserDto> users = new ArrayList<UserDto>();
        int index = searchcom.indexOf("*");
        String search=null;
        String name =null;
        if(searchcom.endsWith("*")){
        	name = searchcom.substring(0, index);
        	search = name+"%";
        }else if(searchcom.startsWith("*")){
        	name = searchcom.substring(index+1);
        	search = "%"+name;
        }else if(searchcom.contains("*") == false){
        	search = "%"+searchcom+"%";
        }
        try {
			con=connection.conn();
			String sql="select u.userid, u.username, u.userphone, u.useraddr,c.companyName "
					+ "from users u,(select companyNum, companyName from company) c "
					+ "where c.companyNum=u.companyNum and u.admin=0 and c.companyName LIKE ? ";
			ps = con.prepareStatement(sql);
			ps.setString(1, search);
			rs=ps.executeQuery();
			
			while(rs.next()){
				UserDto user = new UserDto();
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setUserPhone(rs.getString(3));
				user.setUserAddr(rs.getString(4));
				user.setCompanyName(rs.getString(5));
				
				users.add(user);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return users;
	}
	/**
	 * ���� ����ڸ�Ͽ��� ���� �̸����� �˻�
	 */
	public List<UserDto> searchAccessByUserName(String searchname){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserDto> users = new ArrayList<UserDto>();
        int index = searchname.indexOf("*");
        String search=null;
        String name =null;
        if(searchname.endsWith("*")){
        	name = searchname.substring(0, index);
        	search = name+"%";
        }else if(searchname.startsWith("*")){
        	name = searchname.substring(index+1);
        	search = "%"+name;
        }else if(searchname.contains("*") == false){
        	search = "%"+searchname+"%";
        }
        try {
			con=connection.conn();
			String sql="select u.userid, u.username, u.userphone, u.useraddr,c.companyName "
					+ "from users u,(select companyNum, companyName from company) c "
					+ "where c.companyNum=u.companyNum and u.access=0 and u.username LIKE ? ";
			ps = con.prepareStatement(sql);
			ps.setString(1, search);
			rs=ps.executeQuery();
			
			while(rs.next()){
				UserDto user = new UserDto();
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setUserPhone(rs.getString(3));
				user.setUserAddr(rs.getString(4));
				user.setCompanyName(rs.getString(5));
				
				users.add(user);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return users;
	}
	/**
	 * ���� ����ڸ�Ͽ��� ���� ���̵�� �˻�
	 */
	public List<UserDto> searchAccessByUserId(String searchid){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserDto> users = new ArrayList<UserDto>();
        int index = searchid.indexOf("*");
        String search=null;
        String name =null;
        if(searchid.endsWith("*")){
        	name = searchid.substring(0, index);
        	search = name+"%";
        }else if(searchid.startsWith("*")){
        	name = searchid.substring(index+1);
        	search = "%"+name;
        }else if(searchid.contains("*") == false){
        	search = "%"+searchid+"%";
        }
        try {
			con=connection.conn();
			String sql="select u.userid, u.username, u.userphone, u.useraddr,c.companyName "
					+ "from users u,(select companyNum, companyName from company) c "
					+ "where c.companyNum=u.companyNum and u.access=0 and u.userid LIKE ? ";
			ps = con.prepareStatement(sql);
			ps.setString(1, search);
			rs=ps.executeQuery();
			
			while(rs.next()){
				UserDto user = new UserDto();
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setUserPhone(rs.getString(3));
				user.setUserAddr(rs.getString(4));
				user.setCompanyName(rs.getString(5));
				
				users.add(user);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return users;
	}
	/**
	 * ���� ����ڸ�Ͽ��� ȸ�� �̸����� �˻�
	 */
	public List<UserDto> searchAccessByCompany(String searchcom){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserDto> users = new ArrayList<UserDto>();
        int index = searchcom.indexOf("*");
        String search=null;
        String name =null;
        if(searchcom.endsWith("*")){
        	name = searchcom.substring(0, index);
        	search = name+"%";
        }else if(searchcom.startsWith("*")){
        	name = searchcom.substring(index+1);
        	search = "%"+name;
        }else if(searchcom.contains("*") == false){
        	search = "%"+searchcom+"%";
        }
        try {
			con=connection.conn();
			String sql="select u.userid, u.username, u.userphone, u.useraddr,c.companyName "
					+ "from users u,(select companyNum, companyName from company) c "
					+ "where c.companyNum=u.companyNum and u.access=0 and c.companyName LIKE ? ";
			ps = con.prepareStatement(sql);
			ps.setString(1, search);
			rs=ps.executeQuery();
			
			while(rs.next()){
				UserDto user = new UserDto();
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setUserPhone(rs.getString(3));
				user.setUserAddr(rs.getString(4));
				user.setCompanyName(rs.getString(5));
				
				users.add(user);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return users;
	}
	/**
	 * ���� ����ڸ�Ͽ��� ���� ��ȭ��ȣ�� �˻�
	 */
	public List<UserDto> searchAccessByPhone(String searchphone){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserDto> users = new ArrayList<UserDto>();
        int index = searchphone.indexOf("*");
        String search=null;
        String name =null;
        if(searchphone.endsWith("*")){
        	name = searchphone.substring(0, index);
        	search = name+"%";
        }else if(searchphone.startsWith("*")){
        	name = searchphone.substring(index+1);
        	search = "%"+name;
        }else if(searchphone.contains("*") == false){
        	search = "%"+searchphone+"%";
        }
        try {
			con=connection.conn();
			String sql="select u.userid, u.username, u.userphone, u.useraddr,c.companyName "
					+ "from users u,(select companyNum, companyName from company) c "
					+ "where c.companyNum=u.companyNum and u.access=0 and u.userphone LIKE ? ";
			ps = con.prepareStatement(sql);
			ps.setString(1, search);
			rs=ps.executeQuery();
			
			while(rs.next()){
				UserDto user = new UserDto();
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setUserPhone(rs.getString(3));
				user.setUserAddr(rs.getString(4));
				user.setCompanyName(rs.getString(5));
				
				users.add(user);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return users;
	}
	/**
	 * ����� ��Ͽ��� ���� ����
	 * 
	 */
	public void deleteUser(String userId){
		Connection con = null;
        PreparedStatement ps = null;
        
        try {
			con=connection.conn();
			String sql="delete from users where userid = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.executeUpdate();

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
	}
	/**
	 * �α��� ������ ���������� �ƴ��� �����ϱ� ����
	 */
	public int getUserAdmin(String userId){
		
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int admin=0;
        try {
			con=connection.conn();
			String sql="select admin from users where userId=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			rs=ps.executeQuery();
			if(rs.next()){
				admin=rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
		
		return admin;
	}
	
	/**
	 * �α��� ������ ���� ��������� �ƴ��� �����ϱ� ����
	 */
	public int getUserAccess(String userId){
		
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int access=0;
        try {
			con=connection.conn();
			String sql="select access from users where userId=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			rs=ps.executeQuery();
			if(rs.next()){
				access=rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
		
		return access;
	}
	public UserDto updateUser(String userId, String userName, String userAddr, String userPhone, String companyName){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int num = 0;
        UserDto dto = new UserDto();
        
        dto.setUserId(userId);
        dto.setUserName(userName);
        dto.setUserAddr(userAddr);
        dto.setUserPhone(userPhone);
        dto.setCompanyName(companyName);
        
        try {
			con=connection.conn();
			String sql="select companyNum from company where companyName = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, companyName);
			rs=ps.executeQuery();
			if(rs.next()){
				num = rs.getInt(1);
			}
			rs.close();
			ps.close();
			
			sql="update users set userName = ?, userAddr = ?, userPhone = ?, companyNum = ? where userId = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, userAddr);
			ps.setString(3, userPhone);
			ps.setInt(4, num);
			ps.setString(5, userId);
			
			ps.executeUpdate();

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        return dto;
	}
	//userID�� ������ �˻�
	public UserDto getData(String userid){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserDto dto = new UserDto();
        try {
			con=connection.conn();
			String sql="select u.userid, u.username, u.userphone, u.useraddr,c.companyName "
					+ "from users u,(select companyNum, companyName from company) c "
					+ "where c.companyNum=u.companyNum and u.userid=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userid);
			rs=ps.executeQuery();
			if(rs.next()){
				dto.setUserId(rs.getString(1));
				dto.setUserName(rs.getString(2));
				dto.setUserPhone(rs.getString(3));
				dto.setUserAddr(rs.getString(4));
				dto.setCompanyName(rs.getString(5));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        return dto;
	}
	
	public UserDto getOneUserData(String userid){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserDto dto = new UserDto();
        try {
			con=connection.conn();
			String sql="select u.userid, u.username, u.userphone, u.useraddr,u.companyNum "
					+ "from users u where u.userid=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userid);
			rs=ps.executeQuery();
			if(rs.next()){
				dto.setUserId(rs.getString(1));
				dto.setUserName(rs.getString(2));
				dto.setUserPhone(rs.getString(3));
				dto.setUserAddr(rs.getString(4));
				dto.setCompanyNum(rs.getInt(5));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        return dto;
	}
		//Id�� ȸ�� ��ȸ
		public int selectcompany(String userId){
			
			Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        int compnum = 0;
	        try {
				con=connection.conn();
				String sql="select companyNum from users where userId=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, userId);
				rs=ps.executeQuery();
				if(rs.next()){
					compnum=rs.getInt(1);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {rs.close();} catch (Exception e) {e.printStackTrace();}
				try {ps.close();} catch (Exception e) {e.printStackTrace();}
				try {con.close();} catch (Exception e) {e.printStackTrace();}
			}
			
			return compnum;
		}
		//companyNum�� ȸ�� ��ȸ
				public String selectcompanyname(int companyNum){
					
					Connection con = null;
			        PreparedStatement ps = null;
			        ResultSet rs = null;
			        String companyName = "";
			        try {
						con=connection.conn();
						String sql="select companyname from company where companyNum=?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, companyNum);
						rs=ps.executeQuery();
						if(rs.next()){
							companyName=rs.getString(1);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						try {rs.close();} catch (Exception e) {e.printStackTrace();}
						try {ps.close();} catch (Exception e) {e.printStackTrace();}
						try {con.close();} catch (Exception e) {e.printStackTrace();}
					}
					
					return companyName;
				}
}
