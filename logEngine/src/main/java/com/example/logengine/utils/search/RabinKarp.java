package com.example.logengine.utils.search;

import java.math.BigInteger;
import java.util.Random;

import javax.validation.constraints.NotNull;

import com.example.logengine.utils.search.SearchString;

/**
 * 라빈카프 알고리즘
 */
public class RabinKarp implements SearchString {

    @Override
    public boolean find(@NotNull String text, @NotNull String pattern) {
        int pos = 0;
        pos = RabinKarpMethod(text.toCharArray(), pattern.toCharArray() );
        if (pos < 0) {
            return false;
        } else {
            return true;
        }
    }

    public int RabinKarpMethod(char[] text, char[] pattern) {
        int patternSize = pattern.length;
        int textSize = text.length;

        long prime = getBiggerPrime(patternSize);

        long r = 1;
        for (int i = 0; i < patternSize - 1; i++) {
            r *= 2;
            r = r % prime;
        }

        long[] t = new long[textSize];
        t[0] = 0;

        long pfinger = 0;

        for (int j = 0; j < patternSize; j++) {
            t[0] = (2 * t[0] + text[j]) % prime;
            pfinger = (2 * pfinger + pattern[j]) % prime;
        }

        int i = 0;
        boolean passed = false;

        int diff = textSize - patternSize;
        for (i = 0; i <= diff; i++) {
            if (t[i] == pfinger) {
                passed = true;
                for (int k = 0; k < patternSize; k++) {
                    if (text[i + k] != pattern[k]) {
                        passed = false;
                        break;
                    }
                }

                if (passed) {
                    return i;
                }
            }

            if (i < diff) {
                long value = 2 * (t[i] - r * text[i]) + text[i + patternSize];
                t[i + 1] = ((value % prime) + prime) % prime;
            }
        }
        return -1;
    }

    private long getBiggerPrime(int m) {
        BigInteger prime = BigInteger.probablePrime(getNumberOfBits(m) + 1, new Random());
        return prime.longValue();
    }

    private int getNumberOfBits(int number) {
        return Integer.SIZE - Integer.numberOfLeadingZeros(number);
    }

}
