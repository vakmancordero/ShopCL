package shopcl.model;

import java.util.Date;
import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import javax.persistence.IdClass;
import javax.persistence.Id;


/**
 *
 * @author VakSF
 */

@Entity
@Table (name = "user")
public class User implements Serializable, Comparable<User> {
    
    @Id
    private int id;
    
    @Column
    private String username;
    
    @Column
    private Date date;
    
    @Column
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.date = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.username);
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
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(User user) {
        
        int result = this.username.compareTo(user.getUsername());
        
        result += this.password.compareTo(user.getPassword());
        
        return result;
    }

    @Override
    public String toString() {
        return this.username + " - " + this.getDate().toString();
    }
    
}