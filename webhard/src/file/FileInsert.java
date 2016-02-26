package file;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import main.MainPage;

import webhard.dao.FileDao;
import webhard.dto.FileDto;

import FTP.FTPUtil;

public class FileInsert extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton cancelBtn;
	private JFileChooser fc;
	private File fileName;
	private JLabel lblNewLabel_1;
	private File file;
	private String Name,Path,FileType,filesize;
	
	private String host = "192.168.1.6";
	private String id = "user1";
	private String password = "1234";
	private int port = 21;
	private String dir = "test/";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	/*	try {
			FileInsert dialog = new FileInsert(0,0,null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Create the dialog.
	 */
	public FileInsert(final int parentNum,final int companyNum,final String Userid,final MainPage mainpage) {
		setResizable(false);
		setTitle("\uD30C\uC77C \uB4F1\uB85D");
		setBounds(100, 100, 592, 324);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
					TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setPreferredSize(new Dimension(60, 50));
			{
				JLabel label = new JLabel("파일 등록");
				label.setFont(new Font("HY중고딕", Font.BOLD, 24));
				panel.add(label);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JLabel lblNewLabel = new JLabel("파일 등록");
				lblNewLabel.setFont(new Font("HY중고딕", Font.PLAIN, 15));
				lblNewLabel.setBounds(44, 44, 81, 15);
				panel.add(lblNewLabel);
			}
			{
				JButton searchBtn = new JButton("찾아보기");
				searchBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						fileOpen();
					}
				});
				searchBtn.setBounds(446, 75, 97, 23);
				panel.add(searchBtn);
			}
			{
				lblNewLabel_1 = new JLabel("");
				lblNewLabel_1.setFont(new Font("HY중고딕", Font.PLAIN, 12));
				lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
				lblNewLabel_1.setBackground(Color.WHITE);
				lblNewLabel_1.setBounds(44, 75, 359, 23);
				panel.add(lblNewLabel_1);
			}
			
			
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton InsertBtn = new JButton("파일 등록");
				//엑세스가 거부 됨
				InsertBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						
						FileDao dao = new FileDao();
						FileDto dto = new FileDto();
						
						dto = dao.addNewFile(Name, parentNum, id, companyNum, Path, filesize, FileType);
						
						File outpath = new File(Path);
						System.out.println(outpath);
						FTPUtil ftp = new FTPUtil();
						
						ftp.init(host, id, password, port);
						
						ftp.upload(dir, outpath);
						ftp.disconnection();
						//mainpage.addNewFile(dto);
						
						JOptionPane.showMessageDialog(null, "파일이 등록되었습니다.");
						setVisible(false);
					}
				});
				InsertBtn.setActionCommand("파일 등록");
				buttonPane.add(InsertBtn);
				getRootPane().setDefaultButton(InsertBtn);
			}
			{
				cancelBtn = new JButton("취소");
				cancelBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Object obj = e.getSource();
						if ((JOptionPane
								.showConfirmDialog(cancelBtn, "취소하시겠습니까??",
										"종료확인", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
							dispose();
						}
					}
				});
				cancelBtn.setActionCommand("취소");
				buttonPane.add(cancelBtn);
			}
		}
	}

	public void fileOpen() {
		String dir = "";
		if (fileName != null) {
			dir = fileName.getAbsolutePath(); // 현재 파일이 위치하는 디렉토리를 가져온다.
			
			fc = new JFileChooser(dir);
		} else {
			fc = new JFileChooser();
		}

		int yn = fc.showOpenDialog(this);
		if (yn != JFileChooser.FILES_ONLY)
		
		return;
		file = fc.getSelectedFile();
			
		Name = file.getName();
		
		int index = Name.lastIndexOf(".");
		
		FileType = Name.substring(index+1);
		Path = file.getPath();
		int size = (int) file.length();
		filesize = Integer.toString(size)+"byte";
		
		lblNewLabel_1.setText(Path);
		       		
	}
	
}



















