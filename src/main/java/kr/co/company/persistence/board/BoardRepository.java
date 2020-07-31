package kr.co.company.persistence.board;

import kr.co.company.demo.board.vo.res.BoardDefaultResVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoardRepository extends CrudRepository<BoardDefaultResVO, Long>, QuerydslPredicateExecutor<BoardDefaultResVO> {
	// find<Clazz>By<Column>
	List<BoardDefaultResVO> findByTitle(String title);
	List<BoardDefaultResVO> findByContentsContains(String contents);
	Page<BoardDefaultResVO> findByBoardSeqGreaterThan(Long boardSeq, Pageable paging);
}
