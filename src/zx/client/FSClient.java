/**
  * @(#)client.FSClient.java  2008-10-5  
  * Copy Right Information	: Tarena
  * Project					: FileShare
  * JDK version used		: jdk1.6.4
  * Comments				: 文件共享服务器客户端界面。
  * Version					: 1.0
  * Sr	Date		Modified By		Why & What is modified
  * 1.	2008-10-5 	小猪     		新建
  **/
package zx.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import zwatch.kerberos.Client.Client;
import zx.data.FSMessage;
import zx.data.FileData;

import zx.tools.DateDeal;
import zx.tools.DirectoryChoose;
import zx.tools.DownSpeed;
import zx.tools.FileIconExtractor;


 /**
 * 文件共享服务器客户端界面。
 * 2008-10-5
 * @author		达内科技[Tarena Training Group]
 * @version	1.0
 * @since		JDK1.6(建议) 
 */
@SuppressWarnings("serial")
public class FSClient extends JFrame implements ActionListener {

	/** 菜单栏 */
	private JMenuBar menuBar=new JMenuBar();
	/** 操作服务端菜单 */
	private JMenu menuOperation = new JMenu("操作(O)");
	/** 启动服务端按钮 */
	private JMenuItem itemLink = new JMenuItem("连接(L)",KeyEvent.VK_L);
	/** 停止服务端按钮 */
	private JMenuItem itemDisconnect = new JMenuItem("断开(D)",KeyEvent.VK_D);
	/** 设置服务端按钮 */
	private JMenuItem itemSet = new JMenuItem("设置(S)",KeyEvent.VK_S);
	/** 帮助服务端菜单 */
	private JMenu menuHelp = new JMenu("帮助(H)");
	/** 关于服务端按钮 */
	private JMenuItem itemAbout = new JMenuItem("关于(A)",KeyEvent.VK_A);
	/** 显示在线用户列表 */
	private JList list = null;
	/** list的DefaultListModel模型 */
	private DefaultListModel model = new DefaultListModel();
	/** 显示日志 */
	private JTextArea areaLog = new JTextArea();
	/** 显示进度条的Pane */
	private JPanel lblProcess = new JPanel();
	/** 下载速度 */
	private JLabel lblSpeed = new JLabel();
	/** 进度条 */
	private JProgressBar progressBar = new JProgressBar(0,100);
	/** 服务器端口 */
	private static Integer PORT = 3608;
	private static String User = "";
	private static String Password = null;

	/** 服务器ip */
	private static String SERVERIP = "127.0.0.1";
	/** 保存的目录 */
	private static String SAVEDIRECTORY = System.getProperty("user.dir");
	/** 设置对话框 */
	private SetDialog dialog = new SetDialog(false);
	/** 客户端对象 */
	private Socket client = null;
	/** 对象输出流 */
	private ObjectOutputStream oos = null;
	/** 对象输入流 */
	private ObjectInputStream ois = null;
	/** 文件下载流 */
	private DataOutputStream dos = null;
	/** 连接线程 */
	private Thread thread = null;
	/** 程序运行否 */
	private boolean isRun = false;
	/** 是否开始传输文件 */
	private boolean isBeginSend = false;
	/** list的右键菜单 */
	private JPopupMenu popupMenu = null;
	/** 剔除该用户 */
	private JMenuItem itemDown = new JMenuItem("下载");
	/** 文件开始下载时间 */
	private long fileBeginTime = 0L;
	/** 文件结束下载时间 */
	private long fileEndTime = 0L;
	/** 已下载文件数据(单位:字节) */
	private long totalDownData = 0L;
	
	static String ticket_v;
	
	public FSClient() {
		setTitle("文件共享服务器客户端");
		setSize(640, 560);
		setMinimumSize(new Dimension(320,280));
		//setResizable(false);
		Toolkit tk=Toolkit.getDefaultToolkit();
		setLocation((tk.getScreenSize().width-getSize().width)/2,(tk.getScreenSize().height-getSize().height)/2);
		
		initMenu();
		init();
		addWindowListener(new MyWindowAdapter());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * 初始化菜单。
	 */
	private void initMenu(){
		menuOperation.setMnemonic(KeyEvent.VK_O);
		menuOperation.add(itemLink);
		menuOperation.add(itemDisconnect);
		menuOperation.addSeparator();
		menuOperation.add(itemSet);
		menuHelp.add(itemAbout);
		menuHelp.setMnemonic(KeyEvent.VK_H);
		itemAbout.addActionListener(this);
		itemLink.addActionListener(this);
		itemDisconnect.addActionListener(this);
		itemSet.addActionListener(this);
		
		menuBar.add(menuOperation);
		//menuBar.add(menuHelp);
		setJMenuBar(menuBar);
		itemChange(!isRun);
		popupMenu = new JPopupMenu();
		popupMenu.add(itemDown);
		itemDown.addActionListener(this);
	}
	
	/**
	 * 初始化界面。
	 */
	private void init(){
		list = new JList(model);
		list.setCellRenderer(new Renderer());
		list.setFixedCellHeight(60);
		list.setFixedCellWidth(60);
		list.setVisibleRowCount(0);
		list.setDragEnabled(true);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.addMouseListener(new ListMouseAdapter());
		JScrollPane sp = new JScrollPane(list);
		sp.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY),"共享目录"));
		add(sp);
		areaLog.setEditable(false);
		areaLog.setLineWrap(true);
		
		JScrollPane sp2 = new JScrollPane(areaLog);
		sp2.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY),"日志"));
		JPanel paneInfo = new JPanel();
		paneInfo.setPreferredSize(new Dimension(625,30));
		paneInfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
		lblProcess.setPreferredSize(new Dimension(300,20));
		lblProcess.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		lblProcess.setLayout(new BorderLayout());
		progressBar.setBorderPainted(false);
		lblProcess.add(progressBar);
		lblSpeed.setPreferredSize(new Dimension(60,20));
		lblSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpeed.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		paneInfo.add(lblProcess);
		paneInfo.add(lblSpeed);
		JPanel pane = new JPanel();
		pane.setPreferredSize(new Dimension(625,150));
		pane.setLayout(new BorderLayout());
		pane.add(sp2);
		pane.add(paneInfo,BorderLayout.SOUTH);
		add(pane,BorderLayout.SOUTH);
	}
	
	/**
	 * 记录日志功能。
	 * @param msg 日志描述。
	 */
	private void writeLog(String msg){
		areaLog.append(DateDeal.getCurrentTime()+","+msg+"\n");
		//滚动条自动下滚
		areaLog.setCaretPosition(areaLog.getDocument().getLength());
	}
	
	/**
	 * ActionListener事件。
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==itemSet){
			dialog.setVisible(true);
			return;
		}
		if(e.getSource()==itemLink){
			AuthByKerberos();
			linkServer();
			return;
		}
		if(e.getSource()==itemDisconnect){
			disconectServer();
			return;
		}
		if(e.getSource()==itemDown){
			downData();
			return;
		}
		if(e.getSource()==itemAbout)
		{}
		//new About(this,"关于本文件共享服务器",true);
		
	}

 	private void AuthByKerberos() {
		Client client=new Client(User, Password);
		client.run();
	}

	 /**
	 * 禁用/启用菜单选择方法。
	 * @param b b为true时，启动和设置菜单可用，停止菜单不可用；否则，反之。
	 */
	private void itemChange(boolean b){
		itemLink.setEnabled(b);
		itemDisconnect.setEnabled(!b);
		itemSet.setEnabled(b);
	}
	
	private void setSpeed(long fileLength){
		//System.out.println("下载完成:"+totalDownData*100/fileLength+"%");
		progressBar.setStringPainted(true);
		progressBar.setValue((int)(totalDownData*100/fileLength));
		progressBar.setString(totalDownData*100/fileLength+"%");
		//String speed = DownSpeed.getSpeed(totalDownData, fileBeginTime, fileEndTime);
		//if((fileEndTime-fileBeginTime)/1000>0)
			lblSpeed.setText(DownSpeed.getSpeed(totalDownData,fileBeginTime,fileEndTime));
		//System.out.println("下载速度:"+speed);
	}
	
	/**
	 * 获取服务器信息。
	 * @return 服务器信息。
	 */
	private String getServerInfo(){
		return "["+SERVERIP+":"+PORT+"]";
	}
	/**
	 * 获取客户端IP信息
	 * @return 客户端IP信息。
	 */
	private String getClientInfo(){
		if(client!=null){
			InetAddress address = client.getInetAddress();
			return "["+address.getHostName()+"("+address.getHostAddress()+":"+client.getLocalPort()+")]";
		}else
			return "[IP信息未知]";
	}
	
	/**
	 * 连接服务器。
	 */
	private void linkServer(){
		if(!isRun)
			itemChange(isRun);
		isRun = true;
		thread = new LinkServer();
		thread.start();
	}
	
	/**
	 * 断开服务器。
	 */
	private void disconectServer(){
		new WriteThread(new FSMessage(90,null)).start();
		closeClient();
		if(thread!=null)
			thread.interrupt();thread = null;
		list.setEnabled(true);
	}
	
	/**
	 * 断开连接。
	 */
	private void closeClient(){
		closeFile();
		if(isRun)
			itemChange(isRun);
		isRun = false;
		writeLog("和服务器"+getServerInfo()+"断开连接");
		try {
			if(oos!=null)
				oos.close();oos = null;
			if(ois!=null)
				ois.close();ois = null;
			if(client!=null)
				client.close();client = null;
		} catch (IOException e) {
			System.out.println("关闭时错误:"+e.getMessage());
		}
		model.removeAllElements();
	}
	
	/**
	 * 下载数据，采用多线程的方式下载和传输数据。
	 */
	private void downData(){
		try {
			Object[] objects = list.getSelectedValues();
			for(Object obj:objects){
				if(obj instanceof FileData){
					FileData data = (FileData)obj;
					oos.writeObject(new FSMessage(14,data.getPath()));
					oos.flush();
				}
			}
			list.setEnabled(!isRun);
		} catch (IOException e) {}
	}
	
	/**
	 *  连接线程类。
	 * 2008-10-5
	 * @author		达内科技[Tarena Training Group]
	 * @version	1.0
	 * @since		JDK1.6(建议) 
	 */
	private class LinkServer extends Thread{
		
		public LinkServer() {
			try {
				client = new Socket(SERVERIP,PORT);
				writeLog(getClientInfo()+"向服务器"+getServerInfo()+"连接中...");
				oos = new ObjectOutputStream(new BufferedOutputStream(new DataOutputStream(client.getOutputStream())));
				new WriteThread(new FSMessage(10,"/")).start();
				ois = new ObjectInputStream(new BufferedInputStream(new DataInputStream(client.getInputStream())));
				writeLog("和服务器"+getServerInfo()+"连接成功.");
			} catch (UnknownHostException e) {
				writeLog("发生错误:"+e.getMessage());
				closeClient();
			} catch (IOException e) {
				writeLog("发生错误:"+e.getMessage());
				closeClient();
			}
		}
		
		/**
		 * 发送消息
		 * 消息类型：
		 * 	10：客户端发送请求获取目录
		 * 	11：服务端开始传送目录
		 * 	12：服务端根据客户端请求发送目录
		 * 	13：服务端传送目录结束
		 * 	14：客户端发送请求下载共享文件,发送该共享文件的绝对路径
		 *  15：客户端发送的请求有误，不予响应
		 * 	20：服务端根据客户端请求发送客户端创建目录，发送该目录的相对路径
		 * 	30：根据20传输的相对路径创建目录成功,似乎无法处理
		 * 	40：根据20传输的相对路径创建目录失败,似乎无法处理
		 * 	21：开始发送数据，传输该文件的相对路径
		 * 	31：根据21传输的相对路径创建文件成功
		 * 	41：根据21传输的相对路径创建文件失败
		 * 	22：开始传输数据
		 * 	32：传输数据成功，此处不做处理
		 * 	42：传输数据失败，此处不做处理
		 * 	23：数据传输完毕
		 * 	33：关闭流成功，此处不做处理
		 * 	43：关闭流失败，此处不做处理
		 *  90：客户端断开连接
		 * 	91：服务端断开连接
		 */
		@Override
		public void run() {
			try {
				while(isRun && ois!=null && oos!=null){
					//System.out.println("come read");
					if(!isBeginSend){//未传输文件时
						Object obj = ois.readObject();
						if(obj instanceof FSMessage){
							FSMessage message = (FSMessage)obj;
							int type = message.getType();
							//System.out.println(type);
							switch (type) {
							case 11:
								dealSendBegin();
								break;
							case 12:
								dealDirectory(message.getObject());
								break;
							case 13:
								dealSendEnd();
								break;
							case 15:
								dealDownInfo(message.getObject());
								break;
							case 20:
								createDirectory(message.getObject());
								break;
							case 21:
								createFile(message.getObject());
								break;
							case 22:
								isBeginSend = true;
								break;
							case 23:
								getComplete(message.getObject());
								break;
							case 16:
								break;
							case 91:
								writeLog(message.getObject().toString());
								closeClient();
								break;
							}
						}else
							writeLog("服务器"+getServerInfo()+"发送错误数据-->"+obj);
						obj = null;
					}else{//开始传输文件
						fileBeginTime = System.currentTimeMillis();
						long fileLength = ois.readLong();
						while(isRun && oos!=null && ois!=null){
							int readlen = 0;
							byte data[] = new byte[1024];
							readlen = ois.read(data);
							//System.out.println("-->n:"+readlen);
							if(readlen==-1)
								break;
							else{
								dos.write(data,0,readlen);
								dos.flush();
								fileEndTime = System.currentTimeMillis();
								totalDownData += readlen;
							}
							//new WriteThread(new FSMessage(32,null));
							setSpeed(fileLength);
						}
						closeFile();
						isBeginSend = false;
					}//else传输文件结束					
				}
				
			} catch (IOException e) {
				if(isRun && client!=null){
					writeLog("和服务器"+getServerInfo()+"连接发生错误:"+e.getMessage());
					closeClient();
				}
			} catch (ClassNotFoundException e) {
				if(isRun && client!=null){
					writeLog("和服务器"+getServerInfo()+"连接发生错误:"+e.getMessage());
					closeClient();
				}
			}
		}
	}	
	
	/**
	 * 文件下载成功。
	 */
	private void getComplete(Object obj){
		writeLog("文件["+obj.toString()+"]传输完成!用时:"+(fileEndTime-fileBeginTime)/1000+"s");
		//closeFile();
	}
	
	/**
	 * 关闭文件流。
	 */
	private void closeFile(){
		list.setEnabled(isRun);
		totalDownData = 0;
		fileEndTime = System.currentTimeMillis();
		progressBar.setStringPainted(false);
		progressBar.setValue(0);
		lblSpeed.setText("");
		try {
			if(dos!=null){
				dos.flush();
				dos.close();
				dos = null;
			}
			//new WriteThread(new FSMessage(33,null));
		} catch (IOException e) {
			//new WriteThread(new FSMessage(43,null));
		}
	}
	
	/**
	 * 创建文件。
	 * @param obj 创建文件的相对路径。
	 */
	private void createFile(Object obj){
		try {
			String path = obj.toString();
			path = SAVEDIRECTORY+path;
			System.out.println("==>"+path);
			//writeLog("创建文件"+path+"...");
			File file = new File(path);
			if(file.exists()){
				writeLog("存在同名文件"+file.getName()+"，替换之...");
				file.delete();
			}
			else{
				File parentFile = file.getParentFile();
				System.out.println("父目录:"+parentFile.getAbsolutePath());
				if(!parentFile.exists())
					parentFile.mkdirs();
				//file.createNewFile();
			}
			dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			writeLog("文件"+path+"创建成功!");
			//new WriteThread(new FSMessage(31,null));
		} catch (IOException e) {
			//new WriteThread(new FSMessage(41,null));
			System.out.println("创建文件时错误:"+e.getMessage());
		}
	}
	
	/**
	 * 创建目录。
	 * @param obj 创建目录的相对路径路径.
	 */
	private void createDirectory(Object obj){
		try {
			String path = obj.toString();
			path = SAVEDIRECTORY+path;
			System.out.println("==>"+path);
			writeLog("创建目录"+path+"...");
			File file = new File(path);
			if(!file.exists())
				file.mkdirs();
			writeLog("创建目录"+path+"成功!");
			//new WriteThread(new FSMessage(30,null));
		} catch (RuntimeException e) {
			//new WriteThread(new FSMessage(40,null));
		}
	}
	
	/**
	 * 处理客户端下载错误的信息.
	 * @param obj
	 */
	private void dealDownInfo(Object obj){
		writeLog(obj.toString());
		list.setEnabled(isRun);
	}
	
	/**
	 * 传输目录开始。
	 */
	private void dealSendBegin(){
		//System.out.println("come send begin");
		model.removeAllElements();
		list.setEnabled(false);
	}
	/**
	 * 传输目录结束。
	 */
	private void dealSendEnd(){
		//System.out.println("come send end");
		list.setEnabled(true);
	}
	/**
	 * 根据服务端发送的目录显示到列表上。
	 * @param obj 得到的目录FileData对象。
	 */
	private void dealDirectory(Object obj){
		if(obj instanceof FileData)
			model.addElement((FileData)obj);
	}
	/**
	 * 书写线程。
	 * 2008-10-5
	 * @author		达内科技[Tarena Training Group]
	 * @version	1.0
	 * @since		JDK1.6(建议) 
	 */
	private class WriteThread extends Thread{
		FSMessage message = null;
		public WriteThread(FSMessage message) {
			this.message = message;
		}
		@Override
		public void run() {
			try {
				if(oos!=null){
					oos.writeObject(message);
					oos.flush();
				}
			} catch (IOException e) {}
		}
	}
	
	/**
	 * list的ListCellRenderer。
	 * 2008-10-5
	 * @author		达内科技[Tarena Training Group]
	 * @version	1.0
	 * @since		JDK1.6(建议) 
	 */
	private class Renderer extends DefaultListCellRenderer{
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if(value instanceof FileData){
				FileData data = (FileData)value;
				setVerticalAlignment(SwingConstants.CENTER);
				setVerticalTextPosition(SwingConstants.BOTTOM);
				setHorizontalAlignment(SwingConstants.CENTER);
				setHorizontalTextPosition(SwingConstants.CENTER);
				if(data.isFile())
					setIcon(FileIconExtractor.getFileBigIcon(data.getSuffixName()));
				else
					setIcon(FileIconExtractor.getDirectoryBigIcon());
				setToolTipText(data.getFullName().equals("..")?"上一级目录":data.getFullName());
			}
			if(isSelected)
				setBorder(new EmptyBorder(1,1,1,1));
			return component;
		}
	}
	
	/**
	 * 窗体关闭时触发事件。
	 */
	private class MyWindowAdapter extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			//super.windowClosing(e);
			disconectServer();
		}
	}
	
	/**
	 * 鼠标事件。
	 * 2008-10-5
	 * @author		达内科技[Tarena Training Group]
	 * @version	1.0
	 * @since		JDK1.6(建议) 
	 */
	private class ListMouseAdapter extends MouseAdapter{
		 @Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource()==list && list.isEnabled()){
				if(e.getButton()==MouseEvent.BUTTON3){
					list.clearSelection();
					int index = list.locationToIndex(e.getPoint());
					list.setSelectedIndex(index);
					Object obj = list.getSelectedValue();
					FileData data = null;
					if(obj instanceof FileData)
						data = (FileData)obj;
					if(list.getSelectedIndex()!=-1 && data!=null && !data.getFileName().equals(".."))
						popupMenu.show(list, e.getX(), e.getY());
/*					int index = list.locationToIndex(e.getPoint());
					int[] src = list.getSelectedIndices();
					int dest[] = new int[src.length+1];
					System.arraycopy(src, 0, dest, 0, src.length);
					dest[dest.length-1] = index;
					list.setSelectedIndices(dest);*/
				}
				//目录或文件双击事件
				if(e.getClickCount()==2){
					Object object = list.getSelectedValue();
					if(object instanceof FileData){
						FileData data = (FileData)object;
						if(!data.isFile())
							new WriteThread(new FSMessage(10,data.getPath())).start();
						else
							//文件直接下载
							downData();
					}
				}
			}
		}
	}
	
	/**
	 * 设置对话框。
	 * 2008-10-5
	 * @author		达内科技[Tarena Training Group]
	 * @version	1.0
	 * @since		JDK1.6(建议) 
	 */
	private class SetDialog extends JDialog implements ActionListener{
		private JPanel paneSet = new JPanel();
		
		private JLabel lblServerIP = new JLabel("服务器地址");
		private JTextField txtServerIP = new JTextField(SERVERIP+"");
		private JLabel lblPort = new JLabel("服务器端口");
		private JTextField txtPort = new JTextField(PORT+"");
		private JLabel lblSaveDirectory = new JLabel("保存目录");
		private JTextField txtSaveDirectory = new JTextField(SAVEDIRECTORY);
		private JButton btnOK = new JButton("确定");
		private JButton btnCancle = new JButton("取消");
		private JButton btnChange = new JButton("修改");

		//change
		private JLabel lblUser = new JLabel("用户");
		private JLabel lblPassword = new JLabel("密码");

		private JTextField txtUser = new JTextField(User);
		private JTextField txtPassword = new JTextField("******");

		public SetDialog(boolean show) {
			super(FSClient.this,true);
			setTitle("系统设置");
			setSize(300,200);
			setResizable(false);
			Toolkit tk=Toolkit.getDefaultToolkit();
			setLocation((tk.getScreenSize().width-getSize().width)/2,(tk.getScreenSize().height-getSize().height)/2);
			init();
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setVisible(show);
		}
		
		private void initSet(){
			try {
				PORT = Integer.parseInt(txtPort.getText());
				SERVERIP = txtServerIP.getText();
				SAVEDIRECTORY = txtSaveDirectory.getText();
				/*
				Set User And Password
				 */
				User= txtUser.getText();
				Password = txtPassword.getText();


			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "请输入正确的值!");
			}
		}
		
		private void init(){
			btnOK.addActionListener(this);
			btnOK.setPreferredSize(new Dimension(80,25));
			btnCancle.addActionListener(this);
			btnCancle.setPreferredSize(new Dimension(80,25));
			btnChange.addActionListener(this);
			btnChange.setPreferredSize(new Dimension(45,25));
			btnChange.setMargin(new Insets(0,0,0,0));
			lblPort.setPreferredSize(new Dimension(60,25));
			txtPort.setPreferredSize(new Dimension(200,25));

			//change
			lblUser.setPreferredSize(new Dimension(60,25));
			txtUser.setPreferredSize(new Dimension(200,25));
			lblPassword.setPreferredSize(new Dimension(60,25));
			txtPassword.setPreferredSize(new Dimension(200,25));


			lblServerIP.setPreferredSize(new Dimension(60,25));
			txtServerIP.setPreferredSize(new Dimension(200,25));
			lblSaveDirectory.setPreferredSize(new Dimension(60,25));
			txtSaveDirectory.setPreferredSize(new Dimension(150,25));
			txtSaveDirectory.setEditable(false);
			paneSet.setLayout(new FlowLayout(FlowLayout.CENTER,7,5));
			paneSet.add(lblServerIP);
			paneSet.add(txtServerIP);
			paneSet.add(lblPort);
			paneSet.add(txtPort);
			//change
			paneSet.add(lblUser);
			paneSet.add(txtUser);
			paneSet.add(lblPassword);
			paneSet.add(txtPassword);

			paneSet.add(lblSaveDirectory);
			paneSet.add(txtSaveDirectory);
			paneSet.add(btnChange);
			paneSet.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY),"设置[->]"));
			add(paneSet);
			JPanel pane = new JPanel();
			pane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			pane.add(btnOK);
			pane.add(btnCancle);
			pane.setPreferredSize(new Dimension(200,35));
			add(pane,BorderLayout.SOUTH);
		}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==btnOK)
				initSet();
			if(e.getSource()==btnChange){
				DirectoryChoose chooser = new DirectoryChoose(this,"选择要保存到的目录");
				File file = chooser.getSelectedFile();
				if(file!=null)
					txtSaveDirectory.setText(file.getAbsolutePath());
				return;
			}
			
			dispose();
		}
	}

}
