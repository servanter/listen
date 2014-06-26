package com.zhy.listen.service;

import com.zhy.listen.bean.Music;

public interface FetchMusicService {

	/**
	 * 查找歌曲
	 * @param author 作者
	 * @param topic 歌曲名称
	 * @return
	 */
	public Music findMusicByBaidu(String author, String topic) throws Exception;
}
