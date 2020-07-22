package kr.co.company.demo;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void jayptStingEncryptTest() {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword("pk4s_Mj$Duv_1jcQ");

        String enc = pbeEnc.encrypt("grkim1!");
        System.out.println("enc = " + enc);

        String des = pbeEnc.decrypt(enc);
        System.out.println("des = " + des);
    }

}
