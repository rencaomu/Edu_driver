package com.ren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ren.commonutils.JwtUtils;
import com.ren.commonutils.MD5;
import com.ren.entity.UcenterMember;
import com.ren.entity.vo.RegisterVo;
import com.ren.exception.RenException;
import com.ren.mapper.UcenterMemberMapper;
import com.ren.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2022-10-06
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

  @Autowired
  private RedisTemplate<String,String> redisTemplate;
    //登录的方法
    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new RenException(20001,"手机号或密码不存在");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMembers = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if(mobileMembers == null) {//没有这个手机号
            throw new RenException(20001,"号码错误");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        if(!MD5.encrypt(password).equals(mobileMembers.getPassword())) {
            throw new RenException(20001,"密码错误");
        }

        //判断用户是否禁用
        if(mobileMembers.getIsDisabled()) {
            throw new RenException(20001,"该账号已被禁用，请联系工作人员");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMembers.getId(), mobileMembers.getNickname());
        return jwtToken;
    }

    // 注册接口
    @Override
    public void register(RegisterVo registerVo) {
        // 获取注册数据
//         String code = registerVo.getCode();
         String mobile = registerVo.getMobile();
         String nickname = registerVo.getNickname();
         String password = registerVo.getPassword();

//   ||StringUtils.isEmpty(code)
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
         ||   StringUtils.isEmpty(nickname)) {
            throw new RenException(20001,"注册失败");
        }

        // 判断验证码是否正确
//         String redisCode = redisTemplate.opsForValue().get(mobile);
//        if (!code.equals(redisCode)){
//            throw new RenException(20001,"注册失败");
//        }

        // 判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
         Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new RenException(20001,"手机号重复注册");
        }
        // 数据加入数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/jJHyeM0EN2jhB70LntI3k8fEKe7W6CwykrKMgDJM4VZqCpcxibVibX397p0vmbKURGkLS4jxjGB0GpZfxCicgt07w/132");
        baseMapper.insert(member);

    }

    // 查新数据库有没有重复的Id
    @Override
    public UcenterMember getOpenIdMember(String oppenid) {

       QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
       wrapper.eq("openid",oppenid);
       UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    // 根据天数查询注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegister(day);
    }

}
