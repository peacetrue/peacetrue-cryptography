package com.github.peacetrue.signature;

import com.github.peacetrue.lang.UncheckedException;

/**
 * 签名者，用于生成签名和验证签名。
 *
 * @param <T> 待签名消息
 * @param <S> 已签名消息
 * @author peace
 */
public interface Signer<T, S> {

    /**
     * 签名，生成签名。
     *
     * @param toBeSigned 待签名消息
     * @return 已签名消息
     * @throws UncheckedException 生成签名过程中发生异常
     */
    S sign(T toBeSigned) throws UncheckedException;

    /**
     * 验证签名。
     *
     * @param toBeSigned 待签名消息
     * @param signed     已签名消息
     * @return true 如果验证通过，否则 false
     * @throws UncheckedException 验证签名过程中发生异常
     */
    default boolean verify(T toBeSigned, S signed) throws UncheckedException {
        return signed.equals(sign(toBeSigned));
    }

}
