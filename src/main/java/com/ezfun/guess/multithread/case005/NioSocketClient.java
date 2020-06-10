package com.ezfun.guess.multithread.case005;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author SoySauce
 * @date 2019/10/31
 */
public class NioSocketClient {
    private Selector selector;
    private static ExecutorService pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder().setNameFormat("heavy-socket-pool-%d").build());
    //sleep 1秒 TimeUnit.SECONDS.toNanos(1);
    private static final int SLEEP_TIME = 1000 * 1000 * 1000;
    private static Map<Socket, Long> time_stat = new HashMap<>(10240);

    /**
     * 初始化
     *
     * @param ip
     * @param port
     * @throws IOException
     */
    public void init(String ip, int port) throws IOException {
        //创建一个SocketChannel实例,并设置为非阻塞模式
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        //创建一个selector
        this.selector = SelectorProvider.provider().openSelector();
        //将SocketChannel绑定到Socket上.但由于当前Channel是非阻塞的,因此connect方法返回时,连接并不一定建立成功
        //在后续使用这个连接时,还需要使用finishConnect()再次确认
        channel.connect(new InetSocketAddress(ip, port));
        //将这个Chanl和selector进行绑定,并注册了感兴趣的事件 为连接(OP_CONNECT)
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void working() throws IOException {
        while (true) {
            if (!selector.isOpen()) {
                break;
            }
            selector.select();
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                //连接事件发生
                if (key.isConnectable()) {
                    connect(key);
                } else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        //创建读取的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = channel.read(byteBuffer);
        byte[] data = byteBuffer.array();
        String msg = new String(data).trim();
        System.out.println("客户端收到信息: " + msg);
        channel.close();
        key.selector().close();

    }

    private void connect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        //如果正在连接,则完成连接
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        channel.write(ByteBuffer.wrap("hello server!\r\n".getBytes()));
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 1; i++) {
            NioSocketClient client = new NioSocketClient();
            client.init("10.68.177.96",8000);
            client.working();
        }
    }


}
