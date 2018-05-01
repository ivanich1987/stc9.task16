package ru.innopolis.stc9.task16;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        LoadListFile loadListFile = new LoadListFile();

        List<String > listFile = new ArrayList<>();

        //скачасть файлы
        //loadListFile.downloadFiles((ArrayList<String>) listFile);
        //Создать большой файл
        // loadListFile.createBigFile(1200000, (ArrayList<String>) listFile);

        //Добавить файлы из локальной папки file
        listFile.addAll(loadListFile.loadListInFile(100000));

        //Добавить ссылки на файлы в интернете
        //listFile.addAll(loadListFile.loadListData(100,"http://tululu.org/sitemap-file-3.xml"));

        //Добавить большой файл в середину списка
        //listFile.set(listFile.size()/2,"big.txt");

        //Добавить большой файл в коней списка
        //listFile.add("big.txt");

        //Сортировка локальных файлов по убыванию размер файла. онлайн файлы помещаются в конец списка
        listFile =  loadListFile.sortListInFile(listFile);

        String[] sources = listFile.toArray(new String[0]);

        //Список поисковых слов
        String[] words = new String[]{"картина","подушка","собака","шишка","земля","любовь","секунд","время","жизнь","красота",
                "мыло","еда","самолет","доктор","сила","бег","велосипед","красота","компьютер","окно",
                "шкаф","шарф","жираф","лошадь","фотография","ложь","дорога","дерево","зверь","корабль"};


        Occurencies occurencies = new Occurencies(8);
        occurencies.getOccurencies(sources,words,"asd.txt");

    }


}
