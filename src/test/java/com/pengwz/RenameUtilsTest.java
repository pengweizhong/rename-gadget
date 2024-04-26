package com.pengwz;


import com.pengwz.utils.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class RenameUtilsTest {

    @Test
    public void testRename() {
        ArrayList<String> objects = new ArrayList<>();
        //kgm/kgma 格式
        String name1 = "one love - 七元.kgma.flac";
        objects.add(name1);
        String name2 = "Town of Windmill (风车小镇) - A_Hisa.kgma.flac";
        objects.add(name2);
        String name3 = "I Miss You - 罗百吉、宝贝.kgma";
        objects.add(name3);
        String name4 = "海市蜃楼 (女声版) - 一颗狼星.kgma.jpg";
        objects.add(name4);
        String name5 = "探窗 (416女团版) - 叶聪明、ya妖精、小淅儿、边靖婷、可爱晨.kgma.flac";
        objects.add(name5);
        String name6 = "认真的雪 - 薛之谦.kgma.lrc";
        objects.add(name6);
        objects.add("png");
        objects.add(".jpeg");
        objects.add("123.jpeg");
        objects.add(".123.jpeg");
        for (String name : objects) {
            if (StringUtils.isEmpty(name)) {
                continue;
            }
            if (name.startsWith(".")) {
                continue;
            }
            String[] split = name.split("\\.");
            if (split.length <= 2) {
                continue;
            }
            StringBuilder newName = new StringBuilder();
            int position = split.length - 2;
            for (int i = 0; i < split.length; i++) {
                String spName = split[i];
                if (i == position) {
                    //kgm/kgma 格式
                    if ("kgm".equalsIgnoreCase(spName) || "kgma".equalsIgnoreCase(spName)) {
                        continue;
                    }
                }
                newName.append(spName).append(".");
            }
            String newFilename = newName.substring(0, newName.length() - 1);
            System.out.println(name + " -----------> " + newFilename);
        }
    }
}