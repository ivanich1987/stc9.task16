package ru.innopolis.gr9.task7;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ScanFile {

    final static Logger logger = Logger.getLogger(Occurencies.class);

    private WriteThread thread;

    public ScanFile(WriteThread thread) {

        this.thread = thread;

    }

    /**
     * Сканоровать файл на наличие наличие слов
     * @param path  путь к Файлу
     * @param words список слов
     * @param thId
     * @return
     * @throws IOException
     */
    public Boolean scanFile(String path, String[] words, int thId) throws IOException {

        if(path.getClass()!=String.class && path.length()==0) return false;

        InputStream is = new ReadFile().getInputStream(path); // To download

        if(is==null) return false;

        try (Scanner scanner = new Scanner(is)) {
            String strDelimiter = "\\.";
            scanner.useDelimiter(strDelimiter);
            while (scanner.hasNext()) {
                String text = scanner.next();
                // Пропуск больших предложений
                if (text.length() > 25000) {
                    continue;
                }
                if(findWordFromString(text,words)){
                    thread.setBufferSetString(text);
                }
            }
            scanner.close();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * поиск слова в предложение
     * @param text
     * @param words
     * @return
     */
    private boolean findWordFromString(String text, String[] words){
        String lowerText = text.toLowerCase();
        for (String searchWord : words) {
            if (lowerText.contains(searchWord)) {
                return true;
            }
        }
        return false;
    }


}
