package ru.innopolis.stc9.task16;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Occurencies{

    final static Logger logger = Logger.getLogger(Occurencies.class);

    private int countThread = 1;

    public Occurencies(int countThread) {
        this.countThread = countThread;
    }

    public void getOccurencies(String[] sources, String[] words, String res) throws ExecutionException, InterruptedException, IOException {

        logger.info("Start scannig");

        //Создание потока для записи в файл
        WriteThread thread = new WriteThread(res);
        thread.start();

        //Создание пула потоков для чтения файлов
        ExecutorService threadPool = Executors.newFixedThreadPool(countThread);
        List<Future<Boolean>> futures = new ArrayList<>();
        int i = 0;
        ScanFile scanFile = new ScanFile(thread);
        for(String path:sources){
            final String pathF = path;
            final int j = i++;
            final String[] wordsF = words;
            futures.add(
                    CompletableFuture.supplyAsync(
                            () -> {
                                try {
                                    return scanFile.scanFile(pathF, wordsF, j);
                                } catch (IOException e) {
                                    return false;
                                }
                            },
                            threadPool
                    ));

        }

        for (Future<Boolean> future : futures) {
            future.get();
        }

        thread.stopWriter();
        logger.info("Stop scannig");
    }


}
