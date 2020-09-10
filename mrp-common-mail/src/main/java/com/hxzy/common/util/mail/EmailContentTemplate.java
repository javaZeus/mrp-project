package com.hxzy.common.util.mail;


import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 邮件内容的模板方法
 */
@Log4j2
public abstract class EmailContentTemplate {
    //锁唯一的对象
    private final static Object  lockObj=new Object();
    /**
     * 邮件内容
     */
    private  StringBuilder emailCotent=new StringBuilder();

    /**
     * 邮件内容是否读完
     */
    private boolean readComplete=false;

    /**
     * 邮件内容
     * @return
     */
    public abstract String getEmailContent() throws IOException;

    /**
     * 邮件内容流
     * @return
     */
    protected abstract InputStream  fileInputStream();


    /**
     * 取得邮件的内容
     * @return
     */
    private void  readEmailContent() throws IOException {
         log.debug("----------读取邮件操作-------");
        //InputStream -->  BufferReader  字节流  转换成 字符流
        BufferedReader br=new BufferedReader( new InputStreamReader(fileInputStream(),"UTF-8"));
        String content="";
        while((content=br.readLine())!=null){
            emailCotent.append(content);
        }
        br.close();
        //设定标识为已读完
        this.readComplete=true;
    }

    /**
     * 取得邮件的内容
     * @return
     */
    protected  String checkAndGetEmailContent() throws IOException {
        //到底什么才叫做读完了 (保证只读取一次)
       if(!readComplete){
           synchronized (lockObj){
               if(!readComplete){
                   this.readEmailContent();
               }
           }
       }
       return this.emailCotent.toString();
    }

}
