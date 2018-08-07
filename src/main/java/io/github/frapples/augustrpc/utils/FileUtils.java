package io.github.frapples.augustrpc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/7
 */
final public class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException();
    }

    public static List<URL> getResouces(String path) throws IOException {
        Enumeration<URL> resources = FileUtils.class.getClassLoader().getResources(path);
        List<URL> urls = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            urls.add(url);
        }
        return urls;
    }

    public static URL getResource(String path) {
        return FileUtils.class.getClassLoader().getResource(path);
    }

    public static String readFromStream(InputStream inputStream, Charset charset) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream, charset);
        char[] buffer = new char[1024];
        StringBuilder stringBuilder = new StringBuilder();
        int count;
        while ((count = reader.read(buffer)) > 0) {
            stringBuilder.append(buffer, 0, count);
        }
        return stringBuilder.toString();
    }

}
