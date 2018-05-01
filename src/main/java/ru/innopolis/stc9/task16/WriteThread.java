package ru.innopolis.stc9.task16;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WriteThread extends Thread{

    private WriteFile writeFile;
    private boolean runForestRun = true;
    private Set<String> bufferSet;
    final static Logger logger = Logger.getLogger(WriteThread.class);


    /**
     * Создать поток записи и создать файл для записи
     * @param res название файла
     */
    public WriteThread(String res) {
        this.setName("WriteText");
        try {
            bufferSet = new HashSet<>();
            writeFile = new WriteFile(res);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Добавить преложение в очередб на запись в файл
     * @param str
     */
    public void setBufferSetString(String str){
        synchronized (bufferSet) {
            bufferSet.add(str);
        }
    }

    /**
     * Остановить запись в файл
     */
    public void stopWriter() {
        this.runForestRun = false;
        try {
            writeFile.closeFile();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        this.interrupt();
    }

    @Override
    public void run() {

        while (runForestRun){
            synchronized (bufferSet) {
                if (bufferSet.size() > 0) {
                    String joined = String.join("\\n", bufferSet);
                    bufferSet.clear();
                    writeFile.writeText(joined);
                }
            }

        }
    }
}
