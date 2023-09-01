package ephyl.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesUtilTest {

    @Test
    void get() {

        String url = PropertiesUtil.get("db.url");
        String name = PropertiesUtil.get("db.username");
        String password = PropertiesUtil.get("db.password");
        assertFalse(url.isEmpty());
        assertFalse(name.isEmpty());
        assertFalse(password.isEmpty());

    }
}