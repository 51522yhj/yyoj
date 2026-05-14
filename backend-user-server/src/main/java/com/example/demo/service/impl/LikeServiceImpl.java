package com.example.demo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.LikeMapper;
import com.example.demo.service.LikeService;
import com.yupi.yuojbackendmodel.model.entity.Like;
import org.springframework.stereotype.Service;

/**
* @author h'h
* @description 针对表【like(点赞表)】的数据库操作Service实现
* @createDate 2025-02-05 12:59:21
*/
@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like>
    implements LikeService {

}




