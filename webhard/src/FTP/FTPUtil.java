package FTP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;

public class FTPUtil {

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
	 * 하나의 파일을 다운로드 한다.
	 * 
	 * @param ftpServer
	 *            다운받을 서버
	 * @param user
	 * 			   유저 아이디
	 * @param password
	 * 			   유저 패스워드
	 * @param fileName
	 *  		   다운로드받을 파일
	 * @param source
	 *           경로
	 * 
	 */
	public void upload( String ftpServer, String user, String password,String fileName, File source) throws MalformedURLException,IOException{

	      if (ftpServer != null && fileName != null && source != null)

	      {
	         StringBuffer sb = new StringBuffer("ftp://");

	         // check for authentication else assume its anonymous access.

	         if (user != null && password != null){

	            sb.append( user );
	            sb.append( ':' );
	            sb.append( password );
	            sb.append( '@' );
	         }

	         sb.append(ftpServer);
	         sb.append("/test");
	         sb.append(fileName);

	         /*
	          * type ==&gt; a=ASCII mode, i=image (binary) mode, d= file directory
	          * listing
	          */
	         sb.append( ";type=i" );

	         BufferedInputStream bis = null;
	         BufferedOutputStream bos = null;

	         try
	         {

	            URL url = new URL(sb.toString());
	            URLConnection urlc = url.openConnection();

	            bos = new BufferedOutputStream(urlc.getOutputStream());
	            bis = new BufferedInputStream(new FileInputStream( source ));

	            int i;

	            // read byte by byte until end of stream

	            while ((i = bis.read()) != -1){
	               bos.write( i );
	            }
	         }
	         finally{
	            if (bis != null)
	               try{
	                  bis.close();
	               }catch (IOException ioe){
	                  ioe.printStackTrace();
	               }

	            if (bos != null)
	               try{
	                  bos.close();
	               }catch (IOException ioe){
	                  ioe.printStackTrace();
	               }
	         }
	      }else{
	         System.out.println( "Input not available." );
	      }

	   }

	/**
	 * 하나의 파일을 다운로드 한다.
	 * 
	 * @param ftpServer
	 *            다운받을 경로
	 * @param user
	 * 			   유저 아이디
	 * @param password
	 * 			   유저 패스워드
	 * @param fileName
	 *  		   다운로드받을 파일
	 * @param destination
	 *           저장 할 곳
	 * 
	 */

	public void download(String ftpServer, String user, String password,String fileName, File destination)throws MalformedURLException,IOException{

	      if (ftpServer != null && fileName != null && destination != null)
	      {
	         StringBuffer sb = new StringBuffer("ftp://");

	         // check for authentication else assume its anonymous access.
	         if (user != null && password != null)
	         {

	            sb.append(user);
	            sb.append(':');
	            sb.append(password);
	            sb.append('@');
	         }

	         sb.append(ftpServer);
	         sb.append("/test");
	         sb.append(fileName);
	         /*

	          * type ==&gt; a=ASCII mode, i=image (binary) mode, d= file directory

	          * listing

	          */
	         sb.append(";type=i");
	         BufferedInputStream bis = null;
	         BufferedOutputStream bos = null;
	         try{

	            URL url = new URL(sb.toString());
	            URLConnection urlc = url.openConnection();
	         
	            bis = new BufferedInputStream(urlc.getInputStream());	        
	            bos = new BufferedOutputStream(new FileOutputStream(destination));
	            	         
	            int i;
	            
	            while ((i = bis.read()) != -1){
	               bos.write(i);
	            }
	         }

	         finally{
	            if (bis != null)
	               try{
	                  bis.close();
	               }catch (IOException ioe){
	                  ioe.printStackTrace();
	               }
	            if (bos != null)
	               try{
	                  bos.close();
	               }catch (IOException ioe){
	                  ioe.printStackTrace();
	               }
	        }
	      }
	      else{
	         System.out.println( "Input not available" );
	      }
	   }
	public void openFile( String ftpServer, String user, String password,String fileName) throws MalformedURLException,IOException{

	      if (ftpServer != null && fileName != null)

	      {
	         StringBuffer sb = new StringBuffer("ftp://");

	         // check for authentication else assume its anonymous access.

	         if (user != null && password != null){

	            sb.append( user );
	            sb.append( ':' );
	            sb.append( password );
	            sb.append( '@' );
	         }

	         sb.append(ftpServer);
	         sb.append("/test");
	         sb.append(fileName);

	         /*
	          * type ==&gt; a=ASCII mode, i=image (binary) mode, d= file directory
	          * listing
	          */
	         sb.append( ";type=i" );
	         try{
	        	 URI uri = new URI(sb.toString());
	        	 System.out.println(uri);
//	        	 URL url = new URL(sb.toString());
//		         URLConnection urlc = url.openConnection();
		         File file =  new File(uri);
	        	 Process p= Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());
				    
			     p.waitFor();
	         }catch (InterruptedException ex) {
			     ex.printStackTrace();
	         }catch(Exception e){
	        	 
	         }
	         
	      }

	   }
	

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

	/*
	 * public static void main(String[] args) {
	 * 
	 * FTPUtil ftp = new FTPUtil();
	 * 
	 * String host = "192.168.1.6"; String id = "user1"; String password =
	 * "1234"; int port = 21; String dir = "test/";// ex. "images/"
	 * ftp.init(host, id, password, port);
	 * 
	 * ftp.upload(dir, new
	 * File("C:/Users/Public/Pictures/Sample Pictures/files.png"));//
	 * ex."f:\\test.txt" ftp.download(dir, "set1.png", "set2.png"); //
	 * ex.ftp.download(dir, // "test.txt", // "f:\\testgood.txt")
	 * ftp.disconnection();
	 * 
	 * }
	 */
}