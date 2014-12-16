package org.ybygjy.jcip.chap2;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 缓存最近执行因数分解的数值及响应计算结果
 * @author WangYanCheng
 * @version 2014年12月16日
 */
public class CachedFactorizer implements Servlet {
	/** 记录最后一次因式分解的数值*/
	private BigInteger lastNumber;
	/** 记录最后一次因式分解结果*/
	private BigInteger[] lastFactors;
	/**计数*/
	private long hits;
	/**缓存命中计数*/
	private long cacheHits;
	@Override
	public void init(ServletConfig config) throws ServletException {
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		BigInteger i = extractFromRequest(req);
		if (null == i) {
			return;
		}
		BigInteger[] factors = null;
		synchronized(this) {
			++hits;
			if (i.equals(lastNumber)) {
				++cacheHits;
				factors = lastFactors.clone();
			}
		}
		if (factors == null) {
			factors = new FactorClass(i).doWork();
			synchronized(this) {
				lastNumber = i;
				lastFactors = factors.clone();
			}
		}
		encodeIntoResponse(res, factors);
	}

	/**
	 * 封装响应内容
	 * @param res {@link HttpResponse}
	 * @param factors 因子
	 */
	private void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
		try {
			res.getWriter().write(factors.toString());
			res.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从请求数据中取待分解的整数
	 * @param req {@link HttpRequest}
	 * @return rtnStr
	 */
	private BigInteger extractFromRequest(ServletRequest req) {
		String tmpValue = req.getParameter("TX_FACTOR_NUMBER");
		if (tmpValue != null && tmpValue.matches("\\d+")) {
			return BigInteger.valueOf(Long.parseLong(tmpValue));
		}
		return null;
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void destroy() {
	}
	/**
	 * 运算次数
	 * @return hits
	 */
	public long getHits() {
		return hits;
	}
	/**
	 * 缓存命中率
	 * @return cacheHitsRatio
	 */
	public synchronized double getCacheHitRatio() {
		double rtnValue = (double) cacheHits / (double) hits;
		return rtnValue;
	}
}
