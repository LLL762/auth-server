package com.delacasa.auth;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the account_status database table.
 * 
 */
@Entity
@Table(name="account_status")
@NamedQuery(name="AccountStatus.findAll", query="SELECT a FROM AccountStatus a")
public class AccountStatus  {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String description;

	private String name;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="accountStatus")
	private Set<Account> accounts;

	public AccountStatus() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Account addAccount(Account account) {
		getAccounts().add(account);
		account.setAccountStatus(this);

		return account;
	}

	public Account removeAccount(Account account) {
		getAccounts().remove(account);
		account.setAccountStatus(null);

		return account;
	}

}