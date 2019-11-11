package com.zdxt.common.util;

import java.io.File;

public class FileUtils {
    public static void getDelete(File file) {
        //生成File[]数组   listFiles()方法获取当前目录里的文件夹  文件
        File[] files = file.listFiles();
        //判断是否为空   //有没有发现讨论基本一样
        if(files!=null){
            //遍历
            for (File file2 : files) {
                //是文件就删除
                if(file2.isFile()){
                    file2.delete();
                }else if(file2.isDirectory()){
                    //是文件夹就递归
                    getDelete(file2);
                    //空文件夹直接删除
                    file2.delete();
                }
            }
        }

    }
}
