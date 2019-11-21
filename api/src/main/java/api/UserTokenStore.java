package api;

public interface UserTokenStore {

    public String getUser(String token);

    public void setUserToken(String username, String token);
}
