package ru.innopolis.gr9.task7;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ReadFile {

    final static Logger logger = Logger.getLogger(ReadFile.class);

    /**
     * Получить InputStream для разного вида контенета
     */
    public InputStream getInputStream(String path) throws IOException {

        logger.info("Scan file "+path);

        if(path.indexOf("http")==0 || path.indexOf("ftp")==0) {
            URL url = new URL(path);
            URLConnection urlc = url.openConnection();
            return urlc.getInputStream();
        }

        return new FileInputStream(path);
    }
}
