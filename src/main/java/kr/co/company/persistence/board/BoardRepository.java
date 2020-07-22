package kr.co.company.persistence.board;

import kr.co.company.demo.board.vo.res.BoardDefaultResVO;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface BoardRepository extends CrudRepository<BoardDefaultResVO, Long> {
	// find<Clazz>By<Column>
	List<BoardDefaultResVO> findBoardDefaultResVOByTitle(String title);
	List<BoardDefaultResVO> findBoardDefaultResVOSByContentsContains(String contents);
	Collection<BoardDefaultResVO> findBoardDefaultResVOByWriterEqualsAndContentsContains(String writer, String contents);
}
