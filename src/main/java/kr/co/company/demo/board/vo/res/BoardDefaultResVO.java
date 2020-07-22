package kr.co.company.demo.board.vo.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity
@Table(name = "board_tb")
public class BoardDefaultResVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardSeq;
	private String title;
	private String writer;
	private String contents;

	@CreationTimestamp
	private Timestamp regDate;
	@UpdateTimestamp
	private Timestamp modDate;
}
