package Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "post")
public class Post implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "post_id", unique = true)
    private int id;

    @Column(name = "text", unique = false, nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name="thread_id", nullable=false)
    private Thread thread;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(referencedColumnName = "u_id")
    private User createdBy;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(referencedColumnName = "u_id")
    private User updatedBy;

    @Column(name = "created_at", unique = true, nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", unique = true, nullable = true)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
