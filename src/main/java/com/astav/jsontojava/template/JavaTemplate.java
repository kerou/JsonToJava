package com.astav.jsontojava.template;

import com.astav.jsontojava.ClassFileData;
import com.astav.jsontojava.ClassFiles;
import com.astav.jsontojava.util.StringHelper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * User: Astav
 * Date: 10/21/12
 */
public class JavaTemplate {
    public static final String JSON_ANNOTATION = "@JsonProperty";

    public File writeOutJavaFile(String key, String outputDirectory, String packageName, ClassFiles classFiles) throws IOException, ClassNotFoundException {
        ClassFileData classFileData = classFiles.get(key);
        String className = StringHelper.capFirstLetter(key);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("package ").append(packageName).append(";\r\n\r\n");
        stringBuilder.append("import org.codehaus.jackson.annotate.JsonProperty;\r\n");
        for (String importPackageName : classFileData.getImportPackages()) {
            stringBuilder.append("import ").append(importPackageName).append(";\r\n");
        }
         stringBuilder.append("import java.util.*;\r\n\r\n");
        
        StringBuilder methodsStringBuilder = new StringBuilder();
        //begin class
        stringBuilder.append("public class ").append(className).append(" {\r\n");
        for (Map.Entry<String, String> variablesToTypeEntry : classFileData.getMapOfVariablesToTypes().entrySet()) {
            String v = variablesToTypeEntry.getKey();
            String t = variablesToTypeEntry.getValue();
            //appendJsonKey(stringBuilder, v).append(" private ").append(t).append(" ").append(v).append(";\r\n");
            stringBuilder.append("\tprivate ").append(t).append(" ").append(v).append(";\r\n");
            methodsStringBuilder.append(getMethodString(t,v));
        }
        
        stringBuilder.append("\r\n").append(methodsStringBuilder).append("\r\n");
        //end class
        stringBuilder.append("}\r\n");

//        String packageDirectory = packageName.replaceAll("\\.", File.separator);
        
        /**
         * modified by wanggang
         * 2015-10-30 21:26:39
         */
        String packageDirectory = packageName;
        if (!packageDirectory.endsWith(File.separator)) packageDirectory = packageDirectory + File.separator;
        String file = String.format("%s%s%s%s.java", outputDirectory, File.separator, packageDirectory, className);
        System.out.print(String.format("Writing file '%s' ...", file));
        File outputFile = new File(file);
        FileUtils.writeStringToFile(outputFile, stringBuilder.toString());
        System.out.print("done.");
        System.out.println();
        return outputFile;
    }

    private String getMethodString(String type,String virableName){
        StringBuilder stringBuilder = new StringBuilder();
        //get method
        stringBuilder.append("\tpublic ").append(type).append(" get").append(StringUtils.capitalize(virableName)).append("()").append("{\r\n");
        stringBuilder.append("\t\treturn ").append(virableName).append(";\r\n");
        stringBuilder.append("\t}");
        //set method
        stringBuilder.append("\tpublic void").append(" set").append(StringUtils.capitalize(virableName)).append("(").append(StringUtils.capitalize(type)).append(" ").append(virableName).append(")").append("{\r\n");
        stringBuilder.append("\t\tthis.").append(virableName).append(" = ").append(virableName).append(";\r\n");
        stringBuilder.append("\t}");
        
        return stringBuilder.toString();
    }

    private StringBuilder appendJsonKey(StringBuilder stringBuilder, String entryKey) {
        return stringBuilder.append(String.format("\t%s(\"%s\")", JSON_ANNOTATION, entryKey));
    }
}
