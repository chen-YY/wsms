package wsms.Login;

import entity.StorageAdmin;
import entity.StoreAdmin;

public interface LoginService {
    public String login(String account,String password,String accountType);
    public String test();
    public StorageAdmin selectStorageAdminById(int id);
    public StoreAdmin selectStoreAdminById(int id);
}
