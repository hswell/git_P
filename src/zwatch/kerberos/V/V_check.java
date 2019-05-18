package zwatch.kerberos.V;

import zwatch.kerberos.Utils;
import zwatch.kerberos.ticket.Authenticator_tgs;
import zwatch.kerberos.ticket.Authenticator_v;
import zwatch.kerberos.ticket.Ticket_V;

import java.net.Socket;

public class V_check {
    private static String v_password="20161001";
    public static boolean check(Socket s , String ticket_v, String auth){
        Ticket_V ticketV=null;
        Authenticator_v authV=null;
        boolean ret=true;
        if(true){
            try {
                ticketV=Ticket_V.UnCryptPack(ticket_v, v_password.getBytes());
                authV=Authenticator_v.UnCryptPack(auth, ticketV.Kc_v);
                ret&=checkIP(s, ticketV);
            } catch (Exception e) {
                e.printStackTrace();
                ret &= false;
            }
        }
        return ret;
    }

    public static boolean checkIP(Socket s , Ticket_V ticket_v){
        //return true;
        return Utils.GetAddressOnlyIP(s).equals(ticket_v.ADc);
    }
}
