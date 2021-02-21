package com.deeplify.tutorial.batch.tasklets;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@StepScope
@RequiredArgsConstructor
public class TutorialTasklet implements Tasklet {

    @Value("#{jobParameters[datetime]}")
    private String datetime;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.debug("# executed tasklet !!");
        log.debug("datetime: {}", datetime);
        return RepeatStatus.FINISHED;
    }
}
