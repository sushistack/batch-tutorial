package com.deeplify.tutorial.batch.jobs;

import com.deeplify.tutorial.batch.tasklets.TutorialTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TutorialConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job tutorialJob() {
        return jobBuilderFactory.get("tutorialJob")
                .start(tutorialStep())
                .build();
    }

    @Bean
    public Step tutorialStep() {
        return stepBuilderFactory.get("tutorialStep")
                .tasklet(new TutorialTasklet())
                .build();
    }
}
