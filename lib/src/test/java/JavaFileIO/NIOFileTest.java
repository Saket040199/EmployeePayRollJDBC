package JavaFileIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;



public class NIOFileTest {

	private static String  HOME=System.getProperty("user.home");
	private static String NIO= "TemplePlayGround";
	
	@Test
	public void GivenPAthWhenCheckedThenConfirm() throws IOException {
	Path homePath=Paths.get(HOME);
	Assert.assertTrue(Files.exists(homePath));
	
	Path playpath=Paths.get(HOME + "/"+ NIO);
	if (Files.exists(playpath)) FileUtils.deleteFiles(playpath.toFile());
	Assert.assertTrue(Files.notExists(playpath));
	
	Files.createDirectory(playpath);
	Assert.assertTrue(Files.exists(playpath));
	
	IntStream.range(1, 10).forEach(cntr -> {
		Path tempfile=Paths.get(playpath +"/temp"+cntr);
		Assert.assertTrue(Files.notExists(tempfile));
		try { 
			Files.createFile(tempfile);
		}catch (IOException e) { }
		Assert.assertTrue(Files.exists(tempfile));
		
	});
	
	Files.list(playpath).filter(Files::isRegularFile).forEach(System.out::println);
	Files.newDirectoryStream(playpath).forEach(System.out::println);
	Files.newDirectoryStream(playpath, path -> path.toFile().isFile() &&  path.toString().startsWith("temp")).forEach(System.out::println);
	
	
	}
}
