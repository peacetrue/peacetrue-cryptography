/**
 * cryptology：密码学
 * <p>
 * 加密/解密：
 * <ul>
 *     <li>动词：encrypt and decrypt</li>
 *     <li>名词：Encryption and decryption</li>
 *     <li>名词：Encryptor and Decryptor</li>
 * </ul>
 * <p>
 * 所有方法的参数和返回值默认都不能为空，如果可以为空，使用 {@link javax.annotation.Nullable} 标注。
 *
 * @author peace
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html">JDK 8 密码规范</a>
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html">JDK 8 密码算法</a>
 * @see <a href="https://blog.csdn.net/fanxiaobin577328725/article/details/51713624">java 之 jce</a>
 * @see <a href="https://www.bejson.com/enc/rsa/">在线工具</a>
 **/
@NonNullApi
package com.github.peacetrue.crypto;

import org.springframework.lang.NonNullApi;
