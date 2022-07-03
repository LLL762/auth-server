package com.delacasa.auth.entity;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Getter;

/**
 * The persistent class for the account_has_authorization database table.
 * 
 */
@Entity
@Table(name = "account_has_authorization")
@Immutable
@Getter
public class AccountHasAuthorization {

	@EmbeddedId
	private AccountHasAuthorizationPK id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "authorization_id")
	@MapsId("authorizationId")
	private AccountAuthorization authorization;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "account_id")
	@MapsId("accountId")
	private Account account;

	@Column(name = "is_restriction")
	private boolean isRestriction;

}