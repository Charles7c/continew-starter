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

package top.charles7c.continew.starter.log.httptracepro.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.charles7c.continew.starter.log.common.enums.Include;

import java.util.HashSet;
import java.util.Set;

/**
 * 日志配置属性
 *
 * @author Charles7c
 * @since 1.1.0
 */
@Data
@ConfigurationProperties(prefix = "continew-starter.log")
public class LogProperties {

    /**
     * 是否启用日志
     */
    private boolean enabled = false;

    /**
     * 是否打印日志，开启后可打印访问日志（类似于 Nginx access log）
     */
    private Boolean isPrint = false;

    /**
     * 包含信息
     */
    private Set<Include> include = new HashSet<>(Include.defaultIncludes());
}
