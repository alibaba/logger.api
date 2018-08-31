package com.taobao.middleware.logger;

import com.taobao.middleware.logger.json.JSONObject;
import com.taobao.middleware.logger.support.Log4jHelper;
import com.taobao.middleware.logger.support.LogbackHelper;
import com.taobao.middleware.logger.support.LoggerInfo;
import com.taobao.pandora.pandolet.annotation.Parameter;
import com.taobao.pandora.pandolet.annotation.Service;
import com.taobao.pandora.pandolet.builder.ResponseBuilder;
import com.taobao.pandora.pandolet.domain.PandoletResponse;
import com.taobao.pandora.pandolet.domain.PandoletSupport;

import java.util.Map;

/**
 */
public class LoggerPandolet extends PandoletSupport {

    @Override
    public String getName() {
        return "log-console";
    }

    @Service
    public PandoletResponse changeLevel(@Parameter(name = "name") String name,
                                        @Parameter(name = "level") String level) {
        ResponseBuilder builder = pandoletService.createResponseBuilder();
        builder.status(200);
        Boolean log4j = Log4jHelper.changeLevel(name, level);
        if (log4j != null) {
            builder.name("log4j").boolValue(log4j.booleanValue());
        }
        Boolean logback = LogbackHelper.changeLevel(name, level);
        if (logback != null) {
            builder.name("logback").boolValue(logback.booleanValue());
        }

        return builder.status("success").build();
    }

    @Service
    public PandoletResponse changeStack(@Parameter(name = "depth") int depth) {
        ResponseBuilder builder = pandoletService.createResponseBuilder();
        builder.status(200);

        Boolean log4j = Log4jHelper.setDepth(depth), logback = LogbackHelper.setDepth(depth);
        if (log4j != null) {
            builder.name("log4j").boolValue(log4j.booleanValue());
        }
        if (logback != null) {
            builder.name("logback").boolValue(logback.booleanValue());
        }

        return builder.status("success").build();
    }

    @Service
    public PandoletResponse loggers(@Parameter(name = "name") String name) {
        ResponseBuilder builder = pandoletService.createResponseBuilder();
        builder.status(200).status("success");

        Map<String, LoggerInfo> log4j = Log4jHelper.getLoggers(name);
        Map<String, LoggerInfo> logback = LogbackHelper.getLoggers(name);

        if (!log4j.isEmpty()) {
            builder.name("log4j").stringValue(JSONObject.toJSONString(log4j));
        }

        if (!logback.isEmpty()) {
            builder.name("logback").stringValue(JSONObject.toJSONString(logback));
        }

        return builder.build();
    }
}
