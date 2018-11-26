package com.lazy.falcon.core.parser.support;

import com.alibaba.fastjson.JSON;
import com.lazy.falcon.common.tools.DateUtils;
import com.lazy.falcon.common.tools.SetsUtils;
import com.lazy.falcon.common.tools.SnowflakeldUtils;
import com.lazy.falcon.common.tools.StringUtils;
import com.lazy.falcon.core.constants.Constants;
import com.lazy.falcon.core.model.input.*;
import com.lazy.falcon.core.model.output.*;
import com.lazy.falcon.core.parser.dto.ApiGenerateDto;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Api Module Parser Abstract
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
public abstract class AbstractApiModuleParser extends AbstractComponent {

    protected AbstractApiParser apiParser;

    public AbstractApiModuleParser(AbstractApiParser apiParser) {
        this.apiParser = apiParser;
    }

    /**
     * 解析
     *
     * @param clazz 模块
     */
    public abstract void doParser(Class clazz);

    /**
     * 构建模块明细
     *
     * @param docDto   Doc DTO对象
     * @param apiModel ApiDoc模型
     */
    protected void buildModuleItem(ApiGenerateDto docDto, ApiModel apiModel) {
        String nowModuleItemHtmlName = String.format("module_item_%s.html", SnowflakeldUtils.nextId());
        //生成左侧栏时需要
        docDto.getNowApiDto().setNowApiHtmlName(nowModuleItemHtmlName);
        String nowModuleItemDirStr = String.format("%s/%s", docDto.getWorkModulesDirStr(), nowModuleItemHtmlName);
        //创建当前模块Item文件
        File nowModuleItemHtmlFile = new File(nowModuleItemDirStr);
        if (nowModuleItemHtmlFile.exists()) {
            docDto.getLog().error("GEN API DOC ERROR: system gen id chongfu");
        } else {
            //将模板复制一份到新的模块Item Html
            try {
                FileUtils.copyFile(docDto.getWorkApiTemplateDirFile(), nowModuleItemHtmlFile);
            } catch (IOException e) {
                docDto.getLog().error("GEN API DOC ERROR：", e);
            }
        }

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            Document apiDoc = Jsoup.parse(nowModuleItemHtmlFile, "UTF-8");
            apiDoc.getElementById("apiDocDiv").attr("title", apiModel.getModuleItem());
            apiDoc.getElementById("apiDesc").val(apiModel.getDesc());
            if (StringUtils.isEmpty(docDto.getDomain())) {
                apiDoc.getElementById("apiUrl").val(apiModel.getUrl());
            } else {
                apiDoc.getElementById("apiUrl").val(String.format("%s%s", docDto.getDomain(), apiModel.getUrl()));
            }
            if (apiModel.isRequestBody()) {
                apiDoc.getElementById("isRequestBody").val(String.valueOf(apiModel.isRequestBody()));
            }
            apiDoc.getElementById("apiAuthor").val(apiModel.getAuthor());
            apiDoc.getElementById("apiMethod").val(apiModel.getMethod().toUpperCase());
            apiDoc.getElementById("apiCreateTime").val(apiModel.getCreateDate() == null ? DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD) : apiModel.getCreateDate());
            apiDoc.getElementById("apiLstUpdTime").val(apiModel.getLstUpdDate() == null ? DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD) : apiModel.getLstUpdDate());

            List<ApiRequestParamGroupInputModel> reqParamGroupModelList = apiModel.getReqParams();
            //处理请求参数
            Document reqTableDoc = Jsoup.parse(docDto.getWorkReqParamTemplateFile(), "UTF-8");
            //截取Table标签
            Element reqTableElement = reqTableDoc.getElementsByTag("table").get(0);
            Element rootElement = apiDoc.getElementById("apiBasicInfo");
            StringBuffer appendAfterHtmlText = new StringBuffer();
            for (ApiRequestParamGroupInputModel groupModel : reqParamGroupModelList) {
                //设置标题
                reqTableElement.attr("title", groupModel.getTitle());
                //转换为工作对象 Group
                ApiRequestParamGroupOutputModel workReqParamGroupModel = new ApiRequestParamGroupOutputModel();
                //遍历参数Item
                for (ApiRequestParamItemInputModel inputReqParamItemModel : groupModel.getItems()) {
                    //转换为工作对象 Item
                    ApiRequestParamItemOutputModel workReqParamItemModel = new ApiRequestParamItemOutputModel();
                    workReqParamItemModel.setReqParamField(inputReqParamItemModel.getName());
                    workReqParamItemModel.setReqParamFieldDesc(inputReqParamItemModel.getDesc());
                    workReqParamItemModel.setReqParamFieldRemark(inputReqParamItemModel.getRemark());
                    workReqParamItemModel.setReqParamFieldRequired(inputReqParamItemModel.isRequired() ? "是" : "否");
                    workReqParamItemModel.setReqParamFieldType(inputReqParamItemModel.getType());
                    workReqParamGroupModel.getRows().add(workReqParamItemModel);
                }
                //生成请求参数json文件名
                String reqParamJsonFileName = String.format("req_param_%s.json", SnowflakeldUtils.nextId());
                reqTableElement.attr("data-options", String.format("collapsible:true,nowrap:false,url:'./req/%s',method:'get'", reqParamJsonFileName));
                //在modules/req/目录下创建json文件
                File reqParamJsonFile = new File(docDto.getWorkReqParamDirFile(), reqParamJsonFileName);
                //写入JSON内容
                FileUtils.write(reqParamJsonFile, JSON.toJSONString(workReqParamGroupModel), "UTF-8");
                //追加到id为apiBasicInfo的div下面
                appendAfterHtmlText.append(reqTableElement);
            }

            //处理响应参数
            Document resTableDoc = Jsoup.parse(docDto.getWorkResParamTemplateFile(), "UTF-8");
            //截取Table标签
            Element resTableElement = resTableDoc.getElementsByTag("table").get(0);
            for (ApiResponseParamGroupInputModel inputResParamGroupModel : apiModel.getResParams()) {
                //设置标题
                resTableElement.attr("title", inputResParamGroupModel.getTitle());
                //转换为工作对象 Group
                ApResponseParamGroupOutputModel workResParamGroupModel = new ApResponseParamGroupOutputModel();
                //遍历参数Item
                for (ApiResponseParamItemInputModel inputResParamItemModel : inputResParamGroupModel.getItems()) {
                    ApiResponseParamItemOutputModel workResParamItemModel = new ApiResponseParamItemOutputModel();
                    workResParamItemModel.setResParamField(inputResParamItemModel.getName());
                    workResParamItemModel.setResParamFieldDesc(inputResParamItemModel.getDesc());
                    workResParamItemModel.setResParamFieldRemark(inputResParamItemModel.getRemark());
                    workResParamItemModel.setResParamFieldType(inputResParamItemModel.getType());
                    workResParamGroupModel.getRows().add(workResParamItemModel);
                }
                //生成响应参数json文件名
                String resParamJsonFileName = String.format("res_param_%s.json", SnowflakeldUtils.nextId());
                resTableElement.attr("data-options", String.format("collapsible:true,nowrap:false,url:'./res/%s',method:'get'", resParamJsonFileName));
                //在modules/res/目录下创建json文件
                File resParamJsonFile = new File(docDto.getWorkResParamDirFile(), resParamJsonFileName);
                FileUtils.write(resParamJsonFile, JSON.toJSONString(workResParamGroupModel), "UTF-8");
                //追加到id为apiBasicInfo的div下面
                appendAfterHtmlText.append(resTableElement);
            }
            //写入ElementTextStr
            rootElement.after(appendAfterHtmlText.toString());

            //拼接参数到URL后面
            if (!apiModel.isRequestBody()) {
                apiModel.setUrl(this.appendParamToUrl(apiModel.getUrl(), apiModel.getReqParams()));
            }
            this.writeJsonTextParamsToTestDialog(
                    apiModel.getReqParams(),
                    apiDoc,
                    apiModel.getMethod(),
                    StringUtils.isEmpty(docDto.getNowApiDto().getBasicUri()) ? docDto.getDomain() + apiModel.getUrl() :
                            docDto.getNowApiDto().getBasicUri() + apiModel.getUrl());

            fos = new FileOutputStream(nowModuleItemHtmlFile, false);
            osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(apiDoc.html());
        } catch (Exception e) {
            docDto.getLog().error("GEN API DOC ERROR：", e);
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 追加参数到url
     *
     * @param url              url
     * @param groupInputModels 参数组
     * @return url
     */
    protected String appendParamToUrl(String url, List<ApiRequestParamGroupInputModel> groupInputModels) {
        if (groupInputModels == null || groupInputModels.isEmpty()) {
            return url;
        }
        StringBuilder urls = new StringBuilder();
        ApiRequestParamGroupInputModel groupInputModel = groupInputModels.get(0);
        for (ApiRequestParamItemInputModel itemInputModel : groupInputModel.getItems()) {
            urls.append("&").append(itemInputModel.getName()).append("=");
        }
        return url + "?" + urls.substring(1);
    }

    /**
     * 生成请求参数Json文本
     *
     * @param reqParamGroupModelList 请求参数列表
     * @return 参数字典
     */
    private Map<String, Object> genTestParamJsonText(List<ApiRequestParamGroupInputModel> reqParamGroupModelList) {
        Map<String, Object> rootParam = SetsUtils.map();
        if (reqParamGroupModelList == null || reqParamGroupModelList.isEmpty()) {
            return rootParam;
        }
        ApiRequestParamGroupInputModel groupModel = reqParamGroupModelList.get(0);
        for (ApiRequestParamItemInputModel itemModel : groupModel.getItems()) {
            if (StringUtils.isEmpty(itemModel.getGroupTitle())) {
                rootParam.put(itemModel.getName(), String.format("%s_%s", itemModel.getType(), itemModel.isRequired() ? "Required" : "NotRequired"));
            } else {
                rootParam.put(itemModel.getName(), recursionFields(reqParamGroupModelList, itemModel.getGroupTitle(), itemModel.getType()));
            }
        }
        return rootParam;
    }

    /**
     * 递归出所有对象字段
     *
     * @param reqParamGroupModelList 请求参数组列表
     * @param fieldsTitle            参数Item 字段标题
     * @param javaTypeStr            JavaType 字符串
     * @return
     */
    private Object recursionFields(List<ApiRequestParamGroupInputModel> reqParamGroupModelList, String fieldsTitle, String javaTypeStr) {
        if ("List".equals(javaTypeStr) || "Set".equals(javaTypeStr) || "Collection".equals(javaTypeStr) || "Array".equals(javaTypeStr)) {
            List<Map<String, Object>> fieldsTitleList = SetsUtils.list();
            Map<String, Object> fieldsItemMap = SetsUtils.map();
            for (ApiRequestParamGroupInputModel groupModel : reqParamGroupModelList) {
                if (!groupModel.getTitle().equals(fieldsTitle)) {
                    continue;
                }
                for (ApiRequestParamItemInputModel itemModel : groupModel.getItems()) {
                    if (StringUtils.isEmpty(itemModel.getGroupTitle())) {
                        fieldsItemMap.put(itemModel.getName(), String.format("%s_%s", itemModel.getType(), itemModel.isRequired() ? "Required" : "NotRequired"));
                    } else {
                        fieldsItemMap.put(itemModel.getName(), recursionFields(reqParamGroupModelList, itemModel.getGroupTitle(), itemModel.getType()));
                    }
                }
                fieldsTitleList.add(fieldsItemMap);
                break;
            }
            return fieldsTitleList;
        } else if ("Object".equals(javaTypeStr)) {
            Map<String, Object> fieldsItemMap = SetsUtils.map();
            for (ApiRequestParamGroupInputModel groupModel : reqParamGroupModelList) {
                if (!groupModel.getTitle().equals(fieldsTitle)) {
                    continue;
                }
                for (ApiRequestParamItemInputModel itemModel : groupModel.getItems()) {
                    if (StringUtils.isEmpty(itemModel.getGroupTitle())) {
                        fieldsItemMap.put(itemModel.getName(), String.format("%s_%s", itemModel.getType(), itemModel.isRequired() ? "Required" : "NotRequired"));
                    } else {
                        fieldsItemMap.put(itemModel.getName(), recursionFields(reqParamGroupModelList, itemModel.getGroupTitle(), itemModel.getType()));
                    }
                }
                break;
            }
            return fieldsItemMap;
        } else {
            throw new RuntimeException("[ERROR] GEN API DOC ERROR: 不支持类型：" + javaTypeStr + "，当fieldsTitle字段有值时请使用类型[List,Set,Collection,Array,Object]");
        }
    }

    /**
     * JsonText形式写入参数到测试对话框
     *
     * @param reqParamGroupModelList 请求参数组列表
     * @param reqMethod              请求Method
     * @param reqUrl                 请求URl
     * @param apiDoc                 ApiDocument
     */
    private void writeJsonTextParamsToTestDialog(List<ApiRequestParamGroupInputModel> reqParamGroupModelList,
                                                 Document apiDoc, String reqMethod, String reqUrl) throws IOException {
        if (reqParamGroupModelList == null) {
            reqParamGroupModelList = SetsUtils.list();
        }
        apiDoc.getElementById("reqURL20180606666").val(reqUrl);
        apiDoc.getElementById("reqMethod20180606666").val(reqMethod.toUpperCase());
        Map<String, Object> paramMap = genTestParamJsonText(reqParamGroupModelList);
        if (paramMap != null && !paramMap.isEmpty()) {
            apiDoc.getElementById("testParamTextarea").val(JSON.toJSONString(paramMap));
        }
    }

    /**
     * 添加左侧栏
     *
     * @param docDto   Doc DTO对象
     * @param apiModel API DOC模型
     */
    protected void addSidebarTree(ApiGenerateDto docDto, ApiModel apiModel) {
        SidebarTreeModel childModel = new SidebarTreeModel();
        childModel.setText(apiModel.getModuleItem());
        childModel.setId(docDto.getNowApiDto().getNowApiHtmlName());
        childModel.setUrl(String.format("%s/%s/%s/%s",
                docDto.getDomain(),
                Constants.OUTPUT_ROOT_DIR_NAME,
                Constants.MODULES_DIR_NAME,
                docDto.getNowApiDto().getNowApiHtmlName()));
        boolean isNotFound = true;
        for (SidebarTreeModel sidebarTreeModel : docDto.getSidebarTreeModelList()) {
            if (sidebarTreeModel.getId().equals(apiModel.getModule())) {
                sidebarTreeModel.getChildren().add(childModel);
                isNotFound = false;
                break;
            }
        }
        if (isNotFound) {
            SidebarTreeModel rootModel = new SidebarTreeModel();
            rootModel.setText(apiModel.getModule());
            rootModel.setId(apiModel.getModule());
            rootModel.getChildren().add(childModel);
            docDto.getSidebarTreeModelList().add(rootModel);
        }
    }

    /**
     * 菜单写到工作左侧栏
     *
     * @param docDto DocDto
     */
    protected void writeSidebarTree(ApiGenerateDto docDto) {
        try {
            //菜单写到工作左侧栏
            FileUtils.write(docDto.getWorkSidebarTreeDirFile(), JSON.toJSONString(docDto.getSidebarTreeModelList()), "UTF-8");
        } catch (Exception ex) {
            docDto.getLog().error("GEN API DOC ERROR: ", ex);
        }
    }

}
