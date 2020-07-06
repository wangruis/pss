package com.shop.pss.util;

import com.alibaba.druid.util.StringUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.Writer;
import java.net.URL;

/**
 * @author 王瑞
 * @description
 * @date 2019/2/21 10:24
 */
public class WeChatUtil {

    /**
     * xml转成bean泛型方法
     *
     * @param resultXml
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(String resultXml, Class clazz) {
        // XStream对象设置默认安全防护，同时设置允许的类
        XStream stream = new XStream(new DomDriver());
//        XStream.setupDefaultSecurity(stream);
//        stream.allowTypes(new Class[]{clazz});
        stream.processAnnotations(new Class[]{clazz});
//        stream.setMode(XStream.NO_REFERENCES);
        stream.alias("xml", clazz);
        return (T) stream.fromXML(resultXml);
    }

    //xstream扩展,bean转xml自动加上![CDATA[]]
    public static XStream getMyXStream() {
        return new XStream(new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    // 对所有xml节点都增加CDATA标记
                    boolean cdata = true;

                    @Override
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                    }

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (cdata) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });
    }


    /**
     * bean转成微信的xml消息格式
     *
     * @param object
     * @return
     */
    public static String beanToXml(Object object) {
        XStream xStream = getMyXStream();
        xStream.alias("xml", object.getClass());
        xStream.processAnnotations(object.getClass());
        String xml = xStream.toXML(object);
        if (!StringUtils.isEmpty(xml)) {
            return xml;
        } else {
            return null;
        }
    }


    /**
     * 根据经纬度获取地址
     *
     * @param lon 经度
     * @param lat 纬度
     * @return
     */
    public static String getAdd(String lon, String lat) {
        String urlString = "http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=" + lat + "," + lon + "&output=json&pois=1&ak=" + "gQN24IICw1D2MQK4Gli1CnA10DLUZesx";
        StringBuffer res = new StringBuffer();
        try {
            URL url = new URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res.append(line + "\n");
            }
            in.close();
        } catch (Exception e) {
            System.out.println("error in wapaction,and e is " + e.getMessage());
        }
        System.out.println(res);
        return String.valueOf(res);
    }


}
