package file;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.jar.Attributes.Name;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridBagLayout;

public class Filedownload extends JDialog {
	private JFileChooser fc;
	private File fileName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Filedownload dialog = new Filedownload();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Filedownload() {
		setTitle("\uD30C\uC77C \uB2E4\uC6B4\uB85C\uB4DC");
		setResizable(false);
		setBounds(100, 100, 585, 371);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.SOUTH);
			panel.setPreferredSize(new Dimension(50,50));
			
			JButton button = new JButton("");
			panel.add(button);
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.NORTH);
			panel.setPreferredSize(new Dimension(50,50));
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.CENTER);
				panel_1.setLayout(new BorderLayout(0, 0));
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2, BorderLayout.WEST);
				}
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2, BorderLayout.NORTH);
				}
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2, BorderLayout.SOUTH);
				}
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2, BorderLayout.EAST);
				}
				{
					JLabel lblNewLabel = new JLabel("\uD30C\uC77C \uB2E4\uC6B4\uB85C\uB4DC");
					lblNewLabel.setFont(new Font("굴림", Font.BOLD, 20));
					lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
					panel_1.add(lblNewLabel, BorderLayout.CENTER);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JLabel lblNewLabel_1 = new JLabel("\uD30C\uC77C \uACBD\uB85C");
			lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 15));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setBounds(42, 62, 67, 23);
			panel.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel("");
			lblNewLabel_2.setBounds(42, 95, 368, 23);
			panel.add(lblNewLabel_2);
			
			JButton btnNewButton = new JButton("New button");
			btnNewButton.setBounds(439, 95, 97, 23);
			panel.add(btnNewButton);
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

		int yn = fc.showSaveDialog(this);
		if (yn != JFileChooser.DIRECTORIES_ONLY)
		
		return;
		       		
	}
}

