package btx.bigboss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by BaiyangTX on 2017/1/3.
 */
@SpringBootApplication
@ServletComponentScan
public class Main {

    public static void main(String[] args){
//        args = new String[]{
//                "--spring.thymeleaf.cache=false"
//        };

        SpringApplication.run(Main.class,args);
    }
}
