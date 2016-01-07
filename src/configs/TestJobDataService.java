package configs;
import java.util.List;

import com.bstek.bdf2.job.model.JobDefinition;
import com.bstek.bdf2.job.service.IJobDataService;

public class TestJobDataService implements IJobDataService {

	@Override
	public List<JobDefinition> filterJobs(List<JobDefinition> jobs) {
		// TODO Auto-generated method stub
		return jobs;
	}

	@Override
	public String getCompanyId() {
		// TODO Auto-generated method stub
		return "BUAA111";
	}

}
