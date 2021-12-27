package com.smart.cms.stock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.service.product.stock.ItemStock;
import com.smart.cms.stock.mapper.StockMapper;
import com.smart.cms.stock.service.StockService;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/27 17:05
 * @Version: 1.0
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, ItemStock> implements StockService {
}
