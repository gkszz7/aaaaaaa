package user;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import webhard.dto.CompanyDto;
import webhard.dao.CompanyDao;
import webhard.dao.UserDao;

public class UserInsert extends JDialog {
	private JTextField IdText;
	private JPasswordField PwField;
	private JTextField NameText;
	private JTextField Phone1;
	private JTextField Phone2;
	private JTextField Phone3;
	private JTextField AddrText;
	private JButton cancelBtn,insertBtn;
	private JPasswordField pwaccess;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UserInsert dialog = new UserInsert();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public UserInsert() {
		setTitle("\uD68C\uC6D0 \uAC00\uC785");
		setResizable(false);
		setBounds(100, 100, 435, 462);
		getContentPane().setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setPreferredSize(new Dimension(50, 40));
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_3, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setPreferredSize(new Dimension(50, 60));
		
		JLabel insert = new JLabel("회원가입");
		insert.setFont(new Font("굴림", Font.PLAIN, 40));
		panel_1.add(insert);

		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setPreferredSize(new Dimension(450, 50));
		panel_2.setLayout(null);
		
		JLabel id = new JLabel("아이디 :");
		id.setFont(new Font("굴림", Font.PLAIN, 15));
		id.setBounds(33, 36, 74, 15);
		panel_2.add(id);
		
		JLabel pw = new JLabel("비밀번호 :");
		pw.setFont(new Font("굴림", Font.PLAIN, 15));
		pw.setBounds(33, 87, 74, 15);
		panel_2.add(pw);
		
		JLabel name = new JLabel("이름 :");
		name.setFont(new Font("굴림", Font.PLAIN, 15));
		name.setBounds(33, 164, 74, 15);
		panel_2.add(name);
		
		JLabel Company = new JLabel("회사 :");
		Company.setFont(new Font("굴림", Font.PLAIN, 15));
		Company.setBounds(34, 245, 74, 21);
		panel_2.add(Company);
		
		IdText = new JTextField();
		IdText.setBounds(119, 33, 116, 21);
		panel_2.add(IdText);
		IdText.setColumns(10);
		
		PwField = new JPasswordField();
		PwField.setBounds(119, 84, 116, 21);
		panel_2.add(PwField);
		
		NameText = new JTextField();
		NameText.setBounds(119, 161, 116, 21);
		panel_2.add(NameText);
		NameText.setColumns(10);
		
		final Choice company = new Choice();
		company.setBounds(120, 245, 116, 21);
		CompanyDao cDao = new CompanyDao();
		List<CompanyDto> companys = new ArrayList<CompanyDto>();
		companys = cDao.selectCompany();
		if(companys != null && companys.size()>0){
			for(CompanyDto comp : companys){
				company.addItem(comp.getCompanyName());
			}
		}else{
			company.addItem("등록X");
		}
		
		panel_2.add(company);
		
		JLabel Phone = new JLabel("전화번호 :");
		Phone.setFont(new Font("굴림", Font.PLAIN, 15));
		Phone.setBounds(33, 203, 74, 20);
		panel_2.add(Phone);
		
		JButton checkId = new JButton("중복확인");
		checkId.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UserDao userdao = new UserDao();
				boolean check = userdao.checkUserId(IdText.getText());
				if(check ==true){
					JOptionPane.showMessageDialog(null,"존재하는 아이디입니다.");
					setVisible(true);
					IdText.setText("");
				}else{
					JOptionPane.showMessageDialog(null,"사용 가능 한 아이디입니다.");
					setVisible(true);
				}
			}
		});
		checkId.setBounds(247, 32, 97, 23);
		panel_2.add(checkId);
		
		Phone1 = new JTextField();
		Phone1.setBounds(119, 203, 51, 21);
		panel_2.add(Phone1);
		Phone1.setColumns(10);
		
		JLabel label = new JLabel("-");
		label.setBounds(178, 206, 13, 15);
		panel_2.add(label);
		
		Phone2 = new JTextField();
		Phone2.setBounds(191, 203, 51, 21);
		panel_2.add(Phone2);
		Phone2.setColumns(10);
		
		JLabel label_1 = new JLabel("-");
		label_1.setBounds(250, 206, 13, 15);
		panel_2.add(label_1);
		
		Phone3 = new JTextField();
		Phone3.setBounds(260, 203, 51, 21);
		panel_2.add(Phone3);
		Phone3.setColumns(10);
		
		JLabel check = new JLabel("");
		check.setBounds(119, 59, 57, 15);
		panel_2.add(check);
		
		JLabel addr = new JLabel("주소 :");
		addr.setFont(new Font("굴림", Font.PLAIN, 15));
		addr.setBounds(34, 287, 74, 15);
		panel_2.add(addr);
		
		AddrText = new JTextField();
		AddrText.setBounds(120, 284, 206, 21);
		panel_2.add(AddrText);
		AddrText.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\uBE44\uBC00\uBC88\uD638 \uD655\uC778 :");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setBounds(33, 126, 116, 15);
		panel_2.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("\uBE44\uBC00\uBC88\uD638 \uD655\uC778");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password  = PwField.getText();
				String pwdaccess = pwaccess.getText();
				
				if(password.equals(pwdaccess)&& password.length()>0 && pwdaccess.length()>0){
					JOptionPane.showMessageDialog(null,"비밀번호가 일치합니다.");
				}else if(password.length() == 0 || pwdaccess.length() == 0){
					JOptionPane.showMessageDialog(null,"비밀번호를 작성해주세요.");
				}else if(!password.equals(pwdaccess)){
					JOptionPane.showMessageDialog(null,"비밀번호가 틀립니다.");
					PwField.setText("");
					pwaccess.setText("");
				}
			}  
		});
		btnNewButton.setBounds(275, 122, 121, 23);
		panel_2.add(btnNewButton);
		
		pwaccess = new JPasswordField();
		pwaccess.setBounds(142, 123, 121, 21);
		panel_2.add(pwaccess);
		
		insertBtn = new JButton("확인");
		insertBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				UserDao userdao = new UserDao();
				CompanyDao compDao= new CompanyDao();
				String id=IdText.getText();
				String pw=PwField.getText();
				String pwdaccess = pwaccess.getText();
				String name=NameText.getText();
				String com=company.getSelectedItem();
				String addr=AddrText.getText();
				String phone=Phone1.getText()+Phone2.getText()+Phone3.getText();
				int compNum = compDao.selectCompanyNum(com);
				
				
				boolean check = userdao.checkUserId(id);
				
				if(check ==true){
					JOptionPane.showMessageDialog(null,"존재하는 아이디입니다.");
					setVisible(true);
					IdText.setText("");
				}else if(pw.length() == 0){
					JOptionPane.showMessageDialog(null,"비밀번호를 입력하지 않았습니다.");
					setVisible(true);
				}else if(name.length() == 0){
					JOptionPane.showMessageDialog(null,"이름을 입력하지 않았습니다..");
					setVisible(true);
				}else if(Phone1.getText().length() == 0 || Phone2.getText().length() == 0 || Phone3.getText().length() == 0){
					JOptionPane.showMessageDialog(null,"전화번호를 입력하지 않았습니다.");
					setVisible(true);
				}else if(addr.length() == 0){
					JOptionPane.showMessageDialog(null,"주소를 입력하지 않았습니다.");
					setVisible(true);
				}else if(!pw.equals(pwdaccess)){
					JOptionPane.showMessageDialog(null,"비밀번호가 틀립니다.");
					insertBtn.enable(false);
					PwField.setText("");
					pwaccess.setText("");
				}
				else{
					userdao.entryNewUser(id, pw, name, phone, addr, compNum);
					JOptionPane.showMessageDialog(null,"가입을 축하드립니다.");
					setVisible(false);
				}
			}
		});
		insertBtn.setFont(new Font("굴림", Font.PLAIN, 15));
		panel_3.add(insertBtn);
		
		cancelBtn = new JButton("취소");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
         		if((JOptionPane.showConfirmDialog(null, "가입을 취소하시겠습니까??","종료확인", JOptionPane.YES_NO_OPTION)) == JOptionPane.YES_OPTION)
         	   {
    				dispose();
         	   }
			}
		});
		cancelBtn.setFont(new Font("굴림", Font.PLAIN, 15));
		panel_3.add(cancelBtn);
	}
}
