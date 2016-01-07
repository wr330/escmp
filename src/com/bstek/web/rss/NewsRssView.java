package com.bstek.web.rss;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;
import com.google.common.collect.Lists;
import com.bstek.newstree.domain.NewsTree;
import com.sun.syndication.feed.rss.Content;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Item;

public class NewsRssView extends AbstractRssFeedView {

	public static String VIEW_NAME = "rss-feed-view";

	public NewsRssView() {
		setContentType("application/atom+xml; charset=utf-8");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void buildFeedMetadata(Map<String, Object> model, Channel feed,
			HttpServletRequest request) {
		feed.setLink((String)model.get("base"));
		feed.setEncoding("UTF-8");
		feed.setTitle("open-nrs");
		feed.setDescription("open-nrs | open source news release system");
		List<NewsTree> newsList = (List<NewsTree>) model.get("newsList");
		for (NewsTree news : newsList) {
			Date date = news.getCreateDate();
			if (feed.getLastBuildDate() == null
					|| date.compareTo(feed.getLastBuildDate()) > 0) {
				feed.setLastBuildDate(news.getCreateDate());
			}
			if (feed.getPubDate() == null
					|| date.compareTo(feed.getPubDate()) > 0) {
				feed.setPubDate(news.getCreateDate());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Item> buildFeedItems(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<NewsTree> newsList = (List<NewsTree>) model.get("newsList");
		List<Item> items = Lists.newArrayList();
		if (newsList != null)
			for (NewsTree news : newsList) {
				Item item = new Item();
				item.setTitle(news.getNodeTitle());
				item.setLink(String.format((String) model.get("basePath")+"dynamic/hot_news/%s",
						news.getNodeCode()));
				item.setContent(getContent(news));
				item.setPubDate(news.getCreateDate());
				items.add(item);
			}
		return items;
	}

	private Content getContent(NewsTree news) {
		Content content = new Content();
		content.setType(Content.HTML);
		content.setValue(news.getNodeContent());
		return content;
	}

}
