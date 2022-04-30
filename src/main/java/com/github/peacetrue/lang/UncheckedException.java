package com.github.peacetrue.lang;

/**
 * 非受检异常，用于封装一个受检异常。
 * <p>
 * 有些方法抛出了受检异常，
 * 实际上我们知道并不会发生该异常，
 * 此时可以使用本异常封装。
 * <p>
 * 一个最典型的案例是 {@link Cloneable}：
 *
 *     <pre>
 *     public static class TestBean implements Cloneable {
 *         public TestBean clone() {
 *             try {
 *                 return (TestBean)super.clone();
 *             } catch (CloneNotSupportedException e) {
 *                 // 已经实现了 Cloneable，不会抛出 CloneNotSupportedException，但必须捕获
 *                 throw new UncheckedException(e);
 *             }
 *         }
 *     }
 *     </pre>
 *
 * @author peace
 */
public class UncheckedException extends RuntimeException {

    public UncheckedException(Throwable cause) {
        super(cause);
    }
}
