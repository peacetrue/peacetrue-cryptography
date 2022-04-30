package com.github.peacetrue.digest;

/**
 * 摘要者。
 *
 * @author peace
 */
public interface Digester<T> {

    /**
     * 生成消息摘要。
     *
     * @param message 消息
     * @return 摘要
     */
    T digest(T message);

    /**
     * 验证摘要是否匹配消息。
     *
     * @param message 消息
     * @param digest  摘要
     * @return true 如果匹配
     */
    boolean match(T message, T digest);

}
