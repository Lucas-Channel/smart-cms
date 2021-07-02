package com.smart.cms.system.notice;
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
@TableName("smart_system_notice")
@ApiModel(value = "notice对象", description = "notice对象")
public class NoticeDTO extends BaseEntityData implements Serializable {

	private static final long serialVersionUID = 6835726409842428685L;
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	private String title;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;

	/**
	 * 发送人Id
	 */
	@ApiModelProperty(value = "发送人Id")
	private Long sender;

	/**
	 * 发送人名称
	 */
	@ApiModelProperty(value = "发送人名称")
	private String senderName;

	/**
	 * 接收类型（1、指定用户才可接收，2、角色，3、部门）
	 */
	@ApiModelProperty(value = "接收类型")
	private Integer acceptType;

	/**
	 * 接收方ids
	 */
	@ApiModelProperty(value = "接收方ids，用英文的逗号分割")
	private String acceptIds;

	/**
	 * 通知状态
	 */
	@ApiModelProperty(value = "通知状态：save，apply，revoke")
	private String noticeStatus;

	/**
	 * 通知紧急程度：正常，紧急
	 */
	@ApiModelProperty(value = "通知紧急程度：normal、urgency")
	private String emergency;



	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		NoticeDTO other = (NoticeDTO) obj;
		if (Objects.equals(this.getId(), other.getId())) {
			return true;
		}
		return false;
	}

}
