package com.delacasa.auth.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Getter
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "username")
	@Size(min = 4, max = 255)
	@ReadOnlyProperty
	private String username;

	@Column(name = "email")
	@Email(message = "[${validatedValue}] is not recognized has a valid email")
	@Size(min = 4, max = 255, message = "Email should be maximum {max} characters and minimum {min} characters ")
	@ReadOnlyProperty
	private String email;

	@Column(name = "phone")
	@NotBlank
	@Size(min = 3, max = 18)
	@ReadOnlyProperty
	private String phone;

	@Column(name = "password")
	@NotBlank
	@Size(min = 60, max = 60)
	@ReadOnlyProperty
	private String password;

	@Column(name = "has_multi_factor_auth")
	private boolean twoFactorAuth;

	@Column(name = "remaining_tries")
	@Min(value = 0)
	@Setter
	private byte failedAttempt;

	@Column(name = "remember_me")
	@Setter
	private boolean rememberMe;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "status_id")
	@NotNull
	@Setter
	private AccountStatus status;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "role_id")
	@ReadOnlyProperty
	private AccountRole role;

	@Column(name = "creation_date", columnDefinition = "TIMESTAMP")
	@ReadOnlyProperty
	private LocalDateTime creationDate;

	@OneToMany(fetch = LAZY, mappedBy = "account")
	@Immutable
	private Set<AccountHasAuthorization> authorizations = new HashSet<>();

	@Column(name = "totp")
	@Setter
	private String totp;

	@Column(name = "totp_expiration_time")
	@Setter
	private LocalDateTime totpExpiration;

	@Column(name = "totp_ip")
	@Setter
	private String totpIp;

	@Column(name = "latest_edit")
	@UpdateTimestamp
	private LocalDateTime latestEdit;

	private String latestSucessIpV4;
	private String latestSucessIpV6;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(final Object obj) {

		final Account other;

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		other = (Account) obj;
		return id != null && Objects.equals(id, other.id);
	}

}