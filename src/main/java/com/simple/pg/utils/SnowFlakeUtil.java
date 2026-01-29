package com.simple.pg.utils;

/**
 * 雪花算法工具类（线程安全，分布式唯一ID生成）
 * 标准结构：0(1位) + 时间戳(41位) + 数据中心ID(5位) + 机器ID(5位) + 序列号(12位)
 */
public class SnowFlakeUtil {
    // ====================== 算法核心配置常量 ======================
    /** 基准时间（可自定义，建议设置为项目首次部署时间，越近越好，延长使用年限） */
    private static final long EPOCH = 1735689600000L; // 2025-01-01 00:00:00 毫秒数

    /** 数据中心ID的位数：5位，最大支持 2^5 -1 = 31 */
    private static final long DATA_CENTER_BIT = 5;
    /** 机器ID的位数：5位，最大支持 2^5 -1 = 31 */
    private static final long WORKER_BIT = 5;
    /** 序列号的位数：12位，最大支持 2^12 -1 = 4095 */
    private static final long SEQUENCE_BIT = 12;

    // ====================== 最大值计算 ======================
    /** 数据中心ID最大值：31 */
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_BIT);
    /** 机器ID最大值：31 */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_BIT);
    /** 序列号最大值：4095 */
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    // ====================== 位移量计算 ======================
    /** 序列号左移位数：0（最末位） */
    private static final long SEQUENCE_SHIFT = 0;
    /** 机器ID左移位数：12（序列号占12位） */
    private static final long WORKER_SHIFT = SEQUENCE_BIT;
    /** 数据中心ID左移位数：17（机器ID5位+序列号12位） */
    private static final long DATA_CENTER_SHIFT = SEQUENCE_BIT + WORKER_BIT;
    /** 时间戳左移位数：22（数据中心5位+机器ID5位+序列号12位） */
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BIT + WORKER_BIT + DATA_CENTER_BIT;

    // ====================== 实例变量（每个机器唯一） ======================
    private final long dataCenterId; // 数据中心ID
    private final long workerId;     // 机器ID
    private long sequence = 0L;      // 序列号，初始为0
    private long lastTimestamp = -1L;// 上一次生成ID的时间戳，初始为-1

    // ====================== 构造方法（私有化，通过静态方法创建） ======================
    private SnowFlakeUtil(long dataCenterId, long workerId) {
        // 校验数据中心ID和机器ID是否超出范围
        if (dataCenterId < 0 || dataCenterId > MAX_DATA_CENTER_ID) {
            throw new IllegalArgumentException("数据中心ID超出范围，允许范围：0-" + MAX_DATA_CENTER_ID);
        }
        if (workerId < 0 || workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException("机器ID超出范围，允许范围：0-" + MAX_WORKER_ID);
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    // ====================== 单例模式（可选，也可根据机器ID多实例） ======================
    // 这里提供一个默认单例（数据中心ID=1，机器ID=1，实际使用请根据部署机器修改）
    private static class SingletonHolder {
        private static final SnowFlakeUtil INSTANCE = new SnowFlakeUtil(1, 1);
    }

    /**
     * 获取默认单例实例（实际项目中建议根据机器配置动态传入dataCenterId和workerId）
     */
    public static SnowFlakeUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 自定义数据中心ID和机器ID，创建实例
     */
    public static SnowFlakeUtil getInstance(long dataCenterId, long workerId) {
        return new SnowFlakeUtil(dataCenterId, workerId);
    }

    // ====================== 核心方法：生成唯一ID（加锁保证线程安全） ======================
    public synchronized long nextId() {
        // 1. 获取当前毫秒级时间戳
        long currentTimestamp = getCurrentTimestamp();

        // 2. 处理时钟回拨：当前时间 < 上一次生成ID的时间，说明时钟回拨，抛出异常（分布式环境禁止时钟回拨）
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("时钟回拨异常，拒绝生成ID！上一次时间：" + lastTimestamp + "，当前时间：" + currentTimestamp);
            // 若需兼容轻微时钟回拨，可替换为：return waitNextMillis(lastTimestamp);
        }

        // 3. 同一毫秒内，序列号自增
        if (currentTimestamp == lastTimestamp) {
            // 序列号自增后与最大值取模，超出则归0（保证不超过4095）
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒序列号耗尽，等待下一个毫秒
            if (sequence == 0L) {
                currentTimestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒，序列号重置为0
            sequence = 0L;
        }

        // 4. 更新上一次生成ID的时间戳
        lastTimestamp = currentTimestamp;

        // 5. 按位拼接生成最终ID
        return (currentTimestamp - EPOCH) << TIMESTAMP_SHIFT // 时间戳部分
                | dataCenterId << DATA_CENTER_SHIFT          // 数据中心ID部分
                | workerId << WORKER_SHIFT                  // 机器ID部分
                | sequence << SEQUENCE_SHIFT;                // 序列号部分
    }

    // ====================== 辅助方法：获取当前毫秒时间戳 ======================
    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    // ====================== 辅助方法：等待下一个毫秒（解决同一毫秒序列号耗尽问题） ======================
    private long waitNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimestamp();
        }
        return timestamp;
    }

    // ====================== 测试主方法 ======================
    public static void main(String[] args) {
        // 测试单例生成ID
        SnowFlakeUtil snowFlake = SnowFlakeUtil.getInstance();
        // 模拟10个线程并发生成1000个ID
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    System.out.println(snowFlake.nextId());
                }
            }).start();
        }
    }
}