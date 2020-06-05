package top.fols.box.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import top.fols.box.io.XStream;

/**
 * simple socket tool
 */
public class XSocket {
	public static final String SSLCONTEXT_PROTOCOL_SSL = "SSL";
	
	
	
	public XSocket() {
	}


	/**
	 * 设置 https 请求证书
	 * 自定义的认证管理类
	 */
	public static class MySimpleTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }
        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }
        public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
            return;
        }
        public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
            return;
        }
    }




	/**
	 * 获取上下文
	 */
	public static SSLContext createSSLContext() throws NoSuchAlgorithmException, KeyManagementException {
		return createSSLContext(XSocket.SSLCONTEXT_PROTOCOL_SSL);
	}
	public static SSLContext createSSLContext(String protocol) throws NoSuchAlgorithmException, KeyManagementException {
		TrustManager myTrustManager[] = { new MySimpleTrustManager() };
		return createSSLContext(protocol, null, myTrustManager);
	}


	public static SSLContext createSSLContext(KeyManager[] km, TrustManager[] tm) throws NoSuchAlgorithmException, KeyManagementException {
		return createSSLContext(XSocket.SSLCONTEXT_PROTOCOL_SSL, km, tm);
	}
	public static SSLContext createSSLContext(String protocol, KeyManager[] km, TrustManager[] tm) throws NoSuchAlgorithmException, KeyManagementException {
//		File keystore = new File("/root/IdeaProjects/JohnTest/keys/https.keystore");
//		String password = "john";
//		
//		KeyStore keyStore = KeyStore.getInstance("JKS");
//		keyStore.load(new FileInputStream(keyStore), password.toCharArray());
//		
//		// Create key manager
//		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
//		keyManagerFactory.init(keyStore, password.toCharArray());
//		KeyManager[] km = keyManagerFactory.getKeyManagers();
//		// Create trust manager
//		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
//		trustManagerFactory.init(keyStore);
//		TrustManager[] tm = trustManagerFactory.getTrustManagers();
		
		
		// 得到上下文
		//SSLContext sc = SSLContext.getDefault();
		SSLContext sc = SSLContext.getInstance(protocol);
		// 初始化
		sc.init(km, tm, new SecureRandom());
		return sc;
	}






	public static Socket createSSLSocket(SSLContext sslContext) throws IOException  {
		SSLSocketFactory factory = sslContext.getSocketFactory();
		Socket socket = factory.createSocket();
		return socket;
	}
	public static Socket createSocket() {
		Socket socket = new Socket();
		return socket;
	}


	public static SSLServerSocket createSSLServerSocket(SSLContext sslContext) throws IOException  {
		// Create server socket factory
		SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
		// Create server socket
		SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket();
        return sslServerSocket;
	}
	public static ServerSocket createServerSocket() throws IOException {
		ServerSocket serverSocket = new ServerSocket();
		return serverSocket;
	}


	public static void socketConnect(Socket socket, 
									 String ipAddres, int port,
									 int connectOutTime, 
									 int soOutTime) throws SocketException, IOException {
		socket.setSoTimeout(soOutTime);
		socket.connect(new InetSocketAddress(ipAddres, port), 
			connectOutTime);
	}

	public static void socketHandshake(SSLSocket socket) throws IOException {
		SSLSocket sslSocket = socket;

		// set enabled Cipher Suites
		sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());
		// start handshake
		sslSocket.startHandshake();
		// get session after the connection is established
		// SSLSession sslSession = sslSocket.getSession();

//		System.out.println(String.format(
//				"SSLSession :[Protocol: %s, CipherSuite: %s]",
//				sslSession.getProtocol(),
//				sslSession.getCipherSuite())
//		);

	}






	/**
     * 设置 https 请求
     */
//	public static void trustAllHttpsCertificates(SSLSocketFactory factory) {
//		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//				public boolean verify(String str, SSLSession session) {
//					return true;
//				}
//			});
//		HttpsURLConnection.setDefaultSSLSocketFactory(factory);
//	}


	public static String getLocalHostHostAddres() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}

	public static boolean tryClose(Socket s) {
		boolean b = true;
		try {
			s.shutdownInput();
		} catch (Throwable e) {
			b = b & false;
		}
		try {
			s.shutdownOutput();
		} catch (Throwable e) {
			b = b & false;
		}
		try {
			XStream.tryClose(s.getInputStream());
		} catch (Throwable e) {
			b = b & false;
		}
		try {
			XStream.tryClose(s.getOutputStream());
		} catch (Throwable e) {
			b = b & false;
		}
		try {
			XStream.tryClose(s);
		} catch (Throwable e) {
			b = b & false;
		}
		return b;
	}
}
