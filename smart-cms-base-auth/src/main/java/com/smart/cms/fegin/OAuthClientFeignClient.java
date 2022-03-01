package com.smart.cms.fegin;

import com.smart.cms.common.Result;
import com.smart.cms.system.client.ClientDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/2/28 18:31
 * @Version: 1.0
 */
@FeignClient(value = "smart-cms-base-system", url = "localhost:8989/",fallback = OAuthClientFeignClientFallBack.class)
//@FeignClient(url = "localhost:8989/", fallback = OAuthClientFeignClientFallBack.class)
public interface OAuthClientFeignClient {

    @GetMapping("/client/getOAuth2ClientById")
    Result<ClientDetail> getOAuth2ClientById(@RequestParam String clientId);
}
