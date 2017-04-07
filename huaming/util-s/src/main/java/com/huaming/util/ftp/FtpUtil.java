package com.huaming.util.ftp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FtpUtil {
	private static Logger logger = Logger.getLogger(FtpUtil.class);
	public static String STRICT_HOST_KEY_CHECK = "StrictHostKeyChecking";
	public static String NO = "no";
	public static String YES = "yes";

	private static Session getSftpChanl(String host, int port, String user, String pwd) throws Exception {
		Session session = null;
		if (session == null || !session.isConnected()) {
			try {
				session = new JSch().getSession(user, host, port);
				session.setPassword(pwd);
				Properties config = new Properties();
				config.put(STRICT_HOST_KEY_CHECK, NO);
				session.setConfig(config);
				session.connect();
			} catch (JSchException e) {
				throw new Exception("建立会话连接出现错误，请检查用户名、密码或者端口号",e);
			}
		}
		return session;
	}

	public static Channel getChanl(Session session) throws Exception {
		Channel channel = null;
		try {
			channel = session.openChannel("sftp");
			channel.connect();
		} catch (JSchException e) {
			throw new Exception("通道连接建立出现异常",e);
		}
		return channel;
	}

	/**
	 * 未测试功能 慎用
	 * @param host 主机 （IP或者主机外网名称）
	 * @param port 端口
	 * @param user 用户
	 * @param pwd  密码
	 * @param path 上传路径 如 /home/test/
	 * @param fileName 文件名称
	 * @param msg  上传的文件内容
	 */
	public static void upLoadMsg(String host, int port, String user, String pwd, String path,String fileName, String msg) throws Exception {
		Session session = null;
		Channel chnl = null;
		ChannelSftp sftp = null;
		try {
			session = getSftpChanl(host, port, user, pwd);
			chnl = getChanl(session);
			sftp = (ChannelSftp) chnl;
			byte[] msgBytes = msg.getBytes("UTF-8");
			ByteArrayInputStream src = new ByteArrayInputStream(msgBytes);
			if (!StringUtils.isEmpty(path)) {
				String dirs[] = path.split(File.separator);
				for (int i = 1; i < dirs.length-1; i++) {
					try {
						sftp.cd(dirs[i]);
					} catch (SftpException e) {
						logger.info("当前目录"+dirs[i]+"不存在"+e.id);
						if (ChannelSftp.SSH_FX_NO_SUCH_FILE == e.id) {
							try {
								sftp.mkdir(path);
								sftp.cd(path);
							} catch (SftpException ee) {
								logger.error("当前目录"+dirs[i]+"创建失败",ee);
								throw new Exception("目录创建出现异常",ee);
							}	
						}else{
							throw new Exception("目录切换出现异常",e);
						}
					}
					
				}
				sftp.put(src, fileName);
			}else{
				throw new Exception("上传文件路径目录为空");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("上传内容编码异常",e);
			throw new Exception(e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传出现异常",e);
			throw new Exception(e);
		}finally {
			if (sftp != null) {
				sftp.quit();
			}
			if (chnl != null) {
				chnl.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}

	}

}
