package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductSellDaily;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductSellDailyDaoTest {
    @Autowired
    private ProductSellDailyDao productSellDailyDao;

    @Test
    @Ignore
    public void testAInsertProductSellDaily(){
        int effectedNum = productSellDailyDao.insertProductSellDaily();
    }
    @Test
    public void testBQueryProductSellDailyList(){
        ProductSellDaily productSellDaily = new ProductSellDaily();
        Product product = new Product();
        product.setProductId(1L);
        productSellDaily.setProduct(product);
        productSellDailyDao.queryProductSellDailyList(productSellDaily,null,null);

    }
}
