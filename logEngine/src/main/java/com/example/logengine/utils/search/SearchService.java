package com.example.logengine.utils.search;

import org.springframework.stereotype.Component;

@Component
public class SearchService {
	private SearchString searchString;

	public void setAlgorithm(String algorithm) {
		if (SearchConstants.KMP.equals(algorithm)) {
			this.searchString = new KnuthMorrisPratt();
		} else if (SearchConstants.BOYER_MOORE.equals(algorithm)) {
			this.searchString = new BoyerMoore();
		} else if (SearchConstants.RABIN_KARP.equals(algorithm)) {
			this.searchString = new RabinKarp();
		} else {
			this.searchString = null;
		}
	}

	public boolean find(String text, String pattern) {
		if (!this.searchString.find(text, pattern)) {
			return false;
		} else {
			return true;
		}
	}
}
