package com.liu.config.log4j2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author w
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    /**
     * 执行处理程序之前的拦截点。在 HandlerMapping 确定适当的处理程序对象之后，但在 HandlerAdapter 调用处理程序之前调用。
     * DispatcherServlet 在执行链中处理处理程序，该执行链由任意数量的拦截器组成，处理程序本身位于末尾。使用此方法，
     * 每个拦截器都可以决定中止执行链，通常发送 HTTP 错误或编写自定义响应。
     * 注意： 异步请求处理需要特别注意。有关更多详细信息，请参阅
     * {@link org.springframework.web.servlet.AsyncHandlerInterceptor}.
     *
     * @param request  当前 HTTP 请求
     * @param response 当前 HTTP 响应
     * @param handler  选择要执行的处理程序，用于类型和/ 或实例评估
     * @return 执行链是否应该继续下一个拦截器或处理程序本身。否则，DispatcherServlet 假定此拦截器已经处理了响应本身。
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        // 日志id
        ThreadContext.put("logId", UuidUtil.getTimeBasedUuid().toString());
        return true;
    }

    /**
     * 请求处理完成后的回调，即渲染视图后。将在处理程序执行的任何结果上调用，从而允许进行适当的资源清理。
     * 注意：仅当此拦截器的方法 preHandle 成功完成并返回 true！
     * postHandle与该方法一样，该方法将以相反的顺序在链中的每个拦截器上调用，因此第一个拦截器将是最后一个被调用的
     * 注意： 异步请求处理需要特别注意。有关更多详细信息，请参阅
     * {@link org.springframework.web.servlet.AsyncHandlerInterceptor}.
     *
     * @param request  当前 HTTP 请求
     * @param response 当前 HTTP 响应
     * @param handler  启动异步执行的处理程序（或 {@link HandlerMethod})，用于类型和/ 或实例检查
     * @param ex       处理程序执行时抛出的任何异常（如果有）; 这不包括已通过异常解析程序处理的异常
     *                 include exceptions that have been handled through an exception resolver
     */
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, Exception ex) {
        ThreadContext.clearAll();
    }
}
