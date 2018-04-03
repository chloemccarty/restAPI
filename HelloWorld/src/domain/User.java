package domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class User extends DataModel {
    private int id;
    private String username;
    private String password;
    private int numPosts;
    private boolean admin;
    private List<Conversation> conversations;
    private boolean banned;
    private Date banEnd;
    private String url;

    @Override
    public String toJson() {
        Type userType = (new TypeToken<User>() {}).getType();
        return (new Gson()).toJson(this, userType);
    }

    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
        this.url = rootURI + "/users/" + id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumPosts() {
        return numPosts;
    }
    public void incrNumPosts() {
        this.numPosts++;
    }

    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }
    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public boolean isBanned() {
        return banned;
    }
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public Date getBanEnd() {
        return banEnd;
    }
    public void setBanEnd(Date banEnd) {
        this.banEnd = banEnd;
    }
}
