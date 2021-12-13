package cn.com.xuxiaowei.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link HttpSession} {@link RestController}
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@RestController
@RequestMapping("/session")
public class SessionRestController {

    /**
     * 获取 Session ID
     *
     * @param request  请求
     * @param response 响应
     * @return 返回 数据
     */
    @RequestMapping
    public Map<String, Object> index(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<>(4);

        HttpSession session = request.getSession();
        map.put("sessionId", session.getId());

        return map;
    }

    /**
     * 获取 Session ID
     *
     * @param request  请求
     * @param response 响应
     * @param session  Session
     * @return 返回 数据
     */
    @RequestMapping("/auto")
    public Map<String, Object> index(HttpServletRequest request, HttpServletResponse response,
                                     HttpSession session) {

        Map<String, Object> map = new HashMap<>(4);

        map.put("sessionId", session.getId());

        return map;
    }

}
