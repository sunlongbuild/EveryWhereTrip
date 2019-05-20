package com.jiyun.everywheretrip.bean.country;

import com.jiyun.everywheretrip.utils.Cn2Spell;

/**
 * Created by $sl on 2019/5/20 9:07.
 */
public class CityBean implements Comparable<CityBean>{
    private String name; // 姓名
    private String pinyin; // 姓名对应的拼音
    private String firstLetter; // 拼音的首字母

    public CityBean() {
    }

    public CityBean(String name) {
        this.name = name;
        pinyin = Cn2Spell.getPinYin(name); // 根据姓名获取拼音
        firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            firstLetter = "#";
        }
    }

    public String getName() {
        return name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getFirstLetter() {
        return firstLetter;
    }


    @Override
    public int compareTo(CityBean cityBean) {
        if (firstLetter.equals("#") && !cityBean.getFirstLetter().equals("#")) {
            return 1;
        } else if (!firstLetter.equals("#") && cityBean.getFirstLetter().equals("#")) {
            return -1;
        } else {
            return pinyin.compareToIgnoreCase(cityBean.getPinyin());
        }
    }
}
