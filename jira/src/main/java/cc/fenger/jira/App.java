package cc.fenger.jira;

import com.alibaba.fastjson.JSON;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.jira.rest.client.api.domain.ServerInfo;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class App {
    private static final Logger log                  = LoggerFactory.getLogger(com.atlassian.jira.rest.client.app.App.class);
    private static final String OPTION_JIRA_HOST_URL = "host";
    private static final String OPTION_JIRA_USERNAME = "username";
    private static final String OPTION_JIRA_PASSWORD = "password";
    private static final String ERROR_PARAMETER      = "Parameter {} is missing";
    private static final String COMMAND_LINE_SYNTAX  = "use the following parameters";

    public App() {
    }

    private static String url = "http://10.128.5.214:9999";
    private static String username = "robot";
    private static String password = "123456";

    public static void main(String[] args) {

        JiraRestClient client;
        try {
            client = (new AsynchronousJiraRestClientFactory()).createWithBasicHttpAuthentication(new URI(url), username, password);
        } catch (URISyntaxException var11) {
            log.error(var11.getMessage(), var11);
            return;
        }

        try {
            // 获取服务信息
            ServerInfo serverInfo = (ServerInfo)client.getMetadataClient().getServerInfo().claim();
            log.info("Found JIRA version {}", serverInfo.getVersion());

            // all projects
            client.getProjectClient().getAllProjects().claim().forEach(bp -> {
                log.info("Found project {}", JSON.toJSONString(bp));
            });

            // get project by key
            Project p = client.getProjectClient().getProject("P1").claim();
            log.info("Get project {}", JSON.toJSONString(p));

            // get users
            // client.getUserClient().

            // create issue
            // IssueInput ii = new IssueInput();

            // client.getIssueClient().createIssue();

        } catch (RestClientException e) {
            log.error("Error accessing JIRA, please check URL and credentials", e);
        }

    }
}

