package com.delacasa.auth;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Getter
@Setter
public class AppUser {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;

	@Lob
	private String description;

	private String name;

	// bi-directional one-to-one association to Account
	@OneToOne(fetch = LAZY, cascade = { MERGE, REMOVE })
	@JoinColumn(name = "user-id")
	private Account account;

}