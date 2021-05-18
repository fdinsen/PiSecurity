package Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "board")
public class Board implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", unique = true)
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description", unique = false, nullable = true)
    private String description;

    @ManyToOne()
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    @OneToMany(mappedBy="board",orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Thread> threads;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
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

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
