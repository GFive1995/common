package com.communication.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 
 * 服务端：异步IO
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月25日 下午4:46:28
 */
public class SocketServer7 {

	private static final Object waitObject = new Object();
	
	public static void main(String[] args) throws Exception {
		ExecutorService threadPool = Executors.newFixedThreadPool(20);
		AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(threadPool);
		final AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open(group);
		
		// 设置要监听的端口 "0.0.0.0" 代表本机所有IP设备
		serverSocket.bind(new InetSocketAddress("0.0.0.0", 83));
		// 为AsynchronousServerSocketChannel注册监听，注意只是为AsynchronousServerSocketChannel通道注册监听
		// 并不包括为随后客户端和服务器 socketChannel 通道注册监听
		serverSocket.accept(null, new ServerSocketChannelHandle(serverSocket));
		
		//等待，观察现象
		synchronized (waitObject) {
			waitObject.wait();
		}
	}
	
}

/**
 * 
 * 响应 ServerSocketChannel 事件
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月25日 下午5:22:40
 */
class ServerSocketChannelHandle implements CompletionHandler<AsynchronousSocketChannel, Void> {

	private AsynchronousServerSocketChannel serverSocketChannel;
	
	public ServerSocketChannelHandle(AsynchronousServerSocketChannel serverSocketChannel) {
		this.serverSocketChannel = serverSocketChannel;
	}
	
	// 我们分别观察 this、socketChannel、attachment三个对象的id。
	// 来观察不同客户端连接到达时，这三个对象的变化，以说明ServerSocketChannelHandle的监听模式
	public void completed(AsynchronousSocketChannel socketChannel, Void attachment) {
		System.out.println("completed(AsynchronousSocketChannel result, ByteBuffer attachment)");
		// 每次都要重新注册监听（一次注册，一次响应），但是由于“文件状态标示符”是独享的，所以不需要担心有“漏掉的”事件
        this.serverSocketChannel.accept(attachment, this);
        
        // 为这个新的socketChannel注册"read"事件，以便操作系统在收到数据并准备好后，主动通知应用程序
        // 在这里，由于我们要将这个客户端多次传输的数据累加起来一起处理，所以我们将一个stringbuffer对象作为一个“附件”依附在这个channel上
        ByteBuffer readBuffer = ByteBuffer.allocate(50);
        socketChannel.read(readBuffer, new StringBuffer(), new SocketChannelReadHandle(socketChannel , readBuffer));		
	}
	
	public void failed(Throwable exc, Void attachment) {
		System.out.println("failed(Throwable exc, ByteBuffer attachment)");
	}

}

/**
 * 
 * 负责对每一个socketChannel的数据获取事件进行监听。
 * 
 * 重要的说明：一个socketchannel都会有一个独立工作的SocketChannelReadHandle对象（CompletionHandler接口的实现），
 * 其中又都将独享一个“文件状态标示”对象FileDescriptor、
 * 一个独立的由程序员定义的Buffer缓存（这里我们使用的是ByteBuffer）、
 * 所以不用担心在服务器端会出现“窜对象”这种情况，因为JAVA AIO框架已经帮您组织好了。<p>
 * 
 * 但是最重要的，用于生成channel的对象：AsynchronousChannelProvider是单例模式，无论在哪组socketchannel，
 * 对是一个对象引用（但这没关系，因为您不会直接操作这个AsynchronousChannelProvider对象）。
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月25日 下午5:44:00
 */
class SocketChannelReadHandle implements CompletionHandler<Integer, StringBuffer> {

    private AsynchronousSocketChannel socketChannel;

    // 专门用于进行这个通道数据缓存操作的ByteBuffer，也可以作为CompletionHandler的attachment形式传入
    // 这是，在这段示例代码中，attachment被我们用来记录所有传送过来的Stringbuffer了。
    private ByteBuffer byteBuffer;

    public SocketChannelReadHandle(AsynchronousSocketChannel socketChannel , ByteBuffer byteBuffer) {
        this.socketChannel = socketChannel;
        this.byteBuffer = byteBuffer;
    }

    public void completed(Integer result, StringBuffer historyContext) {
        //如果条件成立，说明客户端主动终止了TCP套接字，这时服务端终止就可以了
        if(result == -1) {
            try {
                this.socketChannel.close();
            } catch (IOException e) {
            	e.printStackTrace();
            }
            return;
        }

        System.out.println("completed(Integer result, Void attachment) : 然后我们来取出通道中准备好的值");
        // 实际上，由于从Integer result知道了本次channel从操作系统获取数据总长度
        // 所以实际上，不需要切换成“读模式”的，但是为了保证编码的规范性，还是建议进行切换。
        // 另外，无论是JAVA AIO框架还是JAVA NIO框架，都会出现“buffer的总容量”小于“当前从操作系统获取到的总数据量”，
        // 但区别是，JAVA AIO框架中，我们不需要专门考虑处理这样的情况，因为JAVA AIO框架已经帮我们做了处理（做成了多次通知）
        this.byteBuffer.flip();
        byte[] contexts = new byte[1024];
        this.byteBuffer.get(contexts, 0, result);
        this.byteBuffer.clear();
        try {
            String nowContent = new String(contexts , 0 , result , "UTF-8");
            historyContext.append(nowContent);
            System.out.println("================目前的传输结果：" + historyContext);
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        }

        //如果条件成立，说明还没有接收到“结束标记”
        if(historyContext.indexOf("over") == -1) {
            return;
        }

        System.out.println("=======收到完整信息，开始处理业务=========");
        historyContext = new StringBuffer();

        //还要继续监听（一次监听一次通知）
        this.socketChannel.read(this.byteBuffer, historyContext, this);
    }

    public void failed(Throwable exc, StringBuffer historyContext) {
    	System.out.println("=====发现客户端异常关闭，服务器将关闭TCP通道");
        try {
            this.socketChannel.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
}
