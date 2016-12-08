package domain;

import java.util.Date;

/**
 * Created by olgo on 11-Nov-16.
 */
public class  DBUser implements java.io.Serializable {
    private long userId;
    private String username;
    private String createdBy;

    public DBUser() {
    }

    public DBUser(String username, String createdBy) {
        this.username = username;
        this.createdBy = createdBy;
    }

    public long getUserId() {
        return this.userId;
    }

    private void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "DBUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
