package com.lozzby.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
    @NotEmpty
    @Column(name = "first_name" , nullable = false)
    private String firstName;
    
    
    private String lastName;
    
    @NotEmpty
    @Column(name = "email" , nullable =  false , unique = true)
    @Email(message = "Email is not valid",regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @NotEmpty
    private String Password;
    
    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER )
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "USER_ID" , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "id")
    )
    private List<Role> roles;

	public User(User user) {
		
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.Password = user.getPassword();
		this.roles = user.getRoles();
	}
    
    
    
}
