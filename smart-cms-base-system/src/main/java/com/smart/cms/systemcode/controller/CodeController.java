package com.smart.cms.systemcode.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.cms.system.code.Datasource;
import com.smart.cms.system.code.SystemCode;
import com.smart.cms.systemcode.gener.CodeGenerator;
import com.smart.cms.systemcode.service.IDatasourceService;
import com.smart.cms.systemcode.service.ISystemCodeService;
import com.smart.cms.utils.other.PageData;
import com.smart.cms.utils.other.StringUtil;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/21 17:41
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/code")
@Api(value = "代码生成", tags = "代码生成")
//@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
public class CodeController {

	private final ISystemCodeService codeService;
	private final IDatasourceService datasourceService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入code")
	public R<SystemCode> detail(SystemCode code) {
		return R.ok(null);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "codeName", value = "模块名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "tableName", value = "表名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "modelName", value = "实体名", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "分页", notes = "传入code")
	public R<IPage<SystemCode>> list(@ApiIgnore @RequestParam Map<String, Object> code, PageData pageData) {
		QueryWrapper<SystemCode> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("del_flag", 0);
		Page<SystemCode> page = new Page<>(pageData.getCurrent(), pageData.getSize());
		IPage<SystemCode> pages = codeService.page(page, queryWrapper);
		return R.ok(pages);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入code")
	public R submit(@RequestBody SystemCode code) {
		return R.ok(codeService.save(code));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		codeService.lambdaUpdate().set(SystemCode::getDelFlag, 0).in(SystemCode::getId, ids).update();
		return R.ok(1);
	}


	/**
	 * 代码生成
	 */
	@PostMapping("/gen-code")
	@ApiOperation(value = "代码生成", notes = "传入ids")
	public R genCode(@ApiParam(value = "主键集合", required = true) @RequestParam String ids, @RequestParam(defaultValue = "sword") String system) {
		Collection<SystemCode> codes = codeService.listByIds(Collections.singleton(Long.valueOf(ids)));// 批量需要处理
		codes.forEach(code -> {
			CodeGenerator generator = new CodeGenerator();
			// 设置数据源
			Datasource datasource = datasourceService.getById(code.getDatasourceId());
			generator.setDriverName(datasource.getDriverClass());
			generator.setUrl(datasource.getUrl());
			generator.setUsername(datasource.getUsername());
			generator.setPassword(datasource.getPassword());
			// 设置基础配置
			generator.setSystemName(system);
			generator.setCodeName(code.getCodeName());
			generator.setServiceName(code.getServiceName());
			generator.setPackageName(code.getPackageName());
			generator.setPackageDir(code.getApiPath());
			generator.setPackageWebDir(code.getWebPath());
			generator.setTablePrefix(StringUtil.toStrArray(code.getTablePrefix()));
			generator.setIncludeTables(StringUtil.toStrArray(code.getTableName()));
			// 设置是否继承基础业务字段
			generator.setHasSuperEntity(code.getBaseMode() == 2);
			// 设置是否开启包装器模式
			generator.setHasWrapper(code.getWrapMode() == 2);
			generator.run();
		});
		return R.ok("代码生成成功");
	}
}
