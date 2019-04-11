package com.task.kp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.task.kp.model.SshConf;


public class CallCommand {
	 public List<String> executeCommand(SshConf conf, String command) throws JSchException, IOException {
		 Session session = null;
	     try {
	    	 session = setSession(conf);
	    	 return resultCommand(command, session);
	     } finally {
	    	 if(session != null)
	    		 session.disconnect();
	     }
	 }
	 
	 public void executeCommandShell(SshConf conf) throws JSchException, IOException {
		 Session session = null;
	     try {
	    	 session = setSession(conf);

	    	 resultShell(session);
	    	
	     } finally {
	    	 if(session != null)
	    		 session.disconnect();
	     }
	 }
	 
	 private List<String> resultCommand(String command, Session session) throws JSchException, IOException {
		 Channel channel 		= null;
		 byte[] tmp				= new byte[1024];
		 List<String> result 	= new LinkedList<String>();
		 try {
			 channel=session.openChannel("exec");
		     ((ChannelExec)channel).setCommand(command);
		     channel.setInputStream(null);
		     ((ChannelExec)channel).setErrStream(System.err);
		     
		     InputStream in=channel.getInputStream();
		     channel.connect();
		     while(true){
		    	 while(in.available()>0){
		            int i=in.read(tmp, 0, 1024);
		            if(i<0)break;
		            System.out.print(new String(tmp, 0, i));
		            result.add(new String(tmp, 0, i));
		          }
		          if(channel.isClosed()){
		            System.out.println("exit-status: "+channel.getExitStatus());
		            break;
		          }
		        }
		} finally {
			channel.disconnect();
		}
	        
		return result;
	}
	 
	 private void resultShell(Session session) throws JSchException, IOException {
		 Channel channel 		= null;
		 byte[] tmp				= new byte[1024];
		 try {
			 channel=session.openChannel("shell");
		      InputStream in=channel.getInputStream();
		      channel.setInputStream(System.in);
		      channel.setOutputStream(System.out);
		      channel.connect();

			     while(true){
			    	 while(in.available()>0){
			            int i=in.read(tmp, 0, 1024);
			            System.out.print(new String(tmp, 0, i));
			          }
			          if(channel.isClosed()){
			            System.out.println("exit-status: "+channel.getExitStatus());
			            break;
			          }
			        }
		 }catch(Exception e){
		          System.out.println(e);
		        }
	}

	private Session setSession(SshConf conf) throws JSchException {
		 java.util.Properties config = new java.util.Properties(); 
		 config.put("StrictHostKeyChecking", "no");
		 JSch jsch = new JSch();
		 Session session=jsch.getSession(conf.getUserName(),conf.getHost());
		 session.setPassword(conf.getPassword());
		 session.setConfig(config);
		 session.connect();
		 return session;
		 
	 }

}
