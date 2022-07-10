package com.delacasa.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Immutable;

/**
 * The primary key class for the account_has_authorization database table.
 * 
 */
@Embeddable
@Immutable
public class AccountHasAuthorizationPK
		implements Serializable {

	private static final long serialVersionUID = 4405919140411314531L;

	@Column(name = "authorization_id", insertable = false, updatable = false)
	private Long authorizationId;

	@Column(name = "account_id", insertable = false, updatable = false)
	private Long accountId;

	@Override
	public boolean equals(final Object obj) {

		final AccountHasAuthorizationPK other;

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		other = (AccountHasAuthorizationPK) obj;
		return (this.authorizationId.equals(other.authorizationId)) && (this.accountId.equals(other.accountId));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;

		hash = hash * prime + (authorizationId == null ? 0 : authorizationId.hashCode());
		hash = hash * prime + (accountId == null ? 0 : accountId.hashCode());

		return hash;
	}
}