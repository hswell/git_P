package zwatch.kerberos.Client;

import zwatch.kerberos.ITGS_Server;
import zwatch.kerberos.Ticket_V;
import zwatch.kerberos.Ticket_TGS;

public class TGS_proxy implements ITGS_Server {
    @Override
    public void run() {

    }

    @Override
    public void Login(Ticket_TGS ticket_as) {

    }

    @Override
    public Ticket_V getTicket(String pass) {
        return null;
    }

    @Override
    public void SaveConfig(String filename) {

    }

    @Override
    public void LoadConfig(String filename) {

    }
}
