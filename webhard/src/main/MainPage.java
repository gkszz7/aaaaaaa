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
import FTP.FTPUtil;

import company.CompanyInsert;
import company.CompanyList;

import file.FileInsert;
import folder.FolderInsert;
import folder.FolderUpdate;

public class MainPage extends JFrame {

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

	private String host = "192.168.1.6";
	private String fileid = "user1";
	private String password = "1234";
	private int port = 21;
	private String dir = "test/";

	int parentNum = 0;
	int homeNum = 0;
	int companyNum = 0;

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
	public MainPage(String name, final String id, int admin, int access) {
		setResizable(false);
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
					if (parentNum != 0) {
						selectNode = (DefaultMutableTreeNode) tree
								.getLastSelectedPathComponent();
						FolderInsert folderInsert = new FolderInsert(parentNum,
								id, companyNum, MainPage.this);
						Dimension dim = Toolkit.getDefaultToolkit()
								.getScreenSize();
						folderInsert.setLocation((dim.width / 2)
								- (folderInsert.getWidth() / 2),
								(dim.height / 2)
										- (folderInsert.getHeight() / 2));
						folderInsert.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
					}
				}
			});
			forder.add(insert);

			JMenuItem update = new JMenuItem("폴더 수정");
			update.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (parentNum != 0) {
						FolderUpdate folderupdate = new FolderUpdate(parentNum,
								MainPage.this);
						Dimension dim = Toolkit.getDefaultToolkit()
								.getScreenSize();
						folderupdate.setLocation((dim.width / 2)
								- (folderupdate.getWidth() / 2),
								(dim.height / 2)
										- (folderupdate.getHeight() / 2));
						folderupdate.setVisible(true);

					} else {
						JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
					}

				}
			});
			forder.add(update);

			delete = new JMenuItem("폴더 삭제");
			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					FolderDao dao = new FolderDao();
					selectNode = (DefaultMutableTreeNode) tree
							.getLastSelectedPathComponent();
					if (parentNum != 0) {
						deleteFolder(parentNum);
						tree.updateUI();
					} else {
						JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
					}

				}
			});
			forder.add(delete);

			JMenu file = new JMenu("파일");
			menuBar.add(file);

			JMenuItem flieinsert = new JMenuItem("파일 등록");
			flieinsert.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parentNum != 0) {
						selectNode = (DefaultMutableTreeNode) tree
								.getLastSelectedPathComponent();
						FileInsert flieI = new FileInsert(parentNum,
								companyNum, id, MainPage.this);
						Dimension dim = Toolkit.getDefaultToolkit()
								.getScreenSize();
						flieI.setLocation((dim.width / 2)
								- (flieI.getWidth() / 2), (dim.height / 2)
								- (flieI.getHeight() / 2));
						flieI.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
					}
				}
			});
			file.add(flieinsert);

			JMenuItem fileupdate = new JMenuItem("파일 수정");
			file.add(fileupdate);

			JMenuItem filedelete = new JMenuItem("파일 삭제");
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

						AccessList Al = new AccessList();
						Dimension dim = Toolkit.getDefaultToolkit()
								.getScreenSize();
						Al.setLocation((dim.width / 2) - (Al.getWidth() / 2),
								(dim.height / 2) - (Al.getHeight() / 2));
						Al.setVisible(true);

					}
				});
				falseUser.add(tureUser);

				UserList = new JMenu("사용자 목록");
				UserList.setHorizontalAlignment(SwingConstants.CENTER);
				menuBar.add(UserList);

				JMenuItem UserDelete = new JMenuItem("사용자 삭제");
				UserDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						UserList ul = new UserList();
						Dimension dim = Toolkit.getDefaultToolkit()
								.getScreenSize();
						ul.setLocation((dim.width / 2) - (ul.getWidth() / 2),
								(dim.height / 2) - (ul.getHeight() / 2));
						ul.setVisible(true);

					}
				});
				UserList.add(UserDelete);

				JMenu company = new JMenu("회사 추가");

				company = new JMenu("회사 추가");
				JMenuItem companyinsert = new JMenuItem("회사 등록");
				companyinsert.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CompanyInsert ci = new CompanyInsert(id, MainPage.this);
						Dimension dim = Toolkit.getDefaultToolkit()
								.getScreenSize();
						ci.setLocation((dim.width / 2) - (ci.getWidth() / 2),
								(dim.height / 2) - (ci.getHeight() / 2));
						ci.setVisible(true);
					}
				});

				company.add(companyinsert);
				JMenuItem companyList = new JMenuItem("\uD68C\uC0AC \uBAA9\uB85D");
				companyList.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CompanyList ci = new CompanyList();
						Dimension dim = Toolkit.getDefaultToolkit()
								.getScreenSize();
						ci.setLocation((dim.width / 2) - (ci.getWidth() / 2),
								(dim.height / 2) - (ci.getHeight() / 2));
						ci.setVisible(true);
					}
				});
				company.add(companyList);
				menuBar.add(company);
			}
		} else {

		}
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setPreferredSize(new Dimension(20, 35));
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(150, 70));
		panel.add(panel_3, BorderLayout.EAST);

		JLabel UserName = new JLabel();
		UserName.setText(name);
		panel_3.add(UserName);
		// admin 사용구간

		Logout = new Button("로그아웃");
		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
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
						int tableItemNum = Integer.parseInt((String) (table.getModel().getValueAt(table.getSelectedRow(),0)));
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
				public void mousePressed(MouseEvent e) {

					if (DEBUG && tree.getSelectionCount() > 0) {

						UserDao dao = new UserDao();
						CompanyDao cdao = new CompanyDao();
						int compnum = dao.selectcompany(id);

						DefaultMutableTreeNode selectedNode = getSelectedNode();
						String compname = selectedNode.getUserObject().toString();
						ItemDto selectObtion = (ItemDto) selectedNode.getUserObject();
						int parentNum1 = selectObtion.getCompanyNum();
						// int Home = selectObtion.getItemNum();
						parentNum = selectObtion.getItemNum();
						companyNum = selectObtion.getCompanyNum();

						int selectpnum = cdao.selectCompanyNum(compname);
						if (selectedNode != null) {
							if (compnum == selectpnum || id.equals("admin")|| compnum == parentNum1|| parentNum == homeNum) {
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
								
								fileList = fileDao.printFileInParentFolder(parentNum);
								
								listBySearch(folders, fileList);
								
								

								// //////////////////////////////////
								if (parentNode != null)
									pobj = parentNode.getUserObject();
								if (((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
										&& (tree.getSelectionCount() > 0)) {
									showMenu(e.getX(), e.getY(), id);
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

		/*
		 * tree.addTreeSelectionListener(new TreeSelectionListener() {
		 * 
		 * @Override public void valueChanged(TreeSelectionEvent e) { TreePath
		 * path = e.getPath(); DefaultMutableTreeNode element =
		 * (DefaultMutableTreeNode)path.getLastPathComponent(); ItemDto
		 * selectObtion = (ItemDto)element.getUserObject(); parentNum =
		 * selectObtion.getItemNum(); companyNum = selectObtion.getCompanyNum();
		 * 
		 * } });
		 */

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
						selectNode.remove(child);

					}
				}
			} else {
				dao.deleteFolder(child);
			}
		}
		if (((ArrayList<Integer>) dao.itemNumByParentNum(parentNum)).size() == 0) {
			dao.deleteFolder(parentNum);
			selectNode.remove(parentNum);
		}

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
			Object[] info = { folTable.get(i).getName(),folTable.get(i).getDate(), folTable.get(i).getUserId(),new ImageIcon("Folder-48.png")};
			model.addRow(info);
		}
		
		if(fileList != null){
			for (FileDto file : fileList) {
				Object[] files = { file.getName(),file.getDate(), file.getUserId(), new ImageIcon("Folder-48.png")};
				model.addRow(files);
			}
		}

		table.setModel(model);
		//table.removeColumn(table.getColumnModel().getColumn(0));
		table.updateUI();
	}

	protected void showMenu(int x, int y, final String id) {
		JPopupMenu popup = new JPopupMenu();
		JMenuItem mi = new JMenuItem("추가");
		popup.add(mi);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addNewNodeItem(id);
			}
		});
		mi = new JMenuItem("수정");
		TreePath path = tree.getSelectionPath();
		Object node = path.getLastPathComponent();
		if (node == tree.getModel().getRoot()) {
			mi.setEnabled(false);
		}
		popup.add(mi);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				modifySelectedNode();
			}
		});
		mi = new JMenuItem("삭제");
		if (node == tree.getModel().getRoot()) {
			mi.setEnabled(false);
		}
		popup.add(mi);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				deleteSelectedItems();
			}
		});

		mi = new JMenuItem("파일 다운로드");
		popup.add(mi);
		mi.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectNode = getSelectedNode();
				
				ItemDto selectItem = (ItemDto) selectNode.getUserObject();
				
				if(selectItem instanceof FileDto)
				{
					FileDto fileInfo = (FileDto) selectItem;
					FTPUtil ftp = new FTPUtil();
					ftp.init(host, fileid, password, port);
					int last =  fileInfo.getFileURL().lastIndexOf("\\");		
					String filePath = fileInfo.getFileURL().substring(last+1, fileInfo.getFileURL().length());
					ftp.download(dir, filePath, "D:\\"+filePath);
					ftp.disconnection();
				}
				else
				{
					//오류알림
				}
			}
		});
		popup.show(tree, x, y);
	}

	private DefaultMutableTreeNode getSelectedNode() {
		return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}

	// 폴더 수정
	private void modifySelectedNode() {
		if (parentNum != 0) {
			FolderUpdate folderupdate = new FolderUpdate(parentNum,
					MainPage.this);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			folderupdate.setLocation((dim.width / 2)
					- (folderupdate.getWidth() / 2), (dim.height / 2)
					- (folderupdate.getHeight() / 2));
			folderupdate.setVisible(true);

			if (folderupdate.isVisible() == false) {

			}
		} else {
			JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
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
	protected void deleteSelectedItems() {
		DefaultMutableTreeNode node = getSelectedNode();
		FolderDao dao = new FolderDao();

		if (parentNum != 0) {
			deleteFolder(parentNum);

		} else {
			JOptionPane.showMessageDialog(null, "폴더를 선택해주세요.");
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

		/*
		 * FolderDto homefDto = new FolderDto(); FolderDao folDao = new
		 * FolderDao(); homefDto = folDao.selectHomeFolder(); ItemDto homeFolder
		 * = homefDto; DefaultMutableTreeNode home = new
		 * DefaultMutableTreeNode(homeFolder); home.add((MutableTreeNode)
		 * newFolder); tree.updateUI();
		 */
	}

	public void addNewFolder(FolderDto newFolder) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(newFolder);
		selectNode.add(childNode);
		tree.updateUI();
	}

	// 폴더를 수정
	public void updateFolder(FolderDto newFolder) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(newFolder);
		System.out.println(childNode);
		selectNode.setUserObject(newFolder);
		tree.updateUI();
	}

	// 파일 등록
	public void addNewFile(FileDto newFile) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(newFile);
		selectNode.add(childNode);
		tree.updateUI();
	}
	
	public class CustomTreeCellRenderer extends DefaultTreeCellRenderer
	{
		Icon folderIcon = new ImageIcon("Folder.png");
		Icon closedIcon = new ImageIcon("Folder.png");
		Icon openIcon = new ImageIcon("Open Folder.png");
		Icon fileIcon = new ImageIcon("Files.png");
		
		public CustomTreeCellRenderer() {
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
	                row, hasFocus);

			ItemDto node = (ItemDto) ((DefaultMutableTreeNode)value).getUserObject();
			
			if(node instanceof FolderDto)
			{
				if(expanded)
				{
					setIcon(openIcon);
				}
				else
				{
					setIcon(closedIcon);
				}
			}
			else
			{
				setIcon(fileIcon);
			}
			return this;
		}
	}
}
