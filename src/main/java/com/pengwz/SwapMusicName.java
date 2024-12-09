package com.pengwz;

import java.io.File;
import java.util.ArrayList;

public class SwapMusicName {
    //将歌手-music name 调整为 music name - 歌手
    public static void main(String[] args) {
        swapMusic("/Users/rocky/Music/TG_副本");
    }

    private static void swapMusic(String filePath) {
        ArrayList<File> fileList = new ArrayList<>();
        //找到文件相同的  但是文件名不同
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File f : files) {
            if (!f.isDirectory()) {
                fileList.add(f);
            }
        }
        ArrayList<File> errFileList = new ArrayList<>();
        System.out.println("歌曲数量：" + fileList.size());
        for (File file1 : fileList) {
            System.out.println("<== 处理：" + file1.getName());
            int i = file1.getName().lastIndexOf(".");
            String subName = file1.getName().substring(0, i);
            String[] strings = subName.replace(" ", "").split("-");
            if (strings.length != 2) {
                errFileList.add(file1);
                continue;
            }
            //名字对换
            String newName = strings[1] + " - " + strings[0] + file1.getName().substring(i);
            File newFile = new File(file1.getParent(), newName);
            // 重命名文件
            if (file1.renameTo(newFile)) {
                System.out.println("==> 完成写入：" + newName);
            } else {
                System.out.println("！！！ 文件重命名失败：" + file1.getName());
                errFileList.add(file1);
            }
        }
        System.out.println("没有对调名字的歌曲数量：" + errFileList.size());
        errFileList.forEach(System.out::println);
    }

}
