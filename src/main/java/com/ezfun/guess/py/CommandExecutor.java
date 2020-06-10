package com.ezfun.guess.py;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandExecutor {
	// 保存进程的输入流信息
	private StringBuffer stdout;
	// 保存进程的错误流信息
	private StringBuffer errorout;

	public void executeCommand(String command) {
		stdout = new StringBuffer();
		errorout = new StringBuffer();
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);

			// 创建2个线程，分别读取输入流缓冲区和错误流缓冲区
			ThreadUtil stdoutUtil = new ThreadUtil(p.getInputStream(), stdout);
			ThreadUtil erroroutUtil = new ThreadUtil(p.getErrorStream(), errorout);
			// 启动线程读取缓冲区数据
			stdoutUtil.start();
			erroroutUtil.start();

			p.waitFor();//等待程序执行完成
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getStdout() {
		return stdout.toString();
	}

	public String getErrorout() {
		return errorout.toString();
	}

	private class ThreadUtil implements Runnable {
		// 设置读取的字符编码
		// private String character = "GB2312";
		private String character = "UTF-8";
		private StringBuffer strb;
		private InputStream inputStream;

		public ThreadUtil(InputStream inputStream, StringBuffer strb) {
			this.inputStream = inputStream;
			this.strb = strb;
		}

		public void start() {
			Thread thread = new Thread(this);
			thread.setDaemon(true);// 将其设置为守护线程
			thread.start();

		}

		public void run() {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(inputStream,
						character));
				String line = null;
				while ((line = br.readLine()) != null) {
					if (line != null) {
						strb.append(line).append("\n");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {// 释放资源
					inputStream.close();
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
