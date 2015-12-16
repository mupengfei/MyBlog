package test.kankanews.search.action.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * JUnit测试action时使用的基类
 * 
 * @author fule
 * 
 */
public class JUnitActionBase {
	private static HandlerMapping handlerMapping;
	private static HandlerAdapter handlerAdapter;

	/**
	 * 读取配置文件
	 */
	@BeforeClass
	public static void setUp() {
		if (handlerMapping == null) {

			String[] configs = {
					"file:src/main/resources/spring/springmvc-config.xml",
					"file:src/main/resources/spring/spring-search-bean.xml",
					"file:src/main/resources/spring/spring-search-datasource.xml" };
			// String[] configs = { "file:src/config/applicationContext.xml" };
			XmlWebApplicationContext context = new XmlWebApplicationContext();
			context.setConfigLocations(configs);
			MockServletContext msc = new MockServletContext();
			context.setServletContext(msc);
			context.refresh();
			msc.setAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
					context);// TODO
			handlerMapping = (HandlerMapping) context
					.getBean(RequestMappingHandlerMapping.class);

			handlerAdapter = (HandlerAdapter) context
					.getBean(context
							.getBeanNamesForType(RequestMappingHandlerAdapter.class)[0]);

			// handlerAdapter = (HandlerAdapter) context.getBean(context
			// .getNamespace());
		}
	}

	/**
	 * 执行request请求的action
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView excuteAction(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 这里需要声明request的实际类型，否则会报错
		request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);
		HandlerExecutionChain chain = handlerMapping.getHandler(request);
		ModelAndView model = null;
		try {
			model = handlerAdapter
					.handle(request, response, chain.getHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}