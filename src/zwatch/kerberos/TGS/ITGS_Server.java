package zwatch.kerberos.TGS;

import zwatch.kerberos.IServerConfig;
import zwatch.kerberos.ticket.Ticket_TGS;
import zwatch.kerberos.ticket.Ticket_V;

public interface ITGS_Server extends IServerConfig {
    void run();
    void Login(Ticket_TGS ticket_as);
    Ticket_V getTicket(String pass);
}
