package com.delacasa.auth;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the resource database table.
 * 
 */
@Entity
@Getter
@Setter
public class AppResource {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Lob
	private String description;

	private String name;

}