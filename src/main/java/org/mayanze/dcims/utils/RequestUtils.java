package org.mayanze.dcims.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Auther: mayanze
 * @Date: 2018/10/12 11:05
 * @Description:
 */
public class RequestUtils {

	private static final Logger log = LoggerFactory.getLogger(RequestUtils.class);

	public static String getLines(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader;
		try {
			reader = request.getReader();
			String line;
			// 没读取完毕，会加line
			while ((line = reader.readLine()) != null)
				sb.append(line);
		} catch (IOException e) {
			log.debug(null, e);
		}
		return sb.toString();
	}
}
