package cn.com.xuxiaowei.controller;

import cn.com.xuxiaowei.utils.rsa.Rsa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA {@link RestController}
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/rsa")
public class RsaRestController {

    /**
     * 获取 RSA 公钥
     *
     * @param request  请求
     * @param response 响应
     * @param session  Session
     * @return 返回 数据
     */
    @RequestMapping("/get-public-key")
    public Map<String, Object> getPublicKey(HttpServletRequest request, HttpServletResponse response,
                                            HttpSession session) {

        Map<String, Object> map = new HashMap<>(4);

        Rsa.StringKey stringKey = Rsa.getStringKey();
        String publicKey = stringKey.getPublicKey();
        String privateKey = stringKey.getPrivateKey();

        session.setAttribute("RSA_PRIVATE_KEY", privateKey);

        map.put("publicKey", publicKey);

        return map;
    }

    /**
     * 解密数据
     *
     * @param request  请求
     * @param response 响应
     * @param session  Session
     * @param encrypt     密文
     * @return 返回 数据
     */
    @RequestMapping("/decrypt")
    public Map<String, Object> index(HttpServletRequest request, HttpServletResponse response,
                                     HttpSession session, String encrypt) {

        Map<String, Object> map = new HashMap<>(4);

        String rsaPrivateKey = (String) session.getAttribute("RSA_PRIVATE_KEY");

        try {
            String decrypt = Rsa.privateKeyDecrypt(rsaPrivateKey, encrypt);
            map.put("decrypt", decrypt);
        } catch (InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchPaddingException e) {
            log.error("解密失败", e);
            map.put("msg", "解密失败：" + e.getMessage());
        }

        return map;
    }

}
