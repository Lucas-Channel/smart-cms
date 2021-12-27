package com.smart.cms.item.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.smart.cms.item.service.ItemFileService;
import com.smart.cms.service.product.ItemFile;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/12/27 16:02
 * @Version: 1.0
 */
@RestController
@RequestMapping("itemFile")
@AllArgsConstructor
public class ItemFileController {

    private ItemFileService itemFileService;

    @GetMapping("/listItemFileByItemId")
    public R<List<ItemFile>> listItemFileByItemId(Long itemId) {
        List<ItemFile> list = itemFileService.lambdaQuery()
                .eq(ItemFile::getDelFlag, 0)
                .eq(ItemFile::getItemId, itemId)
                .list();
        return R.ok(list);
    }

}
