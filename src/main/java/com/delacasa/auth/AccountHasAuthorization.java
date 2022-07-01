package com.delacasa.auth;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the account_has_authorization database table.
 * 
 */
@Entity
@Table(name = "account_has_authorization")
@Getter
@Setter
public class AccountHasAuthorization {

	@EmbeddedId
	private AccountHasAuthorizationPK id;

	@ManyToOne
	@JoinColumn
	@MapsId("resourceId")
	private AppResource resource;

	@ManyToOne
	@JoinColumn
	@MapsId("accountId")
	private Account account;

	private boolean isRestriction;

}