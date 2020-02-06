import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class Test {

    public static void main(String[] args) throws IOException, InterruptedException {

    	long time = System.currentTimeMillis();
    	
        File folder = new File("/tmp/test/deployment");
        folder.mkdirs();
        
        Path path = folder.toPath();
        Path file1 = path.resolve(time + "-1.txt");
        Path file2 = path.resolve(time + "-2.txt");
        
        file1 = Files.createFile(file1);
        file2 = Files.createFile(file2);

        FileTime lastModifiedTime = Files.getLastModifiedTime(file1);
        
        System.out.println("Setting the timestamp of file2 to " + lastModifiedTime.toMillis());
        Files.setLastModifiedTime(file2, lastModifiedTime);
        System.out.println("Getting the timestamp of file2 to " + Files.getLastModifiedTime(file2).toMillis());
        
        long file1Millis = Files.getLastModifiedTime(file1).toMillis();
        long file2Millis = file2.toFile().lastModified();
        
        if (file1Millis == file2Millis) {
        	System.out.println("Timestamps are equal - no bug is found");
        } else {
        	System.out.println("Timestamps are not equal - It should... this is a bug");	
        }  
    }
}
