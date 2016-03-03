package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultTreeCellRenderer;

import main.MainPage;
import user.UserInsert;
import webhard.dao.UserDao;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class login extends JDialog {

	/**
	 * Launch the application.
	 */
	
	private ImageIcon im;
	private JTextField idtext;
	private JPasswordField pwField;
	private JButton insertBtn, loginBtn;

	public static void main(String[] args) {
		try {
			login dialog = new login();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			dialog.setLocation((dim.width / 2) - (dialog.getWidth() / 2),
					(dim.height / 2) - (dialog.getHeight() / 2));
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public login() {
		setTitle("\uB85C\uADF8\uC778");
		setResizable(false);
		setBounds(100, 100, 470, 331);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setForeground(Color.LIGHT_GRAY);
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		getContentPane().add(panel, BorderLayout.NORTH);

		panel.setPreferredSize(new Dimension(100, 100));
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel.add(panel_1, BorderLayout.EAST);
		panel_1.setPreferredSize(new Dimension(200, 200));

		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		Icon img = new ImageIcon("img1.png");
		Image icon = ((ImageIcon) img).getImage(); // ImageIcon을 Image로 변환.
		Image icon1 = icon.getScaledInstance(170, 90,
				java.awt.Image.SCALE_SMOOTH);
		ImageIcon icon2 = new ImageIcon(icon1); // Image로 ImageIcon 생성

		JLabel lblNewLabel_1 = new JLabel(icon2);
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 62));
		panel_1.add(lblNewLabel_1);

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		panel.add(panel_6, BorderLayout.WEST);
		panel_6.setPreferredSize(new Dimension(240, 300));
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_6.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_6.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_6.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel_6.setLayout(gbl_panel_6);

		JLabel label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(20, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel_6.add(label, gbc_label);

		JLabel lblNewLabel = new JLabel("비회원 일 경우 회원가입을");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 18));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.anchor = GridBagConstraints.SOUTH;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel_6.add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("해주시기 바랍니다.");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 18));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel_6.add(lblNewLabel_2, gbc_lblNewLabel_2);

		JPanel panel_2 = new JPanel();
		panel_2.setForeground(Color.WHITE);
		getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_2.add(panel_3, BorderLayout.SOUTH);
		panel_3.setPreferredSize(new Dimension(30, 45));
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.EAST);
		panel_5.setPreferredSize(new Dimension(195, 10));

		loginBtn = new JButton("로그인");
		
		loginBtn.setEnabled(true);

		loginBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String id = null;
				String passwd = null;
				if (idtext.getText().length() > 0
						&& pwField.getText().length() > 0) {
					id = idtext.getText();
					passwd = pwField.getText();
					UserDao dao = new UserDao();
					int check = dao.loginUser(id, passwd);
					if (check == 1) {
						String name = dao.getUserName(id);
						int admin = dao.getUserAdmin(id);
						int access = dao.getUserAccess(id);
						int companynum = dao.selectcompany(id);
						String companyName = dao.selectcompanyname(companynum);
						String id2 = id;
						JOptionPane.showMessageDialog(null, companyName+"회사로 로그인 되었습니다.");
						dispose();
						MainPage ma = new MainPage(name,companyName,id2, admin, access);
						
						Dimension dim = Toolkit.getDefaultToolkit()
								.getScreenSize();
						ma.setLocation((dim.width / 2) - (ma.getWidth() / 2),
								(dim.height / 2) - (ma.getHeight() / 2));
						ma.setVisible(true);
						
					} else if (check == 0) {
						JOptionPane.showMessageDialog(null, "비밀번호가 틀립니다.");
					} else {
						JOptionPane.showMessageDialog(null, "아이디가 틀립니다.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "아이디 비밀번호를 입력해주세요.");
				}
			}
		});
		loginBtn.setVerticalAlignment(SwingConstants.BOTTOM);
		loginBtn.setFont(new Font("굴림", Font.PLAIN, 16));
		panel_5.add(loginBtn);

		insertBtn = new JButton("회원가입");
		insertBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserInsert insert = new UserInsert();
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				insert.setLocation((dim.width / 2) - (insert.getWidth() / 2),
						(dim.height / 2) - (insert.getHeight() / 2));
				insert.setVisible(true);

			}
		});

		insertBtn.setVerticalAlignment(SwingConstants.BOTTOM);
		insertBtn.setFont(new Font("굴림", Font.PLAIN, 16));
		panel_5.add(insertBtn);
		JPanel panel_4 = new JPanel();
		panel_4.setForeground(Color.WHITE);
		panel_2.add(panel_4, BorderLayout.WEST);
		panel_4.setPreferredSize(new Dimension(300, 80));
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0,
				0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		JLabel lblNewLabel_3 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(30, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 0;
		panel_4.add(lblNewLabel_3, gbc_lblNewLabel_3);

		JLabel ID = new JLabel("아이디 :");
		ID.setFont(new Font("굴림", Font.PLAIN, 15));
		ID.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_ID = new GridBagConstraints();
		gbc_ID.anchor = GridBagConstraints.SOUTH;
		gbc_ID.gridwidth = 4;
		gbc_ID.insets = new Insets(0, 0, 5, 5);
		gbc_ID.gridx = 0;
		gbc_ID.gridy = 1;
		panel_4.add(ID, gbc_ID);

		idtext = new JTextField();
		GridBagConstraints gbc_idtext = new GridBagConstraints();
		gbc_idtext.anchor = GridBagConstraints.SOUTH;
		gbc_idtext.gridwidth = 3;
		gbc_idtext.insets = new Insets(0, 0, 5, 5);
		gbc_idtext.fill = GridBagConstraints.HORIZONTAL;
		gbc_idtext.gridx = 4;
		gbc_idtext.gridy = 1;
		panel_4.add(idtext, gbc_idtext);
		idtext.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(10, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 2;
		gbc_lblNewLabel_4.gridy = 2;
		panel_4.add(lblNewLabel_4, gbc_lblNewLabel_4);

		JLabel password = new JLabel("비밀번호 :");
		password.setFont(new Font("굴림", Font.PLAIN, 15));
		GridBagConstraints gbc_password = new GridBagConstraints();
		gbc_password.anchor = GridBagConstraints.NORTH;
		gbc_password.gridwidth = 4;
		gbc_password.insets = new Insets(0, 0, 0, 5);
		gbc_password.gridx = 0;
		gbc_password.gridy = 3;
		panel_4.add(password, gbc_password);

		pwField = new JPasswordField();
		pwField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					loginBtn.doClick();
				}
			}
		});
		GridBagConstraints gbc_pwField = new GridBagConstraints();
		gbc_pwField.anchor = GridBagConstraints.SOUTH;
		gbc_pwField.gridwidth = 3;
		gbc_pwField.insets = new Insets(0, 0, 0, 5);
		gbc_pwField.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwField.gridx = 4;
		gbc_pwField.gridy = 3;
		panel_4.add(pwField, gbc_pwField);

	}

}
