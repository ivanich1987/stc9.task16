package ru.innopolis.stc9.task16;

import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

    final static Logger logger = Logger.getLogger(WriteFile.class);

    private FileWriter writer;

    public WriteFile(String res) throws IOException {
        try {
            writer = new FileWriter(res);
        }
        catch (IOException e){
            logger.error("Error create file");
        }
    }

    /**
     * Запись строки в файл
     * @param str
     * @return
     */
    public synchronized boolean writeText(String str) {
        try {
            writer.write(str);
        }
        catch (IOException e){
            logger.error("Error write file");
            return false;
        }
        return true;
    }

    /**
     * Закрыть файл
     * @throws IOException
     */
    public void closeFile() throws IOException {
        writer.close();
    }
}
