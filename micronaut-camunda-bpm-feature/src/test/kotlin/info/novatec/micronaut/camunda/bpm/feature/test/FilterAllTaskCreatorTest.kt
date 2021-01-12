package info.novatec.micronaut.camunda.bpm.feature.test

import info.novatec.micronaut.camunda.bpm.feature.FilterAllTaskCreator
import info.novatec.micronaut.camunda.bpm.feature.MnProcessEngineConfiguration
import io.micronaut.context.ApplicationContext
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import org.camunda.bpm.engine.ProcessEngine
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class FilterAllTaskCreatorTest {

    @MicronautTest
    @Nested
    inner class FilterNotCreated {

        @Inject
        lateinit var processEngine: ProcessEngine

        @Inject
        lateinit var filterAllTaskCreator: Optional<FilterAllTaskCreator>

        @Test
        fun `filter not created` () {
            assertFalse(filterAllTaskCreator.isPresent)
            assertEquals(null, processEngine.filterService.createFilterQuery().filterName("All tasks").singleResult())
        }

    }

    @MicronautTest
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    inner class FilterCreated : TestPropertyProvider {
        override fun getProperties(): MutableMap<String, String> {
            return mutableMapOf(
                "camunda.bpm.filter" to "All tasks"
            )
        }

        @Inject
        lateinit var processEngine: ProcessEngine

        @Inject
        lateinit var filterAllTaskCreator: Optional<FilterAllTaskCreator>

        @Test
        fun `filter created` () {
            assertTrue(filterAllTaskCreator.isPresent)
            assertNotNull(processEngine.filterService.createFilterQuery().filterName("All tasks").singleResult())
        }

        @Test
        fun `filter creation fails due to no name` () {

        }
    }

    /*
    TODO Test that throws the Error when no name for the filter is provided
    @MicronautTest
    @Nested
    inner class FilterCreatedFail {


        @Test
        fun `filter creation fails` () {


                val properties = mapOf<String, String?> (
                    "camunda.bpm.filter" to null
                )
                ApplicationContext.run(properties).use {
                    assertThrows(Exception::class.java) { it}
                }

        }
    }*/


}