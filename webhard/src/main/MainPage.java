package main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import login.login;
import user.AccessList;
import user.UserList;
import webhard.dao.CompanyDao;
import webhard.dao.FileDao;
import webhard.dao.FolderDao;
import webhard.dao.UserDao;
import webhard.dto.FileDto;
import webhard.dto.FolderDto;
import webhard.dto.ItemDto;
import webhard.dto.UserDto;

import company.CompanyInsert;
import company.CompanyList;

import file.FileInsert;
import file.Filedownload;
import folder.FolderInsert;
import folder.FolderUpdate;

public class MainPage extends JFrame {
	
	private AccessList Al;
	private UserList ul;
	
	private CompanyList CompList;
	private CompanyInsert ci;
	
	private FolderInsert folderInsert;
	private FolderUpdate folderupdate;
	
	private FileInsert flieI;
	
	private Button Logout;
	private JTable table;
	private JPanel panel_2;
	private JTree tree;
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	private JMenuItem delete;
	private boolean DEBUG = true;
	private JMenu falseUser, company, UserList;
	private DefaultMutableTreeNode home;
	int parentNum = 0;
	int homeNum = 0;
	int companyNum = 0;
	int deleteComNum = 0;
	private UserDto uDto;
	private DefaultMutableTreeNode selectNode;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		/*
		 * EventQueue.invokeLater(new Runnable() { public void run() { try {
		 * String name = null; String id = null; int admin = 0; MainPage frame =
		 * new MainPage(name, id,admin);
		 * 
		 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace();
		 * } } });
		 */
	}

	/**
	 * Create the frame.
	 * 
	 * @param access
	 */
	public MainPage(String name,final String companyname, final String id, int admin, int access) {
		if(access==0){
			JOptionPane.showMessageDialog(null,id+"님은 안증대기 상태입니다.");
		}
		uDto = new UserDto();
		uDto.setUserId(id);
		uDto.setAdmin(admin);
		uDto.setAccess(access);
		setTitle("웹 하드");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 999, 696);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// 사용자
		if (access != 0) {
			JMenu forder = new JMenu("폴더");
			menuBar.add(forder);

			JMenuItem insert = new JMenuItem("폴더 생성");
			insert.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
						
					CompanyDao ComDao = new CompanyDao();
					FolderDao folDao = new FolderDao();
					int UserCompNum = ComDao.selectCompanyNum(companyname);
					int compNum = folDao.selectCompanyNumByItemNum(parentNum);
					
					if(folderInsert == null){	
						if(parentNum != homeNum){
							if(UserCompNum == compNum || id.equals("admin")){
						if (parentNum != 0) {
							selectNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
							folderInsert = new FolderInsert(parentNum,id, companyNum, MainPage.this);
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							folderInsert.setLocation((dim.width / 2) - (folderInsert.getWidth() / 2),
									(dim.height / 2) - (folderInsert.getHeight() / 2));
							
							folderInsert.setVisible(true);
							
						} else {
							JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
						}
							}else{
								JOptionPane.showMessageDialog(null, "등록 할 수없는 회사입니다.");
							}
						}else{
							JOptionPane.showMessageDialog(null, "HOME에서는 사용 할 수 없습니다.");
						}
					}else{
						JOptionPane.showMessageDialog(null,"이미 사용중인 서비스입니다.");
					}
					
				}
			});
			forder.add(insert);

			JMenuItem update = new JMenuItem("폴더 수정");
			update.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					CompanyDao ComDao = new CompanyDao();
					FolderDao folDao = new FolderDao();
					int UserCompNum = ComDao.selectCompanyNum(companyname);
					int compNum = folDao.selectCompanyNumByItemNum(parentNum);
					int selectParent = folDao.parentHomeNum(parentNum);
					if(folderupdate == null){
						if (parentNum != 0) {
						if(selectParent != homeNum){
						if(UserCompNum == compNum || id.equals("admin")){
						if(parentNum != homeNum){
							folderupdate = new FolderUpdate(parentNum,MainPage.this, companyNum, id);
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							folderupdate.setLocation((dim.width / 2) - (folderupdate.getWidth() / 2),
									(dim.height / 2) - (folderupdate.getHeight() / 2));
							folderupdate.setVisible(true);
								
						} else {
							JOptionPane.showMessageDialog(null, "수정 할 수없는 폴더입니다.");
						}
						}else{
							JOptionPane.showMessageDialog(null, "수정 할 수없는 폴더입니다.");
						}
						}else{
							JOptionPane.showMessageDialog(null, "수정 할 수없는 폴더입니다.");
						}
						}else{
							JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
						}
					}else{
						JOptionPane.showMessageDialog(null,"이미 사용중인 서비스입니다.");
					}
				}
			});
			forder.add(update);

			delete = new JMenuItem("폴더 삭제");
			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					FolderDao dao = new FolderDao();
					CompanyDao ComDao = new CompanyDao();
					int UserCompNum = ComDao.selectCompanyNum(companyname);
					int compNum = dao.selectCompanyNumByItemNum(parentNum);
					int selectParent = dao.parentHomeNum(parentNum);
					System.out.println(selectParent);
					System.out.println(parentNum);
					if(parentNum == homeNum){
						JOptionPane.showMessageDialog(null, "HOME에서는 사용 할 수 없습니다.");
					}else{
						if (parentNum != 0) {
							if(selectParent != homeNum ){
								if(UserCompNum == compNum || id.equals("admin")){
							selectNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
							deleteFolder(parentNum);
							tree.updateUI();
							}else{
								JOptionPane.showMessageDialog(null, "삭제 할 수없는 폴더입니다.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "삭제 할 수없는 폴더입니다.");
						}	
						}else{
							JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
						}
					}

				}
			});
			forder.add(delete);

			JMenu file = new JMenu("파일");
			menuBar.add(file);

			JMenuItem flieinsert = new JMenuItem("파일 등록");
			flieinsert.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					FolderDao dao = new FolderDao();
					CompanyDao ComDao = new CompanyDao();
					int UserCompNum = ComDao.selectCompanyNum(companyname);
					int compNum = dao.selectCompanyNumByItemNum(parentNum);
					if(parentNum != homeNum){
					if (parentNum != 0) {
						if(UserCompNum == compNum || id.equals("admin")){
							if(flieI == null){
								flieI = new FileInsert(parentNum,companyNum, id, MainPage.this);
								selectNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
								Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
						flieI.setLocation((dim.width / 2) - (flieI.getWidth() / 2),
								(dim.height / 2) - (flieI.getHeight() / 2));
						flieI.setVisible(true);
							}else{
								JOptionPane.showMessageDialog(null, "이미 사용중인 서비스입니다..");
							}
						}else{
							JOptionPane.showMessageDialog(null, "등록 할 수 없는 회사입니다.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
					}
					}else{
						JOptionPane.showMessageDialog(null, "HOME에서는 사용 할 수 없습니다.");
					}
				}
			});
			file.add(flieinsert);

			JMenuItem filedelete = new JMenuItem("파일 삭제");

			filedelete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					FolderDao dao = new FolderDao();
					CompanyDao ComDao = new CompanyDao();
					int UserCompNum = ComDao.selectCompanyNum(companyname);
					int compNum = dao.selectCompanyNumByItemNum(parentNum);
					
					if(parentNum != homeNum){
					if (parentNum != 0) {
						if(UserCompNum == compNum || id.equals("admin")){
						deleteFolder(parentNum);
						tree.updateUI();
						}else{
							JOptionPane.showMessageDialog(null, "삭제 할 수 없는 회사입니다.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "파일을 선택해주세요.");
					}
					}else{
						JOptionPane.showMessageDialog(null, "HOME에서는 사용 할 수 없습니다.");
					}
				}
			});
			file.add(filedelete);
			// 사용자

			// admin 사용
			if (admin == 1) {
				falseUser = new JMenu("인증대기 사용자");
				falseUser.setHorizontalAlignment(SwingConstants.CENTER);
				menuBar.add(falseUser);
				
				JMenuItem tureUser = new JMenuItem("인증대기 사용자 목록");
				tureUser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(Al == null){
							Al = new AccessList(MainPage.this);
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							Al.setLocation((dim.width / 2) - (Al.getWidth() / 2),
									(dim.height / 2) - (Al.getHeight() / 2));
							Al.setVisible(true);		
						}else{
							JOptionPane.showMessageDialog(null,"이미 사용중인 서비스입니다.");
						}

					}
				});
				falseUser.add(tureUser);

				UserList = new JMenu("사용자 목록");
				UserList.setHorizontalAlignment(SwingConstants.CENTER);
				
				menuBar.add(UserList);

				JMenuItem UserDelete = new JMenuItem("사용자 메뉴");
				UserDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {			
						if(ul==null){
							ul = new UserList(MainPage.this);
							Dimension dim = Toolkit.getDefaultToolkit()
									.getScreenSize();
							ul.setLocation((dim.width / 2) - (ul.getWidth() / 2),
									(dim.height / 2) - (ul.getHeight() / 2));
							ul.setVisible(true);
						}else{
							JOptionPane.showMessageDialog(null,"이미 사용중인 서비스입니다.");
						}
						
					}
				});
				UserList.add(UserDelete);

				JMenu company = new JMenu("회사 메뉴");

				company = new JMenu("회사 추가");
				JMenuItem companyinsert = new JMenuItem("회사 등록");
				companyinsert.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(ci==null){
							ci = new CompanyInsert(id, MainPage.this);
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							ci.setLocation((dim.width / 2) - (ci.getWidth() / 2),
									(dim.height / 2) - (ci.getHeight() / 2));
							ci.setVisible(true);
						}else{
							JOptionPane.showMessageDialog(null,"이미 사용중인 서비스입니다.");
						}
					}
				});

				company.add(companyinsert);
				JMenuItem companyList = new JMenuItem("회사 목록");
				companyList.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(CompList == null){
							    
							CompList = new CompanyList(MainPage.this);
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							CompList.setLocation((dim.width / 2) - (CompList.getWidth() / 2),
									(dim.height / 2) - (CompList.getHeight() / 2));
							CompList.setVisible(true);
						}else{
							JOptionPane.showMessageDialog(null,"이미 사용중인 서비스입니다.");
						}
					}
				});
				company.add(companyList);
				menuBar.add(company);
			}
		} else {

		}
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,TitledBorder.TOP, null, null));
		panel.setPreferredSize(new Dimension(20, 35));
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(330, 70));
		panel.add(panel_3, BorderLayout.EAST);
		// admin 사용구간
	                     
		Logout = new Button("로그아웃");
		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((JOptionPane.showConfirmDialog(Logout, "로그아웃하시겠습니까??",
						"로그아웃 확인", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
					login lo = new login();
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					lo.setLocation((dim.width / 2) - (lo.getWidth() / 2),
							(dim.height / 2) - (lo.getHeight() / 2));
					lo.setVisible(true);
					dispose();
				}     
			}
		});
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_8.setPreferredSize(new Dimension(240, 25));
		panel_3.add(panel_8);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 12));
		panel_8.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setText("회사 명 : "+companyname);
				
				JPanel panel_9 = new JPanel();
				panel_9.setPreferredSize(new Dimension(5, 5));
				panel_8.add(panel_9);
		
				JLabel UserName = new JLabel();
				UserName.setFont(new Font("굴림", Font.PLAIN, 12));
				panel_8.add(UserName);
				UserName.setHorizontalAlignment(SwingConstants.CENTER);
				UserName.setText("ID : "+id);
		panel_3.add(Logout);
		

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		splitPane = new JSplitPane();
		panel_1.add(splitPane);

		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		/******************************************************/

		/** 폴더 테이블 **/
		FolderDao folDao = new FolderDao();
		homeNum = folDao.selectHomeFolder().getItemNum();

		FolderDto homefDto = new FolderDto();

		List<FolderDto> folTable = new ArrayList<FolderDto>();
		folTable = folDao.printFolderInParentFolder(homeNum);

		createTable(folTable);

		/******************* 테이블 컬럼 더블클릭할시 *********************/
		if (access != 0) {
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					FolderDao folderDao = new FolderDao();
					List<FolderDto> folders = new ArrayList<FolderDto>();

					if (e.getClickCount() == 2) {
						int tableItemNum = Integer.parseInt((String) (table
								.getModel().getValueAt(table.getSelectedRow(),
								0)));
						folders = folderDao
								.printFolderInParentFolder(tableItemNum);
						listBySearch(folders, null);
					}
				}

			});
		}
		/******************************************************/

		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(200, 50));
		splitPane.setLeftComponent(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_5.setPreferredSize(new Dimension(70, 10));
		panel_4.add(panel_5, BorderLayout.NORTH);

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		panel_4.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new BorderLayout(0, 0));

		// ////////////////////////////////////////////

		/** 폴더 트리 **/

		homefDto = folDao.selectHomeFolder();
		ItemDto homeFolder = homefDto;
		home = new DefaultMutableTreeNode(homeFolder);
		// Select All Node
		setTree(home);

		/** 트리 아이콘 변경 **/
		panel_6.add(tree, BorderLayout.NORTH);
		// Custom Tree Cell Renderer
		tree.setCellRenderer(new CustomTreeCellRenderer());
		tree.setEditable(true);

		/**********************************************************/

		tree.addTreeExpansionListener(new TreeExpansionListener() {
			// 트리가 열렸을 때

			@Override
			public void treeExpanded(TreeExpansionEvent event) {
				// TODO Auto-generated method stub
			}

			@Override
			public void treeCollapsed(TreeExpansionEvent event) {
				// TODO Auto-generated method stub

			}

		});

		if (access != 0) {
			tree.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					if (DEBUG && tree.getSelectionCount() > 0) {

						UserDao dao = new UserDao();
						CompanyDao cdao = new CompanyDao();
						int compnum = dao.selectcompany(id);

						DefaultMutableTreeNode selectedNode = getSelectedNode();
						String compname = selectedNode.getUserObject()
								.toString();
						ItemDto selectObtion = (ItemDto) selectedNode
								.getUserObject();
						int parentNum1 = selectObtion.getCompanyNum();
						// int Home = selectObtion.getItemNum();
						parentNum = selectObtion.getItemNum();
					
						companyNum = selectObtion.getCompanyNum();

						int selectpnum = cdao.selectCompanyNum(compname);
						if (selectedNode != null) {
							if (compnum == selectpnum || id.equals("admin")
									|| compnum == parentNum1
									|| parentNum == homeNum) {
								Object pobj = null;
								DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode
										.getParent();
								// ///////////////////////////////
								FolderDao folderDao = new FolderDao();
								FileDao fileDao = new FileDao();
								List<FolderDto> folders = new ArrayList<FolderDto>();
								List<FileDto> fileList = new ArrayList<>();

								folders = folderDao
										.printFolderInParentFolder(parentNum);

								fileList = fileDao
										.printFileInParentFolder(parentNum);

								listBySearch(folders, fileList);
								
								boolean filecheck = fileDao.checkfile(parentNum);
								boolean foldercheck = folderDao.checkFolder(parentNum);
								// //////////////////////////////////
								if (parentNode != null)
									pobj = parentNode.getUserObject();
								if(parentNum != homeNum){
								if(filecheck == true && foldercheck == false){
									if (((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
											&& (tree.getSelectionCount() > 0)) {
										showfileMenu(e.getX(), e.getY(), id);
									}
								}else if(filecheck == false && foldercheck == true){
									if (((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
											&& (tree.getSelectionCount() > 0)) {
										showfolderMenu(e.getX(), e.getY(),companyname,id);
									}
								}
								}else{
									JOptionPane.showMessageDialog(null,"HOME에서는 사용 할 수 없습니다.");
								}
								
							}
						}
					}
				}
			});
		}
		panel_6.add(tree, BorderLayout.CENTER);

		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_6.add(panel_7, BorderLayout.WEST);

	}

	public void setTree(DefaultMutableTreeNode cycle) {

		FolderDao folDao = new FolderDao();
		DefaultMutableTreeNode home = null;

		List<ItemDto> childNodes = new ArrayList<ItemDto>();
		List<ItemDto> grandChildNodes = new ArrayList<ItemDto>();

		if (cycle != null) {
			home = cycle;
		}
		ItemDto homeDto = (ItemDto) home.getUserObject();
		ItemDto childDto;

		childNodes = folDao.selectChildByParentNum(homeDto.getItemNum());

		for (int i = 0; i < childNodes.size(); i++) {
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(
					childNodes.get(i));
			childDto = (ItemDto) childNode.getUserObject();
			grandChildNodes = folDao.selectChildByParentNum(childDto
					.getItemNum());

			if (grandChildNodes.size() != 0) {
				for (int j = 0; j < grandChildNodes.size(); j++) {
					DefaultMutableTreeNode grandChildNode = new DefaultMutableTreeNode(
							grandChildNodes.get(j));
					home.add(childNode);
					setTree(childNode);

					break;
				}

			} else {
				if (homeDto.getItemNum() == homeNum || childNode.isLeaf()) {
					home.add(childNode);

				}

			}

		}
		tree = new JTree(home);

	}

	public void deleteFolder(int itemNum) {
		
		
		ArrayList<Integer> childs = new ArrayList<Integer>();
		FolderDao dao = new FolderDao();
		childs = (ArrayList<Integer>) dao.itemNumByParentNum(itemNum);

		for (int i = 0; i < childs.size(); i++) {
			int child = childs.get(i);
			ArrayList<Integer> grandChilds = (ArrayList<Integer>) dao
					.itemNumByParentNum(child);
			if (grandChilds.size() != 0) {
				for (int j = 0; j < grandChilds.size(); j++) {
					deleteFolder(child);
					if (((ArrayList<Integer>) dao.itemNumByParentNum(child))
							.size() == 0) {
						dao.deleteFolder(child);
					}
				}
			} else {
				dao.deleteFolder(child);
			}
		}
		if (((ArrayList<Integer>) dao.itemNumByParentNum(parentNum)).size() == 0) {
			dao.deleteFolder(parentNum);

		}
		
		selectNode.removeAllChildren();
		selectNode.removeFromParent();
	}

	private void createTable(List<FolderDto> folTable) {
		panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout(0, 0));
		splitPane.setRightComponent(panel_2);

		table = new JTable();
		table.setFont(new Font("굴림", Font.PLAIN, 13));
		table.setBorder(null);

		JScrollPane scrollPane = new JScrollPane(table);
		Container c = getContentPane();

		panel_2.add(scrollPane, BorderLayout.CENTER);

		listBySearch(folTable, null);
	}

	public void listBySearch(List<FolderDto> folTable, List<FileDto> fileList) {
		table.removeAll();
		String columnNames[] = { "이름", "생성일", "만든 이", "경로", "비고" };
		String itemNumByString = null;
		DefaultTableModel model = new DefaultTableModel() {
			public boolean isCellEditable(int rowindex, int Mollndex) {
				return false;
			}
		};
		// model.setValueAt(aValue, row, column);
		for (int i = 0; i < columnNames.length; i++) {
			model.addColumn(columnNames[i]);
		}
		for (int i = 0; i < folTable.size(); i++) {
			Object[] info = { folTable.get(i).getName(),
					folTable.get(i).getDate(), folTable.get(i).getUserId() };
			model.addRow(info);
		}

		if (fileList != null) {
			for (FileDto file : fileList) {
				Object[] files = { file.getName(), file.getDate(),
						file.getUserId(), file.getFileURL(), file.getFileType() };
				model.addRow(files);
			}
		}

		table.setModel(model);
		// table.removeColumn(table.getColumnModel().getColumn(0));
		table.updateUI();
	}

	protected void showfolderMenu(int x, int y,final String companyname ,final String id) {
		JPopupMenu popup = new JPopupMenu();
		JMenuItem mi = new JMenuItem("폴더 추가");
		popup.add(mi);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addNewNodeItem(id);
			}
		});
		mi = new JMenuItem("폴더 수정");
		TreePath path = tree.getSelectionPath();
		Object node = path.getLastPathComponent();
		if (node == tree.getModel().getRoot()) {
			mi.setEnabled(false);
		}
		popup.add(mi);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				modifySelectedNode(companyname,id);
			}
		});
		mi = new JMenuItem("폴더 삭제");
		if (node == tree.getModel().getRoot()) {
			mi.setEnabled(false);
		}
		popup.add(mi);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				deleteSelectedItems(companyname,id);
			}
		});
		popup.show(tree, x, y);
	}

	protected void showfileMenu(int x, int y, final String id) {

		JPopupMenu popup = new JPopupMenu();
		JMenuItem mi = new JMenuItem("파일 다운로드");
		popup.add(mi);
		mi.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectNode = getSelectedNode();

				if (selectNode != null) {
					Filedownload FileDownload = new Filedownload(selectNode);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					FileDownload.setLocation(
							(dim.width / 2) - (FileDownload.getWidth() / 2),
							(dim.height / 2) - (FileDownload.getHeight() / 2));
					FileDownload.setVisible(true);

					if (FileDownload.isVisible() == false) {

					}
				} else {
					JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
				}
			}
		});
		popup.show(tree, x, y);
	}

	private DefaultMutableTreeNode getSelectedNode() {
		return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}

	// 폴더 수정
	private void modifySelectedNode(String companyname,String id) {
		CompanyDao ComDao = new CompanyDao();
		FolderDao folDao = new FolderDao();
		int UserCompNum = ComDao.selectCompanyNum(companyname);
		int compNum = folDao.selectCompanyNumByItemNum(parentNum);
		
		int selectParent = folDao.parentHomeNum(parentNum);
		
		if(folderupdate == null){
			if (parentNum != 0) {
			if(selectParent != homeNum){
			if(UserCompNum == compNum || id.equals("admin")){
			if(parentNum != homeNum){
				folderupdate = new FolderUpdate(parentNum,MainPage.this, companyNum, id);
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				folderupdate.setLocation((dim.width / 2) - (folderupdate.getWidth() / 2),
						(dim.height / 2) - (folderupdate.getHeight() / 2));
				folderupdate.setVisible(true);
					
			} else {
				JOptionPane.showMessageDialog(null, "수정할수없는 폴더입니다.");
			}
			}else{
				JOptionPane.showMessageDialog(null, "수정할수없는 폴더입니다.");
			}
			}else{
				JOptionPane.showMessageDialog(null, "수정할수없는 폴더입니다.");
			}
			}else{
				JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
			}
		}else{
			JOptionPane.showMessageDialog(null,"이미 사용중인 서비스입니다.");
		}

	}

	// 폴더 추가
	private void addNewNodeItem(String id) {
		DefaultMutableTreeNode parent = getSelectedNode();
		if (parentNum != 0) {
			selectNode = (DefaultMutableTreeNode) tree
					.getLastSelectedPathComponent();
			FolderInsert folderInsert = new FolderInsert(parentNum, id,
					companyNum, MainPage.this);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			folderInsert.setLocation((dim.width / 2)
					- (folderInsert.getWidth() / 2), (dim.height / 2)
					- (folderInsert.getHeight() / 2));
			folderInsert.setVisible(true);

			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			model.reload(home);
		} else {
			JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
		}
	}

	// 폴더 삭제
	protected void deleteSelectedItems(String companyname,String id) {
		
		FolderDao dao = new FolderDao();
		CompanyDao ComDao = new CompanyDao();
		int UserCompNum = ComDao.selectCompanyNum(companyname);
		int compNum = dao.selectCompanyNumByItemNum(parentNum);
		int selectParent = dao.parentHomeNum(parentNum);
		
		if(parentNum == homeNum){
			JOptionPane.showMessageDialog(null, "Home폴더는 삭제하실 수 없습니다.");
		}else{
			if (parentNum != 0) {
				if(selectParent != homeNum ){
					if(UserCompNum == compNum || id.equals("admin")){
				selectNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				deleteFolder(parentNum);
				tree.updateUI();
				}else{
					JOptionPane.showMessageDialog(null, "삭제할수없는 폴더업니다.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "삭제할수없는 폴더업니다.");
			}	
			}else{
				JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
			}
		}
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	/**
	 * 폴더를 추가한다.
	 * 
	 * @param newFolder
	 */
	public void addCompanyFolder(FolderDto newFolder) {

		DefaultMutableTreeNode nFolder = new DefaultMutableTreeNode(newFolder);
		home.add(nFolder);
		tree.updateUI();

	}

	public void deleteCompanyFolder(int itemNum, String name) {
		DefaultMutableTreeNode node = null;
		deleteComNum = itemNum;
		for(int i = 0; i<home.getChildCount(); i++){
			if(home.getChildAt(i).toString().equals(name)){
				node = (DefaultMutableTreeNode) home.getChildAt(i);			
				selectNode = node;
				deleteCompanyFolder(itemNum);
			}
		}		
		
		tree.updateUI();

	}
	public void deleteCompanyFolder(int itemNum) {
		
		
		ArrayList<Integer> childs = new ArrayList<Integer>();
		FolderDao dao = new FolderDao();
		childs = (ArrayList<Integer>) dao.itemNumByParentNum(itemNum);

		for (int i = 0; i < childs.size(); i++) {
			int child = childs.get(i);
			ArrayList<Integer> grandChilds = (ArrayList<Integer>) dao
					.itemNumByParentNum(child);
			if (grandChilds.size() != 0) {
				for (int j = 0; j < grandChilds.size(); j++) {
					deleteCompanyFolder(child);
					if (((ArrayList<Integer>) dao.itemNumByParentNum(child))
							.size() == 0) {
						dao.deleteFolder(child);
					}
				}
			} else {
				dao.deleteFolder(child);
			}
		}
		if (((ArrayList<Integer>) dao.itemNumByParentNum(deleteComNum)).size() == 0) {
			dao.deleteFolder(deleteComNum);

		}
		
		selectNode.removeAllChildren();
		selectNode.removeFromParent();
	}

	public void addNewFolder(FolderDto newFolder) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(newFolder);
		selectNode.add(childNode);
		tree.updateUI();
	}

	// 폴더를 수정
	public void updateFolder(FolderDto newFolder) {
		getSelectedNode().setUserObject(newFolder);
		tree.updateUI();
	}

	// 파일 등록
	public void addNewFile(FileDto newFile) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(newFile);
		selectNode.add(childNode);
		tree.updateUI();
	}
	
	//창 한번만 뜨게 만들기
	public FolderInsert openFolderInsert(FolderInsert values){
		
		folderInsert = values;
		
		return folderInsert;
	}
	
	public FolderUpdate openFolderUpdate(FolderUpdate values){
		
		folderupdate = values;
		
		return folderupdate;
	}
	
	public AccessList openAccessList(AccessList values){
		
		Al = values;
		
		return Al;
	}
	
	public UserList openUserList(UserList values){
		
		ul = values;
		
		return ul;
	}
	
	public CompanyInsert openCompanyInsert(CompanyInsert values){
		
		ci = values;
		
		return ci;
	}

	public CompanyList openCompanyList(CompanyList values){
		
		CompList = values;
		
		return CompList;
	}
	
	public FileInsert openFileInsert(FileInsert values){
		
		flieI = values;
		
		return flieI;
	}
	
	@SuppressWarnings("serial")
	public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
		
		Icon folderIcon = new ImageIcon("Folder.png");
		Icon closedIcon = new ImageIcon("Folder.png");
		Icon openIcon = new ImageIcon("Open Folder.png");
		Icon fileIcon = new ImageIcon("Files.png");
		Icon zipIcon = new ImageIcon("ZIP.png");
		Icon mp3Icon = new ImageIcon("MP3.png");
		Icon ExcelIcon = new ImageIcon("MS Excel.png");
		Icon PowerPointIcon = new ImageIcon("MS PowerPoint.png");
		Icon TextIcon = new ImageIcon("Text Box.png");
		
		public CustomTreeCellRenderer() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded,leaf, row, hasFocus);

			ItemDto node = (ItemDto) ((DefaultMutableTreeNode) value).getUserObject();

			if (node instanceof FolderDto) {
				if (expanded) {
					setIcon(openIcon);
				} else {
					setIcon(closedIcon);
				}
			} else {
				setIcon(fileIcon);
			}
			return this;
		}
	}
}
