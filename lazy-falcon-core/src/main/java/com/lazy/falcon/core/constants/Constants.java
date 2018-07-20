package com.lazy.falcon.core.constants;

import com.alibaba.fastjson.JSON;
import com.lazy.falcon.core.tools.SetsUtils;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * <p>
 *     常量
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/7.
 */
public class Constants {

    public static final  String DOC_TEMPLATE_DIR_NAME = "doc_template";
    public static final  String API_TEMPLATE_DIR_NAME = "api_template";
    public static final  String SIDEBAR_TREE_FILE_NAME = "sidebar_tree.json";
    public static final  String INDEX_HTML_FILE_NAME = "index.html";
    public static final  String MODULES_DIR_NAME = "modules";
    public static final  String API_HTML_TEMPLATE_NAME = "api.html";
    public static final  String API_REQ_PARAM_TABLE_TEMPLATE_HTML_NAME = "api_req_param_table.html";
    public static final  String API_RES_PARAM_TABLE_TEMPLATE_HTML_NAME = "api_res_param_table.html";

    public static final String OUTPUT_ROOT_DIR_NAME = "api_doc";
    public static final String REQ_PARAM_JSON_FILE_OUTPUT_DIR = "req";
    public static final String RES_PARAM_JSON_FILE_OUTPUT_DIR = "res";

    public static final String[] TEMPLATE_FILES_DIR = {"css/easyui.css", "css/icon.css", "css/icons/back.png", "css/icons/blank.gif", "css/icons/cancel.png", "css/icons/clear.png", "css/icons/cut.png", "css/icons/edit_add.png", "css/icons/edit_remove.png", "css/icons/filesave.png", "css/icons/filter.png", "css/icons/help.png", "css/icons/large_chart.png", "css/icons/large_clipart.png", "css/icons/large_picture.png", "css/icons/large_shapes.png", "css/icons/large_smartart.png", "css/icons/lock.png", "css/icons/man.png", "css/icons/mini_add.png", "css/icons/mini_edit.png", "css/icons/mini_refresh.png", "css/icons/more.png", "css/icons/no.png", "css/icons/ok.png", "css/icons/pencil.png", "css/icons/print.png", "css/icons/redo.png", "css/icons/reload.png", "css/icons/search.png", "css/icons/sum.png", "css/icons/tip.png", "css/icons/undo.png", "css/images/accordion_arrows.png", "css/images/blank.gif", "css/images/calendar_arrows.png", "css/images/combo_arrow.png", "css/images/datagrid_icons.png", "css/images/datebox_arrow.png", "css/images/layout_arrows.png", "css/images/linkbutton_bg.png", "css/images/loading.gif", "css/images/menu_arrows.png", "css/images/messager_icons.png", "css/images/pagination_icons.png", "css/images/panel_tools.png", "css/images/passwordbox_close.png", "css/images/passwordbox_open.png", "css/images/searchbox_button.png", "css/images/slider_handle.png", "css/images/spinner_arrows.png", "css/images/tabs_icons.png", "css/images/tagbox_icons.png", "css/images/tree_icons.png", "css/images/validatebox_warning.png", "index.html", "js/jquery.easyui.min.js", "js/jquery.min.js", "modules/api_template/api.html", "modules/api_template/api_req_param_table.html", "modules/api_template/api_res_param_table.html", "modules/example.html", "modules/req/req_param_example.json", "modules/res/res_param_example.json", "sidebar_tree.json"};


    /**
     * 测试 main method
     * @param args
     */
    public static void main(String[] args) {
        URL url = Constants.class.getClassLoader().getResource("doc_template");
        File file = new File(url.getFile());
        List<String> path = SetsUtils.list();
        doscan(file, path);
        System.out.println(JSON.toJSONString(path));
    }

    private static void doscan(File dir, List<String> fileNames) {
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                doscan(f, fileNames);
            } else {
                System.out.println(f.getPath().split("doc_template\\\\")[1]);
                fileNames.add(f.getPath().split("doc_template\\\\")[1].replaceAll("\\\\", "/"));
            }
        }
    }
}
