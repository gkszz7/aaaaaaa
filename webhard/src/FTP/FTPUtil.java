package FTP;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;

public class FTPUtil{

	private FTPClient client = null;

	/**
	 * 서버와 연결에 필요한 값들을 가져와 초기화 시킴
	 * 
	 * @param host
	 *            서버 주소
	 * @param userName
	 *            접속에 사용될 아이디
	 * @param password
	 *            비밀번호
	 * @param port
	 *            포트번호
	 */
	public void init(String host, String userName, String password, int port) {
		client = new FTPClient();
		client.setControlEncoding("UTF-8"); // 한글 encoding....

		FTPClientConfig config = new FTPClientConfig();
		client.configure(config);
		try {
			client.connect(host, port);
			client.login(userName, password);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 하나의 파일을 업로드 한다.
	 * 
	 * @param dir
	 *            저장시킬 주소(서버)
	 * @param file
	 *            저장할 파일
	 */
	public void upload(String dir, File file) {

		InputStream in = null;
	
		try {
			in = new FileInputStream(file);
			
			client.storeFile(dir + file.getName(), in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 하나의 파일을 다운로드 한다.
	 * 
	 * @param dir
	 *            저장할 경로(서버)
	 * @param downloadFileName
	 *            다운로드할 파일
	 * @param path
	 *            저장될 공간
	 */
	public void download(String dir, String downloadFileName, String path) {

		FileOutputStream out = null;
		InputStream in = null;
		client.setControlEncoding("UTF-8");
		dir += downloadFileName;
		try {
			in = client.retrieveFileStream(dir);
			out = new FileOutputStream(new File(path));
			int i;
			while ((i = in.read()) != -1) {
				out.write(i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	};

	/**
	 * 서버와의 연결을 끊는다.
	 */
	public void disconnection() {
		try {
			client.logout();
			if (client.isConnected()) {
				client.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*public static void main(String[] args) {

		FTPUtil ftp = new FTPUtil();
		
		String host = "192.168.1.6";
		String id = "user1";
		String password = "1234";
		int port = 21;
		String dir = "test/";// ex. "images/"
		ftp.init(host, id, password, port);

		ftp.upload(dir, new File("C:/Users/Public/Pictures/Sample Pictures/files.png"));// ex."f:\\test.txt"
		ftp.download(dir, "set1.png", "set2.png"); // ex.ftp.download(dir,
													// "test.txt",
													// "f:\\testgood.txt")
		ftp.disconnection();

	}*/
}