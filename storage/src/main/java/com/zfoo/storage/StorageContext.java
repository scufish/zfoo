/*
 * Copyright (C) 2020 The zfoo Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.zfoo.storage;

import com.zfoo.protocol.util.StringUtils;
import com.zfoo.storage.interpreter.IResourceReader;
import com.zfoo.storage.manager.IStorageManager;
import com.zfoo.storage.schema.ResInjectionProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.Ordered;
import org.springframework.core.convert.ConversionService;

/**
 * @author jaysunxiao
 * @version 3.0
 */
public class StorageContext implements ApplicationListener<ApplicationContextEvent>, Ordered {

    private static StorageContext instance;

    private ApplicationContext applicationContext;

    private IResourceReader resourceReader;

    private IStorageManager storageManager;

    private ConversionService conversionService;

    public static StorageContext getStorageContext() {
        return instance;
    }

    public static ApplicationContext getApplicationContext() {
        return instance.applicationContext;
    }

    public static StorageContext getInstance() {
        return instance;
    }

    public static ConversionService getConversionService() {
        return instance.conversionService;
    }

    public static IResourceReader getResourceReader() {
        return instance.resourceReader;
    }

    public static IStorageManager getStorageManager() {
        return instance.storageManager;
    }

    public static void injectResource() {
        var applicationContext = instance.applicationContext;
        var beanNames = applicationContext.getBeanDefinitionNames();
        var processor = applicationContext.getBean(ResInjectionProcessor.class);

        for (var beanName : beanNames) {
            processor.postProcessAfterInitialization(applicationContext.getBean(beanName), beanName);
        }
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {

        if (event instanceof ContextRefreshedEvent) {
            if (instance != null) {
                return;
            }
            // 初始化上下文
            StorageContext.instance = this;
            instance.applicationContext = event.getApplicationContext();
            instance.conversionService = (ConversionService) applicationContext.getBean(StringUtils.uncapitalize(ConversionServiceFactoryBean.class.getName()));
            instance.resourceReader = applicationContext.getBean(IResourceReader.class);
            instance.storageManager = applicationContext.getBean(IStorageManager.class);

            // 初始化，并读取配置表
            instance.storageManager.initBefore();

            // 注入配置表资源
            injectResource();

            // 移除没有被引用的不必要资源，为了节省服务器内存
            instance.storageManager.initAfter();
        } else if (event instanceof ContextClosedEvent) {

        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
