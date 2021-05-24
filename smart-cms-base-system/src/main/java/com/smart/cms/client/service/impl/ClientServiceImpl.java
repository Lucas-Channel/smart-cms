package com.smart.cms.client.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.cms.client.mapper.ClientMapper;
import com.smart.cms.client.service.IClientService;
import com.smart.cms.system.client.ClientDetail;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/24 15:02
 * @Version: 1.0
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, ClientDetail> implements IClientService {
}
