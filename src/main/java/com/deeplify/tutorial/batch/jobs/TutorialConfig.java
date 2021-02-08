package com.deeplify.tutorial.batch.jobs;

import com.deeplify.tutorial.batch.tasklets.TutorialTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
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
                .start(tutorialStep())
                .build();
    }

    @Bean
    public Step tutorialStep() {
        return stepBuilderFactory.get("tutorialStep")
                .tasklet(new TutorialTasklet())
                .build();
    }

    @Bean
    public Job sequentialStepFlowJob() {
        return jobBuilderFactory.get("sequentialStepFlowJob")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    log.debug("I'm STEP1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    log.debug("I'm STEP2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    log.debug("I'm STEP3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Job conditionalStepFlowJob() {
        return jobBuilderFactory.get("conditionalStepFlowJob")
                .start(conditionStep1())
                    .on("FAILED")
                    .end()
                .from(conditionStep1())
                    .on("STOPPED")
                    .to(printWhenStopStep())
                    .end()
                .build();
    }

    @Bean
    public Step conditionStep1() {
        return stepBuilderFactory.get("conditionStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.debug("I'm conditionStep1");
                    log.debug("set Exit Status to {}", ExitStatus.STOPPED);
                    contribution.setExitStatus(ExitStatus.STOPPED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step printWhenStopStep() {
        return stepBuilderFactory.get("printWhenStopStep")
                .tasklet((contribution, chunkContext) -> {
                    log.debug("I'm printWhenStopStep!");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
