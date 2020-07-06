package com.shop.pss.cache;

import com.shop.pss.bean.Menu;
import com.shop.pss.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王瑞
 * @description
 * @date 2019-03-05 11:00
 */
@Component
public class LcMenuAllCache extends GuavaAbstractLoadingCache<String, List<Menu>> implements ILocalCache<String, List<Menu>> {

    @Autowired
    MenuService menuService;

    private LcMenuAllCache() {
        setMaximumSize(100); //最大缓存条数
    }

    @Override
    public List<Menu> get(String key) {
        try {
            return getValue(key);
        } catch (Exception e) {
            logger.error("无法根据key:{},获取菜单,error:{}", key, e);
            return new ArrayList<>();
        }
    }

    @Override
    public Boolean invalidate(String key) {

        return true;
    }

    @Override
    protected List<Menu> fetchData(String key) {
        return menuService.getAllMenu();
    }


}
