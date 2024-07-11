import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;


public class TestMain {


    @Test
    public void testMainWelcome() {
       OutputStream out = new ByteArrayOutputStream();
       System.setOut(new PrintStream(out));

       CongoDriver.main(new String[0]);

       String expected = "Welcome to Congo Books!";

       assertEquals(out.toString().trim(), expected);


    }

    
}
