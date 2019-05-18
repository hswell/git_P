package zx.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;
import sun.awt.shell.ShellFolder;

public class FileIconExtractor{

	
	/**
	 * 获取文件的小图标。
	 * @param suffixName 文件的后缀名。
	 * @return 返回该类型文件的小图标。
	 */
	public static Icon getFileSmallIcon(String suffixName){
		try {
			File file = File.createTempFile("icon", "." + suffixName);
			return getSmallIcon(file);
		} catch (IOException e) {
			System.out.println("创建文件时发生错误:"+e.getMessage());
			return null;
		}
	}
	/**
	 * 获取文件的大图标。
	 * @param suffixName 文件的后缀名。
	 * @return 返回该类型文件的大图标。
	 */
	public static Icon getFileBigIcon(String suffixName){
		try {
			File file = File.createTempFile("icon", "." + suffixName);
			return getBigIcon(file);
		} catch (IOException e) {
			System.out.println("创建文件时发生错误:"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 获取目录的小图标。
	 * @return 返回文件夹的小图标。
	 */
	public static Icon getDirectorySmallIcon(){
		File file = new File(System.getProperty("user.dir"));
		return getSmallIcon(file);
	}
	
	/**
	 * 获取目录的大图标。
	 * @return 返回文件夹的大图标。
	 */
	public static Icon getDirectoryBigIcon(){
		File file = new File(System.getProperty("user.dir"));
		return getBigIcon(file);
	}
	
	/**
	 * 获取文件file的小图标。
	 * @param file 文件或目录。
	 * @return 返回该文件或目录的大图标。
	 */
	public static Icon getSmallIcon(File file){
		try {
			FileSystemView view = FileSystemView.getFileSystemView();
			Icon smallIcon = view.getSystemIcon(file);
			file.delete();
			return smallIcon;
		} catch (RuntimeException ioe) {
			System.out.println("获取小图标时发生错误:"+ioe.getMessage());
			return null;
		}
	}
	
	/**
	 * 获取文件File的大图标。
	 * @param file 文件或目录。
	 * @return 返回该文件或目录的大图标。
	 */
	public static Icon getBigIcon(File file){
		try {
			ShellFolder shellFolder = ShellFolder.getShellFolder(file);
			Icon bigIcon = new ImageIcon(shellFolder.getIcon(true));
			file.delete();
			return bigIcon;
		} catch (RuntimeException ioe) {
			System.out.println("获取大图标时发生错误:"+ioe.getMessage());
			return null;
		} catch (FileNotFoundException e) {
			System.out.println("获取大图标时发生错误:"+e.getMessage());
			return null;
		}
	}
}
