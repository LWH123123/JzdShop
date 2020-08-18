package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author lwh
 * @description :
 * @date 2020/4/21.
 */
public class HomeBannerEntity {
    private List<BannEntity> bannEntities;


    public HomeBannerEntity(List<BannEntity> bannEntities) {
        this.bannEntities = bannEntities;
    }

    public List<BannEntity> getBannEntities() {
        return bannEntities;
    }

    public void setBannEntities(List<BannEntity> bannEntities) {
        this.bannEntities = bannEntities;
    }
}
