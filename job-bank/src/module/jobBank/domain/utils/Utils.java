package module.jobBank.domain.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import pt.utl.ist.fenix.tools.util.CollectionPager;

public class Utils {

    private static final String charset = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static String getRandomString(int length) {
	Random rand = new Random(System.currentTimeMillis());
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < length; i++) {
	    int pos = rand.nextInt(charset.length() - 1);
	    sb.append(charset.charAt(pos));
	}
	return sb.toString();
    }

    public static <T> Set<T> readValuesToSatisfiedPredicate(IPredicate<T> predicate, Set<T> setToRead) {
	Set<T> results = new HashSet<T>();
	for (T object : setToRead) {
	    if (predicate.evaluate(object)) {
		results.add(object);
	    }
	}
	return results;

    }

    public static <T> Collection<T> doPagination(final HttpServletRequest request, Collection<T> allResults, int resultsPerPage) {
	final CollectionPager<T> pager = new CollectionPager<T>(allResults, resultsPerPage);
	request.setAttribute("collectionPager", pager);
	request.setAttribute("numberOfPages", Integer.valueOf(pager.getNumberOfPages()));
	final String pageParameter = request.getParameter("pageNumber");
	final Integer page = StringUtils.isEmpty(pageParameter) ? Integer.valueOf(1) : Integer.valueOf(pageParameter);
	request.setAttribute("pageNumber", page);
	return pager.getPage(page);
    }
}
