package com.io.mio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


/**
 * 
 * 服务端：多路复用IO
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月25日 下午2:48:45
 */
public class SocketServer5 {

	public static void main(String[] args) throws Exception {
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		ServerSocket serverSocket = serverChannel.socket();
		serverSocket.setReuseAddress(true);
		serverSocket.bind(new InetSocketAddress(83));
		
		Selector selector = Selector.open();
		// 服务器通道只能注册SelectionKey.OP_ACCEPT事件
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		try {
			while(true) {
				// 如果条件成立，说明本次询问selector，并没有获取到任何准备好的，感兴趣的事件
				// Java程序对多路复用IO的支持也包括了阻塞模式和非阻塞模式两种
				if (selector.select(100) == 0) {
					// 业务
					continue;
				}
				// 这里就是本次询问操作系统，所获取的“所关心的事件”的事件类型（每一个通道都是独立的）
				Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
				
				while(selectionKeys.hasNext()) {
					SelectionKey readyKey = selectionKeys.next();
					// 处理的readyKey需要删除
					selectionKeys.remove();
					
					SelectableChannel selectableChannel = readyKey.channel();
					if (readyKey.isValid() && readyKey.isAcceptable()) {
						System.out.println("=====channel通道已经准备好=====");
						// 当server socket channel通道已经准备好，就可以从server socket channel中获取socketchannel了
						// 拿到socket channel后，要做的事情就是马上到selector注册这个socket channel感兴趣的事情
						// 否则无法监听到这个socket channel到达的数据
						ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectableChannel;
						SocketChannel socketChannel = serverSocketChannel.accept();
						registerSocketChannel(socketChannel , selector);
					} else if (readyKey.isValid() && readyKey.isConnectable()) {
						System.out.println("=====socket channel 建立连接=====");
					} else if (readyKey.isValid() && readyKey.isReadable()) {
						System.out.println("=====socket channel 数据准备完成，可以取数据=====");
						readSocketChannel(readyKey);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			 serverSocket.close();
		}
	}
	
	/**
	 * 
	 * 在server socket channel接收到/准备好一个新的TCP连接后会向程序返回一个新的socketChannel
	 * 但是这个新的socket channel并没有在selector"选择器/代理器"中注册
	 * 所以程序没法通过selector通知这个socket channel的事件
	 * 拿到新的socket channel后，要做的第一件事情就是到selector"选择器/代理器"中注册这个socket channel感兴趣的事件
	 * 
	 * @param socketChannel	新的socket channel
	 * @param selector		selector "选择器/代理器"
	 * @throws Exception
	 * 
	 * @author wangcy
	 * @date 2019年6月25日 下午3:23:22
	 */
	private static void registerSocketChannel(SocketChannel socketChannel, Selector selector) throws Exception {
		socketChannel.configureBlocking(false);
		// socket通道可以且只可以注册三种事件SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT
		socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(2048));
	}
	
	/**
	 * 
	 * 这个方法用于读取从客户端传来的信息。
	 * 并且观察从客户端过来的socket channel在经过多次传输后，是否完成传输。
	 * 如果传输完成，则返回一个true的标记。
	 *
	 * @param readyKey
	 * @throws Exception
	 * 
	 * @author wangcy
	 * @date 2019年6月25日 下午3:41:37
	 */
	private static void readSocketChannel(SelectionKey readyKey) throws Exception {
		SocketChannel clientSocketChannel = (SocketChannel) readyKey.channel();
		// 获取客户端使用的端口
		InetSocketAddress sourceSocketAddress = (InetSocketAddress) clientSocketChannel.getRemoteAddress();
		Integer resoucePort = sourceSocketAddress.getPort();
		// 拿到这个socket channel使用的缓存区，准备读数据
		ByteBuffer contextBytes = (ByteBuffer) readyKey.attachment();
		// 将通道的数据写入到缓存区
		// 由于之前设置了ByteBuffer的大小为2048byte，所以可以存在写入不完的情况
		int realLen = -1;
		try {
			realLen = clientSocketChannel.read(contextBytes);
		} catch (Exception e) {
			clientSocketChannel.close();
			return;
		}
		// 如果缓存区没有任何数据
		if (realLen == -1) {
			System.out.println("=====缓存区没有数据？=====");
			return;
		}
        //将缓存区从写状态切换为读状态（实际上这个方法是读写模式互切换）。
        //这是java nio框架中的这个socket channel的写请求将全部等待。
        contextBytes.flip();
        //注意中文乱码的问题，进行解编码。
        byte[] messageBytes = contextBytes.array();
        String messageEncode = new String(messageBytes, "UTF-8");
        String message = URLDecoder.decode(messageEncode, "UTF-8");
        
        // 如果收到了"over"关键字，才会清空buffer，并返回数据
        // 否则不清空缓存，还要还原buffer的"写状态"
        if (message.indexOf("over") != -1) {
        	// 清空读取的缓存，从新切换Wie写状态
        	contextBytes.clear();
        	System.out.println("端口:" + resoucePort + "客户端发来的信息======message : " + message);
        	//回发数据，并关闭channel
            ByteBuffer sendBuffer = ByteBuffer.wrap(URLEncoder.encode("回发处理结果", "UTF-8").getBytes());
            clientSocketChannel.write(sendBuffer);
            clientSocketChannel.close();
        } else {
        	System.out.println("端口:" + resoucePort + "客户端信息还未接受完，继续接受======message : " + message);
        	contextBytes.position(realLen);
        	contextBytes.limit(contextBytes.capacity());
        }
	}
}
