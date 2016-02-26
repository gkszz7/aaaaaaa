package user;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import webhard.dao.UserDao;
import webhard.dto.UserDto;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;


public class AccessList extends JDialog {
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AccessList dialog = new AccessList();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AccessList() {
		setResizable(false);
		setTitle("����� �������");
		setBounds(100, 100, 766, 480);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setPreferredSize(new Dimension(50, 150));
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setPreferredSize(new Dimension(50,50));
		
		JLabel lblNewLabel = new JLabel("����� �˻�");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("HY�߰��", Font.PLAIN, 20));
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("����� �˻� : ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(137, 37, 107, 15);
		panel_2.add(lblNewLabel_1);
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(253, 34, 89, 21);
		comboBox.addItem("�̸�");
		comboBox.addItem("ȸ���");
		comboBox.addItem("���̵�");
		comboBox.addItem("��ȭ��ȣ");
		panel_2.add(comboBox);
		
		textField = new JTextField();
		textField.setBounds(358, 34, 199, 21);
		panel_2.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("�˻�");
		btnNewButton.addActionListener(new ActionListener() {
			
			List<UserDto> searchUser = new ArrayList<UserDto>();
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				UserDao dao = new UserDao();
				if(comboBox.getSelectedItem().toString().equals("�̸�")){
					searchUser = dao.searchAccessByUserName(textField.getText());
					listBySearch(searchUser);
				}else if(comboBox.getSelectedItem().toString().equals("ȸ���")){
					searchUser = dao.searchAccessByCompany(textField.getText());
					listBySearch(searchUser);
				}else if(comboBox.getSelectedItem().toString().equals("���̵�")){
					searchUser = dao.searchAccessByUserId(textField.getText());
					listBySearch(searchUser);
				}else{
					searchUser = dao.searchAccessByPhone(textField.getText());
					listBySearch(searchUser);
				}
				
			}
		});
		
		btnNewButton.setBounds(460, 65, 97, 23);
		panel_2.add(btnNewButton);

		List<UserDto> users = new ArrayList<UserDto>();
		UserDao userdao = new UserDao();
		users = userdao.selectAccessUser();
		createTable(users);
		
	}

	private void changeCellEditor(final JTable table, TableColumn column) {
		 	final JComboBox<String> comboBox = new JComboBox<String>();
	        comboBox.addItem("���� ���");
	        comboBox.addItem("���� �Ϸ�");
	        		
	        column.setCellEditor(new DefaultCellEditor(comboBox));
	        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	        renderer.setToolTipText("Ŭ���ϸ� �޺��ڽ��� ����˴ϴ�.");
	        column.setCellRenderer(renderer);
			
	       
	        comboBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					List<UserDto> users = new ArrayList<UserDto>();
					UserDao userdao = new UserDao();
					
					if(comboBox.getSelectedItem().toString().equals("���� �Ϸ�")){
				        userdao.permitAccessUser(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());

						users = userdao.selectAccessUser();
						listBySearch(users);
				    }
				}
			});
	        
	}
	private void createTable(List<UserDto> users) {
		
		
		table = new JTable();
		
		table.setFont(new Font("����", Font.PLAIN, 15));
		getContentPane().add(table, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(table);
		Container c = getContentPane();
		c.add(scrollPane,BorderLayout.CENTER);
		
		listBySearch(users);
		

		
	}

	public void listBySearch(List<UserDto> search){
		
		String access="";
		table.removeAll();
		
		String columnNames[] = { "���̵�", "�̸�", "ȸ�� ��", "��ȭ��ȣ","�ּ�","����" };
		DefaultTableModel model = new DefaultTableModel();
		for (int i = 0; i < columnNames.length; i++) {
			model.addColumn(columnNames[i]);
		}
		
		for (int i = 0; i < search.size(); i++) {
			if(search.get(i).getAccess()==0){
				access="���� ���";
			}
			 String[] socrates = { search.get(i).getUserId(), search.get(i).getUserName(),
						search.get(i).getCompanyName(), search.get(i).getUserPhone(), search.get(i).getUserAddr(), access };
			 model.addRow(socrates);
		}
		table.setModel(model);
		
		changeCellEditor(table, table.getColumnModel().getColumn(5));
		

	}

}
