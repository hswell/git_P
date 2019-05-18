/**
  * @(#)tools.DownSpeed.java  2008-10-6  
  * Copy Right Information	: Tarena
  * Project					: FileShare
  * JDK version used		: jdk1.6.4
  * Comments				: 下载速度类。
  * Version					: 1.0
  * Sr	Date		Modified By		Why & What is modified
  * 1.	2008-10-6 	小猪     		新建
  **/
package zx.tools;

import java.util.Vector;

 /**
 * 下载速度类。
 * 2008-10-6
 * @author		达内科技[Tarena Training Group]
 * @version	1.0
 * @since		JDK1.6(建议) 
 */
public class DownSpeed {

	private static Vector<String> v = new Vector<String>();
	static{
		v.add("B");
		v.add("K");
		v.add("M");
		v.add("G");
		v.add("T");
	}
	
	/**
	 * 根据下载的字节数和下载的时间返回合适的下载速度
	 * @param totalData 下载的总字节数
	 * @param beginTime 开始下载时间，毫秒为单位
	 * @param endTime 结束下载时间，毫秒为单位
	 * @return 根据大小返回合适的速度，如1M/s
	 */
	public static String getSpeed(long totalData,long beginTime,long endTime){
		int n = 0;
		if(endTime-beginTime==0) return "0B/s";
		long speed = totalData/(endTime-beginTime)*1000;
		//System.out.println("speed-->"+speed);
		//System.out.println("totalData-->"+totalData+"\nbegin-->"+beginTime+"\nend-->"+endTime);
		while(speed>1024){
			speed /=1024;
			n++;
		}
		if(n>4)
			n = 4;
		return ""+speed+v.get(n)+"/s";
	}
	
	/**
	 * 返回1s中内传输的速度
	 * @param totalData 1s内下载的总字节数
	 * @return 根据大小返回合适的速度，如1M/s
	 */
	public static String getSpeed(long totalData){
		int n = 0;
		long speed = totalData;
		while(speed>1024){
			speed /=1024;
			n++;
		}
		if(n>4)
			n = 4;
		return ""+speed+v.get(n)+"/s";
	}
}
