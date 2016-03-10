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
     * ������ ������ �ٿ�ε� �Ѵ�.
     * 
     * @param  server
     * @param  username
     * @param
     */
    public static void getDataFiles(String server, String username, String password, String folder, String localFolder) {
    
    // FTPClient object
    FTPClient ftp = null;
   
    try {
      // object ����
      ftp = new FTPClient();
      
        // ������ ����
        ftp.connect(server);
        System.out.println("Connected to " + server);
        
        // ����� ID�� ��й�ȣ�� �α׿�
        ftp.login(username, password);
        System.out.println(ftp.getReplyString());
        
        
        
        // ���丮�� �̵�
        ftp.changeWorkingDirectory(folder);
        
        // ���丮�� ���ϸ�� ��ȸ
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
      
      // FTP ���� �α׾ƿ� & disconnect
      ftp.disconnect();
      
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}