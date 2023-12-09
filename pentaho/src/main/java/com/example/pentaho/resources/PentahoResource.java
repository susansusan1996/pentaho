package com.example.pentaho.resources;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class PentahoResource {

    private final Logger log = LoggerFactory.getLogger(PentahoResource.class);

    /**
     * 啟動JOB
     */
    @GetMapping("/run-pentaho")
    public void runPentaho() {
        log.info("JOB啟動");
        String file = "src/main/resources/Job_1.kjb";
        Repository repository = null;
        try {
            KettleEnvironment.init();
            JobMeta jobmeta = new JobMeta(file, repository);
            Job job = new Job(repository, jobmeta);
            job.start();
            job.waitUntilFinished();
            if (job.getErrors() > 0) {
                log.error("JOB執行失敗");
            } else {
                log.info("JOB執行成功");
            }
        } catch (KettleException e) {
            throw new RuntimeException(e);
        }
    }
}
