package com.smart.cms.item.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.extension.api.R;
import com.smart.cms.item.service.ItemPriceDetailService;
import com.smart.cms.service.product.ItemPriceDetail;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/27 10:22
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@Api(value = "商品价格控制器")
@RequestMapping("/itemPriceDetail")
public class ItemPriceDetailController {

    private ItemPriceDetailService priceDetailService;

    @GetMapping("selectPriceDetailByItemId")
    public R<List<ItemPriceDetail>> selectPriceDetailByItemId(ItemPriceDetail itemPriceDetail) {
        List<ItemPriceDetail> list = priceDetailService.lambdaQuery()
                .eq(Objects.nonNull(itemPriceDetail.getItemId()), ItemPriceDetail::getItemId, itemPriceDetail.getItemId())
                .eq(Objects.nonNull(itemPriceDetail.getInvOrgId()), ItemPriceDetail::getInvOrgId, itemPriceDetail.getInvOrgId())
                .list();
        return R.ok(list);
    }

    /**
     * 以下为demo案例
     * @param multipartFile
     * @return
     */
    @PostMapping("importPriceDataByExcel")
    public R importPriceDataByExcel(MultipartFile multipartFile) {
        // 注意，导入文件有固定目录，如果是持久类文件，需要按目录区分文件，临时文件使用@Value("${file.upload.temp}"的地址，该目录下的地址会在第二天凌晨通过调度任务清除附件数据)
        // 使用注解的方式导入数据
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(1);
        importParams.setHeadRows(1);
        importParams.setSheetNum(1);
        List<ItemPriceDetail> list = ExcelImportUtil.importExcel(new File(multipartFile.getOriginalFilename()), ItemPriceDetail.class, importParams);
        return R.ok("导入成功");
    }

}
