import io.restassured.RestAssured;
import org.junit.Before;

import static data.TestData.BASE_URL;

public class BaseTest {
    @Before
    public void setUp() {
        RestAssured.baseURI= BASE_URL;
    }

}
