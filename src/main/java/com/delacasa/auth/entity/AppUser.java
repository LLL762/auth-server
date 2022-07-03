package com.delacasa.auth.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Immutable;

import lombok.Getter;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Immutable
@Getter
public class AppUser {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;

	@Lob
	private String description;

	private String name;

	// bi-directional one-to-one association to Account
	@OneToOne(fetch = LAZY)
	private Account account;

}