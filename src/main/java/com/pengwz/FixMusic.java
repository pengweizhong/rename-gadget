package com.pengwz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FixMusic {

    public static void main(String[] args) {
        scanMusic("C:\\Users\\Administrator\\OneDrive\\音乐\\Music");
//        String name1 = "龙的传人 - Namewee, 小熊为你(1).m4a";
//        String name2 = "龙的传人 - Namewee, 小熊为你.m4a";
//        String reg = ".*\\(\\d+\\).*";
//        System.out.println(name1.matches(reg));
//        System.out.println(name2.matches(reg));
    }

    public static void scanMusic(String filePath) {
        ArrayList<File> fileList = new ArrayList<>();
        //找到文件相同的  但是文件名不同
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File f : files) {
            if (!f.isDirectory()) {
                fileList.add(f);
            }
        }
        System.out.println("歌曲数量：" + fileList.size());
        String reg = ".*\\(\\d+\\).*";
        String reg2 = ".*\\s\\d+\\..*";
        List<File> collect = fileList.stream().filter(f -> {
            return f.getName().matches(reg) || f.getName().matches(reg2);
        }).collect(Collectors.toList());
        for (File file1 : collect) {
            System.out.println("----------- 匹配到的字符串 === " + file1.getName());
        }
        ArrayList<String> musicNames = new ArrayList<>();
        ArrayList<String> musicSingers = new ArrayList<>();
        for (File music : fileList) {
            String fileName = music.getName();
            String[] split = fileName.split("-");
            if (split.length != 2) {
                System.out.println("多个-先不用管========== " + fileName);
                continue;
            }
            String musicName = split[0].trim();
            String singerName = split[1].trim();
            int lastIndexOf = singerName.lastIndexOf(".");
            singerName = singerName.substring(0, lastIndexOf);
            musicNames.add(musicName);
            musicSingers.add(singerName);
        }
        System.out.println("==========================");
        System.out.println("==========================");
        System.out.println("==========================");
        System.out.println("==========================");
        System.out.println("==========================");
        System.out.println("==========================");
        System.out.println("==========================");
        System.out.println("==========================");
        System.out.println("==========================");
        System.out.println("==========================");
        //要求的格式  歌曲名称 - 歌手.后缀
        //先找到歌曲名称 不是真正的歌曲名的
        //先找到所有相同的歌曲
        for (File music : fileList) {
            String fileName = music.getName();
            String[] split = fileName.split("-");
            if (split.length != 2) {
                continue;
            }
            String musicName = split[0].trim();
            String singerName = split[1].trim();
            if (/*musicNames.contains(musicName) && */musicSingers.contains(musicName)) {
                System.out.println("可能不是歌曲名：【" + musicName + "】 , 文件：【" + fileName+"】");
            }
        }
    }

}
