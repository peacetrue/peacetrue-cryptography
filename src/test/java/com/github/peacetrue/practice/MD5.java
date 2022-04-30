package com.github.peacetrue.practice;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * MD5 实现。
 *
 * @author peace
 * @see <a href="https://zh.wikipedia.org/wiki/MD5">wiki</a>
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc1321">rfc1321</a>
 * @see <a href="https://stackoverflow.com/questions/32933254/which-type-of-leftrotation-is-used-by-the-md5-hash-algorithm">rotateLeft</a>
 * @see <a href="https://www.jianshu.com/p/93a8ab5bfeb9">MD5算法原理及实现</a>
 **/
@Slf4j
public class MD5 {

    /** r specifies the per-round shift amounts */
    public static final int[] S = {
            7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
            5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20,
            4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
            6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21
    };

    /** Use binary integer part of the sines of integers as constants: */
    public static final int[] T = IntStream.range(0, 64).map(i -> (int) ((long) (Math.abs(Math.sin(i + 1)) * (long) Math.pow(2, 32)))).toArray();

    /** MD5 初始值 */
    public static final int H0 = 0x67452301, H1 = 0xEFCDAB89, H2 = 0x98BADCFE, H3 = 0x10325476;

    /** 分组字节数 */
    public static final int GROUP_LENGTH = 512 / 8;//64
    /** 追加字节数 */
    public static final int APPENDED_LENGTH = 1;
    /** 内容长度字节数 */
    public static final int INPUT_LENGTH = 64 / 8;//8

    public byte[] hash(byte[] plaintext) {
        byte[] aligned = align(plaintext);
        log.info("aligned input: {}", Arrays.toString(aligned));
        int[] processed = process(aligned);
        log.info("processed input: {}", Arrays.toString(processed));
        return intToBytes(processed);
    }

    private int[] process(byte[] aligned) {
        int[] h = {H0, H1, H2, H3};
        for (int i = 0; i < aligned.length; i = i + GROUP_LENGTH) {
            int[] grouped = group(aligned, i);
            log.info("grouped text: {}", Arrays.toString(grouped));

            int a = h[0], b = h[1], c = h[2], d = h[3], f, g, temp;
            for (int j = 0; j < 64; j++) {
                if (j <= 15) {
                    f = (b & c) | ((~b) & d);
                    g = j;
                } else if (j <= 31) {
                    f = (d & b) | ((~d) & c);
                    g = (5 * j + 1) % 16;
                } else if (j <= 47) {
                    f = b ^ c ^ d;
                    g = (3 * j + 5) % 16;
                } else {
                    f = c ^ (b | (~d));
                    g = (7 * j) % 16;
                }
                temp = d;
                d = c;
                c = b;
                b += rotateLeft(a + f + grouped[g] + T[j], S[j]);
                a = temp;
            }

            h[0] += a;
            h[1] += b;
            h[2] += c;
            h[3] += d;
        }
        return h;
    }

    static byte[] intToBytes(int[] ints) {
        byte[] bytes = new byte[ints.length * 4];
        for (int i = 0; i < ints.length; i++) {
            byte[] partBytes = intToBytesLittle(ints[i]);
            System.arraycopy(partBytes, 0, bytes, i * 4, partBytes.length);
        }
        return bytes;
    }

    static int rotateLeft(int x, int n) {
        return (x << n) | (x >>> (32 - n));
    }

    static byte[] align(byte[] input) {
        log.info("align input: {}", input);
        int inputLength = input.length;
        int paddingLength = paddingLength(inputLength);
        byte[] alignedInput = Arrays.copyOf(input, inputLength + APPENDED_LENGTH + paddingLength + INPUT_LENGTH);
        //append "1" bit to message
        alignedInput[inputLength] = Byte.MIN_VALUE;//10000000
        //append "0" bits until message length in bits ≡ 448 (mod 512)
        Arrays.fill(alignedInput, inputLength + APPENDED_LENGTH, inputLength + APPENDED_LENGTH + paddingLength, (byte) 0);
        //append bit length of message as 64-bit little-endian integer to message
        byte[] inputLengthBytes = intToBytes(inputLength * 8, INPUT_LENGTH);
        System.arraycopy(inputLengthBytes, 0, alignedInput, inputLength + APPENDED_LENGTH + paddingLength, inputLengthBytes.length);
        return alignedInput;
    }

    static int paddingLength(int inputLength) {
        return GROUP_LENGTH - (inputLength % GROUP_LENGTH + APPENDED_LENGTH + INPUT_LENGTH);
    }

    static byte[] reserve(byte[] bytes) {
        //big-endian 大端模式，eg1 -> [0, 0, 0, 1]
        //little-endian 小端模式，eg1 -> [1, 0, 0, 0]
        for (int i = 0; i < bytes.length / 2; i++) {
            byte temp = bytes[i];
            bytes[i] = bytes[bytes.length - i - 1];
            bytes[bytes.length - i - 1] = temp;
        }
        return bytes;
    }

    static byte[] intToBytes(int number, int length) {
        return Arrays.copyOf(intToBytesLittle(number), length);
    }

    private static byte[] intToBytesLittle(int number) {
        return reserve(intToBytes(number));
    }

    static byte[] intToBytes(int number) {
        //int 32 位，byte 8 位，最多 4 个 byte 可存储 int
        //big-endian 大端模式，eg：1 -> [0, 0, 0, 1]
        return ByteBuffer.allocate(4).putInt(number).array();
    }

    private static int[] group(byte[] data, int index) {
        int[] grouped = new int[16];
        for (int i = 0; i < 16; i++) {
            grouped[i] = (data[4 * i + index] & 0xFF) |
                    (data[4 * i + 1 + index] & 0xFF) << 8 |
                    (data[4 * i + 2 + index] & 0xFF) << 16 |
                    (data[4 * i + 3 + index] & 0xFF) << 24;
        }
        return grouped;
    }


}
