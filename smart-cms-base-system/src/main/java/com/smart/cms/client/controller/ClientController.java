package com.smart.cms.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.authconstant.RoleConstant;
import com.smart.cms.client.service.IClientService;
import com.smart.cms.system.client.ClientDetail;
import com.smart.cms.utils.other.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 *  客户端信息管理控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@ApiIgnore
@Api(value = "客户端信息管理", tags = "接口")
@PreAuthorize(RoleConstant.HAS_ROLE_ADMIN)
public class ClientController {

	private final IClientService clientService;


	/**
	* 分页
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页-获取客户端信息列表", notes = "传入client")
	public R<IPage<ClientDetail>> list(ClientDetail client, PageData pageData) {
		QueryWrapper<ClientDetail> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(client.getClientId())) {
			queryWrapper.like("client_id", client.getClientId());
		}
		Page<ClientDetail> page = new Page<>(pageData.getCurrent(), pageData.getSize());
		IPage<ClientDetail> pages = clientService.page(page, queryWrapper);
		return R.ok(pages);
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入client")
	public R save( @RequestBody ClientDetail authClient) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		authClient.setClientSecret(bCryptPasswordEncoder.encode(authClient.getClientSecret()));
		boolean row = clientService.save(authClient);
		return row ? R.ok("操作成功") : R.failed("操作成功");
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam List<Long> ids) {
		return R.ok(clientService.removeByIds(ids));
	}


}
