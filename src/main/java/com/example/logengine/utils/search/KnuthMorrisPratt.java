package com.example.logengine.utils.search;

import javax.validation.constraints.NotNull;

public class KnuthMorrisPratt implements SearchString {

	@Override
	public boolean find(@NotNull String text, @NotNull String pattern) {
		int pos = 0;
		pos = kmpSearch(text.toCharArray(), pattern.toCharArray());
		if (pos < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * KMP 알고리즘
	 * @param pattern   검색하고자 하는 pattern
	 * @param text      검색대상이 되는 text
	 * @return 찾으면 0 보다 큰 값을 리턴
	 */
	public int kmpSearch(char[] text, char[] pattern) {
		int patternSize = pattern.length;
		int textSize = text.length;

		int i = 0, j = 0;
		int[] shift = knuthMorrisPrattShift(pattern);

		while ((i + patternSize) <= textSize) {
			while (text[i + j] == pattern[j]) {
				j += 1;
				if (j >= patternSize)
					return i;
			}

			if (j > 0) {
				i += shift[j - 1];
				j = Math.max(j - shift[j - 1], 0);
			} else {
				i++;
				j = 0;
			}
		}
		return -1;
	}

	private int[] knuthMorrisPrattShift(char[] pattern) {
		int patternSize = pattern.length;

		int[] shift = new int[patternSize];
		shift[0] = 1;

		int i = 1, j = 0;
		while ((i + j) < patternSize) {
			if (pattern[i + j] == pattern[j]) {
				shift[i + j] = i;
				j++;
			} else {
				if (j == 0)
					shift[i] = i + 1;

				if (j > 0) {
					i = i + shift[j - 1];
					j = Math.max(j - shift[j - 1], 0);
				} else {
					i = i + 1;
					j = 0;
				}
			}
		}
		return shift;
	}
}
