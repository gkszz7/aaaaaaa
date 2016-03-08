package file;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import main.MainPage;

import webhard.dto.FileDto;
import webhard.dto.ItemDto;
import FTP.FTPUtil;

public class Filedownload extends JDialog {
	
	private JFileChooser fc;
	private File file;
	private String Name,Path;
	private JLabel lblNewLabel_2;
	private JButton cancelBtn;
	
	private String host = "192.168.1.6";
	private String fileid = "user1";
	private String password = "1234";
	private int port = 21;
	private String dir = "test/";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*try {
			Filedownload dialog = new Filedownload();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Create the dialog.
	 */
	public Filedownload(final DefaultMutableTreeNode selectNode,final MainPage mainpage) {
		setResizable(false);
		setBounds(100, 100, 601, 373);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			getContentPane().add(panel, BorderLayout.NORTH);
			panel.setPreferredSize(new Dimension(50,50));
			{
				JLabel lblNewLabel = new JLabel("\uD30C\uC77C \uB2E4\uC6B4\uB85C\uB4DC");
				lblNewLabel.setFont(new Font("굴림", Font.BOLD, 20));
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
				Filedownload filedownload = null;
				
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
			panel.setLayout(null);
			{
				JButton btnNewButton = new JButton("다운로드");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						ItemDto selectItem = (ItemDto) selectNode.getUserObject();
						
						if(selectItem instanceof FileDto)
						{
							FileDto fileInfo = (FileDto) selectItem;
					
							FTPUtil ftp = new FTPUtil();
							ftp.init(host, fileid, password, port);			
							
							int last1 = Path.lastIndexOf("\\");
							
							String fileName = Path.substring(last1+1,Path.length());
							
							int last =  fileInfo.getFileURL().lastIndexOf("\\");	
							
							String downloadName = fileInfo.getFileURL().substring(last+1,fileInfo.getFileURL().length());
							
							System.out.println(downloadName);
							
							ftp.download(dir, downloadName, Path+"."+fileInfo.getFileType());
							ftp.disconnection();
							JOptionPane.showMessageDialog(null, "파일이 다운되었습니다.");
							setVisible(false);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "잘못 된 경로입니다.");
						}
					}
				});
				btnNewButton.setBounds(374, 10, 97, 23);
				panel.add(btnNewButton);
			}
			{
				cancelBtn = new JButton("취소");
				cancelBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if ((JOptionPane
								.showConfirmDialog(cancelBtn, "취소하시겠습니까??",
										"종료확인", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION) {
							dispose();
						}
					}
				});
				cancelBtn.setBounds(483, 10, 97, 23);
				panel.add(cancelBtn);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JLabel lblNewLabel_1 = new JLabel("\uD30C\uC77C \uACBD\uB85C");
				lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 17));
				lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
				lblNewLabel_1.setBounds(46, 72, 99, 15);
				panel.add(lblNewLabel_1);
			}
			{
				lblNewLabel_2 = new JLabel(" ");
				lblNewLabel_2.setBounds(46, 113, 398, 15);
				panel.add(lblNewLabel_2);
			}
			{
				JButton btnNewButton_2 = new JButton("\uCC3E\uC544\uBCF4\uAE30");
				btnNewButton_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						fileSave();
					}
				});
				btnNewButton_2.setBounds(473, 109, 97, 23);
				panel.add(btnNewButton_2);
			}
		}
	}
	 public void fileSave()
	    {
	        JFileChooser fc = new JFileChooser();
	        int yn = fc.showSaveDialog(this);
	       
	        if(yn != JFileChooser.APPROVE_OPTION)
	        {
	             file = null;
	            
	        }
	       
	        file = fc.getSelectedFile();
	   	       
	        Path = file.getPath();
		
		lblNewLabel_2.setText(Path);       		
	}
	

}
