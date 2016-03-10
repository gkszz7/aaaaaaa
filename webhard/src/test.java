import java.io.File;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;



public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // 프로젝트 현재 폴더를 객체로 생성한다.
        File file = new File("C:/aa");
		// 확장자 확인
      
		
        // file이 존재하고 폴더일 경우
        if(file.exists() && file.isDirectory()){
            
            // 폴더의 파일/폴더 목록을 문자열 배열로 반환
            String[] fList = file.list();
            // 출력
            
            for(int i=0; i<fList.length; i++){
            	int index = fList[i].lastIndexOf(".");
            	String name = fList[i].substring(index);
            	System.out.println("파일 이름 : "+fList[i]);
            	System.out.println("파일 확장자 : "+name);
                System.out.println("파일 크기 : " + fList[i].length());
                
            }
        }else{
            System.out.println("해당 경로는 폴더가 아닙니다.");
        }
	}

}
