package com.zzptc.LiuXiaolong.baidu.Tools;

import java.io.Serializable;

public class UpdateInfo implements Serializable{
	private int version_code;
	private String version_info;
	private String version_downloadUrl;
	private String version_name;

	public int getVersion_code() {
		return version_code;
	}

	public void setVersion_code(int version_code) {
		this.version_code = version_code;
	}

	public String getVersion_info() {
		return version_info;
	}

	public void setVersion_info(String version_info) {
		this.version_info = version_info;
	}

	public String getVersion_downloadUrl() {
		return version_downloadUrl;
	}

	public void setVersion_downloadUrl(String version_downloadUrl) {
		this.version_downloadUrl = version_downloadUrl;
	}

	public String getVersion_name() {
		return version_name;
	}

	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}
	
}
