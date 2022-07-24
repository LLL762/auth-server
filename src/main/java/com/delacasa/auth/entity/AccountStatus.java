package com.delacasa.auth.entity;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The persistent class for the account_status database table.
 * 
 */
@Entity
@Table(name = "account_status")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Immutable
@ToString
public class AccountStatus {

	@Id
	@GeneratedValue(strategy = AUTO)
	private int id;

//	@Lob
//	@Basic(fetch = LAZY)
//	private String description;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

}