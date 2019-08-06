package com.jvm.oom;

/**
 * 
 * 创建线程导致内存溢出
 * java.lang.OutOfMemoryError: unable to create new native thread
 * 
 * -Xss2M
 * 
 * 注意：谨慎运行，由于windows平台的虚拟机中，Java的线程是映射到操作系统的内核线程上的，可以会导致系统假死。
 * 
 * @version 1.0
 */
public class JavaVMStackOOM {

	private void dontStop() {
		while (true) {
		}
	}

	public void stackLeakByThread() {
		while (true) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					dontStop();
				}
			});
			thread.start();
		}
	}

	public static void main(String[] args) throws Throwable {
		JavaVMStackOOM oom = new JavaVMStackOOM();
		oom.stackLeakByThread();
	}
}
