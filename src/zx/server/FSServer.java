/**
  * @(#)server.FSServer.java  2008-10-5  
  * Copy Right Information	: Tarena
  * Project					: FileShare
  * JDK version used		: jdk1.6.4
  * Comments				: 服务器界面端。
  * Version					: 1.0
  * Sr	Date		Modified By		Why & What is modified
  * 1.	2008-10-5 	小猪     		新建
  **/
package zx.server;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import zwatch.kerberos.V.V_check;
import zx.data.FSMessage;
import zx.data.FileData;

import zx.tools.DateDeal;
import zx.tools.DirectoryChoose;


 /**
 * 服务器界面端。
 * 2008-10-5
 * @author		达内科技[Tarena Training Group]
 * @version	1.0
 * @since		JDK1.6(建议) 
 */
@SuppressWarnings("serial")
public class FSServer extends JFrame implements ActionListener,Runnable{
	
	/** 菜单栏 */
	private JMenuBar menuBar=new JMenuBar();
	/** 操作服务端菜单 */
	private JMenu menuOperation = new JMenu("操作(O)");
	/** 启动服务端按钮 */
	private JMenuItem itemBegin = new JMenuItem("启动(B)",KeyEvent.VK_B);
	/** 停止服务端按钮 */
	private JMenuItem itemEnd = new JMenuItem("停止(E)",KeyEvent.VK_E);
	/** 设置服务端按钮 */
	private JMenuItem itemSet = new JMenuItem("设置(S)",KeyEvent.VK_S);
	/** 帮助服务端菜单 */
	private JMenu menuHelp = new JMenu("帮助(H)");
	/** 关于服务端按钮 */
	private JMenuItem itemAbout = new JMenuItem("关于(A)",KeyEvent.VK_A);
	/** list的DefaultListModel */
	DefaultListModel model = new DefaultListModel();
	/** 显示在线用户列表 */
	private JList list = new JList(model);
	/** 显示日志 */
	private JTextArea areaLog = new JTextArea();
	/** 服务器启动的端口 */
	private static Integer PORT = 3608;
	/** 允许连接的最大数 */
	private static Integer ALLOWNUM = 5;
	/** 共享的目录，多个目录用|分开 */
	private static String SHAREDIRECTORY = "";
	/** 设置对话框 */
	private SetDialog dialog = new SetDialog(false);
	/** 服务端ServerSocket */
	private ServerSocket server = null;
	/** 服务端是否启动 */
	private boolean isRun = false;
	/** 服务端启动时的线程 */
	private Thread thread = null;
	/** list的右键菜单 */
	private JPopupMenu popupMenu = null;
	/** 剔除该用户 */
	private JMenuItem itemDelete = new JMenuItem("踢他下线");
	
	public FSServer() {
		setTitle("文件共享服务器服务端[FileShareServer]");
		setSize(620,420);
		setMinimumSize(new Dimension(300,210));
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
		menuOperation.add(itemBegin);
		menuOperation.add(itemEnd);
		menuOperation.addSeparator();
		menuOperation.add(itemSet);
		menuHelp.add(itemAbout);
		menuHelp.setMnemonic(KeyEvent.VK_H);
		itemAbout.addActionListener(this);
		itemBegin.addActionListener(this);
		itemEnd.addActionListener(this);
		itemSet.addActionListener(this);
		
		menuBar.add(menuOperation);
		//menuBar.add(menuHelp);
		setJMenuBar(menuBar);
		itemChange(!isRun);
		popupMenu = new JPopupMenu("操作菜单");
		popupMenu.add(itemDelete);
		itemDelete.addActionListener(this);
	}
	
	/**
	 * 初始化界面。
	 */
	private void init(){
		list.addMouseListener(new ListMouseAdapter());
		areaLog.setLineWrap(true);
		areaLog.setEditable(false);
		JScrollPane sp1 = new JScrollPane(list);
		sp1.setPreferredSize(new Dimension(160,380));
		sp1.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY),"在线客户端"));
		JScrollPane sp2 = new JScrollPane(areaLog);
		sp2.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY),"操作日志"));
		add(sp1,BorderLayout.WEST);
		add(sp2);
		
		
	}
	
	/**
	 * ActionListener事件。
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==itemSet){
			dialog.setVisible(true);
			return;
		}
		if(e.getSource()==itemBegin){
			if(SHAREDIRECTORY.equals("")){
				JOptionPane.showMessageDialog(null, "请先设置共享目录!");
				return;
			}
			startServer();
			return;
		}
		if(e.getSource()==itemEnd){
			stopServer();
			return;
		}
		if(e.getSource()==itemAbout)
		{}
		//new About(this,"关于本文件共享服务器",true);
		if(e.getSource()==itemDelete){
			Object[] objects = list.getSelectedValues();
			for(Object obj:objects){
				if(obj instanceof ClientThread){
					ClientThread client = (ClientThread)obj;
					int n = JOptionPane.showConfirmDialog(list, "确认删除该用户"+client.getClientInfo()+"?","确认替他?",JOptionPane.OK_CANCEL_OPTION);
					if(n==JOptionPane.OK_OPTION)
						client.letClientQuit("管理员请你下线休息几分钟!");
				}
			}
		}
	}
	
	@Override
	/**
	 * 启动接受线程。
	 */
	public void run() {
		try {
			while(isRun){
				Socket client = server.accept();
				new ClientThread(client).start();
			}
		} catch (IOException e) {
			if(isRun && server!=null){
				writeLog("服务器"+getServerInfo()+"发生错误:"+e.getMessage());
				stopServer();
			}
		}
	}
	/**
	 * 禁用/启用菜单选择方法。
	 * @param b b为true时，启动和设置菜单可用，停止菜单不可用；否则，反之。
	 */
	private void itemChange(boolean b){
		itemBegin.setEnabled(b);
		itemEnd.setEnabled(!b);
		itemSet.setEnabled(b);
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
	 * 获取服务器的IP信息。
	 * @return 服务器的IP信息。
	 */
	private String getServerInfo(){
		if(server!=null){
			InetAddress address = server.getInetAddress();
			return "["+address.getHostName()+"("+address.getHostAddress()+":"+server.getLocalPort()+")]";
		}else
			return "[IP信息未知]";
	}
	
	/**
	 * 启动服务器。
	 */
	private void startServer(){
		try {
			itemChange(isRun);
			isRun = true;
			writeLog("服务器"+getServerInfo()+"启动中...");
			server = new ServerSocket(PORT);
			thread = new Thread(this);
			thread.start();
			writeLog("服务器"+getServerInfo()+"启动成功!等待客户端连接.");
		} catch (IOException e) {
			writeLog("服务器"+getServerInfo()+"启动失败!原因如下:"+e.getMessage());
			stopServer();
		}
	}
	
	/**
	 * 停止服务器。
	 */
	private void stopServer(){
		try {
			itemChange(isRun);
			isRun = false;
			writeLog("服务器"+getServerInfo()+"关闭中...");
			for(int i=0;i<model.size();i++){
				Object obj = model.get(i);
				if(obj instanceof ClientThread){
					ClientThread client = (ClientThread)obj;
					client.letClientQuit("服务端关闭!");
				}
			}
			model.clear();
			if(server!=null)
				server.close();server = null;
			writeLog("服务器"+getServerInfo()+"关闭成功.");
			if(thread!=null)
				thread.interrupt();thread = null;
		} catch (IOException e) {
			writeLog("服务器"+getServerInfo()+"关闭失败!原因如下:"+e.getMessage());
		}
	}
	
	/**
	 * 客户端线程类。
	 * 2008-10-5
	 * @author		达内科技[Tarena Training Group]
	 * @version	1.0
	 * @since		JDK1.6(建议) 
	 */
	private class ClientThread extends Thread{
		private Socket client = null;
		private ObjectOutputStream oos = null;
		private ObjectInputStream ois = null;
		public ClientThread(Socket client) {
			this.client = client;
			model.addElement(this);
			try {
				writeLog("客户端"+getClientInfo()+"上线");
				ois = new ObjectInputStream(new BufferedInputStream(new DataInputStream(client.getInputStream())));
				oos = new ObjectOutputStream(new BufferedOutputStream(new DataOutputStream(client.getOutputStream())));
				if(model.size()>ALLOWNUM)
					letClientQuit("超出最大连接数:"+ALLOWNUM+".请稍后连接!");
			} catch (IOException e) {
				writeLog("客户端"+getClientInfo()+"发生错误:"+e.getMessage());
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
		 * 	15：客户端发送的请求有误，不予响应
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
		public void run() {
			try {
				while(isRun && ois!=null && oos!=null){
					Object obj = ois.readObject();
					if(obj instanceof FSMessage){
						//Verification on here
						FSMessage message = (FSMessage)obj;
						if(!V_check.check(client, message.getTicket(), message.getAuth())){
							letClientQuit("票据错误");
							break;
						}else{
							int type = message.getType();
							switch (type) {
							case 10:
								dealDirectory(message.getObject());
								break;
							case 14:
								dealSendData(message.getObject());
								break;
							case 90:
								closeClient();
								break;
							}
						}
					}else{ writeLog("客户端"+getClientInfo()+"发送错误数据-->"+obj);}
				}
			} catch (IOException e) {
				writeLog("客户端"+getClientInfo()+"发生错误:"+e.getMessage());
				closeClient();
			} catch (ClassNotFoundException e) {
				writeLog("客户端"+getClientInfo()+"发生错误:"+e.getMessage());
				closeClient();
			}			
		}
		
		/**
		 * 处理客户端获取目录的请求。
		 * @param obj 欲获取的目录。
		 */
		private void dealSendData(Object obj){			
			new WriteThreadForSendData(obj).start();
		}
		
		/**
		 * 处理客户端获取目录的请求。
		 * @param obj 欲获取的目录。
		 */
		private void dealDirectory(Object obj){			
			new WriteThreadForDirectory(obj).start();
		}
		
		/**
		 * 获取客户端IP信息
		 * @return 客户端IP信息。
		 */
		private String getClientInfo(){
			if(client!=null){
				InetAddress address = client.getInetAddress();
				return "["+address.getHostName()+"("+address.getHostAddress()+":"+client.getPort()+")]";
			}else
				return "[IP信息未知]";
		}
		
		/**
		 * 使得客户端退出。
		 */
		public void letClientQuit(String msg){
			FSMessage message = new FSMessage(91,msg);
			//new WriteThread(message).start();
			//防止空指针异常!如果启用单独线程关闭到服务端的连接时很可能发生空指针异常。
			try {
				oos.writeObject(message);
				oos.flush();
			} catch (IOException e) {}
			closeClient();
		}
		
		/**
		 * 关闭到客户端的连接。
		 */
		private void closeClient(){
			model.removeElement(this);
			writeLog("客户端"+getClientInfo()+"下线!");
			try {
				if(oos!=null)
					oos.close();oos = null;
				if(ois!=null)
					ois.close();ois = null;
				if(client!=null)
					client.close();client = null;
			} catch (IOException e) {}
		}
		
		@Override
		public String toString() {
			return getClientInfo();
		}
		/*
		 * 书写线程。
		 * 2008-10-5
		 * @author		达内科技[Tarena Training Group]
		 * @version	1.0
		 * @since		JDK1.6(建议) 
		 */		/*
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
		}*/
		/**
		 * 传输目录线程。
		 * 2008-10-6
		 * @author		达内科技[Tarena Training Group]
		 * @version	1.0
		 * @since		JDK1.6(建议) 
		 */
		private class WriteThreadForDirectory extends Thread{
			String path = null;
			public WriteThreadForDirectory(Object obj) {
				path = obj.toString();
			}
			@Override
			public void run() {
				try {
					if(oos!=null){
						oos.writeObject(new FSMessage(11,null));
						oos.flush();
						//传输顶级共享目录.
						writeLog("客户端"+getClientInfo()+"获取目录:"+path);
						if(path.equals("/")){
							//System.out.println("come ==>"+path);
							StringTokenizer tokenizer = new StringTokenizer(SHAREDIRECTORY,"|");
							while(tokenizer.hasMoreTokens()){
								String xpath = tokenizer.nextToken();
								File file = new File(xpath);
								if(file!=null && file.exists() && file.canRead()){
									FSMessage message = new FSMessage(12,new FileData(file));
									oos.writeObject(message);
									oos.flush();
								}
							}
						}else{
							//System.out.println("come -->"+path);
							//首先确保客户端传输过来的目录是已经共享的子目录
							if(FileData.ContainByShareDirectory(path, SHAREDIRECTORY, "|")!=null){
							//if(path.indexOf(xpath)!=-1){
								File xfile = new File(path);
								if(xfile.exists() && xfile.canRead()){
									//如果传输过来的目录是共享目录中的其中一个,则上一级目录为/
									if(SHAREDIRECTORY.indexOf(path)!=-1)
										oos.writeObject(new FSMessage(12,new FileData("/","..")));
									//不是共享目录中的其中一个,则它的上一级目录为该path
									else
										oos.writeObject(new FSMessage(12,new FileData(xfile.getParent(),"..")));
									oos.flush();
									//先传输目录
									for(File file:xfile.listFiles())
										if(file!=null && file.canRead() && file.exists() && file.isDirectory()){
											FSMessage message = new FSMessage(12,new FileData(file));
											oos.writeObject(message);
											oos.flush();
										}
									//在传输文件
									for(File file:xfile.listFiles())
										if(file!=null && file.canRead() && file.exists() && file.isFile()){
											FSMessage message = new FSMessage(12,new FileData(file));
											oos.writeObject(message);
											oos.flush();
										}
								}
							}
						}
						oos.writeObject(new FSMessage(13,null));
						oos.flush();
					}
				} catch (IOException e) {}
			}
		}
		
		/**
		 * 传输数据线程。
		 * 2008-10-6
		 * @author		达内科技[Tarena Training Group]
		 * @version	1.0
		 * @since		JDK1.6(建议) 
		 */
		private class WriteThreadForSendData extends Thread{
			private String path = null;
			public WriteThreadForSendData(Object obj) {
				path = obj.toString();
			}
			@Override
			public void run() {
				send(new File(path));
			}
			
			synchronized private void send(File file){
				try {
					String relativePath = FileData.ContainByShareDirectory(file.getAbsolutePath(), SHAREDIRECTORY, "|");
					if(relativePath!=null && file!=null && file.exists() && file.canRead() && !file.getName().equals("")){//文件名不为空
						//writeLog("客户端"+getClientInfo()+"准备下载文件:"+path+"。");
						if(file.isDirectory()){
							oos.writeObject(new FSMessage(20,relativePath));
							oos.flush();
							for(File xfile:file.listFiles())
								send(xfile);
						}
						if(file.isFile()){
							oos.writeObject(new FSMessage(21,relativePath));
							oos.flush();
							writeLog("开始向客户端"+getClientInfo()+"发送文件:"+file.getAbsolutePath()+"...");
							oos.writeObject(new FSMessage(22,null));
							oos.flush();
							DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
							oos.writeLong(file.length());
							oos.flush();
							int readlen = 0;
							do{
								byte[] data = new byte[1024];
								readlen = dis.read(data);
								if(readlen==-1)
									break;
								//oos.writeObject(new FSMessage(22,data));
								oos.write(data,0,readlen);
								oos.flush();
								data = null;
							}while(true);
							dis.close();
							dis = null;
							oos.writeObject(new FSMessage(23,file.getName()));
							oos.flush();
						}
						writeLog("客户端"+getClientInfo()+"成功下载文件:"+file.getAbsolutePath()+"。");
					}else{//file不为空end
						oos.writeObject(new FSMessage(15,"请求服务"+path+"不可达..."));
						oos.flush();
					}
				} catch (IOException e) {
					System.out.println(""+e.getMessage());
					//e.printStackTrace();
				}
			}//send函数end
		}
	}
	
	/**
	 * 窗体关闭时触发事件。
	 */
	private class MyWindowAdapter extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			//super.windowClosing(e);
			stopServer();
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
			if(e.getSource()==list){
				if(e.getButton()==MouseEvent.BUTTON3){
/*					list.clearSelection();
					int index = list.locationToIndex(e.getPoint());
					list.setSelectedIndex(index);*/
					int index = list.locationToIndex(e.getPoint());
					int[] src = list.getSelectedIndices();
					int dest[] = new int[src.length+1];
					System.arraycopy(src, 0, dest, 0, src.length);
					dest[dest.length-1] = index;
					list.setSelectedIndices(dest);
					if(list.getSelectedIndex()!=-1)
						popupMenu.show(list, e.getX(), e.getY());
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
		
		private JLabel lblPort = new JLabel("服务器端口");
		private JTextField txtPort = new JTextField(PORT+"");
		private JLabel lblAllowNum = new JLabel("最大连接数");
		private JTextField txtAllowNum = new JTextField(ALLOWNUM+"");
		private JLabel lblShareDirectory = new JLabel("共享目录");
		private JTextArea txtShareDirectory = new JTextArea(SHAREDIRECTORY);
		private JButton btnOK = new JButton("确定");
		private JButton btnCancle = new JButton("取消");
		private JButton btnAdd = new JButton("添加");
		private JButton btnClear = new JButton("清空");
		
		public SetDialog(boolean show) {
			super(FSServer.this,true);
			setTitle("系统设置");
			setSize(300,220);
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
				ALLOWNUM = Integer.parseInt(txtAllowNum.getText());
				SHAREDIRECTORY = txtShareDirectory.getText();
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "请输入正确的值!");
			}
		}
		
		private void init(){
			btnOK.addActionListener(this);
			btnOK.setPreferredSize(new Dimension(80,25));
			btnCancle.addActionListener(this);
			btnCancle.setPreferredSize(new Dimension(80,25));
			btnAdd.addActionListener(this);
			btnAdd.setPreferredSize(new Dimension(45,25));
			btnAdd.setMargin(new Insets(0,0,0,0));
			btnClear.addActionListener(this);
			btnClear.setPreferredSize(new Dimension(45,25));
			btnClear.setMargin(new Insets(0,0,0,0));
			lblPort.setPreferredSize(new Dimension(60,25));
			txtPort.setPreferredSize(new Dimension(200,25));
			lblAllowNum.setPreferredSize(new Dimension(60,25));
			txtAllowNum.setPreferredSize(new Dimension(200,25));
			lblShareDirectory.setPreferredSize(new Dimension(60,60));
			lblShareDirectory.setToolTipText("添加多个共享目录,用|分隔");
			txtShareDirectory.setPreferredSize(new Dimension(150,60));
			txtShareDirectory.setToolTipText("添加多个共享目录,用|分隔");
			txtShareDirectory.setEditable(false);
			txtShareDirectory.setLineWrap(true);
			paneSet.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
			paneSet.add(lblPort);
			paneSet.add(txtPort);
			paneSet.add(lblAllowNum);
			paneSet.add(txtAllowNum);
			paneSet.add(lblShareDirectory);
			JScrollPane sp = new JScrollPane(txtShareDirectory);
			sp.setPreferredSize(new Dimension(150,60));
			paneSet.add(sp);
			JPanel paneBtn = new JPanel();
			paneBtn.setPreferredSize(new Dimension(46,60));
			paneBtn.setLayout(new FlowLayout(FlowLayout.CENTER,2,3));
			paneBtn.add(btnAdd);
			paneBtn.add(btnClear);
			paneSet.add(paneBtn);
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
			if(e.getSource()==btnAdd){
				DirectoryChoose chooser = new DirectoryChoose(this,"选择要共享的目录");
				File file = chooser.getSelectedFile();
				if(file!=null){
					String path = file.getAbsolutePath();
					String xpath = txtShareDirectory.getText();
					if(xpath.indexOf(path)==-1)
						txtShareDirectory.append(path+"|");
				}
				return;
			}
			if(e.getSource()==btnClear){
				txtShareDirectory.setText("");
				return;
			}
			dispose();
		}
	}
}
