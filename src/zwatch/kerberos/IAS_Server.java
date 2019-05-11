package zwatch.kerberos;

public interface IAS_Server extends IServerConfig {
    void run();
    void Login(String uid);
    Ticket_V getTicket(String pass);
}
