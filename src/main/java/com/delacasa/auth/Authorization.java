package com.delacasa.auth;

import static javax.persistence.FetchType.LAZY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Authorization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Lob
	private String description;

	private String name;

	@OneToMany(fetch = LAZY)
	@JoinColumn(name = "authorization-id")
	private Set<AppResource> resources = new HashSet<>();

	public void addResource(final AppResource resource) {

		resources.add(resource);

	}

	public void removeResource(final AppResource resource) {

		resources.remove(resource);

	}

}