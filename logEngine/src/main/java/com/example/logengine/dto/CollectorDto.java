package com.example.logengine.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectorDto {
	@JsonProperty(value = "@timestamp")
	private Long timestamp;
	private String hostname;
	private String product;
	private String filename;
	private String log;

	@Override
	public String toString() {
		return "CollectorDto{" +
			"timestamp=" + timestamp +
			", hostname='" + hostname + '\'' +
			", product='" + product + '\'' +
			", filename='" + filename + '\'' +
			", log='" + log + '\'' +
			'}';
	}
}
