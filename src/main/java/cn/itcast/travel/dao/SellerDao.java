package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
     * 通过id找到商家
     *
     * @param sid 商家的id sellerId
     * @return 商家对象
     */
    Seller getSellerById(int sid);
}
