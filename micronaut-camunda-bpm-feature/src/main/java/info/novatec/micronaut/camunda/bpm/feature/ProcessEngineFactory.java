package info.novatec.micronaut.camunda.bpm.feature;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Tobias Schäfer
 */
@Factory
public class ProcessEngineFactory {

    public static final String MICRONAUT_AUTO_DEPLOYMENT_NAME = "MicronautAutoDeployment";

    private static final Logger log = LoggerFactory.getLogger(ProcessEngineFactory.class);

    protected final ProcessEngineConfiguration processEngineConfiguration;
    protected final CamundaVersion camundaVersion;

    public ProcessEngineFactory(ProcessEngineConfiguration processEngineConfiguration, CamundaVersion camundaVersion) {
        this.processEngineConfiguration = processEngineConfiguration;
        this.camundaVersion = camundaVersion;
    }

    /**
     * The {@link ProcessEngine} is started with the application start so that the task scheduler is started immediately.
     *
     * @return the initialized {@link ProcessEngine} in the application context.
     * @throws IOException if a resource, i.e. a model, cannot be loaded.
     */
    @Context
    @Bean(preDestroy = "close")
    public ProcessEngine processEngine() throws IOException {

        if (camundaVersion.getVersion().isPresent()) {
            log.info("Camunda version: {}", camundaVersion.getVersion().get());
        } else {
            log.warn("The Camunda version cannot be determined. If you created a Fat/Uber/Shadow JAR then please consider using the Micronaut Application Plugin's 'dockerBuild' task to create a Docker image.");
        }

        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();

        deployProcessModels(processEngine);

        return processEngine;
    }

    /**
     * Deploys all process models found in root directory of the resources.
     * <p>
     * Note: Currently this is not recursive!
     *
     * @param processEngine the {@link ProcessEngine}
     * @throws IOException if a resource, i.e. a model, cannot be loaded.
     */
    protected void deployProcessModels(ProcessEngine processEngine) throws IOException {
        log.info("Searching non-recursively for models in the resources");
        PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
        // Order of extensions has been chosen as a best fit for inter process dependencies.
        for (String extension : Arrays.asList("dmn", "bpmn")) {
            for (Resource resource : resourceLoader.getResources(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "*." + extension)) {
                log.info("Deploying model: {}", resource.getFilename());
                processEngine.getRepositoryService().createDeployment()
                        .name(MICRONAUT_AUTO_DEPLOYMENT_NAME)
                        .addInputStream(resource.getFilename(), resource.getInputStream())
                        .enableDuplicateFiltering(true)
                        .deploy();
            }
        }
    }
}
