package webhard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import webhard.dto.FileDto;
import webhard.dto.FolderDto;

public class FileDao {

	/**
	 * 새로운 파일 업로드
	 */
	public FileDto addNewFile(String name, int itemNum, String userId,
			int companyNum, String fileURL, String fileSize, String fileType) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int num = 0;
		FileDto dto = new FileDto();
		String sql = null;
		
		dto.setName(name);
		dto.setUserId(userId);
		dto.setCompanyNum(companyNum);
		dto.setFileURL(fileURL);
		dto.setFileSize(fileSize);
		dto.setFileType(fileType);
		
		try {
			con = connection.conn();
			if(companyNum == 0){
				sql = "insert into item (itemNum,name,item_creation_date,parentNum,userId) "
						+ "values (next value for itemNum, ?, current_date, ?,?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, name);
				ps.setInt(2, itemNum);
				ps.setString(3, userId);
			}else{
				sql = "insert into item (itemNum,name,item_creation_date,parentNum,userId,companyNum) "
						+ "values (next value for itemNum, ?, current_date, ?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, name);
				ps.setInt(2, itemNum);
				ps.setString(3, userId);
				ps.setInt(4, companyNum);
			}
			
			ps.executeUpdate();
			ps.close();

			sql = "select max(itemNum) from item";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
			}
			dto.setItemNum(num);
			rs.close();
			ps.close();

			sql = "insert into file(itemNum, fileurl, filesize, filetype) values (?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			ps.setString(2, fileURL);
			ps.setString(3, fileSize);
			ps.setString(4, fileType);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
	/**
	 * 기존 파일 정보 수정
	 */
	public void updateFile(String name, int itemNum, String fileURL,
			String fileSize, String fileType) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = connection.conn();
			String sql = "update item set name = ?, item_creation_date = current_date where itemNum = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, itemNum);

			ps.executeUpdate();
			ps.close();
			
			sql="update file set fileurl = ?, fileSize = ?, fileType = ? where itemNum = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, fileURL);
			ps.setString(2, fileSize);
			ps.setString(3, fileType);
			ps.setInt(4, itemNum);
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/** 
	 * 파일 삭제
	 */
	public void deleteFile(int itemNum) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = connection.conn();

			String sql = "delete from item where itemNum= ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, itemNum);
			ps.executeUpdate();
			ps.close();
			
			sql = "delete from file where itemNum= ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, itemNum);
			ps.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	/**
	 * 폴더 안에 파일들 보여질 때
	 */
	public List<FileDto> printFileInParentFolder(int itemNum) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<FileDto> files = new ArrayList<FileDto>();
        
        try {
			con = connection.conn();
			String sql="select f.fileURL, f.fileSize, f.fileType, i.itemNum, i.name, i.ITEM_CREATION_DATE, i.parentNum, i.userid, i.companyNum"
					+ " from file f, item i where i.parentNum=? and i.itemNum=f.itemNum";
			ps = con.prepareStatement(sql);
			ps.setInt(1, itemNum);
			rs=ps.executeQuery();
			
			while(rs.next()){
				FileDto file = new FileDto();
				file.setFileURL(rs.getString(1));
				file.setFileSize(rs.getString(2));
				file.setFileType(rs.getString(3));
				file.setItemNum(rs.getInt(4));
				file.setName(rs.getString(5));
				file.setDate(rs.getString(6));
				file.setParentNum(rs.getInt(7));
				file.setUserId(rs.getString(8));
				file.setCompanyNum(rs.getInt(9));
				
				files.add(file);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return files;
	}
	//파일 확인
		public boolean checkfile(int ItemNum){
			Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        boolean check = false;
	        try {
				con=connection.conn();
				String sql="select * from file where itemNum=?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, ItemNum);
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
		
		public FileDto selectFileByItemNum(int ItemNum){
			Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        FileDto dto = new FileDto();
	        try {
				con=connection.conn();
				String sql="select f.fileURL, f.fileSize, f.fileType, i.itemNum, i.name, i.ITEM_CREATION_DATE, i.parentNum, i.userid, i.companyNum from file f, item i where i.itemNum= ? and i.itemNum=f.itemNum";
				ps = con.prepareStatement(sql);
				ps.setInt(1, ItemNum);
				rs = ps.executeQuery();
				if(rs.next()){
					dto.setFileURL(rs.getString(1));
					dto.setFileSize(rs.getString(2));
					dto.setFileType(rs.getString(3));
					dto.setItemNum(rs.getInt(4));
					dto.setName(rs.getString(5));
					dto.setDate(rs.getString(6));
					dto.setParentNum(rs.getInt(7));
					dto.setUserId(rs.getString(8));
					dto.setCompanyNum(rs.getInt(9));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {rs.close();} catch (Exception e) {e.printStackTrace();}
				try {ps.close();} catch (Exception e) {e.printStackTrace();}
				try {con.close();} catch (Exception e) {e.printStackTrace();}
			}
			return dto;
		}
}
