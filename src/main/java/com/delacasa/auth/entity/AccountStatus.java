package com.delacasa.auth.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Getter;

/**
 * The persistent class for the account_status database table.
 * 
 */
@Entity
@Table(name = "account_status")
@Getter
@Immutable
public class AccountStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Lob
	private String description;

	private String name;

}