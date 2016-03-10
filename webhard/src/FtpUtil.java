import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpUtil {
    
    /** Creates a new instance of FtpUtil */
    public FtpUtil() {
    }
    
    /**
     * 서버의 파일을 다운로드 한다.
     * 
     * @param  server
     * @param  username
     * @param
     */
    public static void getDataFiles(String server, String username, String password, String folder, String localFolder) {
    
    // FTPClient object
    FTPClient ftp = null;
   
    try {
      // object 생성
      ftp = new FTPClient();
      
        // 서버에 접속
        ftp.connect(server);
        System.out.println("Connected to " + server);
        
        // 사용자 ID와 비밀번호로 로그온
        ftp.login(username, password);
        System.out.println(ftp.getReplyString());
        
        
        
        // 디렉토리로 이동
        ftp.changeWorkingDirectory(folder);
        
        // 디렉토리의 파일목록 조회
        FTPFile files[] = ftp.listFiles();
        System.out.println(" Number of files in dir:" + files.length);       
        for(int i=0;i<files.length;i++){
        		if(files[i].getName().equals("ppt.pptx")){     
        		try {										
        			
        			File file = new File("ftp://192.168.1.6/test/testasd.pptx");						   
				            		
				    Process p= Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());								     
				     
				    p.waitFor();
				    
				  } catch (InterruptedException ex) {
				     ex.printStackTrace();
				  } catch (IOException ex) {
				     ex.printStackTrace();
				  }     
        		}
        }
      
      // FTP 서버 로그아웃 & disconnect
      ftp.disconnect();
      
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}