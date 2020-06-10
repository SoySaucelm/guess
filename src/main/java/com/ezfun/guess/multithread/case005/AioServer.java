package com.ezfun.guess.multithread.case005;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author SoySauce
 * @date 2019/11/12
 */
public class AioServer {
    public static final int PORT = 8000;
    private AsynchronousServerSocketChannel server;

    public AioServer() throws IOException {
        this.server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(InetAddress.getLocalHost(), PORT));
    }

    public void start() {
        System.out.println("server listen on " + PORT);
        //注册事件和事件完成后的处理器
        //该方法不会阻塞会立即返回
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);

            //异步操作accept成功时回调
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                System.out.println(Thread.currentThread().getName());
                Future<Integer> writeResult = null;
                try {
                    buffer.clear();
                    //TODO 暂时将异步改为同步
                    result.read(buffer).get(100, TimeUnit.SECONDS);
                    //重置缓冲区
                    buffer.flip();
                    writeResult = result.write(buffer);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        server.accept(null, this);
                        if (writeResult != null) {
                            writeResult.get();
                        }
                        result.close();
                    } catch (InterruptedException | ExecutionException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            //异步操作accept错误时回调
            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("failed: " + exc);
            }
        });
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        AioServer aioServer = new AioServer();
        aioServer.start();
        while (true){
            Thread.sleep(1000);
        }
    }
}
