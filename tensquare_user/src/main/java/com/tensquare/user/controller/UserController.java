package com.tensquare.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public Result login(@RequestBody User user) {
		user = userService.login(user);
		if (user != null) {
			//登陆成功
			String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");
			Map<String,Object> map = new HashMap<>();
			map.put("token",token);
			map.put("roles",user);
			return new Result(true,StatusCode.OK,"登录成功",map);
		}
		return new Result(false,StatusCode.ERROR,"登录失败");
	}

	/**
	 * 用户注册
	 * @param code
	 * @param user
	 * @return
	 */
	@PostMapping("/register/{code}")
	public Result regist(@PathVariable("code") String code,@RequestBody User user) {
		//先得到缓存中的验证码checkcode
		String checkcodeRedis = (String) redisTemplate.opsForValue().get("checkcode_"+user.getMobile());
		//缓存中没有验证码
		if(checkcodeRedis.isEmpty()) {
			return new Result(false,StatusCode.ERROR,"未获取验证码或者验证码已过期");
		}
		//验证码不匹配
		if(!checkcodeRedis.equals(code)) {
			return new Result(false,StatusCode.ERROR,"验证码错误");
		}
		userService.add(user);
		return new Result(true,StatusCode.OK,"注册成功");
	}


	/**
	 * 发送短信验证码
	 * @param mobile 手机号
	 * @return
	 */
	@PostMapping("/sendsms/{mobile}")
	public Result sendSms(@PathVariable String mobile) {
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除 必须有admin角色才能删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		try{
			userService.deleteById(id);
		} catch (Exception e) {
			return new Result(false,StatusCode.ERROR,"权限不足");
		}

		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
