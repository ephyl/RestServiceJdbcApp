package ephyl.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class ConverterFromJSON {

    public static <T> Optional<T> convertFromJson(HttpServletRequest req, T o ) throws IllegalArgumentException, IOException {
        T object = null;
        String data = mapJsonToString(req);
        if (data.contains("\"id\"") || req.getMethod().equals("POST")) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                object = (T)objectMapper.readValue(data, o.getClass());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(object);
    }

    private static String mapJsonToString(HttpServletRequest req) throws IOException {
        StringBuilder data = new StringBuilder();
        BufferedReader bf = req.getReader();
        while (bf.ready()) {
            data.append(bf.readLine());
        }
        return data.toString();
    }
}
