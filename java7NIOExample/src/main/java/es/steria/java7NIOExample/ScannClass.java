package es.steria.java7NIOExample;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScannClass {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScannClass.class);

	/** Path que se tiene que recorrer */
	private static final String PATH = "F:/CTORRESG/ZZZ Otro/Curriculum";

	/** Formato de ficheros que se listaran */
	enum PATTERS {doc, pdf, jpg, zip};

	/**
	 * Scans the directory using the glob pattern passed as parameter.
	 * @param folder directory to scan
	 * @param pattern glob pattern (filter)
	 */
	private void scan(final String pattern) {
		// obtains the Images directory in the app directory
		final Path dir = Paths.get(PATH);
		// the Files class offers methods for validation
		if (Files.exists(dir) || Files.isDirectory(dir)) {
			// Try with resources... so nice!
			try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir, pattern)) {
				// iterate over the content of the directory
				int count = 0;
				for (final Path path : ds) {
					LOGGER.info(path.getFileName().toString());
					count++;
				}
				LOGGER.info("%d Files match the pattern", count);
			} catch (final IOException ex) {
				LOGGER.error("", ex);
			}
		} else {
			LOGGER.error("No existe el directorio solicitado");
		}
	}

	/**
	 * Scans the directory using the patterns passed as parameters. Only 3
	 * patterns will be used.
	 * @param folder directory to scan
	 * @param patterns The first pattern will be used as the glob pattern for the DirectoryStream.
	 */
	private void secondScan(final String... patterns) {
        //obtains the Images directory in the app directory
        final Path dir = Paths.get(PATH);
        //the Files class offers methods for validation
        if (Files.exists(dir) || Files.isDirectory(dir)) {
        	//validate at least the glob pattern
            if (patterns == null || patterns.length < 1) {
                LOGGER.error("Please provide at least the glob pattern.");
                return;
            }

            //obtain the objects that implements PathMatcher
            PathMatcher extraFilterOne = null;
            PathMatcher extraFilterTwo = null;
            if (patterns.length > 1 && patterns[1] != null) {
                extraFilterOne = FileSystems.getDefault().getPathMatcher(patterns[1]);
            }
            if (patterns.length > 2 && patterns[2] != null) {
                extraFilterTwo = FileSystems.getDefault().getPathMatcher(patterns[2]);
            }

            //Try with resources... so nice!
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir, patterns[0])) {
                //iterate over the content of the directory and apply
                //any other extra pattern
                int count = 0;
                for (final Path path : ds) {
                	LOGGER.info("Evaluating " + path.getFileName());
                    if (extraFilterOne != null &&
                        extraFilterOne.matches(path.getFileName())) {
                    	LOGGER.info("Match found Do something!");
                    }
                    if (extraFilterTwo != null &&
                        extraFilterTwo.matches(path.getFileName())) {
                    	LOGGER.info("Match found Do something else!");
                    }
                    count++;
                }

                LOGGER.info("%d Files match the global pattern\n", count);
            } catch (final IOException ex) {
                LOGGER.error("", ex);
            }
        } else {
        	LOGGER.error("No existe el directorio solicitado");
        }
    }

	public static void main(final String args[]) {
		final ScannClass scannClass = new ScannClass();
		scannClass.scan(ScannClass.PATTERS.doc.toString());
		scannClass.secondScan();
	}

}
