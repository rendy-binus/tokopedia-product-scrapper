package com.example.brickscrapper.tasklet;

import com.example.brickscrapper.model.TokopediaProduct;
import com.example.brickscrapper.repository.TokopediaProductRepository;
import com.example.brickscrapper.service.ScrapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokopediaScrapperTasklet implements Tasklet, StepExecutionListener {

    private List<TokopediaProduct> products;

    private final TokopediaProductRepository productRepository;

    private final ScrapperService<TokopediaProduct> scrapperService;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.debug("TokopediaScrapperTasklet beforeStep");

        this.products = new ArrayList<>();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.debug("TokopediaScrapperTasklet afterStep");



        //System.out.println(products);
        return ExitStatus.COMPLETED;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.debug("TokopediaScrapperTasklet execute");

        products = scrapperService.startScrapping();

        productRepository.saveAllAndFlush(products);


        return RepeatStatus.FINISHED;
    }

}
