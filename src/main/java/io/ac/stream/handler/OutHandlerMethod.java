package io.ac.stream.handler;

/**
 * 输出消息处理
 *
 * @author eguid
 * @version 2017年10月13日
 * @since jdk1.7
 */
public interface OutHandlerMethod {
    /**
     * 解析消息
     *
     * @param id-任务ID
     * @param msg     -消息
     */
    void parse(String id, String msg);

    /**
     * 任务是否异常中断
     *
     * @return
     */
    boolean isbroken();
}
