/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.charles7c.continew.starter.log.httptracepro.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import top.charles7c.continew.starter.core.constant.StringConstants;
import top.charles7c.continew.starter.log.common.model.RecordableHttpRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 可记录的 HTTP 请求信息适配器
 *
 * @author Andy Wilkinson（Spring Boot Actuator）
 * @author Charles7c
 */
public final class RecordableServletHttpRequest implements RecordableHttpRequest {

    private final HttpServletRequest request;

    public RecordableServletHttpRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getMethod() {
        return request.getMethod();
    }

    @Override
    public URI getUri() {
        String queryString = request.getQueryString();
        if (StrUtil.isBlank(queryString)) {
            return URI.create(request.getRequestURI());
        }
        try {
            return new URI(this.appendQueryString(queryString));
        } catch (URISyntaxException e) {
            String encoded = UriUtils.encodeQuery(queryString, StandardCharsets.UTF_8);
            return URI.create(this.appendQueryString(encoded));
        }
    }

    @Override
    public String getIp() {
        return JakartaServletUtil.getClientIP(request);
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return JakartaServletUtil.getHeadersMap(request);
    }

    @Override
    public String getBody() {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (null != wrapper) {
            return StrUtil.utf8Str(wrapper.getContentAsByteArray());
        }
        return StringConstants.EMPTY;
    }

    @Override
    public Map<String, Object> getParam() {
        String body = this.getBody();
        return StrUtil.isNotBlank(body) && JSONUtil.isTypeJSON(body)
                ? JSONUtil.toBean(body, Map.class)
                : Collections.unmodifiableMap(request.getParameterMap());
    }

    private String appendQueryString(String queryString) {
        return request.getRequestURI() + StringConstants.QUESTION_MARK + queryString;
    }
}