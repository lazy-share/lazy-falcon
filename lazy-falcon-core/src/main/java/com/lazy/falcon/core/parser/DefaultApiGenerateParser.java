package com.lazy.falcon.core.parser;

import com.lazy.falcon.core.parser.support.AbstractApiGenerateParser;
import com.lazy.falcon.core.parser.support.AbstractApiModuleParser;

/**
 * <p>
 * Default Api Generate Parser
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
public class DefaultApiGenerateParser extends AbstractApiGenerateParser {


    public DefaultApiGenerateParser(AbstractApiModuleParser apiModuleParser) {
        super(apiModuleParser);
    }

    /**
     * 解析
     */
    @Override
    public void doParser() {
        this.initParser(getGenerateDto());
        for (Class clazz : getGenerateDto().getNowApiModuleClasses()) {
            apiModuleParser.doParser(clazz);
        }
    }

}
