package com.smart.cms.notice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.notice.mapper.NoticeMapper;
import com.smart.cms.notice.service.INoticeService;
import com.smart.cms.system.notice.NoticeDTO;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/7/2 17:16
 * @Version: 1.0
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeDTO> implements INoticeService {
}
