package usta.com.jwtdemo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")
public class UsersEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idUser")
    private Long idUser;
    
    @Column(name = "username")
    private String username;

    @Column(name= "password")
    private String password;

    @Column(name = "estate")
    private boolean estate;

    public UsersEntity(Long idUser, String username, String password, boolean estate) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.estate = estate;
    }

    public UsersEntity() {
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
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

    public boolean isEstate() {
        return estate;
    }

    public void setEstate(boolean estate) {
        this.estate = estate;
    }
}
