package kr.co.company.persistence.user;

import kr.co.company.demo.user.vo.res.UserDefaultResVO;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserDefaultResVO, Long>, QuerydslPredicateExecutor<UserDefaultResVO> {
	// find<Clazz>By<Column>
	List<UserDefaultResVO> findAllByUseYn(String useYn);
}
