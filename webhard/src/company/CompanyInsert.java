package company;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import main.MainPage;
import webhard.dao.CompanyDao;
import webhard.dao.FolderDao;
import webhard.dto.FolderDto;

public class CompanyInsert extends JDialog {
	private JTextField CommpanyName;
	private JTextField CommpanyAddr;
	private JTextField phone1;
	private JTextField phone2;
	private JTextField phone3;
	private JButton cancelBtn;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*try {
			EventQueue.invokeLater(new Runnable() {
				String name = null;
				String id = null;
				public void run() {
					try {
						CompanyInsert dialog = new CompanyInsert(id);
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Create the dialog.
	 */
	public CompanyInsert(final String id,final MainPage mainPage) {
		setTitle("회사 등록");
		setBounds(100, 100, 449, 326);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			getContentPane().add(panel, BorderLayout.NORTH);
			panel.setPreferredSize(new Dimension(50,50));
			{
				JLabel lblNewLabel = new JLabel("\uD68C\uC0AC \uB4F1\uB85D");
				lblNewLabel.setFont(new Font("HY중고딕", Font.PLAIN, 22));
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblNewLabel);
			}
		}
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				CompanyInsert companyInsert = null;
				mainPage.openCompanyInsert(companyInsert);
				dispose();	
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			getContentPane().add(panel, BorderLayout.SOUTH);
			panel.setPreferredSize(new Dimension(50, 40));
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.CENTER);
				{
					JButton InsertBtn = new JButton("\uD68C\uC0AC \uB4F1\uB85D");
					
					InsertBtn.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e) {
							
							FolderDao folDao = new FolderDao();
							FolderDto dto = new FolderDto();
							int homeNum = folDao.selectHomeFolder().getItemNum();
							
							if(CommpanyName.getText().length()>0 && CommpanyAddr.getText().length()>0 && phone1.getText().length()>0 && phone2.getText().length()>0 &&phone3.getText().length()>0){
								
								CompanyDao dao = new CompanyDao();
								FolderDao fDao = new FolderDao();
								
								String Coname = CommpanyName.getText();
								String coAddr = CommpanyAddr.getText();
								
								boolean check = dao.checkCompanyName(Coname);
								if(Coname.length()>0 && check == false){
											
									String Phone = phone1.getText() + phone2.getText() + phone3.getText();
									
									dao.entryNewCompany(Coname, coAddr, Phone);
									int cNum=dao.selectCompanyNum(Coname);
									dto = fDao.addNewFolder(Coname, homeNum, id, cNum);
									JOptionPane.showMessageDialog(null,"회사가 등록되었습니다.");
									CompanyInsert companyInsert = null;
									mainPage.openCompanyInsert(companyInsert);
									setVisible(false);
									
									mainPage.addCompanyFolder(dto);
								}else{
									JOptionPane.showMessageDialog(null,"존재하는 회사 명 입니다.");
									CommpanyName.setText("");
								}
								
							}
							else{
								JOptionPane.showMessageDialog(null,"모두 작성해주시기 바랍니다..");
							}
							
						}
					});
					InsertBtn.setFont(new Font("굴림", Font.PLAIN, 14));
					panel_1.add(InsertBtn);
				}
				{
					cancelBtn = new JButton("\uCDE8\uC18C");
					cancelBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Object obj = e.getSource();
							if ((JOptionPane
									.showConfirmDialog(cancelBtn, "취소하시겠습니까??",
											"종료확인", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
								CompanyInsert companyInsert = null;
								mainPage.openCompanyInsert(companyInsert);
								dispose();
							}
						}
					});
					cancelBtn.setFont(new Font("굴림", Font.PLAIN, 14));
					panel_1.add(cancelBtn);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JLabel lblNewLabel_1 = new JLabel("\uD68C\uC0AC \uBA85 :");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 14));
			lblNewLabel_1.setBounds(19, 26, 65, 18);
			panel.add(lblNewLabel_1);
			
			CommpanyName = new JTextField();
			CommpanyName.setBounds(96, 26, 153, 21);
			panel.add(CommpanyName);
			CommpanyName.setColumns(10);
			
			JButton checkName = new JButton("\uC911\uBCF5\uD655\uC778");
			checkName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(CommpanyName.getText().length()>0){
						String ComName = CommpanyName.getText();
						
						CompanyDao dao = new CompanyDao();
						boolean check = dao.checkCompanyName(ComName);
						if(check ==true){
							JOptionPane.showMessageDialog(null,"존재하는 아이디입니다.");
							setVisible(true);
							CommpanyName.setText("");
						}else{
							JOptionPane.showMessageDialog(null,"사용 가능 한 아이디입니다.");
							setVisible(true);
						}
					}else{
						JOptionPane.showMessageDialog(null,"아이디를 입력해주세요.");
					}
				}
			});
			checkName.setBounds(263, 25, 97, 23);
			panel.add(checkName);
			
			JLabel lblNewLabel_2 = new JLabel("\uC8FC\uC18C :");
			lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 14));
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_2.setBounds(19, 75, 57, 15);
			panel.add(lblNewLabel_2);
			
			CommpanyAddr = new JTextField();
			CommpanyAddr.setBounds(96, 72, 262, 21);
			panel.add(CommpanyAddr);
			CommpanyAddr.setColumns(10);
			
			JLabel label = new JLabel("\uC804\uD654\uBC88\uD638 :");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setFont(new Font("굴림", Font.PLAIN, 14));
			label.setBounds(19, 114, 79, 15);
			panel.add(label);
			
			phone1 = new JTextField();
			phone1.setBounds(96, 112, 65, 21);
			panel.add(phone1);
			phone1.setColumns(10);
			
			JLabel lblNewLabel_3 = new JLabel("-");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setBounds(165, 113, 15, 18);
			panel.add(lblNewLabel_3);
			
			phone2 = new JTextField();
			phone2.setBounds(184, 112, 73, 21);
			panel.add(phone2);
			phone2.setColumns(10);
			
			JLabel lblNewLabel_4 = new JLabel("-");
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_4.setBounds(263, 115, 16, 15);
			panel.add(lblNewLabel_4);
			
			phone3 = new JTextField();
			phone3.setBounds(283, 112, 73, 21);
			panel.add(phone3);
			phone3.setColumns(10);
		}
	}
}

