package com.example.learn1;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHelper {

    private Context mContext;

    public FileHelper(){}

    public FileHelper(Context mContext)
    {
        super();
        this.mContext = mContext;
    }

    //文件写入的方法
    public void saveFile(String fileName, String fileContext) throws Exception
    {
        FileOutputStream output = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
        output.write(fileContext.getBytes());
        //关闭输入流
        output.close();
    }

    //文件读取的方法
    public String readFile(String fileName) throws IOException
    {
        FileInputStream input = mContext.openFileInput(fileName);
        byte[] temp = new byte[1024];
        StringBuilder sb = new StringBuilder("");
        int len = 0;
        //读取文件内容:
        while ((len = input.read(temp)) > 0) {
            sb.append(new String(temp, 0, len));
        }
        //关闭输入流
        input.close();
        return sb.toString();
    }

}
