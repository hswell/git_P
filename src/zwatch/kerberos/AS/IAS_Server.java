package zwatch.kerberos.AS;

import zwatch.kerberos.IServerConfig;
import zwatch.kerberos.ticket.Ticket_V;

public interface IAS_Server extends IServerConfig {
    void run();
    void Login(String uid);
    Ticket_V getTicket(String pass);
}
