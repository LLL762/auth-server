package com.delacasa.auth.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Immutable;
import org.springframework.data.annotation.ReadOnlyProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the account_role database table.
 * 
 */
@Entity
@Table(name = "account_role")
@Immutable
@Getter
@NoArgsConstructor
public class AccountRole {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "name", unique = true, nullable = false)
	@NotBlank
	private String name;

	/**
	 * 
	 */
	@Lob
	@Basic(fetch = LAZY)
	@Column(name = "description", unique = false, nullable = true)
	private String description;

	/**
	 * Uni-directionnal on self.
	 */
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "parent_id", nullable = true)
	private AccountRole parent;

	/**
	 * Bi-directionnal one to many ,not owning side, uncommon usage. Could be empty.
	 * 
	 */
	@OneToMany(fetch = LAZY, mappedBy = "role")
	@ReadOnlyProperty
	@Immutable
	private Set<Account> accounts = new HashSet<>();

	public AccountRole(final String name, final String description) {
		this.description = description;
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountRole other = (AccountRole) obj;
		return id != null && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "AccountRole [id=" + id + ", name=" + name + ", parent_id=" + parent.getId() + "]";
	}

}