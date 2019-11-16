package com.zdxt;

import com.zdxt.common.util.MD5Util;
import com.zdxt.common.util.PageQueryUtil;
import com.zdxt.mapper.ActivityProgramMapper;
import com.zdxt.model.ActivityProgram;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZdmanagementApplicationTests {


	@Test
	public void contextLoads() {
		System.out.println(MD5Util.MD5Encode("123456", "UTF-8"));
    }

}
