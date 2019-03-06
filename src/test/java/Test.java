import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryz.cn.entity.User;

import net.coobird.thumbnailator.Thumbnails;

public class Test {

	public static void  main(String[] a) throws IOException{
		File fromPic=new File("C://Users//ryzc//Pictures//02 - ¸±±¾.jpg");
		File toPic = new File("C://Users//ryzc//Pictures//9_1.jpg");
		System.out.println(fromPic.getName());
	}
}
