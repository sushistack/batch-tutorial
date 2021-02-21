package com.deeplify.tutorial.batch.jobs;

import com.deeplify.tutorial.batch.tasklets.TutorialTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TutorialConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job tutorialJob() {
        return jobBuilderFactory.get("tutorialJob")
                .start(tutorialStep1())
                .next(tutorialStep2())
                .build();
    }

    @Bean
    public Step tutorialStep1() {
        return stepBuilderFactory.get("tutorialStep1")
                .tasklet(new TutorialTasklet())
                .build();
    }

    @Bean
    public Step tutorialStep2() {
        return stepBuilderFactory.get("tutorialStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.debug("I'm a tutorialStep2");
                        return RepeatStatus.FINISHED;
                })
                .build();
    }
}
