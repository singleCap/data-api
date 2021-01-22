package org.bibt.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * data-api程序启动
 *
 * @author ZengFanyong
 * @date 2021/1/22
 */
@SpringBootApplication
@MapperScan("org.bibt.data.mapper")
public class DataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }

}
