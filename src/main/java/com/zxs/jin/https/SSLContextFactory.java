package com.zxs.jin.https;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

public class SSLContextFactory {
	public static SSLContext getSslContext() throws Exception {
        char[] passArray = "123456".toCharArray();
        SSLContext sslContext = SSLContext.getInstance("SSLv3");
        KeyStore ks = KeyStore.getInstance("JKS");
        //加载keytool 生成的文件
        FileInputStream inputStream = new FileInputStream("d://test//netty.keystore");
        ks.load(inputStream, passArray);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, passArray);
        sslContext.init(kmf.getKeyManagers(), null, null);
        inputStream.close();
        return sslContext;

    }
}
