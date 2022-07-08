package com.delacasa.auth.entity;

import static javax.persistence.FetchType.LAZY;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the authorization database table.
 * 
 */
@Entity
@Getter
@Setter
public class AccountAuthorization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private String description;

	private String name;

	@OneToMany(fetch = LAZY)
	private Set<AppResourceCategory> categories = new HashSet<>();

	public void addResource(final AppResourceCategory category) {

		categories.add(category);

	}

	public void removeResource(final AppResourceCategory category) {

		categories.remove(category);

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
		AccountAuthorization other = (AccountAuthorization) obj;
		return Objects.equals(id, other.id);
	}

}