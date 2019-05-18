/**
  * @(#)data.FSMessage.java  2008-10-5  
  * Copy Right Information	: Tarena
  * Project					: FileShare
  * JDK version used		: jdk1.6.4
  * Comments				: 此处输入简单类说明
  * Version					: 1.0
  * Sr	Date		Modified By		Why & What is modified
  * 1.	2008-10-5 	小猪     		新建
  **/
package zx.data;

import zwatch.kerberos.Utils;
import zwatch.kerberos.ticket.Authenticator_tgs;
import zwatch.kerberos.ticket.Ticket_V;

import java.io.Serializable;

 /**
 * 此处加入类详细说明
 * 2008-10-5
 * @author		达内科技[Tarena Training Group]
 * @version	1.0
 * @since		JDK1.6(建议) 
 */
@SuppressWarnings("serial")
public class FSMessage implements Serializable {
	Integer type = 0;
	Object object = null;
	String Ticket_v=null;
	String Auth=null;
	public FSMessage() {
		
	}
	public FSMessage(Integer type, Object object) {
		this.type = type;
		this.object = object;
		Ticket_v= Utils.default_ticket_v;
		Auth = Utils.default_auth;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	} 
	public String getTicket(){
		return Ticket_v;
	}
	public String getAuth(){
		return Auth;
	}

}
