/**
  * @(#)data.FileData.java  2008-10-5  
  * Copy Right Information	: Tarena
  * Project					: FileShare
  * JDK version used		: jdk1.6.4
  * Comments				: 文件的信息。
  * Version					: 1.0
  * Sr	Date		Modified By		Why & What is modified
  * 1.	2008-10-5 	小猪     		新建
  **/
package zx.data;

import java.io.File;
import java.io.Serializable;
import java.util.StringTokenizer;

 /**
 * 文件的信息。
 * 2008-10-5
 * @author		达内科技[Tarena Training Group]
 * @version	1.0
 * @since		JDK1.6(建议) 
 */
@SuppressWarnings("serial")
public class FileData implements Serializable {

	/** file的路径 */
	private String path = "";
	/** file的全称 */
	private String fullName = "";
	/** file的文件名 */
	private String fileName = "";
	/** file的后缀名 */
	private String suffixName = "";
	/** 是否问文件或目录 */
	private boolean isFile = true;
	/** file的相对路径,相对于共享目录的路径 */
	private String relativePath = "";
	
	/**
	 * 以file构造该函数。
	 * @param file 文件。
	 */
	public FileData(File file) {
		path = file.getAbsolutePath();
		fullName = file.getName();
		isFile = file.isFile();
		if(isFile){
			int end = fullName.lastIndexOf(".");
			if(end==-1)
				fileName = fullName;
			else{
				fileName = fullName.substring(0, end);
				suffixName = fullName.substring(end,fullName.length());
			}
		}else
			fileName = fullName;
	}
	
	/**
	 * 以path为路径构造该函数，该函数旨在构造..上一级目录用的目录。
	 * @param path 路径，一般用/。
	 * @param fullName 路径名，一般..,表示上一级目录。
	 */
	public FileData(String path,String fullName) {
		this.path = path;
		this.fullName = fullName;
		fileName = fullName;
		isFile = false;
	}
	
	public FileData(File file,String SharePath,String delim) {
		this(file);
		String xpath = ContainByShareDirectory(path, SharePath, delim);
		relativePath = xpath==null?"":xpath;
	}
	
	public String getFileName() {
		return fileName;
	}
	public String getFullName() {
		return fullName;
	}
	public String getSuffixName() {
		return suffixName;
	}
	public boolean isFile() {
		return isFile;
	}
	public String getPath() {
		return path;
	}
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	
	@Override
	public String toString() {
		return fileName.equals("")?(fullName.equals("")?path:fullName):fileName;
	}
	
	/**
	 * 检测path的路径是否是SharePath的共享目录中其中一个的子目录
	 * @param path 检测的path
	 * @param SharePath 共享的目录
	 * @param delim 用delim分隔
	 * @return 包含返回共享目录的相对路径，否则返回null
	 */
	public static String ContainByShareDirectory(String path,String SharePath,String delim){
		StringTokenizer tokenizer = new StringTokenizer(SharePath,delim);
		while(tokenizer.hasMoreTokens()){
			String xpath = tokenizer.nextToken();
			File file = new File(xpath);
			int n = path.indexOf(xpath);
			if(path.equals(xpath))
				return File.separator+file.getName();
			if(n != -1){
				//System.out.println(xpath.length()+"<-->"+path.length()+"==>"+File.separator+file.getName()+path.substring(xpath.length(), path.length()));
				return File.separator+file.getName()+path.substring(xpath.length(), path.length());
			}
		}
		return null;
	}
	
}
