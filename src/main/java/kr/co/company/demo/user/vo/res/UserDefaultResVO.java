package kr.co.company.demo.user.vo.res;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user_tb")
@EqualsAndHashCode(of = "userSeq")
public class UserDefaultResVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userSeq;

	@NotNull
	@Value(value = "01")
	private String userAdmin;

	private String userId;

	private String userPw;

	private String userName;

	private Timestamp userBirth;

	private String userSex;

	private String userDesc;

	private Timestamp userConnectTime;

	private String userValidateYn;

	private String userIdentifyKey;

	private String useYn;

	private String delYn;

	private String banYn;

	private String regUser;

	private String delUser;

	private String banUser;

	@CreationTimestamp
	private Timestamp regDate;

	@UpdateTimestamp
	private Timestamp updDate;

	private Timestamp delDate;

	private Timestamp banDate;

	private String userBanComment;

}
