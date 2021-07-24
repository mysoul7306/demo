/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.demo.api.spring.framework.advice.repo;

import kr.co.rokroot.core.abstracts.AbstractRestResponse;
import kr.co.rokroot.core.exceptions.BizException;
import kr.co.rokroot.core.type.QueryType;
import kr.co.rokroot.core.type.ResultType;
import kr.co.rokroot.core.utilities.ObjectUtility;
import kr.co.rokroot.core.wrappers.RestEmptyResponse;
import kr.co.rokroot.core.wrappers.RestListResponse;
import kr.co.rokroot.core.wrappers.RestRequest;
import kr.co.rokroot.core.wrappers.RestSingleResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MariaRepository extends DefaultTransactionDefinition {

    @Getter
    @Resource(name = "mariaSqlSessionTemplate")
    private final SqlSessionTemplate sqlSession;

    @Getter
    @Resource(name = "mariaTransactionManager")
    private final DataSourceTransactionManager transaction;


    protected final String BASE_REPO = "kr.co.rokroot.demo.api.spring.framework.advice.repo.MariaRepository.";
    private TransactionStatus status;

    public interface Type<T> {
        T work();
    }

    public interface TypeList<T> {
        List<T> work(List<Map> typeList);
    }


    public <REQ extends Serializable, RES extends Serializable> RestSingleResponse<RES> singleWrapper(String statement, RestRequest<REQ> reqDTO, Type<RES> type){
        Map<String, Object> map = ObjectUtility.castMap(reqDTO.getData());

        return this.singleWrapper(statement, map, type);
    }

    public <REQ extends Serializable, RES extends Serializable> RestListResponse<RES> listWrapper(String statement, RestRequest<REQ> reqDTO, TypeList<RES> typeList){
        Map<String, Object> map = ObjectUtility.castMap(reqDTO.getData());

        return this.listWrapper(statement, map, typeList);
    }

    public <REQ extends Serializable> RestEmptyResponse insertWrapper(String statement, RestRequest<REQ> reqDTO){
        Map<String, Object> map = ObjectUtility.castMap(reqDTO.getData());

        return this.insertWrapper(statement, map);
    }

    public <REQ extends Serializable> RestEmptyResponse updateWrapper(String statement, RestRequest<REQ> reqDTO){
        Map<String, Object> map = ObjectUtility.castMap(reqDTO.getData());

        return this.updateWrapper(statement, map);
    }


    protected <RES extends Serializable> RestSingleResponse<RES> singleWrapper(String statement, Map<String, Object> map, Type<RES> type) {
        map = this.callDatabase(QueryType.SELECT_ONE, statement, map);

        RES resDTO = map.get("result") == null? null : ObjectUtility.castDTO(type.work(), (Map<String, Object>) map.get("result"));
        map.clear();

        return this.setMessage(RestSingleResponse.create(resDTO).resultCnt(resDTO == null? 0 : 1));
    }

    protected <RES extends Serializable> RestListResponse<RES> listWrapper(String statement, Map<String, Object> map, TypeList<RES> typeList) {
        map = this.callDatabase(QueryType.SELECT_LIST, statement, map);

        List<RES> resListDTO = typeList.work(((List<Map>) map.get("result")).stream().filter(Objects::nonNull).collect(Collectors.toList()));
        map.clear();

        return this.setMessage(RestListResponse.create(resListDTO).resultCnt(resListDTO.size()));
    }

    protected RestEmptyResponse insertWrapper(String statement, Map<String, Object> map) {
        map = this.callDatabase(QueryType.INSERT, statement, map);

        Integer result = Integer.parseInt(map.get("result") == null || Integer.parseInt(map.get("result").toString()) < 0? "0" : map.get("result").toString());
        map.clear();

        return this.setMessage(RestEmptyResponse.create(result));
    }

    protected RestEmptyResponse updateWrapper(String statement, Map<String, Object> map) {
        map = this.callDatabase(QueryType.UPDATE, statement, map);

        Integer result = Integer.parseInt(map.get("result") == null || Integer.parseInt(map.get("result").toString()) < 0? "0" : map.get("result").toString());
        map.clear();

        return this.setMessage(RestEmptyResponse.create(result));
    }

    protected synchronized Map<String, Object> callDatabase(QueryType mapping, String statement, Map<String, Object> param) {
        long startTime = 0L;
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            startTime = System.currentTimeMillis();
            this.start(statement);
            switch (mapping) {
                case SELECT_ONE:
                    result.put("result", this.getSqlSession().selectOne(this.BASE_REPO + statement, param));
                    break;
                case SELECT_LIST:
                    result.put("result", this.getSqlSession().selectList(this.BASE_REPO + statement, param));
                    break;
                case INSERT:
                    result.put("result", this.getSqlSession().insert(this.BASE_REPO + statement, param));
                    break;
                case UPDATE:
                    result.put("result", this.getSqlSession().update(this.BASE_REPO + statement, param));
                    break;
                case DELETE:
                    result.put("result", this.getSqlSession().delete(this.BASE_REPO + statement, param));
                    break;
            }
            this.commit();
        } catch (UncategorizedSQLException e) {
            throw new UncategorizedSQLException("Wrong statement called", statement, e.getSQLException());
        } catch (BadSqlGrammarException e) {
            throw new BadSqlGrammarException("API query error", statement, e.getSQLException());
        } catch (MyBatisSystemException e) {
            throw new MyBatisSystemException(e);
        } catch (TransactionException e) {
            throw new TransactionTimedOutException("Transaction timeout, Default timeout(ms) : " + this.getTransaction().getDefaultTimeout() * 1000);
        } catch (Exception e) {
            throw new BizException("Undefined exception error", e);
        } finally {
            this.finish();
            param.clear();
            log.info("Demo API - Database call : {}, Established time(ms) : {}", statement, System.currentTimeMillis() - startTime);
        }

        return result;
    }

    protected void start(String statement) throws TransactionException {
        this.setName(statement);
        this.status = this.getTransaction().getTransaction(this);
    }

    protected void commit() throws TransactionException {
        if (! this.status.isCompleted()) {
            this.getTransaction().commit(this.status);
        }
    }

    protected void finish() throws TransactionException {
        if (! this.status.isCompleted()) {
            this.getTransaction().rollback(this.status);
        }
    }

    protected <RES extends AbstractRestResponse> RES setMessage(RES response) {
        if (response.hasData()) {
            response.setResultType(ResultType.OK);
            response.setResultMsg("SUCCESS");
        } else {
            response.setResultType(ResultType.ERROR);
            response.setResultMsg("NO DATA");
        }

        return response;
    }
}
