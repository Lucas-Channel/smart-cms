package com.smart.cms.quartz.controller;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobs")
@Api(tags = "系统:定时任务管理")
public class QuartzJobController {

    private static final String ENTITY_NAME = "quartzJob";
    private final QuartzJobService quartzJobService;

    @ApiOperation("查询定时任务")
    @GetMapping
    @PreAuthorize("@el.check('timing:list')")
    public ResponseEntity<Object> query(JobQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(quartzJobService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @ApiOperation("导出任务数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('timing:list')")
    public void download(HttpServletResponse response, JobQueryCriteria criteria) throws IOException {
        quartzJobService.download(quartzJobService.queryAll(criteria), response);
    }

    @ApiOperation("导出日志数据")
    @GetMapping(value = "/logs/download")
    @PreAuthorize("@el.check('timing:list')")
    public void downloadLog(HttpServletResponse response, JobQueryCriteria criteria) throws IOException {
        quartzJobService.downloadLog(quartzJobService.queryAllLog(criteria), response);
    }

    @ApiOperation("查询任务执行日志")
    @GetMapping(value = "/logs")
    @PreAuthorize("@el.check('timing:list')")
    public ResponseEntity<Object> queryJobLog(JobQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(quartzJobService.queryAllLog(criteria,pageable), HttpStatus.OK);
    }

    @Log("新增定时任务")
    @ApiOperation("新增定时任务")
    @PostMapping
    @PreAuthorize("@el.check('timing:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody QuartzJob resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        quartzJobService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改定时任务")
    @ApiOperation("修改定时任务")
    @PutMapping
    @PreAuthorize("@el.check('timing:edit')")
    public ResponseEntity<Object> update(@Validated(QuartzJob.Update.class) @RequestBody QuartzJob resources){
        quartzJobService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("更改定时任务状态")
    @ApiOperation("更改定时任务状态")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@el.check('timing:edit')")
    public ResponseEntity<Object> update(@PathVariable Long id){
        quartzJobService.updateIsPause(quartzJobService.findById(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("执行定时任务")
    @ApiOperation("执行定时任务")
    @PutMapping(value = "/exec/{id}")
    @PreAuthorize("@el.check('timing:edit')")
    public ResponseEntity<Object> execution(@PathVariable Long id){
        quartzJobService.execution(quartzJobService.findById(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除定时任务")
    @ApiOperation("删除定时任务")
    @DeleteMapping
    @PreAuthorize("@el.check('timing:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids){
        quartzJobService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
