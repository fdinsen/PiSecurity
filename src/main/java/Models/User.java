package Models;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "user")
public class User implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "u_id", unique = true)
    private UUID id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User() {
    }
    
    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
}
