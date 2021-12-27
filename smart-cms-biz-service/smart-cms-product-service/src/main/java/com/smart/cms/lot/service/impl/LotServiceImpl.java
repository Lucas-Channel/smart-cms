package com.smart.cms.lot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.lot.mapper.LotMapper;
import com.smart.cms.lot.service.LotService;
import com.smart.cms.service.product.lot.LotNumber;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/27 16:35
 * @Version: 1.0
 */
@Service
public class LotServiceImpl extends ServiceImpl<LotMapper, LotNumber> implements LotService {
}
