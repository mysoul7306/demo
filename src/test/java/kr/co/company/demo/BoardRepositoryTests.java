package kr.co.company.demo;

import kr.co.company.persistence.board.BoardRepository;
import kr.co.company.demo.board.vo.res.BoardDefaultResVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTests {
	@Autowired
	private BoardRepository boardRepo;

	@Test
	public void inspect() {
		// 실제 객체 이름
		Class<?> clazz = boardRepo.getClass();
		System.out.println(clazz.getName());

		// 클래스가 구현하고 있는 Interface List
		Class<?>[] interfaces = clazz.getInterfaces();
		Stream.of(interfaces).forEach(interfaceName -> System.out.println(interfaceName.getName()));

		// 클래스의 부모 클래스
		Class<?> superClass = clazz.getSuperclass();
		System.out.println(superClass.getName());
	}

	@Test
//	@Transactional
	public void testCreate() {
		BoardDefaultResVO boardDefaultResVO = new BoardDefaultResVO();
		boardDefaultResVO.setTitle("임태환");
		boardDefaultResVO.setWriter("김근록");
		boardDefaultResVO.setContents("화이팅");

		boardRepo.save(boardDefaultResVO);
	}

	@Test
	public void testRead() {
		boardRepo.findById(1L).ifPresent((board) -> {
			System.out.println(board);
		});
	}

	@Test
	public void testInsertBoard() {
		for (int i = 1; i <= 200; i++) {
			BoardDefaultResVO boardDefaultResVO = new BoardDefaultResVO();
			boardDefaultResVO.setTitle("제목" + i);
			boardDefaultResVO.setWriter("Administrator");
			boardDefaultResVO.setContents("Contents" + i);

			boardRepo.save(boardDefaultResVO);
		}
	}

	@Test
	public void testFindByTitle() {
		boardRepo.findBoardDefaultResVOByTitle("제목1")
				.forEach(board -> System.out.println(board));
	}

	@Test
	public void testFindBoardDefaultResVOSByContentsContains() {
		boardRepo.findBoardDefaultResVOSByContentsContains("1")
				.forEach(board -> System.out.println(board));
	}

	@Test
	public void testFindBoardDefaultResVOByWriterEqualsAndContentsContains() {
		boardRepo.findBoardDefaultResVOByWriterEqualsAndContentsContains("Administrator", "1")
				.forEach(board -> System.out.println(board));
	}
}
