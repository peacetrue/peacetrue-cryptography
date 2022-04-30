package com.github.peacetrue.digest;

import com.github.peacetrue.codec.Codec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author peace
 **/
class DigesterTest {

    @Test
    void digest() {
        digest(StringDigester.MD5, "1", "C4CA4238A0B923820DCC509A6F75849B");
        digest(StringDigester.SHA256, "1", "6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B");
        digest(StringDigester.SHA512, "1", "4DFF4EA340F0A823F15D3F4F01AB62EAE0E5DA579CCB851F8DB9DFE84C58B2B37B89903A740E1EE172DA793A6E79D560E5F7F9BD058A12A280433ED6FA46510A");
        StringDigester hmacSHA256 = new StringDigester(HmacDigester.buildHmacSHA256(Codec.CHARSET_UTF8.decode("123456")));
        digest(hmacSHA256, "baeldung", "5B50D80C7DC7AE8BB1B1433CC0B99ECD2AC8397A555C6F75CB8A619AE35A0C35");
    }

    private void digest(StringDigester stringDigester, String message, String expected) {
        Assertions.assertEquals(expected, stringDigester.digest(message));
        Assertions.assertTrue(stringDigester.match(message, stringDigester.digest(message)));
    }


}
