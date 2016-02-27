package folder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import main.MainPage;

import webhard.dao.FolderDao;
import webhard.dto.FolderDto;

public class FolderUpdate extends JDialog {
	private JButton cancelButton;
	private JTextField folderName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*try {
			int itemNum=0;
			FolderUpdate dialog = new FolderUpdate(itemNum);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Create the dialog.
	 */
	public FolderUpdate(final int itemNum,final MainPage mainPage, final int companyNum, final String userId) {
		setResizable(false);
		setTitle("폴더 등록");
		setBounds(100, 100, 332, 232);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("수정");
				
				//존재 여유 판단
				okButton.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						FolderDto dto = new FolderDto();
						FolderDao dao = new FolderDao();
						if(folderName.getText().length()>0){
							String name = folderName.getText();
							dto = dao.updateFolder(name, itemNum,companyNum, userId);
							
							
							JOptionPane.showMessageDialog(null,"폴더가 수정되었습니다.");
							setVisible(false);//make directory
							
							mainPage.updateFolder(dto);
							
						}
						else{
							JOptionPane.showMessageDialog(null,"폴더 명을 적어주세요.");
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("취소");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Object obj = e.getSource();
		         		if((JOptionPane.showConfirmDialog(cancelButton, "취소하시겠습니까??","종료확인", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION)
		         	   {
		    				dispose();
		         	   }
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.NORTH);
				panel_1.setPreferredSize(new Dimension(50, 80));
				panel_1.setLayout(new BorderLayout(0, 0));
				{
					JPanel panel_2 = new JPanel();
					panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel_2.setBackground(Color.WHITE);
					panel_1.add(panel_2, BorderLayout.CENTER);
					panel_2.setLayout(new BorderLayout(0, 0));
					
					JPanel panel_3 = new JPanel();
					panel_3.setBackground(Color.WHITE);
					panel_2.add(panel_3, BorderLayout.WEST);
					panel_3.setPreferredSize(new Dimension(70, 80));
					{
						JLabel label = new JLabel("");
						label.setIcon(new ImageIcon("Folder-48.png"));
						panel_3.add(label);
					}
					
					JPanel panel_4 = new JPanel();
					panel_4.setBackground(Color.WHITE);
					panel_2.add(panel_4, BorderLayout.EAST);
					panel_4.setPreferredSize(new Dimension(220, 80));
					panel_4.setLayout(null);
					{
						JLabel lblNewLabel_1 = new JLabel("\uD3F4\uB354 \uBA85\uC744");
						lblNewLabel_1.setFont(new Font("HY중고딕", Font.PLAIN, 20));
						lblNewLabel_1.setBounds(12, 10, 104, 24);
						panel_4.add(lblNewLabel_1);
					}
					{
						JLabel lblNewLabel_2 = new JLabel("\uC801\uC5B4\uC8FC\uC138\uC694.");
						lblNewLabel_2.setFont(new Font("HY중고딕", Font.PLAIN, 20));
						lblNewLabel_2.setBounds(85, 34, 123, 31);
						panel_4.add(lblNewLabel_2);
					}
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.CENTER);
				panel_1.setLayout(null);
				{
					JLabel lblNewLabel = new JLabel("\uD3F4\uB354 \uBA85 :");
					lblNewLabel.setFont(new Font("HY중고딕", Font.PLAIN, 15));
					lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
					lblNewLabel.setBounds(12, 33, 73, 18);
					panel_1.add(lblNewLabel);
				}
				{
					folderName = new JTextField();
					folderName.setBounds(104, 33, 166, 21);
					panel_1.add(folderName);
					folderName.setColumns(10);
				}
			}
		}
	}
}
