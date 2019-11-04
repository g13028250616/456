package com.changgou;

import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;

public class FastdfsClientTest {

    /***
     *
     * 文件上传
     */
    @Test
    public void upload() throws Exception{
        //加载全局的配置文件
        ClientGlobal.init("D:\\IdeaWS\\changgou67\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");
        //创建TrackerClient客户端对象
        TrackerClient trackerClient=new TrackerClient();
        //通过TrackerClient对象获取TrackerServer信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取StorageClient对象
        StorageClient storageClient=new StorageClient(trackerServer,null);
        //执行文件上传
        String[] jpgs = storageClient.upload_file("D:\\360Downloads\\325389.jpg", "123456.jpg", null);
        for (String jpg : jpgs) {
            System.out.println(jpg);
        }

    }

    /**
     * 删除文件
     */
    @Test
    public void delete() throws Exception{
        //加载全局的配置文件
        ClientGlobal.init("D:\\IdeaWS\\changgou67\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");
        //创建TrackerClient客户端对象
        TrackerClient trackerClient=new TrackerClient();
        //通过TrackerClient对象获取TrackerServer信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取StorageClient对象
        StorageClient storageClient=new StorageClient(trackerServer,null);
        //执行删除文件
        int group1 = storageClient.delete_file("group1", "M00/00/00/wKjThF1as7iAL11cAAOVQRw9kO0.123456");
        System.out.println(group1);

    }

    /**
     * 下载文件
     */
    @Test
    public void download()throws Exception{
        //加载全局配置文件
        ClientGlobal.init("D:\\IdeaWS\\changgou67\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");
        //创建TrackerClient客户端对象
        TrackerClient trackerClient=new TrackerClient();
        //通过TrackerClient对象获取TrackerServer信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取StorageClient对象
        StorageClient storageClient=new StorageClient(trackerServer,null);
        //执行下载文件
        byte[] bytes = storageClient.download_file("group1", "M00/00/00/wKjThF1aubiAO5uIAAOVQRw9kO0.123456");
        //写成流
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/12345678.jpg"));
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }


    /**
     * 获取文件的信息数据
     */
@Test
    public void GetFileInfo()throws Exception{
        //加载全局配置文件
        ClientGlobal.init("D:\\IdeaWS\\changgou67\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");
        //创建Trackerclient对象
        TrackerClient trackerClient=new TrackerClient();
        //通过Trackerclient对象获取trackerserver信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取storageclient对象
        StorageClient storageClient=new StorageClient(trackerServer,null);
        //执行文件上传
        FileInfo group1 = storageClient.get_file_info("group1", "M00/00/00/wKjThF1aubiAO5uIAAOVQRw9kO0.123456");
        System.out.println(group1);

    }


    /**
     * 获取组相关的信息
     */
    @Test
    public void GetGroupInfo()throws Exception{
        //加载全局配置文件
        ClientGlobal.init("D:\\IdeaWS\\changgou67\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");
        //创建trackerclient对象
        TrackerClient trackerClient=new TrackerClient();
        //通过trackerclient对象获取trackerserver信息
        TrackerServer trackerServer = trackerClient.getConnection();


        StorageServer group1 = trackerClient.getStoreStorage(trackerServer, "group1");
        System.out.println(group1);

        //组对应的服务器的地址  因为有可能有多个服务器.
        ServerInfo[] group1s = trackerClient.getFetchStorages(trackerServer, "group1", "M00/00/00/wKjThF1aubiAO5uIAAOVQRw9kO0.123456");
        for (ServerInfo serverInfo : group1s) {
            System.out.println(serverInfo.getIpAddr());
            System.out.println(serverInfo.getPort());
        }
    }



    //获取tracker的地址(ip 和端口)
    @Test
    public void getTrackerInfo()throws Exception{
        //加载全局配置文件
        ClientGlobal.init("D:\\IdeaWS\\changgou67\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf");
        //创建trackerclient对象
        TrackerClient trackerClient=new TrackerClient();
        //通过trackerclient对象获取trackerserver信息
        TrackerServer trackerServer = trackerClient.getConnection();
        InetSocketAddress inetSocketAddress = trackerServer.getInetSocketAddress();
        System.out.println(inetSocketAddress);
    }
}
