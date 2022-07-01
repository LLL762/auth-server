package com.delacasa.auth;

import static javax.persistence.FetchType.LAZY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Getter
@Setter
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "email")
	@Email
	private String email;

	@Column(name = "has_multi_factor_auth")
	private boolean hasMultiFactorAuth;

	@Column(name = "password")
	@NotBlank
	@Size(min = 255, max = 255)
	private String password;

	@Column(name = "phone")
	@NotBlank
	@Size(min = 4, max = 18)
	private String phone;

	@Column(name = "remaining_tries")
	@Min(value = 0)
	private byte remainingTries;

	@Column(name = "remember_me")
	private boolean rememberMe;

	@Column(name = "username")
	@Size(min = 4, max = 255)
	private String username;

	// bi-directional many-to-one association to AccountRole
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "role_id")
	private AccountRole accountRole;

	// bi-directional many-to-one association to AccountStatus
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "status_id")
	@NotNull
	private AccountStatus accountStatus;

}