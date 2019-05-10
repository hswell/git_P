package zwatch.kerberos;

public interface ITGS_Server extends IServerConfig {
    void run();
    void Login(Ticket_TGS ticket_as);
    Ticket_V getTicket(String pass);
}
