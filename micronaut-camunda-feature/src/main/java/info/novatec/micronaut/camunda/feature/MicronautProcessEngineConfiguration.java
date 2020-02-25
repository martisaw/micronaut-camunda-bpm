package info.novatec.micronaut.camunda.feature;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.micronaut.cli.io.support.PathMatchingResourcePatternResolver;
import io.micronaut.cli.io.support.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Factory
public class MicronautProcessEngineConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MicronautProcessEngineConfiguration.class);
    public static final String MICRONAUT_AUTO_DEPLOYMENT_NAME = "MicronautAutoDeployment";
    public static final String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

    @Inject
    private ApplicationContext applicationContext;

    /**
     * The {@link ProcessEngine} is started with the application start so that the task scheduler is started immediately.
     *
     * @return the initialized {@link ProcessEngine} in the application context.
     */
    @Context
    public ProcessEngine processEngine() throws IOException {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000")
                .setJobExecutorActivate(true);

        ((ProcessEngineConfigurationImpl)processEngineConfiguration).setExpressionManager(new MicronautExpressionManager(new ApplicationContextElResolver(applicationContext)));

        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        deployProcessModels(processEngine);

        return processEngine;
    }


    private void deployProcessModels(ProcessEngine processEngine) throws IOException {
        PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();

        // Order of extensions has been chosen as a best fit for inter process dependencies.
        for (String extension : Arrays.asList("dmn", "cmmn", "bpmn")) {
            for (Resource resource : resourceLoader.getResources(CLASSPATH_ALL_URL_PREFIX +  "**/*." + extension)) {
                log.info("Deploying model from classpath: {}", resource.getFilename());
                processEngine.getRepositoryService().createDeployment()
                        .name(MICRONAUT_AUTO_DEPLOYMENT_NAME)
                        .addInputStream(resource.getFilename(), resource.getInputStream())
                        .deploy();
            }
        }
    }






    /**
     * Creates a bean for the {@link RuntimeService} in the application context.
     * @param processEngine the {@link ProcessEngine}
     * @return the {@link RuntimeService}
     */
    @Singleton
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Singleton
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Singleton
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }

    @Singleton
    public AuthorizationService authorizationService(ProcessEngine processEngine) {
        return processEngine.getAuthorizationService();
    }

    @Singleton
    public CaseService caseService(ProcessEngine processEngine) {
        return processEngine.getCaseService();
    }

    @Singleton
    public DecisionService decisionService(ProcessEngine processEngine) {
        return processEngine.getDecisionService();
    }

    @Singleton
    public ExternalTaskService externalTaskService(ProcessEngine processEngine) {
        return processEngine.getExternalTaskService();
    }

    @Singleton
    public FilterService filterService(ProcessEngine processEngine) {
        return processEngine.getFilterService();
    }

    @Singleton
    public FormService formService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }

    @Singleton
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Singleton
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    @Singleton
    public IdentityService identityService (ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }

}