
import java.io.File;
import java.io.IOException;


public class RuntimeExample {
 public static void main(String[] args){
  try {
     File file = new File("C:/Users/CHUL/Desktop/¹ß±¸Áö/ppt.pptx");
    
     
//MS Windows Only
     Process p= Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + 
     file.getAbsolutePath());
     
// or
     
//Process p= Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + 
     
// file.getAbsolutePath());
    
     
//Apple Mac Only
     
//Process p= Runtime.getRuntime().exec("open " + file.getAbsolutePath());
    
     p.waitFor();
    
  } catch (InterruptedException ex) {
     ex.printStackTrace();
  } catch (IOException ex) {
     ex.printStackTrace();
  }
 }
}