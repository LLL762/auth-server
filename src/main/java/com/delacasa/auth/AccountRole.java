package com.delacasa.auth;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the account_role database table.
 * 
 */
@Entity
@Table(name="account_role")
@NamedQuery(name="AccountRole.findAll", query="SELECT a FROM AccountRole a")
public class AccountRole  {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String description;

	private String name;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="accountRole")
	private Set<Account> accounts;

	//bi-directional many-to-one association to AccountRole
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_id")
	private AccountRole accountRole;

	//bi-directional many-to-one association to AccountRole
	@OneToMany(mappedBy="accountRole")
	private Set<AccountRole> accountRoles;

	public AccountRole() {
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
		account.setAccountRole(this);

		return account;
	}

	public Account removeAccount(Account account) {
		getAccounts().remove(account);
		account.setAccountRole(null);

		return account;
	}

	public AccountRole getAccountRole() {
		return this.accountRole;
	}

	public void setAccountRole(AccountRole accountRole) {
		this.accountRole = accountRole;
	}

	public Set<AccountRole> getAccountRoles() {
		return this.accountRoles;
	}

	public void setAccountRoles(Set<AccountRole> accountRoles) {
		this.accountRoles = accountRoles;
	}

	public AccountRole addAccountRole(AccountRole accountRole) {
		getAccountRoles().add(accountRole);
		accountRole.setAccountRole(this);

		return accountRole;
	}

	public AccountRole removeAccountRole(AccountRole accountRole) {
		getAccountRoles().remove(accountRole);
		accountRole.setAccountRole(null);

		return accountRole;
	}

}