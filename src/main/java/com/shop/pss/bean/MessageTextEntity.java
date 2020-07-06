package com.shop.pss.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author 王瑞
 * @description
 * @date 2019/2/21 10:25
 */
public class MessageTextEntity {

    @XStreamAlias("ToUserName")
    private String toUserName;
    @XStreamAlias("FromUserName")
    private String fromUserName;
    //由于微信服务端需要的时间整形是以秒为单位的，故需要除以1000L
    // this.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
    @XStreamAlias("CreateTime")
    private String createTime;
    @XStreamAlias("MsgType")
    private String msgType;
    @XStreamAlias("Content")
    private String content;
    @XStreamAlias("MsgId")
    private String msgId;

    public MessageTextEntity() {
    }

    @Override
    public String toString() {
        return "MessageTextEntity{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", msgType='" + msgType + '\'' +
                ", content='" + content + '\'' +
                ", msgId='" + msgId + '\'' +
                '}';
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * get msgId
     *
     * @return java.lang.String msgId
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * set msgId
     *
     * @param msgId
     */

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}

