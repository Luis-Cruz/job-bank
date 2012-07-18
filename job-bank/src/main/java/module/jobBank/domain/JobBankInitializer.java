package module.jobBank.domain;

import javax.servlet.http.HttpServletRequest;

import pt.ist.bennu.core.domain.ModuleInitializer;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.fenixWebFramework.servlets.filters.contentRewrite.RequestChecksumFilter;
import pt.ist.fenixWebFramework.servlets.filters.contentRewrite.RequestChecksumFilter.ChecksumPredicate;

public class JobBankInitializer implements ModuleInitializer {

    public JobBankInitializer() {
	super();
	// setMyorg(MyOrg.getInstance());
    }

    @Override
    public void init(MyOrg root) {
	RequestChecksumFilter.registerFilterRule(new ChecksumPredicate() {
	    public boolean shouldFilter(HttpServletRequest httpServletRequest) {
		return !(httpServletRequest.getRequestURI().endsWith("jobBank/enterprise/emailValidation.do"));
		// && httpServletRequest.getQueryString() != null &&
		// httpServletRequest.getQueryString().contains(
		// "method=emailValidation"));
	    }
	});
    }
}
