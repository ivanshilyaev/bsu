package com.ivanshilyaev.rsa.service;

import com.ivanshilyaev.rsa.exception.RsaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RsaUtilsTest {

    private RsaUtils rsaUtils;

    @BeforeEach
    public void setUp() {
        rsaUtils = new RsaUtils();
    }

    @Test
    @DisplayName("Extended Euclidean algorithm")
    public void extendedEuclideanAlgorithmTest() throws RsaException {
        BigInteger result = rsaUtils.extendedEuclideanAlgorithm(new BigInteger("20"), new BigInteger("3"));
        assertEquals(new BigInteger("7"), result);
        result = rsaUtils.extendedEuclideanAlgorithm(new BigInteger("5783236356223"), new BigInteger("3246757"));
        assertEquals(new BigInteger("4522528282808"), result);
    }

    @Test
    @DisplayName("Exponentiation modulo")
    public void modPowTest() {
        BigInteger result = rsaUtils.modPow(new BigInteger("2"), new BigInteger("2"), new BigInteger("3"));
        assertEquals(new BigInteger("1"), result);
        result = rsaUtils.modPow(new BigInteger("3"), new BigInteger("618970019642690137449562110"),
            new BigInteger("618970019642690137449562111"));
        assertEquals(new BigInteger("1"), result);
    }

    @Test
    @DisplayName("Fermat primality test")
    public void fermatPrimalityTest() {
        BigInteger n = rsaUtils.genPrimeJava(512);
        assertTrue(rsaUtils.fermatPrimalityTest(n));
        assertFalse(rsaUtils.fermatPrimalityTest(n.add(BigInteger.ONE)));
    }
}
