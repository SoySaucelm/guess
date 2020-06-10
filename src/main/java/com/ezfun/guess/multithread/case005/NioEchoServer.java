package com.ezfun.guess.multithread.case005;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author SoySauce
 * @date 2019/10/29
 */
public class NioEchoServer {
    private Selector selector;
    //使用线程池来处理每一个客户端连接
    private static ExecutorService tp = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            6000L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), new BasicThreadFactory.Builder().namingPattern
            ("tp-pool-%d").build());
    private static final Map<Socket, Long> TIME_STAT = new HashMap<>(10240);

    public static class EchoClient {
        /**
         * 封装一个队列,保存需要回复给这个客户端的所有信息
         */
        private LinkedList<ByteBuffer> outQueue;

        public EchoClient() {
            this.outQueue = new LinkedList<>();
        }

        public LinkedList<ByteBuffer> getOutQueue() {
            return outQueue;
        }

        public void enQueue(ByteBuffer bb) {
            outQueue.addFirst(bb);
        }
    }

    /**
     * 由一个客户端Socket构造而成
     */
    class HandleMsg implements Runnable {
        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey sk, ByteBuffer bb) {
            this.sk = sk;
            this.bb = bb;
        }

        @Override
        public void run() {
            EchoClient echoClient = (EchoClient) sk.attachment();
            //将数据压入到echoClient队列 or do other sth
            echoClient.enQueue(bb);
            //将读OP_READ和写OP_WRITE事件作为感兴趣的事件进行提交 这样在通道准备好写入时,就能通知线程
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            //强迫selector立即返回
            selector.wakeup();
        }
    }

    private void startServer() throws IOException {
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
//        InetSocketAddress isa = new InetSocketAddress(8000);
        ssc.socket().bind(isa);
        SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
        for (; ; ) {
            //阻塞方法,如果当前没有任何数据准备好,它就会等待.一旦有数据可读,他就会返回.返回值:已经准备就绪的SelectionKey的数量
            selector.select();
            //获取准备好的SelectionKey
            Set<SelectionKey> readKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readKeys.iterator();
            long e = 0;
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                //将这个元素移除,否则会重复处理
                iterator.remove();
                //判断当前SelectionKey所代表的Channel是否在Acceptable状态
                if (sk.isAcceptable()) {
                    doAccept(sk);
                }
                //判断Channel是否已经可读
                else if (sk.isValid() && sk.isReadable()) {
                    SocketChannel sc = (SocketChannel) sk.channel();
                    if (!TIME_STAT.containsKey(sc.socket())) {
                        TIME_STAT.put(sc.socket(), System.currentTimeMillis());
                        doRead(sk);
                    }
                }
                //判断是否准备好进行写
                else if (sk.isValid() && sk.isWritable()) {
                    doWirte(sk);
                    e = System.currentTimeMillis();
                    long b = TIME_STAT.remove(((SocketChannel) sk.channel()).socket());
                    System.out.println("spend:" + (e - b) + "ms");
                }

            }

        }

    }
    private void doAccept(SelectionKey sk) {
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {
            clientChannel = server.accept();
            clientChannel.configureBlocking(false);
            //Register this channel for reading;
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);
            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connection from " + clientAddress.getHostAddress() + ".");
        } catch (IOException e) {
            System.out.println("Failed to accept new client.");
            e.printStackTrace();
        }
    }

    private void doRead(SelectionKey sk) {
        //得到当前的客户端Channel
        SocketChannel channel = (SocketChannel) sk.channel();
        //准备8k的缓冲区读取数据
        ByteBuffer bb = ByteBuffer.allocate(10);
        int len;
        try {
            len = channel.read(bb);
            if (len < 0) {
                disconnect(sk);
                return;
            }
        } catch (IOException e) {
            System.out.println("Failed to read from client.");
            e.printStackTrace();
            disconnect(sk);
            return;
        }
        //重置缓冲区
        bb.flip();
        tp.execute(new HandleMsg(sk, bb));
    }

    private void doWirte(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoClient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outQueue = echoClient.getOutQueue();
        ByteBuffer bb = outQueue.getLast();
        try {
            int len = channel.write(bb);
            if (len == -1) {
                disconnect(sk);
                return;
            }
            //全部发送完成移除这个缓存对象
            if (bb.remaining() == 0) {
                //the buffer was completely written, remove it
                outQueue.removeLast();
            }
        } catch (IOException e) {
            System.out.println("Failed to wriete to client");
            e.printStackTrace();
            disconnect(sk);
        }
        if (outQueue.size() == 0) {
            sk.interestOps(SelectionKey.OP_READ);
        }
    }



    private void disconnect(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws IOException {
        new NioEchoServer().startServer();
//        ServerSocket echoServer = null;
//        Socket clientSocket;
//        try {
//            echoServer = new ServerSocket(8000);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        while (true) {
//            try {
//                clientSocket = echoServer.accept();
//                System.out.println(clientSocket.getRemoteSocketAddress() + " connect!");
//                tp.execute(new HandleMsg(clientSocket));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }


}
