package com.trilha.exercicio3.usuarios;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name="Usuario")
@Table(name="usuarios")
@EqualsAndHashCode(of= "id")
@NoArgsConstructor
@Getter
@Setter
public class Usuario implements UserDetails{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String login;

	@Column
	private String senha;

	public Usuario() {
    }
	
	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

}