package company;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import webhard.dao.CompanyDao;
import webhard.dto.CompanyDto;

public class CompanyUpdate extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField CompName;
	private JTextField compAddr;
	private JTextField compPhone;
	private JButton cancelBtn;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//
//		int compnum=0;
//		String Name=null;
//		String addr=null;
//		String phone=null;
//
//		try {
//			CompanyUpdate dialog = new CompanyUpdate(compnum,Name,addr,phone);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	
	/**
	 * Create the dialog.
	 */
	public CompanyUpdate(final int compnum ,String Name, String addr, String phone, final CompanyList comList) {
		setResizable(false);
		setBounds(100, 100, 430, 277);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("\uD68C\uC0AC\uBA85 :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(71, 68, 57, 15);
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\uD68C\uC0AC \uC8FC\uC18C :");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(60, 115, 68, 15);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("\uC804\uD654\uBC88\uD638 :");
		lblNewLabel_2.setBounds(60, 160, 74, 15);
		contentPanel.add(lblNewLabel_2);

		CompName = new JTextField();
		CompName.setBounds(125, 65, 187, 21);
		CompName.setText(Name);
		contentPanel.add(CompName);
		CompName.setColumns(10);

		compAddr = new JTextField();
		compAddr.setBounds(125, 112, 187, 21);
		compAddr.setText(addr);
		contentPanel.add(compAddr);
		compAddr.setColumns(10);

		compPhone = new JTextField();
		compPhone.setBounds(125, 157, 187, 21);
		compPhone.setText(phone);
		contentPanel.add(compPhone);
		compPhone.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(0, 0, 424, 41);
		contentPanel.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("\uD68C\uC0AC \uC218\uC815");
		lblNewLabel_3.setFont(new Font("굴림", Font.BOLD, 22));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(147, 10, 139, 21);
		panel.add(lblNewLabel_3);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BorderLayout(0, 0));

			JPanel panel_2 = new JPanel();
			buttonPane.add(panel_2, BorderLayout.NORTH);
			panel_2.setPreferredSize(new Dimension(50, 35));
			panel_2.setLayout(new BorderLayout(0, 0));

			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_2.add(panel_1, BorderLayout.CENTER);

			JButton updateBtn = new JButton("\uC218\uC815");
			updateBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompanyDao Dao = new CompanyDao();
					String CoName = CompName.getText();
					String coAddr = compAddr.getText();
					String Phone = compPhone.getText();
					List<CompanyDto> companys = new ArrayList<CompanyDto>();
					
					
					Dao.updateCompany(compnum, CoName, coAddr, Phone);
					companys = Dao.selectCompany();
					JOptionPane.showMessageDialog(null,"수정되었습니다.");
					comList.listBySearch(companys);
					dispose();
					
				}
			});
			panel_1.add(updateBtn);

			cancelBtn = new JButton("\uCDE8\uC18C");
			cancelBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if ((JOptionPane
							.showConfirmDialog(cancelBtn, "수정을 취소하시겠습니까??","종료확인", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
						
						dispose();
					}
				}
			});
			panel_1.add(cancelBtn);
		}
	}
}
