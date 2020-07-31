package kr.co.company.demo;

import kr.co.company.demo.user.vo.res.UserDefaultResVO;
import kr.co.company.persistence.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {
	@Autowired
	private UserRepository userRepo;

	@Test
	public void testFindByUseYn() {
		UserDefaultResVO resVO = new UserDefaultResVO();
		resVO.setUseYn("Y");

		userRepo.findAllByUseYn("Y")
				.forEach(user -> System.out.println(user));
	}

	@Test
	public void testInsertUser() {
		IntStream.range(1, 100).forEach(i -> {
			UserDefaultResVO resVO = new UserDefaultResVO();
			resVO.setUserId("admin" + i);
			resVO.setUserPw("admin");
			resVO.setUserName("admin");
			resVO.setUserBirth(new Timestamp(new Date().getTime()));
			resVO.setUserSex("M");
			resVO.setRegUser("admin");

			userRepo.save(resVO);
		});
	}
}
