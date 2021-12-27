package com.smart.cms.service.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.smart.cms.base.BaseEntityData;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/27 15:54
 * @Version: 1.0
 */
@Data
@TableName("smart_service_item_file")
@ApiModel(value = "产品文件对象", description = "产品文件对象")
public class ItemFile extends BaseEntityData implements Serializable {

    private Long itemId;

    private String fileName;

    private String filePath;

    private String fileType;

}
