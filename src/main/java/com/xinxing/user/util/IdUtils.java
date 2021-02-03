package com.xinxing.user.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdUtils {
    private final Long workerId;
    private final Long centerId;
    private long startTimeMillis = 1596816000000L;
    public static final long CENTER_ID_BITS = 6L;
    private static final long CENTER_ID_MAX = (1L << CENTER_ID_BITS) - 1L;
    private static final long WORKER_ID_BITS = 8L;
    private static final long WORKER_ID_MAX = (1L << WORKER_ID_BITS) - 1L;
    private static final long SEQUENCE_BITS = 10L;
    private static final long SEQUENCE_MAX = (1L << SEQUENCE_BITS) - 1L;
    private static final long WORKER_ID_LEFT_SHIFT = SEQUENCE_BITS;
    private static final long CENTER_ID_LEFT_SHIFT = WORKER_ID_LEFT_SHIFT + WORKER_ID_BITS;
    private static final long TIMESTAMP_LEFT_SHIFT = CENTER_ID_LEFT_SHIFT + CENTER_ID_BITS;
    private static long lastTimestamp = -1L;
    private long sequence = 0L;

    private IdUtils() {
        workerId = 1L;
        centerId = 2L;
    }

    private static IdUtils instance;

    public static IdUtils getInstance() {
        if (instance == null) {
            synchronized (IdUtils.class) {
                if (instance == null) {
                    instance = new IdUtils();
                }
            }
        }
        return instance;
    }

    public Long generate() {
        return nextId();
    }

    private synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            log.error("时光倒流" + (lastTimestamp - timestamp) + " 秒，拒绝生成ID");
        }
        if (lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1L) & SEQUENCE_MAX;
            if (this.sequence == 0L) {
                timestamp = nextMillis(lastTimestamp);
            }
        } else {
            this.sequence = 0L;
        }
        lastTimestamp = timestamp;

        return ((timestamp - this.startTimeMillis) << TIMESTAMP_LEFT_SHIFT)
                | (centerId << CENTER_ID_LEFT_SHIFT)
                | (workerId << WORKER_ID_LEFT_SHIFT)
                | this.sequence;

    }

    private long nextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
