package company;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeCellRenderer;

import webhard.dao.CompanyDao;
import webhard.dto.CompanyDto;

public class CompanyList extends JDialog {
	private JTable table;
	private JTextField textField;
	private JButton deleteBtn;
	private JButton searchBtn;
	private JPanel panel;
	private DefaultTableModel model;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CompanyList dialog = new CompanyList();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	
	public CompanyList() {
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setTitle("\uD68C\uC0AC \uBAA9\uB85D");
		setBounds(100, 100, 790, 547);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
			getContentPane().add(panel, BorderLayout.NORTH);
			panel.setPreferredSize(new Dimension(50, 60));
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("\uD68C\uC0AC \uBAA9\uB85D");
			lblNewLabel.setFont(new Font("굴림", Font.BOLD, 22));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(218, 10, 327, 40);
			panel.add(lblNewLabel);
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			getContentPane().add(panel, BorderLayout.SOUTH);
			panel.setPreferredSize(new Dimension(50, 35));
			panel.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_1 = new JPanel();
			panel.add(panel_1, BorderLayout.EAST);
			panel_1.setPreferredSize(new Dimension(250, 60));
			panel_1.setLayout(null);
			
			deleteBtn = new JButton("삭제");
			deleteBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					CompanyDao comDao =  new CompanyDao();
					
					List<CompanyDto> companys = new ArrayList<CompanyDto>();
					
					if(table.getSelectedRowCount()>0){
						String name = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
						if((JOptionPane.showConfirmDialog(deleteBtn, "삭제 하시겠습니까??","종료확인", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION){
							comDao.deleteCompany(name);
							companys = comDao.selectCompany();
							listBySearch(companys);
						}
						
					}else{
						JOptionPane.showMessageDialog(null,"회사를 선택해주세요.");
					}
				}
			});
			deleteBtn.setBounds(141, 0, 97, 25);
			panel_1.add(deleteBtn);
			
			JButton updateBtn = new JButton("수정");
			updateBtn.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					if(table.getSelectedRowCount()>0){
					String id = (table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
					
					List<CompanyDto> companys = new ArrayList<CompanyDto>();
					
					CompanyDao dao = new CompanyDao();
					CompanyDto dto = dao.getData(id);
					
					int compnum = dto.getCompanyNum();
					String companyname = dto.getCompanyName();
					String companyAddr = dto.getCompanyAddr();
					String companyPhone = dto.getCompanyPhone();
					
					CompanyUpdate update = new CompanyUpdate(compnum,companyname,companyAddr,companyPhone, CompanyList.this);
					CompanyDto dto2 = dao.getData(id);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					update.setLocation((dim.width / 2) - (update.getWidth() / 2),
							(dim.height / 2) - (update.getHeight() / 2));
					update.setVisible(true);
					
				}else{
					JOptionPane.showMessageDialog(null,"회사를 선택해주세요.");
				}
			}
			});
			
			updateBtn.setBounds(39, 1, 97, 25);
			panel_1.add(updateBtn);
		}
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setPreferredSize(new Dimension(50, 120));
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_1.add(panel_3, BorderLayout.WEST);
		panel_3.setPreferredSize(new Dimension(200, 60));
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("");
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		Icon img = new ImageIcon("set1.png");
		lblNewLabel_1.setIcon(img);
		lblNewLabel_1.setBounds(0, 0, 200, 105);
		panel_3.add(lblNewLabel_1);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_1.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(null);
		
		final JComboBox<String> search = new JComboBox<String>();
		search.setBounds(229, 43, 95, 21);
		search.addItem("이름");
		search.addItem("주소");
		search.addItem("전화번호");
		panel_4.add(search);
		
		textField = new JTextField();
		textField.setBounds(336, 43, 149, 21);
		panel_4.add(textField);
		textField.setColumns(10);
		
		searchBtn = new JButton("검색");
		searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				List<CompanyDto> searchCom = new ArrayList<CompanyDto>();
				CompanyDao dao = new CompanyDao();
				if(search.getSelectedItem().toString().equals("이름")){
					searchCom = dao.searchCompanybyName(textField.getText());
					listBySearch(searchCom);
				}else if(search.getSelectedItem().toString().equals("주소")){
					searchCom = dao.searchCompanybyAddr(textField.getText());
					listBySearch(searchCom);
				}else{
					searchCom = dao.searchCompanybyPhone(textField.getText());
					listBySearch(searchCom);
				}
				
			}
		});
		searchBtn.setBounds(388, 74, 97, 23);
		panel_4.add(searchBtn);
		
		JLabel lblNewLabel_2 = new JLabel("\uD68C\uC0AC \uBA85 :");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 14));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(145, 46, 72, 15);
		panel_4.add(lblNewLabel_2);
		
		
		
		
		List<CompanyDto> companys = new ArrayList<CompanyDto>();
		CompanyDao comDao = new CompanyDao();
		companys = comDao.selectCompany();
		
		createTable(companys);
	}
	private void createTable(List<CompanyDto> companys){
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
		table.setFont(new Font("굴림", Font.PLAIN, 13));
		table.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JScrollPane scrollPane = new JScrollPane(table);
		Container c = getContentPane();
		
		panel_2.add(scrollPane, BorderLayout.CENTER);
		
		listBySearch(companys);
	}
	public void listBySearch(List<CompanyDto> companys){
		table.removeAll();
		String columnNames[] = {"이름", "주소", "전화번호", "등록 날짜"};
		model = new DefaultTableModel(){
			public boolean isCellEditable(int rowindex,int Mollndex){
				return false;
			}
		};
		//model.setValueAt(aValue, row, column);
		for(int i = 0; i<columnNames.length; i++){
			model.addColumn(columnNames[i]);
		}
		for(int i = 0; i<companys.size(); i++){
			String[] info = {companys.get(i).getCompanyName(), companys.get(i).getCompanyAddr(), companys.get(i).getCompanyPhone()
					,companys.get(i).getCompanyCreationDate()};
			model.addRow(info);
		}
		table.setModel(model);
		
	}

	/*public void update(boolean b) {
		// TODO Auto-generated method stub
		System.out.println(b);
		List<CompanyDto> companys = new ArrayList<CompanyDto>();
		CompanyDao dao = new CompanyDao();
		if(b==false){
			companys = dao.selectCompany();
			listBySearch(companys);
		}
	}*/
}
