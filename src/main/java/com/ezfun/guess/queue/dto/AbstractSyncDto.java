package com.ezfun.guess.queue.dto;

/**
 * @author SoySauce
 * @Description 同步抽象参数
 */
public abstract class AbstractSyncDto {

	//重试次数
	protected int retryCount = 0;

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	public void addRetryCount() {
		this.retryCount++;
	}
}
