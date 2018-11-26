package com.lazy.falcon.core.parser;

import com.lazy.falcon.core.loader.GenDocApiClassLoader;
import com.lazy.falcon.core.parser.dto.ApiGenerateDto;
import com.lazy.falcon.core.parser.support.AbstractApiGenerateParser;
import com.lazy.falcon.core.parser.support.AbstractApiModuleParser;
import com.lazy.falcon.core.parser.support.AbstractApiParser;
import org.apache.maven.plugin.logging.Log;

/**
 * <p>
 * Parser Bootstrap
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
public class ParserBootstrap {

    /**
     * Something Att
     */
    private Log log;
    private ApiGenerateDto generateDto;
    private GenDocApiClassLoader loader;

    /**
     * Start
     */
    public void run() {
        AbstractApiParser apiParser = new ObserverApiParser();
        apiParser.setLog(log).setGenerateDto(generateDto).setLoader(loader);

        AbstractApiModuleParser apiModuleParser = new DefaultApiModuleParser(apiParser);
        apiModuleParser.setLog(log).setGenerateDto(generateDto);

        AbstractApiGenerateParser generateParser = new DefaultApiGenerateParser(apiModuleParser);
        generateParser.setLog(log).setGenerateDto(generateDto);

        generateParser.doParser();
    }

    public GenDocApiClassLoader getLoader() {
        return loader;
    }

    public ParserBootstrap setLoader(GenDocApiClassLoader loader) {
        this.loader = loader;
        return this;
    }

    public Log getLog() {
        return log;
    }

    public ParserBootstrap setLog(Log log) {
        this.log = log;
        return this;
    }

    public ApiGenerateDto getGenerateDto() {
        return generateDto;
    }

    public ParserBootstrap setGenerateDto(ApiGenerateDto generateDto) {
        this.generateDto = generateDto;
        return this;
    }
}
