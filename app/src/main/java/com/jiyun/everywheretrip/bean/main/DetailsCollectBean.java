package com.jiyun.everywheretrip.bean.main;

/**
 * Created by $sl on 2019/5/14 14:49.
 */
public class DetailsCollectBean {
    /**
     * code : 0
     * desc : 收藏成功
     * result : {}
     */

    private int code;
    private String desc;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
    }
}
