package com.lazy.falcon.core.parser;

import com.alibaba.fastjson.JSON;
import com.lazy.falcon.core.annotaion.ApiBasicUri;
import com.lazy.falcon.core.annotaion.GenApiDocByJsonFile;
import com.lazy.falcon.core.constants.Constants;
import com.lazy.falcon.core.dto.ParserDocDto;
import com.lazy.falcon.core.dto.ParserNowApiDto;
import com.lazy.falcon.core.model.input.*;
import com.lazy.falcon.core.model.output.*;
import com.lazy.falcon.core.tools.AsserUtils;
import com.lazy.falcon.core.tools.SetsUtils;
import com.lazy.falcon.core.tools.SnowflakeldUtils;
import com.lazy.falcon.core.tools.StringUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * html 解析
 * 通过JSON文件
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
@SuppressWarnings({"all", "unchecked"})
public class HtmlByJsonConfigFileParser implements IParser {

    /**
     * 解析
     *
     * @param docDto DTO对象
     */
    @Override
    public void doParser(ParserDocDto docDto) {
        this.copyTemplateToWorkDir(docDto);
        this.initIndexHtmlTitle(docDto);
        this.initDirStr(docDto);
        this.initDirFile(docDto);
        String basicUri;
        ApiBasicUri apiBasicUri;
        GenApiDocByJsonFile genApiDocByJsonFile;
        Method[] methods;
        String nowApiConfigFilePath;
        String nowHandlerMethodKey;
        Annotation[] annotations;
        boolean isController = false;
        boolean isRequestMapping = false;
        ParserNowApiDto nowApiDto;
        for (Class clazz : docDto.getNowProjectControllerClasses()) {
            if (clazz == null) {
                continue;
            }
            annotations = clazz.getAnnotations();
            if (annotations == null || annotations.length == 0) {
                continue;
            }
            if (!AsserUtils.isController(clazz)) {
                continue;
            }
            apiBasicUri = (ApiBasicUri) clazz.getAnnotation(ApiBasicUri.class);
            basicUri = (apiBasicUri != null) ? apiBasicUri.value() : null;
            methods = clazz.getMethods();
            if (methods == null || methods.length < 1) {
                docDto.getLog().warn(String.format("GEN API DOC [%s] not method", clazz.getName()));
                continue;
            }

            for (Method method : methods) {
                genApiDocByJsonFile = method.getAnnotation(GenApiDocByJsonFile.class);
                if (genApiDocByJsonFile == null) {
                    continue;
                }
                nowHandlerMethodKey = String.format("GEN API DOC Method: [%s#%s]", clazz.getName(), method.getName());
                annotations = method.getAnnotations();
                if (annotations == null || annotations.length == 0) {
                    docDto.getLog().info(String.format("GEN API DOC %s 缺少RequestMapping 或 GetMapping PostMapping PutMapping DeleteMapping注解", nowHandlerMethodKey));
                    continue;
                }
                if (!AsserUtils.isRequestMapping(method)) {
                    docDto.getLog().info(String.format("GEN API DOC %s 缺少RequestMapping 或 GetMapping PostMapping PutMapping DeleteMapping注解", nowHandlerMethodKey));
                    continue;
                }
                nowApiDto = new ParserNowApiDto();
                nowApiDto.setNowApiConfigFilePath(genApiDocByJsonFile.value());
                nowApiDto.setBasicUri(basicUri);
                docDto.getLog().info(String.format("[%s], nowApiConfigFilePath [%s]",
                        nowHandlerMethodKey, nowApiDto.getNowApiConfigFilePath()));
                docDto.setNowApiDto(nowApiDto);
                this.buildApiDoc(docDto);
            }
        }

        try {
            //菜单写到工作左侧栏
            FileUtils.write(docDto.getWorkSidebarTreeDirFile(), JSON.toJSONString(docDto.getSidebarTreeModelList()), "UTF-8");
        } catch (Exception ex) {
            docDto.getLog().error("GEN API DOC ERROR: ", ex);
        }
    }

    /**
     * 初始化首页标题
     *
     * @param docDto Doc DTO对象
     */
    private void initIndexHtmlTitle(ParserDocDto docDto) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            //indexHtml File 对象
            File indexHtmlFile = new File(String.format("%s/%s", docDto.getWorkApiDocDirStr(), Constants.INDEX_HTML_FILE_NAME));

            Document doc = Jsoup.parse(indexHtmlFile, "UTF-8");
            doc.title(docDto.getNowProjectDocTitle());
            doc.getElementById("navTitle").html(docDto.getNowProjectDocTitle());
            fos = new FileOutputStream(indexHtmlFile, false);
            osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(doc.html());
        } catch (Exception ex) {
            docDto.getLog().error("GEN API DOC ERROR：", ex);
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ex) {
                docDto.getLog().error("GEN API DOC ERROR: ", ex);
            }
        }
    }

    /**
     * 复制模板到工作目录
     *
     * @param docDto Doc DTO对象
     */
    private void copyTemplateToWorkDir(ParserDocDto docDto) {
        //从模板复制到Client项目的ClassPath下
        InputStream inputStream = null;
        File workDir;
        try {
            for (String fileDir : Constants.TEMPLATE_FILES_DIR) {
                try {
                    inputStream = HtmlByJsonConfigFileParser.class.getClassLoader().getResourceAsStream(String.format("%s/%s", "doc_template", fileDir));
                    workDir = new File(String.format("%s/%s/%s", docDto.getWorkStaticDir(),
                            Constants.OUTPUT_ROOT_DIR_NAME, fileDir));
                    FileUtils.copyToFile(inputStream, workDir);
                } catch (Exception ex) {
                    docDto.getLog().error("GEN API DOC ERROR:", ex);
                } finally {
                    inputStream.close();
                    inputStream = null;
                }
            }
        } catch (IOException e) {
            docDto.getLog().error("GEN API DOC ERROR:", e);
        }
    }

    /**
     * 构建一个API DOC
     *
     * @param docDto Doc DTO对象
     */
    private void buildApiDoc(ParserDocDto docDto) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            File nowApiConfigFile = new File(String.format("%s/%s", docDto.getNowProductClassPath(), docDto.getNowApiDto().getNowApiConfigFilePath()));
            String nowApiConfigFileStr = FileUtils.readFileToString(nowApiConfigFile, "UTF-8");
            ApiDocModel apiDocModel = JSON.parseObject(nowApiConfigFileStr, ApiDocModel.class);

            this.buildModuleItem(docDto, apiDocModel);
            this.addSidebarTree(docDto, apiDocModel);
        } catch (Exception ex) {
            docDto.getLog().error("GEN API DOC ERROR：", ex);
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ex) {
                docDto.getLog().error("GEN API DOC ERROR: ", ex);
            }
        }
    }

    /**
     * 初始化目录字符串
     *
     * @param docDto Doc DTO对象
     */
    private void initDirStr(ParserDocDto docDto) {
        //模板Modules目录
        String workModulesDirStr = String.format("%s/%s",
                docDto.getWorkApiDocDirStr()
                , Constants.MODULES_DIR_NAME
        );
        //左侧栏JSON文件
        String sidebarTreeJsonDirStr = String.format("%s/%s",
                docDto.getWorkApiDocDirStr(),
                Constants.SIDEBAR_TREE_FILE_NAME);
        //api_template目录
        String workApiTemplateDirStr = String.format("%s/%s",
                workModulesDirStr
                , Constants.API_TEMPLATE_DIR_NAME
        );

        docDto.setWorkModulesDirStr(workModulesDirStr);
        docDto.setWorkApiTemplateDirStr(workApiTemplateDirStr);
        docDto.setWorkReqParamDirStr(String.format("%s/%s", docDto.getWorkModulesDirStr(), Constants.REQ_PARAM_JSON_FILE_OUTPUT_DIR));
        docDto.setWorkResParamDirStr(String.format("%s/%s", docDto.getWorkModulesDirStr(), Constants.RES_PARAM_JSON_FILE_OUTPUT_DIR));
        docDto.setWorkRootDirStr(workApiTemplateDirStr);
        docDto.setWorkSidebarTreeJsonDirStr(sidebarTreeJsonDirStr);
    }

    /**
     * 初始化目录文件File 对象
     *
     * @param docDto Doc DTO对象
     */
    private void initDirFile(ParserDocDto docDto) {
        //核心html模板文件目录
        String apiHtmlTemplateDirStr = String.format("%s/%s", docDto.getWorkApiTemplateDirStr(), Constants.API_HTML_TEMPLATE_NAME);
        String apiReqParamTableHtmlTemplateStr = String.format("%s/%s", docDto.getWorkApiTemplateDirStr(), Constants.API_REQ_PARAM_TABLE_TEMPLATE_HTML_NAME);
        String apiResParamTableHtmlTemplateStr = String.format("%s/%s", docDto.getWorkApiTemplateDirStr(), Constants.API_RES_PARAM_TABLE_TEMPLATE_HTML_NAME);

        docDto.setWorkApiTemplateDirFile(new File(apiHtmlTemplateDirStr));
        docDto.setWorkReqParamTemplateFile(new File(apiReqParamTableHtmlTemplateStr));
        docDto.setWorkResParamTemplateFile(new File(apiResParamTableHtmlTemplateStr));
        docDto.setWorkReqParamDirFile(new File(docDto.getWorkReqParamDirStr()));
        docDto.setWorkResParamDirFile(new File(docDto.getWorkResParamDirStr()));
        docDto.setWorkModulesDirFile(new File(docDto.getWorkModulesDirStr()));
        docDto.setWorkSidebarTreeDirFile(new File(docDto.getWorkSidebarTreeJsonDirStr()));
    }

    /**
     * 构建模块明细
     *
     * @param docDto      Doc DTO对象
     * @param apiDocModel ApiDoc模型
     */
    private void buildModuleItem(ParserDocDto docDto, ApiDocModel apiDocModel) {
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
            apiDoc.getElementById("apiDocDiv").attr("title", apiDocModel.getModuleItem());
            apiDoc.getElementById("apiDesc").val(apiDocModel.getDesc());
            if (StringUtils.isEmpty(docDto.getDomain())) {
                apiDoc.getElementById("apiUrl").val(apiDocModel.getUrl());
            } else {
                apiDoc.getElementById("apiUrl").val(String.format("%s%s", docDto.getDomain(), apiDocModel.getUrl()));
            }
            apiDoc.getElementById("apiAuthor").val(apiDocModel.getAuthor());
            apiDoc.getElementById("apiMethod").val(apiDocModel.getMethod().toUpperCase());
            apiDoc.getElementById("apiCreateTime").val(apiDocModel.getCreateDate());
            apiDoc.getElementById("apiLstUpdTime").val(apiDocModel.getLstUpdDate() == null ? String.valueOf(new Date()) : apiDocModel.getLstUpdDate());

            List<ApiDocInputReqParamGroupModel> reqParamGroupModelList = apiDocModel.getReqParams();
            //处理请求参数
            Document reqTableDoc = Jsoup.parse(docDto.getWorkReqParamTemplateFile(), "UTF-8");
            //截取Table标签
            Element reqTableElement = reqTableDoc.getElementsByTag("table").get(0);
            Element rootElement = apiDoc.getElementById("apiBasicInfo");
            StringBuffer appendAfterHtmlText = new StringBuffer();
            for (ApiDocInputReqParamGroupModel groupModel : reqParamGroupModelList) {
                //设置标题
                reqTableElement.attr("title", groupModel.getTitle());
                //转换为工作对象 Group
                ApiDocOutputReqParamGroupModel workReqParamGroupModel = new ApiDocOutputReqParamGroupModel();
                //遍历参数Item
                for (ApiDocInputReqParamItemModel inputReqParamItemModel : groupModel.getItems()) {
                    //转换为工作对象 Item
                    ApiDocOutputReqParamItemModel workReqParamItemModel = new ApiDocOutputReqParamItemModel();
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
            for (ApiDocInputResParamGroupModel inputResParamGroupModel : apiDocModel.getResParams()) {
                //设置标题
                resTableElement.attr("title", inputResParamGroupModel.getTitle());
                //转换为工作对象 Group
                ApiDocOutputResParamGroupModel workResParamGroupModel = new ApiDocOutputResParamGroupModel();
                //遍历参数Item
                for (ApiDocInputResParamItemModel inputResParamItemModel : inputResParamGroupModel.getItems()) {
                    ApiDocOutputResParamItemModel workResParamItemModel = new ApiDocOutputResParamItemModel();
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

            this.writeJsonTextParamsToTestDialog(
                    apiDocModel.getReqParams(),
                    apiDoc,
                    apiDocModel.getMethod(),
                    StringUtils.isEmpty(docDto.getNowApiDto().getBasicUri())
                            ? docDto.getDomain() + apiDocModel.getUrl() :
                            docDto.getNowApiDto().getBasicUri() + apiDocModel.getUrl());

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
     * 生成请求参数Json文本
     *
     * @param reqParamGroupModelList 请求参数列表
     * @return 参数字典
     */
    private Map<String, Object> genTestParamJsonText(List<ApiDocInputReqParamGroupModel> reqParamGroupModelList) {
        Map<String, Object> rootParam = SetsUtils.map();
        if (reqParamGroupModelList == null || reqParamGroupModelList.isEmpty()){
            return rootParam;
        }
        ApiDocInputReqParamGroupModel groupModel = reqParamGroupModelList.get(0);
        for (ApiDocInputReqParamItemModel itemModel : groupModel.getItems()) {
            if (StringUtils.isEmpty(itemModel.getFieldsTitle())) {
                rootParam.put(itemModel.getName(), String.format("%s_%s", itemModel.getType(), itemModel.isRequired() ? "Required" : "NotRequired"));
            } else {
                rootParam.put(itemModel.getName(), recursionFields(reqParamGroupModelList, itemModel.getFieldsTitle(), itemModel.getType()));
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
    private Object recursionFields(List<ApiDocInputReqParamGroupModel> reqParamGroupModelList, String fieldsTitle, String javaTypeStr) {
        if ("List".equals(javaTypeStr) || "Set".equals(javaTypeStr) || "Collection".equals(javaTypeStr) || "Array".equals(javaTypeStr)) {
            List<Map<String, Object>> fieldsTitleList = SetsUtils.list();
            Map<String, Object> fieldsItemMap = SetsUtils.map();
            for (ApiDocInputReqParamGroupModel groupModel : reqParamGroupModelList) {
                if (!groupModel.getTitle().equals(fieldsTitle)) {
                    continue;
                }
                for (ApiDocInputReqParamItemModel itemModel : groupModel.getItems()) {
                    if (StringUtils.isEmpty(itemModel.getFieldsTitle())) {
                        fieldsItemMap.put(itemModel.getName(), String.format("%s_%s", itemModel.getType(), itemModel.isRequired() ? "Required" : "NotRequired"));
                    } else {
                        fieldsItemMap.put(itemModel.getName(), recursionFields(reqParamGroupModelList, itemModel.getFieldsTitle(), itemModel.getType()));
                    }
                }
                fieldsTitleList.add(fieldsItemMap);
                break;
            }
            return fieldsTitleList;
        } else if ("Object".equals(javaTypeStr)) {
            Map<String, Object> fieldsItemMap = SetsUtils.map();
            for (ApiDocInputReqParamGroupModel groupModel : reqParamGroupModelList) {
                if (!groupModel.getTitle().equals(fieldsTitle)) {
                    continue;
                }
                for (ApiDocInputReqParamItemModel itemModel : groupModel.getItems()) {
                    if (StringUtils.isEmpty(itemModel.getFieldsTitle())) {
                        fieldsItemMap.put(itemModel.getName(), String.format("%s_%s", itemModel.getType(), itemModel.isRequired() ? "Required" : "NotRequired"));
                    } else {
                        fieldsItemMap.put(itemModel.getName(), recursionFields(reqParamGroupModelList, itemModel.getFieldsTitle(), itemModel.getType()));
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
    private void writeJsonTextParamsToTestDialog(List<ApiDocInputReqParamGroupModel> reqParamGroupModelList,
                                                 Document apiDoc, String reqMethod, String reqUrl) throws IOException {
        if (reqParamGroupModelList == null) {
            reqParamGroupModelList = SetsUtils.list();
        }
        apiDoc.getElementById("reqURL20180606666").val(reqUrl);
        apiDoc.getElementById("reqMethod20180606666").val(reqMethod.toUpperCase());
        Map<String, Object> paramMap = genTestParamJsonText(reqParamGroupModelList);
        if (paramMap != null && !paramMap.isEmpty()){
            apiDoc.getElementById("testParamTextarea").val(JSON.toJSONString(paramMap));
        }
    }

    /**
     * 添加左侧栏
     *
     * @param docDto      Doc DTO对象
     * @param apiDocModel API DOC模型
     */
    private void addSidebarTree(ParserDocDto docDto, ApiDocModel apiDocModel) {
        SidebarTreeModel childModel = new SidebarTreeModel();
        childModel.setText(apiDocModel.getModuleItem());
        childModel.setId(docDto.getNowApiDto().getNowApiHtmlName());
        childModel.setUrl(String.format("%s/%s/%s/%s",
                docDto.getDomain(),
                Constants.OUTPUT_ROOT_DIR_NAME,
                Constants.MODULES_DIR_NAME,
                docDto.getNowApiDto().getNowApiHtmlName()));
        boolean isNotFound = true;
        for (SidebarTreeModel sidebarTreeModel : docDto.getSidebarTreeModelList()) {
            if (sidebarTreeModel.getId().equals(apiDocModel.getModule())) {
                sidebarTreeModel.getChildren().add(childModel);
                isNotFound = false;
                break;
            }
        }
        if (isNotFound) {
            SidebarTreeModel rootModel = new SidebarTreeModel();
            rootModel.setText(apiDocModel.getModule());
            rootModel.setId(apiDocModel.getModule());
            rootModel.getChildren().add(childModel);
            docDto.getSidebarTreeModelList().add(rootModel);
        }
    }
}
