package com.github.peacetrue.practice;

import com.github.peacetrue.codec.Codec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author peace
 **/
class MD5Test {

    @Test
    void paddingLength() {
        Assertions.assertEquals(54, MD5.paddingLength(1));
    }

    @Test
    void intToBytes() {
        byte[] bytes = new byte[8];
        Arrays.fill(bytes, (byte) 0);
        bytes[0] = 1;
        Assertions.assertArrayEquals(bytes, MD5.intToBytes(1, MD5.INPUT_LENGTH));
    }

    @Test
    void hash() {
        MD5 md5 = new MD5();
        Assertions.assertEquals("1055D3E698D289F2AF8663725127BD4B", Codec.HEX.encode(md5.hash("The quick brown fox jumps over the lazy cog".getBytes(StandardCharsets.UTF_8))));
    }

    void toBinaryString() {
        IntStream.rangeClosed(0, Byte.MAX_VALUE).forEach(item -> {
            System.out.printf("%s: %s", item, Integer.toBinaryString(item));
            System.out.println();
        });

        System.out.println((byte) 1 << 7);
        System.out.println(Integer.toBinaryString(1 << 7));
    }
}
