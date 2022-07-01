package com.delacasa.auth;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the account_has_authorization database table.
 * 
 */
@Embeddable
public class AccountHasAuthorizationPK {

	@Column(name = "authorization_id", insertable = false, updatable = false)
	private int authorizationId;

	@Column(name = "account_id", insertable = false, updatable = false)
	private int accountId;

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccountHasAuthorizationPK)) {
			return false;
		}
		AccountHasAuthorizationPK castOther = (AccountHasAuthorizationPK) other;
		return (this.authorizationId == castOther.authorizationId) && (this.accountId == castOther.accountId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.authorizationId;
		hash = hash * prime + this.accountId;

		return hash;
	}
}