package zwatch.kerberos;


public interface IServerConfig {
    void SaveConfig(String filename);
    void LoadConfig(String filename);
}
