package com.zdxt;

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


	@Autowired
	private ActivityProgramMapper activityProgramMapper;

	@Test
	public void contextLoads() {
//		_search: false
////		nd: 1573565021747
////		limit: 3
////		page: 1
////		sidx:
////		order: asc

		Map map = new HashMap();
		map.put("limit",3);
		map.put("page",1);
		PageQueryUtil pageQueryUtil = new PageQueryUtil(map);
        List<ActivityProgram> activityProgramList = activityProgramMapper.findActivityProgramList(pageQueryUtil);


    }

}
