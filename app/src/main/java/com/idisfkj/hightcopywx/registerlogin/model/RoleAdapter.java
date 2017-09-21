package com.idisfkj.hightcopywx.registerlogin.model;

import com.idisfkj.hightcopywx.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fvelement on 2017/9/12.
 */

public class RoleAdapter {
    public static List<Map<String, Object>> getListMap(){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleImg", R.drawable.me_);
        map.put("roleName", "宝贝");
        map.put("roleID", "baby");
        list.add(map);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("roleImg", R.drawable.me);
        map2.put("roleName", "家长");
        map2.put("roleID", "parent");
        list.add(map2);


        return list;

    }
}
