/**
  * @(#)tools.DirectoryChoose.java  2008-10-5  
  * Copy Right Information	: Tarena
  * Project					: FileShare
  * JDK version used		: jdk1.6.4
  * Comments				: 目录选择对话框。
  * Version					: 1.0
  * Sr	Date		Modified By		Why & What is modified
  * 1.	2008-10-5 	小猪     		新建
  **/
package zx.tools;

import java.awt.Component;

import javax.swing.JFileChooser;

 /**
 * 目录选择对话框。
 * 2008-10-5
 * @author		达内科技[Tarena Training Group]
 * @version	1.0
 * @since		JDK1.6(建议) 
 */
@SuppressWarnings("serial")
public class DirectoryChoose extends JFileChooser {

	public DirectoryChoose(Component parent,String title) {
		super(System.getProperty("user.dir"));
		setFileSelectionMode(DIRECTORIES_ONLY );
		setDialogTitle(title);
		showOpenDialog(parent);
	}
}
