package com.example.logengine;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.Test;


public class qwer {
	@Test
	public void test() {
		Instant beforeTime = Instant.now();
		System.out.println(beforeTime);
		int cnt=0;
		for(int i=0; i<100000; i++) {
			cnt++;
		}
		System.out.println(cnt);
		System.out.println(Duration.between(beforeTime, Instant.now()).toNanos());


		beforeTime = Instant.now();
		System.out.println(beforeTime);
		int cnt2=0;
		for(int i=0; i<10; i++) {
			for(int j=0; j<10000; j++) {
				cnt2++;
			}
		}
		System.out.println(cnt2);
		System.out.println(Duration.between(beforeTime, Instant.now()).toNanos());
	}
}
