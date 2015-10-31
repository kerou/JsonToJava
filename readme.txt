for this brach,use like this:


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.codehaus.jackson.map.ObjectMapper;

import com.astav.jsontojava.Generator;

public class TestFunctions {
	
	public static void main(String[] args) throws Exception{
		InputStream  is=TestFunctions.class.getResourceAsStream("jsonfile");
		String jsonContent=getString(is);
		System.out.println(jsonContent);
		
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked") 
		Map<String, Object> map = mapper.readValue(jsonContent, Map.class);
//		String regexFilename="AAA";
		
		String outputDirectory="D:\\test\\ttest\\";
		String packageName="com.fkls.test";
		String regexFilename="";
		String importDirectory="";
		List<String> importPackages=new ArrayList<String>(); 
		boolean promptForComplexValueTypes=true;
		Generator generator = new Generator(outputDirectory, packageName, regexFilename, importDirectory, importPackages, promptForComplexValueTypes);
		String key="ReturnResult";
		generator.generateClasses(key, map);
		
	}
	
	public static String getString(InputStream  is) throws IOException{
		int i = -1;
		//org.apache.commons.io.output.ByteArrayOutputStream
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((i = is.read()) != -1) {
		    baos.write(i);
		}
		String content = baos.toString();
		return content;
	}

}
