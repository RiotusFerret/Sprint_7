import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

import static data.TestData.BASE_URL;

public class BaseTest {
    @Before
    public void setUp() {
        RestAssured.baseURI= BASE_URL;
    }

}
