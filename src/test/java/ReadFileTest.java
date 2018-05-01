import org.apache.log4j.Logger;
import org.junit.*;
import ru.innopolis.stc9.task16.ReadFile;


import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ReadFileTest {

    private static Logger logger = Logger.getLogger(ReadFile.class);

    private ReadFile readFile;

    @BeforeClass
    public static void beforeTests(){
        logger.info("@BeforeClass");
    }

    @Before
    public void before(){
        logger.info("@Before");
        this.readFile = new ReadFile();
    }

    @Test
    public void getInputStreamNoData() throws IOException {
        logger.info("getInputStreamNoData");
        String path = null;
        InputStream inputStream = this.readFile.getInputStream(path);
        assertEquals(inputStream,null);
    }

    @Test
    public void getInputStreamOnline() throws IOException {
        logger.info("getInputStreamNoData");
        String path = "https://yandex.ru";
        InputStream inputStream =  this.readFile.getInputStream(path);

        assertNotNull(inputStream);
    }

    @Test
    public void getInputStreamLocal() throws IOException {
        logger.info("getInputStreamNoData");
        String path = "file/97.txt";
        InputStream inputStream = this.readFile.getInputStream(path);
        assertNotNull(inputStream);
    }

}
