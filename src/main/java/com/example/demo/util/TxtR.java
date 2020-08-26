package com.example.demo.util;


import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2020/8/4.
 */
public class TxtR {




    public static List<String> readTxtFile(String filePath)
    {
        List paramList = new ArrayList();
        try
        {
            String encoding = "UTF-8";//GBK
            File file = new File(filePath);
            if ((file.isFile()) && (file.exists())) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (!StringUtils.isEmpty(lineTxt)) {
                        paramList.add(lineTxt);
                    }
                    System.out.println("|" + lineTxt + "|");
                }
                read.close();
            } else {
                System.out.println("找不到指定文件！");
            }
            return paramList;
        } catch (Exception e) {
            System.out.println("读取文件内容错误");
            e.printStackTrace();
        }

        return null;
    }





    public static void method2(String file, String conent)
    {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));

            out.write(conent + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
