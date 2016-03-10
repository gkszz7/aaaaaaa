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
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
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
import FTP.FTPUtil;

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
	private Filedownload FileDownload;

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
	private String ftpid = "user1";
	private String password = "1234";
	private int port = 21;
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
	public MainPage(String name, final String companyname, final String id, int admin, int access) {
		if (access == 0) {
			JOptionPane.showMessageDialog(null, id + "���� ������� �����Դϴ�.");
		}
		uDto = new UserDto();
		uDto.setUserId(id);
		uDto.setAdmin(admin);
		uDto.setAccess(access);
		setTitle("Web Hard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 999, 696);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// �����
		if (access != 0) {
			JMenu forder = new JMenu("����");
			menuBar.add(forder);

			JMenuItem insert = new JMenuItem("���� ����");
			insert.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					CompanyDao ComDao = new CompanyDao();
					FolderDao folDao = new FolderDao();
					int UserCompNum = ComDao.selectCompanyNum(companyname);
					int compNum = folDao.selectCompanyNumByItemNum(parentNum);

					ItemDto selectItem = (ItemDto) selectNode.getUserObject();
					int selectItemNum = selectItem.getItemNum();
					int selectItemCom = selectItem.getCompanyNum();
					int selectItemParNum = selectItem.getParentNum();
					boolean checkfolder = folDao.checkFolder(selectItemNum);

					if (folderInsert == null) {
						if (parentNum != homeNum) {
							if (UserCompNum == compNum || id.equals("admin")
									|| (selectItemCom == 0 && selectItemParNum != homeNum)) {
								if (checkfolder == true) {
									if (parentNum != 0) {
										selectNode = getSelectedNode();
										folderInsert = new FolderInsert(parentNum, id, companyNum, MainPage.this);
										Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
										folderInsert.setLocation((dim.width / 2) - (folderInsert.getWidth() / 2),
												(dim.height / 2) - (folderInsert.getHeight() / 2));

										folderInsert.setVisible(true);
									} else {
										JOptionPane.showMessageDialog(null, "������ �������ּ���.");
									}
								} else {
									JOptionPane.showMessageDialog(null, "������ �������ּ���.");
								}
							} else {
								JOptionPane.showMessageDialog(null, "���� �� �� �����ϴ�.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "HOME������ ��� �� �� �����ϴ�.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "�̹� ������� �����Դϴ�.");
					}

				}
			});
			forder.add(insert);

			JMenuItem update = new JMenuItem("���� ����");
			update.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					CompanyDao ComDao = new CompanyDao();
					FolderDao folDao = new FolderDao();
					int UserCompNum = ComDao.selectCompanyNum(companyname);
					int compNum = folDao.selectCompanyNumByItemNum(parentNum);
					int selectParent = folDao.parentHomeNum(parentNum);

					ItemDto selectItem = (ItemDto) selectNode.getUserObject();
					int selectItemNum = selectItem.getItemNum();
					int selectItemCom = selectItem.getCompanyNum();
					int selectItemParNum = selectItem.getParentNum();
					boolean checkfolder = folDao.checkFolder(selectItemNum);
					if (folderupdate == null) {
						if (parentNum != 0) {
							if (selectParent != homeNum) {
								if (UserCompNum == compNum || id.equals("admin")
										|| (selectItemCom == 0 && selectItemParNum != homeNum)) {
									if (checkfolder == true) {
										if (parentNum != homeNum) {
											folderupdate = new FolderUpdate(parentNum, MainPage.this, companyNum, id);
											Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
											folderupdate.setLocation((dim.width / 2) - (folderupdate.getWidth() / 2),
													(dim.height / 2) - (folderupdate.getHeight() / 2));
											folderupdate.setVisible(true);

										} else {
											JOptionPane.showMessageDialog(null, "���� �� �� ���� �����Դϴ�.");
										}
									} else {
										JOptionPane.showMessageDialog(null, "������ ���� �� �� �����ϴ�.");
									}
								} else {
									JOptionPane.showMessageDialog(null, "���� �� �� ���� �����Դϴ�.");
								}
							} else {
								JOptionPane.showMessageDialog(null, "���� �� �� ���� �����Դϴ�.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "������ �������ּ���.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "�̹� ������� �����Դϴ�.");
					}
				}
			});
			forder.add(update);

			delete = new JMenuItem("���� ����");
			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					FolderDao dao = new FolderDao();
					CompanyDao ComDao = new CompanyDao();
					int UserCompNum = ComDao.selectCompanyNum(companyname);
					int compNum = dao.selectCompanyNumByItemNum(parentNum);
					int selectParent = dao.parentHomeNum(parentNum);

					FolderDao folderDao = new FolderDao();
					ItemDto selectItem = (ItemDto) selectNode.getUserObject();
					int selectItemNum = selectItem.getItemNum();
					int selectItemCom = selectItem.getCompanyNum();
					int selectItemParNum = selectItem.getParentNum();
					boolean checkfolder = folderDao.checkFolder(selectItemNum);

					if (parentNum == homeNum) {
						JOptionPane.showMessageDialog(null, "HOME������ ��� �� �� �����ϴ�.");
					} else {
						if (parentNum != 0) {
							if (selectParent != homeNum) {
								if (checkfolder == true) {
									if (UserCompNum == compNum || id.equals("admin")
											|| (selectItemCom == 0 && selectItemParNum != homeNum)) {
										if ((JOptionPane.showConfirmDialog(null, "���� �Ͻðڽ��ϱ�??", "����Ȯ��",
												JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
											selectNode = getSelectedNode();
											deleteFolder(parentNum);
											tree.updateUI();
										}
									} else {
										JOptionPane.showMessageDialog(null, "���� �� �� ���� �����Դϴ�.");
									}
								} else {
									JOptionPane.showMessageDialog(null, "������ �ƴմϴ�.");
								}
							} else {
								JOptionPane.showMessageDialog(null, "���� �� �� ���� �����Դϴ�.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "������ �������ּ���.");
						}
					}

				}
			});
			forder.add(delete);

			JMenu file = new JMenu("����");
			menuBar.add(file);

			JMenuItem flieinsert = new JMenuItem("���� ���");
			flieinsert.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					FolderDao dao = new FolderDao();
					CompanyDao ComDao = new CompanyDao();
					int UserCompNum = ComDao.selectCompanyNum(companyname);
					int compNum = dao.selectCompanyNumByItemNum(parentNum);
					ItemDto selectItem = (ItemDto) selectNode.getUserObject();
					int selectItemNum = selectItem.getItemNum();
					int selectItemCom = selectItem.getCompanyNum();
					boolean foldercheck = dao.checkFolder(selectItemNum);

					if (parentNum != homeNum) {
						if (parentNum != 0) {
							if (UserCompNum == compNum || id.equals("admin") || selectItemCom == 0) {
								if (foldercheck == true) {
									if (flieI == null) {
										selectNode = getSelectedNode();
										flieI = new FileInsert(parentNum, companyNum, id, MainPage.this);
										Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
										flieI.setLocation((dim.width / 2) - (flieI.getWidth() / 2),
												(dim.height / 2) - (flieI.getHeight() / 2));
										flieI.setVisible(true);
									} else {
										JOptionPane.showMessageDialog(null, "�̹� ������� �����Դϴ�.");
									}
								} else {
									JOptionPane.showMessageDialog(null, "�������� ����� �����մϴ�.");
								}
							} else {
								JOptionPane.showMessageDialog(null, "��� �� �� �����ϴ�.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "������ �������ּ���.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "HOME������ ��� �� �� �����ϴ�.");
					}
				}
			});
			file.add(flieinsert);

			JMenuItem filedelete = new JMenuItem("���� ����");

			filedelete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					FolderDao dao = new FolderDao();
					CompanyDao ComDao = new CompanyDao();
					FileDao fileDao = new FileDao();
					int UserCompNum = ComDao.selectCompanyNum(companyname);
					int compNum = dao.selectCompanyNumByItemNum(parentNum);
					ItemDto selectItem = (ItemDto) selectNode.getUserObject();
					int selectItemNum = selectItem.getItemNum();

					boolean filecheck = fileDao.checkfile(selectItemNum);
					if (parentNum != homeNum) {
						if (parentNum != 0) {
							if (filecheck == true) {
								if (UserCompNum == compNum || id.equals("admin")) {
									if ((JOptionPane.showConfirmDialog(null, "���� �Ͻðڽ��ϱ�??", "����Ȯ��",
											JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
										selectNode = getSelectedNode();
										ItemDto item = (ItemDto) selectNode.getUserObject();
										int fileNum = item.getItemNum();
										deleteFile(fileNum);
										tree.updateUI();
									}
								} else {
									JOptionPane.showMessageDialog(null, "���� �� �� �����ϴ�.");
								}
							} else {
								JOptionPane.showMessageDialog(null, "������ �ƴմϴ�.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "������ �������ּ���.");
						}
					}else{
						JOptionPane.showMessageDialog(null, "HOME������ ��� �� �� �����ϴ�.");
					}
				}
			});
			file.add(filedelete);

			JMenuItem filedownload = new JMenuItem("���� �ٿ�ε�");
			filedownload.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					FileDao fileDao = new FileDao();

					// TODO Auto-generated method stub
					selectNode = getSelectedNode();
					ItemDto selectItem = (ItemDto) selectNode.getUserObject();
					int selectItemNum = selectItem.getItemNum();
					boolean filecheck = fileDao.checkfile(selectItemNum);
					if (selectNode != null) {
						if (filecheck == true) {
							FileDownload = new Filedownload(selectNode, MainPage.this);
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							FileDownload.setLocation((dim.width / 2) - (FileDownload.getWidth() / 2),
									(dim.height / 2) - (FileDownload.getHeight() / 2));
							FileDownload.setVisible(true);

							if (FileDownload.isVisible() == false) {

							}
						} else {
							JOptionPane.showMessageDialog(null, "������ �ٿ�ε� �� �� �����ϴ�.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "������ �������ּ���.");
					}
				}
			});

			file.add(filedownload);
			// �����

			// admin ���
			if (admin == 1) {
				falseUser = new JMenu("������� �����");
				falseUser.setHorizontalAlignment(SwingConstants.CENTER);
				menuBar.add(falseUser);

				JMenuItem tureUser = new JMenuItem("������� ����� ���");
				tureUser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if (Al == null) {
							Al = new AccessList(MainPage.this);
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							Al.setLocation((dim.width / 2) - (Al.getWidth() / 2),
									(dim.height / 2) - (Al.getHeight() / 2));
							Al.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null, "�̹� ������� �����Դϴ�.");
						}

					}
				});
				falseUser.add(tureUser);

				UserList = new JMenu("����� ���");
				UserList.setHorizontalAlignment(SwingConstants.CENTER);

				menuBar.add(UserList);

				JMenuItem UserDelete = new JMenuItem("����� �޴�");
				UserDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (ul == null) {
							ul = new UserList(MainPage.this);
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							ul.setLocation((dim.width / 2) - (ul.getWidth() / 2),
									(dim.height / 2) - (ul.getHeight() / 2));
							ul.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null, "�̹� ������� �����Դϴ�.");
						}

					}
				});
				UserList.add(UserDelete);

				JMenu company = new JMenu("ȸ�� �޴�");

				company = new JMenu("ȸ�� �߰�");
				JMenuItem companyinsert = new JMenuItem("ȸ�� ���");
				companyinsert.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if (ci == null) {
							ci = new CompanyInsert(id, MainPage.this);
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							ci.setLocation((dim.width / 2) - (ci.getWidth() / 2),
									(dim.height / 2) - (ci.getHeight() / 2));
							ci.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null, "�̹� ������� �����Դϴ�.");
						}
					}
				});

				company.add(companyinsert);
				JMenuItem companyList = new JMenuItem("ȸ�� ���");
				companyList.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if (CompList == null) {

							CompList = new CompanyList(MainPage.this, id);
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							CompList.setLocation((dim.width / 2) - (CompList.getWidth() / 2),
									(dim.height / 2) - (CompList.getHeight() / 2));
							CompList.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null, "�̹� ������� �����Դϴ�.");
						}
					}
				});
				company.add(companyList);
				menuBar.add(company);
			}
		} else {

		}
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setPreferredSize(new Dimension(20, 35));
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(330, 70));
		panel.add(panel_3, BorderLayout.EAST);
		// admin ��뱸��

		Logout = new Button("�α׾ƿ�");
		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((JOptionPane.showConfirmDialog(null, "�α׾ƿ��Ͻðڽ��ϱ�??", "�α׾ƿ� Ȯ��",
						JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
					login lo = new login();
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					lo.setLocation((dim.width / 2) - (lo.getWidth() / 2), (dim.height / 2) - (lo.getHeight() / 2));
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
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 12));
		panel_8.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setText("ȸ�� �� : " + companyname);

		JPanel panel_9 = new JPanel();
		panel_9.setPreferredSize(new Dimension(5, 5));
		panel_8.add(panel_9);

		JLabel UserName = new JLabel();
		UserName.setFont(new Font("����", Font.PLAIN, 12));
		panel_8.add(UserName);
		UserName.setHorizontalAlignment(SwingConstants.CENTER);
		UserName.setText("ID : " + id);
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

		/** ���� ���̺� **/
		FolderDao folDao = new FolderDao();
		homeNum = folDao.selectHomeFolder().getItemNum();

		FolderDto homefDto = new FolderDto();

		List<FolderDto> folTable = new ArrayList<FolderDto>();
		folTable = folDao.printFolderInParentFolder(homeNum);

		createTable(folTable);

		/******************* ���̺� �÷� ����Ŭ���ҽ� *********************/
		if (access != 0) {
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					FolderDao folderDao = new FolderDao();
					List<FolderDto> folders = new ArrayList<FolderDto>();

					if (e.getClickCount() == 2) {
						int tableItemNum = Integer
								.parseInt((String) (table.getModel().getValueAt(table.getSelectedRow(), 0)));
						folders = folderDao.printFolderInParentFolder(tableItemNum);
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

		/** ���� Ʈ�� **/

		homefDto = folDao.selectHomeFolder();
		ItemDto homeFolder = homefDto;
		home = new DefaultMutableTreeNode(homeFolder);
		// Select All Node
		setTree(home);

		/** Ʈ�� ��� Ŭ�� ���� �� **/
		panel_6.add(tree, BorderLayout.NORTH);
		// Custom Tree Cell Renderer
		tree.setCellRenderer(new CustomTreeCellRenderer());
		tree.setEditable(false);

		if (access != 0) {
			tree.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					UserDao dao = new UserDao();
					CompanyDao cdao = new CompanyDao();
					FolderDao folderDao = new FolderDao();
					FileDao fileDao = new FileDao();
					List<FolderDto> folders = new ArrayList<FolderDto>();
					List<FileDto> fileList = new ArrayList<>();

					int compnum = dao.selectcompany(id);

					selectNode = getSelectedNode();

					if (selectNode != null) {
						ItemDto selectObtion = (ItemDto) selectNode.getUserObject();
						// int Home = selectObtion.getItemNum();
						parentNum = selectObtion.getItemNum();
						companyNum = selectObtion.getCompanyNum();

						int selectItemNum = selectObtion.getItemNum();

						boolean filecheckd = fileDao.checkfile(selectItemNum);
						
						//���� Ŭ��
						if (filecheckd == true) {

							if (e.getClickCount() == 2) {

								try {													
									
									FileDto dto = fileDao.selectFileByItemNum(selectItemNum);
									//String destination = "C:/Users/CHUL/Desktop/test/";
									String filename = dto.getName();
									String saveFileName = filename+"."+dto.getFileType();												
									FTPUtil ftp = new FTPUtil();
									//File saveFile = new File(destination+saveFileName);
									
									ftp.openFile("192.168.1.6", "user1", "1234", 21, filename);
									/*
									 * ftp.init(host, ftpid, password, port);
									ftp.download(host, ftpid,password, filename, saveFile);
									Runtime runtime = Runtime.getRuntime();
									ftp.disconnection();								
									
								    File file = new File(destination+saveFileName);
								    
								    if(file != null){
								     
								    Process p = runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());
								    
									p.waitFor();
									
								    }
								    */
								    
								     
								} catch (SecurityException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IllegalArgumentException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}
					if (DEBUG && tree.getSelectionCount() > 0) {

						if (selectNode != null) {
							ItemDto selectObtion = (ItemDto) selectNode.getUserObject();

							String compname = selectNode.getUserObject().toString();

							int selectpnum = cdao.selectCompanyNum(compname);
							int selectItemCom = selectObtion.getCompanyNum();
							int parentNum1 = selectObtion.getCompanyNum();
							if (compnum == selectpnum || id.equals("admin") || compnum == parentNum1
									|| parentNum == homeNum || selectItemCom == 0) {
								Object pobj = null;
								DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectNode.getParent();
								// ///////////////////////////////

								folders = folderDao.printFolderInParentFolder(parentNum);

								fileList = fileDao.printFileInParentFolder(parentNum);

								listBySearch(folders, fileList);

								boolean filecheck = fileDao.checkfile(parentNum);
								boolean foldercheck = folderDao.checkFolder(parentNum);
								// //////////////////////////////////

								if (parentNum != homeNum) {
									if (parentNode != null)
										pobj = parentNode.getUserObject();
									if (filecheck == true && foldercheck == false) {
										if (((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
												&& (tree.getSelectionCount() > 0)) {
											showfileMenu(e.getX(), e.getY(), id);
										}
									} else if (filecheck == false && foldercheck == true) {
										if (((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
												&& (tree.getSelectionCount() > 0)) {
											showfolderMenu(e.getX(), e.getY(), companyname, id);
										}
									}
								} else {
									JOptionPane.showMessageDialog(null, "HOME������ ��� �� �� �����ϴ�.");
								}

							}
						}
					}
				}
			});
		} else {
			tree.disable();
		}
		panel_6.add(tree, BorderLayout.CENTER);

		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_6.add(panel_7, BorderLayout.WEST);

		/************* ���� ���� ȸ�� ���� �� �������� �� ********************/
		FolderDao folderDao = new FolderDao();
		FolderDto shareFolder = new FolderDto();
		shareFolder = folderDao.printShareFolder();
		int shareFolNum = shareFolder.getItemNum();

		if (id.equals("admin") == false) {
			CompanyDao comDao = new CompanyDao();
			int userCompNum = comDao.selectCompanyNum(companyname);
			DefaultMutableTreeNode disableNode = null;

			for (int i = 0; i < home.getChildCount(); i++) {
				disableNode = (DefaultMutableTreeNode) home.getChildAt(i);
				ItemDto item = (ItemDto) disableNode.getUserObject();
				if (item.getItemNum() != shareFolNum) {
					if (userCompNum != item.getCompanyNum()) {
						disableNode.setAllowsChildren(false);
					}
				}

			}

		}
		/**************************************************************/
	}

	public void setTree(DefaultMutableTreeNode cycle) {

		FolderDao folDao = new FolderDao();
		FolderDto fDto;
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
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childNodes.get(i));
			childDto = (ItemDto) childNode.getUserObject();
			fDto = folDao.printFolderbyNum(childDto.getItemNum());
			if (fDto.getFolderType() == 0) {
				grandChildNodes = folDao.selectChildByParentNum(childDto.getItemNum());

				if (grandChildNodes.size() != 0) {
					for (int j = 0; j < grandChildNodes.size(); j++) {
						DefaultMutableTreeNode grandChildNode = new DefaultMutableTreeNode(grandChildNodes.get(j));
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
		}

		for (int i = 0; i < childNodes.size(); i++) {
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childNodes.get(i));
			childDto = (ItemDto) childNode.getUserObject();
			fDto = folDao.printFolderbyNum(childDto.getItemNum());
			if (fDto.getFolderType() != 0) {
				if (homeDto.getItemNum() == homeNum) {
					home.add(childNode);
					setTree(childNode);
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
			ArrayList<Integer> grandChilds = (ArrayList<Integer>) dao.itemNumByParentNum(child);
			if (grandChilds.size() != 0) {
				for (int j = 0; j < grandChilds.size(); j++) {
					deleteFolder(child);
					if (((ArrayList<Integer>) dao.itemNumByParentNum(child)).size() == 0) {
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

	public void deleteFile(int itemNum) {
		FileDao dao = new FileDao();
		dao.deleteFile(itemNum);
		selectNode.removeFromParent();
	}

	private void createTable(List<FolderDto> folTable) {
		panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout(0, 0));
		splitPane.setRightComponent(panel_2);

		table = new JTable();
		table.setFont(new Font("����", Font.PLAIN, 13));
		table.setBorder(null);

		JScrollPane scrollPane = new JScrollPane(table);
		Container c = getContentPane();

		panel_2.add(scrollPane, BorderLayout.CENTER);

		listBySearch(folTable, null);
	}

	public void listBySearch(List<FolderDto> folTable, List<FileDto> fileList) {
		table.removeAll();
		String columnNames[] = { "�̸�", "������", "���� ��", "���", "���" };
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
			Object[] info = { folTable.get(i).getItemNum(), folTable.get(i).getName(), folTable.get(i).getDate(),
					folTable.get(i).getUserId() };
			model.addRow(info);
		}

		if (fileList != null) {
			for (FileDto file : fileList) {
				Object[] files = { file.getItemNum(), file.getName(), file.getDate(), file.getUserId(),
						file.getFileURL(), file.getFileType() };
				model.addRow(files);
			}
		}

		table.setModel(model);
		table.removeColumn(table.getColumnModel().getColumn(0));
		table.updateUI();
	}

	protected void showfolderMenu(int x, int y, final String companyname, final String id) {
		JPopupMenu popup = new JPopupMenu();
		JMenuItem mi = new JMenuItem("���� �߰�");
		popup.add(mi);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addNewNodeItem(id);
			}
		});
		mi = new JMenuItem("���� ����");
		TreePath path = tree.getSelectionPath();
		Object node = path.getLastPathComponent();
		if (node == tree.getModel().getRoot()) {
			mi.setEnabled(false);
		}
		popup.add(mi);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				modifySelectedNode(companyname, id);
			}
		});
		mi = new JMenuItem("���� ����");
		if (node == tree.getModel().getRoot()) {
			mi.setEnabled(false);
		}
		popup.add(mi);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				deleteSelectedItems(companyname, id);
			}
		});
		popup.show(tree, x, y);
	}

	protected void showfileMenu(int x, int y, final String id) {

		JPopupMenu popup = new JPopupMenu();
		JMenuItem mi = new JMenuItem("���� �ٿ�ε�");
		popup.add(mi);
		mi.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectNode = getSelectedNode();

				if (selectNode != null) {
					FileDownload = new Filedownload(selectNode, MainPage.this);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					FileDownload.setLocation((dim.width / 2) - (FileDownload.getWidth() / 2),
							(dim.height / 2) - (FileDownload.getHeight() / 2));
					FileDownload.setVisible(true);

					if (FileDownload.isVisible() == false) {

					}
				} else {
					JOptionPane.showMessageDialog(null, "������ �������ּ���.");
				}
			}
		});
		popup.show(tree, x, y);
	}

	private DefaultMutableTreeNode getSelectedNode() {
		return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}

	// ���� ����
	private void modifySelectedNode(String companyname, String id) {
		CompanyDao ComDao = new CompanyDao();
		FolderDao folDao = new FolderDao();
		int UserCompNum = ComDao.selectCompanyNum(companyname);
		int compNum = folDao.selectCompanyNumByItemNum(parentNum);
		ItemDto selectItem = (ItemDto) selectNode.getUserObject();
		int selectItemCom = selectItem.getCompanyNum();
		int selectItemParNum = selectItem.getParentNum();

		int selectParent = folDao.parentHomeNum(parentNum);

		if (folderupdate == null) {
			if (parentNum != 0) {
				if (selectParent != homeNum) {
					if (UserCompNum == compNum || id.equals("admin")
							|| (selectItemCom == 0 && selectItemParNum != homeNum)) {
						if (parentNum != homeNum) {
							folderupdate = new FolderUpdate(parentNum, MainPage.this, companyNum, id);
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							folderupdate.setLocation((dim.width / 2) - (folderupdate.getWidth() / 2),
									(dim.height / 2) - (folderupdate.getHeight() / 2));
							folderupdate.setVisible(true);

						} else {
							JOptionPane.showMessageDialog(null, "���� �� �� ���� �����Դϴ�.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "���� �� �� ���� �����Դϴ�.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "���� �� �� ���� �����Դϴ�.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "������ �������ּ���.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "�̹� ������� �����Դϴ�.");
		}

	}

	// ���� �߰�
	private void addNewNodeItem(String id) {
		DefaultMutableTreeNode parent = getSelectedNode();
		if (parentNum != 0) {
			selectNode = getSelectedNode();
			folderInsert = new FolderInsert(parentNum, id, companyNum, MainPage.this);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			folderInsert.setLocation((dim.width / 2) - (folderInsert.getWidth() / 2),
					(dim.height / 2) - (folderInsert.getHeight() / 2));
			folderInsert.setVisible(true);

			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			model.reload(home);
		} else {
			JOptionPane.showMessageDialog(null, "������ �������ּ���.");
		}
	}

	// ���� ����
	protected void deleteSelectedItems(String companyname, String id) {

		FolderDao dao = new FolderDao();
		CompanyDao ComDao = new CompanyDao();
		int UserCompNum = ComDao.selectCompanyNum(companyname);
		int compNum = dao.selectCompanyNumByItemNum(parentNum);
		int selectParent = dao.parentHomeNum(parentNum);

		FolderDao folderDao = new FolderDao();
		ItemDto selectItem = (ItemDto) selectNode.getUserObject();
		int selectItemNum = selectItem.getItemNum();
		int selectItemCom = selectItem.getCompanyNum();
		int selectItemParNum = selectItem.getParentNum();
		boolean checkfolder = folderDao.checkFolder(selectItemNum);
		if (parentNum == homeNum) {
			JOptionPane.showMessageDialog(null, "HOME������ ��� �� �� �����ϴ�.");
		} else {
			if (parentNum != 0) {
				if (selectParent != homeNum) {
					if (checkfolder == true) {
						if (UserCompNum == compNum || id.equals("admin")
								|| (selectItemCom == 0 && selectItemParNum != homeNum)) {
							if ((JOptionPane.showConfirmDialog(null, "���� �Ͻðڽ��ϱ�??", "����Ȯ��",
									JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
								selectNode = getSelectedNode();
								deleteFolder(parentNum);
								tree.updateUI();
							}
						} else {
							JOptionPane.showMessageDialog(null, "���� �� ������ �����Դϴ�.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "������ �ƴմϴ�.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "���� �� �� ���� �����Դϴ�.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "������ �������ּ���.");
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
	 * ������ �߰��Ѵ�.
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
		for (int i = 0; i < home.getChildCount(); i++) {
			if (home.getChildAt(i).toString().equals(name)) {
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
			ArrayList<Integer> grandChilds = (ArrayList<Integer>) dao.itemNumByParentNum(child);
			if (grandChilds.size() != 0) {
				for (int j = 0; j < grandChilds.size(); j++) {
					deleteCompanyFolder(child);
					if (((ArrayList<Integer>) dao.itemNumByParentNum(child)).size() == 0) {
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

	// ������ ����
	public void updateFolder(FolderDto newFolder) {
		getSelectedNode().setUserObject(newFolder);
		tree.updateUI();
	}

	public void updateComFolder(FolderDto newFolder, String comName) {
		DefaultMutableTreeNode node = null;
		for (int i = 0; i < home.getChildCount(); i++) {
			if (home.getChildAt(i).toString().equals(comName)) {
				node = (DefaultMutableTreeNode) home.getChildAt(i);
				selectNode = node;
				selectNode.setUserObject(newFolder);
			}
		}
		tree.updateUI();
	}

	// ���� ���
	public void addNewFile(FileDto newFile) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(newFile);
		selectNode.add(childNode);
		FileDto dto = (FileDto) childNode.getUserObject();
		tree.updateUI();

	}

	// â �ѹ��� �߰� �����
	public FolderInsert openFolderInsert(FolderInsert values) {

		folderInsert = values;

		return folderInsert;
	}

	public FolderUpdate openFolderUpdate(FolderUpdate values) {

		folderupdate = values;

		return folderupdate;
	}

	public AccessList openAccessList(AccessList values) {

		Al = values;

		return Al;
	}

	public UserList openUserList(UserList values) {

		ul = values;

		return ul;
	}

	public CompanyInsert openCompanyInsert(CompanyInsert values) {

		ci = values;

		return ci;
	}

	public CompanyList openCompanyList(CompanyList values) {

		CompList = values;

		return CompList;
	}

	public FileInsert openFileInsert(FileInsert values) {

		flieI = values;

		return flieI;
	}

	public Filedownload openFiledownload(Filedownload values) {

		FileDownload = values;

		return FileDownload;

	}

	@SuppressWarnings("serial")
	public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {

		Icon folderIcon = new ImageIcon("Folder.png");
		Icon closedIcon = new ImageIcon("Folder.png");
		Icon openIcon = new ImageIcon("Open Folder.png");
		Icon fileIcon = new ImageIcon("Files.png");
		Icon zipIcon = new ImageIcon("ZIP.png");
		Icon mp3Icon = new ImageIcon("MP3.png");
		Icon excelIcon = new ImageIcon("Excel.png");
		Icon powerPointIcon = new ImageIcon("PowerPoint.png");
		Icon textIcon = new ImageIcon("Text Box.png");
		private FileDto fileDto;
		private String fileType;

		public CustomTreeCellRenderer() {

		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			ItemDto node = (ItemDto) ((DefaultMutableTreeNode) value).getUserObject();

			FileDao fileDao = new FileDao();
			if (fileDao.checkfile(node.getItemNum()) == true) {
				fileDto = fileDao.selectFileByItemNum(node.getItemNum());
				fileType = fileDto.getFileType();
			}
			if (node instanceof FolderDto) {
				if (expanded) {
					setIcon(openIcon);
				} else {
					setIcon(closedIcon);
				}
			} else {
				if (fileType.equals("txt")) {
					setIcon(textIcon);
				} else if (fileType.equals("pptx")) {
					setIcon(powerPointIcon);
				} else if (fileType.equals("xlsx")) {
					setIcon(excelIcon);
				} else if (fileType.equals("zip")) {
					setIcon(zipIcon);
				} else if (fileType.equals("mp3")) {
					setIcon(mp3Icon);
				} else {
					setIcon(fileIcon);
				}
			}
			return this;
		}
	}
}
