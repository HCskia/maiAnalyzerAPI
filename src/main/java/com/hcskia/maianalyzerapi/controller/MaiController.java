package com.hcskia.maianalyzerapi.controller;

import com.auth0.jwt.interfaces.Claim;
import com.hcskia.maianalyzerapi.pojo.MaiData;
import com.hcskia.maianalyzerapi.pojo.User;
import com.hcskia.maianalyzerapi.repository.UserRepository;
import com.hcskia.maianalyzerapi.service.TokenUtli;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/mai")
public class MaiController {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostMapping(value = "/datarefresh",consumes = "application/json")
    public String DataRefresh(@RequestHeader(value = "Authorization") String token,@RequestBody Map<String, Map> datas) throws Exception {
        Map<String, Claim> claimMap = TokenUtli.VerifyJWTToken(token);
        String userID = null;
        for (Map.Entry<String, Claim> entry : claimMap.entrySet()) {
            if (Objects.equals(entry.getKey(), "userid")){
                userID = String.valueOf(entry.getValue());
                break;
            }
        }
        assert userID != null;//因为token生成后用户名不可能为null
        userID = userID.replace("\"","");
        StringBuilder sqlBuilder = new StringBuilder();
        List<Map<String, Object>> tableStructure = jdbcTemplate.queryForList("SHOW TABLES LIKE '" +userID+ "';");
        if (!tableStructure.isEmpty()){
            jdbcTemplate.execute("DROP TABLE "+ userID + ";");
        }
        String sql = "CREATE TABLE `"+ userID + "` (`Achievements` double DEFAULT NULL,`Fc` varchar(5) DEFAULT NULL,`Fs` varchar(5) DEFAULT NULL,`Id` varchar(10) DEFAULT NULL,`Level` varchar(10) DEFAULT NULL,`LevelIndex` int(3) DEFAULT NULL,`Title` varchar(100) DEFAULT NULL,`Type` varchar(5) DEFAULT NULL);";
        jdbcTemplate.execute(sql);
        List<Object> list = (List<Object>) datas.get("data").get("verlist");//强制类型转换
        Iterator<Object> iterator = list.iterator();
        while(iterator.hasNext()){
            Map<String,Object> tempData = (Map<String, Object>) iterator.next();
            String tempSql = "INSERT INTO `" + userID +"` (`Achievements`, `Fc`, `Fs`, `Id`, `Level`, `LevelIndex`, `Title`, `Type`) VALUES (";
            tempSql += tempData.get("achievements")+",'";
            tempSql += tempData.get("fc")+"','";
            tempSql += tempData.get("fs")+"','";
            tempSql += tempData.get("id")+"','";
            tempSql += tempData.get("level")+"',";
            tempSql += tempData.get("level_index")+",'";
            tempSql += tempData.get("title").toString().replace("'","")+"','";
            tempSql += tempData.get("type")+"');";
            jdbcTemplate.execute(tempSql);
        }
        return "SUCCESS";
    }

    @GetMapping(value = "/getdata")
    public List<Map<String, Object>> GetData(@RequestHeader(value = "Authorization") String token) throws Exception {
        Map<String, Claim> claimMap = TokenUtli.VerifyJWTToken(token);
        String userID = null;
        for (Map.Entry<String, Claim> entry : claimMap.entrySet()) {
            if (Objects.equals(entry.getKey(), "userid")){
                userID = String.valueOf(entry.getValue());
                break;
            }
        }
        assert userID != null;//因为token生成后用户名不可能为null
        userID = userID.replace("\"","");
        List<Map<String, Object>> tableStructure = jdbcTemplate.queryForList("SHOW TABLES LIKE '" +userID+ "';");
        if (tableStructure.isEmpty()){
            return null;
        }

        tableStructure = jdbcTemplate.queryForList("SELECT * FROM `" +userID+ "`;");
        return tableStructure;
    }
}
