package zx;




import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class TEST1 extends JFrame{
JTextField txtzhujiip=new JTextField();
JTextField txtyuanip=new JTextField();
JTextField txtmiwen=new JTextField();
JPasswordField txtjiexi=new JPasswordField();
JTextField txtfanmi=new JTextField();
JTextField txtfanmin=new JTextField();
JButton bl=new JButton("登录");
JButton bg=new JButton("关闭");

//构造无参构造器把主要的方法放在构造器里,然后在main方法里面调
public TEST1(){
	bl.setVisible(false);
	bg.setVisible(false);
setBounds(30,25,300,300);
Container c = getContentPane();
c.setLayout(new GridLayout(4,2,10,10));
c.add(new JLabel("主机IP"));
c.add(txtzhujiip);
//txtzhujiip.setText("test");
c.add(new JLabel("源IP"));
c.add(txtyuanip);
c.add(new JLabel("源IP密文"));
c.add(txtmiwen);
c.add(new JLabel("源IP密文解析"));
c.add(txtjiexi);
c.add(new JLabel("主机返回密文"));
c.add(txtfanmi);
c.add(new JLabel("主机返回明文"));
c.add(txtfanmin);
c.add(bl);
c.add(bg);
setDefaultCloseOperation(EXIT_ON_CLOSE);
setVisible(true);
//注意：此处是匿名内部类
bg.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
System.exit(0);
}
});

              //注意：此处是匿名内部类
bl.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
String name = txtzhujiip.getText();
String pass = txtyuanip.getText();
if(name.equals("tom")&&pass.equals("123")){
System.out.println("登陆成功");
}else{
System.out.println("登录失败");
}
}

});

}
public static void main(String[] args) {
new TEST1();
}
}