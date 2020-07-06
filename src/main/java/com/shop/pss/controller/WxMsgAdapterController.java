package com.shop.pss.controller;

import com.shop.pss.exception.AesException;
import com.shop.pss.util.SHA1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王瑞
 * @description
 * @create 2018-12-27 16:14
 **/
@RestController
@RequestMapping("/pss/api/wechat")
public class WxMsgAdapterController<T> extends BaseController<T> {

    @Value("${wechat.serviceToken}")
    private String serviceToken;

    /**
     * 用于接收微信公众号配置的服务器域名token验证，本服务会通过在微信服务号上配置好的域名和80端口和微信接口进行通讯。
     * 若微信服务器域名校验不在本服务上（借用了其他和微信服务号通讯的服务）
     * 先开发后台token验证服务（利用微信提供的SHA1加密算法是对三个参数（token，接口参数timestamp，接口参数nonce）进行加密,若加密结果和入参signature相同，则返回入参echostr;则表示时微信发来的消息
     */
    @GetMapping("/wechataccess")
    public String getSuiteToken(@RequestParam(name = "signature", required = false) String signature,
                                @RequestParam(name = "timestamp", required = false) String timestamp,
                                @RequestParam(name = "nonce", required = false) String nonce,
                                @RequestParam(name = "echostr", required = false) String echostr) {
        String jiami = "";
        try {
            jiami = SHA1.getSHA1(serviceToken, timestamp, nonce, "");//这里调用微信提供的SHA1加密算法是对三个参数进行加密
        } catch (AesException e) {
            logger.error(e.getMessage());
        }
        logger.info("加密" + jiami);
        logger.info("本身" + signature);
        if (jiami.equals(signature))
            return echostr;
        return null;
    }

    /**
     * 启用并设置服务器配置后，用户发给公众号的消息以及开发者需要的事件推送，将被微信转发到该URL中。
     * 说白了就是作为开发者你要定制公众号功能，不想直接微信提供的标准处理消息的模板。设置成功了URL和token后，后面用户发送给你的公众号的数据会先发到微信平台，
     * 然后平台转发给我们提供的URL，我们就在这个URL里来定制各种操作，如果需要返回消息给用户，再通过微信平台转发给用户即可。

    @PostMapping(value = "/wechataccess" ,produces = {"application/json;charset=UTF-8"})
    public String adapterMsg(@RequestBody String requestBody) {
        logger.info(requestBody);
        if (requestBody.contains("<MsgType><![CDATA[text]]></MsgType>")) {
            MessageTextEntity entity = WeChatUtil.xmlToBean(requestBody, MessageTextEntity.class);
            System.out.println(entity);

            MessageTextEntity res = new MessageTextEntity();
            res.setToUserName(entity.getFromUserName());
            res.setFromUserName(entity.getToUserName());
        } else if (requestBody.contains("<MsgType><![CDATA[event]]></MsgType>")) {
            return "哈哈哈，你点我啦!";
        }
        return requestBody;
    }
     */

}
