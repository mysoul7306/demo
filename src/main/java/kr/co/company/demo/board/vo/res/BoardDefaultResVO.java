package kr.co.company.demo.board.vo.res;

import kr.co.company.demo.user.vo.res.UserDefaultResVO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@ToString(exclude = "userDefaultResVO")
@Table(name = "board_tb")
@EqualsAndHashCode(callSuper = false, of = "boardSeq")
public class BoardDefaultResVO implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardSeq;

	private String title;

	@ManyToOne
	private UserDefaultResVO userDefaultResVO;

	private String contents;

	@CreationTimestamp
	private Timestamp regDate;

	@UpdateTimestamp
	private Timestamp modDate;
}
