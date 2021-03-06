package com.shop.pss.common;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

/**
 * @author 王瑞
 * @description 生成主键的公共类
 * @date 2018/2/2 9:33
 */
public class IdGen {

    private final static long twepoch = 12888349746579L;
    // 机器标识位数
    private final static long workerIdBits = 5L;
    // 数据中心标识位数
    private final static long datacenterIdBits = 5L;

    // 毫秒内自增位数
    private final static long sequenceBits = 12L;
    // 机器ID偏左移12位
    private final static long workerIdShift = sequenceBits;
    // 数据中心ID左移17位
    private final static long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间毫秒左移22位
    private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    //sequence掩码，确保sequnce不会超出上限
    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);
    //上次时间戳
    private static long lastTimestamp = -1L;
    //序列
    private long sequence = 0L;
    //服务器ID
    private long workerId = 1L;
    private static long workerMask = -1L ^ (-1L << workerIdBits);
    //进程编码
    private long processId = 1L;
    private static long processMask = -1L ^ (-1L << datacenterIdBits);
    private static IdGen IdGen = null;

    static {
        IdGen = new IdGen();
    }

    public static synchronized long nextId() {
        return IdGen.getNextId();
    }


    private IdGen() {
        //获取机器编码
        this.workerId = this.getMachineNum();
        //获取进程编码
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        this.processId = Long.parseLong(runtimeMXBean.getName().split("@")[0]);

        //避免编码超出最大值
        this.workerId = workerId & workerMask;
        this.processId = processId & processMask;
    }


    /**
     * 生成随机字符串
     *
     * @param size 字段长度
     * @return
     */
    public static String uuid(int size) {
        final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
                'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
                'Z'};
        Random random = new Random();
        //生成长度为size的随机字符串
        char[] cs = new char[size];
        for (int i = 0; i < cs.length; i++) {
            cs[i] = digits[random.nextInt(digits.length)];
        }
        return new String(cs);
    }

    public synchronized long getNextId() {
        //获取时间戳
        long timestamp = timeGen();
        //如果时间戳小于上次时间戳则报错
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //如果时间戳与上次时间戳相同
        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1，与sequenceMask确保sequence不会超出上限
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        //lastTimestamp = timestamp;
        IdGen.setLastTimestamp(timestamp);
        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - twepoch) << timestampLeftShift) | (processId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
        return nextId;
    }

    /**
     * 再次获取时间戳直到获取的时间戳与现有的不同
     *
     * @return 下一个时间戳
     */
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private static long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 获取机器编码
     *
     * @return
     */
    private long getMachineNum() {
        long machinePiece = 0;
        StringBuilder sb = new StringBuilder();
        Enumeration<NetworkInterface> e = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e1) {
            e1.printStackTrace();
        }
        if (null != e) {
            while (e.hasMoreElements()) {
                NetworkInterface ni = e.nextElement();
                sb.append(ni.toString());
            }
            machinePiece = sb.toString().hashCode();
        }
        return machinePiece;
    }

    public static long idGen() {
        return nextId();
    }


    public static void setLastTimestamp(long lastTimestamp) {
        com.shop.pss.common.IdGen.lastTimestamp = lastTimestamp;
    }
}
