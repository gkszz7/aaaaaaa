import java.io.File;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;



public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // ������Ʈ ���� ������ ��ü�� �����Ѵ�.
        File file = new File("C:/aa");
		// Ȯ���� Ȯ��
      
		
        // file�� �����ϰ� ������ ���
        if(file.exists() && file.isDirectory()){
            
            // ������ ����/���� ����� ���ڿ� �迭�� ��ȯ
            String[] fList = file.list();
            // ���
            
            for(int i=0; i<fList.length; i++){
            	int index = fList[i].lastIndexOf(".");
            	String name = fList[i].substring(index);
            	System.out.println("���� �̸� : "+fList[i]);
            	System.out.println("���� Ȯ���� : "+name);
                System.out.println("���� ũ�� : " + fList[i].length());
                
            }
        }else{
            System.out.println("�ش� ��δ� ������ �ƴմϴ�.");
        }
	}

}
