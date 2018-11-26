package com.lazy.falcon.core.parser.support;

import com.lazy.falcon.core.constants.Constants;
import com.lazy.falcon.core.parser.dto.ApiGenerateDto;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;

/**
 * <p>
 * Api Generate Parser Abstract
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/10/15.
 */
public abstract class AbstractApiGenerateParser extends AbstractComponent {

    protected AbstractApiModuleParser apiModuleParser;

    public AbstractApiGenerateParser(AbstractApiModuleParser apiModuleParser) {
        this.apiModuleParser = apiModuleParser;
    }

    /**
     * 解析工作
     */
    public abstract void doParser();

    /**
     * 初始化工作
     *
     * @param docDto 文档Dto
     */
    protected void initParser(ApiGenerateDto docDto) {
        this.copyTemplateToWorkDir(docDto);
        this.initIndexHtmlTitle(docDto);
        this.initDirStr(docDto);
        this.initDirFile(docDto);
    }

    /**
     * 初始化目录文件File 对象
     *
     * @param docDto Doc DTO对象
     */
    private void initDirFile(ApiGenerateDto docDto) {
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
     * 初始化目录字符串
     *
     * @param docDto Doc DTO对象
     */
    private void initDirStr(ApiGenerateDto docDto) {
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
     * 初始化首页标题
     *
     * @param docDto Doc DTO对象
     */
    private void initIndexHtmlTitle(ApiGenerateDto docDto) {
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
    private void copyTemplateToWorkDir(ApiGenerateDto docDto) {
        //从模板复制到Client项目的ClassPath下
        InputStream inputStream = null;
        File workDir;
        try {
            for (String fileDir : Constants.TEMPLATE_FILES_DIR) {
                try {
                    inputStream = this.getClass().getClassLoader().getResourceAsStream(String.format("%s/%s", "doc_template", fileDir));
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


}
