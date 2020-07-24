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
        // 사용할 알고리즘 종류
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        // jaypt에 사용 될 Password
        pbeEnc.setPassword("pk4s_Mj$Duv_1jcQ");

        // 암호화 키
        String enc = pbeEnc.encrypt("rmsfhr12!@");
        System.out.println("enc = " + enc);

        // 복호화 키
        String des = pbeEnc.decrypt(enc);
        System.out.println("des = " + des);
    }

}
