package com.task.kp.singleton;

import com.task.kp.service.CallCommand;
import com.task.kp.service.FileService;
import com.task.kp.service.HbaseService;

public class Singleton {
	private static Singleton instance;
	private static HbaseService hbaseService;
	private static FileService fileService;
	private static CallCommand callCommand;
	
	 private Singleton(){
		 hbaseService = new HbaseService();
		 fileService = new FileService();
		 callCommand = new CallCommand();

	 }

	public static Singleton getInstance(){
		 if(instance == null){
	            instance = new Singleton();
	        }
	        return instance;
	 }

	public HbaseService getHbaseService() {
		return hbaseService;
	}

	public FileService getFileService() {
		return fileService;
	}

	public CallCommand getCallCommand() {
		return callCommand;
	}
}
