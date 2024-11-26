module org.heliosx.consultation {
    requires java.logging;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires org.apache.commons.io;
    requires openapi.specification;
    requires spring.web;
    requires com.alibaba.fastjson2;

    exports org.heliosx.consultation.domain.model;
    exports org.heliosx.consultation.domain.model.rules;

    opens org.heliosx.consultation.infrastructure.web.dto;
    opens org.heliosx.consultation.config;
}
