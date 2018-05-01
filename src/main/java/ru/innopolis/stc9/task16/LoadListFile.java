package ru.innopolis.stc9.task16;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;

public class LoadListFile {

    final static Logger logger = Logger.getLogger(LoadListFile.class);


    /**
     * Загрузить список ссылок на сайте
     *
     * @param count
     * @param urlXML
     * @return
     */
    public List<String> loadListData(int count, String urlXML) {

        List<String> newList = new ArrayList<String>() {
        };

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(urlXML);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("url");

            for (int temp = 0; temp < Math.min(count, nList.getLength()); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    String url = eElement.getElementsByTagName("loc").item(0).getTextContent();

                    url = url.replace("http://tululu.org/b", "http://tululu.org/txt.php?id=");

                    newList.add(url.substring(0, url.length() - 1));

                }
            }
            return newList;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return newList;
        }
    }

    /**
     * Загрузить список файлов с каталога file
     * @param countFile
     * @return
     */
    public List<String> loadListInFile(int countFile) {
        List<String> newList = new ArrayList<String>() {
        };

        File folder = new File("file/");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < Math.min(listOfFiles.length, countFile); i++) {
            if (listOfFiles[i].isFile()) {
                newList.add("file/" + listOfFiles[i].getName());
            }
        }
        return newList;
    }

    /**
     * Получить размер локальных файлов и отсортировать
     * @param listFile
     * @param sortType
     * @return
     */
    public List<String> sortListInFile(List<String> listFile) {
        HashMap<String, Long> map = new HashMap<>();

        for (String path : listFile) {
            File file = new File(path);
            map.put(path, (long) file.length());
        }

        return sortHashMapByValues(map);
    }

    /**
     * Сортировка Таблицы по значению
     * @param passedMap
     * @return
     */
    public List<String> sortHashMapByValues(
            HashMap<String, Long> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Long> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues,Collections.reverseOrder());
        Collections.sort(mapKeys,Collections.reverseOrder());

        LinkedHashMap<String, Long> sortedMap =
                new LinkedHashMap<>();

        Iterator<Long> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Long val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Long comp1 = passedMap.get(key);
                Long comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return new ArrayList<>(sortedMap.keySet());
    }

    /**
     * Скачать файл по URL
     * @param urlString
     * @param destination
     */
    public void downloadFileFromURL(String urlString, String destination) {
        try {
            URL website = new URL(urlString);
            if (!doesURLExist(website)) {
                return;
            }
            ReadableByteChannel rbc;
            rbc = Channels.newChannel(website.openStream());
            File file = new File(destination);
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Скачать файлы по списку
     * @param fileName
     */
    public void downloadFiles(ArrayList<String> fileName) {
        for (String nameF : fileName) {

            downloadFileFromURL(nameF, "file/" + nameF.replace("http://tululu.org/txt.php?id=", "") + ".txt");

        }
    }

    /**
     * Проверка на существования файла поURL
     * @param url
     * @return
     * @throws IOException
     */
    public static boolean doesURLExist(URL url) throws IOException {
        // We want to check the current URL
        HttpURLConnection.setFollowRedirects(false);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        // We don't need to get data
        httpURLConnection.setRequestMethod("HEAD");

        // Some websites don't like programmatic access so pretend to be a browser
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
        int responseCode = httpURLConnection.getResponseCode();

        // We only accept response code 200
        return responseCode == HttpURLConnection.HTTP_OK;
    }

    /**
     * Создать большой файл по списку URL
     * @param sizeFile
     * @param fileName
     */
    public void createBigFile(int sizeFile, ArrayList<String> fileName) {

        List<String> newList = new ArrayList<String>() {
        };

        try {

            FileWriter writer = new FileWriter("big.txt");

            for (String nameF : fileName) {

                URL url = new URL(nameF);
                URLConnection urlc = url.openConnection();
                InputStream is = urlc.getInputStream(); // To download


                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.flush();
                }

                reader.close();

            }
            writer.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

}
