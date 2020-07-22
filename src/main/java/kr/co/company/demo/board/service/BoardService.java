package kr.co.company.demo.board.service;

import kr.co.company.persistence.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepo;


}
