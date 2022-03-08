package com.smart.cms.client.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.authconstant.RoleConstant;
import com.smart.cms.client.service.IClientService;
import com.smart.cms.common.Result;
import com.smart.cms.system.client.ClientDetail;
import com.smart.cms.utils.other.PageData;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.List;


/**
 *  客户端信息管理控制器
 *
 * @author Lucas
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@ApiIgnore
@Api(value = "客户端信息管理", tags = "接口")
//@PreAuthorize(RoleConstant.HAS_ROLE_ADMIN)
public class ClientController {

	private final IClientService clientService;


	/**
	* 分页
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页-获取客户端信息列表", notes = "传入client")
	public Result<IPage<ClientDetail>> list(ClientDetail client, PageData pageData) {
		QueryWrapper<ClientDetail> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("del_flag", 0);
		if (StringUtils.isNotBlank(client.getClientId())) {
			queryWrapper.like("client_id", client.getClientId());
		}
		Page<ClientDetail> page = new Page<>(pageData.getCurrent(), pageData.getSize());
		IPage<ClientDetail> pages = clientService.page(page, queryWrapper);
		return Result.success(pages);
	}

	@ApiOperation(value = "客户端详情")
	@ApiImplicitParam(name = "clientId", value = "客户端id", required = true, paramType = "path", dataType = "String")
	@GetMapping("/{clientId}")
	public Result detail(@PathVariable String clientId) {
		ClientDetail client = clientService.getById(clientId);
		return Result.success(client);
	}

	@ApiOperation(value = "新增客户端")
	@ApiImplicitParam(name = "client", value = "实体JSON对象", required = true, paramType = "body", dataType = "OauthClientDetails")
	@PostMapping
	public Result add(@RequestBody ClientDetail client) {
		boolean status = clientService.save(client);
		return Result.judge(status);
	}

	@ApiOperation(value = "修改客户端")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clientId", value = "客户端id", required = true, paramType = "path", dataType = "String"),
			@ApiImplicitParam(name = "client", value = "实体JSON对象", required = true, paramType = "body", dataType = "OauthClientDetails")
	})
	@PutMapping(value = "/{clientId}")
	public Result update(
			@PathVariable String clientId,
			@RequestBody ClientDetail client) {
		boolean status = clientService.updateById(client);
		return Result.judge(status);
	}


	/**
	 * 删除
	 */
	@ApiOperation(value = "删除客户端")
	@ApiImplicitParam(name = "ids", value = "id集合,以,拼接字符串", required = true, paramType = "query", dataType = "String")
	@DeleteMapping("/{ids}")
	public Result delete(@PathVariable("ids") String ids) {
		boolean status = clientService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.judge(status);
	}

	@ApiOperation(hidden = true, value = "获取 OAuth2 客户端认证信息", notes = "Feign 调用")
	@GetMapping("/getOAuth2ClientById")
	public Result<ClientDetail> getOAuth2ClientById(@RequestParam String clientId) {
		ClientDetail client = clientService.lambdaQuery().eq(ClientDetail::getClientId, clientId).one();
		Assert.isTrue(client!=null, "OAuth2 客户端不存在");
		return Result.success(client);
	}
}
