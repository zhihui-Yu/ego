package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

public interface TbUserDubboService {

	/**
	 * 根据用户密码查询登入
	 * @param user
	 * @return
	 */
	TbUser selByUser(TbUser user);
}
