package com.smart.cms.systemcode.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.authconstant.RoleConstant;
import com.smart.cms.system.code.Datasource;
import com.smart.cms.systemcode.service.IDatasourceService;
import com.smart.cms.utils.other.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/21 17:41
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/datasource")
@Api(value = "数据源配置表", tags = "数据源配置表接口")
public class DatasourceController {

	private final IDatasourceService datasourceService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入datasource")
	public R<Datasource> detail(Datasource datasource) {
		Datasource detail = datasourceService.lambdaQuery().eq(Datasource::getId, datasource.getId()).one();
		return R.ok(detail);
	}

	/**
	 * 分页 数据源配置表
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入datasource")
	public R<IPage<Datasource>> list(Datasource datasource, PageData pageData) {
		QueryWrapper<Datasource> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("del_flag", 0);
		Page<Datasource> page = new Page<>(pageData.getCurrent(), pageData.getSize());
		IPage<Datasource> pages = datasourceService.page(page, queryWrapper);
		return R.ok(pages);
	}

	/**
	 * 新增 数据源配置表
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入datasource")
	public R save(@RequestBody Datasource datasource) {
		return R.ok(datasourceService.save(datasource));
	}

	/**
	 * 修改 数据源配置表
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入datasource")
	public R update(@RequestBody Datasource datasource) {
		return R.ok(datasourceService.updateById(datasource));
	}

	/**
	 * 新增或修改 数据源配置表
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入datasource")
	public R submit(@RequestBody Datasource datasource) {
		datasource.setUrl(datasource.getUrl().replace("&amp;", "&"));
		return R.ok(datasourceService.saveOrUpdate(datasource));
	}


	/**
	 * 删除 数据源配置表
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.ok(null);
	}

	/**
	 * 数据源列表
	 */
	@GetMapping("/select")
	@ApiOperation(value = "下拉数据源", notes = "查询列表")
	public R<List<Datasource>> select() {
		List<Datasource> list = datasourceService.list();
		return R.ok(list);
	}

}
