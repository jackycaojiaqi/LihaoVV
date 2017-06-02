package sample.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UpdateStructOrder {
	public static void main(String[] args) {
		String path = "/Users/yj/Desktop/clientsdk/trunk/android/src/com/xlg/android/protocol/";
	
		File file=new File(path);//path为文件夹的路径
		File[] tempList = file.listFiles();//该方法返回的是文件数组
		  for (int i = 0; i < tempList.length; i++) {//循环这个素组
			  if (tempList[i].isFile()) {//根据需要取出文件
				  System.out.println("更新文件："+tempList[i].getPath());
				  UpdateJavaFile(tempList[i].getPath());
		   }
		  }
	}
	
	// 更新文件
	public static void UpdateJavaFile(String path) {
		String out = "";
		boolean start = false;
		boolean end = false;
		int order = 0;

        try {
			FileReader fr;
        	fr = new FileReader(path);
        	
            //可以换成工程目录下的其他文本文件
            BufferedReader br=new BufferedReader(fr);
        	
			while(true){
			    String s=br.readLine();
			    if(null == s) {
			    	break;
			    }
			    
			    if(false == end) {
		    		// 还没有开始
		    		String[] sp = s.split("[\\t|\\x20]");
		    		if(sp.length > 2) {
				    	if(false == start) {
				    		// 还没有开始
				    		if(sp[1].equals("class")) {
				    			start = true;
				    		}
				    	} else {
				    		// 已经开始了
				    		if(sp[1].equals("private")) {
				    			out += "\t@StructOrder(" + order + ")\r\n";
				    			order++;
				    		} else if(sp[1].equals("public")) {
				    			end = true;
				    		}
				    	}
		    		} else {
		    			end = true;
		    		}
			    }
			    
			    out += s + "\r\n";
			}
			
			br.close();
			
			System.out.println(out);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // return;
 		try {
	       FileWriter fileWritter;
			fileWritter = new FileWriter(path,false);
	       BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        bufferWritter.write(out);
	        bufferWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
}
