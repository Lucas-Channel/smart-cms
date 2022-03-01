package com.smart.cms.fegin;

import com.smart.cms.common.Result;
import com.smart.cms.common.ResultCode;
import com.smart.cms.system.client.ClientDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/1 10:41
 * @Version: 1.0
 */
@Component
@Slf4j
public class OAuthClientFeignClientFallBack implements OAuthClientFeignClient {
    @Override
    public Result<ClientDetail> getOAuth2ClientById(String clientId) {
        log.error("feign远程调用系统服务异常后的降级方法");
        return Result.failed(ResultCode.DEGRADATION);
    }
}
