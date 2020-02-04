import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException, InterruptedException {

        File folder = new File("/tmp/test/deployment");
        folder.mkdirs();

        File file = new File(folder, "file.txt");
        file.createNewFile();

        while (true) {
            long deploymentTimestamp = getDeploymentTimestamp(folder);
            System.out.println("Time: " + deploymentTimestamp);
            Thread.sleep(500);
        }

    }

    private static long getDeploymentTimestamp(File deploymentFile) {
        if (deploymentFile.isDirectory()) {
            // Scan for most recent file
            long latest = deploymentFile.lastModified();
            for (File child : listDirectoryChildren(deploymentFile)) {
                long childTimestamp = getDeploymentTimestamp(child);
                if (childTimestamp > latest) {
                    latest = childTimestamp;
                }
            }
            return latest;
        } else {
            return deploymentFile.lastModified();
        }
    }

    private static List<File> listDirectoryChildren(File directory) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory.toPath())) {
            final List<File> result = new ArrayList<>();
            for (Path entry : stream) {
                result.add(entry.toFile());
            }
            return result;
        } catch (SecurityException | IOException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

}
