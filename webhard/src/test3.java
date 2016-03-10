
public class test3 {
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String host = "192.168.1.6";
		String id = "user1";
		String password = "1234";
		int port = 21;
		
		FtpUtil ftp = new FtpUtil();
		
		ftp.getDataFiles(host, id, password, "test", null);
		
	}

}
