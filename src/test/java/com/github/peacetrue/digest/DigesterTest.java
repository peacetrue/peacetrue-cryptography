package com.github.peacetrue.digest;

import com.github.peacetrue.codec.Codec;
import com.github.peacetrue.lang.UncheckedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author peace
 **/
class DigesterTest {

    @Test
    void digest() {
        byte[] message = "1".getBytes(StandardCharsets.UTF_8);
        Assertions.assertTrue(ByteDigester.MD5.verify(message, ByteDigester.MD5.digest(message)));

        digest(CodecDigester.MD5, "1", "C4CA4238A0B923820DCC509A6F75849B");
        digest(CodecDigester.SHA256, "1", "6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B");
        digest(CodecDigester.SHA512, "1", "4DFF4EA340F0A823F15D3F4F01AB62EAE0E5DA579CCB851F8DB9DFE84C58B2B37B89903A740E1EE172DA793A6E79D560E5F7F9BD058A12A280433ED6FA46510A");
        CodecDigester hmacSHA256 = new CodecDigester(HmacDigester.buildHmacSHA256(Codec.CHARSET_UTF8.decode("123456")));
        digest(hmacSHA256, "baeldung", "5B50D80C7DC7AE8BB1B1433CC0B99ECD2AC8397A555C6F75CB8A619AE35A0C35");

        byte[] bytes = Codec.CHARSET_UTF8.decode("123456");
        Assertions.assertThrows(UncheckedException.class, () -> ByteDigester.digest("MD6", bytes));
        Assertions.assertThrows(UncheckedException.class, () -> new HmacDigester(new SecretKeySpec(bytes, "HmacSHA1024")).digest(bytes));
    }

    private void digest(CodecDigester stringDigester, String message, String expected) {
        Assertions.assertEquals(expected, stringDigester.digest(message));
        Assertions.assertTrue(stringDigester.verify(message, stringDigester.digest(message)));
    }


}
