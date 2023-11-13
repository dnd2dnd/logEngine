package com.example.logengine.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Getter
@Entity
public class FilterInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer filterSn;

	@Column
	private String fileName;

	@Column
	private String level;

	@Column
	private String msg;
}
