package com.smart.cms.utils.other;


public class CommonCst {

    /**
     * 上传文件的类型-markdown
     *
     */
    public static final String UPLOAD_TYPE_MARKDOWN = "markdown";
    /**
     * 上传文件的类型-userIcon
     *
     */
    public static final String UPLOAD_TYPE_USER_ICON = "userIcon";

    /**
     * 审核状态，0-提交
     *
     */
    public static final String APPROVE_STATUS_SUBMIT = "0";
    /**
     * 审核状态：1-退回后提交
     *
     */
    public static final String APPROVE_STATUS_resubmit = "1";
    /**
     * 审核状态：2-退回
     *
     */
    public static final String APPROVE_STATUS_REWORK = "2";

    // 执行类型：1-spring id
    public static final String CLASS_TYPE_SPRING_ID = "1";
    // 执行类型：2-全类名
    public static final String CLASS_TYPE_BEAN_CLASS = "2";
    /**
     * 日志跟踪id名。
     */
    public static final String LOG_TRACE_ID = "traceid";

    /**
     * 请求头跟踪id名。
     */
    public static final String HTTP_HEADER_TRACE_ID = "app_trace_id";
}
