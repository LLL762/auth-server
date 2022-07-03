package com.delacasa.auth.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Immutable;

import lombok.Getter;

/**
 * The persistent class for the resource database table.
 * 
 */
@Entity
@Immutable
@Getter
public class AppResource {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;

	@Lob
	private String description;

	private String name;

}