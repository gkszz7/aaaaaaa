package user;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import webhard.dao.CompanyDao;
import webhard.dao.UserDao;
import webhard.dto.CompanyDto;
import webhard.dto.UserDto;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserUpdate extends JDialog {
	private JTextField userName;
	private JTextField userPhone;
	private JTextField userAddr;
	private JLabel UserId;
	private Choice comboBox;
	private JButton cancelBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*String Userid = null;
		String UserName = null;
		String UserCompName = null;
		String UserPhone = null;
		String UserAddr = null;
		try {
			
			UserUpdate dialog = new UserUpdate(Userid,UserName,UserCompName,UserPhone,UserAddr);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Create the dialog.
	 */
	public UserUpdate(String Userid,String UserName,String UserCompName,String uPhone,String UserAddr,final UserList userlist) {
		setResizable(false);
		setBounds(100, 100, 383, 403);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			getContentPane().add(panel, BorderLayout.SOUTH);
			panel.setPreferredSize(new Dimension(50, 40));
			panel.setLayout(null);
			
			JButton updateBtn = new JButton("¼öÁ¤");
			updateBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					UserDao dao = new UserDao();
					UserDto dto = new UserDto();
					CompanyDao compDao = new CompanyDao();
					
					String userid = UserId.getText();
					String uname = userName.getText();
					String addr = userAddr.getText();
					String phone = userPhone.getText();
					String com = comboBox.getSelectedItem().toString();
					int compNum = compDao.selectCompanyNum(com);
					
					dto = dao.updateUser(userid, uname, addr, phone, com);
					
					userlist.userUpdate(dto);
					dispose();
					}
				
			});
			updateBtn.setBounds(102, 6, 80, 30);
			panel.add(updateBtn);
			
			cancelBtn = new JButton("Ãë¼Ò");
			cancelBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if((JOptionPane.showConfirmDialog(cancelBtn, "¼öÁ¤À» Ãë¼ÒÇÏ½Ã°Ú½À´Ï±î??","Ãë¼Ò", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION)
		         	   {
		    				dispose();
		         	   }
					
				}
			});
			cancelBtn.setBounds(200, 6, 80, 30);
			panel.add(cancelBtn);
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			getContentPane().add(panel, BorderLayout.NORTH);
			panel.setPreferredSize(new Dimension(50,50));
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("\uD68C\uC6D0 \uC218\uC815");
			lblNewLabel.setFont(new Font("±¼¸²", Font.BOLD, 20));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(130, 10, 134, 30);
			panel.add(lblNewLabel);
		}
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("\uC544\uC774\uB514 :");
		lblNewLabel_1.setFont(new Font("±¼¸²", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(32, 41, 73, 15);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\uC774\uB984 :");
		lblNewLabel_2.setFont(new Font("±¼¸²", Font.PLAIN, 14));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(32, 85, 73, 15);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\uD68C\uC0AC \uBA85 :");
		lblNewLabel_3.setFont(new Font("±¼¸²", Font.PLAIN, 14));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(32, 128, 57, 15);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\uC804\uD654\uBC88\uD638 :");
		lblNewLabel_4.setFont(new Font("±¼¸²", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(16, 170, 73, 15);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("\uC8FC\uC18C : ");
		lblNewLabel_5.setFont(new Font("±¼¸²", Font.PLAIN, 14));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(32, 215, 57, 15);
		panel.add(lblNewLabel_5);
		
		UserId = new JLabel("");
		UserId.setText(Userid);
		UserId.setHorizontalAlignment(SwingConstants.CENTER);
		UserId.setBounds(101, 41, 123, 15);
		panel.add(UserId);
		
		userName = new JTextField();
		userName.setText(UserName);
		userName.setBounds(101, 82, 123, 21);
		panel.add(userName);
		userName.setColumns(10);
		
		comboBox = new Choice();
		comboBox.setBounds(101, 125, 100, 21);
		CompanyDao cDao = new CompanyDao();
		List<CompanyDto> companys = new ArrayList<CompanyDto>();
		companys = cDao.selectCompany();
		if(companys != null && companys.size()>0){
			for(CompanyDto comp : companys){
				comboBox.addItem(comp.getCompanyName());
			}
		}else{
			comboBox.addItem("µî·ÏX");
		}
		panel.add(comboBox);
		
		userPhone = new JTextField();
		userPhone.setText(uPhone);
		userPhone.setBounds(101, 168, 156, 21);
		panel.add(userPhone);
		userPhone.setColumns(10);
		
		userAddr = new JTextField();
		userAddr.setText(UserAddr);
		userAddr.setBounds(101, 213, 168, 21);
		panel.add(userAddr);
		userAddr.setColumns(10);
	}
}
