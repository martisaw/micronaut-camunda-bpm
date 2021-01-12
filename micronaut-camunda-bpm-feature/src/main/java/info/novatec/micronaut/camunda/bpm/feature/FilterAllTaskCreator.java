package info.novatec.micronaut.camunda.bpm.feature;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Optional;
/**
 * Bean creating an initial filter if {@code camunda.bpm.filter} property is present.
 *
 * @author Martin Sawilla
 */
// Implementation based on https://github.com/camunda/camunda-bpm-spring-boot-starter/blob/master/starter/src/main/java/org/camunda/bpm/spring/boot/starter/configuration/impl/custom/CreateFilterConfiguration.java
@Singleton
@Requires(property = "camunda.bpm.filter")
public class FilterAllTaskCreator implements ApplicationEventListener<ServerStartupEvent> {
    private static final Logger log = LoggerFactory.getLogger(FilterAllTaskCreator.class);

    protected final ProcessEngine processEngine;
    protected String filterName;

    public FilterAllTaskCreator(final ProcessEngine processEngine, Configuration configuration) throws Exception {
        this.processEngine = processEngine;
        Optional.ofNullable(configuration.getFilter())
                .map(value -> this.filterName = value)
                .orElseThrow(() -> new Exception("You have to provide a name for the filter"));
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        Filter filter = processEngine.getFilterService().createFilterQuery().filterName(filterName).singleResult();
        if (filter == null) {
            filter = processEngine.getFilterService().newTaskFilter(filterName);
            processEngine.getFilterService().saveFilter(filter);
            log.info("Created initial '"+filterName+"' filter");
        }
    }
}
