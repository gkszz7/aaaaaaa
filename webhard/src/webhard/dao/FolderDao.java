package webhard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import webhard.dto.FileDto;
import webhard.dto.FolderDto;
import webhard.dto.ItemDto;

public class FolderDao {
	



	/**
	 * 새 폴더 생성
	 */
	public FolderDto addNewFolder(String name, int itemNum, String userId,
			int companyNum) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int num=0;
		
		FolderDto folderDto = new FolderDto();
		
		folderDto.setName(name);
		
		folderDto.setUserId(userId);
		folderDto.setCompanyNum(companyNum);

		try {
			con = connection.conn();
			String sql = "insert into item (itemNum,name,item_creation_date,parentNum,userId,companyNum) "
					+ "values (next value for itemNum, ?, current_date, ?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, itemNum);
			ps.setString(3, userId);
			ps.setInt(4, companyNum);
			ps.executeUpdate();
			ps.close();

			sql = "select max(itemNum) from item";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
			folderDto.setItemNum(num);
			rs.close();
			ps.close();

			sql="insert into folder(itemNum, foldertype, step) values (?,0,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			ps.setInt(2, 0);
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
		
		return folderDto;
	}

	/**
	 * 회사 가입 후 바로 생성 되는 회사 폴더
	 */
	public FolderDto addCompanyFolder(String name, int parentNum, String userId,
			int companyNum) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		FolderDto dto = new FolderDto();
		dto.setName(name);
		dto.setUserId(userId);
		dto.setCompanyNum(companyNum);
		int num=0;

		try {
			con = connection.conn();
			String sql = "insert into item (itemNum,name,item_creation_date,parentNum,userId,companyNum) "
					+ "values (next value for itemNum, ?, current_date, ?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, parentNum);
			ps.setString(3, userId);
			ps.setInt(4, companyNum);
			ps.executeUpdate();
			ps.close();

			sql = "select max(itemNum) from item";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
			dto.setItemNum(num);
			rs.close();
			ps.close();

			sql="insert into folder(itemNum, foldertype, step) values (?,0,1)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
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
	 * 메인 화면의 폴더 트리 출력 시
	 */
	public List<FolderDto> selectFolderTree() {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<FolderDto> folders = new ArrayList<FolderDto>();
        
        try {
			con = connection.conn();
			String sql="select f.foldertype, f.step, i.itemNum, i.name, i.ITEM_CREATION_DATE, i.parentNum, i.userid, i.companyNum"
					+ " from folder f, item i where i.itemNum = f.itemNum order by i.companyNum , f.step, i.itemNum";
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			
			while(rs.next()){
				FolderDto folder = new FolderDto();
				folder.setFolderType(rs.getInt(1));
				folder.setStep(rs.getInt(2));
				folder.setItemNum(rs.getInt(3));
				folder.setName(rs.getString(4));
				folder.setDate(rs.getString(5));
				folder.setParentNum(rs.getInt(6));
				folder.setUserId(rs.getString(7));
				folder.setCompanyNum(rs.getInt(8));
				
				folders.add(folder);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return folders;
	}
	/**
	 * HoME 폴더 출력
	 * 
	 */
	public FolderDto selectHomeFolder() {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        FolderDto folder = new FolderDto();
        try {
			con = connection.conn();
			String sql="select f.foldertype, f.step, i.itemNum, i.name, i.ITEM_CREATION_DATE, i.parentNum, i.userid, i.companyNum "
					+ "from folder f, item i where i.itemNum = f.itemNum and i.itemNum = 41";
			ps = con.prepareStatement(sql);
			rs=ps.executeQuery();
			
			if(rs.next()){
				
				folder.setFolderType(rs.getInt(1));
				folder.setStep(rs.getInt(2));
				folder.setItemNum(rs.getInt(3));
				folder.setName(rs.getString(4));
				folder.setDate(rs.getString(5));
				folder.setParentNum(rs.getInt(6));
				folder.setUserId(rs.getString(7));
				folder.setCompanyNum(rs.getInt(8));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return folder;
	}
	
	/**
	 * 선택 폴더 안에 들어 있는 폴더와 파일 목록 출력
	 */
	public List<FolderDto> printFolderInParentFolder(int itemNum) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<FolderDto> folders = new ArrayList<FolderDto>();
        
        try {
			con = connection.conn();
			String sql="select f.foldertype, f.step, i.itemNum, i.name, i.ITEM_CREATION_DATE, i.parentNum, i.userid, i.companyNum"
					+ " from folder f, item i where i.parentNum=? and i.itemNum=f.itemNum";
			ps = con.prepareStatement(sql);
			ps.setInt(1, itemNum);
			rs=ps.executeQuery();
			
			while(rs.next()){
				FolderDto folder = new FolderDto();
				folder.setFolderType(rs.getInt(1));
				folder.setStep(rs.getInt(2));
				folder.setItemNum(rs.getInt(3));
				folder.setName(rs.getString(4));
				folder.setDate(rs.getString(5));
				folder.setParentNum(rs.getInt(6));
				folder.setUserId(rs.getString(7));
				folder.setCompanyNum(rs.getInt(8));
				
				folders.add(folder);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return folders;
	}
	
	/**
	 * 폴더 이름 수정
	 */
	public FolderDto updateFolder(String name, int itemNum,int companyNum, String userId) {
		Connection con = null;
        PreparedStatement ps = null;
        FolderDto dto = new FolderDto();
        
        dto.setName(name);
        dto.setItemNum(itemNum);
        dto.setCompanyNum(companyNum);
        dto.setUserId(userId);
        try {
			con = connection.conn();
			String sql="update item set name = ?, item_creation_date = current_date where itemNum = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, itemNum);
			
			ps.executeUpdate();
			
			dto.setName(name);
	        dto.setItemNum(itemNum);
	        dto.setCompanyNum(companyNum);
	        dto.setUserId(userId);

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        return dto;
	}
	/**
	 * 상위 폴더의 아이템넘버 가져옴
	 */
	public List<Integer> itemNumByParentNum(int itemNum){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Integer> num =new ArrayList<Integer>();
        try {
			con = connection.conn();
			String sql = "select itemNum from item where parentNum= ? ";
			ps = con.prepareStatement(sql);
			ps.setInt(1, itemNum);
			rs = ps.executeQuery();

			while(rs.next()){
				int i=1;
				num.add(rs.getInt(i));
				
				i++;
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
	 * 선택한 폴더 및 하위 폴더 삭제
	 */
	public void deleteFolder(int itemNum) {
		Connection con = null;
        PreparedStatement ps = null;

        try {
			con = connection.conn();

			String sql="delete from item where itemNum= ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, itemNum);
			ps.executeUpdate();
	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
	}
	/**
	 * 아이템넘버로 폴더의 데이터 가져오기
	 */
	public FolderDto printFolderbyNum(int itemNum) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        FolderDto folder = new FolderDto();
        
        try {
			con = connection.conn();
			String sql="select f.foldertype, f.step, i.itemNum, i.name, i.ITEM_CREATION_DATE, i.parentNum, i.userid, i.companyNum"
					+ " from folder f, item i where i.itemNum=? and i.itemNum=f.itemNum";
			ps = con.prepareStatement(sql);
			ps.setInt(1, itemNum);
			rs=ps.executeQuery();
			
			if(rs.next()){
				folder.setFolderType(rs.getInt(1));
				folder.setStep(rs.getInt(2));
				folder.setItemNum(rs.getInt(3));
				folder.setName(rs.getString(4));
				folder.setDate(rs.getString(5));
				folder.setParentNum(rs.getInt(6));
				folder.setUserId(rs.getString(7));
				folder.setCompanyNum(rs.getInt(8));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return folder;
	}
	public List<ItemDto> selectChildByParentNum(int itemNum) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ItemDto> items = new ArrayList<ItemDto>();
        
        try {
			con = connection.conn();
			//String sql="select f.foldertype, f.step, i.itemNum, i.name, i.ITEM_CREATION_DATE, i.parentNum, i.userid, i.companyNum"
			//+ " from folder f, item i where i.itemNum=? and i.itemNum=f.itemNum";
			String sql="select i.itemNum, i.name, i.ITEM_CREATION_DATE, i.parentNum, i.userid, i.companyNum"
					+ " from folder f, item i where i.parentNum=? and i.itemNum = f.itemNum";
			ps = con.prepareStatement(sql);
			ps.setInt(1, itemNum);
			rs=ps.executeQuery();
			
			while(rs.next()){
				FolderDto item = new FolderDto();
				item.setItemNum(rs.getInt(1));
				item.setName(rs.getString(2));
				item.setDate(rs.getString(3));
				item.setParentNum(rs.getInt(4));
				item.setUserId(rs.getString(5));
				item.setCompanyNum(rs.getInt(6));
				
				items.add(item);
			}
			
			String sql2="select i.itemNum, i.name, i.ITEM_CREATION_DATE, i.parentNum, i.userid, i.companyNum, f.fileurl, f.filesize, f.filetype from file f, item i where i.parentNum = ? and i.itemNum = f.itemNum";
			ps = con.prepareStatement(sql2);
			ps.setInt(1, itemNum);
			rs=ps.executeQuery();
			
			while(rs.next()){
				FileDto item = new FileDto();
				item.setItemNum(rs.getInt(1));
				item.setName(rs.getString(2));
				item.setDate(rs.getString(3));
				item.setParentNum(rs.getInt(4));
				item.setUserId(rs.getString(5));
				item.setCompanyNum(rs.getInt(6));
				item.setFileURL(rs.getString(7));
				item.setFileSize(rs.getString(8));
				item.setFileType(rs.getString(9));
				
				items.add(item);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return items;
	}
	
	/**
	 * 회사넘버로 폴더의 데이터 가져오기
	 */
	public ItemDto printFolderbyCompanyNum(int comNum) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ItemDto item = new ItemDto();
        
        try {
			con = connection.conn();
			String sql="select * from item where companyNum= ? and parentNum = 41";
			ps = con.prepareStatement(sql);
			ps.setInt(1, comNum);
			rs=ps.executeQuery();
			
			if(rs.next()){
				item.setItemNum(rs.getInt(1));
				item.setName(rs.getString(2));
				item.setDate(rs.getString(3));
				item.setParentNum(rs.getInt(4));
				item.setUserId(rs.getString(5));
				item.setCompanyNum(rs.getInt(6));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (Exception e) {e.printStackTrace();}
			try {ps.close();} catch (Exception e) {e.printStackTrace();}
			try {con.close();} catch (Exception e) {e.printStackTrace();}
		}
        
        return item;
	}
	//선택 된 폴더나 파일의 회사 번호 알아오기
	public int selectCompanyNumByItemNum(int parentNum){
			
			Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        int companyNum = 0;
	        try {
				con=connection.conn();
				String sql="select companynum from item where itemnum=?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, parentNum);
				rs=ps.executeQuery();
				if(rs.next()){
					 companyNum=rs.getInt(1);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {rs.close();} catch (Exception e) {e.printStackTrace();}
				try {ps.close();} catch (Exception e) {e.printStackTrace();}
				try {con.close();} catch (Exception e) {e.printStackTrace();}
			}
			
			return  companyNum;
		}
	public int parentHomeNum(int itemNum){
			
			Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        int companyNum = 0;
	        try {
				con=connection.conn();
				String sql="select parentnum from item where itemnum=?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, itemNum);
				rs=ps.executeQuery();
				if(rs.next()){
					 companyNum=rs.getInt(1);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {rs.close();} catch (Exception e) {e.printStackTrace();}
				try {ps.close();} catch (Exception e) {e.printStackTrace();}
				try {con.close();} catch (Exception e) {e.printStackTrace();}
			}
			
			return companyNum;
	}
	public boolean checkFolder(int ItemNum){
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean check = false;
        try {
			con=connection.conn();
			String sql="select * from folder where itemNum=?";
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
}
