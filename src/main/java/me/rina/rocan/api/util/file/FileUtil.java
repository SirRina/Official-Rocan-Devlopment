package me.rina.rocan.api.util.file;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author SrRina
 * @since 22/01/2021 at 23:29
 **/
public class FileUtil {
    public final static int BUFFER = 2048;

    /**
     * Simple extract zip to manage zip files.
     *
     * @param zipPath the current zip file path.
     * @param extractedPath the extracted path of zip file.
     */
    public static void extractZipFolder(String zipPath, String extractedPath) {
        try {
            File file = new File(zipPath);
            ZipFile zip = new ZipFile(file);

            new File(extractedPath).mkdir();

            Enumeration zipFilesEntries = zip.entries();

            while (zipFilesEntries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) zipFilesEntries.nextElement();

                File destFile = new File(extractedPath, entry.getName());
                File destinationFileParent = destFile.getParentFile();

                destinationFileParent.mkdir();

                if (entry.isDirectory() == false) {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(zip.getInputStream(entry));

                    int currentByte;

                    byte data[] = new byte[BUFFER];

                    FileOutputStream fileOutputStream = new FileOutputStream(destFile);
                    BufferedOutputStream dest = new BufferedOutputStream(fileOutputStream, BUFFER);

                    while ((currentByte = bufferedInputStream.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, currentByte);
                    }

                    dest.flush();
                    dest.close();

                    bufferedInputStream.close();
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void compressFolder(File folder, ZipOutputStream zip, String baseName) throws IOException {
        File[] fileArray = folder.listFiles();

        for (File files : fileArray) {
            if (files.isDirectory()) {
                compressFolder(files, zip, baseName);
            } else {
                String name = files.getAbsolutePath().substring(baseName.length());

                ZipEntry zipEntry = new ZipEntry(name);
                zip.putNextEntry(zipEntry);

                IOUtils.copy(new FileInputStream(files), zip);

                zip.closeEntry();
            }
        }
    }
}
