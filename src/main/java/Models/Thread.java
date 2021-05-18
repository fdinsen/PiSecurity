package Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "thread")
public class Thread implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "thread_id", unique = true)
    private int id;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @Column(name = "text", unique = false, nullable = false)
    private String text;

    @Column(name = "views", unique = false, nullable = false,columnDefinition = "int default 0")
    private int views;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="board_id", nullable=false)
    private Board board;

    @OneToMany(mappedBy="thread",orphanRemoval = true, cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private List<Post> posts;

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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
