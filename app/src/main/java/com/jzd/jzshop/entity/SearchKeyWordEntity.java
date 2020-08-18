package com.jzd.jzshop.entity;

import java.util.List;

/**
 * Created by lxb on 2020/2/15.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class SearchKeyWordEntity {
    private String tip;
    private List<String> keys;

    public SearchKeyWordEntity(String tip, List<String> keys) {
        this.tip = tip;
        this.keys = keys;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }
}
