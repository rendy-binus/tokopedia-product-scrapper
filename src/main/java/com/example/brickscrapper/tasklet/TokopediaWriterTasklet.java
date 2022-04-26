package com.example.brickscrapper.tasklet;

import com.example.brickscrapper.config.ScrapperConfiguration;
import com.example.brickscrapper.model.TokopediaProduct;
import com.example.brickscrapper.repository.TokopediaProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokopediaWriterTasklet implements Tasklet, StepExecutionListener {

    private final ScrapperConfiguration scrapperConfiguration;

    private final TokopediaProductRepository productRepository;

    private final FlatFileItemWriter<TokopediaProduct> csvWriter;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("preparing to write csv");
        csvWriter.open(stepExecution.getExecutionContext());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        csvWriter.close();
        log.info("output csv file: {}", scrapperConfiguration.getTokopedia().getProduct().getFileOutputPath());
        return ExitStatus.COMPLETED;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("writing to csv");

        List<TokopediaProduct> products = productRepository.findAll();

        csvWriter.write(products);

        return RepeatStatus.FINISHED;
    }
}
