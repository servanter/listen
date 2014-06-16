package com.zhy.listen.template;

public interface TemplateService {

    public String getMessage(String key, String... args);

    public boolean contains(String key);
}
