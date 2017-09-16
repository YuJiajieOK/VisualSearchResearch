package ObjectComparison;

import java.io.File;

public class LoadFiles {
	
	public static File getFile(String path, File[] array, int i){ 
		File files = null; 
            if(array[i].isFile()){   
//            	files = array[i].getPath(); 
            	files = array[i];
            }          
		return files;   
    }   

}
