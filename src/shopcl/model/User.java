package shopcl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author VakSF
 */

@Entity
@Table (name = "user")
public class User implements Serializable, Comparable<User> {
    
    @Id
    @Column(name = "username", nullable = false)
    private String username;
    
    @Column
    private String password;
    
    @Column
    private Date date;
    
    public User() {
        
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.date = new Date();
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.username);
        hash = 97 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return this.username + " - " + this.date;
    }
    
    @Override
    public int compareTo(User user) {
        return this.username.compareTo(user.getUsername());
    }
    
}