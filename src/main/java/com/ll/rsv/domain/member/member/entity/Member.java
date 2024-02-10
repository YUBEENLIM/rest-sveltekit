package com.ll.rsv.domain.member.member.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Member {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	@CreatedDate
	private LocalDateTime createDate;
	@LastModifiedDate
	private LocalDateTime modifyDate;
	@Column(unique = true)
	private String username;
	private String password;
	@Column(unique = true)
	private String refreshToken;

	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getAuthoritiesAsStringList()
			.stream()
			.map(SimpleGrantedAuthority::new)
			.toList();
	}

	@Transient
	public List<String> getAuthoritiesAsStringList() {
		List<String> authorities = new ArrayList<>();

		authorities.add("ROLE_MEMBER");

		if (List.of("system", "admin").contains(username)) {
			authorities.add("ROLE_ADMIN");
		}

		return authorities;
	}
}
