package blservice.userblservice;

import java.util.ArrayList;

import message.ResultMessage;
import vo.UserVO;

/**
 * 负责实现用户管理界面所需要的服务
 * @author cylong
 * @version Oct 26, 2014 3:19:03 PM
 */
/**
 * 登陆的时候使用ID和密码，ID类似于QQ号
 * 属性：ID、用户名、密码、职位
 * 管理员可以修改：用户名、密码、职位
 * 用户可以修改：用户名、密码
 * @author Zing
 * @version 2014年10月28日下午7:44:23
 */
public interface UserBLService {

	/**
	 * @return 全部的用户
	 * @author cylong
	 * @version 2014年11月29日 下午6:35:45
	 */
	public ArrayList<UserVO> show();

	/**
	 * 界面新建用户的时候得到一个可用的ID
	 * @return 新用户的ID
	 * @author cylong
	 * @version 2014年11月29日 下午6:41:05
	 */
	public String getID();

	/**
	 * 验证用户登录
	 * @param loaginInfo
	 * @return 返回登陆结果（成功、失败）
	 * @author cylong
	 * @version 2014年11月29日 下午6:39:53
	 */
	public ResultMessage login(LoginInfo loaginInfo);

	/**
	 * 返回用户保存的ID
	 * @return 用户登录ID或者null
	 * @author cylong
	 * @version 2014年11月29日 下午6:42:27
	 */
	public String returnUserID();

	/**
	 * 添加一位用户
	 * @param vo UserVO
	 * @return 处理信息
	 * @author cylong
	 * @version 2014年11月29日 下午6:43:15
	 */
	public ResultMessage addUser(UserVO vo);

	/**
	 * 根据用户ID删除用户
	 * @param ID
	 * @return 处理结果
	 */
	public ResultMessage deleteUser(String ID);

	/**
	 * 更新一位用户信息
	 * @param vo UserVO
	 * @return 处理结果
	 * @author cylong
	 * @version 2014年11月29日 下午6:44:29
	 */
	public ResultMessage updateUser(UserVO vo);

}