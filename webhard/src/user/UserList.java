package user;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import main.MainPage;

import webhard.dao.CompanyDao;
import webhard.dao.UserDao;
import webhard.dto.CompanyDto;
import webhard.dto.UserDto;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserList extends JDialog {
	private JTextField searchField;
	private JTable table;
	private JButton DeleteBtn, searchBtn;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*try {
			UserList dialog = new UserList();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Create the dialog.
	 * @param mainPage 
	 */
	public UserList(final MainPage mainPage) {

		setTitle("사용자 정보");
		setResizable(false);
		setBounds(100, 100, 729, 488);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setPreferredSize(new Dimension(50, 130));
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("사용자 검색");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 18));
		panel_1.add(lblNewLabel);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		panel_2.setPreferredSize(new Dimension(50, 150));

		final JComboBox<String> search = new JComboBox<String>();
		search.setBounds(229, 35, 88, 21);
		search.addItem("이름");
		search.addItem("회사명");
		search.addItem("아이디");
		search.addItem("전화번호");
		panel_2.add(search);
		
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				UserList userList = null;
				mainPage.openUserList(userList);
				
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		searchField = new JTextField();
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					searchBtn.doClick();
				}

			}
		});
		searchField.setBounds(329, 35, 212, 21);
		panel_2.add(searchField);
		searchField.setColumns(10);

		searchBtn = new JButton("검색");
		searchBtn.addActionListener(new ActionListener() {

			List<UserDto> searchUser = new ArrayList<UserDto>();

			@Override
			public void actionPerformed(ActionEvent e) {

				UserDao dao = new UserDao();
				if (search.getSelectedItem().toString().equals("이름")) {
					searchUser = dao.searchUserByUserName(searchField.getText());
					listBySearch(searchUser);
				} else if (search.getSelectedItem().toString().equals("회사명")) {
					searchUser = dao.searchUserByCompany(searchField.getText());
					listBySearch(searchUser);
				} else if (search.getSelectedItem().toString().equals("아이디")) {
					searchUser = dao.searchUserByUserId(searchField.getText());
					listBySearch(searchUser);
				} else {
					searchUser = dao.searchUserByUserPhone(searchField
							.getText());
					listBySearch(searchUser);
				}
			}
		});
		searchBtn.setBounds(444, 66, 97, 23);
		panel_2.add(searchBtn);

		JLabel lblNewLabel_1 = new JLabel("사용자 검색 :");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(119, 37, 88, 15);
		panel_2.add(lblNewLabel_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		getContentPane().add(panel_3, BorderLayout.SOUTH);
		panel_3.setPreferredSize(new Dimension(50, 40));
		panel_3.setLayout(null);

		/************************************************************/

		List<UserDto> users = new ArrayList<UserDto>();
		UserDao userdao = new UserDao();
		users = userdao.selectAllUser();

		DeleteBtn = new JButton("삭제");

		DeleteBtn.setBounds(621, 10, 77, 23);
		panel_3.add(DeleteBtn);

		JButton updateBtn = new JButton("수정");

		updateBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (table.getSelectedRowCount() > 0) {
					UserDao dao = new UserDao();
					String id = (table.getModel().getValueAt(
							table.getSelectedRow(), 0).toString());
					UserDto dto = dao.getData(id);

					String Userid = dto.getUserId();
					String UserName = dto.getUserName();
					String UserCompName = dto.getCompanyName();
					String UserPhone = dto.getUserPhone();
					String UserAddr = dto.getUserAddr();

					UserUpdate ma = new UserUpdate(Userid, UserName,
							UserCompName, UserPhone, UserAddr, UserList.this);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					ma.setLocation((dim.width / 2) - (ma.getWidth() / 2),
							(dim.height / 2) - (ma.getHeight() / 2));
					ma.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "사용자를 선택해주세요.");
				}
			}
		});
		updateBtn.setBounds(531, 10, 77, 23);
		panel_3.add(updateBtn);

		createTable(users);

		// if((table.getModel().getValueAt(table.getSelectedRow(),
		// 0).toString()) != null){

		DeleteBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				UserDao userdao = new UserDao();
				String id = null;
				List<UserDto> users = new ArrayList<UserDto>();

				if (table.getSelectedRowCount() > 0) {

					id = (table.getModel()
							.getValueAt(table.getSelectedRow(), 0).toString());
					if ((JOptionPane.showConfirmDialog(DeleteBtn,
							"삭제 하시겠습니까??", "삭제확인", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
						userdao.deleteUser(id);
						users = userdao.selectAllUser();
						listBySearch(users);
					}

				} else {
					JOptionPane.showMessageDialog(null, "회사를 선택해주세요.");
				}

			}
		});
	}

	private void createTable(List<UserDto> users) {

		table = new JTable();

		table.setFont(new Font("굴림", Font.PLAIN, 15));
		getContentPane().add(table, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane(table);
		Container c = getContentPane();
		c.add(scrollPane, BorderLayout.CENTER);

		listBySearch(users);

	}

	public void listBySearch(List<UserDto> search) {

		table.removeAll();
		String columnNames[] = { "아이디", "이름", "회사 명", "전화번호", "주소" };
		DefaultTableModel model = new DefaultTableModel() {
			public boolean isCellEditable(int rowindex, int Mollndex) {
				return false;
			}
		};
		for (int i = 0; i < columnNames.length; i++) {
			model.addColumn(columnNames[i]);
		}

		for (int i = 0; i < search.size(); i++) {
			String[] socrates = { search.get(i).getUserId(),
					search.get(i).getUserName(),
					search.get(i).getCompanyName(),
					search.get(i).getUserPhone(), search.get(i).getUserAddr() };
			model.addRow(socrates);
		}
		table.setModel(model);
	}
}
