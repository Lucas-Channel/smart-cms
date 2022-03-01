package com.smart.cms.security.core.clientdetails;

import com.smart.cms.common.Result;
import com.smart.cms.common.enums.PasswordEncoderTypeEnum;
import com.smart.cms.fegin.OAuthClientFeignClient;
import com.smart.cms.system.client.ClientDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

/**
 * OAuth2 客户端信息
 */
@Service
@RequiredArgsConstructor
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final OAuthClientFeignClient oAuthClientFeignClient;

    @Override
//    @Cacheable(cacheNames = "auth", key = "'oauth-client:'+#clientId")
    public ClientDetails loadClientByClientId(String clientId) {
        try {
            // 通过clientId获取getOAuth2ClientById
            Result<ClientDetail> result = oAuthClientFeignClient.getOAuth2ClientById(clientId);
            if (Result.success().getCode().equals(result.getCode())) {
                ClientDetail client = result.getData();
                BaseClientDetails clientDetails = new BaseClientDetails(
                        client.getClientId(),
                        client.getResourceIds(),
                        client.getScope(),
                        client.getAuthorizedGrantTypes(),
                        client.getAuthorities(),
                        client.getWebServerRedirectUri()
                );
                clientDetails.setClientSecret(PasswordEncoderTypeEnum.NOOP.getPrefix() + client.getClientSecret());
                clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
                clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
                return clientDetails;
            } else {
                throw new NoSuchClientException("No client with requested id: " + clientId);
            }
        } catch (EmptyResultDataAccessException var4) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
    }
}
