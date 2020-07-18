package ua.wismut.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @CreatedBy
    private Instant created;

    @LastModifiedDate
    private Instant updated;

    @LastModifiedDate
    private Instant lastPasswordChangeDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(table = "users", name = "user_id"),
            inverseJoinColumns = @JoinColumn(table = "roles", name = "role_id"))
    private Set<Role> roles;

    @Transient
    private String confirmPassword;

    public User() {
    }

    public User(Long id, String username, String password, Instant created, Instant updated, Instant lastPasswordChangeDate, Status status, String phoneNumber, Set<Role> roles, String confirmPassword) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.created = created;
        this.updated = updated;
        this.lastPasswordChangeDate = lastPasswordChangeDate;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.confirmPassword = confirmPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Instant getLastPasswordChangeDate() {
        return lastPasswordChangeDate;
    }

    public void setLastPasswordChangeDate(Instant lastPasswordChangeDate) {
        this.lastPasswordChangeDate = lastPasswordChangeDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @PrePersist
    public void prePersist() {
        created = Instant.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", lastPasswordChangeDate=" + lastPasswordChangeDate +
                ", status=" + status +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roles=" + roles +
                '}';
    }
}
