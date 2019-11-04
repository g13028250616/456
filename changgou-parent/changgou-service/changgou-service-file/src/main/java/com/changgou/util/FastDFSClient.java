package com.changgou.util;


import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FastDFSClient {

    /**
     * 初始化tracker信息
     */
    static {
        //获取tracker的配置文件fdfs_client.conf的位置
        String path = new ClassPathResource("fdfs_client.conf").getPath();
        //加载tracker配置信息
        try {
            ClientGlobal.init(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     */

    public static String[] upload(FastDFSFile file) {
        try {
            StorageClient storageClient = getStorageClient();
            //获取文件作者
//            NameValuePair[] meta_list = new NameValuePair[1];
//            meta_list[0] = new NameValuePair(file.getAuthor());
            NameValuePair[] mata_list = {new NameValuePair(file.getAuthor()), new NameValuePair(file.getName())};

            //参数1 字节数组
            //参数2 扩展名(不带点)
            //参数3 元数据( 文件的大小,文件的作者,文件的创建时间戳)

            String[] strings = storageClient.upload_file(file.getContent(), file.getExt(), mata_list);
            return strings;//// strings[0]==group1  strings[1]=M00/00/00/wKjThF1aW9CAOUJGAAClQrJOYvs424.jpg
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    /**
     * 获取文件信息
     * @param groupName  指定组名
     * @param remoteFileName  文件储存完整名
     * @return
     */
    public static FileInfo getFile(String groupName,String remoteFileName){
        try {
            //创建trackerclien对象
            StorageClient storageClient = getStorageClient();
            //获取文件信息
            return storageClient.get_file_info(groupName,remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *文件下载
     * @param groupName 组名
     * @param remoteFileName 文件存储完整名
     * @return
     */
    public static InputStream downFile(String groupName,String remoteFileName){
        ByteArrayInputStream byteArrayInputStream=null;
        try {
            //创建trackerclien对象
            StorageClient storageClient = getStorageClient();
            //文件下载
            byte[] bytes = storageClient.download_file(groupName, remoteFileName);
            //写流
           byteArrayInputStream = new ByteArrayInputStream(bytes);
            return byteArrayInputStream;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (byteArrayInputStream!=null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 删除文件
     * @param groupName
     * @param remoteFileName
     */
  private static void delete(String groupName,String remoteFileName){
      try {
          //创建trackerclien对象
          StorageClient storageClient = getStorageClient();
          //删除文件
          int i = storageClient.delete_file(groupName, remoteFileName);
          if (i==0) {
              System.out.println("删除成功");
          }else {
              System.out.println("删除失败");
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
  }


    /**
     * 根据组名获取组的信息
     * @param groupName
     * @return
     */
  public static StorageServer getStorage(String groupName){
      try {
          //创建trackerclient对象
          TrackerClient trackerClient=new TrackerClient();
          //通过trackerclient获取trackerserv信息
          TrackerServer trackerServer = trackerClient.getConnection();
          //创建storageclient对象
          StorageServer group1 = trackerClient.getStoreStorage(trackerServer, groupName);
          return group1;
      } catch (IOException e) {
          e.printStackTrace();
      }
      return null;
  }


    /**
     * 根据文件名和组名获取文件的信息
     * @param groupName
     * @param remoteFileName
     * @return
     */
  public static FileInfo getInfo(String groupName,String remoteFileName){
      try {
          //创建trackerclient对象
          StorageClient storageClient = getStorageClient();
          //参数1 指定组名
          //参数2 指定文件的路径
          FileInfo fileinfo = storageClient.get_file_info(groupName, remoteFileName);
          return fileinfo;
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }

    /**
     * ServerInfo
     * @param groupName   组名
     * @param remoteFileName   文件储存完整名
     * @return
     */
  public static ServerInfo[] getServerInfo(String groupName,String remoteFileName){

      try {
          //创建trackerclient对象
          TrackerClient trackerClient=new TrackerClient();
          //通过trackerclient获取trackerserv信息
          TrackerServer trackerServer = trackerClient.getConnection();
          //创建storageclient对象
          ServerInfo[] group1s = trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
          return group1s;
      } catch (IOException e) {
          e.printStackTrace();
      }
    return null;
  }

    /**
     * 获取tracker 的ip和端口的信息
     * @return
     */
  public static String getTrackerUrl(){
      try {
          //创建trackerclient对象
          TrackerServer trackerServer = getTrackerServer();
          //travker的ip
          String hostString = trackerServer.getInetSocketAddress().getHostString();
          //tracker的端口号
          int g_tracker_http_port = ClientGlobal.getG_tracker_http_port();
          return "Http://"+hostString+":"+g_tracker_http_port;
      } catch (IOException e) {
          e.printStackTrace();
      }
      return null;
  }



  //优化

    /**
     * 获取TrackerServer
     * @return
     * @throws IOException
     */
    private static TrackerServer getTrackerServer() throws IOException {
        //创建trackerclien对象
        TrackerClient trackerClient = new TrackerClient();
        //通过trackerc获取trackerserver信息
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    /**
     * 获取StorageClient
     * @return
     * @throws IOException
     */
    private static StorageClient getStorageClient() throws IOException {
        //获取travkerserver对象
        TrackerServer trackerServer = getTrackerServer();

        //通过TrackerServer创建StorageClient
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }
}
