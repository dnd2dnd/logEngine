package com.example.logengine.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rawSn;

	@Column
	private Timestamp timestamp;

	@Column
	private String filepath;

	@Column
	private String filename;

	@Column
	private String msg;

}
