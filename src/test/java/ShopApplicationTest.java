import com.vzv.shop.ShopApplication;
import com.vzv.shop.controller.rest.*;
import com.vzv.shop.controller.web.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ShopApplication.class})
public class ShopApplicationTest {

    @Autowired
    private AuthController authController;
    @Autowired
    private AuthMvcController authMvcController;
    @Autowired
    private ConstructorMvcController constructorMvcController;
    @Autowired
    private OrderController orderController;
    @Autowired
    private OrderMvcController orderMvcController;
    @Autowired
    private PageController pageController;
    @Autowired
    private PictureController pictureController;
    @Autowired
    private ProductController productController;
    @Autowired
    private ProductMvcController productMvcController;
    @Autowired
    private SettlementAndStreetController settlementAndStreetController;
    @Autowired
    private SimpleDataController simpleDataController;
    @Autowired
    private UserController userController;
    @Autowired
    private UserMvcController userMvcController;

    @Test
    void contextLoads() {
        assertThat(authController).isNotNull();
        assertThat(authMvcController).isNotNull();
        assertThat(constructorMvcController).isNotNull();
        assertThat(orderController).isNotNull();
        assertThat(orderMvcController).isNotNull();
        assertThat(pictureController).isNotNull();
        assertThat(productController).isNotNull();
        assertThat(productMvcController).isNotNull();
        assertThat(settlementAndStreetController).isNotNull();
        assertThat(simpleDataController).isNotNull();
        assertThat(userController).isNotNull();
        assertThat(userMvcController).isNotNull();
    }
}
