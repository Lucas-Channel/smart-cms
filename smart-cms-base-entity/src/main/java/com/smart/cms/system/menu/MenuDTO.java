package com.smart.cms.system.menu;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 实体类
 *
 * @author Lucas
 */
@Data
@TableName("smart_system_menu")
@ApiModel(value = "Menu对象", description = "Menu对象")
public class MenuDTO extends BaseEntityData implements Serializable {


	private static final long serialVersionUID = -738225716132899120L;
	/**
	 * 菜单父主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "菜单父主键")
	private Long parentId;

	/**
	 * 菜单名称
	 */
	@ApiModelProperty(value = "菜单名称")
	private String name;

	/**
	 * 请求地址
	 */
	@ApiModelProperty(value = "请求地址")
	private String path;

	/**
	 * 菜单图标
	 */
	@ApiModelProperty(value = "菜单图标")
	private String icon;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;


	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	private Integer status;



	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		MenuDTO other = (MenuDTO) obj;
		if (Objects.equals(this.getId(), other.getId())) {
			return true;
		}
		return false;
	}

}
