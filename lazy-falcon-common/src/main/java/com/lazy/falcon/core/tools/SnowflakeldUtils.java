package com.lazy.falcon.core.tools;


import com.lazy.falcon.core.helper.SnowflakeIdWorkerHelper;

import java.util.List;

/**
 * @author laizhiyuan
 * @date 2017/12/31.
 * <p>生成snowflake id工具类</p>
 */
public final class SnowflakeldUtils {

    private static final SnowflakeIdWorkerHelper SNOWFLAKE_ID_WORKER_HELPER = new SnowflakeIdWorkerHelper(0, 0);

    /**
     * 下一个id
     * @return id
     */
    public static long nextId(){
        return SNOWFLAKE_ID_WORKER_HELPER.nextId();
    }

    /**
     * 批量生成id
     * @param num 数量
     * @return id集合
     */
    public static List<Long> batchGenId(int num){
        if (num < 1){
            throw new RuntimeException("num required greater than 0");
        }
        List<Long> ids = SetsUtils.list(num);
        while (num > 0){
            ids.add(nextId());
            num -= 1;
        }
        return ids;
    }

    public static void main(String[] args) {
        System.out.println(nextId());
        System.out.println(batchGenId(10));
    }
}
